import { Routes } from '@angular/router';
import { OnboardingComponent } from './pages/onboarding.component';
import { PlanComponent } from './pages/plan.component';
import { CheckInComponent } from './pages/checkin.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'onboarding' },
  { path: 'onboarding', component: OnboardingComponent },
  { path: 'plan', component: PlanComponent },
  { path: 'checkin', component: CheckInComponent },
  { path: '**', redirectTo: 'onboarding' },
];

