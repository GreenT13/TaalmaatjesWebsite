import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import 'rxjs/Rx';
import {VolunteerModel} from "../valueobject/volunteer.model";
import {VolunteerInstanceModel} from "../valueobject/volunteerinstance.model";
import {VolunteerMatchModel} from "../valueobject/volunteermatch.model";

@Injectable()
export class VolunteerService {
  public currentVolunteer: VolunteerModel;

  constructor(private myHttpClient: MyHttpClient) {}

  searchVolunteers(search: String, city: String, isActive: Boolean, hasTraining: Boolean, hasMatch: Boolean) {
    const url: string = 'volunteer' + MyHttpClient.createParameterUrl([
      {name: 'search', value: search}, {name: 'city', value: city}, {name: 'isActive', value: isActive},
      {name: 'hasTraining', value: hasTraining}, {name: 'hasMatch', value: hasMatch}]);
    return this.myHttpClient.get(url, null);
  }

  insertVolunteer(volunteerModel: VolunteerModel, dateStartActive: String) {
    const url: string = 'volunteer' + MyHttpClient.createParameterUrl([{name: 'dateStartActive', value: dateStartActive}]);
    return this.myHttpClient.put(url, null, volunteerModel);
  }

  getVolunteer(volunteerExtId: string) {
    return this.myHttpClient.get('volunteer/' + volunteerExtId, null);
  }

  insertVolunteerInstance(volunteerInstanceModel: VolunteerInstanceModel) {
    return this.myHttpClient.put('volunteer/' + volunteerInstanceModel.volunteerExtId + '/instance', null, volunteerInstanceModel);
  }

  updateVolunteerInstance(volunteerInstanceModel: VolunteerInstanceModel) {
    return this.myHttpClient.post('volunteer/' +
      volunteerInstanceModel.volunteerExtId +
      '/instance/' +
      volunteerInstanceModel.externalIdentifier, null, volunteerInstanceModel);
  }

  deleteVolunteerInstance(volunteerExtId: string, volunteerInstanceExtId: string) {
    return this.myHttpClient.delete('volunteer/' + volunteerExtId + '/instance/' + volunteerInstanceExtId, null);
  }

  insertVolunteerMatch(volunteerMatchModel: VolunteerMatchModel) {
    return this.myHttpClient.put('volunteer/' + volunteerMatchModel.volunteerValueObject.externalIdentifier + '/match',
      null, volunteerMatchModel);
  }

  updateVolunteerMatch(volunteerMatchModel: VolunteerMatchModel) {
    return this.myHttpClient.post('volunteer/' + volunteerMatchModel.volunteerValueObject.externalIdentifier +
      '/match/' + volunteerMatchModel.externalIdentifier, null, volunteerMatchModel);
  }

  deleteVolunteerMatch(volunteerExtId: string, volunteerMatchExtId: string) {
    return this.myHttpClient.delete('volunteer/' + volunteerExtId +
      '/match/' + volunteerMatchExtId, null);
  }
}
