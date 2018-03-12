import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {share} from "rxjs/operators";

@Injectable()
export class MyHttpClient {
  baseString: string;

  constructor(private httpClient: HttpClient) {
    this.baseString = 'api/';
  }

  get(url: string, httpHeaders: HttpHeaders): Observable<Object> {
    // Return with .pipe(share()) so observable doesn't get executed several times.
    // https://stackoverflow.com/questions/49208543/angular-2-observable-subscribing-twice-executes-call-twice
    return this.httpClient.get(this.baseString + url, { headers: httpHeaders}).pipe(share());
  }

  post(url: string, httpHeaders: HttpHeaders, body: object) {
    return this.httpClient.post(this.baseString + url, body, {headers: httpHeaders});
  }

  put(url: string, httpHeaders: HttpHeaders, body: object) {
    return this.httpClient.put(this.baseString + url, body, {headers: httpHeaders});
  }

  delete(url: string, httpHeaders: HttpHeaders) {
    return this.httpClient.delete(this.baseString + url, {headers: httpHeaders});
  }
}
