import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {HttpErrorResponse} from "@angular/common/http";
import {FormUtil} from "../../../util/form.util";
import {Subscription} from "rxjs/Subscription";
import {AlertModel} from "../../alert/alert.model";
import {TaskModel} from "../../../valueobject/task.model";
import {TaskService} from "../../../services/task.service";

@Component({
  selector: 'app-task-overview',
  templateUrl: './task.overview.component.html',
  styleUrls: ['./task.overview.component.css']
})
export class TaskOverviewComponent implements OnInit {
  public alertModel = new AlertModel();
  tasks: TaskModel[];
  currentHttpRequest: Subscription = null;

  // Searching parameters
  public status: Boolean = false;
  public inputSearch: string;
  public volunteer: VolunteerModel = new VolunteerModel();

  // Values for dropdown boxes.
  public OpenClosedNull = FormUtil.OpenClosedNull;

  constructor(private taskService: TaskService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.search();
  }

  click(extId: string) {
    // Go to the task.
    this.router.navigate([extId], {relativeTo: this.route});
  }

  search() {
    // If you keep typing in a box, it will keep requesting to the server.
    if (this.currentHttpRequest != null) {
      this.currentHttpRequest.unsubscribe();
    }

    this.currentHttpRequest = this.taskService.searchTask(
      this.inputSearch, null, this.status, this.volunteer.externalIdentifier
    ).subscribe(
      (tasks: any) => {
        this.tasks = tasks;
      },
      (error: HttpErrorResponse) => {
        this.alertModel.setError(error);
      });
  }
}
