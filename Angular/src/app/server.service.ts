import {Injectable, isDevMode} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class ServerService {
  constructor(private http: HttpClient) {}
  storeServers() {
    var username = 'root';
    var password = 'root';
    var httpHeaders = new HttpHeaders().append("Authorization", 'Basic ' + btoa(username + ':' + password));

    var url = 'api/message';
    if (isDevMode()) {
      url = 'http://localhost:7998/rest/' + url;
    }
    console.log(url);

    return this.http.get(url, {headers: httpHeaders});
  }
}
