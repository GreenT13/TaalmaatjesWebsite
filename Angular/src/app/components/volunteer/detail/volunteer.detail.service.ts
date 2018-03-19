import {Injectable} from "@angular/core";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {Observable} from "rxjs/Observable";
import {ReplaySubject} from "rxjs/ReplaySubject";
import {VolunteerService} from "../../../services/volunteer.service";

@Injectable()
export class VolunteerDetailService {
  // Use a replay subject, so that every subcomponent still gets the volunteer that is set.
  private subject = new ReplaySubject<VolunteerModel>(1);

  public setVolunteer(volunteerModel: VolunteerModel) {
    this.subject.next(volunteerModel);
  }

  public getVolunteer(): Observable<VolunteerModel> {
    return this.subject.asObservable();
  }

  public retrieveVolunteer(extId: string, volunteerService: VolunteerService) {
    let observable = volunteerService.getVolunteer(extId);
    observable.subscribe(
      (response: VolunteerModel) => {
        this.setVolunteer(response);
        return null;
      },
      (error) => {
        return error;
      }
    );
    return observable;
  }
}
