import {VolunteerModel} from "../valueobject/volunteer.model";

export class NameUtil {

  public static isEmptyString(value: string): boolean {
    // When doing !object, it checks for null or undefined.
    return !value || value.length == 0 || value.trim().length == 0;
  }

  public static parseName(volunteer: VolunteerModel): string {
    if (!volunteer) {
      return '';
    }

    let name: string = '';
    if (!NameUtil.isEmptyString(volunteer.firstName)) {
      name += volunteer.firstName + ' ';
    }
    if (!NameUtil.isEmptyString(volunteer.insertion)) {
      name += volunteer.insertion + ' ';
    }
    if (!NameUtil.isEmptyString(volunteer.lastName)) {
      name += volunteer.lastName;
    }
    // Possible that we end with a space, so we remove this one.
    return name.trim();
  }

}
