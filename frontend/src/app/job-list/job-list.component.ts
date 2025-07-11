import { Component, OnInit } from '@angular/core';
import { JobOfferService } from '../service/job-offer.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.scss'],
  standalone: true,
  imports: [CommonModule, MatCardModule]
})
export class JobListComponent implements OnInit {
  jobs: any[] = [];

  constructor(private jobOfferService: JobOfferService) {}

  ngOnInit(): void {
    this.loadJobs();
  }

  loadJobs() {
    this.jobOfferService.getJobOffers().subscribe({
      next: data => {
        this.jobs = data;
      },
      error: err => {
        console.error('Erreur lors de la récupération des jobs:', err);
      }
    });
  }
}
