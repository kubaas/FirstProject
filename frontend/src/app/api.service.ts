import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CheckInRequest, MealPlanResponse, OnboardingRequest, OnboardingResponse } from './api.models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = 'http://localhost:8080/api';

  constructor(private readonly http: HttpClient) {}

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

