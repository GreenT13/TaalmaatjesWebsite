import {Component, EventEmitter, Output} from "@angular/core";
import {VolunteerActiveUpsertComponent} from "./volunteer.active.upsert.component";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {HttpErrorResponse} from "@angular/common/http";
import {DatepickerComponent} from "../../../datepicker/datepicker.component";

@Component({
  selector: 'app-volunteer-edit-active',
  templateUrl: './volunteer.active.upsert.component.html',
  styleUrls: ['./volunteer.active.upsert.component.css'],
  providers: [DatepickerComponent]
})
export class VolunteerEditActiveComponent extends VolunteerActiveUpsertComponent {
  @Output()
  onDeleteEmitter = new EventEmitter<string>();

  constructor(protected volunteerService: VolunteerService) {
    super('Wijzigen activiteit', volunteerService);
  }

  doHttpRequest() {
    return this.volunteerService.updateVolunteerInstance(this.volunteerInstance).subscribe(
      () => this.didHttpRequest.emit(this.volunteerInstance.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

  delete() {
    // Show popup.
    if(!confirm("Weet je zeker dat je deze regel wilt verwijderen?")) {
      return;
    }

    this.volunteerService.deleteVolunteerInstance(this.volunteer.externalIdentifier, this.volunteerInstance.externalIdentifier).subscribe(
      () => this.onDeleteEmitter.emit(this.volunteerInstance.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

}
