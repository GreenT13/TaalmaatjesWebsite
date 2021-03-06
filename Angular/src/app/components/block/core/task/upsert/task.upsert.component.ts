import {EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {AlertModel} from "../../../alert/alert.model";
import {DestroyUtil} from "../../../../../util/destroy.util";
import {TaskDVO} from "../../../../../valueobject/dvo/task.dvo";
import {VolunteerDVO} from "../../../../../valueobject/dvo/volunteer.dvo";

export abstract class TaskUpsertComponent implements OnDestroy {
  public triedSubmit = false;
  protected destroyUtil: DestroyUtil = new DestroyUtil();

  @Input()
  alertModel: AlertModel;

  @Input()
  public task: TaskDVO = new TaskDVO();

  @Input()
  public prefillVolunteer: boolean = false;

  @Input()
  volunteer: VolunteerDVO;

  @Output()
  didHttpRequest = new EventEmitter<string>();

  @Output()
  onBackEmitter = new EventEmitter();

  constructor(public title: string) { }

  onSubmit() {
    if (this.prefillVolunteer) {
      this.task.volunteerDVO = this.volunteer;
    }

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
