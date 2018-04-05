import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {Subscription} from "rxjs/Subscription";
import {AlertModel} from "../../../block/alert/alert.model";
import {TaskDVO} from "../../../../valueobject/dvo/task.dvo";
import {VolunteerDVO} from "../../../../valueobject/dvo/volunteer.dvo";
import {FormUtil} from "../../../../util/form.util";
import {TaskService} from "../../../../services/task.service";

@Component({
  selector: 'app-task-overview-view',
  templateUrl: './task.overview.view.html',
  styleUrls: ['./task.overview.view.css']
})
export class TaskOverviewView implements OnInit {
  public alertModel = new AlertModel();
  tasks: TaskDVO[];
  currentHttpRequest: Subscription = null;

  // Searching parameters
  public status: Boolean = false;
  public inputSearch: string;
  public volunteer: VolunteerDVO = new VolunteerDVO();

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

  onDblClick(task: TaskDVO) {
    // Go to the volunteer.
    this.router.navigate([task.externalIdentifier], {relativeTo: this.route});
  }
}
