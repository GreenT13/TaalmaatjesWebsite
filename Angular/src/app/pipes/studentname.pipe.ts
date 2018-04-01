import {Pipe, PipeTransform} from "@angular/core";
import {VolunteerModel} from "../valueobject/volunteer.model";
import {StudentModel} from "../valueobject/student.model";

@Pipe({
  name: 'parsestudentname'
})
export class StudentNamePipe implements PipeTransform {
  transform(volunteer: VolunteerModel) {
    return StudentNamePipe.parseName(volunteer);
  }

  public static isEmptyString(value: string): boolean {
    // When doing !object, it checks for null or undefined.
    return !value || value.length == 0 || value.trim().length == 0;
  }

  public static parseName(volunteer: VolunteerModel): string {
    if (!volunteer) {
      return '';
    }

    let name: string = '';
    if (!StudentNamePipe.isEmptyString(volunteer.firstName)) {
      name += volunteer.firstName + ' ';
    }
    if (!StudentNamePipe.isEmptyString(volunteer.insertion)) {
      name += volunteer.insertion + ' ';
    }
    if (!StudentNamePipe.isEmptyString(volunteer.lastName)) {
      name += volunteer.lastName;
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
