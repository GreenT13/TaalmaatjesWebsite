import {Component, EventEmitter, Output} from "@angular/core";
import {HttpErrorResponse} from "@angular/common/http";
import {VolunteerMatchComponent} from "./volunteer.match.upsert.component";
import {VolunteerService} from "../../../../../services/volunteer.service";

@Component({
  selector: 'app-volunteer-edit-match',
  templateUrl: './volunteer.match.upsert.component.html',
  styleUrls: ['./volunteer.match.upsert.component.css']
})
export class VolunteerEditMatchComponent extends VolunteerMatchComponent {
  @Output()
  onDeleteEmitter = new EventEmitter<string>();

  constructor(protected volunteerService: VolunteerService) {
    super('Wijzigen koppeling', volunteerService);
  }

  doHttpRequest() {
    this.volunteerService.updateVolunteerMatch(this.volunteerMatch).subscribe(
      () => this.didHttpRequest.emit(this.volunteerMatch.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

  delete() {
    // Show popup.
    if(!confirm("Weet je zeker dat je deze regel wilt verwijderen?")) {
      return;
    }

    // Delete the line and return.
    this.volunteerService.deleteVolunteerMatch(this.volunteer.externalIdentifier, this.volunteerMatch.externalIdentifier).subscribe(
      () => this.onDeleteEmitter.emit(this.volunteerMatch.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

}
