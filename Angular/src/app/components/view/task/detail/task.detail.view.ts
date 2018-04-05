import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../../block/alert/alert.model";
import {TaskDVO} from "../../../../valueobject/dvo/task.dvo";
import {TaskService} from "../../../../services/task.service";

@Component({
  selector: 'app-task-detail-view',
  template: '<app-alert-component [alertModel]="alertModel"></app-alert-component>\n' +
  '<div class="row">' +
    '<div class="col-sm-6"><app-task-detail [alertModel]="alertModel" [(task)]="task" (onEditEmitter)="onEdit()"></app-task-detail></div>' +
    '<div class="col-sm-6"></div>' +
  '</div>'
})
export class TaskDetailView implements OnInit {
  alertModel: AlertModel = new AlertModel();
  task: TaskDVO = new TaskDVO();

  constructor(public taskService: TaskService,
              private router: Router,
              private route: ActivatedRoute) { }

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

  onEdit() {
    this.router.navigate(['edit'], {relativeTo: this.route});
  }
}
