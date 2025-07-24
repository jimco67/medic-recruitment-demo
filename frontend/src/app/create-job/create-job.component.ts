import { Component, OnInit } from '@angular/core';
// Pour l'implémentation EventEmitter, décommentez les lignes suivantes :
// import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JobOfferService } from '../service/job-offer.service';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { Observable, of } from 'rxjs';
import { map, startWith, switchMap } from 'rxjs/operators';
import { EventBusService } from '../service/event-bus.service';
import { EventData } from '../service/event.class';
import { JobOffer } from '../service/job-offer.model';

@Component({
  selector: 'app-create-job',
  templateUrl: './create-job.component.html',
  styleUrls: ['./create-job.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatAutocompleteModule
  ]
})
export class CreateJobComponent implements OnInit {
  // Pour l'implémentation EventEmitter, décommentez la ligne suivante :
  // @Output() jobCreated = new EventEmitter<JobOffer>();

  showForm: boolean = false;
  buttonText: string = "Créer une annonce";
  jobForm: FormGroup;
  citySuggestions$: Observable<string[]>;

  constructor(
    private readonly http: HttpClient,
    private jobOfferService: JobOfferService,
    private fb: FormBuilder,
    private eventBusService: EventBusService // Pour EventEmitter, supprimez 'private eventBusService: EventBusService'
  ) {
    this.jobForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      location: ['', Validators.required],
      speciality: ['', Validators.required]
    });

    this.citySuggestions$ = this.jobForm.get('location')!.valueChanges.pipe(
      startWith(''),
      switchMap(value => (value && value.length >= 2) ? this.fetchCities(value) : of([]))
    );
  }

  ngOnInit(): void {}

  openCloseForm() {
    this.showForm = !this.showForm;
    this.buttonText = this.showForm ? 'Annuler' : 'Créer une annonce';
    if (!this.showForm) {
      this.jobForm.reset();
    }
  }

  submitForm() {
    if (this.jobForm.valid) {
      const jobData = { ...this.jobForm.value, recruiterId: 123 };
      console.log('Annonce créée :', jobData);

      this.jobOfferService.createJobOffer(jobData).subscribe({
        next: (response: JobOffer) => {
          console.log('Succès de la création du job:', response);
          // Ligne pour l'Event Bus (actuelle)
          this.eventBusService.emit(new EventData('jobCreated', response));

          // Pour l'implémentation EventEmitter, commentez la ligne ci-dessus et décommentez la suivante :
          // this.jobCreated.emit(response);

          this.openCloseForm();
        },
        error: err => {
          console.error('Erreur lors de la création du job:', err);
        }
      });
    }
  }

  private fetchCities(query: string): Observable<string[]> {
    return this.http.get<any[]>(`https://geo.api.gouv.fr/communes?nom=${query}&fields=nom&boost=population&limit=5`)
      .pipe(
        map(data => data.map(ville => ville.nom))
      );
  }
}
