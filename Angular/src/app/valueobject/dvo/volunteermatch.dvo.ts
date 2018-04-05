import {VolunteerDVO} from "./volunteer.dvo";
import {StudentDVO} from "./student.dvo";

export class VolunteerMatchDVO {
  public volunteerDVO: VolunteerDVO = new VolunteerDVO();
  public externalIdentifier: string;
  public studentDVO: StudentDVO = new StudentDVO();
  public dateStart: Date;
  public dateEnd: Date;
}
