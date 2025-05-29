package com.example.tempotide.controller;

import com.example.tempotide.dto.ChatBoxLichSuDTO;
import com.example.tempotide.service.ChatBoxLichSuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatbox-lichsu")
@RequiredArgsConstructor
public class ChatBoxLichSuController {
    private final ChatBoxLichSuService chatBoxLichSuService;

    @GetMapping
    public ResponseEntity<List<ChatBoxLichSuDTO>> getAllChatBoxLichSus() {
        return ResponseEntity.ok(chatBoxLichSuService.getAllChatBoxLichSus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatBoxLichSuDTO> getChatBoxLichSuById(@PathVariable Integer id) {
        return ResponseEntity.ok(chatBoxLichSuService.getChatBoxLichSuById(id));
    }

    @PostMapping
    public ResponseEntity<ChatBoxLichSuDTO> createChatBoxLichSu(@RequestBody ChatBoxLichSuDTO chatBoxLichSuDTO) {
        return ResponseEntity.ok(chatBoxLichSuService.createChatBoxLichSu(chatBoxLichSuDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatBoxLichSuDTO> updateChatBoxLichSu(@PathVariable Integer id, @RequestBody ChatBoxLichSuDTO chatBoxLichSuDTO) {
        return ResponseEntity.ok(chatBoxLichSuService.updateChatBoxLichSu(id, chatBoxLichSuDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatBoxLichSu(@PathVariable Integer id) {
        chatBoxLichSuService.deleteChatBoxLichSu(id);
        return ResponseEntity.noContent().build();
    }
}