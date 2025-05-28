package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.VaiTroDTO;
import com.example.be.tempotide.entity.NguoiDungVaiTro;
import com.example.be.tempotide.entity.VaiTro;
import com.example.be.tempotide.mapper.VaiTroMapper;
import com.example.be.tempotide.repository.KhachHangRepository;
import com.example.be.tempotide.repository.NguoiDungVaiTroRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.repository.VaiTroRepository;
import com.example.be.tempotide.service.NguoiDungVaiTroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NguoiDungVaiTroServiceImpl implements NguoiDungVaiTroService {
    private final NguoiDungVaiTroRepository nguoiDungVaiTroRepository;
    private final VaiTroRepository vaiTroRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;
    private final VaiTroMapper vaiTroMapper;

    @Override
    public List<VaiTroDTO> getRolesByUserIdAndType(Integer userId, String userType) {
        validateUserType(userType);
        validateUserExists(userId, userType);

        return nguoiDungVaiTroRepository.findByIdManguoidungAndIdLoainguoidung(userId, userType)
                .stream()
                .map(NguoiDungVaiTro::getVaiTro)
                .map(vaiTroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void assignRoleToUser(NguoiDungVaiTroRequestDTO requestDTO) {
        validateUserType(requestDTO.getLoainguoidung());
        validateUserExists(requestDTO.getManguoidung(), requestDTO.getLoainguoidung());

        VaiTro vaiTro = vaiTroRepository.findById(requestDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + requestDTO.getMavaitro()));

        if (nguoiDungVaiTroRepository.existsByIdManguoidungAndIdMavaitroAndIdLoainguoidung(
                requestDTO.getManguoidung(), requestDTO.getMavaitro(), requestDTO.getLoainguoidung())) {
            throw new RuntimeException("Role already assigned to user");
        }

        NguoiDungVaiTro nguoiDungVaiTro = new NguoiDungVaiTro();
        NguoiDungVaiTroId id = new NguoiDungVaiTroId();
        id.setManguoidung(requestDTO.getManguoidung());
        id.setMavaitro(requestDTO.getMavaitro());
        id.setLoainguoidung(requestDTO.getLoainguoidung());
        nguoiDungVaiTro.setId(id);
        nguoiDungVaiTro.setVaiTro(vaiTro);

        nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Integer userId, Integer roleId, String userType) {
        validateUserType(userType);
        validateUserExists(userId, userType);

        NguoiDungVaiTroId id = new NguoiDungVaiTroId();
        id.setManguoidung(userId);
        id.setMavaitro(roleId);
        id.setLoainguoidung(userType);

        NguoiDungVaiTro nguoiDungVaiTro = nguoiDungVaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User-Role mapping not found"));
        nguoiDungVaiTroRepository.delete(nguoiDungVaiTro);
    }

    private void validateUserType(String userType) {
        if (!userType.equals("NhanVien") && !userType.equals("KhachHang")) {
            throw new RuntimeException("Invalid user type: " + userType);
        }
    }

    private void validateUserExists(Integer userId, String userType) {
        if (userType.equals("NhanVien")) {
            nhanVienRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + userId));
        } else {
            khachHangRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + userId));
        }
    }
}