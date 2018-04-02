import {Component, EventEmitter, Output} from "@angular/core";
import {HttpErrorResponse} from "@angular/common/http";
import {VolunteerMatchComponent} from "./volunteer.match.upsert.component";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {CopyUtil} from "../../../../../util/copy.util";

@Component({
  selector: 'app-volunteer-edit-match',
  templateUrl: './volunteer.match.upsert.component.html',
  styleUrls: ['./volunteer.match.upsert.component.css']
})
export class VolunteerEditMatchComponent extends VolunteerMatchComponent {
  @Output()
  onDeleteEmitter = new EventEmitter<boolean>();

  constructor(protected volunteerService: VolunteerService) {
    super('Wijzigen koppeling', volunteerService);
  }

  doHttpRequest() {
    this.volunteerMatchModel.volunteerValueObject = CopyUtil.createCopyVolunteer(this.volunteer);

    this.volunteerService.updateVolunteerMatch(this.volunteerMatchModel).subscribe(
      () => this.didHttpRequest.emit(this.volunteerMatchModel.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

  delete() {
    // Show popup.
    if(!confirm("Weet je zeker dat je deze regel wilt verwijderen?")) {
      return;
    }

    // Delete the line and return.
    this.volunteerService.deleteVolunteerMatch(this.volunteerMatchModel.volunteerValueObject.externalIdentifier,
      this.volunteerMatchModel.externalIdentifier).subscribe(
      () => {
        // Remove the instance from the volunteerInstanceValueObjects
        this.volunteer.volunteerMatchValueObjects = this.volunteer.volunteerMatchValueObjects.filter(
          volunteerMatchValueObject => volunteerMatchValueObject.externalIdentifier !== this.volunteerMatchModel.externalIdentifier
        );
        this.onDeleteEmitter.emit(true);
      },
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

}
