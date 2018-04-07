import {Component, EventEmitter, Input, Output} from '@angular/core';
import {StudentDVO} from "../../../../../valueobject/dvo/student.dvo";

@Component({
  selector: 'app-student-detail',
  templateUrl: './student.detail.component.html',
})
export class StudentDetailComponent {
  @Input()
  public student: StudentDVO = new StudentDVO();

  @Output()
  onEditEmitter = new EventEmitter<boolean>();

  onEdit() {
    this.onEditEmitter.emit(true);
  }

}
