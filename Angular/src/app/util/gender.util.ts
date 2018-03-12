import {Injectable} from "@angular/core";

@Injectable()
export class GenderUtil {
  public static genders: any[] = [
    {id: 'M', value: 'Man'},
    {id: 'V', value: 'Vrouw'}];

  public static getMaleId(): string {
    return this.genders[0].id;
  }
}
