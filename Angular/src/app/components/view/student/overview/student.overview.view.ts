import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {AlertModel} from "../../../block/alert/alert.model";
import {Subscription} from "rxjs/Subscription";
import {FormUtil} from "../../../../util/form.util";
import {StudentService} from "../../../../services/student.service";
import {StudentVOSearch} from "../../../../valueobject/student.vo.search";

@Component({
  selector: 'app-student-overview-view',
  templateUrl: './student.overview.view.html',
  styleUrls: ['./student.overview.view.css']
})
export class StudentOverviewView implements OnInit {
  public alertModel = new AlertModel();
  students: StudentVOSearch[] = [];
  currentHttpRequest: Subscription = null;

  // Searching parameters
  public inputSearch: String;
  public hasMatch: Boolean = false; // default value

  // Values for dropdown boxes.
  public yesNoNull = FormUtil.yesNoNull;

  constructor(private studentService: StudentService,
              public router: Router,
              public route: ActivatedRoute) { }

  ngOnInit(): void {
    this.searchStudents();
  }

  searchStudents() {
    // If you keep typing in a box, it will keep requesting to the server.
    if (this.currentHttpRequest != null) {
      this.currentHttpRequest.unsubscribe();
    }

    this.currentHttpRequest = this.studentService.searchStudents(
      this.inputSearch, this.hasMatch
    ).subscribe(
      (students: StudentVOSearch[]) => {
        this.students = students;
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      });
  }

  onDblClick(student: StudentVOSearch) {
    // Go to the volunteer.
    this.router.navigate([student.studentDVO.externalIdentifier], {relativeTo: this.route});
  }
}
