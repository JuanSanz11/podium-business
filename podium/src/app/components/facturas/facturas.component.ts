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
  selector: 'app-facturas',
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
  templateUrl: './facturas.component.html',
  styleUrls: ['./facturas.component.scss']
})
export class FacturasComponent implements OnInit {
  facturas: any[] = [];
  displayedColumns: string[] = ['id', 'descripcion', 'categoria', 'monto', 'fechaEmision'];
  facturaForm: FormGroup;
  loading: boolean = true;

  constructor(private financeService: FinanceService, private fb: FormBuilder) {
    this.facturaForm = this.fb.group({
      descripcion: ['', Validators.required],
      monto: ['', [Validators.required, Validators.min(0.01)]],
      fechaEmision: ['', Validators.required],
      categoria: ['']
    });
  }

  ngOnInit(): void {
    this.loadFacturas();
  }

  loadFacturas() {
    this.financeService.getFacturas().subscribe({
      next: (data) => {
        this.facturas = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  onSubmit() {
    if (this.facturaForm.valid) {
      this.financeService.createFactura(this.facturaForm.value).subscribe({
        next: (newFactura) => {
          this.facturas = [...this.facturas, newFactura];
          this.facturaForm.reset();
        },
        error: (err) => console.error(err)
      });
    }
  }
}
