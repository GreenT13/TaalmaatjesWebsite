import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AuthGuardService} from './auth-guard.service';
import {LoginComponent} from '../login/login.component';
import {VolunteerOverviewComponent} from "../volunteer/overview/volunteer.overview.component";
import {VolunteerDetailComponent} from "../volunteer/detail/volunteer.detail.component";
import {VolunteerAddComponent} from "../volunteer/add/volunteer.add.component";
import {VolunteerEditActiveComponent} from "../volunteer/detail/active/volunteer.edit.active.component";

const appRoutes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'volunteer', canActivate: [AuthGuardService], component: VolunteerOverviewComponent},
  { path: 'volunteer/add', canActivate: [AuthGuardService], component: VolunteerAddComponent},
  { path: 'volunteer/:volunteerExtId', canActivate: [AuthGuardService], component: VolunteerDetailComponent, children: [
      { path: 'active/:volunteerInstanceExtId', component: VolunteerEditActiveComponent},
      { path: 'active', component: VolunteerEditActiveComponent}]}
      ];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
