import {Component} from "@angular/core";
import {HttpErrorResponse} from "@angular/common/http";
import {StudentUpsertComponent} from "./student.upsert.component";
import {StudentService} from "../../../../../services/student.service";

@Component({
  selector: 'app-student-edit',
  templateUrl: './student.upsert.component.html',
})
export class StudentEditComponent extends StudentUpsertComponent {

  constructor(protected studentService: StudentService) {
    super('Wijzigen cursist');
  }

  doHttpRequest() {
    return this.studentService.updateStudent(this.student).subscribe(
      () => this.didHttpRequest.emit(this.student.externalIdentifier),
      (error: HttpErrorResponse) =>  this.alertModel.setError(error)
    );
  }
}
