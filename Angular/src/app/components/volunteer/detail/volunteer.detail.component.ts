import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VolunteerService} from "../../../services/volunteer.service";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {VolunteerDetailService} from "./volunteer.detail.service";
import {AlertModel} from "../../alert/alert.model";
import {DestroyUtil} from "../../../util/destroy.util";

@Component({
  selector: 'app-volunteer-detail',
  providers: [VolunteerDetailService],
  templateUrl: './volunteer.detail.component.html',
  styleUrls: ['./volunteer.detail.component.css']
})
export class VolunteerDetailComponent implements OnInit, OnDestroy {
  private destroyUtil: DestroyUtil = new DestroyUtil();
  public alertModel = new AlertModel();

  public volunteer: VolunteerModel = new VolunteerModel();

  constructor(private volunteerService: VolunteerService,
              public volunteerDetailService: VolunteerDetailService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.destroyUtil.addSubscription(this.route.params.subscribe(
      (params) => {
        this.volunteerService.getVolunteer(params['volunteerExtId']).subscribe(
          (response: VolunteerModel) => {
            this.volunteerDetailService.setVolunteer(response);
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the volunteer on screen if there was any.
            this.volunteer = new VolunteerModel();
          }
        );
      }));

    this.destroyUtil.addSubscription(this.volunteerDetailService.getVolunteer().subscribe(
      (volunteer: VolunteerModel) => this.volunteer = volunteer,
      () => {console.log('error')},
        () => {console.log('completed')}
    ));
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }
}
