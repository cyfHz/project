package com.kingmon.project.psam.appMapUrl.model;

import java.util.Date;

public class mapurl {
	/**
	 * 主键
	 */
    private String mapUrlId;
     /**
      * 地图类别
      */
    private String mapType;
    /**
     * 内外网
     */

    private String intoweb;
  /**
   * 地域区分
   */
    private String areaCode;
    /**
     * 地址url
     */
    private String mapurl;
    /**
     * 登记人
     */
    private String djr;
    /**
     * 登记单位
     */
    private String djdw;
    /**
     * 登记时间
     */

    private Date djsj;
     /**
      *登记人名称 
      */
    private String djrmc;
    /**
     * 登记单位名称
     */
    private String djdwmc;
    /**
     * 修改人名称
     */
    private String xgrmc;
    /**
     * 修改单位名称
     */
    private String xgdwmc;
     /**
      * 修改人
      */
    private String xgr;
   /**
    * 修改单位
    */
    private String xgdw;
     /**
      * 更新时间
      */
    private Date gxsj;
     /**
      * movesign
      */
    private String movesign;
     /**
      * 设备ID
      */
    private String sbid;
    /**
     * 录入角色(民警0,社会积极力量1)
     */
    private String lrjs;
    /**
     * 录入方式(后台系统0,移动终端1)
     */
    private String lrfs;
    /**
     * 录入网络(互联网E,公安网A)
     */
    private String lrwl;

    public String getMapUrlId() {
        return mapUrlId;
    }

    public void setMapUrlId(String mapUrlId) {
        this.mapUrlId = mapUrlId == null ? null : mapUrlId.trim();
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType == null ? null : mapType.trim();
    }

    public String getIntoweb() {
        return intoweb;
    }

    public void setIntoweb(String intoweb) {
        this.intoweb = intoweb == null ? null : intoweb.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getMapurl() {
        return mapurl;
    }

    public void setMapurl(String mapurl) {
        this.mapurl = mapurl == null ? null : mapurl.trim();
    }

    public String getDjr() {
        return djr;
    }

    public void setDjr(String djr) {
        this.djr = djr == null ? null : djr.trim();
    }

    public String getDjdw() {
        return djdw;
    }

    public void setDjdw(String djdw) {
        this.djdw = djdw == null ? null : djdw.trim();
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDjrmc() {
        return djrmc;
    }

    public void setDjrmc(String djrmc) {
        this.djrmc = djrmc == null ? null : djrmc.trim();
    }

    public String getDjdwmc() {
        return djdwmc;
    }

    public void setDjdwmc(String djdwmc) {
        this.djdwmc = djdwmc == null ? null : djdwmc.trim();
    }

    public String getXgrmc() {
        return xgrmc;
    }

    public void setXgrmc(String xgrmc) {
        this.xgrmc = xgrmc == null ? null : xgrmc.trim();
    }

    public String getXgdwmc() {
        return xgdwmc;
    }

    public void setXgdwmc(String xgdwmc) {
        this.xgdwmc = xgdwmc == null ? null : xgdwmc.trim();
    }

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr == null ? null : xgr.trim();
    }

    public String getXgdw() {
        return xgdw;
    }

    public void setXgdw(String xgdw) {
        this.xgdw = xgdw == null ? null : xgdw.trim();
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getMovesign() {
        return movesign;
    }

    public void setMovesign(String movesign) {
        this.movesign = movesign == null ? null : movesign.trim();
    }

    public String getSbid() {
        return sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid == null ? null : sbid.trim();
    }

    public String getLrjs() {
        return lrjs;
    }

    public void setLrjs(String lrjs) {
        this.lrjs = lrjs == null ? null : lrjs.trim();
    }

    public String getLrfs() {
        return lrfs;
    }

    public void setLrfs(String lrfs) {
        this.lrfs = lrfs == null ? null : lrfs.trim();
    }

    public String getLrwl() {
        return lrwl;
    }

    public void setLrwl(String lrwl) {
        this.lrwl = lrwl == null ? null : lrwl.trim();
    }
}