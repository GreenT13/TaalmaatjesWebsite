import {EventEmitter, Injectable} from "@angular/core";
import {CookieService} from "ngx-cookie";

@Injectable()
export class ThemeService {
  public changeTheme = new EventEmitter();
  private currentTheme;

  constructor(private cookieService: CookieService) {
    // Set theme to the theme from the cookie, if it exists.
    if (this.cookieService.get('theme')) {
      this.currentTheme = this.cookieService.get('theme');
      this.changeTheme.emit(this.currentTheme);
    } else {
      this.cookieService.put('theme', 'dark');
      this.currentTheme = 'dark';
      this.changeTheme.emit(this.currentTheme);
    }
  }

  public toggleTheme() {
    if (this.currentTheme == 'dark') {
      this.currentTheme = 'light';
      this.cookieService.put('theme', 'light');
      this.changeTheme.emit(this.currentTheme);
    } else if (this.currentTheme = 'light') {
      this.currentTheme = 'dark';
      this.cookieService.put('theme', 'dark');
      this.changeTheme.emit(this.currentTheme);
    }
  }

  public getTheme() {
    return this.currentTheme;
  }

  public setTheme(theme: string) {
    if (this.currentTheme == theme) {
      this.changeTheme.emit(this.currentTheme);
    } else {
      this.toggleTheme();
    }
  }
}
