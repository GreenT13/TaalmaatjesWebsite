import {HttpErrorResponse} from "@angular/common/http";

export class AlertModel {
  private error;
  private success;
  private warning;

  public setError(error: HttpErrorResponse) {
    if (error) {
      this.error = error.error;
    }
    this.success = null;
    this.warning = null;
  }

  public setSuccess(success) {
    this.error = null;
    if (success) {
      this.success = success;
    }
    this.warning = null;
  }

  public setWarning(warning) {
    this.error = null;
    this.success = null;
    this.warning = warning;
  }

  public getElement() {
    if (this.error != null) {
      return this.error;
    }
    if (this.success != null) {
      return this.success;
    }
    if (this.warning != null) {
      return this.warning;
    }
  }

  public getClass(): string {
    if (this.error != null) {
      return 'danger';
    }
    if (this.success != null) {
      return 'success';
    }
    if (this.warning != null) {
      return 'warning';
    }
  }

  public clear() {
    this.error = null;
    this.success = null;
    this.warning = null;
  }
}
