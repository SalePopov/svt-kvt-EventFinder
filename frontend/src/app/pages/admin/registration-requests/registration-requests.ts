import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService, AccountRequest } from '../../../services/admin';

@Component({
  selector: 'app-registration-requests',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './registration-requests.html',
  styleUrls: ['./registration-requests.scss']
})
export class RegistrationRequestsComponent implements OnInit {

  requests: AccountRequest[] = [];
  isLoading = true;
  message: string | null = null;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.isLoading = true;
    this.adminService.getPendingRequests().subscribe({
      next: (data) => {
        this.requests = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Greška pri učitavanju zahteva:', err);
        this.message = 'Nije moguće učitati zahteve.';
        this.isLoading = false;
      }
    });
  }

  approve(requestId: number): void {
    this.adminService.approveRequest(requestId).subscribe({
      next: () => {
        this.requests = this.requests.filter(req => req.id !== requestId);
        this.message = `Zahtev #${requestId} je uspešno odobren.`;
        this.loadRequests();
      },
      error: (err) => {
        console.error(`Greška pri odobravanju zahteva #${requestId}:`, err);
        this.message = `Greška pri odobravanju zahteva #${requestId}.`;
      }
    });
  }

  reject(requestId: number): void {
    this.adminService.rejectRequest(requestId).subscribe({
      next: () => {
        this.requests = this.requests.filter(req => req.id !== requestId);
        this.message = `Zahtev #${requestId} je odbijen.`;
        this.loadRequests();
      },
      error: (err) => {
        console.error(`Greška pri odbijanju zahteva #${requestId}:`, err);
        this.message = `Greška pri odbijanju zahteva #${requestId}.`;
      }
    });
  }
}
