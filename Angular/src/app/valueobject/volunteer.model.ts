import {VolunteerInstanceModel} from "./volunteerinstance.model";
import {VolunteerMatchModel} from "./volunteermatch.model";
import {TaskModel} from "./task.model";
import {GenderUtil} from "../util/gender.util";

export class VolunteerModel {
  public externalIdentifier: string;
  public firstName: string;
  public insertion: string;
  public lastName: string;
  public dateOfBirth: string;
  public gender: string;
  public phoneNumber: string;
  public mobilePhoneNumber: string;
  public email: string;
  public dateTraining: string;
  public postalCode: string;
  public city: string;
  public streetName: string;
  public houseNr: string;
  public log: string;
  public job: string;
  public isClassAssistant: boolean;
  public isTaalmaatje: boolean;

  // Only for adding volunteers.
  public dateStartActive: string;

  public volunteerInstanceValueObjects: VolunteerInstanceModel[];
  public volunteerMatchValueObjects: VolunteerMatchModel[];
  public taskValueObjects: TaskModel[];

  constructor() {
    this.gender = GenderUtil.getMaleId();
  }
}
