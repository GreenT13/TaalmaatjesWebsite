import {Component, Input} from '@angular/core';
import {StudentDVO} from "../../../../../valueobject/dvo/student.dvo";

@Component({
  selector: 'app-student-detail',
  templateUrl: './student.detail.component.html',
})
export class StudentDetailComponent {
  @Input()
  public student: StudentDVO = new StudentDVO();
}
