import {Injectable} from "@angular/core";
import {VolunteerModel} from "../../valueobject/volunteer.model";
import {Observable} from "rxjs/Observable";
import {ReplaySubject} from "rxjs/ReplaySubject";

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
}
