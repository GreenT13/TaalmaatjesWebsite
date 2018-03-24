import {Component, OnInit} from "@angular/core";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
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
  public taskModel: TaskModel;
  public dateToBeFinished;
  currentHttpRequest: Subscription = null;

  public optionsAll: IMyDpOptions = {
    satHighlight: true,
    dateFormat: 'dd-mm-yyyy'
  };

  ngOnInit(): void {
    this.taskModel = new TaskModel();
    this.taskModel.volunteerValueObject = new VolunteerModel();
  }

  constructor(private taskService: TaskService,
              private router: Router,
              private route: ActivatedRoute) { }

  onSubmit() {
    this.taskModel.dateToBeFinished = DateUtil.convertDateIDateToString(this.dateToBeFinished);

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
