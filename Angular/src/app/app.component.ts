import {Component, Inject} from '@angular/core';
import {DOCUMENT} from "@angular/common";
import {OverlayService} from "./services/overlay.service";
import {LoginService} from "./services/login.service";
import {ThemeService} from "./services/theme.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  hrefDarkTheme: string = 'https://maxcdn.bootstrapcdn.com/bootswatch/3.3.7/slate/bootstrap.min.css';
  hrefLightTheme: string = 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css';
  public showOverlay: boolean = false;

  constructor(@Inject(DOCUMENT) private document,
              public overlayService: OverlayService,
              public loginService: LoginService,
              private themeService: ThemeService,
              private router: Router) {

    overlayService.showOverlayEventEmitter.subscribe((value: boolean) => this.showOverlay = value);

    // Initialize theme.
    this.loadTheme(this.themeService.getTheme());

    // Subscribe so that if theme changes, bootstrap theme changes with it.
    this.themeService.changeTheme.subscribe(
      (theme: string) => this.loadTheme(theme)
    );
  }

  private loadTheme(theme: string) {
    if (theme == 'dark') {
      this.document.getElementById('theme').href = this.hrefDarkTheme;
    } else if (theme == 'light') {
      this.document.getElementById('theme').href = this.hrefLightTheme;
    }
  }

  handleChangeTheme() {
    this.themeService.toggleTheme();
    // Subscription from constructor does the rest.
  }

  logout() {
    this.loginService.logout().subscribe(
      () => this.router.navigate(['/login']),
      (error: HttpErrorResponse) => console.log(error)
    );
  }
}
