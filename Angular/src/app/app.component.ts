import {Component, Inject, OnInit} from '@angular/core';
import {DOCUMENT} from "@angular/common";
import {OverlayService} from "./services/overlay.service";
import {LoginService} from "./services/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentTheme: string = 'dark';
  hrefDarkTheme: string = 'https://maxcdn.bootstrapcdn.com/bootswatch/3.3.7/slate/bootstrap.min.css';
  hrefLightTheme: string = 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css';
  public showOverlay: boolean = false;

  constructor(@Inject(DOCUMENT) private document,
              public overlayService: OverlayService,
              public loginService: LoginService) {
    overlayService.showOverlayEventEmitter.subscribe((value: boolean) => this.showOverlay = value);
  }

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
