package com.kingmon.project.psam.jzw.view;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccqUserInfo  implements Serializable{
    /**
     * Mac地址
     * MAC_ADDRESS varchar2(20);
     */
    private String macAddress;
    
    /**
     * 
     * TF卡号 varchar2(36);
     */
    private String tfCardNum;
    
    /**
     * IMEI号 varchar2(20);
     */
    private String imeiNum;
    
    /**
     * SIM卡号-varchar2(20)
     */
    private String simNum;
    
    /**
     * NUMBER(11,8)
     */
    private BigDecimal gpsX;
    
    /**
     * NUMBER(11,8)
     */
    private BigDecimal gpsY;
}
