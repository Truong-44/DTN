package com.example.tempotide.service.impl;

import com.example.tempotide.dto.ChiTietDonHangDTO;
import com.example.tempotide.entity.ChiTietDonHang;
import com.example.tempotide.entity.ChiTietDonHangId;
import com.example.tempotide.entity.ChiTietSanPham;
import com.example.tempotide.entity.DonHang;
import com.example.tempotide.entity.KhachHang;
import com.example.tempotide.mapper.ChiTietDonHangMapper;
import com.example.tempotide.repository.ChiTietDonHangRepository;
import com.example.tempotide.repository.ChiTietSanPhamRepository;
import com.example.tempotide.repository.DonHangRepository;
import com.example.tempotide.repository.KhachHangRepository;
import com.example.tempotide.service.ChiTietDonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final DonHangRepository donHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final ChiTietDonHangMapper chiTietDonHangMapper;

    @Override
    public List<ChiTietDonHangDTO> getOrderItems(Integer orderId) {
        DonHang donHang = donHangRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Kiểm tra quyền truy cập
        checkOrderAccess(donHang);

        return chiTietDonHangRepository.findByDonhangMadonhang(orderId)
                .stream()
                .map(chiTietDonHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChiTietDonHangDTO addOrderItem(ChiTietDonHangDTO chiTietDonHangDTO) {
        DonHang donHang = donHangRepository.findById(chiTietDonHangDTO.getMadonhang())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + chiTietDonHangDTO.getMadonhang()));

        // Kiểm tra trạng thái đơn hàng và quyền ADMIN/EMPLOYEE
        checkOrderEditable(donHang);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietDonHangDTO.getMachitietsanpham())
                .orElseThrow(() -> new RuntimeException("Product detail not found with ID: " + chiTietDonHangDTO.getMachitietsanpham()));

        // Kiểm tra số lượng tồn
        if (chiTietSanPham.getSoluongton() < chiTietDonHangDTO.getSoluong()) {
            throw new RuntimeException("Insufficient stock for product detail ID: " + chiTietDonHangDTO.getMachitietsanpham());
        }

        // Kiểm tra sản phẩm đã có trong đơn hàng
        ChiTietDonHang existingItem = chiTietDonHangRepository.findById(new ChiTietDonHangId(chiTietDonHangDTO.getMadonhang(), chiTietDonHangDTO.getMachitietsanpham()))
                .orElse(null);
        if (existingItem != null) {
            throw new RuntimeException("Product already exists in order");
        }

        ChiTietDonHang chiTietDonHang = chiTietDonHangMapper.toEntity(chiTietDonHangDTO);
        chiTietDonHang.setDonhang(donHang);
        chiTietDonHang.setChitietsanpham(chiTietSanPham);
        chiTietDonHang.setGia(chiTietSanPham.getGia()); // Lấy giá từ chitietsanpham

        chiTietDonHang = chiTietDonHangRepository.save(chiTietDonHang);

        // Cập nhật tổng tiền đơn hàng
        updateOrderTotal(donHang);

        return chiTietDonHangMapper.toDTO(chiTietDonHang);
    }

    @Override
    @Transactional
    public ChiTietDonHangDTO updateOrderItem(ChiTietDonHangDTO chiTietDonHangDTO) {
        DonHang donHang = donHangRepository.findById(chiTietDonHangDTO.getMadonhang())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + chiTietDonHangDTO.getMadonhang()));

        // Kiểm tra trạng thái đơn hàng và quyền ADMIN/EMPLOYEE
        checkOrderEditable(donHang);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietDonHangDTO.getMachitietsanpham())
                .orElseThrow(() -> new RuntimeException("Product detail not found with ID: " + chiTietDonHangDTO.getMachitietsanpham()));

        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(new ChiTietDonHangId(chiTietDonHangDTO.getMadonhang(), chiTietDonHangDTO.getMachitietsanpham()))
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        // Kiểm tra số lượng tồn
        if (chiTietSanPham.getSoluongton() + chiTietDonHang.getSoluong() < chiTietDonHangDTO.getSoluong()) {
            throw new RuntimeException("Insufficient stock for product detail ID: " + chiTietDonHangDTO.getMachitietsanpham());
        }

        chiTietDonHang.setSoluong(chiTietDonHangDTO.getSoluong());
        chiTietDonHang.setGia(chiTietSanPham.getGia()); // Cập nhật giá mới nhất

        chiTietDonHang = chiTietDonHangRepository.save(chiTietDonHang);

        // Cập nhật tổng tiền đơn hàng
        updateOrderTotal(donHang);

        return chiTietDonHangMapper.toDTO(chiTietDonHang);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Integer orderId, Integer productDetailId) {
        DonHang donHang = donHangRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Kiểm tra trạng thái đơn hàng và quyền ADMIN/EMPLOYEE
        checkOrderEditable(donHang);

        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(new ChiTietDonHangId(orderId, productDetailId))
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        chiTietDonHangRepository.delete(chiTietDonHang);

        // Cập nhật tổng tiền đơn hàng
        updateOrderTotal(donHang);
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

    private void checkOrderEditable(DonHang donHang) {
        boolean isAdminOrEmployee = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_EMPLOYEE"));
        if (!isAdminOrEmployee) {
            throw new RuntimeException("Unauthorized: Admin or Employee role required");
        }

        if (!donHang.getTrangthai().equals("Chờ xác nhận")) {
            throw new RuntimeException("Order cannot be modified in status: " + donHang.getTrangthai());
        }
    }

    private void updateOrderTotal(DonHang donHang) {
        List<ChiTietDonHang> orderItems = chiTietDonHangRepository.findByDonhangMadonhang(donHang.getMadonhang());
        BigDecimal subtotal = orderItems.stream()
                .map(item -> item.getGia().multiply(BigDecimal.valueOf(item.getSoluong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        donHang.setTongtien(subtotal.add(donHang.getPhivanchuyen() != null ? donHang.getPhivanchuyen() : BigDecimal.ZERO));
        donHangRepository.save(donHang);
    }
}