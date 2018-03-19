import {OnDestroy, OnInit} from "@angular/core";
import {VolunteerDetailService} from "../volunteer.detail.service";
import {VolunteerService} from "../../../services/volunteer.service";
import {DateUtil} from "../../../util/date.util";
import {VolunteerInstanceModel} from "../../../valueobject/volunteerinstance.model";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {ActivatedRoute, Router} from "@angular/router";
import {IMyDpOptions} from "mydatepicker";
import {AlertModel} from "../../../alert/alert.model";
import {DestroyUtil} from "../../../util/destroy.util";

export abstract class VolunteerActiveComponent implements OnInit, OnDestroy {
  protected destroyUtil: DestroyUtil = new DestroyUtil();
  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }

  public dateStartActive;
  public dateEndActive;
  public optionsAll: IMyDpOptions = {
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };
  public volunteerInstanceModel: VolunteerInstanceModel;
  protected volunteer: VolunteerModel;
  public alertModel = new AlertModel();

  constructor(protected volunteerService: VolunteerService,
              protected volunteerDetailService: VolunteerDetailService,
              protected router: Router,
              protected route: ActivatedRoute,
              public title: string) {
  }

  ngOnInit(): void {
    // Instantiate to prevent errors.
    this.volunteerInstanceModel = new VolunteerInstanceModel();

    // Retrieve the volunteer from the service.
    this.destroyUtil.addSubscription(this.volunteerDetailService.getVolunteer().subscribe(
      (volunteer: VolunteerModel) => {
        this.volunteerInstanceModel.volunteerExtId = volunteer.externalIdentifier;
        this.volunteer = volunteer;

        this.retrieveSpecificInstance();
      }
    ));
  }

  retrieveSpecificInstance() { }

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

  delete() { }
}
