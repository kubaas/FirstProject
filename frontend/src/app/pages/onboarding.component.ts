import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { ActivityLevel, Budget, DietStyle, Goal, OnboardingRequest } from '../api.models';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="card">
      <h2 style="margin:0 0 6px;">Onboarding (IMO)</h2>
      <div class="muted" style="margin-bottom: 14px;">
        Wypełnij profil — wygeneruję prosty plan na 7 dni (3 posiłki dziennie).
      </div>

      <form [formGroup]="form" (ngSubmit)="submit()">
        <div class="grid">
          <div class="col-6">
            <label>Imię / nazwa</label>
            <input formControlName="displayName" placeholder="np. Jakub" />
            <div class="error" *ngIf="form.controls.displayName.touched && form.controls.displayName.invalid">
              Podaj nazwę.
            </div>
          </div>
          <div class="col-6">
            <label>Styl diety</label>
            <select formControlName="dietStyle">
              <option [ngValue]="'OMNIVORE'">Omnivore</option>
              <option [ngValue]="'VEGETARIAN'">Vegetarian</option>
            </select>
          </div>

          <div class="col-4">
            <label>Wzrost (cm)</label>
            <input type="number" formControlName="heightCm" />
          </div>
          <div class="col-4">
            <label>Masa (kg)</label>
            <input type="number" formControlName="weightKg" />
          </div>
          <div class="col-4">
            <label>Aktywność</label>
            <select formControlName="activityLevel">
              <option [ngValue]="'LOW'">Niska</option>
              <option [ngValue]="'MODERATE'">Umiarkowana</option>
              <option [ngValue]="'HIGH'">Wysoka</option>
            </select>
          </div>

          <div class="col-6">
            <label>Cel</label>
            <select formControlName="goal">
              <option [ngValue]="'FAT_LOSS'">Redukcja</option>
              <option [ngValue]="'MAINTENANCE'">Utrzymanie</option>
            </select>
          </div>
          <div class="col-6">
            <label>Budżet</label>
            <select formControlName="budget">
              <option [ngValue]="'LOW'">Niski</option>
              <option [ngValue]="'MEDIUM'">Średni</option>
              <option [ngValue]="'HIGH'">Wysoki</option>
            </select>
          </div>

          <div class="col-6">
            <label>Maks. czas gotowania (min)</label>
            <input type="number" formControlName="cookTimeMinutes" />
          </div>
          <div class="col-6">
            <label>Alergeny (CSV, np. gluten, lactose)</label>
            <input formControlName="allergensCsv" placeholder="np. gluten,lactose" />
          </div>

          <div class="col-12" style="display:flex; gap:10px; margin-top: 8px;">
            <button class="btn" type="submit" [disabled]="loading || form.invalid">
              {{ loading ? 'Generuję...' : 'Wygeneruj plan' }}
            </button>
            <button class="btn secondary" type="button" (click)="useDemo()">Użyj danych demo</button>
          </div>

          <div class="col-12" *ngIf="error" style="margin-top: 10px;">
            <div class="error">{{ error }}</div>
          </div>
        </div>
      </form>
    </div>
  `,
})
export class OnboardingComponent {
  loading = false;
  error: string | null = null;

  form = this.fb.group({
    displayName: ['', [Validators.required]],
    heightCm: [180, [Validators.required, Validators.min(120), Validators.max(230)]],
    weightKg: [90, [Validators.required, Validators.min(30), Validators.max(250)]],
    activityLevel: ['MODERATE' as ActivityLevel, [Validators.required]],
    goal: ['FAT_LOSS' as Goal, [Validators.required]],
    budget: ['MEDIUM' as Budget, [Validators.required]],
    cookTimeMinutes: [25, [Validators.required, Validators.min(5), Validators.max(120)]],
    dietStyle: ['OMNIVORE' as DietStyle, [Validators.required]],
    allergensCsv: [''],
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly api: ApiService,
    private readonly router: Router
  ) {}

  useDemo(): void {
    this.form.patchValue({
      displayName: 'Demo',
      heightCm: 180,
      weightKg: 95,
      activityLevel: 'MODERATE',
      goal: 'FAT_LOSS',
      budget: 'MEDIUM',
      cookTimeMinutes: 25,
      dietStyle: 'OMNIVORE',
      allergensCsv: '',
    });
  }

  submit(): void {
    this.error = null;
    if (this.form.invalid) return;

    const v = this.form.getRawValue();
    const req: OnboardingRequest = {
      displayName: v.displayName!,
      heightCm: v.heightCm!,
      weightKg: v.weightKg!,
      activityLevel: v.activityLevel!,
      goal: v.goal!,
      mealsPerDay: 3,
      budget: v.budget!,
      cookTimeMinutes: v.cookTimeMinutes!,
      dietStyle: v.dietStyle!,
      allergens: (v.allergensCsv ?? '')
        .split(',')
        .map((s) => s.trim())
        .filter((s) => s.length > 0),
    };

    this.loading = true;
    this.api.onboarding(req).subscribe({
      next: (resp) => {
        localStorage.setItem('imo.userId', resp.userId);
        localStorage.setItem('imo.planId', resp.mealPlan.planId);
        this.loading = false;
        void this.router.navigateByUrl('/plan');
      },
      error: (e) => {
        this.loading = false;
        this.error = `Błąd onboardingu: ${e?.message ?? e}`;
      },
    });
  }
}

