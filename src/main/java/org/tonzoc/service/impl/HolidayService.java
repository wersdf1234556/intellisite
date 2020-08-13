package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.HolidayModel;
import org.tonzoc.service.IHolidayService;

@Service(value ="holidayService" )
public class HolidayService extends BaseService<HolidayModel> implements IHolidayService {
}
