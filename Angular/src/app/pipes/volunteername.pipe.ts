import {Pipe, PipeTransform} from "@angular/core";
import {VolunteerModel} from "../valueobject/volunteer.model";

@Pipe({
  name: 'parsevolunteername'
})
export class VolunteerNamePipe implements PipeTransform {
  transform(volunteer: VolunteerModel) {
    return VolunteerNamePipe.parseName(volunteer);
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
    if (!VolunteerNamePipe.isEmptyString(volunteer.firstName)) {
      name += volunteer.firstName + ' ';
    }
    if (!VolunteerNamePipe.isEmptyString(volunteer.insertion)) {
      name += volunteer.insertion + ' ';
    }
    if (!VolunteerNamePipe.isEmptyString(volunteer.lastName)) {
      name += volunteer.lastName;
    }
    // Possible that we end with a space, so we remove this one.
    return name.trim();
  }


  /**
   * Search for a string in firstName, insertion or lastName.
   * @param {string} search
   * @param {VolunteerModel} volunteer
   * @returns {boolean} Return true if the search string can be found in firstName, insertion or lastName.
   */
  public static containsString(search: string, volunteer: VolunteerModel): boolean {
    if (!search || search.length == 0 || search == " ") {
      return true;
    }
    if (volunteer.firstName && volunteer.firstName.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    if (volunteer.insertion && volunteer.insertion.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    // noinspection RedundantIfStatementJS
    if (volunteer.lastName && volunteer.lastName.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    return false;
  }
}
