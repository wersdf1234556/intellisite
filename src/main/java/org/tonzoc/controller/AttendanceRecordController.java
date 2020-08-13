package org.tonzoc.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.AttendanceDetailQueryParams;
import org.tonzoc.controller.params.AttendanceRecordQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.AttendanceDetailModel;
import org.tonzoc.model.AttendanceRecordModel;
import org.tonzoc.model.support.StatPersonModel;
import org.tonzoc.service.IAttendanceDetailService;
import org.tonzoc.service.IAttendanceRecordService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "attendanceRecord")
public class AttendanceRecordController extends BaseController {
    @Autowired
    private IAttendanceRecordService attendanceRecordService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, AttendanceRecordQueryParams attendanceRecordQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<AttendanceRecordModel> page = parsePage(pageQueryParams);
        AttendanceRecordQueryParams sqlQueryParamList = new AttendanceRecordQueryParams();
        if (attendanceRecordQueryParams.getAttendanceDate() != null && !attendanceRecordQueryParams.getAttendanceDate().equals("")) {
            sqlQueryParamList.setAttendanceDate(attendanceRecordQueryParams.getAttendanceDate());
        }
        if (attendanceRecordQueryParams.getIdCard() != null && !attendanceRecordQueryParams.getIdCard().equals("")) {
            sqlQueryParamList.setIdCard(attendanceRecordQueryParams.getIdCard());
        }
        if (attendanceRecordQueryParams.getSource() != null && !attendanceRecordQueryParams.getSource().equals("")) {
            sqlQueryParamList.setSource(attendanceRecordQueryParams.getSource());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);

        List<AttendanceRecordModel> list = attendanceRecordService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid AttendanceRecordModel attendanceRecordModel) {
        this.attendanceRecordService.save(attendanceRecordModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid AttendanceRecordModel attendanceRecordModel) {
        this.attendanceRecordService.update(attendanceRecordModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.attendanceRecordService.remove(guid);
    }

//    @GetMapping(value = "countAtt")
//    public StatPersonModel countAtt(String projectId, String date){
//        return attendanceRecordService.countAtt(projectId, date);
//    }


}
