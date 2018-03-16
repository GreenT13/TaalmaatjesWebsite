import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'boolean'
})
export class BooleanPipe implements PipeTransform {
  transform(value: boolean) {
    if (value == true) {
      return 'Ja';
    } else if (value == false) {
      return 'Nee'
    } else {
      return 'Onbekend';
    }
  }
}
