import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { Router } from '@angular/router';
import { ChatbotComponent } from './components/chatbot/chatbot.component';
import { NotificationContainerComponent } from './shared/components/notification-container/notification-container.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    NavbarComponent,
    FooterComponent,
    ChatbotComponent,
    NotificationContainerComponent,
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.scss'],
})
export class AppComponent implements OnInit {
  constructor(private router: Router) {}
  title = 'Frontend DTN';

  isAdminRoute(): boolean {
    return this.router.url.startsWith('/admin');
  }
  ngOnInit() {
    console.log('DTN Frontend khởi tạo thành công');
  }
}
