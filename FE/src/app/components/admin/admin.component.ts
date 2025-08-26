import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { AdminSidebarComponent } from './shared/admin-sidebar/admin-sidebar.component';
import { AdminNavbarComponent } from './shared/admin-navbar/admin-navbar.component';
import { AdminFooterComponent } from './shared/admin-footer/admin-footer.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    AdminSidebarComponent,
    AdminNavbarComponent,
    AdminFooterComponent,
  ],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
})
export class AdminComponent {}
