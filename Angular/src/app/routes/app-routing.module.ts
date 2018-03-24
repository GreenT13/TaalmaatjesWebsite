import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AuthGuardService} from './auth-guard.service';
import {LoginComponent} from '../components/login/login.component';
import {VolunteerOverviewComponent} from "../components/volunteer/overview/volunteer.overview.component";
import {VolunteerDetailComponent} from "../components/volunteer/detail/volunteer.detail.component";
import {VolunteerAddComponent} from "../components/volunteer/add/volunteer.add.component";
import {VolunteerEditActiveComponent} from "../components/volunteer/detail/active/volunteer.edit.active.component";
import {VolunteerAddActiveComponent} from "../components/volunteer/detail/active/volunteer.add.active.component";
import {TaskOverviewComponent} from "../components/task/overview/task.overview.component";
import {TaskAddComponent} from "../components/task/add/task.add.component";
import {TaskDetailComponent} from "../components/task/detail/task.detail.component";
import {TaskEditComponent} from "../components/task/add/task.edit.component";

const appRoutes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'volunteer', canActivate: [AuthGuardService], component: VolunteerOverviewComponent },
  { path: 'volunteer/add', canActivate: [AuthGuardService], component: VolunteerAddComponent },
  { path: 'volunteer/:volunteerExtId', canActivate: [AuthGuardService], component: VolunteerDetailComponent, children: [
      { path: 'active/:volunteerInstanceExtId', component: VolunteerEditActiveComponent },
      { path: 'active', component: VolunteerAddActiveComponent }]},
  { path: 'task', canActivate: [AuthGuardService], component: TaskOverviewComponent},
  { path: 'task/add', canActivate: [AuthGuardService], component: TaskAddComponent},
  { path: 'task/:taskExtId', canActivate: [AuthGuardService], component: TaskDetailComponent },
  { path: 'task/:taskExtId/edit', canActivate: [AuthGuardService], component: TaskEditComponent },
      ];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
