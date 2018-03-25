import {Component, OnInit} from "@angular/core";
import {AlertModel} from "../../alert/alert.model";
import {TaskModel} from "../../../valueobject/task.model";
import {TaskService} from "../../../services/task.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-task-detail',
  templateUrl: './task.detail.component.html',
  styleUrls: ['./task.detail.component.css']
})
export class TaskDetailComponent implements OnInit {
  public alertModel = new AlertModel();
  public taskModel: TaskModel = new TaskModel();

  constructor(public taskService: TaskService,
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

  changeState() {
    this.taskService.changeState(this.taskModel).subscribe(
      () => {},
      (error) => this.alertModel.setError(error)
    );
  }
}
