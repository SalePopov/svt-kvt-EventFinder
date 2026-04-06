import {Component, OnInit} from '@angular/core';
import { AuthService } from '../../services/auth';
import { UserService } from '../../services/user';
import { FormsModule } from '@angular/forms';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class DashboardComponent implements OnInit {
  user: any = null;
  passwords = { currentPassword: '', newPassword: '', confirmNewPassword: '' };
  message = '';
  isError = false;

  constructor(public auth: AuthService, private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getMyProfile().subscribe(data => {
      this.user = data;
    });
  }

  updateProfile(): void {
    this.message = '';
    this.userService.updateProfile(this.user).subscribe({
      next: (updatedUser) => {
        this.user = updatedUser;
        this.isError = false;
        this.message = 'Profil uspešno ažuriran!';
      },
      error: (err) => {
        this.isError = true;
        this.message = 'Došlo je do greške pri ažuriranju profila.';
      }
    });
  }

  changePassword(): void {
    this.message = '';
    this.userService.changePassword(this.passwords).subscribe({
      next: (response: any) => {
        this.isError = false;
        this.message = response;
        this.passwords = { currentPassword: '', newPassword: '', confirmNewPassword: '' };
      },
      error: (err: any) => {
        this.isError = true;
        this.message = err.error || 'Došlo je do greške.';
      }
    });
  }

}
