import {Pipe, PipeTransform} from "@angular/core";
import {StudentModel} from "../valueobject/student.model";

@Pipe({
  name: 'parsestudentname'
})
export class StudentNamePipe implements PipeTransform {
  transform(student: StudentModel) {
    return StudentNamePipe.parseName(student);
  }

  public static isEmptyString(value: string): boolean {
    // When doing !object, it checks for null or undefined.
    return !value || value.length == 0 || value.trim().length == 0;
  }

  public static parseName(student: StudentModel): string {
    if (!student) {
      return '';
    }

    let name: string = '';
    if (!StudentNamePipe.isEmptyString(student.firstName)) {
      name += student.firstName + ' ';
    }
    if (!StudentNamePipe.isEmptyString(student.insertion)) {
      name += student.insertion + ' ';
    }
    if (!StudentNamePipe.isEmptyString(student.lastName)) {
      name += student.lastName;
    }
    // Possible that we end with a space, so we remove this one.
    return name.trim();
  }


  /**
   * Search for a string in firstName, insertion or lastName.
   * @param {string} search
   * @param {StudentModel} student
   * @returns {boolean} Return true if the search string can be found in firstName, insertion or lastName.
   */
  public static containsString(search: string, student: StudentModel): boolean {
    if (!search || search.length == 0 || search == " ") {
      return true;
    }
    if (student.firstName && student.firstName.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    if (student.insertion && student.insertion.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    // noinspection RedundantIfStatementJS
    if (student.lastName && student.lastName.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    return false;
  }
}
