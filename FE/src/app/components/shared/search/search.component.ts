import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { SanPhamService } from '../../../core/services/sanpham.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SanPham } from '../../../core/models/sanpham.model';
import { ImageService } from '../../../core/services/image.service';

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent implements OnInit {
  keyword: string = '';
  results: SanPham[] = [];
  total: number = 0;
  page: number = 0;
  size: number = 20;
  allProducts: SanPham[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sanphamService: SanPhamService,
    private imageService: ImageService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params: Params) => {
      this.keyword = params['keyword'] || '';
      this.page = +(params['page'] || 0);
      this.search();
    });
  }

  search() {
    if (this.keyword && this.keyword.trim() !== '') {
      this.sanphamService.searchSanPham(this.keyword).subscribe((products) => {
        this.results = products;
        this.total = products.length;
        // Note: Pagination might need to be handled differently if the API supports it
      });
    } else {
      this.results = [];
      this.total = 0;
    }
  }

  get totalPages(): number {
    return Math.ceil(this.total / this.size);
  }

  onPageChange(newPage: number) {
    this.router.navigate([], {
      queryParams: { keyword: this.keyword, page: newPage },
      queryParamsHandling: 'merge',
    });
  }

  getImageUrl(imageName: string | undefined): string {
    return this.imageService.getImageUrl(imageName || '');
  }
}
