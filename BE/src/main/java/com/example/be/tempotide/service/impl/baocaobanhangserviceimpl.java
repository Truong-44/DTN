package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.baocaobanhangdto;
import com.example.be.tempotide.entity.baocaobanhang;
import com.example.be.tempotide.repository.baocaobanhangrepository;
import com.example.be.tempotide.service.baocaobanhangservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class baocaobanhangserviceimpl implements baocaobanhangservice {

    private static final Logger logger = LoggerFactory.getLogger(baocaobanhangserviceimpl.class);

    private final baocaobanhangrepository baocaobanhangrepository;

    @Autowired
    public baocaobanhangserviceimpl(baocaobanhangrepository baocaobanhangrepository) {
        this.baocaobanhangrepository = baocaobanhangrepository;
    }

    @Override
    public baocaobanhangdto create_baocaobanhang(baocaobanhangdto baocaobanhangdto) {
        logger.info("Creating new baocaobanhang for period: {} to {}", baocaobanhangdto.get_thoi_gian_bat_dau(), baocaobanhangdto.get_thoi_gian_ket_thuc());
        baocaobanhang baocaobanhang = map_to_entity(baocaobanhangdto);
        baocaobanhang = baocaobanhangrepository.save(baocaobanhang);
        logger.info("baocaobanhang created with id: {}", baocaobanhang.get_ma_bao_cao());
        return map_to_dto(baocaobanhang);
    }

    @Override
    public baocaobanhangdto get_baocaobanhang_by_id(Integer id) {
        logger.info("Fetching baocaobanhang with id: {}", id);
        baocaobanhang baocaobanhang = baocaobanhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("baocaobanhang not found with id: {}", id);
                    return new RuntimeException("baocaobanhang not found with id: " + id);
                });
        return map_to_dto(baocaobanhang);
    }

    @Override
    public List<baocaobanhangdto> get_all_baocaobanhang() {
        logger.info("Fetching all baocaobanhang");
        return baocaobanhangrepository.findAll().stream()
                .map(this::map_to_dto)
                .collect(Collectors.toList());
    }

    @Override
    public baocaobanhangdto update_baocaobanhang(Integer id, baocaobanhangdto baocaobanhangdto) {
        logger.info("Updating baocaobanhang with id: {}", id);
        baocaobanhang baocaobanhang = baocaobanhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("baocaobanhang not found with id: {}", id);
                    return new RuntimeException("baocaobanhang not found with id: " + id);
                });

        baocaobanhang.set_thoi_gian_bat_dau(baocaobanhangdto.get_thoi_gian_bat_dau());
        baocaobanhang.set_thoi_gian_ket_thuc(baocaobanhangdto.get_thoi_gian_ket_thuc());
        baocaobanhang.set_tong_doanh_thu(baocaobanhangdto.get_tong_doanh_thu());
        baocaobanhang.set_so_don_hang(baocaobanhangdto.get_so_don_hang());
        baocaobanhang.set_trang_thai(baocaobanhangdto.get_trang_thai());

        baocaobanhang = baocaobanhangrepository.save(baocaobanhang);
        logger.info("baocaobanhang updated with id: {}", baocaobanhang.get_ma_bao_cao());
        return map_to_dto(baocaobanhang);
    }

    @Override
    public void delete_baocaobanhang(Integer id) {
        logger.info("Deleting baocaobanhang with id: {}", id);
        baocaobanhang baocaobanhang = baocaobanhangrepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("baocaobanhang not found with id: {}", id);
                    return new RuntimeException("baocaobanhang not found with id: " + id);
                });
        baocaobanhangrepository.delete(baocaobanhang);
        logger.info("baocaobanhang deleted with id: {}", id);
    }

    private baocaobanhangdto map_to_dto(baocaobanhang baocaobanhang) {
        return baocaobanhangdto.builder()
                .ma_bao_cao(baocaobanhang.get_ma_bao_cao())
                .thoi_gian_bat_dau(baocaobanhang.get_thoi_gian_bat_dau())
                .thoi_gian_ket_thuc(baocaobanhang.get_thoi_gian_ket_thuc())
                .tong_doanh_thu(baocaobanhang.get_tong_doanh_thu())
                .so_don_hang(baocaobanhang.get_so_don_hang())
                .trang_thai(baocaobanhang.get_trang_thai())
                .build();
    }

    private baocaobanhang map_to_entity(baocaobanhangdto baocaobanhangdto) {
        return baocaobanhang.builder()
                .ma_bao_cao(baocaobanhangdto.get_ma_bao_cao())
                .thoi_gian_bat_dau(baocaobanhangdto.get_thoi_gian_bat_dau())
                .thoi_gian_ket_thuc(baocaobanhangdto.get_thoi_gian_ket_thuc())
                .tong_doanh_thu(baocaobanhangdto.get_tong_doanh_thu())
                .so_don_hang(baocaobanhangdto.get_so_don_hang())
                .trang_thai(baocaobanhangdto.get_trang_thai())
                .build();
    }
}