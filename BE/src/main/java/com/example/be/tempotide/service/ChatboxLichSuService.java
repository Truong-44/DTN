package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.ChatboxLichSuDTO;

import java.util.List;

public interface ChatboxLichSuService {
    List<ChatboxLichSuDTO> getAllChatboxLichSus();
    ChatboxLichSuDTO getChatboxLichSuById(Integer id);
    ChatboxLichSuDTO createChatboxLichSu(ChatboxLichSuDTO chatboxLichSuDTO);
    ChatboxLichSuDTO updateChatboxLichSu(Integer id, ChatboxLichSuDTO chatboxLichSuDTO);
    void deleteChatboxLichSu(Integer id);
    List<ChatboxLichSuDTO> getChatboxLichSuByKhachHangId(Integer makhachhang);
    List<ChatboxLichSuDTO> getChatboxLichSuBySodienthoai(String sodienthoai);
}