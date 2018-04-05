import {Component, Input} from "@angular/core";
import {VolunteerDVO} from "../../../../../valueobject/dvo/volunteer.dvo";

@Component({
  selector: 'app-volunteer-detail',
  templateUrl: './volunteer.detail.component.html'
})
export class VolunteerDetailComponent {
  @Input()
  public volunteer: VolunteerDVO = new VolunteerDVO();
}
