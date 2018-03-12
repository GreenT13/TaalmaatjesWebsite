import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {VolunteerService} from "../../services/volunteer.service";
import {VolunteerModel} from "../../valueobject/volunteer.model";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-volunteer-detail',
  templateUrl: './volunteer.detail.component.html',
  styleUrls: ['./volunteer.detail.component.css']
})
export class VolunteerDetailComponent implements OnInit {
  volunteer: VolunteerModel = new VolunteerModel();

  constructor(private volunteerService: VolunteerService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params) => {
        this.volunteerService.getVolunteer(params['volunteerExtId']).subscribe(
          (response: VolunteerModel) => {
            this.volunteerService.currentVolunteer = response;
            this.volunteer = this.volunteerService.currentVolunteer;
          },
          (error) => {
            console.log(error);
          }
        );
      });
  }
}
