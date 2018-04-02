import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from "@angular/core";
import {StudentModel} from "../../../valueobject/student.model";
import {StudentService} from "../../../services/student.service";
import {StudentNamePipe} from "../../../pipes/studentname.pipe";

@Component({
  selector: 'app-autocomplete-student',
  templateUrl: './autocomplete.student.component.html',
  styleUrls: ['./autocomplete.student.component.css']
})
export class AutocompleteStudentComponent implements OnChanges {
  public query: string = "";
  public students: StudentModel[];
  public filteredList = [];

  @Input()
  public disabled: boolean = false;
  @Input()
  public student: StudentModel;
  @Output()
  studentChange = new EventEmitter<StudentModel>();

  constructor(private studentService: StudentService) {
    this.studentService.getStudentForMatch().subscribe(
      (students: StudentModel[]) => {
        this.students = students;
    });
  }

  ngOnChanges(changes: SimpleChanges){
    // Allow prefilling default values. Make sure it doesn't overwrite if we want to overwrite with an empty volunteer.
    if (changes.student && StudentNamePipe.parseName(changes.student.currentValue).length > 0) {
      this.query = StudentNamePipe.parseName(changes.student.currentValue);
    }
  }

  filter() {
    if (this.query !== ""){
      // Filter the list, with at most 10 elements.
      let count = 0;
      this.filteredList = this.students.filter(function(student: StudentModel){
        if (count > 10) {
          return false;
        }
        for (let section of this.query.split(" ")) {
          if (!StudentNamePipe.containsString(section, student)) {
            return false;
          }
        }
        count++;
        return true;
      }.bind(this));
      this.setStudent(new StudentModel());
    } else {
      this.filteredList = [];
      this.setStudent(new StudentModel());
    }
  }

  select(student: StudentModel){
    this.query = StudentNamePipe.parseName(student);
    this.setStudent(student);
    this.filteredList = [];
  }

  setStudent(student: StudentModel) {
    this.student = student;
    this.studentChange.emit(this.student);
  }
}
