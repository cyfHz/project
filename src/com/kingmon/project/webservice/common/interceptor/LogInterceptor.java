package com.kingmon.project.webservice.common.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class LogInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
	
	public LogInterceptor() {
		super(Phase.RECEIVE);
	}
	public LogInterceptor(String phase) {
		super(phase);
	}
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		
		
		
	}

}
