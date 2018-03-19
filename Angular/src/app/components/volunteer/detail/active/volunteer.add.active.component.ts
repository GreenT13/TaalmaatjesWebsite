import {Component} from "@angular/core";
import {VolunteerActiveComponent} from "./volunteer.active.component";
import {VolunteerDetailService} from "../volunteer.detail.service";
import {VolunteerService} from "../../../../services/volunteer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-volunteer-add-active',
  templateUrl: './volunteer.active.component.html',
  styleUrls: ['./volunteer.active.component.css']
})
export class VolunteerAddActiveComponent extends VolunteerActiveComponent {

  constructor(protected volunteerService: VolunteerService,
              protected volunteerDetailService: VolunteerDetailService,
              protected router: Router,
              protected route: ActivatedRoute) {
    super(volunteerService, volunteerDetailService, router, route, 'Toevoegen activiteit');
  }

  doHttpRequest() {
    this.volunteerService.insertVolunteerInstance(this.volunteerInstanceModel).subscribe(
      () =>
          this.volunteerDetailService.retrieveVolunteer(this.volunteer.externalIdentifier, this.volunteerService).subscribe(
            () => this.router.navigate(['../'], {relativeTo: this.route}),
            (error: HttpErrorResponse) => this.alertModel.setError(error)
          ),
      (error: HttpErrorResponse) =>  this.alertModel.setError(error)
    );
  }

}
