import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {Subscription} from "rxjs/Subscription";
import {VolunteerVOSearch} from "../../../../valueobject/volunteer.vo.search";
import {AlertModel} from "../../../block/alert/alert.model";
import {FormUtil} from "../../../../util/form.util";
import {VolunteerService} from "../../../../services/volunteer.service";

@Component({
  selector: 'app-volunteer-overview-view',
  templateUrl: './volunteer.overview.view.html',
  styleUrls: ['./volunteer.overview.view.css']
})
export class VolunteerOverviewView implements OnInit {
  public alertModel = new AlertModel();
  volunteers: VolunteerVOSearch[] = [];
  currentHttpRequest: Subscription = null;

  // Searching parameters
  public isActive: Boolean = true; // default value
  public inputSearch: String;
  public inputCity: String;
  public hasTraining: Boolean;
  public hasMatch: Boolean;

  // Values for dropdown boxes.
  public yesNoNull = FormUtil.yesNoNull;

  constructor(private volunteerService: VolunteerService,
              public router: Router,
              public route: ActivatedRoute) { }

  ngOnInit(): void {
    this.searchVolunteers();
  }

  searchVolunteers() {
    // If you keep typing in a box, it will keep requesting to the server.
    if (this.currentHttpRequest != null) {
      this.currentHttpRequest.unsubscribe();
    }

    this.currentHttpRequest = this.volunteerService.searchVolunteers(
      this.inputSearch, this.inputCity, this.isActive, this.hasTraining, this.hasMatch
    ).subscribe(
      (volunteers: VolunteerVOSearch[]) => {
        this.volunteers = volunteers;
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      });
  }

  onDblClick(volunteer: VolunteerVOSearch) {
    // Go to the volunteer.
    this.router.navigate([volunteer.volunteerDVO.externalIdentifier], {relativeTo: this.route});
  }
}
