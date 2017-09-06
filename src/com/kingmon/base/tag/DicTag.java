package com.kingmon.base.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.kingmon.project.common.dictionary.model.Dictionary;
import com.kingmon.project.common.dictionary.service.IDictionaryService;
import com.kingmon.project.common.dictionary.service.impl.DictionaryServiceImpl;
public class DicTag extends SimpleTagSupport{
	private String id;
	private String dictCode;
	private String value;
	
	ApplicationContext ctx =null;
	IDictionaryService dicService =null;  
	private void init(){
		if(ctx==null){
			PageContext pc=(PageContext) this.getJspContext();
			ctx=WebApplicationContextUtils.getWebApplicationContext(pc.getServletContext());
			if(dicService==null){
				dicService=ctx.getBean(DictionaryServiceImpl.class);
			}
		}
	}
	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
		 init();
		 Dictionary  dic=dicService.loadDictByTypeAndCodeForTag(dictCode ,value );
		if(dic==null){
			 getJspContext().getOut().write(" ");return;
		}
		getJspContext().getOut().write(dic.getDict_mc());
		
		}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

		
		
}
