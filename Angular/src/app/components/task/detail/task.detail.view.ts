import {Component, OnInit} from "@angular/core";
import {TaskModel} from "../../../valueobject/task.model";
import {TaskService} from "../../../services/task.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../alert/alert.model";

@Component({
  selector: 'app-task-detail-view',
  template: '<app-alert-component [alertModel]="alertModel"></app-alert-component>\n' +
  '<div class="row">' +
    '<div class="col-sm-6"><app-task-detail [alertModel]="alertModel" [(taskModel)]="taskModel" (onEditEmitter)="onEdit()"></app-task-detail></div>' +
    '<div class="col-sm-6"></div>' +
  '</div>'
})
export class TaskDetailView implements OnInit {
  alertModel: AlertModel = new AlertModel();
  taskModel: TaskModel = new TaskModel();

  constructor(public taskService: TaskService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params) => {
        this.taskService.getTask(params['taskExtId']).subscribe(
          (response: TaskModel) => {
            this.taskModel = response;
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the task on screen if there was any.
            this.taskModel = new TaskModel();
          }
        );
      });
  }

  onEdit() {
    this.router.navigate(['edit'], {relativeTo: this.route});
  }

}
