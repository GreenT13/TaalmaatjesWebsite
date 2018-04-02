import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AlertModel} from "../../alert/alert.model";
import {DestroyUtil} from "../../../util/destroy.util";
import {StudentService} from "../../../services/student.service";
import {StudentModel} from "../../../valueobject/student.model";

@Component({
  selector: 'app-student-detail',
  templateUrl: './student.detail.component.html',
  styleUrls: ['./student.detail.component.css']
})
export class StudentDetailComponent implements OnInit, OnDestroy {
  private destroyUtil: DestroyUtil = new DestroyUtil();
  public alertModel = new AlertModel();

  public student: StudentModel = new StudentModel();

  constructor(private studentService: StudentService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.destroyUtil.addSubscription(this.route.params.subscribe(
      (params) => {
        this.studentService.getStudent(params['studentExtId']).subscribe(
          (response: StudentModel) => {
            this.student = response;
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the volunteer on screen if there was any.
            this.student = new StudentModel();
          }
        );
      }));
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }


}
