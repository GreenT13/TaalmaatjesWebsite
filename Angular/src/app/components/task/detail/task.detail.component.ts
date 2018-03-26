import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from "@angular/core";
import {AlertModel} from "../../alert/alert.model";
import {TaskModel} from "../../../valueobject/task.model";
import {TaskService} from "../../../services/task.service";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {CopyUtil} from "../../../util/copy.util";

@Component({
  selector: 'app-task-detail',
  templateUrl: './task.detail.component.html',
  styleUrls: ['./task.detail.component.css']
})
export class TaskDetailComponent implements OnChanges {
  @Input()
  alertModel = new AlertModel();

  @Input()
  taskModel: TaskModel;
  @Output()
  taskModelChanged = new EventEmitter<TaskModel>();
  @Input()
  volunteer: VolunteerModel;
  @Output()
  onEditEmitter = new EventEmitter<boolean>();

  constructor(public taskService: TaskService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.taskModel != null && changes.taskModel.currentValue) {
      this.taskModel = changes.taskModel.currentValue;
    }
    if(changes.volunteer != null && changes.volunteer.currentValue) {
      this.taskModel.volunteerValueObject = CopyUtil.createCopyVolunteer(changes.volunteer.currentValue);
    }
  }

  changeState() {
    this.taskService.changeState(this.taskModel).subscribe(
      () => this.taskModelChanged.emit(this.taskModel),
      (error) => this.alertModel.setError(error)
    );
  }

  onEdit() {
    this.onEditEmitter.emit(true);
  }
}
