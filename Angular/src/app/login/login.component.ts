import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from "../services/login.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  errorMessage: string;

  constructor(private loginService: LoginService, private router: Router, private httpClient: HttpClient) {
    // Subscribe at construction, so if it ever changes to true we actually route (in case we refresh browser).
    this.loginService.isLoggedInObservable().subscribe(
      (value: Boolean) => {
        if (value === true) {
          this.router.navigate(['/volunteer']);
        }
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
