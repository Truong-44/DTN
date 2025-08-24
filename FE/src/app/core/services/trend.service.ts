import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiService } from './api.service';
import { Trend, TrendResponse } from '../models/trend.model';
import { PaginationRequest } from '../models/pagination.model';

@Injectable({
  providedIn: 'root',
})
export class TrendService {
  private readonly endpoint = 'trends';

  constructor(private apiService: ApiService) {}

  getAllTrends(pagination?: PaginationRequest): Observable<TrendResponse> {
    let params = new HttpParams();

    if (pagination) {
      params = params.set('page', pagination.page.toString());
      params = params.set('size', pagination.size.toString());
      if (pagination.sort) {
        params = params.set('sort', pagination.sort);
        params = params.set('direction', pagination.direction || 'desc');
      }
    }

    return this.apiService.get<TrendResponse>(this.endpoint, params);
  }

  getFeaturedTrends(limit: number = 3): Observable<Trend[]> {
    const params = new HttpParams()
      .set('featured', 'true')
      .set('limit', limit.toString());

    return this.apiService.get<Trend[]>(`${this.endpoint}/featured`, params);
  }

  getTrendById(id: number): Observable<Trend> {
    return this.apiService.get<Trend>(`${this.endpoint}/${id}`);
  }

  getTrendsByCategory(category: string, limit?: number): Observable<Trend[]> {
    let params = new HttpParams().set('category', category);
    if (limit) {
      params = params.set('limit', limit.toString());
    }

    return this.apiService.get<Trend[]>(`${this.endpoint}/category`, params);
  }

  searchTrends(keyword: string, filters?: any): Observable<TrendResponse> {
    let params = new HttpParams().set('keyword', keyword);

    if (filters) {
      Object.keys(filters).forEach((key) => {
        if (filters[key] !== null && filters[key] !== undefined) {
          params = params.set(key, filters[key]);
        }
      });
    }

    return this.apiService.get<TrendResponse>(
      `${this.endpoint}/search`,
      params
    );
  }

  createTrend(trend: Partial<Trend>): Observable<Trend> {
    return this.apiService.post<Trend>(this.endpoint, trend);
  }

  updateTrend(id: number, trend: Partial<Trend>): Observable<Trend> {
    return this.apiService.put<Trend>(`${this.endpoint}/${id}`, trend);
  }

  deleteTrend(id: number): Observable<void> {
    return this.apiService.delete<void>(`${this.endpoint}/${id}`);
  }
}
