import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import 'rxjs/Rx';
import {StudentModel} from "../valueobject/student.model";
@Injectable()
export class StudentService {
  public currentStudent: StudentModel;

  constructor(private myHttpClient: MyHttpClient) {}

  searchStudents(search: String, hasMatch: Boolean) {
    const url: string = 'student' + MyHttpClient.createParameterUrl([
      {name: 'search', value: search}, {name: 'hasMatch', value: hasMatch}]);
    return this.myHttpClient.get(url, null);
  }

  insertStudent(studentModel: StudentModel) {
    return this.myHttpClient.put('student', null, studentModel);
  }

  getStudent(studentExtId: string) {
    return this.myHttpClient.get('student/' + studentExtId, null);
  }
}
