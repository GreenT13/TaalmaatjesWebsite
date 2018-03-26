import {Component, Input, OnInit} from "@angular/core";
import {TaskModel} from "../../../valueobject/task.model";
import {HttpErrorResponse} from "@angular/common/http";
import {TaskAddComponent} from "./task.add.component";
import {TaskService} from "../../../services/task.service";
import {ActivatedRoute} from "@angular/router";
import {CopyUtil} from "../../../util/copy.util";

@Component({
  selector: 'app-task-edit',
  templateUrl: './task.upsert.component.html',
  styleUrls: ['./task.upsert.component.css']
})
export class TaskEditComponent extends TaskAddComponent implements OnInit {
  @Input()
  prefillTask: TaskModel;

  constructor(protected taskService: TaskService,
              protected route: ActivatedRoute) {
    super(taskService);
  }

  ngOnInit(): void {
    if(!this.prefillTask) {
      // Prefill all values based on retrieved task.
      this.route.params.subscribe(
        (params) => {
          this.taskService.getTask(params['taskExtId']).subscribe(
            (response: TaskModel) => {
              this.prefill(response);
            },
            (error) => {
              this.alertModel.setError(error);
              // Clear the task on screen if there was any.
              this.taskModel = new TaskModel();
            }
          );
        });
    } else {
      // Prefill the given task.
      this.prefill(this.prefillTask);
    }
  }

  prefill(task: TaskModel) {
    this.taskModel = task;
    if (this.volunteer) {
      this.taskModel.volunteerValueObject = this.volunteer;
    }
  }

  doHttpRequest() {
    // Make sure the volunteer has no reference to the task.
    this.taskModel.volunteerValueObject = CopyUtil.createCopyVolunteer(this.volunteer);

    this.taskService.updateTask(this.taskModel).subscribe(
      () => this.didHttpRequest.emit(this.taskModel.taskExtId),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }
}
