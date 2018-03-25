import {Component} from "@angular/core";
import {AlertModel} from "../../alert/alert.model";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-task-add-view',
  template: '<app-alert-component [alertModel]="alertModel"></app-alert-component>' +
  '<div class="row col-sm-6"><app-task-add [(alertModel)]="alertModel" (didHttpRequest)="didHttpRequest($event)"></app-task-add></div>'
})
export class TaskAddView {
  public alertModel: AlertModel = new AlertModel();

  constructor(protected router: Router,
              protected route: ActivatedRoute) { }

  didHttpRequest(taskExtId) {
    this.router.navigate(['task/' + taskExtId]);
  }
}
