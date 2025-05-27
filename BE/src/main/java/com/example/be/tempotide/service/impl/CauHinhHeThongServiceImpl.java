package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.cauhinhhethongdto;
import com.example.be.tempotide.entity.cauhinhhethong;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.cauhinhhethongrepository;
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
public class cauhinhhethongserviceimpl implements com.example.be.tempotide.service.cauhinhhethongservice {

    private static final Logger logger = LoggerFactory.getLogger(cauhinhhethongserviceimpl.class);

    private final cauhinhhethongrepository cauhinhhethongrepository;
    private final nhanvienrepository nhanvienrepository;

    @Autowired
    public cauhinhhethongserviceimpl(cauhinhhethongrepository cauhinhhethongrepository,
                                     nhanvienrepository nhanvienrepository) {
        this.cauhinhhethongrepository = cauhinhhethongrepository;
        this.nhanvienrepository = nhanvienrepository;
    }

    @Override
    public cauhinhhethongdto createcauhinhhethong(cauhinhhethongdto cauhinhhethongdto) {
        logger.info("Creating new cauhinhhethong for tencauhinh: {}", cauhinhhethongdto.gettencauhinh());
        cauhinhhethong cauhinhhethong = maptoentity(cauhinhhethongdto);

        if (cauhinhhethongdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(cauhinhhethongdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", cauhinhhethongdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            cauhinhhethong.setnhanvien(nhanvien);
        }

        cauhinhhethong.setngaytao(LocalDateTime.now());
        cauhinhhethong = cauhinhhethongrepository.save(cauhinhhethong);
        logger.info("cauhinhhethong created with id: {}", cauhinhhethong.getmacauhinh());
        return maptodto(cauhinhhethong);
    }

    @Override
    public cauhinhhethongdto getcauhinhhethongbyid(Integer id) {
        logger.info("Fetching cauhinhhethong with id: {}", id);
        cauhinhhethong cauhinhhethong = cauhinhhethongrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("cauhinhhethong not found with id: {}", id);
                    return new RuntimeException("cauhinhhethong not found with id: " + id);
                });
        return maptodto(cauhinhhethong);
    }

    @Override
    public List<cauhinhhethongdto> getallcauhinhhethong() {
        logger.info("Fetching all cauhinhhethong");
        return cauhinhhethongrepository.findAll().stream()
                .map(this::maptodto)
                .collect(Collectors.toList());
    }

    @Override
    public cauhinhhethongdto updatecauhinhhethong(Integer id, cauhinhhethongdto cauhinhhethongdto) {
        logger.info("Updating cauhinhhethong with id: {}", id);
        cauhinhhethong cauhinhhethong = cauhinhhethongrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("cauhinhhethong not found with id: {}", id);
                    return new RuntimeException("cauhinhhethong not found with id: " + id);
                });

        cauhinhhethong.settencauhinh(cauhinhhethongdto.gettencauhinh());
        cauhinhhethong.setgiatri(cauhinhhethongdto.getgiatri());
        cauhinhhethong.setmota(cauhinhhethongdto.getmota());
        cauhinhhethong.setngaytao(cauhinhhethongdto.getngaytao());
        cauhinhhethong.settrangthai(cauhinhhethongdto.gettrangthai());

        if (cauhinhhethongdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(cauhinhhethongdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", cauhinhhethongdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            cauhinhhethong.setnhanvien(nhanvien);
        }

        cauhinhhethong = cauhinhhethongrepository.save(cauhinhhethong);
        logger.info("cauhinhhethong updated with id: {}", cauhinhhethong.getmacauhinh());
        return maptodto(cauhinhhethong);
    }

    @Override
    public void deletecauhinhhethong(Integer id) {
        logger.info("Deleting cauhinhhethong with id: {}", id);
        cauhinhhethong cauhinhhethong = cauhinhhethongrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("cauhinhhethong not found with id: {}", id);
                    return new RuntimeException("cauhinhhethong not found with id: " + id);
                });
        cauhinhhethongrepository.delete(cauhinhhethong);
        logger.info("cauhinhhethong deleted with id: {}", id);
    }

    private cauhinhhethongdto maptodto(cauhinhhethong cauhinhhethong) {
        return cauhinhhethongdto.builder()
                .macauhinh(cauhinhhethong.getmacauhinh())
                .tencauhinh(cauhinhhethong.gettencauhinh())
                .giatri(cauhinhhethong.getgiatri())
                .mota(cauhinhhethong.getmota())
                .ngaytao(cauhinhhethong.getngaytao())
                .trangthai(cauhinhhethong.gettrangthai())
                .nguoitao(cauhinhhethong.getnguoitao())
                .build();
    }

    private cauhinhhethong maptoentity(cauhinhhethongdto cauhinhhethongdto) {
        return cauhinhhethong.builder()
                .macauhinh(cauhinhhethongdto.getmacauhinh())
                .tencauhinh(cauhinhhethongdto.gettencauhinh())
                .giatri(cauhinhhethongdto.getgiatri())
                .mota(cauhinhhethongdto.getmota())
                .ngaytao(cauhinhhethongdto.getngaytao())
                .trangthai(cauhinhhethongdto.gettrangthai())
                .nguoitao(cauhinhhethongdto.getnguoitao())
                .build();
    }
}