package com.noithat.backend.service.impl;

import com.noithat.backend.dto.NhanVienDTO;
import com.noithat.backend.entity.NhanVien;
import com.noithat.backend.entity.TaiKhoan;
import com.noithat.backend.mapper.NhanVienMapper;
import com.noithat.backend.repository.NhanVienRepository;
import com.noithat.backend.repository.TaiKhoanRepository;
import com.noithat.backend.service.NhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienService {

    private final NhanVienRepository nhanVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final NhanVienMapper mapper;

    @Override
    public NhanVienDTO create(NhanVienDTO dto) {
        TaiKhoan tk = taiKhoanRepository.findById(dto.getTaikhoanId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + dto.getTaikhoanId()));

        NhanVien nv = mapper.toEntity(dto, tk);
        return mapper.toDTO(nhanVienRepository.save(nv));
    }

    @Override
    public NhanVienDTO update(Integer id, NhanVienDTO dto) {
        NhanVien nv = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên ID: " + id));

        TaiKhoan tk = taiKhoanRepository.findById(dto.getTaikhoanId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + dto.getTaikhoanId()));

        nv.setHoten(dto.getHoten());
        nv.setEmail(dto.getEmail());
        nv.setSodienthoai(dto.getSodienthoai());
        nv.setChucvu(dto.getChucvu());
        nv.setTaikhoan(tk);

        return mapper.toDTO(nhanVienRepository.save(nv));
    }

    @Override
    public void delete(Integer id) {
        if (!nhanVienRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy nhân viên ID: " + id);
        }
        nhanVienRepository.deleteById(id);
    }

    @Override
    public NhanVienDTO getById(Integer id) {
        return mapper.toDTO(nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên ID: " + id)));
    }

    @Override
    public List<NhanVienDTO> getAll() {
        return nhanVienRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<NhanVienDTO> getAllPaged(Pageable pageable) {
        return nhanVienRepository.findAll(pageable)
                .map(mapper::toDTO);
    }

    @Override
    public List<NhanVienDTO> searchByName(String name) {
        return nhanVienRepository.findByHotenContainingIgnoreCase(name).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NhanVienDTO> search(String keyword) {
        return searchByName(keyword);
    }
}
