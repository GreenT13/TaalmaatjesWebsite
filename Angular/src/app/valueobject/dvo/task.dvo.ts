import {VolunteerDVO} from "./volunteer.dvo";

export class TaskDVO {
  public externalIdentifier: string;
  public title: string;
  public description: string;
  public volunteerDVO: VolunteerDVO = new VolunteerDVO();
  public isFinished: boolean;
  public dateToBeFinished: string;
}
