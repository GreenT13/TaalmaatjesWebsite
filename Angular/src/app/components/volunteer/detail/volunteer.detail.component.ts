import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VolunteerService} from "../../../services/volunteer.service";
import {VolunteerModel} from "../../../valueobject/volunteer.model";
import {VolunteerDetailService} from "./volunteer.detail.service";
import {AlertModel} from "../../alert/alert.model";
import {DestroyUtil} from "../../../util/destroy.util";
import {TaskModel} from "../../../valueobject/task.model";
import {VolunteerInstanceModel} from "../../../valueobject/volunteerinstance.model";

@Component({
  selector: 'app-volunteer-detail',
  providers: [VolunteerDetailService],
  templateUrl: './volunteer.detail.component.html',
  styleUrls: ['./volunteer.detail.component.css']
})
export class VolunteerDetailComponent implements OnInit, OnDestroy {
  private destroyUtil: DestroyUtil = new DestroyUtil();
  public alertModel = new AlertModel();

  public volunteer: VolunteerModel = new VolunteerModel();

  constructor(private volunteerService: VolunteerService,
              public volunteerDetailService: VolunteerDetailService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.destroyUtil.addSubscription(this.route.params.subscribe(
      (params) => {
        this.volunteerService.getVolunteer(params['volunteerExtId']).subscribe(
          (response: VolunteerModel) => {
            this.volunteerDetailService.setVolunteer(response);
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the volunteer on screen if there was any.
            this.volunteer = new VolunteerModel();
          }
        );
      }));

    this.destroyUtil.addSubscription(this.volunteerDetailService.getVolunteer().subscribe(
      (volunteer: VolunteerModel) => this.volunteer = volunteer,
      () => {console.log('error')},
        () => {console.log('completed')}
    ));
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }



  // All code for determining which component should be shown to the right.
  public TASK_ADD = 'task_add';
  public TASK_EDIT = 'task_edit';
  public TASK_VIEW = 'task_view';
  public INSTANCE_ADD = 'instance_add';
  public INSTANCE_EDIT = 'instance_edit';
  public currentItemInstance: string;
  public currentItem: any;

  clear() {
    this.currentItem = null;
    this.currentItemInstance = null;
  }
  refresh() {
    this.ngOnInit();
    this.clear();
  }

  refreshAndOpenItem(currentItem) {
    this.ngOnInit();
    this.setTask(currentItem);
  }

  setTask(task: TaskModel) {
    this.currentItem = task;
    this.currentItemInstance = this.TASK_VIEW;
  }
  editTask() {
    this.currentItemInstance = this.TASK_EDIT;
  }
  setNewTask() {
    this.currentItem = null;
    this.currentItemInstance = this.TASK_ADD;
  }
  setInstance(instance: VolunteerInstanceModel) {
    this.currentItem = instance;
    this.currentItemInstance = this.INSTANCE_EDIT;
  }
  setNewInstance() {
    this.currentItem = null;
    this.currentItemInstance = this.INSTANCE_ADD;
  }
}
