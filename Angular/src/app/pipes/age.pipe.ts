import {Pipe, PipeTransform} from "@angular/core";
import * as moment from 'moment';

@Pipe({
  name: 'age'
})
export class AgePipe implements PipeTransform {

  transform(dateOfBirth: string): number {
    if (!dateOfBirth) {
      return null;
    }

    return moment().diff(moment(dateOfBirth), 'years');
  }
}
