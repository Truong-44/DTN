import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  public isLoggedIn$ = this.isLoggedInSubject.asObservable();
  private username: string = '';

  constructor() {
    // Kiểm tra trạng thái đăng nhập từ localStorage khi khởi động
    const savedUsername = localStorage.getItem('username');
    if (savedUsername) {
      this.username = savedUsername;
      this.isLoggedInSubject.next(true);
    }
  }

  login(username: string) {
    this.username = username;
    this.isLoggedInSubject.next(true);
    localStorage.setItem('username', username);
  }

  logout() {
    this.username = '';
    this.isLoggedInSubject.next(false);
    localStorage.removeItem('username');
  }

  getUsername(): string {
    return this.username;
  }

  isLoggedIn(): boolean {
    return this.isLoggedInSubject.value;
  }
}
