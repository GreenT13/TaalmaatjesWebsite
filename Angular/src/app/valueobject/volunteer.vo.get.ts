import {VolunteerDVO} from "./dvo/volunteer.dvo";
import {VolunteerInstanceDVO} from "./dvo/volunteerinstance.dvo";
import {VolunteerMatchDVO} from "./dvo/volunteermatch.dvo";
import {TaskDVO} from "./dvo/task.dvo";

export class VolunteerVOGet {
  public volunteerDVO: VolunteerDVO = new VolunteerDVO();
  public volunteerInstanceDVOS: VolunteerInstanceDVO[];
  public volunteerMatchDVOS: VolunteerMatchDVO[];
  public taskDVOS: TaskDVO[];
}
