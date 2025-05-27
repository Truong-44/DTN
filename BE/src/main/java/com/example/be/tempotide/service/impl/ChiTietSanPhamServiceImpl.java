package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.chitietsanphamdto;
import com.example.be.tempotide.entity.chitietsanpham;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.chitietsanphamrepository;
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
public class chitietsanphamserviceimpl implements com.example.be.tempotide.service.chitietsanphamservice {

    private static final Logger logger = LoggerFactory.getLogger(chitietsanphamserviceimpl.class);

    private final chitietsanphamrepository chitietsanphamrepository;
    private final sanphamrepository sanphamrepository;
    private final nhanvienrepository nhanvienrepository;

    @Autowired
    public chitietsanphamserviceimpl(chitietsanphamrepository chitietsanphamrepository,
                                     sanphamrepository sanphamrepository,
                                     nhanvienrepository nhanvienrepository) {
        this.chitietsanphamrepository = chitietsanphamrepository;
        this.sanphamrepository = sanphamrepository;
        this.nhanvienrepository = nhanvienrepository;
    }

    @Override
    public chitietsanphamdto createchitietsanpham(chitietsanphamdto chitietsanphamdto) {
        logger.info("Creating new chitietsanpham for masanpham: {}", chitietsanphamdto.getmasanpham());
        chitietsanpham chitietsanpham = maptoentity(chitietsanphamdto);

        chitietsanpham.setsanpham(sanphamrepository.findById(chitietsanphamdto.getmasanpham())
                .orElseThrow(() -> {
                    logger.error("sanpham not found with id: {}", chitietsanphamdto.getmasanpham());
                    return new RuntimeException("sanpham not found");
                }));

        if (chitietsanphamdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(chitietsanphamdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", chitietsanphamdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            chitietsanpham.setnhanvien(nhanvien);
        }

        chitietsanpham.setngaytao(LocalDateTime.now());
        chitietsanpham = chitietsanphamrepository.save(chitietsanpham);
        logger.info("chitietsanpham created with id: {}", chitietsanpham.getmachitetsanpham());
        return maptodto(chitietsanpham);
    }

    @Override
    public chitietsanphamdto getchitietsanphambyid(Integer id) {
        logger.info("Fetching chitietsanpham with id: {}", id);
        chitietsanpham chitietsanpham = chitietsanphamrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("chitietsanpham not found with id: {}", id);
                    return new RuntimeException("chitietsanpham not found with id: " + id);
                });
        return maptodto(chitietsanpham);
    }

    @Override
    public List<chitietsanphamdto> getallchitietsanpham() {
        logger.info("Fetching all chitietsanpham");
        return chitietsanphamrepository.findAll().stream()
                .map(this::maptodto)
                .collect(Collectors.toList());
    }

    @Override
    public chitietsanphamdto updatechitietsanpham(Integer id, chitietsanphamdto chitietsanphamdto) {
        logger.info("Updating chitietsanpham with id: {}", id);
        chitietsanpham chitietsanpham = chitietsanphamrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("chitietsanpham not found with id: {}", id);
                    return new RuntimeException("chitietsanpham not found with id: " + id);
                });

        chitietsanpham.setmasanpham(chitietsanphamdto.getmasanpham());
        chitietsanpham.setsoluongton(chitietsanphamdto.getsoluongton());
        chitietsanpham.setgiaban(chitietsanphamdto.getgiaban());
        chitietsanpham.settrangthai(chitietsanphamdto.gettrangthai());

        chitietsanpham.setsanpham(sanphamrepository.findById(chitietsanphamdto.getmasanpham())
                .orElseThrow(() -> {
                    logger.error("sanpham not found with id: {}", chitietsanphamdto.getmasanpham());
                    return new RuntimeException("sanpham not found");
                }));

        if (chitietsanphamdto.getnguoitao() != null) {
            nhanvien nhanvien = nhanvienrepository.findById(chitietsanphamdto.getnguoitao())
                    .orElseThrow(() -> {
                        logger.error("nhanvien not found with id: {}", chitietsanphamdto.getnguoitao());
                        return new RuntimeException("nhanvien not found");
                    });
            chitietsanpham.setnhanvien(nhanvien);
        }

        chitietsanpham = chitietsanphamrepository.save(chitietsanpham);
        logger.info("chitietsanpham updated with id: {}", chitietsanpham.getmachitetsanpham());
        return maptodto(chitietsanpham);
    }

    @Override
    public void deletechitietsanpham(Integer id) {
        logger.info("Deleting chitietsanpham with id: {}", id);
        chitietsanpham chitietsanpham = chitietsanphamrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("chitietsanpham not found with id: {}", id);
                    return new RuntimeException("chitietsanpham not found with id: " + id);
                });
        chitietsanphamrepository.delete(chitietsanpham);
        logger.info("chitietsanpham deleted with id: {}", id);
    }

    private chitietsanphamdto maptodto(chitietsanpham chitietsanpham) {
        return chitietsanphamdto.builder()
                .machitetsanpham(chitietsanpham.getmachitetsanpham())
                .masanpham(chitietsanpham.getmasanpham())
                .soluongton(chitietsanpham.getsoluongton())
                .giaban(chitietsanpham.getgiaban())
                .ngaytao(chitietsanpham.getngaytao())
                .trangthai(chitietsanpham.gettrangthai())
                .nguoitao(chitietsanpham.getnguoitao())
                .build();
    }

    private chitietsanpham maptoentity(chitietsanphamdto chitietsanphamdto) {
        return chitietsanpham.builder()
                .machitetsanpham(chitietsanphamdto.getmachitetsanpham())
                .masanpham(chitietsanphamdto.getmasanpham())
                .soluongton(chitietsanphamdto.getsoluongton())
                .giaban(chitietsanphamdto.getgiaban())
                .ngaytao(chitietsanphamdto.getngaytao())
                .trangthai(chitietsanphamdto.gettrangthai())
                .nguoitao(chitietsanphamdto.getnguoitao())
                .build();
    }
}