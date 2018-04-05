import {Component, OnInit} from "@angular/core";
import {AlertModel} from "../../../block/alert/alert.model";
import {GenderUtil} from "../../../../util/gender.util";
import {VolunteerDVO} from "../../../../valueobject/dvo/volunteer.dvo";
import {ActivatedRoute, Router} from "@angular/router";
import {StringVO} from "../../../../valueobject/string.vo";
import {HttpErrorResponse} from "@angular/common/http";
import {VolunteerService} from "../../../../services/volunteer.service";

@Component({
  selector: 'app-volunteer-add-view',
  templateUrl: './volunteer.add.view.html'
})
export class VolunteerAddView implements OnInit {
  public alertModel = new AlertModel();
  public genders = GenderUtil.genders;
  public volunteer: VolunteerDVO = new VolunteerDVO();
  public dateStartActive: string;

  ngOnInit(): void {
    this.volunteer.isClassAssistant = false;
    this.volunteer.isTaalmaatje = true;
  }

  constructor(private volunteerService: VolunteerService,
              private router: Router, private route: ActivatedRoute) { }

  onSubmit() {
    this.volunteerService.insertVolunteer(this.volunteer, this.dateStartActive).subscribe(
      (volunteerExtId: StringVO) => {
        this.router.navigate(['../' + volunteerExtId.value], {relativeTo: this.route});
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      }
    );
  }

  onBack() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
