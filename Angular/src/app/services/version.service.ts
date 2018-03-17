import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";

@Injectable()
export class VersionService {
  constructor(private myHttpClient: MyHttpClient) {}

  getVersion() {
    return this.myHttpClient.get('version', null);
  }

}
