import {VolunteerModel} from "./volunteer.model";

export class TaskModel {
  public taskExtId: string;
  public title: string;
  public description: string;
  public volunteerValueObject: VolunteerModel = new VolunteerModel();
  public isFinished: boolean;
  public dateToBeFinished: string;
}
