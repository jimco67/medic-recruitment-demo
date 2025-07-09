import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {JobOfferService} from '../service/job-offer.service';

@Component({
  selector: 'app-create-job',
  templateUrl: './create-job.component.html',
  styleUrls: ['./create-job.component.scss'],
  standalone: false
})
export class CreateJobComponent {
  showForm: boolean = false;
  buttonText: string = "Créer une annonce";

  form = {
    title: '',
    description: '',
    location: '',
    specialty: ''
  };

  suggestions: string[] = [];

  constructor(private readonly http: HttpClient, private jobOfferService: JobOfferService) {}


  openCloseForm() {
    this.showForm = !this.showForm;
    if(this.showForm)
      this.buttonText = 'Annuler';
    else
      this.buttonText = 'Créer une annonce';
  }

  closeForm() {
    this.showForm = false;
    this.suggestions = [];
  }

  submitForm() {
    const jobData = {
      title: this.form.title,
      description: this.form.description,
      location: this.form.location,
      specialty: this.form.specialty, // Corrected typo from "speciality" to "specialty"
      recruiterId: 1, // Always set to 1
    };

    console.log('Annonce créée :', jobData);

    this.jobOfferService.createJobOffer(jobData).subscribe({
      next: response => {
        console.log('Succès de la création du job:', response);
        this.closeForm();
      },
      error: err => {
        console.error('Erreur lors de la création du job:', err);
      }
    });
  }


  onLocationInput(event: any) {
    const query = event.target.value;
    if (query.length >= 2) {
      this.http.get<any[]>(`https://geo.api.gouv.fr/communes?nom=${query}&fields=nom&boost=population&limit=5`)
        .subscribe(data => {
          this.suggestions = data.map(ville => ville.nom);
        });
    } else {
      this.suggestions = [];
    }
  }

  selectSuggestion(ville: string) {
    this.form.location = ville;
    this.suggestions = [];
  }
}
