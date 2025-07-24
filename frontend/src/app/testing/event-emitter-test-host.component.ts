import { Component } from '@angular/core';
import { JobListComponent } from '../job-list/job-list.component';

@Component({
  standalone: true,
  imports: [JobListComponent],
  template: `
    <app-job-list></app-job-list>
  `,
})
export class EventEmitterTestHostComponent {}
