import {Component, EventEmitter, Input, Output, SimpleChanges} from "@angular/core";
import {StudentDVO} from "../../../../valueobject/dvo/student.dvo";
import {StudentService} from "../../../../services/student.service";
import {NamePipe} from "../../../../pipes/name.pipe";

@Component({
  selector: 'app-autocomplete-student',
  templateUrl: './autocomplete.student.component.html',
  styleUrls: ['./autocomplete.student.component.css']
})
export class AutocompleteStudentComponent {
  public query: string = "";
  public students: StudentDVO[];
  public filteredList = [];

  @Input()
  public disabled: boolean = false;
  @Input()
  public student: StudentDVO;
  @Output()
  studentChange = new EventEmitter<StudentDVO>();

  constructor(private studentService: StudentService) {
    this.studentService.getStudentForMatch().subscribe(
      (students: StudentDVO[]) => {
        this.students = students;
    });
  }

  ngOnChanges(changes: SimpleChanges){
    // Allow prefilling default values. Make sure it doesn't overwrite if we want to overwrite with an empty volunteer.
    if (changes.student && NamePipe.parseName(changes.student.currentValue).length > 0) {
      this.query = NamePipe.parseName(changes.student.currentValue);
    }
  }

  filter() {
    if (this.query !== ""){
      // Filter the list, with at most 10 elements.
      let count = 0;
      this.filteredList = this.students.filter(function(student: StudentDVO){
        if (count > 10) {
          return false;
        }
        for (let section of this.query.split(" ")) {
          if (!NamePipe.containsString(section, student)) {
            return false;
          }
        }
        count++;
        return true;
      }.bind(this));
      this.setStudent(new StudentDVO());
    } else {
      this.filteredList = [];
      this.setStudent(new StudentDVO());
    }
  }

  select(student: StudentDVO){
    this.query = NamePipe.parseName(student);
    this.setStudent(student);
    this.filteredList = [];
  }

  setStudent(student: StudentDVO) {
    this.student = student;
    this.studentChange.emit(this.student);
  }
}
