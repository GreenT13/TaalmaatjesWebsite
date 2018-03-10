import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {LoginService} from "../services/login.service";

@Injectable()
export class AuthGuardService implements CanActivate {
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    // if (!this.loginService.isLoggedIn()) {
    //     this.router.navigate(['/login']);
    // } else {
    //   return true;
    // }
    return this.loginService.isLoggedInObservable();
  }

  constructor(private loginService: LoginService, private router: Router) { }
}
