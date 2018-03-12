import {Component, OnInit} from "@angular/core";
import {IMyDpOptions} from "mydatepicker";
import {VolunteerInstanceModel} from "../../../valueobject/volunteerinstance.model";
import {ActivatedRoute} from "@angular/router";
import {VolunteerService} from "../../../services/volunteer.service";
import {DateUtil} from "../../../util/date.util";

@Component({
  selector: 'app-volunteer-edit-active',
  templateUrl: './volunteer.edit.active.component.html',
  styleUrls: ['./volunteer.edit.active.component.css']
})
export class VolunteerEditActiveComponent implements OnInit {
  public dateStartActive;
  public dateEndActive;
  public optionsAll: IMyDpOptions = {
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };
  private volunteerInstanceModel: VolunteerInstanceModel;

  constructor(private volunteerService: VolunteerService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params) => {
        if (params['volunteerInstanceExtId'] != undefined) {
          // Find the one from the volunteer.
          //this.volunteerInstanceModel = this.volunteerService.currentVolunteer.volunteerInstanceValueObjects.find()
          // Prefill the values.
        } else {
          // New instance, so initialize new one.
          this.volunteerInstanceModel = new VolunteerInstanceModel();
          this.volunteerInstanceModel.volunteerExtId = this.volunteerService.currentVolunteer.externalIdentifier;
        }
      }
    );
  }

  onSubmit() {
    if (this.dateStartActive != undefined) {
      this.volunteerInstanceModel.dateStart = DateUtil.convertIDateToString(this.dateStartActive.date);
    }
    if (this.dateEndActive != undefined) {
      this.volunteerInstanceModel.dateEnd = DateUtil.convertIDateToString(this.dateEndActive.date);
    }
    console.log(this.volunteerInstanceModel);
    this.volunteerService.insertVolunteerInstance(this.volunteerInstanceModel).subscribe(
      (response: Response) => {
        console.log(response);
      }
    );
  }

}
