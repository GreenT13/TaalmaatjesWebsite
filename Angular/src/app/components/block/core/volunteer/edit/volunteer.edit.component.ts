import {Component, EventEmitter, Input, Output} from "@angular/core";
import {AlertModel} from "../../../alert/alert.model";
import {DestroyUtil} from "../../../../../util/destroy.util";
import {GenderUtil} from "../../../../../util/gender.util";
import {VolunteerDVO} from "../../../../../valueobject/dvo/volunteer.dvo";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-volunteer-edit',
  templateUrl: './volunteer.edit.component.html'
})
export class VolunteerEditComponent {
  public genders = GenderUtil.genders;
  protected destroyUtil: DestroyUtil = new DestroyUtil();

  @Input()
  alertModel: AlertModel;

  @Input()
  public volunteer: VolunteerDVO = new VolunteerDVO();

  @Output()
  didHttpRequest = new EventEmitter<string>();

  @Output()
  onBackEmitter = new EventEmitter();

  constructor(private volunteerService: VolunteerService) { }

  onSubmit() {
    this.destroyUtil.addSubscription(this.doHttpRequest());
  }

  doHttpRequest() {
    return this.volunteerService.updateVolunteer(this.volunteer).subscribe(
      () => this.didHttpRequest.emit(this.volunteer.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

  onBack() {
    this.onBackEmitter.emit(true);
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }
}
