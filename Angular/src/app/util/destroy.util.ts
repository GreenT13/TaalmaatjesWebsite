import {Subscription} from "rxjs/Subscription";

export class DestroyUtil {
  private subscribed: Subscription[] = [];

  public addSubscription(subscription: Subscription) {
    this.subscribed.push(subscription);
  }

  public destroy() {
    for (let subscribe of this.subscribed) {
      subscribe.unsubscribe();
    }
  }
}
