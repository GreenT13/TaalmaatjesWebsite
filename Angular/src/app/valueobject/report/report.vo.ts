import {ReportPersonVO} from "./report.person.vo";

export class ReportVO {
  public dateStart: Date;
  public dateEnd: Date;
  public volunteers: ReportPersonVO[] = [];
  public students: ReportPersonVO[] = [];
}
