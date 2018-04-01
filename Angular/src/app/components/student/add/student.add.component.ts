import {Component, OnInit} from "@angular/core";
import {GenderUtil} from "../../../util/gender.util";
import {ActivatedRoute, Router} from "@angular/router";
import {SingleStringModel} from "../../../valueobject/singlestring.model";
import {HttpErrorResponse} from "@angular/common/http";
import {AlertModel} from "../../alert/alert.model";
import {StudentModel} from "../../../valueobject/student.model";
import {StudentService} from "../../../services/student.service";

@Component({
  selector: 'app-student-add',
  templateUrl: './student.add.component.html',
  styleUrls: ['./student.add.component.css']
})
export class StudentAddComponent implements OnInit {
  public alertModel = new AlertModel();
  public genders = GenderUtil.genders;
  public student: StudentModel;

  setNewGender(id: any) {
    this.student.gender = id;
  }

  ngOnInit(): void {
    this.student = new StudentModel();
    this.setNewGender(GenderUtil.getMaleId());
  }

  constructor(private studentService: StudentService,
              private router: Router, private route: ActivatedRoute) { }

  onSubmit() {
    this.studentService.insertStudent(this.student).subscribe(
      (studentExtId: SingleStringModel) => {
        this.router.navigate(['../' + studentExtId.value], {relativeTo: this.route});
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      }
    );
  }
}
