package com.kingmon.project.common.tasks;

import org.springframework.stereotype.Component;

@Component(value="searchDataSycTask")
public class SearchDataSycTask {
	 public SearchDataSycTask() {
		  //System.out.println("构造函数");  
	}

    public void sycSearchData(){  
          System.out.println("进入测试......");  
    }  
}
