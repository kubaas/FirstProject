import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../api.service';
import { CheckInRequest } from '../api.models';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  template: `
    <div class="card">
      <div style="display:flex; justify-content: space-between; align-items: baseline; gap: 12px;">
        <div>
          <h2 style="margin:0 0 6px;">Tygodniowy check-in</h2>
          <div class="muted">
            Skala 0..1. Na podstawie wyniku dobieram strategię i generuję plan na kolejny tydzień.
          </div>
        </div>
        <a class="btn secondary" routerLink="/plan">Wróć do planu</a>
      </div>

      <form [formGroup]="form" (ngSubmit)="submit()" style="margin-top: 14px;">
        <div class="grid">
          <div class="col-6">
            <label>Zgodność z planem (0..1)</label>
            <input type="number" step="0.1" min="0" max="1" formControlName="complianceScore" />
          </div>
          <div class="col-6">
            <label>Głód wieczorem (0..1)</label>
            <input type="number" step="0.1" min="0" max="1" formControlName="hungerEvening" />
          </div>
          <div class="col-6">
            <label>Energia po posiłkach (0..1)</label>
            <input type="number" step="0.1" min="0" max="1" formControlName="energyAfterMeals" />
          </div>
          <div class="col-6">
            <label>Sen (0..1)</label>
            <input type="number" step="0.1" min="0" max="1" formControlName="sleepScore" />
          </div>
          <div class="col-6">
            <label>Cravings / zachcianki (0..1)</label>
            <input type="number" step="0.1" min="0" max="1" formControlName="cravings" />
          </div>

          <div class="col-12" style="display:flex; gap:10px; margin-top: 6px;">
            <button class="btn" type="submit" [disabled]="loading || form.invalid">
              {{ loading ? 'Przetwarzam...' : 'Wyślij check-in i wygeneruj plan' }}
            </button>
            <button class="btn secondary" type="button" (click)="useDemo()">Demo</button>
          </div>

          <div class="col-12" *ngIf="error" style="margin-top: 10px;">
            <div class="error">{{ error }}</div>
          </div>
        </div>
      </form>
    </div>
  `,
})
export class CheckInComponent {
  loading = false;
  error: string | null = null;

  form = this.fb.group({
    complianceScore: [0.7, [Validators.required, Validators.min(0), Validators.max(1)]],
    hungerEvening: [0.3, [Validators.required, Validators.min(0), Validators.max(1)]],
    energyAfterMeals: [0.6, [Validators.required, Validators.min(0), Validators.max(1)]],
    sleepScore: [0.6, [Validators.required, Validators.min(0), Validators.max(1)]],
    cravings: [0.3, [Validators.required, Validators.min(0), Validators.max(1)]],
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly api: ApiService,
    private readonly router: Router
  ) {}

  useDemo(): void {
    this.form.patchValue({
      complianceScore: 0.55,
      hungerEvening: 0.8,
      energyAfterMeals: 0.45,
      sleepScore: 0.55,
      cravings: 0.65,
    });
  }

  submit(): void {
    this.error = null;
    const userId = localStorage.getItem('imo.userId');
    if (!userId) {
      this.error = 'Brak userId — najpierw zrób onboarding.';
      return;
    }
    if (this.form.invalid) return;

    const v = this.form.getRawValue();
    const req: CheckInRequest = {
      complianceScore: v.complianceScore!,
      hungerEvening: v.hungerEvening!,
      energyAfterMeals: v.energyAfterMeals!,
      sleepScore: v.sleepScore!,
      cravings: v.cravings!,
    };

    this.loading = true;
    this.api.submitCheckIn(userId, req).subscribe({
      next: (plan) => {
        localStorage.setItem('imo.planId', plan.planId);
        this.loading = false;
        void this.router.navigateByUrl('/plan');
      },
      error: (e) => {
        this.loading = false;
        this.error = `Błąd check-in: ${e?.message ?? e}`;
      },
    });
  }
}

