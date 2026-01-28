import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <div class="container">
      <div class="nav">
        <div>
          <div class="brand">IMO Diet Coach</div>
          <div class="muted" style="font-size: 12px;">MVP: onboarding → plan → check-in</div>
        </div>
        <div style="display:flex; gap:10px;">
          <a routerLink="/onboarding">Onboarding</a>
          <a routerLink="/plan">Plan</a>
          <a routerLink="/checkin">Check-in</a>
        </div>
      </div>
      <router-outlet />
      <div class="muted" style="margin-top: 18px; font-size: 12px;">
        Uwaga: to nie jest porada lekarska. Jeśli masz leki/choroby współistniejące, konsultuj zmiany z lekarzem/dietetykiem.
      </div>
    </div>
  `,
})
export class AppComponent {}

