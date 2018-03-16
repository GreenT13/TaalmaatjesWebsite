import {Component, Input} from "@angular/core";

@Component({
  selector: 'app-alert-component',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent {
  @Input()
  public error;

}
