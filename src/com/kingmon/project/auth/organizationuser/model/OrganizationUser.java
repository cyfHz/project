package com.kingmon.project.auth.organizationuser.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 应用组织人员表
 * app_organization_user
 * @author zhaohuatai
 * @date 2015年10月6日 上午10:15:20
 */
public class OrganizationUser implements Serializable{
	
	/**
	 * 主键<br>
	 * APPUSER_ID -VARCHAR2(36)
	 */
    private String appuser_id;
	
	/**
	 * 应用领域ID -VARCHAR2(36)
	 */
    private String area_id;
	
	/**
	 * Organization orgna_id<br>
	 * 应用组织机构ID -VARCHAR2(36)
	 */
    private String orgna_id;
	
	/**
	 * 未用
	 * 用户ID -VARCHAR2(36)
	 */
    private String user_id;
	
	/**
	 * 用户登录账号 -VARCHAR2(40)
	 */
    private String user_loginname;
	
	/**
	 * 用户姓名-VARCHAR2(100)
	 */
    private String user_name;
	
	/**
	 * #?
	 * 性别-VARCHAR2(2)
	 */
    private String user_sex;
	
	/**
	 * 明文
	 * 登录密码-VARCHAR2(32)
	 */
    private String user_password;
	
	/**
	 * #?
	 * 管理员标识：1（管理员）-VARCHAR2(32)
	 */
    private String user_desc;
	
	/**
	 * 用户有效时间-VARCHAR2(20)
	 */
    private String user_validity_start;
	
	/**
	 * 用户失效时间-VARCHAR2(20)
	 */
    private String user_validity_end;
	
	/**
	 * 用户IP-VARCHAR2(24)
	 */
    private String user_ip;
	
	/**
	 * 未用
	 * 1是0否-CHAR(1)
	 */
    private String user_xsqsy;
	
	/**未用
	 * 是否锁定-CHAR(1)
	 */
    private String user_sfsd;
	
	/**累加
	 * 登录次数-NUMBER(8)
	 */
    private Integer user_dlcs;
	
	/**
	 * 未用
	 * 外部系统对应帐号-VARCHAR2(40)
	 */
    private String user_outbh;
	
	/**
	 * 有效标识：0为无效；1为有效；-CHAR(1)
	 */
    private String enabled;
	
	/**
	 * 操作时间-VARCHAR2(20)
	 */
    private String opratetime;
	
	/**
	 * 备注0  -VARCHAR2(40)
	 */
    private String bz0;
	
	/**
	 * 备注1(管理员标示 1 是  0否) -VARCHAR2(40)
	 */
    private String bz1;
	
	/**
	 *  备注2-VARCHAR2(40)
	 */
    private String bz2;
	
	/**
	 *  备注3(是否通过审核)-VARCHAR2(40)
	 */
    private String bz3;
	
	/**
	 *  备注4(用户性质)-VARCHAR2(40)
	 */
    private String bz4;
	
	/**
	 * 备注5(所属单位id) -VARCHAR2(40)
	 */
    private String bz5;
	
	/**
	 *  备注6(所属单位名称)-VARCHAR2(40)
	 */
    private String bz6;
	
	/**
	 *备注7  -VARCHAR2(40)
	 */
    private String bz7;
	
	/**
	 * 身份证号-VARCHAR2(18)
	 */
    private String user_sfzh;
	
	/**
	 * #? 不用管
	 * 所属分局-VARCHAR2(50)
	 */
    private String ssfj;
	
	/**#?不用管
	 * 所属派出所-VARCHAR2(50)
	 */
    private String sspcs;
	
	/**#?不用管
	 * 所属警务区-VARCHAR2(50)
	 */
    private String ssjwq;
	
	/**#?不用管
	 * 所属级别-VARCHAR2(50)
	 */
    private String ssjb;
	
	/**
	 * 同步标记(0未同步,1已同步)-CHAR(1)
	 */
    private String movesign;
	
	/**
	 * 手机号-VARCHAR2(50)
	 */
    private String user_mobile;
	
	/**
	 * 邮箱-VARCHAR2(50)
	 */
    private String user_email;
	
	/**
	 * ukey的id-VARCHAR2(50)
	 */
    private String ukeyid;
	
	/**
	 * 添加日期-DATE
	 */
    private Date createdate;
	
	/**
	 * 民警照片--BLOB
	 */
    private byte[] zp;

	/**
	 * @return the appuser_id
	 */
	public String getAppuser_id() {
		return appuser_id;
	}

	/**
	 * @param appuser_id the appuser_id to set
	 */
	public void setAppuser_id(String appuser_id) {
		this.appuser_id = appuser_id;
	}

	/**
	 * @return the area_id
	 */
	public String getArea_id() {
		return area_id;
	}

	/**
	 * @param area_id the area_id to set
	 */
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	/**
	 * @return the orgna_id
	 */
	public String getOrgna_id() {
		return orgna_id;
	}

	/**
	 * @param orgna_id the orgna_id to set
	 */
	public void setOrgna_id(String orgna_id) {
		this.orgna_id = orgna_id;
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_loginname
	 */
	public String getUser_loginname() {
		return user_loginname;
	}

	/**
	 * @param user_loginname the user_loginname to set
	 */
	public void setUser_loginname(String user_loginname) {
		this.user_loginname = user_loginname;
	}

	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return the user_sex
	 */
	public String getUser_sex() {
		return user_sex;
	}

	/**
	 * @param user_sex the user_sex to set
	 */
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	/**
	 * @return the user_password
	 */
	public String getUser_password() {
		return user_password;
	}

	/**
	 * @param user_password the user_password to set
	 */
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	/**
	 * @return the user_desc
	 */
	public String getUser_desc() {
		return user_desc;
	}

	/**
	 * @param user_desc the user_desc to set
	 */
	public void setUser_desc(String user_desc) {
		this.user_desc = user_desc;
	}

	/**
	 * @return the user_validity_start
	 */
	public String getUser_validity_start() {
		return user_validity_start;
	}

	/**
	 * @param user_validity_start the user_validity_start to set
	 */
	public void setUser_validity_start(String user_validity_start) {
		this.user_validity_start = user_validity_start;
	}

	/**
	 * @return the user_validity_end
	 */
	public String getUser_validity_end() {
		return user_validity_end;
	}

	/**
	 * @param user_validity_end the user_validity_end to set
	 */
	public void setUser_validity_end(String user_validity_end) {
		this.user_validity_end = user_validity_end;
	}

	/**
	 * @return the user_ip
	 */
	public String getUser_ip() {
		return user_ip;
	}

	/**
	 * @param user_ip the user_ip to set
	 */
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	/**
	 * @return the user_xsqsy
	 */
	public String getUser_xsqsy() {
		return user_xsqsy;
	}

	/**
	 * @param user_xsqsy the user_xsqsy to set
	 */
	public void setUser_xsqsy(String user_xsqsy) {
		this.user_xsqsy = user_xsqsy;
	}

	/**
	 * @return the user_sfsd
	 */
	public String getUser_sfsd() {
		return user_sfsd;
	}

	/**
	 * @param user_sfsd the user_sfsd to set
	 */
	public void setUser_sfsd(String user_sfsd) {
		this.user_sfsd = user_sfsd;
	}

	/**
	 * @return the user_dlcs
	 */
	public Integer getUser_dlcs() {
		return user_dlcs;
	}

	/**
	 * @param user_dlcs the user_dlcs to set
	 */
	public void setUser_dlcs(Integer user_dlcs) {
		this.user_dlcs = user_dlcs;
	}

	/**
	 * @return the user_outbh
	 */
	public String getUser_outbh() {
		return user_outbh;
	}

	/**
	 * @param user_outbh the user_outbh to set
	 */
	public void setUser_outbh(String user_outbh) {
		this.user_outbh = user_outbh;
	}

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the opratetime
	 */
	public String getOpratetime() {
		return opratetime;
	}

	/**
	 * @param opratetime the opratetime to set
	 */
	public void setOpratetime(String opratetime) {
		this.opratetime = opratetime;
	}

	/**
	 * @return the bz0
	 */
	public String getBz0() {
		return bz0;
	}

	/**
	 * @param bz0 the bz0 to set
	 */
	public void setBz0(String bz0) {
		this.bz0 = bz0;
	}

	/**
	 * @return the bz1
	 */
	public String getBz1() {
		return bz1;
	}

	/**
	 * @param bz1 the bz1 to set
	 */
	public void setBz1(String bz1) {
		this.bz1 = bz1;
	}

	/**
	 * @return the bz2
	 */
	public String getBz2() {
		return bz2;
	}

	/**
	 * @param bz2 the bz2 to set
	 */
	public void setBz2(String bz2) {
		this.bz2 = bz2;
	}

	/**
	 * @return the bz3
	 */
	public String getBz3() {
		return bz3;
	}

	/**
	 * @param bz3 the bz3 to set
	 */
	public void setBz3(String bz3) {
		this.bz3 = bz3;
	}

	/**
	 * @return the bz4
	 */
	public String getBz4() {
		return bz4;
	}

	/**
	 * @param bz4 the bz4 to set
	 */
	public void setBz4(String bz4) {
		this.bz4 = bz4;
	}

	/**
	 * @return the bz5
	 */
	public String getBz5() {
		return bz5;
	}

	/**
	 * @param bz5 the bz5 to set
	 */
	public void setBz5(String bz5) {
		this.bz5 = bz5;
	}

	/**
	 * @return the bz6
	 */
	public String getBz6() {
		return bz6;
	}

	/**
	 * @param bz6 the bz6 to set
	 */
	public void setBz6(String bz6) {
		this.bz6 = bz6;
	}

	/**
	 * @return the bz7
	 */
	public String getBz7() {
		return bz7;
	}

	/**
	 * @param bz7 the bz7 to set
	 */
	public void setBz7(String bz7) {
		this.bz7 = bz7;
	}

	/**
	 * @return the user_sfzh
	 */
	public String getUser_sfzh() {
		return user_sfzh;
	}

	/**
	 * @param user_sfzh the user_sfzh to set
	 */
	public void setUser_sfzh(String user_sfzh) {
		this.user_sfzh = user_sfzh;
	}

	/**
	 * @return the ssfj
	 */
	public String getSsfj() {
		return ssfj;
	}

	/**
	 * @param ssfj the ssfj to set
	 */
	public void setSsfj(String ssfj) {
		this.ssfj = ssfj;
	}

	/**
	 * @return the sspcs
	 */
	public String getSspcs() {
		return sspcs;
	}

	/**
	 * @param sspcs the sspcs to set
	 */
	public void setSspcs(String sspcs) {
		this.sspcs = sspcs;
	}

	/**
	 * @return the ssjwq
	 */
	public String getSsjwq() {
		return ssjwq;
	}

	/**
	 * @param ssjwq the ssjwq to set
	 */
	public void setSsjwq(String ssjwq) {
		this.ssjwq = ssjwq;
	}

	/**
	 * @return the ssjb
	 */
	public String getSsjb() {
		return ssjb;
	}

	/**
	 * @param ssjb the ssjb to set
	 */
	public void setSsjb(String ssjb) {
		this.ssjb = ssjb;
	}

	/**
	 * @return the movesign
	 */
	public String getMovesign() {
		return movesign;
	}

	/**
	 * @param movesign the movesign to set
	 */
	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	/**
	 * @return the user_mobile
	 */
	public String getUser_mobile() {
		return user_mobile;
	}

	/**
	 * @param user_mobile the user_mobile to set
	 */
	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	/**
	 * @return the user_email
	 */
	public String getUser_email() {
		return user_email;
	}

	/**
	 * @param user_email the user_email to set
	 */
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	/**
	 * @return the ukeyid
	 */
	public String getUkeyid() {
		return ukeyid;
	}

	/**
	 * @param ukeyid the ukeyid to set
	 */
	public void setUkeyid(String ukeyid) {
		this.ukeyid = ukeyid;
	}

	/**
	 * @return the createdate
	 */
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * @param createdate the createdate to set
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return the zp
	 */
	public byte[] getZp() {
		return zp;
	}

	/**
	 * @param zp the zp to set
	 */
	public void setZp(byte[] zp) {
		this.zp = zp;
	}

}