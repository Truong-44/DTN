import { Component, OnInit, HostListener, signal } from '@angular/core';
import gsap from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
interface NavItem {
  label: string;
  target: string;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent implements OnInit {
  scrolled = false;
  loginDropdown = false;
  searchText = '';
  navItems: NavItem[] = [
    { label: 'Trang chủ', target: 'section1' },
    { label: 'Sản phẩm', target: 'section2' },
    { label: 'Dự án', target: 'section3' },
    { label: 'Blog', target: 'section4' },
  ];

  ngOnInit(): void {
    gsap.registerPlugin(ScrollTrigger);

    ScrollTrigger.create({
      start: 'top top',
      onUpdate: (self) => {
        this.scrolled = self.scroll() > 10;
      },
    });

    // Optional hover animation
    setTimeout(() => {
      const navItems = document.querySelectorAll('.navbar__center ul li');
      navItems.forEach((el) => {
        el.addEventListener('mouseenter', () => {
          gsap.to(el, {
            scale: 1.1,
            duration: 0.3,
            ease: 'power2.out',
          });
        });

        el.addEventListener('mouseleave', () => {
          gsap.to(el, {
            scale: 1,
            duration: 0.3,
            ease: 'power2.inOut',
          });
        });
      });
    }, 0);
  }

  toggleLoginDropdown(): void {
    this.loginDropdown = !this.loginDropdown;
  }

  scrollToSection(targetId: string): void {
    const target = document.getElementById(targetId);
    if (target) {
      target.scrollIntoView({ behavior: 'smooth' });
    }
  }
}
