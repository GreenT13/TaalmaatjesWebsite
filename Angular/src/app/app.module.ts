import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './routes/app-routing.module';
import {LoginComponent} from './components/view/login/login.component';
import {AuthGuardService} from './routes/auth-guard.service';
import {MyHttpClient} from "./services/base/myhttpclient.service";
import {LoginService} from "./services/login.service";
import {HttpClientModule} from "@angular/common/http";
import {VolunteerService} from "./services/volunteer.service";
import {OverlayService} from "./services/overlay.service";
import {AlertComponent} from "./components/block/alert/alert.component";
import {GenderPipe} from "./pipes/gender.pipe";
import {BooleanPipe} from "./pipes/boolean.pipe";
import {VersionService} from "./services/version.service";
import {ThemeDirective} from "./directive/theme.directive";
import {ThemeService} from "./services/theme.service";
import {CookieModule} from "ngx-cookie";
import {TaskService} from "./services/task.service";
import {AutocompleteComponent} from "./components/block/autocomplete/autocomplete.component";
import {NamePipe} from "./pipes/name.pipe";
import {ShortenPipe} from "./pipes/shorten.pipe";
import {PaginationComponent} from "./components/block/pagination/pagination.component";
import {MyDatePipe} from "./pipes/mydate.pipe";
import {StudentService} from "./services/student.service";
import {AgePipe} from "./pipes/age.pipe";
import {AutocompleteStudentComponent} from "./components/block/autocomplete/student/autocomplete.student.component";
import {StudentEditComponent} from "./components/block/core/student/upsert/student.edit.component";
import {StudentAddView} from "./components/view/student/add/student.add.view";
import {StudentAddComponent} from "./components/block/core/student/upsert/student.add.component";
import {StudentDetailComponent} from "./components/block/core/student/detail/student.detail.component";
import {StudentDetailView} from "./components/view/student/detail/student.detail.view";
import {StudentOverviewView} from "./components/view/student/overview/student.overview.view";
import {StudentEditView} from "./components/view/student/edit/student.edit.view";
import {VolunteerAddActiveComponent} from "./components/block/core/volunteerinstance/upsert/volunteer.add.active.component";
import {VolunteerEditActiveComponent} from "./components/block/core/volunteerinstance/upsert/volunteer.edit.active.component";
import {TaskDetailComponent} from "./components/block/core/task/detail/task.detail.component";
import {TaskDetailView} from "./components/view/task/detail/task.detail.view";
import {TaskAddView} from "./components/view/task/add/task.add.view";
import {TaskEditView} from "./components/view/task/edit/task.edit.view";
import {TaskAddComponent} from "./components/block/core/task/upsert/task.add.component";
import {TaskEditComponent} from "./components/block/core/task/upsert/task.edit.component";
import {TaskOverviewView} from "./components/view/task/overview/task.overview.view";
import {VolunteerOverviewView} from "./components/view/volunteer/overview/volunteer.overview.view";
import {VolunteerDetailComponent} from "./components/block/core/volunteer/detail/volunteer.detail.component";
import {VolunteerDetailView} from "./components/view/volunteer/detail/volunteer.detail.view";
import {VolunteerAddView} from "./components/view/volunteer/add/volunteer.add.view";
import {VolunteerEditMatchComponent} from "./components/block/core/volunteermatch/upsert/volunteer.edit.match.component";
import {VolunteerAddMatchComponent} from "./components/block/core/volunteermatch/upsert/volunteer.add.match.component";
import {VolunteerEditComponent} from "./components/block/core/volunteer/edit/volunteer.edit.component";
import {DatepickerComponent} from "./components/block/datepicker/datepicker.component";


@NgModule({
  declarations: [
    AppComponent,
    // Login view
    LoginComponent,

    // Volunteer blocks
    VolunteerDetailComponent, VolunteerEditComponent,

    // Volunteer active blocks
    VolunteerAddActiveComponent, VolunteerEditActiveComponent,

    // Volunteer match blocks
    VolunteerAddMatchComponent, VolunteerEditMatchComponent,

    // Volunteer views
    VolunteerOverviewView, VolunteerDetailView, VolunteerAddView,

    // Student blocks
    StudentDetailComponent, StudentAddComponent, StudentEditComponent,

    // Student views
    StudentAddView, StudentDetailView, StudentEditView, StudentOverviewView,

    // Task blocks
    TaskDetailComponent, TaskAddComponent, TaskEditComponent,

    // Task views
    TaskAddView, TaskDetailView, TaskEditView, TaskOverviewView,

    AutocompleteComponent, AutocompleteStudentComponent,
    AlertComponent, PaginationComponent, DatepickerComponent,
    GenderPipe, BooleanPipe, NamePipe, ShortenPipe, MyDatePipe, AgePipe,
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
    MyHttpClient, LoginService, VolunteerService, VersionService, TaskService, StudentService,
    OverlayService, AuthGuardService,
    ThemeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
