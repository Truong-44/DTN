package com.example.be.TempoTideBE.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        if (responseObj != null) {
            map.put("data", responseObj);
        }
        return new ResponseEntity<>(map, status);
    }
}



//Vai trò của ResponseHandler.java
//File này là một lớp tiện ích (utility class) được sử dụng để chuẩn hóa cấu trúc phản hồi API (response) trong toàn bộ dự án.
//Nó giúp các controller trả về phản hồi với định dạng nhất quán, bao gồm:
//message: Thông điệp mô tả trạng thái của yêu cầu (ví dụ: "Thành công", "Sản phẩm không tồn tại").
//status: Mã trạng thái HTTP (ví dụ: 200, 404).
//data: Dữ liệu trả về (nếu có).
//Lớp này được sử dụng rộng rãi trong các controller, ví dụ như SanPhamController, để xử lý phản hồi cho các yêu cầu như tạo, cập nhật, xóa, hoặc lấy dữ liệu.