package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.DanhSachYeuThichDto;
import com.example.be.TempoTide.service.DanhSachYeuThichService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhsachyeuthich")
public class DanhSachYeuThichController {

    private static final Logger logger = LoggerFactory.getLogger(DanhSachYeuThichController.class);

    private final DanhSachYeuThichService danhSachYeuThichService;

    @Autowired
    public DanhSachYeuThichController(DanhSachYeuThichService danhSachYeuThichService) {
        this.danhSachYeuThichService = danhSachYeuThichService;
    }

    @PostMapping
    public ResponseEntity<DanhSachYeuThichDto> createDanhSachYeuThich(@Valid @RequestBody DanhSachYeuThichDto danhSachYeuThichDto) {
        logger.info("API call: POST /api/danhsachyeuthich");
        DanhSachYeuThichDto createdDanhSachYeuThich = danhSachYeuThichService.createDanhSachYeuThich(danhSachYeuThichDto);
        return new ResponseEntity<>(createdDanhSachYeuThich, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhSachYeuThichDto> getDanhSachYeuThichById(@PathVariable Integer id) {
        logger.info("API call: GET /api/danhsachyeuthich/{}", id);
        DanhSachYeuThichDto danhSachYeuThichDto = danhSachYeuThichService.getDanhSachYeuThichById(id);
        return ResponseEntity.ok(danhSachYeuThichDto);
    }

    @GetMapping
    public ResponseEntity<List<DanhSachYeuThichDto>> getAllDanhSachYeuThich() {
        logger.info("API call: GET /api/danhsachyeuthich");
        List<DanhSachYeuThichDto> danhSachYeuThichList = danhSachYeuThichService.getAllDanhSachYeuThich();
        return ResponseEntity.ok(danhSachYeuThichList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DanhSachYeuThichDto> updateDanhSachYeuThich(@PathVariable Integer id, @Valid @RequestBody DanhSachYeuThichDto danhSachYeuThichDto) {
        logger.info("API call: PUT /api/danhsachyeuthich/{}", id);
        DanhSachYeuThichDto updatedDanhSachYeuThich = danhSachYeuThichService.updateDanhSachYeuThich(id, danhSachYeuThichDto);
        return ResponseEntity.ok(updatedDanhSachYeuThich);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhSachYeuThich(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/danhsachyeuthich/{}", id);
        danhSachYeuThichService.deleteDanhSachYeuThich(id);
        return ResponseEntity.noContent().build();
    }
}