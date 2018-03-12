import {EventEmitter, Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";

@Injectable()
export class OverlayService {
  // Need to give 'true' as parameter, since this will prevent errors.
  // See https://blog.angularindepth.com/everything-you-need-to-know-about-the-expressionchangedafterithasbeencheckederror-error-e3fd9ce7dbb4
  public showOverlayEventEmitter = new EventEmitter(true);
  private nrWaiting: number = 0;

  public addOverlayWait(observable: Observable<Object>) {
    this.nrWaiting++;
    // It is probably fine to emit 'true' multiple times.
    this.showOverlayEventEmitter.emit(true);
    observable.subscribe(
      ()=>{},
      // Note that either error or complete occurs, not both.
      () => this.minusObserver(),
      () => this.minusObserver());

    // Return observer so we can use lambda expression style.
    return observable;
  }

  private minusObserver() {
    this.nrWaiting--;
    if (this.nrWaiting === 0) {
      this.showOverlayEventEmitter.emit(false);
    }
  }
}
