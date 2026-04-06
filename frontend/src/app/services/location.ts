import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LocationData {
  id: number;
  name: string;
  description: string;
  address: string;
  type: string;
  totalRating: number;
  createdAt: string;
}

export interface LocationDTO {
  name: string;
  description: string;
  address: string;
  type: string;
}

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private apiUrl = 'http://localhost:8080/api/locations';

  constructor(private http: HttpClient) { }

  getAllLocations(): Observable<LocationData[]> {
    return this.http.get<LocationData[]>(this.apiUrl);
  }

  getLocationById(id: number | string): Observable<LocationData> {
    return this.http.get<LocationData>(`${this.apiUrl}/${id}`);
  }

  searchLocations(searchTerm: string): Observable<LocationData[]> {
    if (!searchTerm.trim()) {
      return this.getAllLocations();
    }

    const options = { params: new HttpParams().set('query', searchTerm) };
    return this.http.get<LocationData[]>(`${this.apiUrl}/search`, options);
  }

  createLocation(locationData: LocationDTO): Observable<LocationData> {
    return this.http.post<LocationData>(this.apiUrl, locationData);
  }

  updateLocation(id: number, locationData: LocationDTO): Observable<LocationData> {
    return this.http.put<LocationData>(`${this.apiUrl}/${id}`, locationData);
  }

  deleteLocation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
