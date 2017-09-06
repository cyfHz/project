	function isInperminssion(lon,lat){
    	var pointX=lon;
    	var pointY=lat;
    	if(allPolygons==null){
    		return false;	
    	}
    	for(var k=0;k<allPolygons.length;k++){
    		if(""!=allPolygons[k]&&"null"!=allPolygons[k]&&"undefined"!=allPolygons[k]&&undefined!=allPolygons[k]){
	    		var flag=isPermissionbyPoint(allPolygons, pointX, pointY);
	        	if(!flag){
	        		 return false;	
	        	}
    		}
    		
    	}
    	return true;
 }
