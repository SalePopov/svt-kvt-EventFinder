import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
      CommonModule,
      FormsModule
  ],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  };

  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    this.errorMessage = '';
    this.authService.login(this.credentials).subscribe({
      next: () => {
        console.log('Login uspešan!');
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        console.error('Greška pri loginu:', err);
        this.errorMessage = 'Pogrešan email ili lozinka.';
      }
    });
  }
}
