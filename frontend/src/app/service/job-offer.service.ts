import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JobOfferService {
  private apiUrl = 'http://localhost:8080/job-offers/create';

  constructor(private http: HttpClient) {}

  createJobOffer(jobData: any): Observable<any> {
    return this.http.post(this.apiUrl, jobData);
  }
}
