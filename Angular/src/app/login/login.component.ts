import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from "../services/login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  errorMessage: string;

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit() {
    this.loginService.isLoggedInObservable().subscribe(
      (value: Boolean) => {
        if (value === true) {
          this.router.navigate(['/user']);
        }
      }
    )
  }

  onSubmit() {
    this.loginService.login(this.username, this.password).subscribe(
      (response: Response) => {
        console.log(response);
        this.router.navigate(['/user']);
      },
      (error: Error) => {
        console.log(error);
        console.log('Could not login');
      });
  }
}
