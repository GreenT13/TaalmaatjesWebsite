import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'gender'
})
export class GenderPipe implements PipeTransform {
  transform(value: string) {
    if (value == 'M') {
      return 'Man';
    } else if (value == 'V') {
      return 'Vrouw';
    } else {
      return 'Onbekend';
    }
  }
}
