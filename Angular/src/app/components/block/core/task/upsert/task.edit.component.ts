import {Component} from "@angular/core";
import {HttpErrorResponse} from "@angular/common/http";
import {TaskUpsertComponent} from "./task.upsert.component";
import {TaskService} from "../../../../../services/task.service";

@Component({
  selector: 'app-task-edit',
  templateUrl: './task.upsert.component.html',
  styleUrls: ['./task.upsert.component.css']
})
export class TaskEditComponent extends TaskUpsertComponent {

  constructor(protected taskService: TaskService) {
    super('Wijzigen taak');
  }

  doHttpRequest() {
    this.taskService.updateTask(this.task).subscribe(
      () => this.didHttpRequest.emit(this.task.externalIdentifier),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }
}
