package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.VaiTro_QuyenDto;
import com.example.be.TempoTide.service.VaiTro_QuyenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaitro_quyen")
public class VaiTro_QuyenController {

    private static final Logger logger = LoggerFactory.getLogger(VaiTro_QuyenController.class);

    private final VaiTro_QuyenService vaiTro_QuyenService;

    @Autowired
    public VaiTro_QuyenController(VaiTro_QuyenService vaiTro_QuyenService) {
        this.vaiTro_QuyenService = vaiTro_QuyenService;
    }

    @PostMapping
    public ResponseEntity<VaiTro_QuyenDto> createVaiTro_Quyen(@Valid @RequestBody VaiTro_QuyenDto vaiTro_QuyenDto) {
        logger.info("API call: POST /api/vaitro_quyen");
        VaiTro_QuyenDto createdVaiTro_Quyen = vaiTro_QuyenService.createVaiTro_Quyen(vaiTro_QuyenDto);
        return new ResponseEntity<>(createdVaiTro_Quyen, HttpStatus.CREATED);
    }

    @GetMapping("/{maVaiTro}/{maQuyen}")
    public ResponseEntity<VaiTro_QuyenDto> getVaiTro_QuyenById(@PathVariable Integer maVaiTro, @PathVariable Integer maQuyen) {
        logger.info("API call: GET /api/vaitro_quyen/{}/{}", maVaiTro, maQuyen);
        VaiTro_QuyenDto vaiTro_QuyenDto = vaiTro_QuyenService.getVaiTro_QuyenById(maVaiTro, maQuyen);
        return ResponseEntity.ok(vaiTro_QuyenDto);
    }

    @GetMapping
    public ResponseEntity<List<VaiTro_QuyenDto>> getAllVaiTro_Quyen() {
        logger.info("API call: GET /api/vaitro_quyen");
        List<VaiTro_QuyenDto> vaiTro_QuyenList = vaiTro_QuyenService.getAllVaiTro_Quyen();
        return ResponseEntity.ok(vaiTro_QuyenList);
    }

    @PutMapping("/{maVaiTro}/{maQuyen}")
    public ResponseEntity<VaiTro_QuyenDto> updateVaiTro_Quyen(@PathVariable Integer maVaiTro, @PathVariable Integer maQuyen, @Valid @RequestBody VaiTro_QuyenDto vaiTro_QuyenDto) {
        logger.info("API call: PUT /api/vaitro_quyen/{}/{}", maVaiTro, maQuyen);
        VaiTro_QuyenDto updatedVaiTro_Quyen = vaiTro_QuyenService.updateVaiTro_Quyen(maVaiTro, maQuyen, vaiTro_QuyenDto);
        return ResponseEntity.ok(updatedVaiTro_Quyen);
    }

    @DeleteMapping("/{maVaiTro}/{maQuyen}")
    public ResponseEntity<Void> deleteVaiTro_Quyen(@PathVariable Integer maVaiTro, @PathVariable Integer maQuyen) {
        logger.info("API call: DELETE /api/vaitro_quyen/{}/{}", maVaiTro, maQuyen);
        vaiTro_QuyenService.deleteVaiTro_Quyen(maVaiTro, maQuyen);
        return ResponseEntity.noContent().build();
    }
}