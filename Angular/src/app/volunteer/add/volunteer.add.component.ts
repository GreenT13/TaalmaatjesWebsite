import {Component, OnInit} from "@angular/core";
import {IMyDpOptions} from 'mydatepicker';
import {DateUtil} from "../../util/date.util";
import {VolunteerModel} from "../../valueobject/volunteer.model";
import {GenderUtil} from "../../util/gender.util";
import {VolunteerService} from "../../services/volunteer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SingleStringModel} from "../../valueobject/singlestring.model";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-volunteer-add',
  templateUrl: './volunteer.add.component.html',
  styleUrls: ['./volunteer.add.component.css']
})
export class VolunteerAddComponent implements OnInit {
  public error;
  public genders = GenderUtil.genders;
  public volunteer: VolunteerModel;
  public dateOfBirth;
  public dateTraining;
  public dateStartActive;

  public optionsBeforeToday: IMyDpOptions = {
    disableSince: DateUtil.getCurrentIDate(),
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };
  public optionsAll: IMyDpOptions = {
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };

  setNewGender(id: any) {
    this.volunteer.gender = id;
  }

  ngOnInit(): void {
    this.volunteer = new VolunteerModel();
    this.setNewGender(GenderUtil.getMaleId());
    this.volunteer.isClassAssistant = false;
    this.volunteer.isTaalmaatje = true;
    this.dateStartActive = {date: DateUtil.getCurrentIDate()};
  }

  constructor(private volunteerService: VolunteerService,
              private router: Router, private route: ActivatedRoute) { }

  onSubmit() {
    this.volunteer.dateOfBirth = DateUtil.convertDateIDateToString(this.dateOfBirth);
    this.volunteer.dateTraining = DateUtil.convertDateIDateToString(this.dateTraining);

    this.volunteerService.insertVolunteer(this.volunteer, DateUtil.convertDateIDateToString(this.dateStartActive)).subscribe(
      (volunteerExtId: SingleStringModel) => {
        this.router.navigate(['../' + volunteerExtId.value], {relativeTo: this.route});
      },
      (error: HttpErrorResponse) => {
        this.error = error;
      }
    );
  }
}
