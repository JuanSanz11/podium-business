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
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-deudas',
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
    RouterModule
  ],
  templateUrl: './deudas.component.html',
  styleUrls: ['../facturas/facturas.component.scss']
})
export class DeudasComponent implements OnInit {
  deudas: any[] = [];
  displayedColumns: string[] = ['id', 'descripcion', 'acreedor', 'montoOriginal', 'fechaVencimiento'];
  deudaForm: FormGroup;
  loading: boolean = true;

  constructor(private financeService: FinanceService, private fb: FormBuilder) {
    this.deudaForm = this.fb.group({
      descripcion: ['', Validators.required],
      acreedor: ['', Validators.required],
      montoOriginal: ['', [Validators.required, Validators.min(0.01)]],
      fechaVencimiento: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadDeudas();
  }

  loadDeudas() {
    this.financeService.getDeudas().subscribe({
      next: (data) => {
        this.deudas = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  onSubmit() {
    if (this.deudaForm.valid) {
      this.financeService.createDeuda(this.deudaForm.value).subscribe({
        next: (newDeuda) => {
          this.deudas = [...this.deudas, newDeuda];
          this.deudaForm.reset();
        },
        error: (err) => console.error(err)
      });
    }
  }
}
