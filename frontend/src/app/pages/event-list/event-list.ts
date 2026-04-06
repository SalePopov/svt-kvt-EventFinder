import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventData, EventService, EventFilters } from '../../services/event';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './event-list.html',
  styleUrls: ['./event-list.scss']
})
export class EventListComponent implements OnInit {

  events: EventData[] = [];
  isLoading = true;
  error: string | null = null;

  filters: EventFilters = {
    today: true
  };

  constructor(private eventService: EventService) { }

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.isLoading = true;
    this.error = null;
    this.eventService.filterEvents(this.filters).subscribe({
      next: (data) => {
        this.events = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Došlo je do greške pri učitavanju događaja.';
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  onFilterChange(): void {
    this.loadEvents();
  }

  resetFilters(): void {
    this.filters = { today: false };
    this.loadEvents();
  }
}
