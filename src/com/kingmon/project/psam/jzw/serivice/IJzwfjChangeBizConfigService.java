package com.kingmon.project.psam.jzw.serivice;

import java.util.List;


public interface IJzwfjChangeBizConfigService {

	public void processJzwfjHeBing(List<String> fromFjIds,String toFjId);
	
	public void processJzwfjChaiFen(String fromFjId,List<String> toFjIds,String targetFjId);
}
