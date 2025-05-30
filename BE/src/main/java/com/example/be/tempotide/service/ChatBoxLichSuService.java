package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.ChatBoxLichSuDTO;

import java.util.List;

public interface ChatBoxLichSuService {
    List<ChatBoxLichSuDTO> getAllChatBoxLichSus();
    ChatBoxLichSuDTO getChatBoxLichSuById(Integer id);
    ChatBoxLichSuDTO createChatBoxLichSu(ChatBoxLichSuDTO chatBoxLichSuDTO);
    ChatBoxLichSuDTO updateChatBoxLichSu(Integer id, ChatBoxLichSuDTO chatBoxLichSuDTO);
    void deleteChatBoxLichSu(Integer id);
}