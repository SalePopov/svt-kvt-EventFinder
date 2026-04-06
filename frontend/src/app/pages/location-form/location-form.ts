import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationService, LocationDTO } from '../../services/location';

@Component({
  selector: 'app-location-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './location-form.html',
  styleUrls: ['./location-form.scss']
})
export class LocationFormComponent implements OnInit {

  // Svojstva
  isEditMode = false;
  locationId: number | null = null;
  locationData: LocationDTO = {
    name: '',
    description: '',
    address: '',
    type: ''
  };
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private locationService: LocationService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.locationId = Number(idParam);

      this.locationService.getLocationById(this.locationId).subscribe({
        next: (data) => {
          this.locationData = data;
        },
        error: (err) => {
          this.errorMessage = 'Nije moguće učitati podatke o lokaciji.';
          console.error(err);
        }
      });
    } else {
      this.isEditMode = false;
    }
  }

  onSubmit(): void {
    this.errorMessage = '';

    if (this.isEditMode && this.locationId) {
      this.locationService.updateLocation(this.locationId, this.locationData).subscribe({
        next: () => {
          this.router.navigate(['/locations', this.locationId]);
        },
        error: (err) => {
          this.errorMessage = 'Greška pri ažuriranju. Pokušajte ponovo.';
          console.error(err);
        }
      });
    } else {
      this.locationService.createLocation(this.locationData).subscribe({
        next: (newLocation) => {
          this.router.navigate(['/locations', newLocation.id]);
        },
        error: (err) => {
          this.errorMessage = 'Greška pri kreiranju. Pokušajte ponovo.';
          console.error(err);
        }
      });
    }
  }
}
