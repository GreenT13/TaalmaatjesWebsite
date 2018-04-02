import {Component} from "@angular/core";
import {VolunteerMatchComponent} from "./volunteer.match.upsert.component";
import {HttpErrorResponse} from "@angular/common/http";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {CopyUtil} from "../../../../../util/copy.util";

@Component({
  selector: 'app-volunteer-add-match',
  templateUrl: './volunteer.match.upsert.component.html',
  styleUrls: ['./volunteer.match.upsert.component.css']
})
export class VolunteerAddMatchComponent extends VolunteerMatchComponent {

  constructor(protected volunteerService: VolunteerService) {
    super('Toevoegen koppeling', volunteerService);
  }

  doHttpRequest() {
    this.volunteerMatchModel.volunteerValueObject = CopyUtil.createCopyVolunteer(this.volunteer);

    this.volunteerService.insertVolunteerMatch(this.volunteerMatchModel).subscribe(
      () => this.didHttpRequest.emit(this.volunteerMatchModel.externalIdentifier),
      (error: HttpErrorResponse) =>  this.alertModel.setError(error)
    );
  }
}
