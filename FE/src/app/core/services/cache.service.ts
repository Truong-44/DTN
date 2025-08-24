// core/services/cache.service.ts
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';

interface CacheItem<T> {
  data: T;
  timestamp: number;
  ttl: number; // Time to live in milliseconds
}

@Injectable({
  providedIn: 'root',
})
export class CacheService {
  private cache = new Map<string, CacheItem<any>>();
  private defaultTTL = 5 * 60 * 1000; // 5 minutes

  constructor() {
    // Clean expired cache every 10 minutes
    setInterval(() => this.cleanExpiredCache(), 10 * 60 * 1000);
  }

  // Get cached data
  get<T>(key: string): T | null {
    const item = this.cache.get(key);

    if (!item) {
      return null;
    }

    if (this.isExpired(item)) {
      this.cache.delete(key);
      return null;
    }

    return item.data;
  }

  // Set cache data
  set<T>(key: string, data: T, ttl?: number): void {
    const item: CacheItem<T> = {
      data,
      timestamp: Date.now(),
      ttl: ttl || this.defaultTTL,
    };

    this.cache.set(key, item);
  }

  // Remove specific cache item
  remove(key: string): void {
    this.cache.delete(key);
  }

  // Clear all cache
  clear(): void {
    this.cache.clear();
  }

  // Cache wrapper for observables
  cacheObservable<T>(
    key: string,
    source$: Observable<T>,
    ttl?: number
  ): Observable<T> {
    const cached = this.get<T>(key);

    if (cached !== null) {
      return of(cached);
    }

    return source$.pipe(tap((data) => this.set(key, data, ttl)));
  }

  // Check if cache item is expired
  private isExpired(item: CacheItem<any>): boolean {
    return Date.now() - item.timestamp > item.ttl;
  }
  // Clean expired cache items
  private cleanExpiredCache(): void {
    const now = Date.now();
    const keysToDelete: string[] = [];

    this.cache.forEach((item, key) => {
      if (now - item.timestamp > item.ttl) {
        keysToDelete.push(key);
      }
    });

    keysToDelete.forEach((key) => this.cache.delete(key));
  }

  // Get cache statistics
  getStats(): { size: number; keys: string[] } {
    return {
      size: this.cache.size,
      keys: Array.from(this.cache.keys()),
    };
  }

  // Predefined cache keys for common data
  static readonly CACHE_KEYS = {
    SANPHAM: {
      ALL: 'sanpham_all',
      BY_ID: (id: number) => `sanpham_${id}`,
      BY_CATEGORY: (categoryId: number) => `sanpham_category_${categoryId}`,
      SEARCH: (keyword: string) => `sanpham_search_${keyword}`,
    },
    DANHMUC: {
      ALL: 'danhmuc_all',
      BY_ID: (id: number) => `danhmuc_${id}`,
    },
    KHACHHANG: {
      BY_ID: (id: number) => `khachhang_${id}`,
    },
    GIOHANG: {
      BY_CUSTOMER: (customerId: number) => `giohang_customer_${customerId}`,
    },
  };
}
