package org.tonzoc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.AttendanceDetailQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.AttendanceDetailModel;
import org.tonzoc.model.support.StatPersonModel;
import org.tonzoc.service.IAttendanceDetailService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "attendanceDetail")
public class AttendanceDetailController extends BaseController {
    @Autowired
    private IAttendanceDetailService attendancedetailService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, AttendanceDetailQueryParams attendanceDetailQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<AttendanceDetailModel> page = parsePage(pageQueryParams);
        AttendanceDetailQueryParams sqlQueryParamList = new AttendanceDetailQueryParams();

        if (attendanceDetailQueryParams.getPersonName() != null && !attendanceDetailQueryParams.getPersonName().equals("")) {
            sqlQueryParamList.setPersonName(attendanceDetailQueryParams.getPersonName());
        }
        if (attendanceDetailQueryParams.getWorkDate() != null && !attendanceDetailQueryParams.getWorkDate().equals("")) {
            sqlQueryParamList.setWorkDate(attendanceDetailQueryParams.getWorkDate());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);

        List<AttendanceDetailModel> list = attendancedetailService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid AttendanceDetailModel attendanceDetailModel) {
        this.attendancedetailService.save(attendanceDetailModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid AttendanceDetailModel attendanceDetailModel) {
        this.attendancedetailService.update(attendanceDetailModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.attendancedetailService.remove(guid);
    }

    @GetMapping(value = "export")
    public void export(HttpServletResponse response, String departmentId, String startDate, String endDate) throws Exception{
        attendancedetailService.export(response,departmentId,startDate,endDate);
    }
}
