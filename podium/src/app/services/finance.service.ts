import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FinanceService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  // Facturas
  getFacturas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/facturas`);
  }

  createFactura(factura: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/facturas`, factura);
  }

  // Gastos
  getGastos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/gastos`);
  }

  createGasto(gasto: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/gastos`, gasto);
  }

  // Deudas
  getDeudas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/deudas`);
  }

  createDeuda(deuda: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/deudas`, deuda);
  }

  // Reporte
  getReporte(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/gastos/reporte`);
  }
}
