import {Injectable} from "@angular/core";
import {MyHttpClient} from "./base/myhttpclient.service";
import 'rxjs/Rx';
import {StudentDVO} from "../valueobject/dvo/student.dvo";

@Injectable()
export class StudentService {
  constructor(private myHttpClient: MyHttpClient) {}

  searchStudents(search: String, hasMatch: Boolean) {
    const url: string = 'student' + MyHttpClient.createParameterUrl([
      {name: 'search', value: search}, {name: 'hasMatch', value: hasMatch}]);
    return this.myHttpClient.get(url, null);
  }

  insertStudent(student: StudentDVO) {
    return this.myHttpClient.put('student', null, student);
  }

  updateStudent(student: StudentDVO) {
    return this.myHttpClient.post('student' + student.externalIdentifier, null, student);
  }

  getStudent(studentExtId: string) {
    return this.myHttpClient.get('student/' + studentExtId, null);
  }

  getStudentForMatch() {
    return this.myHttpClient.get('student/match', null);
  }
}
