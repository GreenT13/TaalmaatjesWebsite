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

  public static convertStringToIDate(date: String): IMyDate {
    if (!date || date.length == 0) {
      return null;
    }
    const year = date.substr(0, date.indexOf('-'));
    const month = date.substr(date.indexOf('-') + 1, date.substr(date.indexOf('-') + 1).indexOf('-'));
    const day = date.substr(date.lastIndexOf('-') + 1);
    return {year: +year, month: +month, day: +day};

  }

  public static convertIDateToString(iDate: IMyDate): string {
    if (!iDate) {
      return null;
    }
    return iDate.year + '-' + iDate.month + '-' + iDate.day;
  }
}
