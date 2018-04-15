import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertModel} from "../../../block/alert/alert.model";
import {DestroyUtil} from "../../../../util/destroy.util";
import {ReportVO} from "../../../../valueobject/report/report.vo";
import {ReportService} from "../../../../services/report.service";

@Component({
  selector: 'app-report-view',
  templateUrl: './report.view.html'
})
export class ReportView implements OnInit, OnDestroy {
  private destroyUtil: DestroyUtil = new DestroyUtil();
  public alertModel = new AlertModel();
  public reportVO: ReportVO = new ReportVO();

  constructor(private reportService: ReportService,
              private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.destroyUtil.addSubscription(this.route.queryParams.subscribe(
      (params) => {
        this.reportService.getReport(params['dateStart'], params['dateEnd']).subscribe(
          (response: ReportVO) => {
            this.reportVO = response;
          },
          (error) => {
            this.alertModel.setError(error);
            this.reportVO = new ReportVO();
          }
        );
      }
    ));
  }

  ngOnDestroy(): void {
    this.destroyUtil.destroy();
  }

  onBack() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
