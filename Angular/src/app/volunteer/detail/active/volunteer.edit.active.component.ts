import {Component} from "@angular/core";
import {VolunteerActiveComponent} from "./volunteer.active.component";
import {VolunteerDetailService} from "../volunteer.detail.service";
import {VolunteerService} from "../../../services/volunteer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DateUtil} from "../../../util/date.util";
import {CopyUtil} from "../../../util/copy.util";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-volunteer-edit-active',
  templateUrl: './volunteer.active.component.html',
  styleUrls: ['./volunteer.active.component.css']
})
export class VolunteerEditActiveComponent extends VolunteerActiveComponent {

  constructor(protected volunteerService: VolunteerService,
              protected volunteerDetailService: VolunteerDetailService,
              protected router: Router,
              protected route: ActivatedRoute) {
    super(volunteerService, volunteerDetailService, router, route, 'Wijzigen activiteit');
  }

  retrieveSpecificInstance() {
    this.destroyUtil.addSubscription(this.route.params.subscribe(
      (params) => {
        // Find the instance from the volunteer that corresponds to this instance.
        this.volunteerInstanceModel = CopyUtil.createCopy(this.volunteer.volunteerInstanceValueObjects.find(
          x => x.externalIdentifier == params['volunteerInstanceExtId']
        ));

        // Prefill the values.
        this.dateStartActive = {date: DateUtil.convertStringToIDate(this.volunteerInstanceModel.dateStart)};
        this.dateEndActive = {date: DateUtil.convertStringToIDate(this.volunteerInstanceModel.dateEnd)};
      }
    ));
  }

  doHttpRequest() {
    this.volunteerService.updateVolunteerInstance(this.volunteerInstanceModel).subscribe(
      () => {
        this.volunteerDetailService.retrieveVolunteer(this.volunteer.externalIdentifier, this.volunteerService).subscribe(
          () => this.router.navigate(['../../'], {relativeTo: this.route}),
          (error: HttpErrorResponse) => this.alertModel.setError(error)
        );
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      }
    );
  }

  delete() {
    // Show popup.
    if(!confirm("Weet je zeker dat je deze regel wilt verwijderen?")) {
      return;
    }

    // Delete the line and return.
    this.volunteerService.deleteVolunteerInstance(this.volunteerInstanceModel.volunteerExtId, this.volunteerInstanceModel.externalIdentifier).subscribe(
      () => {
        // Remove the instance from the volunteerInstanceValueObjects
        this.volunteer.volunteerInstanceValueObjects = this.volunteer.volunteerInstanceValueObjects.filter(
          volunteerInstanceValueObject => volunteerInstanceValueObject.externalIdentifier !== this.volunteerInstanceModel.externalIdentifier
        );
        this.router.navigate(['../../'], {relativeTo: this.route});
      },
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }

}
