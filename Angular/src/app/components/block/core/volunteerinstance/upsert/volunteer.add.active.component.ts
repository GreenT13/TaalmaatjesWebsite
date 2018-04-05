import {Component} from "@angular/core";
import {VolunteerActiveUpsertComponent} from "./volunteer.active.upsert.component";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-volunteer-add-active',
  templateUrl: './volunteer.active.upsert.component.html',
  styleUrls: ['./volunteer.active.upsert.component.css']
})
export class VolunteerAddActiveComponent extends VolunteerActiveUpsertComponent {

  constructor(protected volunteerService: VolunteerService) {
    super('Toevoegen activiteit', volunteerService);
  }

  doHttpRequest() {
    this.volunteerService.insertVolunteerInstance(this.volunteerInstance).subscribe(
      () => this.didHttpRequest.emit(this.volunteerInstance.externalIdentifier),
      (error: HttpErrorResponse) =>  this.alertModel.setError(error)
    );
  }

}
