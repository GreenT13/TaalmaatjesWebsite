import {Injectable} from "@angular/core";

@Injectable()
export class DateUtil {
  public static getCurrentDate(): string {
    let date: Date = new Date();
    return date.getDate() + '-' + date.getMonth() + 1 + '-' + date.getFullYear();
  }
}
