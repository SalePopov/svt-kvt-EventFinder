import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs'; // Importujte tap
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'; // Osnovni URL za auth endpoint-e

  constructor(private http: HttpClient, private router: Router) { }

  // Metoda za login
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
      tap((response: any) => {
        // Kada dobijemo odgovor, čuvamo token
        localStorage.setItem('token', response.token);
      })
    );
  }

  // Metoda za logout
  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  // Pomoćna metoda za dobijanje tokena
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Provera da li je korisnik ulogovan (da li token postoji)
  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }
}