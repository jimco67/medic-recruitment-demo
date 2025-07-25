import { Routes } from '@angular/router';
import { JobListComponent } from './job-list/job-list.component';
import { CreateJobComponent } from './create-job/create-job.component';

export const appRoutes: Routes = [
  { path: '', component: JobListComponent, title: 'Job List - Medic Recruitment' },
  { path: 'create-job', component: CreateJobComponent, title: 'Create Job - Medic Recruitment' },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];