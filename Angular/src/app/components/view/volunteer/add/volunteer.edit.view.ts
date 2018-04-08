import {Component, OnInit} from "@angular/core";
import {VolunteerDVO} from "../../../../valueobject/dvo/volunteer.dvo";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {VolunteerService} from "../../../../services/volunteer.service";
import {VolunteerAddView} from "./volunteer.add.view";
import {VolunteerVOGet} from "../../../../valueobject/volunteer.vo.get";
import {DestroyUtil} from "../../../../util/destroy.util";

@Component({
  selector: 'app-volunteer-edit-view',
  templateUrl: './volunteer.add.view.html'
})
export class VolunteerEditView extends VolunteerAddView implements OnInit {
  private destroyUtil: DestroyUtil = new DestroyUtil();

  constructor(protected volunteerService: VolunteerService,
              protected router: Router, protected route: ActivatedRoute) {
    super(volunteerService, router, route);
    this.isEdit = true;
  }

  ngOnInit(): void {
    this.destroyUtil.addSubscription(this.route.params.subscribe(
      (params) => {
        this.volunteerService.getVolunteer(params['volunteerExtId']).subscribe(
          (response: VolunteerVOGet) => {
            this.volunteer = response.volunteerDVO;
            console.log(this.volunteer);
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the volunteer on screen if there was any.
            this.volunteer = new VolunteerDVO();
          }
        );
      }
    ));
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }

  onSubmit() {
    this.volunteerService.updateVolunteer(this.volunteer).subscribe(
      () => {
        this.router.navigate(['../' + this.volunteer.externalIdentifier], {relativeTo: this.route});
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      }
    );
  }

  onBack() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
