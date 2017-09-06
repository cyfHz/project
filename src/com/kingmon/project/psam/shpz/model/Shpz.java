package com.kingmon.project.psam.shpz.model;
/**
 * 审核配置
* @ClassName :Shpz     
* @Description :   
* @createTime :2015年12月4日  下午1:45:38   
* @author ：zhaohuatai   
* @version :1.0
 */
public class Shpz {
	/**
	 * 主键
	 */
    private String pzid;

    /**
     * 配置类型
     *  配置类型 1 街路巷审核,2 门楼牌号审核
     *  CHAR(1)
     */
    private String pzlx;

    /**
     * 配置字典
     * 数据字典SHPZDM
     * 	根据该市局代码  从 DZ_SHPZ找到配置<br>
		PZDM=0：不需要审核	设置spzt为 2 通过<br>
		PZDM=1：派出所审核	（警务区）设置spzt为0 正在审核    （市局/区县/派出所） 设为2 通过<br>
		PZDM=2：区县审核     	（警务区/派出所）设置spzt为0 正在审核     （市局/区县） 设为2 通过 <br>
		PZDM=3：市局审核 	（警务区/派出所/区县）设置spzt为0 正在审核   （ 市局）设为2 通过<br>
     */
    private String pzdm;

    /**
     * 组织机构代码（市）370100六位
     */
    private String organCode;

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid == null ? null : pzid.trim();
    }

    public String getPzlx() {
        return pzlx;
    }

    public void setPzlx(String pzlx) {
        this.pzlx = pzlx == null ? null : pzlx.trim();
    }

    public String getPzdm() {
        return pzdm;
    }

    public void setPzdm(String pzdm) {
        this.pzdm = pzdm == null ? null : pzdm.trim();
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode == null ? null : organCode.trim();
    }
}