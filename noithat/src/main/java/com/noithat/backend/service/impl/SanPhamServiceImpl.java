package com.noithat.backend.service.impl;

import com.noithat.backend.dto.SanPhamDTO;
import com.noithat.backend.dto.ChiTietSanPhamDTO;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.DanhMuc;
import com.noithat.backend.entity.SanPham;
import com.noithat.backend.mapper.SanPhamMapper;
import com.noithat.backend.repository.DanhMucRepository;
import com.noithat.backend.repository.SanPhamRepository;
import com.noithat.backend.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {

    private final SanPhamRepository sanPhamRepository;
    private final DanhMucRepository danhMucRepository;
    private final SanPhamMapper mapper;

    @Override
    public SanPhamDTO create(SanPhamDTO dto) {
        DanhMuc dm = danhMucRepository.findById(dto.getDanhmucId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục ID: " + dto.getDanhmucId()));

        SanPham sp = mapper.toEntity(dto, dm);
        return mapper.toDTO(sanPhamRepository.save(sp));
    }

    @Override
    public SanPhamDTO update(Integer id, SanPhamDTO dto) {
        SanPham sp = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));

        DanhMuc dm = danhMucRepository.findById(dto.getDanhmucId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục ID: " + dto.getDanhmucId()));

        sp.setTensanpham(dto.getTensanpham());
        sp.setMota(dto.getMota());
        sp.setDanhmuc(dm);
        sp.setTenloai(dto.getTenloai());
        sp.setGiacu(dto.getGiacu());
        sp.setGiamoi(dto.getGiamoi());
        sp.setTrangthai(dto.getTrangthai());

        return mapper.toDTO(sanPhamRepository.save(sp));
    }

    @Override
    public void delete(Integer id) {
        if (!sanPhamRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm ID: " + id);
        }
        sanPhamRepository.deleteById(id);
    }

    @Override
    public SanPhamDTO getById(Integer id) {
        return sanPhamRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));
    }

    @Override
    public List<SanPhamDTO> getAll() {
        return sanPhamRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SanPhamDTO updateStatus(Integer id, Boolean status) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));
        
        sanPham.setTrangthai(status);
        return mapper.toDTO(sanPhamRepository.save(sanPham));
    }

    @Override
    public List<ChiTietSanPhamDTO> getChiTietSanPhamById(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));
        
        return sanPham.getChiTietSanPhams().stream()
                .map(chiTiet -> {
                    ChiTietSanPhamDTO dto = new ChiTietSanPhamDTO();
                    dto.setId(chiTiet.getId());
                    dto.setSanphamId(chiTiet.getSanpham().getId());
                    dto.setTensanpham(chiTiet.getSanpham().getTensanpham());
                    dto.setTenmau(chiTiet.getTenmau());
                    dto.setMamau(chiTiet.getMamau());
                    dto.setChatlieu(chiTiet.getChatlieu());
                    dto.setKichthuoc(chiTiet.getKichthuoc());
                    dto.setTrongluong(chiTiet.getTrongluong());
                    dto.setSoluong(chiTiet.getSoluong());
                    dto.setHinhchinh(chiTiet.getHinhchinh());
                    dto.setHinhphu(chiTiet.getHinhphu());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SanPhamDTO getFullInfoById(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));
        return mapper.toDTO(sanPham);
    }

    @Override
    public List<String> getAvailableColors(Integer sanPhamId) {
        SanPham sanPham = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + sanPhamId));
        
        return sanPham.getChiTietSanPhams().stream()
                .map(ChiTietSanPham::getTenmau)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAvailableSizes(Integer sanPhamId) {
        SanPham sanPham = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + sanPhamId));
        
        return sanPham.getChiTietSanPhams().stream()
                .map(ChiTietSanPham::getKichthuoc)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Page<SanPhamDTO> searchByNamePaged(String name, Pageable pageable) {
        return sanPhamRepository.findByTensanphamContainingIgnoreCase(name, pageable)
                .map(mapper::toDTO);
    }

    @Override
    public Page<SanPhamDTO> getAllPaged(Pageable pageable) {
        return sanPhamRepository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public List<SanPhamDTO> getByDanhMucId(Integer danhMucId) {
        return sanPhamRepository.findByDanhmuc_Id(danhMucId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPhamDTO> searchByName(String name) {
        return sanPhamRepository.intelligentSearch(name).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
