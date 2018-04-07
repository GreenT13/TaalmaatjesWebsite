import {EventEmitter, Input, OnDestroy, Output} from "@angular/core";
import {AlertModel} from "../../../alert/alert.model";
import {DestroyUtil} from "../../../../../util/destroy.util";
import {StudentDVO} from "../../../../../valueobject/dvo/student.dvo";
import {GenderUtil} from "../../../../../util/gender.util";
import {DateUtil} from "../../../../../util/date.util";

export abstract class StudentUpsertComponent implements OnDestroy {
  public triedSubmit = false;
  public REGEX_DATE = DateUtil.REGEX_DATE;
  public genders = GenderUtil.genders;
  protected destroyUtil: DestroyUtil = new DestroyUtil();

  @Input()
  alertModel: AlertModel;

  @Input()
  public student: StudentDVO = new StudentDVO();

  @Output()
  didHttpRequest = new EventEmitter<string>();

  @Output()
  onBackEmitter = new EventEmitter();

  constructor(public title: string) { }

  onSubmit() {
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
