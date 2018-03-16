import {Component} from "@angular/core";
import {VolunteerActiveComponent} from "./volunteer.active.component";
import {VolunteerDetailService} from "../volunteer.detail.service";
import {VolunteerService} from "../../../services/volunteer.service";
import {ActivatedRoute} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {VolunteerModel} from "../../../valueobject/volunteer.model";

@Component({
  selector: 'app-volunteer-add-active',
  templateUrl: './volunteer.active.component.html',
  styleUrls: ['./volunteer.active.component.css']
})
export class VolunteerAddActiveComponent extends VolunteerActiveComponent {

  constructor(protected volunteerService: VolunteerService,
              protected volunteerDetailService: VolunteerDetailService,
              protected route: ActivatedRoute) {
    super(volunteerService, volunteerDetailService, route, 'Toevoegen activiteit');
  }

  doHttpRequest() {
    this.volunteerService.insertVolunteerInstance(this.volunteerInstanceModel).subscribe(
      (response: Response) => {
        this.error = this.volunteerDetailService.retrieveVolunteer(this.volunteer.externalIdentifier, this.volunteerService);
      },
      (error: HttpErrorResponse) => {
        this.error = error;
      }
    );
  }

}
