import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminLoadingService {
  private loadingStates = new Map<string, boolean>();
  private globalLoading$ = new BehaviorSubject<boolean>(false);

  constructor() {}

  // Global loading state
  getGlobalLoading(): Observable<boolean> {
    return this.globalLoading$.asObservable();
  }

  setGlobalLoading(loading: boolean): void {
    this.globalLoading$.next(loading);
  }

  // Component-specific loading states
  setLoading(component: string, loading: boolean): void {
    this.loadingStates.set(component, loading);
    this.updateGlobalLoading();
  }

  isLoading(component: string): boolean {
    return this.loadingStates.get(component) || false;
  }

  private updateGlobalLoading(): void {
    const hasAnyLoading = Array.from(this.loadingStates.values()).some(loading => loading);
    this.globalLoading$.next(hasAnyLoading);
  }

  // Utility methods for common operations
  startOperation(operation: string): void {
    this.setLoading(operation, true);
  }

  endOperation(operation: string): void {
    this.setLoading(operation, false);
  }

  // Decorator for automatic loading states
  withLoading<T>(operation: string, promise: Promise<T>): Promise<T> {
    this.startOperation(operation);
    return promise.finally(() => {
      this.endOperation(operation);
    });
  }

  // For RxJS observables
  wrapObservable<T>(operation: string, observable: Observable<T>): Observable<T> {
    this.startOperation(operation);
    return new Observable(subscriber => {
      const subscription = observable.subscribe({
        next: value => subscriber.next(value),
        error: error => {
          this.endOperation(operation);
          subscriber.error(error);
        },
        complete: () => {
          this.endOperation(operation);
          subscriber.complete();
        }
      });
      return () => subscription.unsubscribe();
    });
  }
}
