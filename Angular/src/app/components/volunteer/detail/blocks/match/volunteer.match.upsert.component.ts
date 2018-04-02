import {EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from "@angular/core";
import {DestroyUtil} from "../../../../../util/destroy.util";
import {AlertModel} from "../../../../alert/alert.model";
import {VolunteerModel} from "../../../../../valueobject/volunteer.model";
import {VolunteerService} from "../../../../../services/volunteer.service";
import {VolunteerMatchModel} from "../../../../../valueobject/volunteermatch.model";

export abstract class VolunteerMatchComponent implements OnInit, OnDestroy, OnChanges {
  protected destroyUtil: DestroyUtil = new DestroyUtil();

  @Input()
  alertModel: AlertModel;

  @Input()
  public volunteerMatchModel: VolunteerMatchModel = new VolunteerMatchModel();

  @Input()
  protected volunteer: VolunteerModel;

  @Output()
  didHttpRequest = new EventEmitter<string>();

  @Output()
  onBackEmitter = new EventEmitter();

  constructor(public title: string,
              protected volunteerService: VolunteerService) { }

  ngOnInit(): void {
    if (this.volunteer) {
      this.volunteerMatchModel.volunteerValueObject = this.volunteer;
    }
    this.volunteerMatchModel.volunteerValueObject.externalIdentifier = this.volunteer.externalIdentifier;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.volunteerMatchModel) {
      this.volunteerMatchModel.volunteerValueObject = this.volunteer;
    }
  }

  onSubmit() {
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
