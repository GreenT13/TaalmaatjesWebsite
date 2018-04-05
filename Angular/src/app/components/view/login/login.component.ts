import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from "../../../services/login.service";
import {VersionService} from "../../../services/version.service";
import {StringVO} from "../../../valueobject/string.vo";
import {HttpErrorResponse} from "@angular/common/http";
import {AlertModel} from "../../block/alert/alert.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string;
  password: string;
  alertModel: AlertModel = new AlertModel();
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
      },
      // Add this line so the 'ERROR false' disappears.
      () => { }
    );

    this.versionService.getVersion().subscribe(
      (version: StringVO) => {
        this.version = version.value;
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      }
    )
  }

  onSubmit() {
    this.loginService.login(this.username, this.password).subscribe(
      () => {
        // We already route because of constructor.
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      });
  }
}
