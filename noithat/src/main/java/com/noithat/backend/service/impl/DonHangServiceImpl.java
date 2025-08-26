package com.noithat.backend.service.impl;

import com.noithat.backend.dto.DonHangDTO;
import com.noithat.backend.entity.DonHang;
import com.noithat.backend.entity.KhachHang;
import com.noithat.backend.mapper.DonHangMapper;
import com.noithat.backend.repository.DonHangRepository;
import com.noithat.backend.repository.KhachHangRepository;
import com.noithat.backend.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonHangServiceImpl implements DonHangService {

    private final DonHangRepository repository;
    private final KhachHangRepository khachHangRepository;
    private final DonHangMapper mapper;

    @Override
    public DonHangDTO create(DonHangDTO dto) {
        KhachHang kh = khachHangRepository.findById(dto.getKhachHangId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng ID: " + dto.getKhachHangId()));

        DonHang entity = mapper.toEntity(dto, kh);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public DonHangDTO getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + id));
    }

    @Override
    public List<DonHangDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonHangDTO> getByKhachHangId(Integer khachHangId) {
        return repository.findByKhachHang_Id(khachHangId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public DonHangDTO cancel(Integer id, String reason) {
        DonHang donHang = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + id));
        
        donHang.setTrangthaidonhang("Đã hủy");
        donHang.setGhichu(reason);
        
        return mapper.toDTO(repository.save(donHang));
    }

    @Override
    public DonHangDTO update(Integer id, DonHangDTO dto) {
        DonHang existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + id));
        
        existing.setDiachinhan(dto.getDiachinhan());
        existing.setTrangthaidonhang(dto.getTrangthaidonhang());
        existing.setTongtien(dto.getTongtien());
        existing.setPhuongthucthanhtoan(dto.getPhuongthucthanhtoan());
        existing.setTrangthaithanhtoan(dto.getTrangthaithanhtoan());
        existing.setGhichu(dto.getGhichu());
        
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public Page<DonHangDTO> getAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public List<DonHangDTO> getByStatus(String status) {
        return repository.findByTrangthaidonhang(status).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DonHangDTO updateStatus(Integer id, String status) {
        DonHang donHang = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + id));
        
        donHang.setTrangthaidonhang(status);
        return mapper.toDTO(repository.save(donHang));
    }
}
