import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {OverlayService} from "../overlay.service";

@Injectable()
export class MyHttpClient {
  baseString: string;

  constructor(private httpClient: HttpClient, private overlayService: OverlayService) {
    this.baseString = 'api/';
  }

  get(url: string, httpHeaders: HttpHeaders): Observable<Object> {
    // Return with .publishReplay().refCount() so observable doesn't get executed several times.
    // https://stackoverflow.com/questions/49208543/angular-2-observable-subscribing-twice-executes-call-twice
    return this.overlayService.addOverlayWait(
      this.httpClient.get(this.baseString + url, { headers: httpHeaders}).publishReplay().refCount()
    );
  }

  post(url: string, httpHeaders: HttpHeaders, body: object) {
    return this.overlayService.addOverlayWait(
      this.httpClient.post(this.baseString + url, body, {headers: httpHeaders}).publishReplay().refCount()
    );
  }

  put(url: string, httpHeaders: HttpHeaders, body: object) {
    return this.overlayService.addOverlayWait(
      this.httpClient.put(this.baseString + url, body, {headers: httpHeaders}).publishReplay().refCount()
    );
  }

  delete(url: string, httpHeaders: HttpHeaders) {
    return this.overlayService.addOverlayWait(
      this.httpClient.delete(this.baseString + url, {headers: httpHeaders}).publishReplay().refCount()
    );
  }

  public static createParameterUrl(params: {name: string, value: object}[]) {
    if (!params || params.length == 0) {
      return '';
    }

    let url: string = '?';
    let isFirst: boolean = true;
    for (let {name, value} of params) {
      if (value == null || value == undefined || value.toString().length === 0) {
        continue;
      }

      if (!isFirst) {
        url += '&'
      } else {
        isFirst = false;
      }
      url += name + '=' + value;
    }

    return url;
  }
}

