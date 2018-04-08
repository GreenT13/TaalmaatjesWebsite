import {Component, EventEmitter, Input, Output} from "@angular/core";
import {ControlContainer, NgForm} from "@angular/forms";

@Component({
  selector: 'app-datepicker-component',
  templateUrl: './datepicker.component.html',
  // Add viewProviders, so the input will be treated part of the form in the parent.
  // https://stackoverflow.com/questions/39242219/angular2-nested-template-driven-form
  viewProviders: [ { provide: ControlContainer, useExisting: NgForm } ]
})
export class DatepickerComponent {
  @Input()
  public name: string;

  @Input()
  public label: string;

  @Input()
  public date: string;

  @Output()
  public dateChange : EventEmitter<string> = new EventEmitter<string>();

  @Input()
  public required: boolean = true;

  @Input()
  public triedSubmit = false;
}
