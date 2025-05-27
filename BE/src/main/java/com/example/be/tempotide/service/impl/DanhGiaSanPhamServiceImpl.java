package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.danhgiasanphamdto;
import com.example.be.tempotide.entity.danhgiasanpham;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.entity.khachhang;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.danhgiasanphamrepository;
import com.example.be.tempotide.repository.sanphamrepository;
import com.example.be.tempotide.repository.khachhangrepository;
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
public class danhgiasanphamserviceimpl implements com.example.be.tempotide.service.danhgiasanphamservice {

    private static final Logger logger = LoggerFactory.getLogger(danhgiasanphamserviceimpl.class);

    private final danhgiasanphamrepository danhgiasanphamrepository;
    private final sanphamrepository sanphamrepository;
    private final khachhangrepository khachhangrepository;
    private final nhanvienrepository nhanvienrepository;

    @Autowired
    public danhgiasanphamserviceimpl(danhgiasanphamrepository danhgiasanphamrepository,
                                     sanphamrepository sanphamrepository,
                                     khachhangrepository khachhangrepository,
                                     nhanvienrepository nhanvienrepository) {
        this.danhgiasanphamrepository = danhgiasanphamrepository;
        this.sanphamrepository = sanphamrepository;
        this.khachhangrepository = khachhangrepository;
        this.nhanvienrepository = nhanvienrepository;
    }

    @Override
    public danhgiasanphamdto createdanhgiasanpham(danhgiasanphamdto danhgiasanphamdto) {
        logger.info("Creating new danhgiasanpham for masanpham: {}", danhgiasanphamdto.getmasanpham());
        danhgiasanpham danhgiasanpham = maptoentity(danhgiasanphamdto);

        danhgiasanpham.setsanpham(sanphamrepository.findById(danhgiasanphamdto.getmasanpham())
                .orElseThrow(() -> {
                    logger.error("sanpham not found with id: {}", danhgiasanphamdto.getmasanpham());
                    return new RuntimeException("sanpham not found");
                }));
        danhgiasanpham.setkhachhang(khachhangrepository.findById(danhgiasanphamdto.getmakhachhang())
                .orElseThrow(() -> {
                    logger.error("khachhang not found with id: {}", danhgiasanphamdto.getmakhachhang());
                    return new RuntimeException("khachhang not found");
                }));

        if (danhgiasanphamdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(danhgiasanphamdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", danhgiasanphamdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            danhgiasanpham.setnguoitao_nhanvien(nhanvien);
        }

        danhgiasanpham.setngaydanhgia(LocalDateTime.now());
        danhgiasanpham = danhgiasanphamrepository.save(danhgiasanpham);
        logger.info("danhgiasanpham created with id: {}", danhgiasanpham.getmadanhgia());
        return maptodto(danhgiasanpham);
    }

    @Override
    public danhgiasanphamdto getdanhgiasanphambyid(Integer id) {
        logger.info("Fetching danhgiasanpham with id: {}", id);
        danhgiasanpham danhgiasanpham = danhgiasanphamrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("danhgiasanpham not found with id: {}", id);
                    return new RuntimeException("danhgiasanpham not found with id: " + id);
                });
        return maptodto(danhgiasanpham);
    }

    @Override
    public List<danhgiasanphamdto> getalldanhgiasanpham() {
        logger.info("Fetching all danhgiasanpham");
        return danhgiasanphamrepository.findAll().stream()
                .map(this::maptodto)
                .collect(Collectors.toList());
    }

    @Override
    public danhgiasanphamdto updatedanhgiasanpham(Integer id, danhgiasanphamdto danhgiasanphamdto) {
        logger.info("Updating danhgiasanpham with id: {}", id);
        danhgiasanpham danhgiasanpham = danhgiasanphamrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("danhgiasanpham not found with id: {}", id);
                    return new RuntimeException("danhgiasanpham not found with id: " + id);
                });

        danhgiasanpham.setsanpham(sanphamrepository.findById(danhgiasanphamdto.getmasanpham())
                .orElseThrow(() -> {
                    logger.error("sanpham not found with id: {}", danhgiasanphamdto.getmasanpham());
                    return new RuntimeException("sanpham not found");
                }));
        danhgiasanpham.setkhachhang(khachhangrepository.findById(danhgiasanphamdto.getmakhachhang())
                .orElseThrow(() -> {
                    logger.error("khachhang not found with id: {}", danhgiasanphamdto.getmakhachhang());
                    return new RuntimeException("khachhang not found");
                }));
        danhgiasanpham.setdiemdanhgia(danhgiasanphamdto.getdiemdanhgia());
        danhgiasanpham.setbinhluan(danhgiasanphamdto.getbinhluan());
        danhgiasanpham.sethinhanhdanhgia(danhgiasanphamdto.gethinhanhdanhgia());
        danhgiasanpham.setngaydanhgia(danhgiasanphamdto.getngaydanhgia());
        danhgiasanpham.settrangthai(danhgiasanphamdto.gettrangthai());

        if (danhgiasanphamdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(danhgiasanphamdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", danhgiasanphamdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            danhgiasanpham.setnguoitao_nhanvien(nhanvien);
        }

        danhgiasanpham = danhgiasanphamrepository.save(danhgiasanpham);
        logger.info("danhgiasanpham updated with id: {}", danhgiasanpham.getmadanhgia());
        return maptodto(danhgiasanpham);
    }

    @Override
    public void deletedanhgiasanpham(Integer id) {
        logger.info("Deleting danhgiasanpham with id: {}", id);
        danhgiasanpham danhgiasanpham = danhgiasanphamrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("danhgiasanpham not found with id: {}", id);
                    return new RuntimeException("danhgiasanpham not found with id: " + id);
                });
        danhgiasanphamrepository.delete(danhgiasanpham);
        logger.info("danhgiasanpham deleted with id: {}", id);
    }

    private danhgiasanphamdto maptodto(danhgiasanpham danhgiasanpham) {
        return danhgiasanphamdto.builder()
                .madanhgia(danhgiasanpham.getmadanhgia())
                .masanpham(danhgiasanpham.getsanpham().getmasanpham())
                .makhachhang(danhgiasanpham.getkhachhang().getmakhachhang())
                .diemdanhgia(danhgiasanpham.getdiemdanhgia())
                .binhluan(danhgiasanpham.getbinhluan())
                .hinhanhdanhgia(danhgiasanpham.gethinhanhdanhgia())
                .ngaydanhgia(danhgiasanpham.getngaydanhgia())
                .trangthai(danhgiasanpham.gettrangthai())
                .nguoitao(danhgiasanpham.getnguoitao())
                .build();
    }

    private danhgiasanpham maptoentity(danhgiasanphamdto danhgiasanphamdto) {
        return danhgiasanpham.builder()
                .madanhgia(danhgiasanphamdto.getmadanhgia())
                .diemdanhgia(danhgiasanphamdto.getdiemdanhgia())
                .binhluan(danhgiasanphamdto.getbinhluan())
                .hinhanhdanhgia(danhgiasanphamdto.gethinhanhdanhgia())
                .ngaydanhgia(danhgiasanphamdto.getngaydanhgia())
                .trangthai(danhgiasanphamdto.gettrangthai())
                .nguoitao(danhgiasanphamdto.getnguoitao())
                .build();
    }
}