import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import gsap from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger);

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
})
export class FooterComponent implements AfterViewInit {
  @ViewChild('footerRef', { static: true }) footerRef!: ElementRef;

  ngAfterViewInit(): void {
    gsap.from(this.footerRef.nativeElement.querySelectorAll('.footer-column'), {
      scrollTrigger: {
        trigger: this.footerRef.nativeElement,
        start: 'top 90%',
      },
      y: 100,
      opacity: 0,
      duration: 1,
      ease: 'power3.out',
      stagger: 0.2,
    });
  }
}
