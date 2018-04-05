import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from "@angular/core";
import {VolunteerService} from "../../../services/volunteer.service";
import {NamePipe} from "../../../pipes/name.pipe";
import {VolunteerDVO} from "../../../valueobject/dvo/volunteer.dvo";

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.css']
})
export class AutocompleteComponent implements OnChanges {
  public query: string = "";
  public volunteers: VolunteerDVO[];
  public filteredList = [];

  @Input()
  public disabled: boolean = false;
  @Input()
  public volunteer: VolunteerDVO;
  @Output()
  volunteerChange = new EventEmitter<VolunteerDVO>();

  constructor(private volunteerService: VolunteerService) {
    this.volunteerService.searchVolunteers(this.query, null, true, null, null).subscribe(
      (volunteers: VolunteerDVO[]) => {
        this.volunteers = volunteers;
    });
  }

  ngOnChanges(changes: SimpleChanges){
    // Allow prefilling default values. Make sure it doesn't overwrite if we want to overwrite with an empty volunteer.
    if (changes.volunteer && NamePipe.parseName(changes.volunteer.currentValue).length > 0) {
      this.query = NamePipe.parseName(changes.volunteer.currentValue);
    }
  }

  filter() {
    if (this.query !== ""){
      // Filter the list, with at most 10 elements.
      let count = 0;
      this.filteredList = this.volunteers.filter(function(volunteer: VolunteerDVO){
        if (count > 10) {
          return false;
        }
        for (let section of this.query.split(" ")) {
          if (!NamePipe.containsString(section, volunteer)) {
            return false;
          }
        }
        count++;
        return true;
      }.bind(this));
      this.setVolunteer(new VolunteerDVO());
    } else {
      this.filteredList = [];
      this.setVolunteer(new VolunteerDVO());
    }
  }

  select(volunteer: VolunteerDVO){
    this.query = NamePipe.parseName(volunteer);
    this.setVolunteer(volunteer);
    this.filteredList = [];
  }

  setVolunteer(volunteer: VolunteerDVO) {
    this.volunteer = volunteer;
    this.volunteerChange.emit(this.volunteer);
  }
}
