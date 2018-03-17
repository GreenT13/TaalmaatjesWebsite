import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from "../services/login.service";
import {VersionService} from "../services/version.service";
import {SingleStringModel} from "../valueobject/singlestring.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  errorMessage: string;
  version: string;

  constructor(private loginService: LoginService,
              private router: Router,
              private versionService: VersionService) {
    // Subscribe at construction, so if it ever changes to true we actually route (in case we refresh browser).
    this.loginService.isLoggedInObservable().subscribe(
      (value: boolean) => {
        if (value == true) {
          this.router.navigate(['/volunteer']);
        }
      }
    );

    this.versionService.getVersion().subscribe(
      (version: SingleStringModel) => {
        this.version = version.value;
      },
      (error: any) => {
        this.errorMessage = 'Versie kon niet opgehaald worden.';
      }
    )
  }

  ngOnInit() {
  }

  onSubmit() {
    this.loginService.login(this.username, this.password).subscribe(
      () => {
        // We already route because of constructor.
      },
      (error: Error) => {
        this.errorMessage = 'Inloggen mislukt.'
      });
  }
}
