import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ApiService } from '../api.service';
import { MealPlanDayView, MealPlanResponse, MealType } from '../api.models';

function mealTypeLabel(t: MealType): string {
  switch (t) {
    case 'BREAKFAST': return 'Śniadanie';
    case 'LUNCH': return 'Obiad';
    case 'DINNER': return 'Kolacja';
  }
}

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="card">
      <div style="display:flex; justify-content: space-between; align-items: baseline; gap: 12px;">
        <div>
          <h2 style="margin:0 0 6px;">Plan na 7 dni</h2>
          <div class="muted" *ngIf="plan">
            Strategia: <b>{{ plan.strategy }}</b> • Start tygodnia: <b>{{ plan.weekStart }}</b>
          </div>
        </div>
        <div style="display:flex; gap:10px;">
          <a class="btn secondary" routerLink="/onboarding">Zmień profil</a>
          <a class="btn" routerLink="/checkin">Zrób check-in</a>
        </div>
      </div>

      <div *ngIf="loading" class="muted" style="margin-top: 12px;">Ładuję plan...</div>
      <div *ngIf="error" class="error" style="margin-top: 12px;">{{ error }}</div>

      <div *ngIf="plan" style="margin-top: 14px;">
        <table class="table">
          <thead>
            <tr>
              <th style="width: 70px;">Dzień</th>
              <th>Posiłki</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let d of plan.days">
              <td><b>{{ d.dayIndex + 1 }}</b></td>
              <td>
                <div *ngFor="let it of d.items" style="margin-bottom: 10px;">
                  <div>
                    <span class="pill">{{ mealTypeLabel(it.mealType) }}</span>
                    <b>{{ it.recipe.name }}</b>
                    <span class="muted"> • {{ it.recipe.prepMinutes }} min</span>
                  </div>
                  <div class="muted" style="margin-top: 4px; font-size: 12px;">
                    {{ it.recipe.calories }} kcal •
                    B: {{ it.recipe.proteinG }}g •
                    W: {{ it.recipe.carbsG }}g •
                    T: {{ it.recipe.fatG }}g
                  </div>
                  <div *ngIf="it.notes" class="muted" style="margin-top: 4px; font-size: 12px;">
                    {{ it.notes }}
                  </div>
                  <div style="margin-top: 6px;">
                    <span class="pill" *ngFor="let tag of it.recipe.tags">{{ tag }}</span>
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div *ngIf="!plan && !loading && !error" class="muted" style="margin-top: 12px;">
        Brak planu. Zrób onboarding.
      </div>
    </div>
  `,
})
export class PlanComponent {
  loading = false;
  error: string | null = null;
  plan: MealPlanResponse | null = null;

  constructor(private readonly api: ApiService) {
    const userId = localStorage.getItem('imo.userId');
    if (!userId) return;

    this.loading = true;
    this.api.latestPlan(userId).subscribe({
      next: (p) => {
        this.plan = { ...p, days: normalizeDays(p.days) };
        this.loading = false;
      },
      error: (e) => {
        this.loading = false;
        this.error = `Błąd pobierania planu: ${e?.message ?? e}`;
      },
    });
  }

  mealTypeLabel(t: MealType): string {
    return mealTypeLabel(t);
  }
}

function normalizeDays(days: MealPlanDayView[]): MealPlanDayView[] {
  return [...days].sort((a, b) => a.dayIndex - b.dayIndex);
}

