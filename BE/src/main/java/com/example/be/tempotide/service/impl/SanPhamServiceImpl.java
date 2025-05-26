package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.sanphamdto;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.repository.danhmucrepository;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.sanphamrepository;
import com.example.be.tempotide.repository.thuonghieurepository;
import com.example.be.tempotide.service.SanPhamService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SanPhamServiceImpl implements SanPhamService {

    private static final Logger logger = LoggerFactory.getLogger(SanPhamServiceImpl.class);

    private final sanphamrepository sanPhamRepository;
    private final danhmucrepository danhMucRepository;
    private final thuonghieurepository thuongHieuRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public SanPhamServiceImpl(sanphamrepository sanPhamRepository, danhmucrepository danhMucRepository,
                              thuonghieurepository thuongHieuRepository, nhanvienrepository nhanVienRepository) {
        this.sanPhamRepository = sanPhamRepository;
        this.danhMucRepository = danhMucRepository;
        this.thuongHieuRepository = thuongHieuRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public sanphamdto createSanPham(sanphamdto sanPhamDto) {
        logger.info("Creating new SanPham: {}", sanPhamDto.getTenSanPham());
        sanpham sanPham = mapToEntity(sanPhamDto);
        sanPham.setMaDanhmuc(danhMucRepository.findById(sanPhamDto.getMaDanhMucId())
                .orElseThrow(() -> {
                    logger.error("MaDanhMuc not found with id: {}", sanPhamDto.getMaDanhMucId());
                    return new RuntimeException("MaDanhMuc not found");
                }));
        sanPham.setMaThuonghieu(thuongHieuRepository.findById(sanPhamDto.getMaThuongHieuId())
                .orElseThrow(() -> {
                    logger.error("MaThuongHieu not found with id: {}", sanPhamDto.getMaThuongHieuId());
                    return new RuntimeException("MaThuongHieu not found");
                }));
        if (sanPhamDto.getNguoiTaoId() != null) {
            sanPham.setNguoiTao(nhanVienRepository.findById(sanPhamDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", sanPhamDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (sanPhamDto.getNguoiCapNhatId() != null) {
            sanPham.setNguoiCapNhat(nhanVienRepository.findById(sanPhamDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", sanPhamDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        sanPham = sanPhamRepository.save(sanPham);
        logger.info("SanPham created with id: {}", sanPham.getMaSanPham());
        return mapToDto(sanPham);
    }

    @Override
    public sanphamdto getSanPhamById(Integer id) {
        logger.info("Fetching SanPham with id: {}", id);
        sanpham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", id);
                    return new RuntimeException("SanPham not found with id: " + id);
                });
        return mapToDto(sanPham);
    }

    @Override
    public List<sanphamdto> getAllSanPham() {
        logger.info("Fetching all SanPham");
        return sanPhamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public sanphamdto updateSanPham(Integer id, sanphamdto sanPhamDto) {
        logger.info("Updating SanPham with id: {}", id);
        sanpham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", id);
                    return new RuntimeException("SanPham not found with id: " + id);
                });

        sanPham.setTenSanPham(sanPhamDto.getTenSanPham());
        sanPham.setMoTa(sanPhamDto.getMoTa());
        sanPham.setGia(sanPhamDto.getGia());
        sanPham.setHinhAnh(sanPhamDto.getHinhAnh());
        sanPham.setMaDanhmuc(danhMucRepository.findById(sanPhamDto.getMaDanhMucId())
                .orElseThrow(() -> {
                    logger.error("MaDanhMuc not found with id: {}", sanPhamDto.getMaDanhMucId());
                    return new RuntimeException("MaDanhMuc not found");
                }));
        sanPham.setMaThuonghieu(thuongHieuRepository.findById(sanPhamDto.getMaThuongHieuId())
                .orElseThrow(() -> {
                    logger.error("MaThuongHieu not found with id: {}", sanPhamDto.getMaThuongHieuId());
                    return new RuntimeException("MaThuongHieu not found");
                }));
        sanPham.setTrangThai(sanPhamDto.getTrangThai());
        sanPham.setNgayCapNhat(sanPhamDto.getNgayCapNhat() != null ? sanPhamDto.getNgayCapNhat() : LocalDateTime.now());

        if (sanPhamDto.getNguoiCapNhatId() != null) {
            sanPham.setNguoiCapNhat(nhanVienRepository.findById(sanPhamDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", sanPhamDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        sanPham = sanPhamRepository.save(sanPham);
        logger.info("SanPham updated with id: {}", sanPham.getMaSanPham());
        return mapToDto(sanPham);
    }

    @Override
    public void deleteSanPham(Integer id) {
        logger.info("Deleting SanPham with id: {}", id);
        sanpham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", id);
                    return new RuntimeException("SanPham not found with id: " + id);
                });
        sanPhamRepository.delete(sanPham);
        logger.info("SanPham deleted with id: {}", id);
    }

    private sanphamdto mapToDto(sanpham sanPham) {
        return sanphamdto.builder()
                .maSanPham(sanPham.getMaSanPham())
                .tenSanPham(sanPham.getTenSanPham())
                .moTa(sanPham.getMoTa())
                .gia(sanPham.getGia())
                .hinhAnh(sanPham.getHinhAnh())
                .maDanhMucId(sanPham.getMaDanhmuc().getMaDanhMuc())
                .maThuongHieuId(sanPham.getMaThuonghieu().getMaThuongHieu())
                .ngayTao(sanPham.getNgayTao())
                .ngayCapNhat(sanPham.getNgayCapNhat())
                .trangThai(sanPham.getTrangThai())
                .nguoiTaoId(sanPham.getNguoiTao() != null ? sanPham.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(sanPham.getNguoiCapNhat() != null ? sanPham.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private sanpham mapToEntity(sanphamdto sanPhamDto) {
        return sanpham.builder()
                .maSanPham(sanPhamDto.getMaSanPham())
                .tenSanPham(sanPhamDto.getTenSanPham())
                .moTa(sanPhamDto.getMoTa())
                .gia(sanPhamDto.getGia())
                .hinhAnh(sanPhamDto.getHinhAnh())
                .ngayTao(sanPhamDto.getNgayTao() != null ? sanPhamDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(sanPhamDto.getNgayCapNhat() != null ? sanPhamDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(sanPhamDto.getTrangThai() != null ? sanPhamDto.getTrangThai() : true)
                .build();
    }
}
