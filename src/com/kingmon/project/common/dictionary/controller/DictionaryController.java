package com.kingmon.project.common.dictionary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.common.dictionary.service.IDictionaryService;

@Controller
@RequestMapping("/common/dictionary")
public class DictionaryController  extends KBaseController{
	@Autowired
	private IDictionaryService dictionaryService;
	
	
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="数据字典查询",operateType="W",refTable="APP_ORGANIZATION",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = { "/loadByTypeCode"}, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public Object loadByTypeCode(String typeCode) {
		List<?> list=dictionaryService.loadByTypeCode(typeCode);
		return FastjsonUtil.convert(list);
	}
}
