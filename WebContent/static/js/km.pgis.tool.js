
var KMPGISTOOL={
		genOneBorderline:function (data) {
			 var  pointsArray=new Array();
		   	 var arrx =new Array();//纵坐标
		   	 arrx=data.split(",");
		   	 for (var i = 0; i < arrx.length-1; i=i+2) {
		   		pointsArray.push(new Point(arrx[i],arrx[i+1]));
		   	 }
		      return pointsArray; 
	}
}
