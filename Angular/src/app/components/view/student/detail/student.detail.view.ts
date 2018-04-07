import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DestroyUtil} from "../../../../util/destroy.util";
import {AlertModel} from "../../../block/alert/alert.model";
import {StudentVOGet} from "../../../../valueobject/student.vo.get";
import {StudentService} from "../../../../services/student.service";

@Component({
  selector: 'app-student-detail-view',
  templateUrl: './student.detail.view.html',
})
export class StudentDetailView implements OnInit, OnDestroy {
  private destroyUtil: DestroyUtil = new DestroyUtil();
  public alertModel = new AlertModel();

  public student: StudentVOGet = new StudentVOGet();

  constructor(private studentService: StudentService,
              private router: Router,
              private route: ActivatedRoute) { }

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
        )
      }
    ));
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }

  onEdit() {
    this.router.navigate(['edit'], {relativeTo: this.route});
  }

}
