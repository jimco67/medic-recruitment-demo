import { render, screen, fireEvent, waitFor, within } from '@testing-library/angular';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CreateJobComponent } from '../create-job/create-job.component';
import { JobListComponent } from '../job-list/job-list.component';
import { JobOfferService } from '../service/job-offer.service';
import { EventBusService } from '../service/event-bus.service';
import { JobOffer } from '../service/job-offer.model';

import { EventBusTestHostComponent } from './event-bus-test-host.component';
import { EventEmitterTestHostComponent } from './event-emitter-test-host.component';

describe('Job Creation and Listing Feature', () => {
  const mockJobOffer: JobOffer = {
    id: 1,
    title: 'Développeur Frontend',
    description: 'Super mission à Paris',
    location: 'Paris',
    speciality: 'Web',
    recruiterId: 123,
    postedDate: new Date().toISOString(),
  };

  const mockJobOfferService = {
    getJobOffers: () => of([]), // Au début, la liste est vide
    createJobOffer: (job: any) => of(mockJobOffer),
  };

  // Fonction de configuration du TestBed réutilisable
  const configureTestBed = async () => {
    await TestBed.configureTestingModule({
      imports: [
        CommonModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        CreateJobComponent,
        JobListComponent,
        EventBusTestHostComponent,
        EventEmitterTestHostComponent,
      ],
      providers: [
        EventBusService,
        { provide: JobOfferService, useValue: mockJobOfferService },
      ],
    }).compileComponents();
  };

  // La fonction de test réutilisable qui simule le comportement utilisateur
  async function runUserFlow() {
    // L'utilisateur ouvre le formulaire en cliquant sur "Créer une annonce"
    fireEvent.click(screen.getByRole('button', { name: /créer une annonce/i }));

    // Attendre que le formulaire soit visible
    await waitFor(() => {
      expect(screen.getByLabelText(/titre/i)).toBeTruthy();
    });

    // L'utilisateur remplit le formulaire
    fireEvent.input(screen.getByTestId('title-input'), { target: { value: mockJobOffer.title } });
    fireEvent.input(screen.getByTestId('description-input'), { target: { value: mockJobOffer.description } });
    fireEvent.input(screen.getByTestId('location-input'), { target: { value: mockJobOffer.location } });
    fireEvent.input(screen.getByTestId('speciality-input'), { target: { value: mockJobOffer.speciality } });

    // L'utilisateur soumet le formulaire
    fireEvent.click(screen.getByRole('button', { name: /valider/i }));

    // On vérifie que la nouvelle offre apparaît dans la liste
    // On cherche la carte qui vient d'être ajoutée
    const newJobCard = await screen.findByTestId('job-card');
    expect(newJobCard).toBeTruthy();

    // On vérifie que le contenu de la carte est correct
    const titleElement = await within(newJobCard).findByTestId('job-title');
    const locationElement = await within(newJobCard).findByTestId('job-location');

    expect(titleElement.textContent).toContain(mockJobOffer.title);
    expect(locationElement.nextSibling?.textContent?.trim()).toBe(mockJobOffer.location);
  }

  describe('with Event Bus implementation', () => {
    beforeEach(async () => await configureTestBed());

    it('should display a new job in the list after creation', async () => {
      await render(EventBusTestHostComponent);
      await runUserFlow();
    });
  });

  describe('with EventEmitter implementation', () => {
    beforeEach(async () => await configureTestBed());

    // Ce test est prêt pour la future refactorisation. Il échouera pour le moment.
    it('should display a new job in the list after creation', async () => {
      await render(EventEmitterTestHostComponent);
      await runUserFlow();
    });
  });
});
