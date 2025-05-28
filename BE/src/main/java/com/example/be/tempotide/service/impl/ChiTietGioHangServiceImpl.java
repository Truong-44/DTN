package com.example.tempotide.service.impl;

import com.example.tempotide.dto.ChiTietGioHangDTO;
import com.example.tempotide.entity.ChiTietGioHang;
import com.example.tempotide.entity.ChiTietSanPham;
import com.example.tempotide.entity.GioHang;
import com.example.tempotide.entity.KhachHang;
import com.example.tempotide.mapper.ChiTietGioHangMapper;
import com.example.tempotide.repository.ChiTietGioHangRepository;
import com.example.tempotide.repository.ChiTietSanPhamRepository;
import com.example.tempotide.repository.GioHangRepository;
import com.example.tempotide.repository.KhachHangRepository;
import com.example.tempotide.service.ChiTietGioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {
    private final ChiTietGioHangRepository chiTietGioHangRepository;
    private final GioHangRepository gioHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final ChiTietGioHangMapper chiTietGioHangMapper;

    @Override
    public List<ChiTietGioHangDTO> getCartItems(Integer cartId) {
        GioHang gioHang = gioHangRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        // Kiểm tra quyền sở hữu hoặc ADMIN
        checkCartOwnership(gioHang);

        return chiTietGioHangRepository.findByGiohangMagiohang(cartId)
                .stream()
                .map(chiTietGioHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChiTietGioHangDTO addCartItem(ChiTietGioHangDTO chiTietGioHangDTO) {
        GioHang gioHang = gioHangRepository.findById(chiTietGioHangDTO.getMagiohang())
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + chiTietGioHangDTO.getMagiohang()));

        // Kiểm tra quyền sở hữu hoặc ADMIN
        checkCartOwnership(gioHang);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietGioHangDTO.getMachitietsanpham())
                .orElseThrow(() -> new RuntimeException("Product detail not found with ID: " + chiTietGioHangDTO.getMachitietsanpham()));

        // Kiểm tra số lượng tồn
        if (chiTietSanPham.getSoluongton() < chiTietGioHangDTO.getSoluong()) {
            throw new RuntimeException("Insufficient stock for product detail ID: " + chiTietGioHangDTO.getMachitietsanpham());
        }

        // Kiểm tra sản phẩm đã có trong giỏ
        ChiTietGioHang existingItem = chiTietGioHangRepository.findById(new ChiTietGioHangId(chiTietGioHangDTO.getMagiohang(), chiTietGioHangDTO.getMachitietsanpham()))
                .orElse(null);
        if (existingItem != null) {
            throw new RuntimeException("Product already exists in cart");
        }

        ChiTietGioHang chiTietGioHang = chiTietGioHangMapper.toEntity(chiTietGioHangDTO);
        chiTietGioHang.setGiohang(gioHang);
        chiTietGioHang.setChitietsanpham(chiTietSanPham);
        chiTietGioHang.setGia(chiTietSanPham.getGia()); // Lấy giá từ chitietsanpham

        chiTietGioHang = chiTietGioHangRepository.save(chiTietGioHang);
        return chiTietGioHangMapper.toDTO(chiTietGioHang);
    }

    @Override
    @Transactional
    public ChiTietGioHangDTO updateCartItem(ChiTietGioHangDTO chiTietGioHangDTO) {
        GioHang gioHang = gioHangRepository.findById(chiTietGioHangDTO.getMagiohang())
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + chiTietGioHangDTO.getMagiohang()));

        // Kiểm tra quyền sở hữu hoặc ADMIN
        checkCartOwnership(gioHang);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietGioHangDTO.getMachitietsanpham())
                .orElseThrow(() -> new RuntimeException("Product detail not found with ID: " + chiTietGioHangDTO.getMachitietsanpham()));

        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(new ChiTietGioHangId(chiTietGioHangDTO.getMagiohang(), chiTietGioHangDTO.getMachitietsanpham()))
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Kiểm tra số lượng tồn
        if (chiTietSanPham.getSoluongton() + chiTietGioHang.getSoluong() < chiTietGioHangDTO.getSoluong()) {
            throw new RuntimeException("Insufficient stock for product detail ID: " + chiTietGioHangDTO.getMachitietsanpham());
        }

        chiTietGioHang.setSoluong(chiTietGioHangDTO.getSoluong());
        chiTietGioHang.setGia(chiTietSanPham.getGia()); // Cập nhật giá mới nhất

        chiTietGioHang = chiTietGioHangRepository.save(chiTietGioHang);
        return chiTietGioHangMapper.toDTO(chiTietGioHang);
    }

    @Override
    @Transactional
    public void deleteCartItem(Integer cartId, Integer productDetailId) {
        GioHang gioHang = gioHangRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        // Kiểm tra quyền sở hữu hoặc ADMIN
        checkCartOwnership(gioHang);

        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(new ChiTietGioHangId(cartId, productDetailId))
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        chiTietGioHangRepository.delete(chiTietGioHang);
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
}