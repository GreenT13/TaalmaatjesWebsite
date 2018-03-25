import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {TaskModel} from "../../../valueobject/task.model";
import {IMyDpOptions} from "mydatepicker";
import {TaskService} from "../../../services/task.service";
import {DateUtil} from "../../../util/date.util";
import {HttpErrorResponse} from "@angular/common/http";
import {SingleStringModel} from "../../../valueobject/singlestring.model";
import {VolunteerModel} from "../../../valueobject/volunteer.model";

@Component({
  selector: 'app-task-add',
  templateUrl: './task.upsert.component.html',
  styleUrls: ['./task.upsert.component.css']
})
export class TaskAddComponent implements OnInit {
  @Input()
  volunteer: VolunteerModel;
  @Input()
  public alertModel;
  @Output()
  didHttpRequest = new EventEmitter<string>();
  @Output()
  onBackEmitter = new EventEmitter<boolean>();

  public taskModel: TaskModel = new TaskModel();
  public dateToBeFinished;

  public optionsAll: IMyDpOptions = {
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };

  ngOnInit(): void {
    if (this.volunteer) {
      this.taskModel.volunteerValueObject = this.volunteer;
    }
  }

  constructor(protected taskService: TaskService) { }

  onSubmit() {
    this.taskModel.dateToBeFinished = DateUtil.convertDateIDateToString(this.dateToBeFinished);
    this.doHttpRequest();
  }

  doHttpRequest() {
    this.taskService.insertTask(this.taskModel).subscribe(
      (taskExtId: SingleStringModel) => {
        this.didHttpRequest.emit(taskExtId.value);
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      }
    );
  }

  onBack() {
    this.onBackEmitter.emit(true);
  }
}
