import {Component, EventEmitter, Input, Output} from "@angular/core";
import {VolunteerDVO} from "../../../../../valueobject/dvo/volunteer.dvo";

@Component({
  selector: 'app-volunteer-detail',
  templateUrl: './volunteer.detail.component.html'
})
export class VolunteerDetailComponent {
  @Input()
  public volunteer: VolunteerDVO = new VolunteerDVO();

  @Output()
  onEditEmitter = new EventEmitter<boolean>();

  onEdit() {
    this.onEditEmitter.emit(true);
  }
}
