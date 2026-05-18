import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FinanceService } from '../../services/finance.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTableModule } from '@angular/material/table';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-gastos',
  standalone: true,
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    MatCardModule, 
    MatButtonModule, 
    MatIconModule, 
    MatInputModule, 
    MatFormFieldModule, 
    MatTableModule,
    MatSlideToggleModule,
    RouterModule
  ],
  templateUrl: './gastos.component.html',
  styleUrls: ['../facturas/facturas.component.scss']
})
export class GastosComponent implements OnInit {
  gastos: any[] = [];
  displayedColumns: string[] = ['id', 'descripcion', 'categoria', 'monto', 'tipo', 'fecha'];
  gastoForm: FormGroup;
  loading: boolean = true;

  constructor(private financeService: FinanceService, private fb: FormBuilder) {
    this.gastoForm = this.fb.group({
      descripcion: ['', Validators.required],
      monto: ['', [Validators.required, Validators.min(0.01)]],
      fecha: ['', Validators.required],
      categoria: [''],
      ahorroOInversion: [false]
    });
  }

  ngOnInit(): void {
    this.loadGastos();
  }

  loadGastos() {
    this.financeService.getGastos().subscribe({
      next: (data) => {
        this.gastos = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  onSubmit() {
    if (this.gastoForm.valid) {
      this.financeService.createGasto(this.gastoForm.value).subscribe({
        next: (newGasto) => {
          this.gastos = [...this.gastos, newGasto];
          this.gastoForm.reset({ ahorroOInversion: false });
        },
        error: (err) => console.error(err)
      });
    }
  }
}
