import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'parsename'
})
export class NamePipe implements PipeTransform {
  transform(object) {
    return NamePipe.parseName(object);
  }

  public static isEmptyString(value: string): boolean {
    // When doing !object, it checks for null or undefined.
    return !value || value.length == 0 || value.trim().length == 0;
  }

  public static parseName(object): string {
    if (!object) {
      return '';
    }

    let name: string = '';
    if (!NamePipe.isEmptyString(object.firstName)) {
      name += object.firstName + ' ';
    }
    if (!NamePipe.isEmptyString(object.insertion)) {
      name += object.insertion + ' ';
    }
    if (!NamePipe.isEmptyString(object.lastName)) {
      name += object.lastName;
    }
    // Possible that we end with a space, so we remove this one.
    return name.trim();
  }


  /**
   * Search for a string in firstName, insertion or lastName.
   * @param {string} search
   * @param object
   * @returns {boolean} Return true if the search string can be found in firstName, insertion or lastName.
   */
  public static containsString(search: string, object): boolean {
    if (!search || search.length == 0 || search == " ") {
      return true;
    }
    if (object.firstName && object.firstName.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    if (object.insertion && object.insertion.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    // noinspection RedundantIfStatementJS
    if (object.lastName && object.lastName.toLowerCase().indexOf(search.toLowerCase()) > -1) {
      return true;
    }

    return false;
  }
}
