import {Component} from "@angular/core";
import {HttpErrorResponse} from "@angular/common/http";
import {TaskService} from "../../../../../services/task.service";
import {StringVO} from "../../../../../valueobject/string.vo";
import {TaskUpsertComponent} from "./task.upsert.component";

@Component({
  selector: 'app-task-add',
  templateUrl: './task.upsert.component.html',
  styleUrls: ['./task.upsert.component.css']
})
export class TaskAddComponent extends TaskUpsertComponent {
  constructor(protected taskService: TaskService) {
    super('Toevoegen taak');
  }

  doHttpRequest() {
    this.taskService.insertTask(this.task).subscribe(
      (externalIdentifier: StringVO) => this.didHttpRequest.emit(externalIdentifier.value),
      (error: HttpErrorResponse) => this.alertModel.setError(error)
    );
  }
}
