import {HttpHeaders} from "@angular/common/http";
import {MyHttpClient} from "./base/myhttpclient.service";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Observer} from "rxjs/Observer";

@Injectable()
export class LoginService {
  loggedIn: boolean = false;

  constructor(private myHttpClient: MyHttpClient) { }

  login(username: string, password: string) {
    // HttpHeaders is an immutable object, so we need to append.
    const httpHeaders = new HttpHeaders()
      .append("Authorization", 'Basic ' + btoa(username + ':' + password));

    const promise = this.myHttpClient.get('version/secured', httpHeaders);
    promise.subscribe(
      () => {
        this.loggedIn = true;
      },
      () => {
        this.loggedIn = false;
      });
    return promise;
  }

  logout() {
    this.myHttpClient.get('user/logout', null);
    this.loggedIn = false;
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  isLoggedInObservable() : Observable<boolean> {
    // No need to try logging in if we are already logged in.
    if (this.loggedIn === true) {
      return Observable.create((observer: Observer<boolean>) => {
        observer.next(true);
        observer.complete();
      });
    }

    return Observable.create((observer: Observer<boolean>) => {
      // If we can already login using cached values, the header doesn't even matter.
      this.login('', '').subscribe(
        () => {
          this.loggedIn = true;
          observer.next(true);
          observer.complete();
        },
        () => {
          this.loggedIn = false;
          observer.next(false);
          observer.complete();
        }
      )
    });
  }
}
