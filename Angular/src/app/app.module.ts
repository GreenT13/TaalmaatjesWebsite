import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './routes/app-routing.module';
import {LoginComponent} from './login/login.component';
import {AuthGuardService} from './routes/auth-guard.service';
import {MyHttpClient} from "./services/base/myhttpclient.service";
import {LoginService} from "./services/login.service";
import {HttpClientModule} from "@angular/common/http";
import {VolunteerOverviewComponent} from "./volunteer/overview/volunteer.overview.component";
import {VolunteerService} from "./services/volunteer.service";
import {NameUtil} from "./util/name.util";
import {VolunteerDetailComponent} from "./volunteer/detail/volunteer.detail.component";
import {VolunteerAddComponent} from "./volunteer/add/volunteer.add.component";
import {MyDatePickerModule} from 'mydatepicker';
import {DateUtil} from "./util/date.util";
import {GenderUtil} from "./util/gender.util";
import {VolunteerEditActiveComponent} from "./volunteer/detail/active/volunteer.edit.active.component";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    VolunteerOverviewComponent,
    VolunteerDetailComponent,
    VolunteerAddComponent,
    VolunteerEditActiveComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    MyDatePickerModule
  ],
  providers: [MyHttpClient, LoginService, AuthGuardService, VolunteerService, NameUtil, DateUtil, GenderUtil],
  bootstrap: [AppComponent]
})
export class AppModule { }
