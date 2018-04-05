import {Component} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../../block/alert/alert.model";

@Component({
  selector: 'app-task-add-view',
  template: '<app-alert-component [alertModel]="alertModel"></app-alert-component>' +
  '<div class="row col-sm-6"><app-task-add [(alertModel)]="alertModel" ' +
  '(didHttpRequest)="didHttpRequest($event)" (onBackEmitter)="onBack()"></app-task-add></div>'
})
export class TaskAddView {
  public alertModel: AlertModel = new AlertModel();

  constructor(protected router: Router,
              protected route: ActivatedRoute) { }

  didHttpRequest(taskExtId) {
    this.router.navigate(['task/' + taskExtId]);
  }

  onBack() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
