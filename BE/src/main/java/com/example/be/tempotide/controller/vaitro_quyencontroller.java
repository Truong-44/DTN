package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.vaitro_quyendto;
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
public class vaitro_quyencontroller {

    private static final Logger logger = LoggerFactory.getLogger(vaitro_quyencontroller.class);

    private final com.example.be.tempotide.service.vaitro_quyenservice vaiTro_QuyenService;

    @Autowired
    public vaitro_quyencontroller(com.example.be.tempotide.service.vaitro_quyenservice vaiTro_QuyenService) {
        this.vaiTro_QuyenService = vaiTro_QuyenService;
    }

    @PostMapping
    public ResponseEntity<vaitro_quyendto> createVaiTro_Quyen(@Valid @RequestBody vaitro_quyendto vaiTro_QuyenDto) {
        logger.info("API call: POST /api/vaitro_quyen");
        vaitro_quyendto createdVaiTro_Quyen = vaiTro_QuyenService.createVaiTro_Quyen(vaiTro_QuyenDto);
        return new ResponseEntity<>(createdVaiTro_Quyen, HttpStatus.CREATED);
    }

    @GetMapping("/{maVaiTro}/{maQuyen}")
    public ResponseEntity<vaitro_quyendto> getVaiTro_QuyenById(@PathVariable Integer maVaiTro, @PathVariable Integer maQuyen) {
        logger.info("API call: GET /api/vaitro_quyen/{}/{}", maVaiTro, maQuyen);
        vaitro_quyendto vaiTro_QuyenDto = vaiTro_QuyenService.getVaiTro_QuyenById(maVaiTro, maQuyen);
        return ResponseEntity.ok(vaiTro_QuyenDto);
    }

    @GetMapping
    public ResponseEntity<List<vaitro_quyendto>> getAllVaiTro_Quyen() {
        logger.info("API call: GET /api/vaitro_quyen");
        List<vaitro_quyendto> vaiTro_QuyenList = vaiTro_QuyenService.getAllVaiTro_Quyen();
        return ResponseEntity.ok(vaiTro_QuyenList);
    }

    @PutMapping("/{maVaiTro}/{maQuyen}")
    public ResponseEntity<vaitro_quyendto> updateVaiTro_Quyen(@PathVariable Integer maVaiTro, @PathVariable Integer maQuyen, @Valid @RequestBody vaitro_quyendto vaiTro_QuyenDto) {
        logger.info("API call: PUT /api/vaitro_quyen/{}/{}", maVaiTro, maQuyen);
        vaitro_quyendto updatedVaiTro_Quyen = vaiTro_QuyenService.updateVaiTro_Quyen(maVaiTro, maQuyen, vaiTro_QuyenDto);
        return ResponseEntity.ok(updatedVaiTro_Quyen);
    }

    @DeleteMapping("/{maVaiTro}/{maQuyen}")
    public ResponseEntity<Void> deleteVaiTro_Quyen(@PathVariable Integer maVaiTro, @PathVariable Integer maQuyen) {
        logger.info("API call: DELETE /api/vaitro_quyen/{}/{}", maVaiTro, maQuyen);
        vaiTro_QuyenService.deleteVaiTro_Quyen(maVaiTro, maQuyen);
        return ResponseEntity.noContent().build();
    }
}