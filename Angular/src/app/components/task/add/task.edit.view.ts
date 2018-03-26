import {Component} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../alert/alert.model";

@Component({
  selector: 'app-task-edit-view',
  template: '<app-alert-component [alertModel]="alertModel"></app-alert-component>' +
  '<div class="col-sm-6"><app-task-edit [(alertModel)]="alertModel" ' +
  '(didHttpRequest)="didHttpRequest($event)" (onBackEmitter)="onBack()"></app-task-edit></div>'
})
export class TaskEditView {
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
