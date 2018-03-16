import {VolunteerInstanceModel} from "../valueobject/volunteerinstance.model";

export class CopyUtil {
  public static createCopy(volunteerInstanceModel: VolunteerInstanceModel): VolunteerInstanceModel {
    let copy: VolunteerInstanceModel = new VolunteerInstanceModel();
    copy.volunteerExtId = volunteerInstanceModel.volunteerExtId;
    copy.externalIdentifier = volunteerInstanceModel.externalIdentifier;
    copy.dateStart = volunteerInstanceModel.dateStart;
    copy.dateEnd = volunteerInstanceModel.dateEnd;
    return copy;
  }

}
