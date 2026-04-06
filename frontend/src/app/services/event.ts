import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EventData {
  id: number;
  name: string;
  address: string;
  type: string;
  date: string;
  recurrent: boolean;
  price: number | null;
}

export interface EventFilters {
  type?: string;
  locationName?: string;
  address?: string;
  maxPrice?: number;
  today?: boolean;
}

export interface EventDTO {
  name: string;
  address: string;
  type: string;
  date: string;
  recurrent: boolean;
  price?: number | null;
}

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getAllEvents(): Observable<EventData[]> {
    return this.http.get<EventData[]>(`${this.apiUrl}/events`);
  }

  getEventsByLocationId(locationId: number | string): Observable<EventData[]> {
    return this.http.get<EventData[]>(`${this.apiUrl}/locations/${locationId}/events`);
  }

  getEventById(id: number | string): Observable<EventData> {
    return this.http.get<EventData>(`${this.apiUrl}/events/${id}`);
  }

  filterEvents(filters: EventFilters): Observable<EventData[]> {
    let params = new HttpParams();

    if (filters.type) {
      params = params.set('type', filters.type);
    }
    if (filters.locationName) {
      params = params.set('locationName', filters.locationName);
    }
    if (filters.address) {
      params = params.set('address', filters.address);
    }
    if (filters.maxPrice != null) { // Proveravamo i za 0
      params = params.set('maxPrice', filters.maxPrice.toString());
    }
    if (filters.today) {
      params = params.set('today', filters.today.toString());
    }

    return this.http.get<EventData[]>(`${this.apiUrl}/events/filter`, { params });
  }

  createEvent(locationId: number, eventData: EventDTO): Observable<EventData> {
    return this.http.post<EventData>(`${this.apiUrl}/locations/${locationId}/events`, eventData);
  }

  updateEvent(eventId: number, eventData: EventDTO): Observable<EventData> {
    return this.http.put<EventData>(`${this.apiUrl}/events/${eventId}`, eventData);
  }

  deleteEvent(eventId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/events/${eventId}`);
  }
}
