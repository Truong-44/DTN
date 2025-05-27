package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.capbackhachhangdto;
import com.example.be.tempotide.entity.capbackhachhang;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.capbackhachhangrepository;
import com.example.be.tempotide.repository.nhanvienrepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class capbackhachhangserviceimpl implements com.example.be.tempotide.service.capbackhachhangservice {

    private static final Logger logger = LoggerFactory.getLogger(capbackhachhangserviceimpl.class);

    private final capbackhachhangrepository capbackhachhangrepository;
    private final nhanvienrepository nhanvienrepository;

    @Autowired
    public capbackhachhangserviceimpl(capbackhachhangrepository capbackhachhangrepository,
                                      nhanvienrepository nhanvienrepository) {
        this.capbackhachhangrepository = capbackhachhangrepository;
        this.nhanvienrepository = nhanvienrepository;
    }

    @Override
    public capbackhachhangdto createcapbackhachhang(capbackhachhangdto capbackhachhangdto) {
        logger.info("Creating new capbackhachhang for tencapbac: {}", capbackhachhangdto.gettencapbac());
        capbackhachhang capbackhachhang = maptoentity(capbackhachhangdto);

        if (capbackhachhangdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(capbackhachhangdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", capbackhachhangdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            capbackhachhang.setnhanvien(nhanvien);
        }

        capbackhachhang.setngaytao(LocalDateTime.now());
        capbackhachhang = capbackhachhangrepository.save(capbackhachhang);
        logger.info("capbackhachhang created with id: {}", capbackhachhang.getmacapbac());
        return maptodto(capbackhachhang);
    }

    @Override
    public capbackhachhangdto getcapbackhachhangbyid(Integer id) {
        logger.info("Fetching capbackhachhang with id: {}", id);
        capbackhachhang capbackhachhang = capbackhachhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("capbackhachhang not found with id: {}", id);
                    return new RuntimeException("capbackhachhang not found with id: " + id);
                });
        return maptodto(capbackhachhang);
    }

    @Override
    public List<capbackhachhangdto> getallcapbackhachhang() {
        logger.info("Fetching all capbackhachhang");
        return capbackhachhangrepository.findAll().stream()
                .map(this::maptodto)
                .collect(Collectors.toList());
    }

    @Override
    public capbackhachhangdto updatecapbackhachhang(Integer id, capbackhachhangdto capbackhachhangdto) {
        logger.info("Updating capbackhachhang with id: {}", id);
        capbackhachhang capbackhachhang = capbackhachhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("capbackhachhang not found with id: {}", id);
                    return new RuntimeException("capbackhachhang not found with id: " + id);
                });

        capbackhachhang.settencapbac(capbackhachhangdto.gettencapbac());
        capbackhachhang.setdiemtoithieu(capbackhachhangdto.getdiemtoithieu());
        capbackhachhang.setgiamgiamacdinh(capbackhachhangdto.getgiamgiamacdinh());
        capbackhachhang.setngaytao(capbackhachhangdto.getngaytao());
        capbackhachhang.settrangthai(capbackhachhangdto.gettrangthai());

        if (capbackhachhangdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(capbackhachhangdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", capbackhachhangdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            capbackhachhang.setnhanvien(nhanvien);
        }

        capbackhachhang = capbackhachhangrepository.save(capbackhachhang);
        logger.info("capbackhachhang updated with id: {}", capbackhachhang.getmacapbac());
        return maptodto(capbackhachhang);
    }

    @Override
    public void deletecapbackhachhang(Integer id) {
        logger.info("Deleting capbackhachhang with id: {}", id);
        capbackhachhang capbackhachhang = capbackhachhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("capbackhachhang not found with id: {}", id);
                    return new RuntimeException("capbackhachhang not found with id: " + id);
                });
        capbackhachhangrepository.delete(capbackhachhang);
        logger.info("capbackhachhang deleted with id: {}", id);
    }

    private capbackhachhangdto maptodto(capbackhachhang capbackhachhang) {
        return capbackhachhangdto.builder()
                .macapbac(capbackhachhang.getmacapbac())
                .tencapbac(capbackhachhang.gettencapbac())
                .diemtoithieu(capbackhachhang.getdiemtoithieu())
                .giamgiamacdinh(capbackhachhang.getgiamgiamacdinh())
                .ngaytao(capbackhachhang.getngaytao())
                .trangthai(capbackhachhang.gettrangthai())
                .nguoitao(capbackhachhang.getnguoitao())
                .build();
    }

    private capbackhachhang maptoentity(capbackhachhangdto capbackhachhangdto) {
        return capbackhachhang.builder()
                .macapbac(capbackhachhangdto.getmacapbac())
                .tencapbac(capbackhachhangdto.gettencapbac())
                .diemtoithieu(capbackhachhangdto.getdiemtoithieu())
                .giamgiamacdinh(capbackhachhangdto.getgiamgiamacdinh())
                .ngaytao(capbackhachhangdto.getngaytao())
                .trangthai(capbackhachhangdto.gettrangthai())
                .nguoitao(capbackhachhangdto.getnguoitao())
                .build();
    }
}