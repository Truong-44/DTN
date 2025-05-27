package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.baohanhdto;
import com.example.be.tempotide.entity.baohanh;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.baohanhrepository;
import com.example.be.tempotide.repository.sanphamrepository;
import com.example.be.tempotide.repository.nhanvienrepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class baohanhserviceimpl implements com.example.be.tempotide.service.baohanhservice {

    private static final Logger logger = LoggerFactory.getLogger(baohanhserviceimpl.class);

    private final baohanhrepository baohanhrepository;
    private final sanphamrepository sanphamrepository;
    private final nhanvienrepository nhanvienrepository;

    @Autowired
    public baohanhserviceimpl(baohanhrepository baohanhrepository,
                              sanphamrepository sanphamrepository,
                              nhanvienrepository nhanvienrepository) {
        this.baohanhrepository = baohanhrepository;
        this.sanphamrepository = sanphamrepository;
        this.nhanvienrepository = nhanvienrepository;
    }

    @Override
    public baohanhdto createbaohanh(baohanhdto baohanhdto) {
        logger.info("Creating new baohanh for masanpham: {}", baohanhdto.getmasanpham());
        baohanh baohanh = maptoentity(baohanhdto);

        sanpham sanpham = sanphamrepository.findById(baohanhdto.getmasanpham())
                .orElseThrow(() -> {
                    logger.error("sanpham not found with id: {}", baohanhdto.getmasanpham());
                    return new RuntimeException("sanpham not found");
                });
        baohanh.setsanpham(sanpham);

        if (baohanhdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(baohanhdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", baohanhdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            baohanh.setnhanvien(nhanvien);
        }

        baohanh = baohanhrepository.save(baohanh);
        logger.info("baohanh created with id: {}", baohanh.getmabaohanh());
        return maptodto(baohanh);
    }

    @Override
    public baohanhdto getbaohanhbyid(Integer id) {
        logger.info("Fetching baohanh with id: {}", id);
        baohanh baohanh = baohanhrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("baohanh not found with id: {}", id);
                    return new RuntimeException("baohanh not found with id: " + id);
                });
        return maptodto(baohanh);
    }

    @Override
    public List<baohanhdto> getallbaohanh() {
        logger.info("Fetching all baohanh");
        return baohanhrepository.findAll().stream()
                .map(this::maptodto)
                .collect(Collectors.toList());
    }

    @Override
    public baohanhdto updatebaohanh(Integer id, baohanhdto baohanhdto) {
        logger.info("Updating baohanh with id: {}", id);
        baohanh baohanh = baohanhrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("baohanh not found with id: {}", id);
                    return new RuntimeException("baohanh not found with id: " + id);
                });

        baohanh.setmasanpham(baohanhdto.getmasanpham());
        baohanh.setthoigianbaohanh(baohanhdto.getthoigianbaohanh());
        baohanh.setdieukienbaohanh(baohanhdto.getdieukienbaohanh());
        baohanh.setngaytao(baohanhdto.getngaytao());
        baohanh.settrangthai(baohanhdto.gettrangthai());
        baohanh.setnguoitao(baohanhdto.getnguoitao());

        sanpham sanpham = sanphamrepository.findById(baohanhdto.getmasanpham())
                .orElseThrow(() -> {
                    logger.error("sanpham not found with id: {}", baohanhdto.getmasanpham());
                    return new RuntimeException("sanpham not found");
                });
        baohanh.setsanpham(sanpham);

        if (baohanhdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(baohanhdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", baohanhdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            baohanh.setnhanvien(nhanvien);
        } else {
            baohanh.setnhanvien(null);
        }

        baohanh = baohanhrepository.save(baohanh);
        logger.info("baohanh updated with id: {}", baohanh.getmabaohanh());
        return maptodto(baohanh);
    }

    @Override
    public void deletebaohanh(Integer id) {
        logger.info("Deleting baohanh with id: {}", id);
        baohanh baohanh = baohanhrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("baohanh not found with id: {}", id);
                    return new RuntimeException("baohanh not found with id: " + id);
                });
        baohanhrepository.delete(baohanh);
        logger.info("baohanh deleted with id: {}", id);
    }

    private baohanhdto maptodto(baohanh baohanh) {
        return baohanhdto.builder()
                .mabaohanh(baohanh.getmabaohanh())
                .masanpham(baohanh.getmasanpham())
                .thoigianbaohanh(baohanh.getthoigianbaohanh())
                .dieukienbaohanh(baohanh.getdieukienbaohanh())
                .ngaytao(baohanh.getngaytao())
                .trangthai(baohanh.gettrangthai())
                .nguoitao(baohanh.getnguoitao())
                .build();
    }

    private baohanh maptoentity(baohanhdto baohanhdto) {
        return baohanh.builder()
                .mabaohanh(baohanhdto.getmabaohanh())
                .masanpham(baohanhdto.getmasanpham())
                .thoigianbaohanh(baohanhdto.getthoigianbaohanh())
                .dieukienbaohanh(baohanhdto.getdieukienbaohanh())
                .ngaytao(baohanhdto.getngaytao())
                .trangthai(baohanhdto.gettrangthai())
                .nguoitao(baohanhdto.getnguoitao())
                .build();
    }
}