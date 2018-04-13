import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../../block/alert/alert.model";
import {TaskService} from "../../../../services/task.service";
import {TaskDVO} from "../../../../valueobject/dvo/task.dvo";

@Component({
  selector: 'app-task-edit-view',
  template: '<app-alert-component [alertModel]="alertModel"></app-alert-component>' +
  '<div class="row">' +
    '<div class="col-sm-6"><app-task-edit [(alertModel)]="alertModel" ' +
    '(didHttpRequest)="didHttpRequest($event)" (onBackEmitter)="onBack()" [task]="task"></app-task-edit></div>' +
  '</div>'
})
export class TaskEditView implements OnInit {
  public alertModel: AlertModel = new AlertModel();
  public task: TaskDVO = new TaskDVO();

  constructor(protected taskService: TaskService,
              protected router: Router,
              protected route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params) => {
        this.taskService.getTask(params['taskExtId']).subscribe(
          (response: TaskDVO) => {
            this.task = response;
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the task on screen if there was any.
            this.task = new TaskDVO();
          }
        );
      });
  }

  didHttpRequest(externalIdentifier) {
    this.router.navigate(['task/' + externalIdentifier]);
  }

  onBack() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }

}
