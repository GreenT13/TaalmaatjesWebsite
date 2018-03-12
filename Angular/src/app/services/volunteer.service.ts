import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import 'rxjs/Rx';
import {VolunteerModel} from "../valueobject/volunteer.model";
import {VolunteerInstanceModel} from "../valueobject/volunteerinstance.model";

@Injectable()
export class VolunteerService {
  public currentVolunteer: VolunteerModel;

  constructor(private myHttpClient: MyHttpClient) {}

  searchVolunteers() {
    return this.myHttpClient.get('volunteer', null);
  }

  insertVolunteer(volunteerModel: VolunteerModel) {
    return this.myHttpClient.put('volunteer', null, volunteerModel);
  }

  getVolunteer(volunteerExtId: string) {
    return this.myHttpClient.get('volunteer/' + volunteerExtId, null);
  }

  insertVolunteerInstance(volunteerInstanceModel: VolunteerInstanceModel) {
    return this.myHttpClient.put('volunteer/' + volunteerInstanceModel.volunteerExtId + '/instance', null, volunteerInstanceModel);
  }

}
