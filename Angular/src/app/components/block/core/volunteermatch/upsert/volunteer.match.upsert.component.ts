import {EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {DestroyUtil} from "../../../../../util/destroy.util";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {VolunteerMatchDVO} from "../../../../../valueobject/dvo/volunteermatch.dvo";
import {VolunteerDVO} from "../../../../../valueobject/dvo/volunteer.dvo";
import {AlertModel} from "../../../alert/alert.model";

export abstract class VolunteerMatchComponent implements OnDestroy {
  protected destroyUtil: DestroyUtil = new DestroyUtil();

  @Input()
  alertModel: AlertModel;

  @Input()
  public volunteerMatch: VolunteerMatchDVO = new VolunteerMatchDVO();

  @Input()
  protected volunteer: VolunteerDVO;

  @Output()
  didHttpRequest = new EventEmitter<string>();

  @Output()
  onBackEmitter = new EventEmitter();

  constructor(public title: string,
              protected volunteerService: VolunteerService) { }

  onSubmit() {
    this.volunteerMatch.volunteerDVO = this.volunteer;

    this.destroyUtil.addSubscription(this.doHttpRequest());
  }

  abstract doHttpRequest();

  delete() { }

  onBack() {
    this.onBackEmitter.emit(true);
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }
}
