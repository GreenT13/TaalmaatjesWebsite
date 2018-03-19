import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {VolunteerService} from "../../../services/volunteer.service";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {NameUtil} from "../../../util/name.util";
import {HttpErrorResponse} from "@angular/common/http";
import {FormUtil} from "../../../util/form.util";
import {Subscription} from "rxjs/Subscription";
import {AlertModel} from "../../alert/alert.model";

@Component({
  selector: 'app-volunteer-overview',
  templateUrl: './volunteer.overview.component.html',
  styleUrls: ['./volunteer.overview.component.css']
})
export class VolunteerOverviewComponent implements OnInit {
  public alertModel = new AlertModel();
  volunteers: VolunteerModel[];
  parseName = NameUtil.parseName;
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
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.searchVolunteers();
  }

  clickOnVolunteer(extId: string) {
    // Go to the volunteer.
    this.router.navigate([extId], {relativeTo: this.route});
  }

  searchVolunteers() {
    // If you keep typing in a box, it will keep requesting to the server.
    if (this.currentHttpRequest != null) {
      this.currentHttpRequest.unsubscribe();
    }

    this.currentHttpRequest = this.volunteerService.searchVolunteers(
      this.inputSearch, this.inputCity, this.isActive, this.hasTraining, this.hasMatch
    ).subscribe(
      (volunteers: VolunteerModel[]) => {
        this.volunteers = volunteers;
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      });
  }
}
