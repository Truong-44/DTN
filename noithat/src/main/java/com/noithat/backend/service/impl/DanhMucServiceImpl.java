package com.noithat.backend.service.impl;

import com.noithat.backend.dto.DanhMucDTO;
import com.noithat.backend.dto.SanPhamDTO;
import com.noithat.backend.entity.DanhMuc;
import com.noithat.backend.mapper.DanhMucMapper;
import com.noithat.backend.repository.DanhMucRepository;
import com.noithat.backend.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanhMucServiceImpl implements DanhMucService {

    private final DanhMucRepository repository;
    private final DanhMucMapper mapper;

    @Override
    public DanhMucDTO create(DanhMucDTO dto) {
        DanhMuc parent = dto.getDanhmucChaId() != null
                ? repository.findById(dto.getDanhmucChaId()).orElse(null)
                : null;

        DanhMuc entity = mapper.toEntity(dto, parent);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public DanhMucDTO update(Integer id, DanhMucDTO dto) {
        DanhMuc existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục ID: " + id));

        DanhMuc parent = dto.getDanhmucChaId() != null
                ? repository.findById(dto.getDanhmucChaId()).orElse(null)
                : null;

        existing.setTendanhmuc(dto.getTendanhmuc());
        existing.setMota(dto.getMota());
        existing.setDanhmucCha(parent);

        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy danh mục ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public DanhMucDTO getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục ID: " + id));
    }

    @Override
    public List<DanhMucDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPhamDTO> getSanPhamByDanhMucId(Integer danhMucId) {
        DanhMuc danhMuc = repository.findById(danhMucId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục ID: " + danhMucId));
        
        return danhMuc.getSanPhams().stream()
                .map(sanPham -> {
                    SanPhamDTO dto = new SanPhamDTO();
                    dto.setId(sanPham.getId());
                    dto.setTensanpham(sanPham.getTensanpham());
                    dto.setMota(sanPham.getMota());
                    dto.setTrangthai(sanPham.getTrangthai());
                    dto.setDanhmucId(sanPham.getDanhmuc().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DanhMucDTO> getRootCategories() {
        return repository.findByDanhmucChaIsNull().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DanhMucDTO> getSubCategories(Integer parentId) {
        DanhMuc parent = repository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục cha ID: " + parentId));
        
        return parent.getDanhMucCon().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DanhMucDTO> search(String keyword) {
        return repository.findByTendanhmucContainingIgnoreCase(keyword).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DanhMucDTO> getAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }
}
