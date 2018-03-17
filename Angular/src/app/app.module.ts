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
import {VolunteerDetailComponent} from "./volunteer/detail/volunteer.detail.component";
import {VolunteerAddComponent} from "./volunteer/add/volunteer.add.component";
import {MyDatePickerModule} from 'mydatepicker';
import {VolunteerEditActiveComponent} from "./volunteer/detail/active/volunteer.edit.active.component";
import {OverlayService} from "./services/overlay.service";
import {AlertComponent} from "./alert/alert.component";
import {MyDatePipe} from "./pipes/mydate.pipe";
import {GenderPipe} from "./pipes/gender.pipe";
import {BooleanPipe} from "./pipes/boolean.pipe";
import {VolunteerAddActiveComponent} from "./volunteer/detail/active/volunteer.add.active.component";
import {VersionService} from "./services/version.service";
import {ThemeDirective} from "./directive/theme.directive";
import {ThemeService} from "./services/theme.service";
import {CookieModule} from "ngx-cookie";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    VolunteerOverviewComponent,
    VolunteerDetailComponent,
    VolunteerAddComponent,
    VolunteerAddActiveComponent, VolunteerEditActiveComponent,
    AlertComponent,
    MyDatePipe, GenderPipe, BooleanPipe,
    ThemeDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    MyDatePickerModule,
    CookieModule.forRoot()
  ],
  providers: [
    MyHttpClient, LoginService, VolunteerService, VersionService,
    OverlayService, AuthGuardService,
    ThemeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
