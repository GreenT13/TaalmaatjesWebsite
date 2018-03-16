import {OnInit} from "@angular/core";
import {VolunteerDetailService} from "../volunteer.detail.service";
import {VolunteerService} from "../../../services/volunteer.service";
import {DateUtil} from "../../../util/date.util";
import {VolunteerInstanceModel} from "../../../valueobject/volunteerinstance.model";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {ActivatedRoute} from "@angular/router";
import {IMyDpOptions} from "mydatepicker";

export abstract class VolunteerActiveComponent implements OnInit {
  public dateStartActive;
  public dateEndActive;
  public optionsAll: IMyDpOptions = {
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };
  public volunteerInstanceModel: VolunteerInstanceModel;
  protected volunteer: VolunteerModel;
  public error = null;

  constructor(protected volunteerService: VolunteerService,
              protected volunteerDetailService: VolunteerDetailService,
              protected route: ActivatedRoute,
              public title: string) {
  }

  ngOnInit(): void {
    // Instantiate to prevent errors.
    this.volunteerInstanceModel = new VolunteerInstanceModel();

    // Retrieve the volunteer from the service.
    this.volunteerDetailService.getVolunteer().subscribe(
      (volunteer: VolunteerModel) => {
        this.volunteerInstanceModel.volunteerExtId = volunteer.externalIdentifier;
        this.volunteer = volunteer;

        this.retrieveSpecificInstance();
      }
    );
  }

  retrieveSpecificInstance() { }

  onSubmit() {
    if (this.dateStartActive != undefined) {
      this.volunteerInstanceModel.dateStart = DateUtil.convertIDateToString(this.dateStartActive.date);
    }
    if (this.dateEndActive != undefined) {
      this.volunteerInstanceModel.dateEnd = DateUtil.convertIDateToString(this.dateEndActive.date);
    }

    this.doHttpRequest();
  }

  abstract doHttpRequest();
}
