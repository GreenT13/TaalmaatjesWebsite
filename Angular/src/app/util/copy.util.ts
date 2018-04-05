import {VolunteerDVO} from "../valueobject/dvo/volunteer.dvo";
import {VolunteerInstanceDVO} from "../valueobject/dvo/volunteerinstance.dvo";

export class CopyUtil {
  public static createCopyVolunteerInstance(volunteerInstance: VolunteerInstanceDVO): VolunteerInstanceDVO {
    let copy: VolunteerInstanceDVO = new VolunteerInstanceDVO();
    copy.volunteerDVO = volunteerInstance.volunteerDVO;
    copy.externalIdentifier = volunteerInstance.externalIdentifier;
    copy.dateStart = volunteerInstance.dateStart;
    copy.dateEnd = volunteerInstance.dateEnd;
    return copy;
  }

  public static createCopyVolunteer(volunteer: VolunteerDVO): VolunteerDVO {
    let copy: VolunteerDVO = new VolunteerDVO();
    copy.externalIdentifier = volunteer.externalIdentifier;
    copy.firstName = volunteer.firstName;
    copy.insertion = volunteer.insertion;
    copy.lastName = volunteer.lastName;
    copy.dateOfBirth = volunteer.dateOfBirth;
    copy.gender = volunteer.gender;
    copy.phoneNumber = volunteer.phoneNumber;
    copy.mobilePhoneNumber = volunteer.mobilePhoneNumber;
    copy.email = volunteer.email;
    copy.dateTraining = volunteer.dateTraining;
    copy.postalCode = volunteer.postalCode;
    copy.city = volunteer.city;
    copy.streetName = volunteer.streetName;
    copy.houseNr = volunteer.houseNr;
    copy.log = volunteer.log;
    copy.job = volunteer.job;
    copy.isClassAssistant = volunteer.isClassAssistant;
    copy.isTaalmaatje = volunteer.isTaalmaatje;
    return copy;
  }

}
