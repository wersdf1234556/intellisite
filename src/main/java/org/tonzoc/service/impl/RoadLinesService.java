package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.RoadLinesMapper;
import org.tonzoc.model.RoadLinesModel;
import org.tonzoc.service.IRoadLinesService;

import java.util.List;

@Service("roadLinesService")
public class RoadLinesService extends BaseService<RoadLinesModel> implements IRoadLinesService {

}
