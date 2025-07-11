import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CreateJobComponent } from './create-job/create-job.component';
import { JobListComponent } from './job-list/job-list.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: true,
  imports: [CreateJobComponent, JobListComponent, RouterOutlet]
})
export class AppComponent {}

