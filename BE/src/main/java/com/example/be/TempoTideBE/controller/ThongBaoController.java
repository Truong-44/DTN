packcom.example.be.TempoTideBE.controller;

import com.example.be.dto.ThongBaoDTO;
import com.example.be.service.ThongBaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thongbao")
@RequiredArgsConstructor
public class ThongBaoController {

    private final ThongBaoService thongBaoService;

    @GetMapping
    public ResponseEntity<List<ThongBaoDTO>> getAllThongBao() {
        return ResponseEntity.ok(thongBaoService.getAllThongBao());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThongBaoDTO> getThongBaoById(@PathVariable Integer id) {
        return ResponseEntity.ok(thongBaoService.getThongBaoById(id));
    }

    @PostMapping
    public ResponseEntity<ThongBaoDTO> createThongBao(@RequestBody ThongBaoDTO dto) {
        return ResponseEntity.ok(thongBaoService.createThongBao(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThongBaoDTO> updateThongBao(@PathVariable Integer id, @RequestBody ThongBaoDTO dto) {
        return ResponseEntity.ok(thongBaoService.updateThongBao(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThongBao(@PathVariable Integer id) {
        thongBaoService.deleteThongBao(id);
        return ResponseEntity.noContent().build();
    }
}