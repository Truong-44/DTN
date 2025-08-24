package com.noithat.backend.service.impl;

import com.noithat.backend.dto.KhachHangDTO;
import com.noithat.backend.entity.KhachHang;
import com.noithat.backend.entity.TaiKhoan;
import com.noithat.backend.mapper.KhachHangMapper;
import com.noithat.backend.repository.KhachHangRepository;
import com.noithat.backend.repository.TaiKhoanRepository;
import com.noithat.backend.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khachHangRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final KhachHangMapper mapper;

    @Override
    public KhachHangDTO create(KhachHangDTO dto) {
        TaiKhoan tk = taiKhoanRepository.findById(dto.getTaikhoanId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + dto.getTaikhoanId()));

        KhachHang entity = mapper.toEntity(dto, tk);
        return mapper.toDTO(khachHangRepository.save(entity));
    }

    @Override
    public KhachHangDTO update(Integer id, KhachHangDTO dto) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng ID: " + id));

        TaiKhoan tk = taiKhoanRepository.findById(dto.getTaikhoanId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + dto.getTaikhoanId()));

        khachHang.setHoten(dto.getHoten());
        khachHang.setDiachi(dto.getDiachi());
        khachHang.setNgaysinh(dto.getNgaysinh());
        khachHang.setGioitinh(dto.getGioitinh());
        khachHang.setTaikhoan(tk);

        return mapper.toDTO(khachHangRepository.save(khachHang));
    }

    @Override
    public void delete(Integer id) {
        if (!khachHangRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy khách hàng ID: " + id);
        }
        khachHangRepository.deleteById(id);
    }

    @Override
    public KhachHangDTO getById(Integer id) {
        return mapper.toDTO(khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng ID: " + id)));
    }

    @Override
    public List<KhachHangDTO> getAll() {
        return khachHangRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<KhachHangDTO> searchByName(String name) {
        return khachHangRepository.findAll().stream()
                .filter(kh -> kh.getHoten().toLowerCase().contains(name.toLowerCase()))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
