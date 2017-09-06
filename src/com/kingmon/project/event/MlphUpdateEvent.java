package com.kingmon.project.event;

import org.springframework.context.ApplicationEvent;

public class MlphUpdateEvent extends ApplicationEvent {

	public MlphUpdateEvent(Object source) {
		super(source);
	}

}
