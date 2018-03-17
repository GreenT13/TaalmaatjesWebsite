import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {LoginService} from "../services/login.service";

@Injectable()
export class AuthGuardService implements CanActivate {

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.loginService.isLoggedInObservable();
  }

  constructor(private loginService: LoginService) { }
}
