import {Component, EventEmitter, Input, Output} from "@angular/core";
import {AlertModel} from "../../../alert/alert.model";
import {TaskDVO} from "../../../../../valueobject/dvo/task.dvo";
import {VolunteerDVO} from "../../../../../valueobject/dvo/volunteer.dvo";
import {TaskService} from "../../../../../services/task.service";

@Component({
  selector: 'app-task-detail',
  templateUrl: './task.detail.component.html',
  styleUrls: ['./task.detail.component.css']
})
export class TaskDetailComponent {
  @Input()
  alertModel = new AlertModel();

  @Input()
  public task: TaskDVO = new TaskDVO();
  @Output()
  taskModelChanged = new EventEmitter<TaskDVO>();
  @Input()
  volunteer: VolunteerDVO = new VolunteerDVO();
  @Output()
  onEditEmitter = new EventEmitter<boolean>();

  constructor(public taskService: TaskService) { }

  changeState() {
    this.task.volunteerDVO = this.volunteer;

    this.taskService.changeState(this.task).subscribe(
      () => this.taskModelChanged.emit(this.task),
      (error) => this.alertModel.setError(error)
    );
  }

  onEdit() {
    this.onEditEmitter.emit(true);
  }
}
