package com.kingmon.project.psam.acquisition.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.geoShapeQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.service.BaseService;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.psam.acquisition.service.IAcquisitionService;
import com.kingmon.project.psam.jwq.model.Jwq;
@Service
public class AcquisitionServiceImpl extends BaseService implements IAcquisitionService{
	@Autowired
	private ElasticService elasticService;
	@Value("${dev.data.process}")
	private String devDataProcess;

}
