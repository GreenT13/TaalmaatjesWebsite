import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from "@angular/core";
import {VolunteerModel} from "../../valueobject/volunteer.model";
import {VolunteerService} from "../../services/volunteer.service";
import {VolunteerNamePipe} from "../../pipes/volunteername.pipe";

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.css']
})
export class AutocompleteComponent implements OnChanges {
  public query: string = "";
  public volunteers: VolunteerModel[];
  public filteredList = [];

  @Input()
  public disabled: boolean = false;
  @Input()
  public volunteer: VolunteerModel;
  @Output()
  volunteerChange = new EventEmitter<VolunteerModel>();

  constructor(private volunteerService: VolunteerService) {
    this.volunteerService.searchVolunteers(this.query, null, true, null, null).subscribe(
      (volunteers: VolunteerModel[]) => {
        this.volunteers = volunteers;
    });
  }

  ngOnChanges(changes: SimpleChanges){
    // Allow prefilling default values. Make sure it doesn't overwrite if we want to overwrite with an empty volunteer.
    if (changes.volunteer && VolunteerNamePipe.parseName(changes.volunteer.currentValue).length > 0) {
      this.query = VolunteerNamePipe.parseName(changes.volunteer.currentValue);
    }
  }

  filter() {
    if (this.query !== ""){
      // Filter the list, with at most 10 elements.
      let count = 0;
      this.filteredList = this.volunteers.filter(function(volunteer: VolunteerModel){
        if (count > 10) {
          return false;
        }
        for (let section of this.query.split(" ")) {
          if (!VolunteerNamePipe.containsString(section, volunteer)) {
            return false;
          }
        }
        count++;
        return true;
      }.bind(this));
      this.setVolunteer(new VolunteerModel());
    } else {
      this.filteredList = [];
      this.setVolunteer(new VolunteerModel());
    }
  }

  select(volunteer: VolunteerModel){
    this.query = VolunteerNamePipe.parseName(volunteer);
    this.setVolunteer(volunteer);
    this.filteredList = [];
  }

  setVolunteer(volunteer: VolunteerModel) {
    this.volunteer = volunteer;
    this.volunteerChange.emit(this.volunteer);
  }
}
