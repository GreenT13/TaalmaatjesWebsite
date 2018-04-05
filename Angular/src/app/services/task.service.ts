import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import {TaskDVO} from "../valueobject/dvo/task.dvo";

@Injectable()
export class TaskService {
  constructor(private myHttpClient: MyHttpClient) {}

  searchTask(title: String, description: String, isFinished: Boolean, volunteerExtId: String) {
    const url: string = 'task' + MyHttpClient.createParameterUrl([
      {name: 'title', value: title}, {name: 'description', value: description}, {name: 'isFinished', value: isFinished},
      {name: 'volunteerExtId', value: volunteerExtId}]);
    return this.myHttpClient.get(url, null);
  }

  insertTask(task: TaskDVO) {
    return this.myHttpClient.put('task', null, task);
  }

  getTask(taskExtId: string) {
    return this.myHttpClient.get('task/' + taskExtId, null);
  }

  changeState(task: TaskDVO) {
    task.isFinished = !task.isFinished;
    return this.updateTask(task);
  }

  updateTask(task: TaskDVO) {
    return this.myHttpClient.post('task/' + task.externalIdentifier, null, task);
  }

}
