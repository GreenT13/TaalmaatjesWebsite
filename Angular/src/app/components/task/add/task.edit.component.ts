import {Component, OnInit} from "@angular/core";
import {TaskModel} from "../../../valueobject/task.model";
import {DateUtil} from "../../../util/date.util";
import {HttpErrorResponse} from "@angular/common/http";
import {TaskAddComponent} from "./task.add.component";

@Component({
  selector: 'app-task-detail',
  templateUrl: './task.add.component.html',
  styleUrls: ['./task.add.component.css']
})
export class TaskEditComponent extends TaskAddComponent implements OnInit {
  ngOnInit(): void {
    // Prefill all values based on retrieved task.
    this.route.params.subscribe(
      (params) => {
        this.taskService.getTask(params['taskExtId']).subscribe(
          (response: TaskModel) => {
            this.taskModel = response;
            this.dateToBeFinished = {date: DateUtil.convertStringToIDate(this.taskModel.dateToBeFinished)};
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the task on screen if there was any.
            this.taskModel = new TaskModel();
          }
        );
      });
  }

  doHttpRequest() {
    this.taskService.updateTask(this.taskModel).subscribe(
      () => this.router.navigate(['../'], {relativeTo: this.route}),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }
}
