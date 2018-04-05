import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import 'rxjs/Rx';
import {VolunteerDVO} from "../valueobject/dvo/volunteer.dvo";
import {VolunteerInstanceDVO} from "../valueobject/dvo/volunteerinstance.dvo";
import {VolunteerMatchDVO} from "../valueobject/dvo/volunteermatch.dvo";

@Injectable()
export class VolunteerService {
  constructor(private myHttpClient: MyHttpClient) {}

  searchVolunteers(search: String, city: String, isActive: Boolean, hasTraining: Boolean, hasMatch: Boolean) {
    const url: string = 'volunteer' + MyHttpClient.createParameterUrl([
      {name: 'search', value: search}, {name: 'city', value: city}, {name: 'isActive', value: isActive},
      {name: 'hasTraining', value: hasTraining}, {name: 'hasMatch', value: hasMatch}]);
    return this.myHttpClient.get(url, null);
  }

  insertVolunteer(volunteer: VolunteerDVO, dateStartActive: String) {
    const url: string = 'volunteer' + MyHttpClient.createParameterUrl([{name: 'dateStartActive', value: dateStartActive}]);
    return this.myHttpClient.put(url, null, volunteer);
  }

  getVolunteer(volunteerExtId: string) {
    return this.myHttpClient.get('volunteer/' + volunteerExtId, null);
  }

  insertVolunteerInstance(volunteerInstance: VolunteerInstanceDVO) {
    return this.myHttpClient.put('volunteer/' + volunteerInstance.volunteerDVO.externalIdentifier + '/instance', null, volunteerInstance);
  }

  updateVolunteerInstance(volunteerInstance: VolunteerInstanceDVO) {
    return this.myHttpClient.post('volunteer/' +
      volunteerInstance.volunteerDVO.externalIdentifier +
      '/instance/' +
      volunteerInstance.externalIdentifier, null, volunteerInstance);
  }

  deleteVolunteerInstance(volunteerExtId: string, volunteerInstanceExtId: string) {
    return this.myHttpClient.delete('volunteer/' + volunteerExtId + '/instance/' + volunteerInstanceExtId, null);
  }

  insertVolunteerMatch(volunteerMatch: VolunteerMatchDVO) {
    return this.myHttpClient.put('volunteer/' + volunteerMatch.volunteerDVO.externalIdentifier + '/match',
      null, volunteerMatch);
  }

  updateVolunteerMatch(volunteerMatch: VolunteerMatchDVO) {
    return this.myHttpClient.post('volunteer/' + volunteerMatch.volunteerDVO.externalIdentifier +
      '/match/' + volunteerMatch.externalIdentifier, null, volunteerMatch);
  }

  deleteVolunteerMatch(volunteerExtId: string, volunteerMatchExtId: string) {
    return this.myHttpClient.delete('volunteer/' + volunteerExtId +
      '/match/' + volunteerMatchExtId, null);
  }
}
