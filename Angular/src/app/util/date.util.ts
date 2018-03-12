import {Injectable} from "@angular/core";
import {IMyDate} from 'mydatepicker';

@Injectable()
export class DateUtil {
  public static getCurrentIDate(): IMyDate {
    let date: Date = new Date();
    return {year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate()};
  }

  public static convertDateToIDate(date: Date): IMyDate {
    return {year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate()}
  }

  public static convertIDateToString(iDate: IMyDate): string {
    var date: Date = new Date();
    date.setUTCFullYear(iDate.year, iDate.month, iDate.day);
    return date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate();
  }
}
