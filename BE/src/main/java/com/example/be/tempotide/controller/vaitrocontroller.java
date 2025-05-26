package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.vaitrodto;
import com.example.be.tempotide.service.VaiTroService;
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
public class vaitrocontroller {

    private static final Logger logger = LoggerFactory.getLogger(vaitrocontroller.class);

    private final VaiTroService vaiTroService;

    @Autowired
    public vaitrocontroller(VaiTroService vaiTroService) {
        this.vaiTroService = vaiTroService;
    }

    @PostMapping
    public ResponseEntity<vaitrodto> createVaiTro(@Valid @RequestBody vaitrodto vaiTroDto) {
        logger.info("API call: POST /api/vaitro");
        vaitrodto createdVaiTro = vaiTroService.createVaiTro(vaiTroDto);
        return new ResponseEntity<>(createdVaiTro, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<vaitrodto> getVaiTroById(@PathVariable Integer id) {
        logger.info("API call: GET /api/vaitro/{}", id);
        vaitrodto vaiTroDto = vaiTroService.getVaiTroById(id);
        return ResponseEntity.ok(vaiTroDto);
    }

    @GetMapping
    public ResponseEntity<List<vaitrodto>> getAllVaiTro() {
        logger.info("API call: GET /api/vaitro");
        List<vaitrodto> vaiTroList = vaiTroService.getAllVaiTro();
        return ResponseEntity.ok(vaiTroList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<vaitrodto> updateVaiTro(@PathVariable Integer id, @Valid @RequestBody vaitrodto vaiTroDto) {
        logger.info("API call: PUT /api/vaitro/{}", id);
        vaitrodto updatedVaiTro = vaiTroService.updateVaiTro(id, vaiTroDto);
        return ResponseEntity.ok(updatedVaiTro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaiTro(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/vaitro/{}", id);
        vaiTroService.deleteVaiTro(id);
        return ResponseEntity.noContent().build();
    }
}