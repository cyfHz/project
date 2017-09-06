package com.kingmon.project.webservice.common.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class AuthInterceptor  extends AbstractPhaseInterceptor<SoapMessage>{
	public AuthInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	public AuthInterceptor(String phase) {
		super(phase);
	}

	@Override
	public void handleMessage(SoapMessage arg0) throws Fault {

	}

}
