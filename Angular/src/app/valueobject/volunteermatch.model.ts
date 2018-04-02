import {VolunteerModel} from "./volunteer.model";
import {StudentModel} from "./student.model";

export class VolunteerMatchModel {
  public volunteerValueObject: VolunteerModel = new VolunteerModel();
  public externalIdentifier: string;
  public studentValueObject: StudentModel = new StudentModel();
  public dateStart: Date;
  public dateEnd: Date;
}
