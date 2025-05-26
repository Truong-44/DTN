package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.VaiTroDto;
import com.example.be.TempoTide.service.VaiTroService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaitro")
public class VaiTroController {

    private static final Logger logger = LoggerFactory.getLogger(VaiTroController.class);

    private final VaiTroService vaiTroService;

    @Autowired
    public VaiTroController(VaiTroService vaiTroService) {
        this.vaiTroService = vaiTroService;
    }

    @PostMapping
    public ResponseEntity<VaiTroDto> createVaiTro(@Valid @RequestBody VaiTroDto vaiTroDto) {
        logger.info("API call: POST /api/vaitro");
        VaiTroDto createdVaiTro = vaiTroService.createVaiTro(vaiTroDto);
        return new ResponseEntity<>(createdVaiTro, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaiTroDto> getVaiTroById(@PathVariable Integer id) {
        logger.info("API call: GET /api/vaitro/{}", id);
        VaiTroDto vaiTroDto = vaiTroService.getVaiTroById(id);
        return ResponseEntity.ok(vaiTroDto);
    }

    @GetMapping
    public ResponseEntity<List<VaiTroDto>> getAllVaiTro() {
        logger.info("API call: GET /api/vaitro");
        List<VaiTroDto> vaiTroList = vaiTroService.getAllVaiTro();
        return ResponseEntity.ok(vaiTroList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaiTroDto> updateVaiTro(@PathVariable Integer id, @Valid @RequestBody VaiTroDto vaiTroDto) {
        logger.info("API call: PUT /api/vaitro/{}", id);
        VaiTroDto updatedVaiTro = vaiTroService.updateVaiTro(id, vaiTroDto);
        return ResponseEntity.ok(updatedVaiTro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaiTro(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/vaitro/{}", id);
        vaiTroService.deleteVaiTro(id);
        return ResponseEntity.noContent().build();
    }
}