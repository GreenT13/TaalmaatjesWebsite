import {EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {DestroyUtil} from "../../../../../util/destroy.util";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {AlertModel} from "../../../alert/alert.model";
import {VolunteerInstanceDVO} from "../../../../../valueobject/dvo/volunteerinstance.dvo";
import {VolunteerDVO} from "../../../../../valueobject/dvo/volunteer.dvo";

export abstract class VolunteerActiveUpsertComponent implements OnDestroy {
  protected destroyUtil: DestroyUtil = new DestroyUtil();

  @Input()
  alertModel: AlertModel;

  @Input()
  public volunteerInstance: VolunteerInstanceDVO = new VolunteerInstanceDVO();

  @Input()
  protected volunteer: VolunteerDVO;

  @Output()
  didHttpRequest = new EventEmitter<string>();

  @Output()
  onBackEmitter = new EventEmitter();

  constructor(public title: string,
              protected volunteerService: VolunteerService) { }

  onSubmit() {
    this.volunteerInstance.volunteerDVO = this.volunteer;

    this.destroyUtil.addSubscription(this.doHttpRequest());
  }

  abstract doHttpRequest();

  onBack() {
    this.onBackEmitter.emit(true);
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }
}
