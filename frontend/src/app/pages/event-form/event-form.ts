// U src/app/pages/event-form/event-form.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import { EventService, EventDTO } from '../../services/event';

@Component({
  selector: 'app-event-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './event-form.html',
  styleUrls: ['./event-form.scss']
})
export class EventFormComponent implements OnInit {
  isEditMode = false;
  eventId: number | null = null;
  locationId: number | null = null;
  eventData: EventDTO = {
    name: '',
    address: '',
    type: '',
    date: '',
    recurrent: false,
    price: null
  };
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService
  ) {}

  ngOnInit(): void {
    const eventIdParam = this.route.snapshot.paramMap.get('id');
    const locationIdParam = this.route.snapshot.paramMap.get('locationId');

    if (eventIdParam) {
      this.isEditMode = true;
      this.eventId = Number(eventIdParam);
    } else if (locationIdParam) {
      this.isEditMode = false;
      this.locationId = Number(locationIdParam);
    }
  }

  onSubmit(): void {
    if (this.isEditMode && this.eventId) {
      this.eventService.updateEvent(this.eventId, this.eventData).subscribe({
        next: () => this.router.navigate(['/locations', this.locationId]), // Vrati se na detalje lokacije
        error: (err) => this.errorMessage = 'Greška pri ažuriranju.'
      });
    } else if (this.locationId) {
      this.eventService.createEvent(this.locationId, this.eventData).subscribe({
        next: () => this.router.navigate(['/locations', this.locationId]), // Vrati se na detalje lokacije
        error: (err) => this.errorMessage = 'Greška pri kreiranju.'
      });
    }
  }
}
