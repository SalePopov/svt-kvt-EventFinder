import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AccountRequest {
  id: number;
  email: string;
  address: string;
  status: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient) { }

  getPendingRequests(): Observable<AccountRequest[]> {
    return this.http.get<AccountRequest[]>(`${this.apiUrl}/registration-requests/pending`);
  }

  approveRequest(requestId: number): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/registration-requests/approve/${requestId}`, {});
  }

  rejectRequest(requestId: number): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/registration-requests/reject/${requestId}`, {});
  }
}
