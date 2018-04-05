import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AuthGuardService} from './auth-guard.service';
import {LoginComponent} from '../components/view/login/login.component';
import {StudentAddView} from "../components/view/student/add/student.add.view";
import {StudentDetailView} from "../components/view/student/detail/student.detail.view";
import {VolunteerOverviewView} from "../components/view/volunteer/overview/volunteer.overview.view";
import {VolunteerAddView} from "../components/view/volunteer/add/volunteer.add.view";
import {VolunteerDetailView} from "../components/view/volunteer/detail/volunteer.detail.view";
import {StudentOverviewView} from "../components/view/student/overview/student.overview.view";
import {TaskOverviewView} from "../components/view/task/overview/task.overview.view";
import {TaskAddView} from "../components/view/task/add/task.add.view";
import {TaskDetailView} from "../components/view/task/detail/task.detail.view";
import {TaskEditView} from "../components/view/task/edit/task.edit.view";
import {StudentEditView} from "../components/view/student/edit/student.edit.view";

const appRoutes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'volunteer', canActivate: [AuthGuardService], component: VolunteerOverviewView },
  { path: 'volunteer/add', canActivate: [AuthGuardService], component: VolunteerAddView },
  { path: 'volunteer/:volunteerExtId', canActivate: [AuthGuardService], component: VolunteerDetailView },
  { path: 'student', canActivate: [AuthGuardService], component: StudentOverviewView},
  { path: 'student/add', canActivate: [AuthGuardService], component: StudentAddView},
  { path: 'student/:studentExtId/edit', canActivate: [AuthGuardService], component: StudentEditView},
  { path: 'student/:studentExtId', canActivate: [AuthGuardService], component: StudentDetailView},
  { path: 'task', canActivate: [AuthGuardService], component: TaskOverviewView},
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
