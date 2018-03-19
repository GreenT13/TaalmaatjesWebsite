import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import 'rxjs/Rx';
import {VolunteerModel} from "../valueobject/volunteer.model";

@Injectable()
export class TaskService {
  public currentVolunteer: VolunteerModel;

  constructor(private myHttpClient: MyHttpClient) {}

  searchTask(title: String, description: String, isFinished: Boolean, volunteerExtId: String) {
    const url: string = 'task' + MyHttpClient.createParameterUrl([
      {name: 'title', value: title}, {name: 'description', value: description}, {name: 'isFinished', value: isFinished},
      {name: 'volunteerExtId', value: volunteerExtId}]);
    return this.myHttpClient.get(url, null);
  }

}
