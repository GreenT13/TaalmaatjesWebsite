import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {VolunteerService} from "../../services/volunteer.service";
import {VolunteerModel} from "../../valueobject/volunteer.model";
import {NameUtil} from "../../util/name.util";

@Component({
  selector: 'app-volunteer-overview',
  templateUrl: './volunteer.overview.component.html',
  styleUrls: ['./volunteer.overview.component.css']
})
export class VolunteerOverviewComponent implements OnInit {
  volunteers: VolunteerModel[];
  parseName = NameUtil.parseName;

  constructor(private volunteerService: VolunteerService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    var promise = this.volunteerService.searchVolunteers();
    promise.subscribe(
      (volunteers: VolunteerModel[]) => {
        this.volunteers = volunteers;
      },
      (response: any) => {
        console.log(response);
      });
  }

  clickOnVolunteer(extId: string) {
    // Go to the volunteer.
    this.router.navigate([extId], {relativeTo: this.route});
  }
}
