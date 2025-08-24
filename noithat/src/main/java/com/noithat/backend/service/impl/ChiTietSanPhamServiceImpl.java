package com.noithat.backend.service.impl;

import com.noithat.backend.dto.ChiTietSanPhamDTO;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.SanPham;
import com.noithat.backend.mapper.ChiTietSanPhamMapper;
import com.noithat.backend.repository.ChiTietSanPhamRepository;
import com.noithat.backend.repository.SanPhamRepository;
import com.noithat.backend.service.ChiTietSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ChiTietSanPhamMapper mapper;

    @Override
    public ChiTietSanPhamDTO create(ChiTietSanPhamDTO dto) {
        SanPham sp = sanPhamRepository.findById(dto.getSanphamId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + dto.getSanphamId()));

        ChiTietSanPham entity = mapper.toEntity(dto, sp);
        return mapper.toDTO(chiTietSanPhamRepository.save(entity));
    }

    @Override
    public ChiTietSanPhamDTO update(Integer id, ChiTietSanPhamDTO dto) {
        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết sản phẩm ID: " + id));

        SanPham sp = sanPhamRepository.findById(dto.getSanphamId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + dto.getSanphamId()));

        ctsp.setSanpham(sp);
        ctsp.setTenmau(dto.getTenmau());
        ctsp.setMamau(dto.getMamau());
        ctsp.setChatlieu(dto.getChatlieu());
        ctsp.setKichthuoc(dto.getKichthuoc());
        ctsp.setTrongluong(dto.getTrongluong());
        ctsp.setSoluong(dto.getSoluong());
        ctsp.setHinhchinh(dto.getHinhchinh());
        ctsp.setHinhphu(dto.getHinhphu());

        return mapper.toDTO(chiTietSanPhamRepository.save(ctsp));
    }

    @Override
    public void delete(Integer id) {
        if (!chiTietSanPhamRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy chi tiết sản phẩm ID: " + id);
        }
        chiTietSanPhamRepository.deleteById(id);
    }

    @Override
    public ChiTietSanPhamDTO getById(Integer id) {
        return chiTietSanPhamRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết sản phẩm ID: " + id));
    }

    @Override
    public List<ChiTietSanPhamDTO> getAll() {
        return chiTietSanPhamRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietSanPhamDTO> getAvailableStock() {
        return chiTietSanPhamRepository.findBySoluongGreaterThan(0).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietSanPhamDTO> getBySanPhamId(Integer sanPhamId) {
        return chiTietSanPhamRepository.findBySanpham_Id(sanPhamId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietSanPhamDTO> getBySanPhamIdAndMau(Integer sanPhamId, String mau) {
        return chiTietSanPhamRepository.findBySanpham_IdAndTenmauContainingIgnoreCase(sanPhamId, mau).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietSanPhamDTO> getBySanPhamIdAndKichThuoc(Integer sanPhamId, String kichThuoc) {
        return chiTietSanPhamRepository.findBySanpham_IdAndKichthuoc(sanPhamId, kichThuoc).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietSanPhamDTO> getBySanPhamIdWithFilter(Integer sanPhamId, String mau, String kichThuoc, String chatLieu) {
        return chiTietSanPhamRepository.findBySanpham_Id(sanPhamId).stream()
                .filter(ctsp -> {
                    boolean match = true;
                    if (mau != null && !mau.isEmpty()) {
                        match = match && ctsp.getTenmau().toLowerCase().contains(mau.toLowerCase());
                    }
                    if (kichThuoc != null && !kichThuoc.isEmpty()) {
                        match = match && ctsp.getKichthuoc().toLowerCase().contains(kichThuoc.toLowerCase());
                    }
                    if (chatLieu != null && !chatLieu.isEmpty()) {
                        match = match && ctsp.getChatlieu().toLowerCase().contains(chatLieu.toLowerCase());
                    }
                    return match;
                })
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietSanPhamDTO> getAllPaged(Pageable pageable) {
        return chiTietSanPhamRepository.findAll(pageable).map(mapper::toDTO);
    }
}
