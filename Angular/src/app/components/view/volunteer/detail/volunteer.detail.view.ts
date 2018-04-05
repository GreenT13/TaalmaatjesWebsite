import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {DestroyUtil} from "../../../../util/destroy.util";
import {AlertModel} from "../../../block/alert/alert.model";
import {VolunteerVOGet} from "../../../../valueobject/volunteer.vo.get";
import {VolunteerService} from "../../../../services/volunteer.service";
import {TaskDVO} from "../../../../valueobject/dvo/task.dvo";
import {VolunteerInstanceDVO} from "../../../../valueobject/dvo/volunteerinstance.dvo";
import {VolunteerMatchDVO} from "../../../../valueobject/dvo/volunteermatch.dvo";
import {VolunteerDVO} from "../../../../valueobject/dvo/volunteer.dvo";

@Component({
  selector: 'app-volunteer-detail-view',
  templateUrl: './volunteer.detail.view.html'
})
export class VolunteerDetailView implements OnInit, OnDestroy {
  private destroyUtil: DestroyUtil = new DestroyUtil();
  public alertModel = new AlertModel();

  public volunteer: VolunteerVOGet = new VolunteerVOGet();

  constructor(private volunteerService: VolunteerService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.destroyUtil.addSubscription(this.route.params.subscribe(
      (params) => {
        this.volunteerService.getVolunteer(params['volunteerExtId']).subscribe(
          (response: VolunteerVOGet) => {
            this.volunteer = response;
          },
          (error) => {
            this.alertModel.setError(error);
            // Clear the volunteer on screen if there was any.
            this.volunteer = new VolunteerVOGet();
          }
        );
      }
    ));
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }


  onDeleteActive(volunteerInstanceExtId: string) {
    // Remove the instance from the list of instances
    this.volunteer.volunteerInstanceDVOS = this.volunteer.volunteerInstanceDVOS.filter(
      volunteerInstance => volunteerInstance.externalIdentifier !== volunteerInstanceExtId
    );

    // Also clear the block.
    this.clear();
  }

  onDeleteMatch(volunteerMatchExtId: string) {
    // Remove the match from the list of matches
    this.volunteer.volunteerMatchDVOS = this.volunteer.volunteerMatchDVOS.filter(
      volunteerMatch => volunteerMatch.externalIdentifier !== volunteerMatchExtId
    );

    // Also clear the block.
    this.clear();
  }


  // All code for determining which component should be shown to the right.
  public VOLUNTEER_EDIT = 'volunteer_edit';
  public TASK_ADD = 'task_add';
  public TASK_EDIT = 'task_edit';
  public TASK_VIEW = 'task_view';
  public INSTANCE_ADD = 'instance_add';
  public INSTANCE_EDIT = 'instance_edit';
  public MATCH_ADD = 'match_add';
  public MATCH_EDIT = 'match_edit';
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

  setVolunteer(volunteer: VolunteerDVO) {
    this.currentItem = volunteer;
    this.currentItemInstance = this.VOLUNTEER_EDIT;
  }
  setTask(task: TaskDVO) {
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
  setInstance(instance: VolunteerInstanceDVO) {
    this.currentItem = instance;
    this.currentItemInstance = this.INSTANCE_EDIT;
  }
  setNewInstance() {
    this.currentItem = null;
    this.currentItemInstance = this.INSTANCE_ADD;
  }
  setMatch(match: VolunteerMatchDVO) {
    this.currentItem = match;
    this.currentItemInstance = this.MATCH_EDIT;
  }
  setNewMatch() {
    this.currentItem = null;
    this.currentItemInstance = this.MATCH_ADD;
  }
}
