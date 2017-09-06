    function OnePoint(x, y) {
    	var kmpoint = new Object();
    	kmpoint.x = x;
    	kmpoint.y = y;
    	return kmpoint;
    }
    function min(x,y){
    	if(x<y)return x;
    	return y;
    }

    function max(x,y){
    	if(x>y)return x;
    	return y;
    }
    function PtInPolygon(p, pt, nCount) {
    	var nCross = 0;
    	var p1;
    	var p2;
    	for (var i = 0; i < nCount; i++) {
    		p1 = pt[i];
    		p2 = pt[(i + 1) % nCount];
    		if (p1.y == p2.y) {
    			if (p.y == p1.y && p.x >= min(p1.x, p2.x) && p.x <= max(p1.x, p2.x))
    				return true;
    			continue;
    		}
    		if (p.y < min(p1.y, p2.y) || p.y > max(p1.y, p2.y))
    			continue;
    		var x = (p.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
    		if (x > p.x)
    			nCross++;
    		else if (x == p.x)
    			return true;
    	}
    	if (nCross % 2 == 1)
    		return true;

    	return false;
    }
    function isPermissionbyPoint(zuobiaoArray, mouseX, mouseY) {
    	for (var m = 0; m < zuobiaoArray.length; m++) {
    		if ("null" != zuobiaoArray[m] && "" != zuobiaoArray[m]) {
    			var zuobiaoList = new Array();
    			zuobiaoList = zuobiaoArray[m].split(",");
    			var ptArry = new Array();
    			var k = 0;
    			for (var i = 0; i < zuobiaoList.length; i = i + 2) {
    				var pt = OnePoint(zuobiaoList[i] * 1, zuobiaoList[i + 1] * 1);
    				ptArry[k] = pt;
    				k++;
    			}
    			var targetPoint = OnePoint(mouseX, mouseY);
    			var flag = PtInPolygon(targetPoint, ptArry, ptArry.length - 1);
    			if (flag == true) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    

/*function Circle(lon,lat,rr){
	this.lon = lon;
	this.lat = lat;
	this.rr = rr;
	return this;
}*/