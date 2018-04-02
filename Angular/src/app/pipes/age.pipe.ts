import {Pipe, PipeTransform} from "@angular/core";
import * as moment from 'moment';

@Pipe({
  name: 'age'
})
export class AgePipe implements PipeTransform {

  transform(dateOfBirth: string): number {
    console.log(dateOfBirth);
    if (!dateOfBirth) {
      return null;
    }

    return moment().diff(moment(dateOfBirth, "DD-MM-YYYY"), 'years');
  }
}
