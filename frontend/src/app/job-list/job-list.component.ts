import { Component, OnInit, OnDestroy } from '@angular/core';
import { JobOfferService } from '../service/job-offer.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { EventBusService } from '../service/event-bus.service';
import { Subscription } from 'rxjs';
import { JobOffer } from '../service/job-offer.model';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class JobListComponent implements OnInit, OnDestroy {
  jobs: JobOffer[] = [];
  private jobCreatedSubscription?: Subscription;

  constructor(
    private jobOfferService: JobOfferService,
    private eventBusService: EventBusService
  ) {}

  ngOnInit(): void {
    this.loadJobs();
    this.jobCreatedSubscription = this.eventBusService.on('jobCreated', (newJob: JobOffer) => {
      console.log('Job created event received, adding new job to the list', newJob);
      this.jobs.unshift(newJob);
    });
  }

  ngOnDestroy(): void {
    if (this.jobCreatedSubscription) {
      this.jobCreatedSubscription.unsubscribe();
    }
  }

  loadJobs() {
    this.jobOfferService.getJobOffers().subscribe({
      next: (data: JobOffer[]) => {
        this.jobs = data;
      },
      error: err => {
        console.error('Erreur lors de la récupération des jobs:', err);
      }
    });
  }
}
