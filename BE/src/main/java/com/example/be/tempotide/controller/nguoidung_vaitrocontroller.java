package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.nguoidung_vaitrodto;
import com.example.be.tempotide.service.NguoiDung_VaiTroService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nguoidung_vaitro")
public class nguoidung_vaitrocontroller {

    private static final Logger logger = LoggerFactory.getLogger(nguoidung_vaitrocontroller.class);

    private final NguoiDung_VaiTroService nguoiDung_VaiTroService;

    @Autowired
    public nguoidung_vaitrocontroller(NguoiDung_VaiTroService nguoiDung_VaiTroService) {
        this.nguoiDung_VaiTroService = nguoiDung_VaiTroService;
    }

    @PostMapping
    public ResponseEntity<nguoidung_vaitrodto> createNguoiDung_VaiTro(@Valid @RequestBody nguoidung_vaitrodto nguoiDung_VaiTroDto) {
        logger.info("API call: POST /api/nguoidung_vaitro");
        nguoidung_vaitrodto createdNguoiDung_VaiTro = nguoiDung_VaiTroService.createNguoiDung_VaiTro(nguoiDung_VaiTroDto);
        return new ResponseEntity<>(createdNguoiDung_VaiTro, HttpStatus.CREATED);
    }

    @GetMapping("/{maNguoiDung}/{maVaiTro}")
    public ResponseEntity<nguoidung_vaitrodto> getNguoiDung_VaiTroById(@PathVariable Integer maNguoiDung, @PathVariable Integer maVaiTro) {
        logger.info("API call: GET /api/nguoidung_vaitro/{}/{}", maNguoiDung, maVaiTro);
        nguoidung_vaitrodto nguoiDung_VaiTroDto = nguoiDung_VaiTroService.getNguoiDung_VaiTroById(maNguoiDung, maVaiTro);
        return ResponseEntity.ok(nguoiDung_VaiTroDto);
    }

    @GetMapping
    public ResponseEntity<List<nguoidung_vaitrodto>> getAllNguoiDung_VaiTro() {
        logger.info("API call: GET /api/nguoidung_vaitro");
        List<nguoidung_vaitrodto> nguoiDung_VaiTroList = nguoiDung_VaiTroService.getAllNguoiDung_VaiTro();
        return ResponseEntity.ok(nguoiDung_VaiTroList);
    }

    @PutMapping("/{maNguoiDung}/{maVaiTro}")
    public ResponseEntity<nguoidung_vaitrodto> updateNguoiDung_VaiTro(@PathVariable Integer maNguoiDung, @PathVariable Integer maVaiTro, @Valid @RequestBody nguoidung_vaitrodto nguoiDung_VaiTroDto) {
        logger.info("API call: PUT /api/nguoidung_vaitro/{}/{}", maNguoiDung, maVaiTro);
        nguoidung_vaitrodto updatedNguoiDung_VaiTro = nguoiDung_VaiTroService.updateNguoiDung_VaiTro(maNguoiDung, maVaiTro, nguoiDung_VaiTroDto);
        return ResponseEntity.ok(updatedNguoiDung_VaiTro);
    }

    @DeleteMapping("/{maNguoiDung}/{maVaiTro}")
    public ResponseEntity<Void> deleteNguoiDung_VaiTro(@PathVariable Integer maNguoiDung, @PathVariable Integer maVaiTro) {
        logger.info("API call: DELETE /api/nguoidung_vaitro/{}/{}", maNguoiDung, maVaiTro);
        nguoiDung_VaiTroService.deleteNguoiDung_VaiTro(maNguoiDung, maVaiTro);
        return ResponseEntity.noContent().build();
    }
}