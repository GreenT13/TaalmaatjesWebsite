package com.apon.service;

import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.StudentMyDao;
import com.apon.database.mydao.VolunteerMyDao;
import com.apon.exceptionhandler.FunctionalException;
import com.apon.guice.InjectContext;
import com.apon.service.valueobject.report.ReportPersonVO;
import com.apon.service.valueobject.report.ReportRange;
import com.apon.service.valueobject.report.ReportVO;
import com.apon.util.DateTimeUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "RedundantThrows"})
@Path("report")
@Produces(MediaType.APPLICATION_JSON)
public class ReportService implements IService {
    private DbContext context;

    /**
     * Create report based on dateStart and dateEnd.
     * @param stringDateStart The start date.
     * @param stringDateEnd The end date.
     * @return RepoortVO
     */
    @GET
    @InjectContext
    public ReportVO createReport(@QueryParam("dateStart") String stringDateStart,
                                 @QueryParam("dateEnd") String stringDateEnd) throws Exception {
        Date dateStart  = stringDateStart != null ? DateTimeUtil.convertStringToDate(stringDateStart) : null;
        Date dateEnd  = stringDateEnd != null ? DateTimeUtil.convertStringToDate(stringDateEnd) : null;

        if (dateStart == null || dateEnd == null) {
            throw new FunctionalException("ReportAPI.createReport.error.noDate");
        }

        if (dateStart.compareTo(dateEnd) > 0) {
            throw new FunctionalException("ReportAPI.createReport.error.startBeforeEnd");
        }

        ReportVO reportVO = new ReportVO(dateStart, dateEnd);
        // Fill the volunteers
        VolunteerMyDao volunteerMyDao = new VolunteerMyDao(context);
        List<ReportPersonVO> volunteerReport = new ArrayList();
        for (ReportRange reportRange : ReportRange.values()) {
            Integer countNew = volunteerMyDao.countNew(dateStart, dateEnd,
                    reportRange.getMinAge(), reportRange.getMaxAge(), reportRange.getSex());
            Integer countActive = volunteerMyDao.countActive(dateStart, dateEnd,
                    reportRange.getMinAge(), reportRange.getMaxAge(), reportRange.getSex());

            // Add new active volunteers.
            volunteerReport.add(
                    new ReportPersonVO(reportRange.getMinAge(), reportRange.getMaxAge(),
                            reportRange.getSex(), countNew, countActive));
        }
        reportVO.setVolunteers(volunteerReport);

        // Fill the volunteers
        StudentMyDao studentMyDao = new StudentMyDao(context);
        List<ReportPersonVO> studentReport = new ArrayList();
        for (ReportRange reportRange : ReportRange.values()) {
            Integer countNew = studentMyDao.countNew(dateStart, dateEnd,
                    reportRange.getMinAge(), reportRange.getMaxAge(), reportRange.getSex());
            Integer countActive = studentMyDao.countActive(dateStart, dateEnd,
                    reportRange.getMinAge(), reportRange.getMaxAge(), reportRange.getSex());

            // Add new active volunteers.
            studentReport.add(
                    new ReportPersonVO(reportRange.getMinAge(), reportRange.getMaxAge(),
                            reportRange.getSex(), countNew, countActive));
        }
        reportVO.setStudents(studentReport);

        return reportVO;
    }

    @Override
    public DbContext getContext() {
        return context;
    }

    @Override
    public void setContext(DbContext context) {
        this.context = context;
    }
}
