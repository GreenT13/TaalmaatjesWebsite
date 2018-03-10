import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {CommonModule} from '@angular/common';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './routes/app-routing.module';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import {AuthGuardService} from './routes/auth-guard.service';
import {MyHttpClient} from "./services/base/myhttpclient.service";
import {LoginService} from "./services/login.service";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [MyHttpClient, LoginService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
