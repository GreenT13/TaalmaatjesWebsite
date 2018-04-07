import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
  selector: 'app-datepicker-component',
  templateUrl: './datepicker.component.html'
})
export class DatepickerComponent {
  @Input()
  public label: string;

  @Input()
  public date: string;

  @Output()
  public dateChange : EventEmitter<string> = new EventEmitter<string>();

  @Input()
  public required = true;

  @Input()
  public triedSubmit = false;
}
