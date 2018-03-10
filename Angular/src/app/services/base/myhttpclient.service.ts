import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class MyHttpClient {
  baseString: string;

  constructor(private httpClient: HttpClient) {
    this.baseString = 'api/';
  }

  get(url: string, httpHeaders: HttpHeaders) {
    return this.httpClient.get(this.baseString + url, { headers: httpHeaders});
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
