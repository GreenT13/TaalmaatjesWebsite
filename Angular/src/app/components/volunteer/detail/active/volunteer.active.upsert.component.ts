import {EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from "@angular/core";
import {VolunteerInstanceModel} from "../../../../valueobject/volunteerinstance.model";
import {VolunteerModel} from "../../../../valueobject/volunteer.model";
import {DestroyUtil} from "../../../../util/destroy.util";
import {VolunteerService} from "../../../../services/volunteer.service";
import {AlertModel} from "../../../alert/alert.model";

export abstract class VolunteerActiveComponent implements OnInit, OnDestroy, OnChanges {
  protected destroyUtil: DestroyUtil = new DestroyUtil();

  @Input()
  alertModel: AlertModel;

  @Input()
  public volunteerInstanceModel: VolunteerInstanceModel =  new VolunteerInstanceModel();

  @Input()
  protected volunteer: VolunteerModel;

  @Output()
  didHttpRequest = new EventEmitter<string>();

  @Output()
  onBackEmitter = new EventEmitter();

  constructor(public title: string,
              protected volunteerService: VolunteerService) { }

  ngOnInit(): void {
    this.volunteerInstanceModel.volunteerExtId = this.volunteer.externalIdentifier;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.volunteerInstanceModel) {
      this.volunteerInstanceModel.volunteerExtId = this.volunteer.externalIdentifier;
    }
  }

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
