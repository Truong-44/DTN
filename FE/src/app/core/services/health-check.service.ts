// core/services/health-check.service.ts
import { Injectable } from '@angular/core';
import { Observable, of, forkJoin } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ApiService } from './api.service';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

export interface HealthCheckResult {
  service: string;
  status: 'healthy' | 'unhealthy' | 'unknown';
  message: string;
  timestamp: Date;
}

export interface SystemHealth {
  overall: 'healthy' | 'degraded' | 'unhealthy';
  services: HealthCheckResult[];
  environment: string;
  timestamp: Date;
}

@Injectable({
  providedIn: 'root',
})
export class HealthCheckService {
  constructor(
    private apiService: ApiService,
    private authService: AuthService
  ) {}

  // Check API connectivity
  checkApiHealth(): Observable<HealthCheckResult> {
    return this.apiService.get<any>('health').pipe(
      map(() => ({
        service: 'API',
        status: 'healthy' as const,
        message: 'API is responding',
        timestamp: new Date(),
      })),
      catchError((error) =>
        of({
          service: 'API',
          status: 'unhealthy' as const,
          message: `API error: ${error.message}`,
          timestamp: new Date(),
        })
      )
    );
  }

  // Check authentication service
  checkAuthHealth(): Observable<HealthCheckResult> {
    try {
      const isAuthenticated = this.authService.isAuthenticated();
      const token = this.authService.getToken();

      return of({
        service: 'Authentication',
        status: 'healthy' as const,
        message: `Auth service working. Authenticated: ${isAuthenticated}, Token: ${
          token ? 'Present' : 'Missing'
        }`,
        timestamp: new Date(),
      });
    } catch (error: any) {
      return of({
        service: 'Authentication',
        status: 'unhealthy' as const,
        message: `Auth service error: ${error.message}`,
        timestamp: new Date(),
      });
    }
  }

  // Check local storage
  checkStorageHealth(): Observable<HealthCheckResult> {
    try {
      const testKey = 'health_check_test';
      const testValue = 'test_value';

      localStorage.setItem(testKey, testValue);
      const retrievedValue = localStorage.getItem(testKey);
      localStorage.removeItem(testKey);

      if (retrievedValue === testValue) {
        return of({
          service: 'Local Storage',
          status: 'healthy' as const,
          message: 'Local storage is working',
          timestamp: new Date(),
        });
      } else {
        return of({
          service: 'Local Storage',
          status: 'unhealthy' as const,
          message: 'Local storage read/write failed',
          timestamp: new Date(),
        });
      }
    } catch (error: any) {
      return of({
        service: 'Local Storage',
        status: 'unhealthy' as const,
        message: `Local storage error: ${error.message}`,
        timestamp: new Date(),
      });
    }
  }

  // Check network connectivity
  checkNetworkHealth(): Observable<HealthCheckResult> {
    if (!navigator.onLine) {
      return of({
        service: 'Network',
        status: 'unhealthy' as const,
        message: 'No network connection detected',
        timestamp: new Date(),
      });
    }

    return of({
      service: 'Network',
      status: 'healthy' as const,
      message: 'Network connection available',
      timestamp: new Date(),
    });
  }

  // Comprehensive health check
  performFullHealthCheck(): Observable<SystemHealth> {
    const checks = [
      this.checkApiHealth(),
      this.checkAuthHealth(),
      this.checkStorageHealth(),
      this.checkNetworkHealth(),
    ];

    return forkJoin(checks).pipe(
      map((results) => {
        const healthyCount = results.filter(
          (r) => r.status === 'healthy'
        ).length;
        const unhealthyCount = results.filter(
          (r) => r.status === 'unhealthy'
        ).length;

        let overall: 'healthy' | 'degraded' | 'unhealthy';

        if (unhealthyCount === 0) {
          overall = 'healthy';
        } else if (healthyCount > unhealthyCount) {
          overall = 'degraded';
        } else {
          overall = 'unhealthy';
        }

        return {
          overall,
          services: results,
          environment: environment.production ? 'production' : 'development',
          timestamp: new Date(),
        };
      })
    );
  }

  // Get system information
  getSystemInfo(): any {
    return {
      userAgent: navigator.userAgent,
      language: navigator.language,
      cookieEnabled: navigator.cookieEnabled,
      onLine: navigator.onLine,
      platform: navigator.platform,
      localStorage: !!window.localStorage,
      sessionStorage: !!window.sessionStorage,
      indexedDB: !!window.indexedDB,
      timestamp: new Date().toISOString(),
    };
  }

  // Log system health to console
  logSystemHealth(): void {
    this.performFullHealthCheck().subscribe((health) => {
      console.group('🏥 System Health Check');
      console.log(`Overall Status: ${health.overall.toUpperCase()}`);
      console.log(`Environment: ${health.environment}`);
      console.log(`Timestamp: ${health.timestamp.toISOString()}`);

      console.group('Service Details:');
      health.services.forEach((service) => {
        const icon =
          service.status === 'healthy'
            ? '✅'
            : service.status === 'unhealthy'
            ? '❌'
            : '❓';
        console.log(`${icon} ${service.service}: ${service.message}`);
      });
      console.groupEnd();

      console.group('System Info:');
      const systemInfo = this.getSystemInfo();
      Object.entries(systemInfo).forEach(([key, value]) => {
        console.log(`${key}: ${value}`);
      });
      console.groupEnd();

      console.groupEnd();
    });
  }
}
