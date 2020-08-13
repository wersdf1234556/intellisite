package org.tonzoc.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tonzoc.service.*;

import java.io.IOException;
import java.text.ParseException;

// 定时器
@Component
public class ScheduledTasks {

    // 机械进退场
    @Autowired
    private IMechanicsEntryAndExitService iMechanicsEntryAndExitService;

    // 加油记录
    @Autowired
    private IMechanicsPlusOilService iMechanicsPlusOilService;

    // 机械实时状态
    @Autowired
    private IMechanicsRealtimeService iMechanicsRealtimeService;

    // 机械的基本信息
    @Autowired
    private IMechanicsService iMechanicsService;

    // 油耗
    @Autowired
    private IMOConsumptionService imoConsumptionService;

    // @Scheduled(cron = "0 0 2 * * ?")
    public void dayFunction() throws IOException, ParseException {

        iMechanicsEntryAndExitService.entryAndExitAdd("", "");
        iMechanicsPlusOilService.plusOilAdd("", "", "", "");
        iMechanicsService.mechanicsInformationAdd("", 0, 0);
        imoConsumptionService.oilConsumptionAdd("");
    }

    // @Scheduled(cron = "0 0 * * * ?")
    public void hourFunction() throws IOException, ParseException {

        imoConsumptionService.oilConsumptionAddHour("");
        iMechanicsRealtimeService.MechanicStateadd();
    }
}
