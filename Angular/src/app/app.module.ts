import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './routes/app-routing.module';
import {LoginComponent} from './components/login/login.component';
import {AuthGuardService} from './routes/auth-guard.service';
import {MyHttpClient} from "./services/base/myhttpclient.service";
import {LoginService} from "./services/login.service";
import {HttpClientModule} from "@angular/common/http";
import {VolunteerOverviewComponent} from "./components/volunteer/overview/volunteer.overview.component";
import {VolunteerService} from "./services/volunteer.service";
import {VolunteerDetailComponent} from "./components/volunteer/detail/volunteer.detail.component";
import {VolunteerAddComponent} from "./components/volunteer/add/volunteer.add.component";
import {VolunteerEditActiveComponent} from "./components/volunteer/detail/active/volunteer.edit.active.component";
import {OverlayService} from "./services/overlay.service";
import {AlertComponent} from "./components/alert/alert.component";
import {GenderPipe} from "./pipes/gender.pipe";
import {BooleanPipe} from "./pipes/boolean.pipe";
import {VolunteerAddActiveComponent} from "./components/volunteer/detail/active/volunteer.add.active.component";
import {VersionService} from "./services/version.service";
import {ThemeDirective} from "./directive/theme.directive";
import {ThemeService} from "./services/theme.service";
import {CookieModule} from "ngx-cookie";
import {TaskService} from "./services/task.service";
import {TaskOverviewComponent} from "./components/task/overview/task.overview.component";
import {TaskAddComponent} from "./components/task/add/task.add.component";
import {AutocompleteComponent} from "./components/autocomplete/autocomplete.component";
import {VolunteerNamePipe} from "./pipes/volunteername.pipe";
import {TaskDetailComponent} from "./components/task/detail/task.detail.component";
import {ShortenPipe} from "./pipes/shorten.pipe";
import {TaskEditComponent} from "./components/task/add/task.edit.component";
import {TaskAddView} from "./components/task/add/task.add.view";
import {TaskEditView} from "./components/task/add/task.edit.view";
import {TaskDetailView} from "./components/task/detail/task.detail.view";
import {VolunteerPersonalComponent} from "./components/volunteer/detail/blocks/personal/volunteerpersonal.component";
import {PaginationComponent} from "./components/pagination/pagination.component";
import {MyDatePipe} from "./pipes/mydate.pipe";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    VolunteerOverviewComponent, VolunteerDetailComponent, VolunteerAddComponent, VolunteerAddActiveComponent, VolunteerEditActiveComponent,
    VolunteerPersonalComponent,
    TaskOverviewComponent, TaskAddComponent, TaskDetailComponent, TaskEditComponent,
    TaskAddView, TaskEditView, TaskDetailView,
    AutocompleteComponent,
    AlertComponent, PaginationComponent,
    GenderPipe, BooleanPipe, VolunteerNamePipe, ShortenPipe, MyDatePipe,
    ThemeDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    CookieModule.forRoot()
  ],
  providers: [
    MyHttpClient, LoginService, VolunteerService, VersionService, TaskService,
    OverlayService, AuthGuardService,
    ThemeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
