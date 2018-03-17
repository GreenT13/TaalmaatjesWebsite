import {Component, Input} from "@angular/core";
import {AlertModel} from "./alert.model";

@Component({
  selector: 'app-alert-component',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent {
  @Input()
  public alertModel: AlertModel;

}
