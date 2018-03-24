import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../alert/alert.model";
import {TaskModel} from "../../../valueobject/task.model";
import {IMyDpOptions} from "mydatepicker";
import {Subscription} from "rxjs/Subscription";
import {TaskService} from "../../../services/task.service";
import {DateUtil} from "../../../util/date.util";
import {HttpErrorResponse} from "@angular/common/http";
import {SingleStringModel} from "../../../valueobject/singlestring.model";

@Component({
  selector: 'app-task-add',
  templateUrl: './task.add.component.html',
  styleUrls: ['./task.add.component.css']
})
export class TaskAddComponent implements OnInit {
  public alertModel = new AlertModel();
  public taskModel: TaskModel = new TaskModel();
  public dateToBeFinished;
  currentHttpRequest: Subscription = null;

  public optionsAll: IMyDpOptions = {
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };

  ngOnInit(): void { }

  constructor(protected taskService: TaskService,
              protected router: Router,
              protected route: ActivatedRoute) { }

  onSubmit() {
    this.taskModel.dateToBeFinished = DateUtil.convertDateIDateToString(this.dateToBeFinished);
    this.doHttpRequest();
  }

  doHttpRequest() {
    this.taskService.insertTask(this.taskModel).subscribe(
      (taskExtId: SingleStringModel) => {
        this.router.navigate(['../' + taskExtId.value], {relativeTo: this.route});
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      }
    );
  }
}
