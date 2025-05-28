package com.example.tempotide.service.impl;

import com.example.tempotide.dto.ChiTietGioHangDTO;
import com.example.tempotide.dto.DonHangDTO;
import com.example.tempotide.entity.*;
import com.example.tempotide.mapper.ChiTietGioHangMapper;
import com.example.tempotide.mapper.DonHangMapper;
import com.example.tempotide.repository.*;
import com.example.tempotide.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonHangServiceImpl implements DonHangService {
    private final DonHangRepository donHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final GioHangRepository gioHangRepository;
    private final ChiTietGioHangRepository chiTietGioHangRepository;
    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final DonHangMapper donHangMapper;
    private final ChiTietGioHangMapper chiTietGioHangMapper;

    private static final List<String> VALID_STATUSES = List.of("Chờ xác nhận", "Đang xử lý", "Đang giao", "Hoàn thành", "Hủy");
    private static final List<String> VALID_PAYMENT_METHODS = List.of("COD", "Chuyển khoản", "Thẻ tín dụng");

    @Override
    public List<DonHangDTO> getAllOrders() {
        return donHangRepository.findAll()
                .stream()
                .map(donHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonHangDTO> getUserOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        KhachHang khachHang = khachHangRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        return donHangRepository.findByKhachhangMakhachhang(khachHang.getMakhachhang())
                .stream()
                .map(donHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DonHangDTO getOrderById(Integer id) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        // Kiểm tra quyền sở hữu hoặc ADMIN
        checkOrderAccess(donHang);

        return donHangMapper.toDTO(donHang);
    }

    @Override
    @Transactional
    public DonHangDTO createOrder(DonHangDTO donHangDTO, Integer cartId) {
        // Kiểm tra trạng thái và phương thức thanh toán
        validateOrderFields(donHangDTO);

        GioHang gioHang = gioHangRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        // Kiểm tra quyền sở hữu giỏ hàng
        checkCartOwnership(gioHang);

        List<ChiTietGioHang> cartItems = chiTietGioHangRepository.findByGiohangMagiohang(cartId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        DonHang donHang = donHangMapper.toEntity(donHangDTO);

        // Gán khách hàng
        if (donHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(donHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + donHangDTO.getMakhachhang()));
            donHang.setKhachhang(khachHang);
        }

        // Gán nhân viên (nếu có)
        if (donHangDTO.getManhanvien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(donHangDTO.getManhanvien())
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + donHangDTO.getManhanvien()));
            donHang.setNhanvien(nhanVien);
        }

        // Gán nguoitao
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        donHang.setNguoitao(nguoitao);
        donHang.setNguoicapnhat(nguoitao);

        // Tính tổng tiền
        BigDecimal subtotal = cartItems.stream()
                .map(item -> item.getGia().multiply(BigDecimal.valueOf(item.getSoluong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        donHang.setTongtien(subtotal.add(donHang.getPhivanchuyen() != null ? donHang.getPhivanchuyen() : BigDecimal.ZERO));

        donHang = donHangRepository.save(donHang);

        // Tạo chi tiết đơn hàng từ giỏ hàng
        for (ChiTietGioHang cartItem : cartItems) {
            ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
            chiTietDonHang.setDonhang(donHang);
            chiTietDonHang.setChitietsanpham(cartItem.getChitietsanpham());
            chiTietDonHang.setSoluong(cartItem.getSoluong());
            chiTietDonHang.setGia(cartItem.getGia());
            chiTietDonHangRepository.save(chiTietDonHang);
        }

        // Xóa giỏ hàng sau khi tạo đơn hàng
        chiTietGioHangRepository.deleteAll(cartItems);
        gioHang.setTrangthai(false);
        gioHangRepository.save(gioHang);

        return donHangMapper.toDTO(donHang);
    }

    @Override
    @Transactional
    public DonHangDTO updateOrder(Integer id, DonHangDTO donHangDTO) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        // Kiểm tra quyền ADMIN hoặc nhân viên
        checkAdminOrEmployeeAccess();

        // Kiểm tra trạng thái và phương thức thanh toán
        validateOrderFields(donHangDTO);

        // Cập nhật thông tin
        if (donHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(donHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + donHangDTO.getMakhachhang()));
            donHang.setKhachhang(khachHang);
        }
        if (donHangDTO.getManhanvien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(donHangDTO.getManhanvien())
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + donHangDTO.getManhanvien()));
            donHang.setNhanvien(nhanVien);
        }

        donHang.setTennguoinhan(donHangDTO.getTennguoinhan());
        donHang.setSodienthoai(donHangDTO.getSodienthoai());
        donHang.setDiachi(donHangDTO.getDiachi());
        donHang.setPhivanchuyen(donHangDTO.getPhivanchuyen());
        donHang.setTongtien(donHangDTO.getTongtien());
        donHang.setGhichu(donHangDTO.getGhichu());
        donHang.setTrangthai(donHangDTO.getTrangthai());
        donHang.setPhuongthucthanhtoan(donHangDTO.getPhuongthucthanhtoan());
        donHang.setNgaycapnhat(LocalDateTime.now());

        // Gán nguoicapnhat
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        donHang.setNguoicapnhat(nguoicapnhat);

        donHang = donHangRepository.save(donHang);
        return donHangMapper.toDTO(donHang);
    }

    @Override
    @Transactional
    public void cancelOrder(Integer id) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        // Kiểm tra quyền sở hữu hoặc ADMIN
        checkOrderAccess(donHang);

        if (donHang.getTrangthai().equals("Hoàn thành") || donHang.getTrangthai().equals("Hủy")) {
            throw new RuntimeException("Cannot cancel order with status: " + donHang.getTrangthai());
        }

        donHang.setTrangthai("Hủy");
        donHang.setNgaycapnhat(LocalDateTime.now());

        // Gán nguoicapnhat
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        donHang.setNguoicapnhat(nguoicapnhat);

        donHangRepository.save(donHang);
    }

    private void validateOrderFields(DonHangDTO donHangDTO) {
        if (!VALID_STATUSES.contains(donHangDTO.getTrangthai())) {
            throw new RuntimeException("Invalid order status: " + donHangDTO.getTrangthai());
        }
        if (!VALID_PAYMENT_METHODS.contains(donHangDTO.getPhuongthucthanhtoan())) {
            throw new RuntimeException("Invalid payment method: " + donHangDTO.getPhuongthucthanhtoan());
        }
    }

    private void checkOrderAccess(DonHang donHang) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        KhachHang currentUser = khachHangRepository.findByEmail(username).orElse(null);
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && (currentUser == null || (donHang.getKhachhang() != null && !donHang.getKhachhang().getMakhachhang().equals(currentUser.getMakhachhang())))) {
            throw new RuntimeException("Unauthorized to access this order");
        }
    }

    private void checkCartOwnership(GioHang gioHang) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        KhachHang currentUser = khachHangRepository.findByEmail(username).orElse(null);
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && (currentUser == null || (gioHang.getKhachhang() != null && !gioHang.getKhachhang().getMakhachhang().equals(currentUser.getMakhachhang())))) {
            throw new RuntimeException("Unauthorized to access this cart");
        }
    }

    private void checkAdminOrEmployeeAccess() {
        boolean isAdminOrEmployee = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_EMPLOYEE"));
        if (!isAdminOrEmployee) {
            throw new RuntimeException("Unauthorized: Admin or Employee role required");
        }
    }
}