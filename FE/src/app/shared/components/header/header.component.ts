import { Component, HostListener, OnInit, OnDestroy } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {
  isScrolled = false;
  currentTime: string = '';

  private timeInterval: any;

  ngOnInit() {
    // Cập nhật thời gian ngay khi khởi tạo
    this.updateTime();
    // Cập nhật thời gian mỗi giây
    this.timeInterval = setInterval(() => this.updateTime(), 1000);
  }

  ngOnDestroy() {
    // Hủy interval để tránh rò rỉ bộ nhớ
    if (this.timeInterval) {
      clearInterval(this.timeInterval);
    }
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.isScrolled = window.scrollY > 10; // Giữ ngưỡng 10px như trong code bạn
  }

  private updateTime() {
    const now = new Date();
    // Định dạng giờ:phút:giây, 24h, luôn có 2 chữ số
    this.currentTime = now.toLocaleTimeString('en-US', {
      hour12: false, // 24h
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    });
  }
    // Nếu muốn toggle dropdown trên mobile, có thể thêm hàm:
  // toggleDropdown(index: number) {
  //   // Logic để hiện/ẩn dropdown
  // }
}
