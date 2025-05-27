package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.chitietdonhangdto;
import com.example.be.tempotide.entity.chitietdonhang;
import com.example.be.tempotide.entity.donhang;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.chitietdonhangrepository;
import com.example.be.tempotide.repository.donhangrepository;
import com.example.be.tempotide.repository.sanphamrepository;
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
public class chitietdonhangserviceimpl implements com.example.be.tempotide.service.chitietdonhangservice {

    private static final Logger logger = LoggerFactory.getLogger(chitietdonhangserviceimpl.class);

    private final chitietdonhangrepository chitietdonhangrepository;
    private final donhangrepository donhangrepository;
    private final sanphamrepository sanphamrepository;
    private final nhanvienrepository nhanvienrepository;

    @Autowired
    public chitietdonhangserviceimpl(chitietdonhangrepository chitietdonhangrepository,
                                     donhangrepository donhangrepository,
                                     sanphamrepository sanphamrepository,
                                     nhanvienrepository nhanvienrepository) {
        this.chitietdonhangrepository = chitietdonhangrepository;
        this.donhangrepository = donhangrepository;
        this.sanphamrepository = sanphamrepository;
        this.nhanvienrepository = nhanvienrepository;
    }

    @Override
    public chitietdonhangdto createchitietdonhang(chitietdonhangdto chitietdonhangdto) {
        logger.info("Creating new chitietdonhang for madonhang: {}", chitietdonhangdto.getmadonhang());
        chitietdonhang chitietdonhang = maptoentity(chitietdonhangdto);

        chitietdonhang.setdonhang(donhangrepository.findById(chitietdonhangdto.getmadonhang())
                .orElseThrow(() -> {
                    logger.error("donhang not found with id: {}", chitietdonhangdto.getmadonhang());
                    return new RuntimeException("donhang not found");
                }));
        chitietdonhang.setsanpham(sanphamrepository.findById(chitietdonhangdto.getmachitetsanpham())
                .orElseThrow(() -> {
                    logger.error("sanpham not found with id: {}", chitietdonhangdto.getmachitetsanpham());
                    return new RuntimeException("sanpham not found");
                }));

        if (chitietdonhangdto.getnguoitao() != null) {
            nhanvien nguoitao = nhanvienrepository.findById(chitietdonhangdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nguoitao not found with id: {}", chitietdonhangdto.getnguoitao());
                        return new RuntimeException("nguoitao not found");
                    });
            chitietdonhang.setnguoitao_nhanvien(nguoitao);
        }

        chitietdonhang.setngaytao(LocalDateTime.now());
        chitietdonhang.setngaycapnhat(LocalDateTime.now());
        chitietdonhang = chitietdonhangrepository.save(chitietdonhang);
        logger.info("chitietdonhang created with id: {}", chitietdonhang.getmachitietdonhang());
        return maptodto(chitietdonhang);
    }

    @Override
    public chitietdonhangdto getchitietdonhangbyid(Integer id) {
        logger.info("Fetching chitietdonhang with id: {}", id);
        chitietdonhang chitietdonhang = chitietdonhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("chitietdonhang not found with id: {}", id);
                    return new RuntimeException("chitietdonhang not found with id: " + id);
                });
        return maptodto(chitietdonhang);
    }

    @Override
    public List<chitietdonhangdto> getallchitietdonhang() {
        logger.info("Fetching all chitietdonhang");
        return chitietdonhangrepository.findAll().stream()
                .map(this::maptodto)
                .collect(Collectors.toList());
    }

    @Override
    public chitietdonhangdto updatechitietdonhang(Integer id, chitietdonhangdto chitietdonhangdto) {
        logger.info("Updating chitietdonhang with id: {}", id);
        chitietdonhang chitietdonhang = chitietdonhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("chitietdonhang not found with id: {}", id);
                    return new RuntimeException("chitietdonhang not found with id: " + id);
                });

        chitietdonhang.setdonhang(donhangrepository.findById(chitietdonhangdto.getmadonhang())
                .orElseThrow(() -> {
                    logger.error("donhang not found with id: {}", chitietdonhangdto.getmadonhang());
                    return new RuntimeException("donhang not found");
                }));
        chitietdonhang.setsanpham(sanphamrepository.findById(chitietdonhangdto.getmachitetsanpham())
                .orElseThrow(() -> {
                    logger.error("sanpham not found with id: {}", chitietdonhangdto.getmachitetsanpham());
                    return new RuntimeException("sanpham not found");
                }));
        chitietdonhang.setsoluong(chitietdonhangdto.getsoluong());
        chitietdonhang.setdongia(chitietdonhangdto.getdongia());
        chitietdonhang.setgiamgia(chitietdonhangdto.getgiamgia());
        chitietdonhang.setladattruoc(chitietdonhangdto.getladattruoc());
        chitietdonhang.setngaycapnhat(LocalDateTime.now());

        if (chitietdonhangdto.getnguoicapnhat() != null) {
            nhanvien nguoicapnhat = nhanvienrepository.findById(chitietdonhangdto.getnguoicapnhat())
                    .orElseThrow(() -> {
                        logger.error("nguoicapnhat not found with id: {}", chitietdonhangdto.getnguoicapnhat());
                        return new RuntimeException("nguoicapnhat not found");
                    });
            chitietdonhang.setnguoicapnhat_nhanvien(nguoicapnhat);
        }

        chitietdonhang = chitietdonhangrepository.save(chitietdonhang);
        logger.info("chitietdonhang updated with id: {}", chitietdonhang.getmachitietdonhang());
        return maptodto(chitietdonhang);
    }

    @Override
    public void deletechitietdonhang(Integer id) {
        logger.info("Deleting chitietdonhang with id: {}", id);
        chitietdonhang chitietdonhang = chitietdonhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("chitietdonhang not found with id: {}", id);
                    return new RuntimeException("chitietdonhang not found with id: " + id);
                });
        chitietdonhangrepository.delete(chitietdonhang);
        logger.info("chitietdonhang deleted with id: {}", id);
    }

    private chitietdonhangdto maptodto(chitietdonhang chitietdonhang) {
        return chitietdonhangdto.builder()
                .machitietdonhang(chitietdonhang.getmachitietdonhang())
                .madonhang(chitietdonhang.getdonhang().getmadonhang())
                .machitetsanpham(chitietdonhang.getsanpham().getmasanpham())
                .soluong(chitietdonhang.getsoluong())
                .dongia(chitietdonhang.getdongia())
                .giamgia(chitietdonhang.getgiamgia())
                .ladattruoc(chitietdonhang.getladattruoc())
                .ngaytao(chitietdonhang.getngaytao())
                .ngaycapnhat(chitietdonhang.getngaycapnhat())
                .trangthai(chitietdonhang.gettrangthai())
                .nguoitao(chitietdonhang.getnguoitao())
                .nguoicapnhat(chitietdonhang.getnguoicapnhat())
                .build();
    }

    private chitietdonhang maptoentity(chitietdonhangdto chitietdonhangdto) {
        return chitietdonhang.builder()
                .machitietdonhang(chitietdonhangdto.getmachitietdonhang())
                .soluong(chitietdonhangdto.getsoluong())
                .dongia(chitietdonhangdto.getdongia())
                .giamgia(chitietdonhangdto.getgiamgia())
                .ladattruoc(chitietdonhangdto.getladattruoc())
                .ngaytao(chitietdonhangdto.getngaytao())
                .ngaycapnhat(chitietdonhangdto.getngaycapnhat())
                .trangthai(chitietdonhangdto.gettrangthai())
                .nguoitao(chitietdonhangdto.getnguoitao())
                .nguoicapnhat(chitietdonhangdto.getnguoicapnhat())
                .build();
    }
}