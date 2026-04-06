import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-register-request',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register-request.html',
  styleUrls: ['./register-request.scss']
})
export class RegisterRequestComponent {

  requestData = {
    email: '',
    password: '',
    address: ''
  };
  message = '';
  isError = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.authService.registerRequest(this.requestData).subscribe({
      next: () => {
        this.isError = false;
        this.message = 'Zahtev uspešno poslat! Bićete obavešteni kada administrator odobri vaš nalog.';
        setTimeout(() => this.router.navigate(['/login']), 3000);
      },
      error: (err) => {
        this.isError = true;
        this.message = err.error || 'Došlo je do greške.';
        console.error(err);
      }
    });
  }
}
