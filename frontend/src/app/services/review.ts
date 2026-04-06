import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ReviewDTO {
  eventId: number;
  performance?: number;
  soundAndLighting?: number;
  venue?: number;
  overallImpression?: number;
  comment?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private apiUrl = 'http://localhost:8080/api/reviews';

  constructor(private http: HttpClient) { }

  createReview(reviewData: ReviewDTO): Observable<any> {
    return this.http.post(this.apiUrl, reviewData);
  }
}
