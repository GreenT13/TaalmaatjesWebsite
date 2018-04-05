import {Component} from "@angular/core";
import {VolunteerMatchComponent} from "./volunteer.match.upsert.component";
import {HttpErrorResponse} from "@angular/common/http";
import {VolunteerService} from "../../../../../services/volunteer.service";

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
    this.volunteerService.insertVolunteerMatch(this.volunteerMatch).subscribe(
      () => this.didHttpRequest.emit(this.volunteerMatch.externalIdentifier),
      (error: HttpErrorResponse) =>  this.alertModel.setError(error)
    );
  }
}
