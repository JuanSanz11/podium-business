import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FinanceService } from '../../services/finance.service';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { NgChartsModule } from 'ng2-charts';
import { ChartData, ChartType } from 'chart.js';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule, RouterModule, NgChartsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  reporte: any = null;
  totalDeudas: number = 0;
  loading: boolean = true;

  // Doughnut Chart
  public doughnutChartLabels: string[] = [ 'Ingresos', 'Gastos', 'Ahorros', 'Deudas' ];
  public doughnutChartData: ChartData<'doughnut'> = {
    labels: this.doughnutChartLabels,
    datasets: [
      { data: [0, 0, 0, 0], backgroundColor: ['#3f51b5', '#f44336', '#4caf50', '#ff9800'] }
    ]
  };
  public doughnutChartType: ChartType = 'doughnut';

  constructor(
    private financeService: FinanceService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadReporte();
  }

  loadReporte() {
    forkJoin({
      reporte: this.financeService.getReporte(),
      deudas: this.financeService.getDeudas()
    }).subscribe({
      next: ({ reporte, deudas }) => {
        this.reporte = reporte;
        this.totalDeudas = deudas.reduce((acc, curr) => acc + (curr.montoOriginal || 0), 0);
        
        this.doughnutChartData = {
          labels: this.doughnutChartLabels,
          datasets: [
            { 
              data: [
                reporte.totalIngresos || 0, 
                reporte.totalGastos || 0, 
                reporte.totalAhorro || 0,
                this.totalDeudas
              ],
              backgroundColor: ['#3f51b5', '#f44336', '#4caf50', '#ff9800'],
              hoverBackgroundColor: ['#303f9f', '#d32f2f', '#388e3c', '#f57c00']
            }
          ]
        };
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
