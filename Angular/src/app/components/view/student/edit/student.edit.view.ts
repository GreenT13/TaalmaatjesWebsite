import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../../block/alert/alert.model";
import {GenderUtil} from "../../../../util/gender.util";
import {StudentService} from "../../../../services/student.service";
import {DestroyUtil} from "../../../../util/destroy.util";
import {StudentVOGet} from "../../../../valueobject/student.vo.get";

@Component({
  selector: 'app-student-edit-view',
  templateUrl: './student.edit.view.html'
})
export class StudentEditView implements OnInit, OnDestroy {
  private destroyUtil: DestroyUtil = new DestroyUtil();
  public alertModel = new AlertModel();
  public student: StudentVOGet = new StudentVOGet();
  public genders = GenderUtil.genders;

  ngOnInit(): void {
    this.destroyUtil.addSubscription(this.route.params.subscribe(
      (params) => {
        this.studentService.getStudent(params['studentExtId']).subscribe(
          (response: StudentVOGet) => {
            this.student = response;
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the volunteer on screen if there was any.
            this.student = new StudentVOGet();
          }
        );
      }
    ));
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
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
