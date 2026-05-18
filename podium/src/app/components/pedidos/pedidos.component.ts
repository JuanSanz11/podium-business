import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PedidoService } from '../../services/pedido.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-pedidos',
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
    MatChipsModule,
    RouterModule
  ],
  templateUrl: './pedidos.component.html',
  styleUrls: ['../facturas/facturas.component.scss']
})
export class PedidosComponent implements OnInit, OnDestroy {
  pedidos: any[] = [];
  displayedColumns: string[] = ['id', 'producto', 'cantidad', 'estado', 'fechaCreacion'];
  pedidoForm: FormGroup;
  loading: boolean = true;
  pollingInterval: any;

  constructor(private pedidoService: PedidoService, private fb: FormBuilder) {
    this.pedidoForm = this.fb.group({
      producto: ['', Validators.required],
      cantidad: [1, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.loadPedidos();
    // Simple polling para actualizar el estado del procesamiento asíncrono
    this.pollingInterval = setInterval(() => {
      this.loadPedidos();
    }, 5000); // 5 seconds
  }

  ngOnDestroy(): void {
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }
  }

  loadPedidos() {
    this.pedidoService.getPedidos().subscribe({
      next: (data) => {
        this.pedidos = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  onSubmit() {
    if (this.pedidoForm.valid) {
      this.pedidoService.createPedido(this.pedidoForm.value).subscribe({
        next: (newPedido) => {
          this.pedidos = [...this.pedidos, newPedido];
          this.pedidoForm.reset({ cantidad: 1 });
        },
        error: (err) => console.error(err)
      });
    }
  }
}
