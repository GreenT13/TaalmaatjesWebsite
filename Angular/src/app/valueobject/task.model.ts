import {VolunteerModel} from "./volunteer.model";

export class TaskModel {
  public taskExtId: string;
  public title: string;
  public description: string;
  public volunteerValueObject: VolunteerModel;
  public isFinished: boolean;
  public dateToBeFinished: Date;
}
