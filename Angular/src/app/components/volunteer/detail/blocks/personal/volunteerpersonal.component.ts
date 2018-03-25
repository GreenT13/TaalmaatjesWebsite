import {Component, Input} from "@angular/core";
import {VolunteerModel} from "../../../../../valueobject/volunteer.model";

@Component({
  selector: 'app-volunteer-personal',
  templateUrl: './volunteerpersonal.component.html',
  styleUrls: ['./volunteerpersonal.component.css']
})
export class VolunteerPersonalComponent {
  @Input()
  public volunteer: VolunteerModel;
}
