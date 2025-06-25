import {
  Component,
  AfterViewInit,
  ElementRef,
  ViewChild,
  ChangeDetectionStrategy,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import gsap from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import ScrollSmoother from 'gsap/ScrollSmoother';

gsap.registerPlugin(ScrollTrigger, ScrollSmoother);

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements AfterViewInit {
  @ViewChild('wrapper', { static: true }) wrapperRef!: ElementRef;
  @ViewChild('content', { static: true }) contentRef!: ElementRef;

  section1Images: string[] = [
    'assets/img/BannerSlide/BS-01.jpg',
    'assets/img/BannerSlide/BS-02.jpg',
    'assets/img/BannerSlide/BS-03.jpg',
  ];

  scrollImages: string[] = [
    'assets/img/home/section2/s2-01.webp',
    'assets/img/home/section2/s2-02.webp',
    'assets/img/home/section2/s2-03.webp',
    'assets/img/home/section2/s2-04.webp',
    'assets/img/home/section2/s2-05.webp',
  ];

  products = [
    { name: 'Red Ginseng', image: 'assets/img/home/section2/s2-01.webp' },
    { name: 'Black Ginseng', image: 'assets/img/home/section2/s2-02.webp' },
    { name: 'Extract Ginseng', image: 'assets/img/home/section2/s2-03.webp' },
    { name: 'Tea Ginseng', image: 'assets/img/home/section2/s2-04.webp' },
  ];

  featuredProducts = [
    {
      badge: 'Bestseller',
      name: 'Expose side table',
      material: 'Aluminium',
      image: 'assets/img/sanpham/ghe/netro.webp',
      colors: ['#000000'],
      price: 12290000,
      original: 10390000,
    },
    {
      badge: "Helena's Pick",
      name: 'Nawabari footstool',
      material: 'Fabric - Lacquered',
      image: 'assets/img/home/products/slide2.jpg',
      colors: ['#f2c0b9', '#000000'],
      price: 13690000,
      original: 13190000,
    },
    {
      name: 'Bornholm coffee table',
      material: 'Wood',
      image: 'assets/img/home/products/slide3.jpg',
      colors: ['#d4aa6e'],
      price: 8290000,
      original: 5590000,
    },
    {
      name: 'Bermuda footstool',
      material: 'Fabric',
      image: 'assets/img/home/products/slide4.jpg',
      colors: ['#8c6d3f', '#000', '#fff'],
      price: 23190000,
      original: 10890000,
    },
    {
      badge: "Editor's choice",
      name: 'Tone mirror',
      material: 'Glass - Wood',
      image: 'assets/img/home/products/slide5.jpg',
      colors: ['#8eb6bd'],
      price: 12290000,
      original: 7990000,
    },
    {
      badge: "Helena's Pick",
      name: 'Bolzano chair',
      material: 'Fabric',
      image: 'assets/img/home/products/slide6.jpg',
      colors: ['#7d5c61'],
      price: 50790000,
      original: 39490000,
    },
  ];

  blogs = [
    {
      title: 'Benefits of Ginseng',
      image: 'assets/img/home/section2/s2-04.webp',
    },
    {
      title: 'How to Use Ginseng',
      image: 'assets/img/home/section2/s2-04.webp',
    },
    {
      title: 'Immune System Boost',
      image: 'assets/img/home/section2/s2-04.webp',
    },
    {
      title: 'Immune System Boost',
      image: 'assets/img/home/section2/s2-04.webp',
    },
    {
      title: 'Immune System Boost',
      image: 'assets/img/home/section2/s2-04.webp',
    },
    {
      title: 'Immune System Boost',
      image: 'assets/img/home/section2/s2-04.webp',
    },
  ];

  ngAfterViewInit(): void {
    this.initSmoothScroll();
    this.revealSections();
    this.initGallerySkew();
    this.animateProducts();
    this.animateBlogCards();
    this.staggerTextEffects();
    this.animateTestimonials();
    this.animateProductShowcase();
  }

  //SECTION 1
  heroImages = ['url("assets/img/BackGround/BG-01.jpg")'];
  currentHeroImage = this.heroImages[0];

  ngOnInit() {
    let i = 0;
    setInterval(() => {
      i = (i + 1) % this.heroImages.length;
      this.currentHeroImage = this.heroImages[i];
    }, 6000);
  }

  scrollToSection(id: string) {
    document.getElementById(id)?.scrollIntoView({ behavior: 'smooth' });
  }

  private initSmoothScroll(): void {
    ScrollSmoother.create({
      wrapper: this.wrapperRef.nativeElement,
      content: this.contentRef.nativeElement,
      smooth: 1.3,
      speed: 1,
      effects: true,
    });
  }

  private revealSections(): void {
    gsap.utils.toArray<HTMLElement>('.section').forEach((el) => {
      gsap.fromTo(
        el,
        { autoAlpha: 0, y: 100 },
        {
          autoAlpha: 1,
          y: 0,
          duration: 1.2,
          ease: 'power2.out',
          scrollTrigger: {
            trigger: el,
            start: 'top 85%',
            toggleActions: 'play none none reverse',
          },
        }
      );
    });
  }

  private initGallerySkew(): void {
    const skewSetter = gsap.quickSetter('.gallery-grid div', 'skewY', 'deg');
    const clamp = gsap.utils.clamp(-20, 20);
    ScrollTrigger.create({
      onUpdate: (self) => skewSetter(clamp(self.getVelocity() / -50)),
    });
  }

  private animateProducts(): void {
    gsap.utils.toArray<HTMLElement>('.product-card').forEach((card, i) => {
      gsap.fromTo(
        card,
        { opacity: 0, y: 50 },
        {
          opacity: 1,
          y: 0,
          duration: 0.9,
          delay: i * 0.2,
          ease: 'power2.out',
          scrollTrigger: {
            trigger: card,
            start: 'top 90%',
            toggleActions: 'play none none reverse',
          },
        }
      );
    });
  }

  private animateBlogCards(): void {
    gsap.utils.toArray<HTMLElement>('.blog-card').forEach((card, i) => {
      gsap.fromTo(
        card,
        { scale: 0.85, opacity: 0 },
        {
          scale: 1,
          opacity: 1,
          duration: 1,
          delay: i * 0.2,
          ease: 'back.out(1.7)',
          scrollTrigger: {
            trigger: card,
            start: 'top 85%',
            toggleActions: 'play none none reverse',
          },
        }
      );
    });
  }

  private animateTestimonials(): void {
    gsap.utils.toArray<HTMLElement>('.testimonial-item').forEach((el, i) => {
      gsap.fromTo(
        el,
        { opacity: 0, x: 50 },
        {
          opacity: 1,
          x: 0,
          duration: 1,
          delay: i * 0.2,
          ease: 'power2.out',
          scrollTrigger: {
            trigger: el,
            start: 'top 85%',
            toggleActions: 'play none none reverse',
          },
        }
      );
    });
  }

  private staggerTextEffects(): void {
    gsap.utils
      .toArray<HTMLElement>('h1.hero-title, p.hero-sub')
      .forEach((el) => {
        gsap.from(el, {
          y: 40,
          opacity: 0,
          duration: 1,
          ease: 'power4.out',
          scrollTrigger: {
            trigger: el,
            start: 'top 80%',
            toggleActions: 'play none none reverse',
          },
        });
      });
  }

  // scrollToSection(id: string): void {
  //   const smoother = ScrollSmoother.get();
  //   if (smoother) {
  //     smoother.scrollTo(`#${id}`, true, 'top center');
  //   } else {
  //     document.getElementById(id)?.scrollIntoView({ behavior: 'smooth' });
  //   }
  // }
  private animateProductShowcase(): void {
    const cards = gsap.utils.toArray<HTMLElement>('.product-slide');

    gsap.set(cards, { opacity: 0, y: 80, scale: 0.9 });

    ScrollTrigger.batch(cards, {
      interval: 0.15,
      batchMax: 3,
      onEnter: (batch) => {
        gsap.to(batch, {
          opacity: 1,
          y: 0,
          scale: 1,
          duration: 1,
          ease: 'power3.out',
          stagger: 0.2,
        });
      },
      start: 'top 85%',
      once: false,
    });

    // Enable smooth natural trackpad scroll
    const slider = document.querySelector('.slider-wrapper') as HTMLElement;
    if (slider) {
      slider.style.scrollBehavior = 'smooth';
      slider.style.touchAction = 'pan-x';
    }
  }
}
