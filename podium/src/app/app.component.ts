import { Component } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';
import { NavbarComponent } from './components/layout/navbar/navbar.component';
import { AuthService } from './services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, CommonModule],
  template: `
    <app-navbar *ngIf="showNavbar()"></app-navbar>
    <main class="content-container">
      <router-outlet></router-outlet>
    </main>
    <footer class="podium-footer" *ngIf="showNavbar()">
      <p>&copy; 2026 Podium Finance & Orders — Todos los derechos reservados.</p>
    </footer>
  `,
  styles: [`
    .content-container {
      min-height: calc(100vh - 130px);
      padding: 20px;
    }
    .podium-footer {
      text-align: center;
      padding: 20px;
      background: #3f51b5;
      color: white;
    }
  `]
})
export class AppComponent {
  title = 'podium';

  constructor(public authService: AuthService, private router: Router) {}

  showNavbar(): boolean {
    const currentUrl = this.router.url;
    return this.authService.isAuthenticated() && !currentUrl.includes('/login') && !currentUrl.includes('/register');
  }
}
