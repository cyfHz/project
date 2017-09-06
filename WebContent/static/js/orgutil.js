function checkOrg(orgCode){
	if(!orgCode||orgCode=="null"||orgCode==null||orgCode==""||orgCode==undefined||orgCode=="undefined"){
		return null;
	}
	var index=orgCode.length()-1;
	var x=orgCode.charAt(index);
	while(index>=0&&x=='0'){
		index--;
		x=orgCode.charAt(index);
	}
	switch (index+1) {
	case 2:
		return "st";
	case 3:
	case 4:
		return "sj";
	case 5:
	case 6:
		return "fj";
	case 7:
	case 8:
	case 9:
		return "pcs";
	case 10:
		return "pcs";
	case 11:
	case 12:
		return "jwq";
	default:
		return null;
	}
}

