import {Component, Inject, OnInit} from '@angular/core';
import {LoginService} from "./services/login.service";
import {DOCUMENT} from "@angular/common";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentTheme: string = 'dark';
  hrefDarkTheme: string = 'https://maxcdn.bootstrapcdn.com/bootswatch/3.3.7/slate/bootstrap.min.css';
  hrefLightTheme: string = 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css';

  constructor(public loginService: LoginService, @Inject(DOCUMENT) private document) { }

  handleChangeTheme() {
    if (this.currentTheme === 'dark') {
      this.document.getElementById('theme').href = this.hrefLightTheme;
      this.currentTheme = 'light';
    } else {
      this.document.getElementById('theme').href = this.hrefDarkTheme;
      this.currentTheme = 'dark';
    }
  }
}
