import {Injectable} from "@angular/core";
import {VolunteerModel} from "../valueobject/volunteer.model";

@Injectable()
export class NameUtil {

  public static isEmptyString(value: string): boolean {
    return value === null || value.length == 0 || value.trim().length == 0;
  }

  public static parseName(volunteer: VolunteerModel): string {
    var name: string = '';
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
