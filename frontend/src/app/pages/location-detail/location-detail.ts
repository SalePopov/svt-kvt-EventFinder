import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { LocationData, LocationService } from '../../services/location';
import { EventData, EventService } from '../../services/event';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth';
import { ReviewService, ReviewDTO } from '../../services/review';

@Component({
  selector: 'app-location-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './location-detail.html',
  styleUrls: ['./location-detail.scss']
})
export class LocationDetailComponent implements OnInit {
  location: LocationData | null = null;
  events: EventData[] = [];
  isLoading = true;
  error: string | null = null;

  showReviewFormForEventId: number | null = null;
  reviewData: ReviewDTO = { eventId: 0 };
  reviewSuccessMessage = '';
  reviewErrorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private locationService: LocationService,
    private eventService: EventService,
    public auth: AuthService,
    private reviewService: ReviewService
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.locationService.getLocationById(id).subscribe({
        next: (locationData) => {
          this.location = locationData;
          this.eventService.getEventsByLocationId(id).subscribe({
            next: (eventData) => {
              this.events = eventData;
              this.isLoading = false;
            },
            error: (err) => this.handleError(err)
          });
        },
        error: (err) => this.handleError(err)
      });
    }
  }

  private handleError(err: any) {
    this.error = 'Došlo je do greške.';
    this.isLoading = false;
    console.error(err);
  }

  toggleReviewForm(eventId: number): void {
    if (this.showReviewFormForEventId === eventId) {
      this.showReviewFormForEventId = null;
    } else {
      this.showReviewFormForEventId = eventId;
      this.reviewData = { eventId: eventId };
      this.reviewSuccessMessage = '';
      this.reviewErrorMessage = '';
    }
  }

  submitReview(): void {
    this.reviewService.createReview(this.reviewData).subscribe({
      next: () => {
        this.reviewSuccessMessage = 'Hvala na utisku!';
        this.reviewErrorMessage = '';
        this.showReviewFormForEventId = null;
      },
      error: (err) => {
        this.reviewErrorMessage = 'Došlo je do greške. Pokušajte ponovo.';
        this.reviewSuccessMessage = '';
        console.error(err);
      }
    });
  }
  deleteEvent(eventId: number): void {
    if (confirm('Da li ste sigurni da želite da obrišete ovaj događaj?')) {
      this.eventService.deleteEvent(eventId).subscribe({
        next: () => {
          this.events = this.events.filter(event => event.id !== eventId);
        },
        error: (err) => {
          this.error = 'Greška pri brisanju događaja.';
          console.error(err);
        }
      });
    }
  }
}
