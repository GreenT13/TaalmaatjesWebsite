import {Component, OnInit} from "@angular/core";
import {DateUtil} from "../../../util/date.util";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {GenderUtil} from "../../../util/gender.util";
import {VolunteerService} from "../../../services/volunteer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SingleStringModel} from "../../../valueobject/singlestring.model";
import {HttpErrorResponse} from "@angular/common/http";
import {AlertModel} from "../../alert/alert.model";

@Component({
  selector: 'app-volunteer-add',
  templateUrl: './volunteer.add.component.html',
  styleUrls: ['./volunteer.add.component.css']
})
export class VolunteerAddComponent implements OnInit {
  public alertModel = new AlertModel();
  public genders = GenderUtil.genders;
  public volunteer: VolunteerModel;
  public dateStartActive: string;

  setNewGender(id: any) {
    this.volunteer.gender = id;
  }

  ngOnInit(): void {
    this.volunteer = new VolunteerModel();
    this.setNewGender(GenderUtil.getMaleId());
    this.volunteer.isClassAssistant = false;
    this.volunteer.isTaalmaatje = true;
    this.dateStartActive = DateUtil.getCurrentDate();
  }

  constructor(private volunteerService: VolunteerService,
              private router: Router, private route: ActivatedRoute) { }

  onSubmit() {
    this.volunteerService.insertVolunteer(this.volunteer, this.dateStartActive).subscribe(
      (volunteerExtId: SingleStringModel) => {
        this.router.navigate(['../' + volunteerExtId.value], {relativeTo: this.route});
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      }
    );
  }
}
