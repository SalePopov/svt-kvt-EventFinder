import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LocationData, LocationService } from '../../services/location';
import {FormsModule} from '@angular/forms';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-location-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './location-list.html',
  styleUrls: ['./location-list.scss']
})
export class LocationListComponent implements OnInit {

  locations: LocationData[] = [];
  isLoading = true;
  error: string | null = null;

  searchTerm: string = '';

  constructor(private locationService: LocationService, public auth: AuthService) { }

  ngOnInit(): void {
    this.loadLocations();
  }

  loadLocations(): void {
    this.isLoading = true;
    this.error = null;
    this.locationService.searchLocations(this.searchTerm).subscribe({
      next: (data) => {
        this.locations = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Došlo je do greške pri pretrazi.';
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  deleteLocation(id: number): void {
    if (confirm('Da li ste sigurni da želite da obrišete ovo mesto?')) {
      this.locationService.deleteLocation(id).subscribe({
        next: () => {
          this.locations = this.locations.filter(loc => loc.id !== id);
        },
        error: (err) => {
          console.error('Greška pri brisanju:', err);
        }
      });
    }
  }
}
