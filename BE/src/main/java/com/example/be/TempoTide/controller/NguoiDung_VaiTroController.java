package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.NguoiDung_VaiTroDto;
import com.example.be.TempoTide.service.NguoiDung_VaiTroService;
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
public class NguoiDung_VaiTroController {

    private static final Logger logger = LoggerFactory.getLogger(NguoiDung_VaiTroController.class);

    private final NguoiDung_VaiTroService nguoiDung_VaiTroService;

    @Autowired
    public NguoiDung_VaiTroController(NguoiDung_VaiTroService nguoiDung_VaiTroService) {
        this.nguoiDung_VaiTroService = nguoiDung_VaiTroService;
    }

    @PostMapping
    public ResponseEntity<NguoiDung_VaiTroDto> createNguoiDung_VaiTro(@Valid @RequestBody NguoiDung_VaiTroDto nguoiDung_VaiTroDto) {
        logger.info("API call: POST /api/nguoidung_vaitro");
        NguoiDung_VaiTroDto createdNguoiDung_VaiTro = nguoiDung_VaiTroService.createNguoiDung_VaiTro(nguoiDung_VaiTroDto);
        return new ResponseEntity<>(createdNguoiDung_VaiTro, HttpStatus.CREATED);
    }

    @GetMapping("/{maNguoiDung}/{maVaiTro}")
    public ResponseEntity<NguoiDung_VaiTroDto> getNguoiDung_VaiTroById(@PathVariable Integer maNguoiDung, @PathVariable Integer maVaiTro) {
        logger.info("API call: GET /api/nguoidung_vaitro/{}/{}", maNguoiDung, maVaiTro);
        NguoiDung_VaiTroDto nguoiDung_VaiTroDto = nguoiDung_VaiTroService.getNguoiDung_VaiTroById(maNguoiDung, maVaiTro);
        return ResponseEntity.ok(nguoiDung_VaiTroDto);
    }

    @GetMapping
    public ResponseEntity<List<NguoiDung_VaiTroDto>> getAllNguoiDung_VaiTro() {
        logger.info("API call: GET /api/nguoidung_vaitro");
        List<NguoiDung_VaiTroDto> nguoiDung_VaiTroList = nguoiDung_VaiTroService.getAllNguoiDung_VaiTro();
        return ResponseEntity.ok(nguoiDung_VaiTroList);
    }

    @PutMapping("/{maNguoiDung}/{maVaiTro}")
    public ResponseEntity<NguoiDung_VaiTroDto> updateNguoiDung_VaiTro(@PathVariable Integer maNguoiDung, @PathVariable Integer maVaiTro, @Valid @RequestBody NguoiDung_VaiTroDto nguoiDung_VaiTroDto) {
        logger.info("API call: PUT /api/nguoidung_vaitro/{}/{}", maNguoiDung, maVaiTro);
        NguoiDung_VaiTroDto updatedNguoiDung_VaiTro = nguoiDung_VaiTroService.updateNguoiDung_VaiTro(maNguoiDung, maVaiTro, nguoiDung_VaiTroDto);
        return ResponseEntity.ok(updatedNguoiDung_VaiTro);
    }

    @DeleteMapping("/{maNguoiDung}/{maVaiTro}")
    public ResponseEntity<Void> deleteNguoiDung_VaiTro(@PathVariable Integer maNguoiDung, @PathVariable Integer maVaiTro) {
        logger.info("API call: DELETE /api/nguoidung_vaitro/{}/{}", maNguoiDung, maVaiTro);
        nguoiDung_VaiTroService.deleteNguoiDung_VaiTro(maNguoiDung, maVaiTro);
        return ResponseEntity.noContent().build();
    }
}