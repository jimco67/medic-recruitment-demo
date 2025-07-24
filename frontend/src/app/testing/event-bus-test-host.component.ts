import { Component } from '@angular/core';
import { CreateJobComponent } from '../create-job/create-job.component';
import { JobListComponent } from '../job-list/job-list.component';

@Component({
  standalone: true,
  imports: [CreateJobComponent, JobListComponent],
  template: `
    <app-create-job></app-create-job>
    <app-job-list></app-job-list>
  `,
})
export class EventBusTestHostComponent {}
