import {Component} from "@angular/core";
import {HttpErrorResponse} from "@angular/common/http";
import {StudentUpsertComponent} from "./student.upsert.component";
import {StudentService} from "../../../../../services/student.service";
import {StringVO} from "../../../../../valueobject/string.vo";

@Component({
  selector: 'app-student-add',
  templateUrl: './student.upsert.component.html',
})
export class StudentAddComponent extends StudentUpsertComponent {

  constructor(protected studentService: StudentService) {
    super('Toevoegen cursist');
  }

  doHttpRequest() {
    return this.studentService.insertStudent(this.student).subscribe(
      (studentExtId: StringVO) => this.didHttpRequest.emit(studentExtId.value),
      (error: HttpErrorResponse) =>  this.alertModel.setError(error)
    );
  }
}
