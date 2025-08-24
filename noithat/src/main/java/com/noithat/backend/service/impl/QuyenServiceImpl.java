package com.noithat.backend.service.impl;

import com.noithat.backend.dto.QuyenDTO;
import com.noithat.backend.entity.Quyen;
import com.noithat.backend.mapper.QuyenMapper;
import com.noithat.backend.repository.QuyenRepository;
import com.noithat.backend.service.QuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuyenServiceImpl implements QuyenService {

    private final QuyenRepository quyenRepository;
    private final QuyenMapper quyenMapper;

    @Override
    public QuyenDTO create(QuyenDTO dto) {
        Quyen saved = quyenRepository.save(quyenMapper.toEntity(dto));
        return quyenMapper.toDTO(saved);
    }

    @Override
    public QuyenDTO update(Integer id, QuyenDTO dto) {
        Quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền ID: " + id));

        quyen.setTen(dto.getTen());
        quyen.setMota(dto.getMota());

        return quyenMapper.toDTO(quyenRepository.save(quyen));
    }

    @Override
    public void delete(Integer id) {
        if (!quyenRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy quyền ID: " + id);
        }
        quyenRepository.deleteById(id);
    }

    @Override
    public QuyenDTO getById(Integer id) {
        return quyenMapper.toDTO(
                quyenRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền ID: " + id))
        );
    }

    @Override
    public List<QuyenDTO> getAll() {
        return quyenRepository.findAll()
                .stream()
                .map(quyenMapper::toDTO)
                .collect(Collectors.toList());
    }
}
