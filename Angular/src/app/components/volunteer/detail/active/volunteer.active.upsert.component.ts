import {EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from "@angular/core";
import {DateUtil} from "../../../../util/date.util";
import {VolunteerInstanceModel} from "../../../../valueobject/volunteerinstance.model";
import {VolunteerModel} from "../../../../valueobject/volunteer.model";
import {IMyDpOptions} from "mydatepicker";
import {DestroyUtil} from "../../../../util/destroy.util";
import {VolunteerService} from "../../../../services/volunteer.service";
import {AlertModel} from "../../../alert/alert.model";

export abstract class VolunteerActiveComponent implements OnInit, OnDestroy, OnChanges {
  protected destroyUtil: DestroyUtil = new DestroyUtil();

  public dateStartActive;
  public dateEndActive;
  public optionsAll: IMyDpOptions = {
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };

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
    this.dateStartActive = {date: DateUtil.convertStringToIDate(this.volunteerInstanceModel.dateStart)};
    this.dateEndActive = {date: DateUtil.convertStringToIDate(this.volunteerInstanceModel.dateEnd)};
    this.volunteerInstanceModel.volunteerExtId = this.volunteer.externalIdentifier;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.volunteerInstanceModel) {
      this.dateStartActive = {date: DateUtil.convertStringToIDate(this.volunteerInstanceModel.dateStart)};
      this.dateEndActive = {date: DateUtil.convertStringToIDate(this.volunteerInstanceModel.dateEnd)};
      this.volunteerInstanceModel.volunteerExtId = this.volunteer.externalIdentifier;
    }
  }

  onSubmit() {
    if (this.dateStartActive != undefined) {
      this.volunteerInstanceModel.dateStart = DateUtil.convertIDateToString(this.dateStartActive.date);
    }
    if (this.dateEndActive != undefined) {
      this.volunteerInstanceModel.dateEnd = DateUtil.convertIDateToString(this.dateEndActive.date);
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
