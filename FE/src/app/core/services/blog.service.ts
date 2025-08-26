import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiService } from './api.service';
import { Blog, BlogResponse } from '../models/blog.model';
import { PaginationRequest } from '../models/pagination.model';

@Injectable({
  providedIn: 'root',
})
export class BlogService {
  private readonly endpoint = 'blogs';

  constructor(private apiService: ApiService) {}

  getAllBlogs(pagination?: PaginationRequest): Observable<BlogResponse> {
    let params = new HttpParams();

    if (pagination) {
      params = params.set('page', pagination.page.toString());
      params = params.set('size', pagination.size.toString());
      if (pagination.sort) {
        params = params.set('sort', pagination.sort);
        params = params.set('direction', pagination.direction || 'desc');
      }
    }

    return this.apiService.get<BlogResponse>(this.endpoint, params);
  }

  getFeaturedBlogs(limit: number = 3): Observable<Blog[]> {
    const params = new HttpParams()
      .set('featured', 'true')
      .set('limit', limit.toString());

    return this.apiService.get<Blog[]>(`${this.endpoint}/featured`, params);
  }

  getLatestBlogs(limit: number = 6): Observable<Blog[]> {
    const params = new HttpParams()
      .set('limit', limit.toString())
      .set('sort', 'date')
      .set('direction', 'desc');

    return this.apiService.get<Blog[]>(`${this.endpoint}/latest`, params);
  }

  getBlogById(id: number): Observable<Blog> {
    return this.apiService.get<Blog>(`${this.endpoint}/${id}`);
  }

  getBlogsByCategory(category: string, limit?: number): Observable<Blog[]> {
    let params = new HttpParams().set('category', category);
    if (limit) {
      params = params.set('limit', limit.toString());
    }

    return this.apiService.get<Blog[]>(`${this.endpoint}/category`, params);
  }

  searchBlogs(keyword: string, filters?: any): Observable<BlogResponse> {
    let params = new HttpParams().set('keyword', keyword);

    if (filters) {
      Object.keys(filters).forEach((key) => {
        if (filters[key] !== null && filters[key] !== undefined) {
          params = params.set(key, filters[key]);
        }
      });
    }

    return this.apiService.get<BlogResponse>(`${this.endpoint}/search`, params);
  }

  createBlog(blog: Partial<Blog>): Observable<Blog> {
    return this.apiService.post<Blog>(this.endpoint, blog);
  }

  updateBlog(id: number, blog: Partial<Blog>): Observable<Blog> {
    return this.apiService.put<Blog>(`${this.endpoint}/${id}`, blog);
  }

  deleteBlog(id: number): Observable<void> {
    return this.apiService.delete<void>(`${this.endpoint}/${id}`);
  }

  incrementViews(id: number): Observable<void> {
    return this.apiService.post<void>(`${this.endpoint}/${id}/views`, {});
  }

  toggleLike(id: number): Observable<{ liked: boolean; totalLikes: number }> {
    return this.apiService.post<{ liked: boolean; totalLikes: number }>(
      `${this.endpoint}/${id}/like`,
      {}
    );
  }
}
