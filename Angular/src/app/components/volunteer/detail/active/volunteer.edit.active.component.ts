import {Component, EventEmitter, Output} from "@angular/core";
import {VolunteerActiveComponent} from "./volunteer.active.upsert.component";
import {VolunteerService} from "../../../../services/volunteer.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-volunteer-edit-active',
  templateUrl: './volunteer.active.upsert.component.html',
  styleUrls: ['./volunteer.active.upsert.component.css']
})
export class VolunteerEditActiveComponent extends VolunteerActiveComponent {
  @Output()
  onDeleteEmitter = new EventEmitter<boolean>();

  constructor(protected volunteerService: VolunteerService) {
    super('Wijzigen activiteit', volunteerService);
  }

  doHttpRequest() {
    this.volunteerService.updateVolunteerInstance(this.volunteerInstanceModel).subscribe(
      () => this.didHttpRequest.emit(this.volunteerInstanceModel.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

  delete() {
    // Show popup.
    if(!confirm("Weet je zeker dat je deze regel wilt verwijderen?")) {
      return;
    }

    // Delete the line and return.
    this.volunteerService.deleteVolunteerInstance(this.volunteerInstanceModel.volunteerExtId, this.volunteerInstanceModel.externalIdentifier).subscribe(
      () => {
        // Remove the instance from the volunteerInstanceValueObjects
        this.volunteer.volunteerInstanceValueObjects = this.volunteer.volunteerInstanceValueObjects.filter(
          volunteerInstanceValueObject => volunteerInstanceValueObject.externalIdentifier !== this.volunteerInstanceModel.externalIdentifier
        );
        this.onDeleteEmitter.emit(true);
      },
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

}
