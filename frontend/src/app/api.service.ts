import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CheckInRequest, MealPlanResponse, OnboardingRequest, OnboardingResponse } from './api.models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = this.getBaseUrl();

  constructor(private readonly http: HttpClient) {}

  private getBaseUrl(): string {
    const hostname = location.hostname;
    
    // Development
    if (hostname === 'localhost' || hostname === '127.0.0.1') {
      return 'http://localhost:8080/api';
    }
    
    // GitHub Pages + Replit Backend
    if (hostname.includes('github.io')) {
      return 'https://FirstProject.kubaas.replit.dev/api';
    }
    
    // Render or other production
    return '/api';
  }

  onboarding(req: OnboardingRequest): Observable<OnboardingResponse> {
    return this.http.post<OnboardingResponse>(`${this.baseUrl}/users/onboarding`, req);
  }

  latestPlan(userId: string): Observable<MealPlanResponse> {
    return this.http.get<MealPlanResponse>(`${this.baseUrl}/users/${userId}/plans/latest`);
  }

  submitCheckIn(userId: string, req: CheckInRequest): Observable<MealPlanResponse> {
    return this.http.post<MealPlanResponse>(`${this.baseUrl}/users/${userId}/checkins`, req);
  }
}

