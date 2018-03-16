import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'mydate'
})
export class MyDatePipe implements PipeTransform {
  transform(value: string, nowIfNull: boolean) {
    if (!value || value.length == 0) {
      if (nowIfNull == true) {
        return 'nu';
      } else {
        return '';
      }
    }

    // We assume the date is in format yyyy-MM-dd and transform it into dd-MM-yyyy.
    const year = value.substr(0, value.indexOf('-'));
    const month = value.substr(value.indexOf('-') + 1, value.substr(value.indexOf('-') + 1).indexOf('-'));
    const day = value.substr(value.lastIndexOf('-') + 1);
    return day + '-' + month + '-' + year;
  }
}
