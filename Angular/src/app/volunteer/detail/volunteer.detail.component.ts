import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VolunteerService} from "../../services/volunteer.service";
import {VolunteerModel} from "../../valueobject/volunteer.model";
import {VolunteerDetailService} from "./volunteer.detail.service";
import {NameUtil} from "../../util/name.util";
import {AlertModel} from "../../alert/alert.model";

@Component({
  selector: 'app-volunteer-detail',
  providers: [VolunteerDetailService],
  templateUrl: './volunteer.detail.component.html',
  styleUrls: ['./volunteer.detail.component.css']
})
export class VolunteerDetailComponent implements OnInit {
  public alertModel = new AlertModel();
  public volunteer: VolunteerModel = new VolunteerModel();
  public parseName = NameUtil.parseName;

  constructor(private volunteerService: VolunteerService,
              public volunteerDetailService: VolunteerDetailService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(
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
      });

    this.volunteerDetailService.getVolunteer().subscribe(
      (volunteer: VolunteerModel) => this.volunteer = volunteer
    );
  }
}
