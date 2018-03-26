import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'mydate'
})
export class MyDatePipe implements PipeTransform {
  transform(value: string) {
    if (value == null || !value || value.length == 0) {
      return 'nu';
    } else {
      return value;
    }
  }
}
