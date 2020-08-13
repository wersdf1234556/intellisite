package org.tonzoc.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.configuration.IntelliSiteProperties;
import org.tonzoc.exception.FileReadErrorException;
import org.tonzoc.mapper.AttendanceDetailMapper;
import org.tonzoc.mapper.MakeUpAttendanceApplyMapper;
import org.tonzoc.mapper.PersonMapper;
import org.tonzoc.model.AttendanceDetailModel;
import org.tonzoc.model.DepartmentModel;
import org.tonzoc.model.MakeUpAttendanceApplyModel;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.support.AttendanceStateModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IAttendanceDetailService;
import org.tonzoc.service.IDepartmentService;
import org.tonzoc.service.IPersonService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "attendancedetailService")
public class AttendanceDetailService extends BaseService<AttendanceDetailModel> implements IAttendanceDetailService {
    //补考勤申请
    @Autowired
    private MakeUpAttendanceApplyMapper makeUpAttendanceApplyMapper;
    //人员
    @Autowired
    private PersonMapper personMapper;
    //考勤表
    @Autowired
    private AttendanceDetailMapper attendanceDetailMapper;
    @Autowired
    private IPersonService personService;
    @Autowired
    private IntelliSiteProperties intelliSiteProperties;
    @Autowired
    private IDepartmentService departmentService;


    /**
     * 查询人员刨除补考勤后的近五天考勤异常记录
     * @param openId
     * @return
     * @throws ParseException
     */
    @Override
    public ResultJson queryAbsent(String openId) throws ParseException {
        ResultJson resultJson = new ResultJson();
        List<AttendanceDetailModel> kaoqinriqiList = new ArrayList<>();
        //根据openId查询人员身份证号
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));
        List<PersonModel> personModelList = personMapper.selectAll(sqlQueryParams);
        PersonModel personModel = personModelList.get(0);
        String idCard = personModel.getIdCard();
//        查询前5天考勤异常记录,用考勤日期去补考勤表查询,如果在补考勤表中查不到,就返回
        List<AttendanceDetailModel> AttendanceDetailModelList = attendanceDetailMapper.select5AbsentByIdCard(idCard);
        for (int i = 0; i < AttendanceDetailModelList.size(); i++) {
            AttendanceDetailModel attendanceDetailModel = AttendanceDetailModelList.get(i);
            String workDate = attendanceDetailModel.getWorkDate();
            List<SqlQueryParam> sqlQueryParams1 = new ArrayList<>();
            sqlQueryParams1.add(new SqlQueryParam("workDate", workDate, "eq"));
            List<MakeUpAttendanceApplyModel> makeUpAttendanceApplyModelList = makeUpAttendanceApplyMapper.selectAll(sqlQueryParams1);
            if (makeUpAttendanceApplyModelList.isEmpty()) {
                kaoqinriqiList.add(attendanceDetailModel);
            }
        }
        if (kaoqinriqiList.isEmpty()) {
            resultJson.setCode("200");
            resultJson.setMsg("最近五天考勤异常为空");
        } else {
            resultJson.setMsg("最近五天异常考勤记录");
            resultJson.setObj(kaoqinriqiList);
        }
        return resultJson;
    }

    /**
     * 查询该人员近5天补考勤请求
     *
     * @param openId
     * @return
     */
    @Override
    public ResultJson queryApply(String openId) {
        ResultJson resultJson = new ResultJson();
        //根据openid查询人员获取人员guid
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));
        List<PersonModel> personModelList = personMapper.selectAll(sqlQueryParams);
        PersonModel personModel = personModelList.get(0);
        String personGuid = personModel.getGuid();
        System.out.println("personGuid" + personGuid);
        //根据人员guid查询近5天补考勤需求
        List<MakeUpAttendanceApplyModel> makeUpAttendanceApplyModelList = makeUpAttendanceApplyMapper.select5Day(personGuid);
        System.out.println("makeUpAttendanceApplyModelList" + makeUpAttendanceApplyModelList);
        //如果没有补考勤请求
        if (makeUpAttendanceApplyModelList.isEmpty()) {
            resultJson.setCode("400");
            resultJson.setMsg("没有补考勤请求");
        } else {
            resultJson.setCode("200");
            resultJson.setMsg("有补考勤请求");
            resultJson.setObj(makeUpAttendanceApplyModelList);
        }
        return resultJson;
    }


    //考勤数据导出
    public void export(HttpServletResponse response,String departmentId,String startDate,String endDate) throws Exception{
        DepartmentModel departmentModel = departmentService.get(departmentId);
        List<String> personNames = personService.listByDepartmentId(departmentId)
                .stream()
                .map(PersonModel::getName)
                .collect(Collectors.toList());
        List<String> dates = findDates(startDate,endDate);
        //创建Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet("模板");
        //首行固定
//        sheet.createFreezePane( 0, 1, 0, 1 );//设置第一行冰冻
        sheet.createFreezePane( 1, 1, 1, 1 );//首行、首列都固定
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(10);

        //居中
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        //创建第一行表头
        XSSFRow headrow = sheet.createRow(0);
        headrow.setHeightInPoints(42);
        //文本格式
        XSSFCellStyle textStyle = workbook.createCellStyle();
        XSSFDataFormat format2 = workbook.createDataFormat();
        textStyle.setDataFormat(format2.getFormat("@"));
        textStyle.setAlignment(HorizontalAlignment.CENTER);
        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        textStyle.setWrapText(true);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // 水平对齐
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        // 垂直对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        cellStyle.setWrapText(true);

        //遍历添加表头
        for (int i = 0; i <= dates.size(); i++) {
            //创建一个单元格
            XSSFCell cell = headrow.createCell(i);

            if (i==0){
                cell.setCellValue("      日期  \r\n\n姓名") ;
//                style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直bai
                cell.setCellStyle(cellStyle);
                //画线(由左上到右下的斜线)  在A1的第一个cell（单位  分类）加入一条对角线
                CreationHelper helper = workbook.getCreationHelper();
                XSSFDrawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = helper.createClientAnchor();
                XSSFSimpleShape shape = drawing.createSimpleShape((XSSFClientAnchor) anchor);
                // 设置形状类型为线型
                shape.setShapeType(ShapeTypes.LINE);
                // 设置斜线的开始位置
                anchor.setCol1(0);
                anchor.setRow1(0);
                // 设置斜线的结束位置
                anchor.setCol2(1);
                anchor.setRow2(1);
                // 设置形状类型为线型
                shape.setShapeType(ShapeTypes.LINE);
                // 设置线宽
                shape.setLineWidth(0.5);
                // 设置线的风格
                shape.setLineStyle(0);
                // 设置线的颜色
                shape.setLineStyleColor(0, 0, 0);
            }else {
//                System.out.println("i"+i);
                //创建一个内容对象
                XSSFRichTextString text = new XSSFRichTextString(dates.get(i-1));
                //将内容对象的文字内容写入到单元格中
                cell.setCellValue(text);
                cell.setCellStyle(textStyle);
            }

        }
        String[] states={"未签到","未签退","旷工","请假"};
        for(int i = 1; i <= states.length; i++){
            XSSFCell stateCell = headrow.createCell(dates.size()+i);
            System.out.println(states[i-1]);
            stateCell.setCellValue(states[i-1]);
            stateCell.setCellStyle(textStyle);
        }
        Cell cell=null;
        for (int i = 0; i < personNames.size(); i++) {
            Row nextrow = sheet.createRow(i+1);

            cell=nextrow.createCell(0);
            cell.setCellValue(personNames.get(i));
            for(int j=0;j<dates.size();j++){
                AttendanceDetailModel attendanceDetailModel = attendanceDetailMapper.listByCondition(departmentId,dates.get(j),personNames.get(i));
                if (attendanceDetailModel!=null){
                    String val = graph(attendanceDetailModel.getLeave(),attendanceDetailModel.getInState(),attendanceDetailModel.getOutState());
                    cell=nextrow.createCell(j+1);
                    System.out.println("val="+val);
                    cell.setCellValue(val);
                    cell.setCellStyle(style);
                }
            }
            AttendanceStateModel stateCount = attendanceDetailMapper.stateCount(departmentId,personNames.get(i),startDate,endDate);
                for(int z = 1; z <= states.length; z++){
                    Cell stateCell = nextrow.createCell(dates.size()+z);
                    if (stateCount!=null){
                        if (z==1){
                            stateCell.setCellValue(stateCount.getNoSignIn());
                        }else if (z==2){
                            stateCell.setCellValue(stateCount.getNoSignOut());
                        }else if (z==3){
                            stateCell.setCellValue(stateCount.getAbsent());
                        }else if (z==4){
                            stateCell.setCellValue(stateCount.getLeave());
                        }
                    }else {
                        stateCell.setCellValue("0");
                    }

                    stateCell.setCellStyle(textStyle);
                }


        }
        Row finalRow = sheet.createRow(personNames.size()+1);
        CellRangeAddress region = new CellRangeAddress(personNames.size()+1, personNames.size()+2, 0, 8);
        sheet.addMergedRegion(region);
        cell=finalRow.createCell(0);
        cell.setCellValue("说明：正常打卡：√，迟到：→，早退：←，旷工：×，休息日：〇 ，请假：△ ，未签到：> ，未签退：<");

        String filePath = intelliSiteProperties.getFilePath();
        String fileName = departmentModel.getName()+"考勤记录"; // 新文件名

        File file = new File(filePath +"/"+ fileName + ".xlsx");
        file.createNewFile();
        //将Excel内容存盘
        FileOutputStream stream = FileUtils.openOutputStream(file);
        workbook.write(stream);
        stream.close();
        //转成流输出到页面可供下载
        dowland(fileName, file.getAbsolutePath(), response);
    }

    //JAVA获取某段时间内的所有日期
    public static List<String> findDates(String dStart, String dEnd) throws ParseException {
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = fmt.parse(dStart);
        Date endDate = fmt.parse(dEnd);
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(startDate);

        List<String> dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(dStart);
//        System.out.println("dStart="+dateList);
        // 此日期是否在指定日期之后
        while (endDate.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(fmt.format(cStart.getTime()));
//            System.out.println("dStart1="+dateList);
        }
        return dateList;
    }

    public void dowland(String fileName, String url, HttpServletResponse response) throws FileReadErrorException {
        try {
            // excel文件流输出到浏览器，选择下载路径
            File file = new File(url);
//            String fileName = file.getName();
            //模板导出
            Workbook workbook = new XSSFWorkbook(FileUtils.openInputStream(file));
            //准备将Excel的输出流通过response输出到页面下载
            //八进制输出流
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            //这后面可以设置导出Excel的名称，此例中名为student.xls
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xlsx");

            //刷新缓冲
            response.flushBuffer();
            //workbook将Excel写入到response的输出流中，供页面下载
            workbook.write(response.getOutputStream());
        } catch (Exception e) {// 发生不可预知异常，在此截获异常信息，并返回客户操作不成功
            e.printStackTrace();
            throw new FileReadErrorException("导出失败！！！");
        }
    }

    //获取图形
    public String graph(Integer leave,String inState,String outState){
        /*
            规定：正常打卡：√   221A
            迟到：→       2192
            早退：←       2190
            旷工：×       00D7
            休息日：〇     3007
            请假：△       25B3
            未签到：>      FE65
            未签退：<      FE64
            */
        int[] code = {0x221A, 0x25B3, 0x3007, 0x2192, 0x2190, 0x00D7, 0x003E, 0x003C};
        String val = null;
        if (leave==0&&inState=="0"&&outState=="0"){//正常打卡
            val=new String(code, 0, 1);
        }else if (leave==0&&inState=="1"&&outState=="0"){
            val=new String(code, 3, 1);//迟到
        } else if (leave==0&&inState=="0"&&outState=="2"){
            val=new String(code, 4, 1);//早退
        }else if (leave==0&&inState=="3"&&outState=="4"){
            val=new String(code, 5, 1);//旷工
        } else if (leave==0&&inState.equals("3")&&!outState.equals("4")){
            val=new String(code, 6, 1);//未签到
        }else if (leave==0&&!inState.equals("3")&&outState.equals("4")){
            val=new String(code, 7, 1);//未签退
        }else if (leave==1){
            val=new String(code, 1, 1);//请假
        }else if (leave==2){
            val=new String(code, 2, 1);//休息日、节假日
        }
        return val;
    }



}
