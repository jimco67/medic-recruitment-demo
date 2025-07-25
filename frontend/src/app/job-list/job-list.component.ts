import { Component, OnInit, OnDestroy } from '@angular/core';
import { JobOfferService } from '../service/job-offer.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { EventBusService } from '../service/event-bus.service';
import { Subscription } from 'rxjs';
import { JobOffer } from '../service/job-offer.model';
// Pour l'implémentation EventEmitter, décommentez la ligne suivante :
// import { CreateJobComponent } from '../create-job/create-job.component';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.scss'],
  standalone: true,
  // Pour EventEmitter, ajoutez 'CreateJobComponent' au tableau 'imports' :
  // imports: [CommonModule, MatCardModule, CreateJobComponent]
  imports: [CommonModule, MatCardModule]
})
export class JobListComponent implements OnInit, OnDestroy {
  jobs: JobOffer[] = [];
  private jobCreatedSubscription?: Subscription;

  constructor(
    private jobOfferService: JobOfferService,
    private eventBusService: EventBusService
    // Pour EventEmitter, supprimez 'private eventBusService: EventBusService'
  ) {}

  ngOnInit(): void {
    this.loadJobs();
    // La ligne suivante est pour l'Event Bus. Commentez-la pour EventEmitter.
    this.jobCreatedSubscription = this.eventBusService.on('jobCreated', (newJob: JobOffer) => {
      console.log('Job created event received, adding new job to the list', newJob);
      this.jobs.unshift(newJob);
    });
  }

  ngOnDestroy(): void {
    // La méthode ngOnDestroy est pour l'Event Bus. Commentez-la pour EventEmitter.
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

  /*
  // Pour l'implémentation EventEmitter, décommentez cette méthode.
  // Elle sera appelée par le template lorsque l'enfant émettra l'événement.
  onJobCreated(newJob: JobOffer) {
    console.log('Événement reçu du composant enfant:', newJob);
    this.jobs.unshift(newJob);
  }
  */
}
