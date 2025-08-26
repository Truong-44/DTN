import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div style="padding: 20px; background: white; margin: 20px;">
      <h1 style="color: #FF9800;">🎯 Test Component Working!</h1>
      <p>If you see this, the routing is working correctly.</p>
      <p>Current time: {{ currentTime }}</p>
    </div>
  `,
  styles: []
})
export class TestComponent {
  currentTime = new Date().toLocaleString();
}
