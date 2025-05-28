package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.ChatboxLichSuDTO;
import com.example.be.tempotide.service.ChatboxLichSuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatboxlichsu")
@RequiredArgsConstructor
@Tag(name = "ChatboxLichSu API", description = "APIs for managing chatbox_lichsu")
public class ChatboxLichSuController {
    private final ChatboxLichSuService chatboxLichSuService;

    @GetMapping
    @Operation(summary = "Get all chatboxlichsus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChatboxLichSuDTO>> getAllChatboxLichSus() {
        return ResponseEntity.ok(chatboxLichSuService.getAllChatboxLichSus());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get chatboxlichsu by ID")
    public ResponseEntity<ChatboxLichSuDTO> getChatboxLichSuById(@PathVariable Integer id) {
        return ResponseEntity.ok(chatboxLichSuService.getChatboxLichSuById(id));
    }

    @GetMapping("/khachhang/{makhachhang}")
    @Operation(summary = "Get chatboxlichsu by KhachHang ID")
    public ResponseEntity<List<ChatboxLichSuDTO>> getChatboxLichSuByKhachHangId(@PathVariable Integer makhachhang) {
        return ResponseEntity.ok(chatboxLichSuService.getChatboxLichSuByKhachHangId(makhachhang));
    }

    @GetMapping("/sodienthoai/{sodienthoai}")
    @Operation(summary = "Get chatboxlichsu by sodienthoai")
    public ResponseEntity<List<ChatboxLichSuDTO>> getChatboxLichSuBySodienthoai(@PathVariable String sodienthoai) {
        return ResponseEntity.ok(chatboxLichSuService.getChatboxLichSuBySodienthoai(sodienthoai));
    }

    @PostMapping
    @Operation(summary = "Create a new chatboxlichsu")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChatboxLichSuDTO> createChatboxLichSu(@Valid @RequestBody ChatboxLichSuDTO chatboxLichSuDTO) {
        return ResponseEntity.ok(chatboxLichSuService.createChatboxLichSu(chatboxLichSuDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a chatboxlichsu")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChatboxLichSuDTO> updateChatboxLichSu(@PathVariable Integer id, @Valid @RequestBody ChatboxLichSuDTO chatboxLichSuDTO) {
        return ResponseEntity.ok(chatboxLichSuService.updateChatboxLichSu(id, chatboxLichSuDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a chatboxlichsu (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteChatboxLichSu(@PathVariable Integer id) {
        chatboxLichSuService.deleteChatboxLichSu(id);
        return ResponseEntity.noContent().build();
    }
}