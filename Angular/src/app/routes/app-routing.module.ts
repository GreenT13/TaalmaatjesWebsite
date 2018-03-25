import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AuthGuardService} from './auth-guard.service';
import {LoginComponent} from '../components/login/login.component';
import {VolunteerOverviewComponent} from "../components/volunteer/overview/volunteer.overview.component";
import {VolunteerDetailComponent} from "../components/volunteer/detail/volunteer.detail.component";
import {VolunteerAddComponent} from "../components/volunteer/add/volunteer.add.component";
import {TaskOverviewComponent} from "../components/task/overview/task.overview.component";
import {TaskAddView} from "../components/task/add/task.add.view";
import {TaskEditView} from "../components/task/add/task.edit.view";
import {TaskDetailView} from "../components/task/detail/task.detail.view";

const appRoutes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'volunteer', canActivate: [AuthGuardService], component: VolunteerOverviewComponent },
  { path: 'volunteer/add', canActivate: [AuthGuardService], component: VolunteerAddComponent },
  { path: 'volunteer/:volunteerExtId', canActivate: [AuthGuardService], component: VolunteerDetailComponent },
  { path: 'task', canActivate: [AuthGuardService], component: TaskOverviewComponent},
  { path: 'task/add', canActivate: [AuthGuardService], component: TaskAddView},
  { path: 'task/:taskExtId', canActivate: [AuthGuardService], component: TaskDetailView },
  { path: 'task/:taskExtId/edit', canActivate: [AuthGuardService], component: TaskEditView },
      ];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
