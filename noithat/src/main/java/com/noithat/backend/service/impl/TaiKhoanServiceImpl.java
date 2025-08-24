package com.noithat.backend.service.impl;

import com.noithat.backend.dto.KhachHangDTO;
import com.noithat.backend.dto.TaiKhoanDTO;
import com.noithat.backend.entity.Quyen;
import com.noithat.backend.entity.TaiKhoan;
import com.noithat.backend.mapper.TaiKhoanMapper;
import com.noithat.backend.repository.QuyenRepository;
import com.noithat.backend.repository.TaiKhoanRepository;
import com.noithat.backend.service.KhachHangService;
import com.noithat.backend.service.TaiKhoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaiKhoanServiceImpl implements TaiKhoanService {

    private final TaiKhoanRepository taiKhoanRepository;
    private final QuyenRepository quyenRepository;
    private final TaiKhoanMapper mapper;
    private final KhachHangService khachHangService;

    @Override
    public TaiKhoanDTO create(TaiKhoanDTO dto) {
        Quyen quyen = quyenRepository.findById(dto.getQuyenId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền ID: " + dto.getQuyenId()));

        TaiKhoan entity = mapper.toEntity(dto);
        entity.setQuyen(quyen);
        return mapper.toDTO(taiKhoanRepository.save(entity));
    }

    @Override
    public TaiKhoanDTO update(Integer id, TaiKhoanDTO dto) {
        TaiKhoan taiKhoan = taiKhoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + id));

        Quyen quyen = quyenRepository.findById(dto.getQuyenId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền ID: " + dto.getQuyenId()));

        taiKhoan.setTendangnhap(dto.getTendangnhap());
        taiKhoan.setMatkhau(dto.getMatkhau());
        taiKhoan.setEmail(dto.getEmail());
        taiKhoan.setSodienthoai(dto.getSodienthoai());
        taiKhoan.setTrangthai(dto.getTrangthai());
        taiKhoan.setLoaidangnhap(dto.getLoaidangnhap());
        taiKhoan.setQuyen(quyen);

        return mapper.toDTO(taiKhoanRepository.save(taiKhoan));
    }

    @Override
    public void delete(Integer id) {
        if (!taiKhoanRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy tài khoản ID: " + id);
        }
        taiKhoanRepository.deleteById(id);
    }

    @Override
    public TaiKhoanDTO getById(Integer id) {
        TaiKhoan tk = taiKhoanRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + id));
        return mapper.toDTO(tk);
    }

    @Override
    public List<TaiKhoanDTO> getAll() {
        return taiKhoanRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaiKhoanDTO getByUsername(String username) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByTendangnhapWithDetails(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với username: " + username));
        return mapper.toDTO(taiKhoan);
    }

    @Override
    public Page<TaiKhoanDTO> getAllPaged(Pageable pageable) {
        return taiKhoanRepository.findAll(pageable)
                .map(mapper::toDTO);
    }

    @Override
    public TaiKhoanDTO register(TaiKhoanDTO dto) {
        // Kiểm tra username đã tồn tại
        if (taiKhoanRepository.existsByTendangnhap(dto.getTendangnhap())) {
            throw new RuntimeException("Username đã tồn tại: " + dto.getTendangnhap());
        }
        
        // Kiểm tra email đã tồn tại  
        if (taiKhoanRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email đã tồn tại: " + dto.getEmail());
        }
        
        // Tạo tài khoản
        TaiKhoanDTO taiKhoanResult = create(dto);
        
        // Nếu là khách hàng (quyenId = 3), tự động tạo record KhachHang
        if (dto.getQuyenId() == 3) {
            try {
                KhachHangDTO khachHang = KhachHangDTO.builder()
                        .taikhoanId(taiKhoanResult.getId())
                        .hoten(dto.getTendangnhap()) // Sử dụng username làm tên tạm thời
                        .build();
                khachHangService.create(khachHang);
            } catch (Exception e) {
                // Log error nhưng không fail toàn bộ quá trình đăng ký
                System.err.println("Lỗi khi tạo KhachHang: " + e.getMessage());
            }
        }
        
        return taiKhoanResult;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByTendangnhapWithDetails(username)
                .orElseThrow(() -> new RuntimeException("Sai username hoặc mật khẩu"));
        
        // Kiểm tra mật khẩu (trong thực tế nên hash password)
        if (!taiKhoan.getMatkhau().equals(password)) {
            throw new RuntimeException("Sai username hoặc mật khẩu");
        }
        
        // Kiểm tra trạng thái tài khoản
        if (!taiKhoan.getTrangthai()) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("user", mapper.toDTO(taiKhoan));
        result.put("token", "jwt-token-here"); // Trong thực tế sẽ generate JWT token
        
        return result;
    }

    @Override
    public void logout(String token) {
        // Trong thực tế sẽ invalidate JWT token
        // Hiện tại chỉ log
        System.out.println("Logout token: " + token);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByTendangnhap(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        
        if (!taiKhoan.getMatkhau().equals(oldPassword)) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
        
        taiKhoan.setMatkhau(newPassword);
        taiKhoanRepository.save(taiKhoan);
    }

    @Override
    public void resetPassword(String email) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với email: " + email));
        
        // Trong thực tế sẽ generate new password và gửi email
        String newPassword = "temp123";
        taiKhoan.setMatkhau(newPassword);
        taiKhoanRepository.save(taiKhoan);
    }

    @Override
    public TaiKhoanDTO updateStatus(Integer id, Boolean active) {
        TaiKhoan taiKhoan = taiKhoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + id));
        
        taiKhoan.setTrangthai(active);
        return mapper.toDTO(taiKhoanRepository.save(taiKhoan));
    }

    @Override
    public boolean validateToken(String token) {
        // Trong thực tế sẽ validate JWT token
        return token != null && !token.trim().isEmpty();
    }
}
