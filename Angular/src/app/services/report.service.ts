import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import 'rxjs/Rx';

@Injectable()
export class ReportService {
  constructor(private myHttpClient: MyHttpClient) {}

  getReport(dateStart: Date, dateEnd: Date) {
    const url: string = 'report' + MyHttpClient.createParameterUrl([
      {name: 'dateStart', value: dateStart}, {name: 'dateEnd', value: dateEnd}]);
    return this.myHttpClient.get(url, null);
  }
}
