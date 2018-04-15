import {Component} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../../block/alert/alert.model";
import {GenderUtil} from "../../../../util/gender.util";
import {StudentService} from "../../../../services/student.service";

@Component({
  selector: 'app-report-create-view',
  templateUrl: './report.create.view.html'
})
export class ReportCreateView {
  public triedSubmit = false;

  public alertModel = new AlertModel();
  public genders = GenderUtil.genders;
  public dateStart: Date;
  public dateEnd: Date;

  onSubmit() {
    this.router.navigate(['view'], {queryParams: { dateStart: this.dateStart, dateEnd: this.dateEnd }, relativeTo: this.route});
  }

  constructor(private studentService: StudentService,
              private router: Router, private route: ActivatedRoute) { }

  didHttpRequest() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }

  onBack() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
