import {StudentDVO} from "./dvo/student.dvo";
import {VolunteerMatchDVO} from "./dvo/volunteermatch.dvo";

export class StudentVOGet {
  public studentDVO: StudentDVO = new StudentDVO();
  public volunteerMatchDVOS: VolunteerMatchDVO[] = [];
}
