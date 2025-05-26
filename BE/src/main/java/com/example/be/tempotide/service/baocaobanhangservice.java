package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.baocaobanhangdto;

import java.util.List;

public interface baocaobanhangservice {
    baocaobanhangdto create_baocaobanhang(baocaobanhangdto baocaobanhangdto);
    baocaobanhangdto get_baocaobanhang_by_id(Integer id);
    List<baocaobanhangdto> get_all_baocaobanhang();
    baocaobanhangdto update_baocaobanhang(Integer id, baocaobanhangdto baocaobanhangdto);
    void delete_baocaobanhang(Integer id);
}