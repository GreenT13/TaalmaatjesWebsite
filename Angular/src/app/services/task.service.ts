import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import {VolunteerModel} from "../valueobject/volunteer.model";
import {TaskModel} from "../valueobject/task.model";

@Injectable()
export class TaskService {
  public currentVolunteer: VolunteerModel;

  constructor(private myHttpClient: MyHttpClient) {}

  searchTask(title: String, description: String, isFinished: Boolean, volunteerExtId: String) {
    const url: string = 'task' + MyHttpClient.createParameterUrl([
      {name: 'title', value: title}, {name: 'description', value: description}, {name: 'isFinished', value: isFinished},
      {name: 'volunteerExtId', value: volunteerExtId}]);
    return this.myHttpClient.get(url, null);
  }

  insertTask(taskModel: TaskModel) {
    return this.myHttpClient.put('task', null, taskModel);
  }

  getTask(taskExtId: string) {
    return this.myHttpClient.get('task/' + taskExtId, null);
  }

  changeState(taskModel: TaskModel) {
    taskModel.isFinished = !taskModel.isFinished;
    return this.updateTask(taskModel);
  }

  updateTask(taskModel: TaskModel) {
    return this.myHttpClient.post('task/' + taskModel.taskExtId, null, taskModel);
  }

}
