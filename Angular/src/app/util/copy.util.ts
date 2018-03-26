import {VolunteerInstanceModel} from "../valueobject/volunteerinstance.model";
import {VolunteerModel} from "../valueobject/volunteer.model";

export class CopyUtil {
  public static createCopyVolunteerInstance(volunteerInstanceModel: VolunteerInstanceModel): VolunteerInstanceModel {
    let copy: VolunteerInstanceModel = new VolunteerInstanceModel();
    copy.volunteerExtId = volunteerInstanceModel.volunteerExtId;
    copy.externalIdentifier = volunteerInstanceModel.externalIdentifier;
    copy.dateStart = volunteerInstanceModel.dateStart;
    copy.dateEnd = volunteerInstanceModel.dateEnd;
    return copy;
  }

  public static createCopyVolunteer(volunteerModel: VolunteerModel): VolunteerModel {
    let copy: VolunteerModel = new VolunteerModel();
    copy.externalIdentifier = volunteerModel.externalIdentifier;
    copy.firstName = volunteerModel.firstName;
    copy.insertion = volunteerModel.insertion;
    copy.lastName = volunteerModel.lastName;
    copy.dateOfBirth = volunteerModel.dateOfBirth;
    copy.gender = volunteerModel.gender;
    copy.phoneNumber = volunteerModel.phoneNumber;
    copy.mobilePhoneNumber = volunteerModel.mobilePhoneNumber;
    copy.email = volunteerModel.email;
    copy.dateTraining = volunteerModel.dateTraining;
    copy.postalCode = volunteerModel.postalCode;
    copy.city = volunteerModel.city;
    copy.streetName = volunteerModel.streetName;
    copy.houseNr = volunteerModel.houseNr;
    copy.log = volunteerModel.log;
    copy.job = volunteerModel.job;
    copy.isClassAssistant = volunteerModel.isClassAssistant;
    copy.isTaalmaatje = volunteerModel.isTaalmaatje;
    return copy;
  }

}
