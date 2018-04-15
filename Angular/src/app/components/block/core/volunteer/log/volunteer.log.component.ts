import {Component, EventEmitter, Input, Output} from "@angular/core";
import {VolunteerDVO} from "../../../../../valueobject/dvo/volunteer.dvo";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {AlertModel} from "../../../alert/alert.model";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-volunteer-log',
  templateUrl: './volunteer.log.component.html'
})
export class VolunteerLogComponent {
  @Input()
  public volunteer: VolunteerDVO = new VolunteerDVO();

  @Input()
  alertModel: AlertModel;

  @Output()
  didHttpRequest = new EventEmitter<string>();

  @Output()
  onBackEmitter = new EventEmitter();

  constructor(private volunteerService: VolunteerService) { }

  onSubmit() {
    this.volunteerService.updateVolunteer(this.volunteer).subscribe(
      () => this.didHttpRequest.emit(this.volunteer.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    )
  }

  onBack() {
    this.onBackEmitter.emit(true);
  }
}
