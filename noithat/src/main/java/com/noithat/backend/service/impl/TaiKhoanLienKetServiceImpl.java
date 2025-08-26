package com.noithat.backend.service.impl;

import com.noithat.backend.dto.TaiKhoanLienKetDTO;
import com.noithat.backend.entity.TaiKhoan;
import com.noithat.backend.entity.TaiKhoanLienKet;
import com.noithat.backend.mapper.TaiKhoanLienKetMapper;
import com.noithat.backend.repository.TaiKhoanLienKetRepository;
import com.noithat.backend.repository.TaiKhoanRepository;
import com.noithat.backend.service.TaiKhoanLienKetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaiKhoanLienKetServiceImpl implements TaiKhoanLienKetService {

    private final TaiKhoanLienKetRepository repository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    public List<TaiKhoanLienKetDTO> findAll() {
        return repository.findAll().stream()
                .map(TaiKhoanLienKetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaiKhoanLienKetDTO findById(Integer id) {
        return repository.findById(id)
                .map(TaiKhoanLienKetMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản liên kết với id = " + id));
    }

    @Override
    public TaiKhoanLienKetDTO getById(Integer id) {
        return findById(id);
    }

    @Override
    public TaiKhoanLienKetDTO save(TaiKhoanLienKetDTO dto) {
        TaiKhoan taikhoan = taiKhoanRepository.findById(dto.getTaikhoanid())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản với id = " + dto.getTaikhoanid()));
        TaiKhoanLienKet entity = TaiKhoanLienKetMapper.toEntity(dto, taikhoan);
        return TaiKhoanLienKetMapper.toDTO(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void unlinkByProvider(Integer taiKhoanId, String provider) {
        repository.deleteByTaikhoan_IdAndLoaidangnhap(taiKhoanId, provider);
    }

    @Override
    public TaiKhoanLienKetDTO update(Integer id, TaiKhoanLienKetDTO dto) {
        TaiKhoanLienKet existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản liên kết với id = " + id));
        
        TaiKhoan taikhoan = taiKhoanRepository.findById(dto.getTaikhoanid())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản với id = " + dto.getTaikhoanid()));

        existing.setLoaidangnhap(dto.getLoaidangnhap());
        existing.setGiatridangnhap(dto.getGiatridangnhap());
        existing.setTaikhoan(taikhoan);

        return TaiKhoanLienKetMapper.toDTO(repository.save(existing));
    }

    @Override
    public List<TaiKhoanLienKetDTO> getByProvider(String provider) {
        return repository.findByLoaidangnhap(provider).stream()
                .map(TaiKhoanLienKetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaiKhoanLienKetDTO create(TaiKhoanLienKetDTO dto) {
        TaiKhoan taikhoan = taiKhoanRepository.findById(dto.getTaikhoanid())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản với id = " + dto.getTaikhoanid()));
        TaiKhoanLienKet entity = TaiKhoanLienKetMapper.toEntity(dto, taikhoan);
        return TaiKhoanLienKetMapper.toDTO(repository.save(entity));
    }

    @Override
    public List<TaiKhoanLienKetDTO> getByTaiKhoanId(Integer taiKhoanId) {
        return repository.findByTaikhoan_Id(taiKhoanId).stream()
                .map(TaiKhoanLienKetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaiKhoanLienKetDTO> getAll() {
        return repository.findAll().stream()
                .map(TaiKhoanLienKetMapper::toDTO)
                .collect(Collectors.toList());
    }
}
