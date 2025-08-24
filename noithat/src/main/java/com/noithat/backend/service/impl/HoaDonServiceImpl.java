package com.noithat.backend.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.noithat.backend.dto.HoaDonDTO;
import com.noithat.backend.entity.DonHang;
import com.noithat.backend.entity.HoaDon;
import com.noithat.backend.mapper.HoaDonMapper;
import com.noithat.backend.repository.DonHangRepository;
import com.noithat.backend.repository.HoaDonRepository;
import com.noithat.backend.service.HoaDonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonService {

    private final HoaDonRepository repository;
    private final DonHangRepository donHangRepository;
    private final HoaDonMapper mapper;

    @Override
    public HoaDonDTO create(HoaDonDTO dto) {
        DonHang dh = donHangRepository.findById(dto.getDonhangId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + dto.getDonhangId()));

        HoaDon hd = mapper.toEntity(dto, dh);
        return mapper.toDTO(repository.save(hd));
    }

    @Override
    public HoaDonDTO getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn ID: " + id));
    }

    @Override
    public List<HoaDonDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<HoaDonDTO> getByNgayTao(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();
        
        return repository.findByNgayxuathoadonBetween(fromDateTime, toDateTime).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HoaDonDTO update(Integer id, HoaDonDTO dto) {
        HoaDon existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn ID: " + id));
        
        existing.setNguoixuathoadon(dto.getNguoixuathoadon());
        existing.setTenkhachhang(dto.getTenkhachhang());
        existing.setDiachinguoinhan(dto.getDiachinguoinhan());
        existing.setTongtien(dto.getTongtien());
        existing.setGhichu(dto.getGhichu());
        
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public Page<HoaDonDTO> getAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public List<HoaDonDTO> getByKhachHangId(Integer khachHangId) {
        return repository.findByDonHang_KhachHang_Id(khachHangId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HoaDonDTO> getByNhanVienId(Integer nhanVienId) {
        // Implement logic to find by nhan vien ID if needed
        // For now, return empty list as the relationship might not exist
        return List.of();
    }
}
