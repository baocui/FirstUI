package com.bc.firstui.net.response;

/**
 * @author bc on 2018/10/12.
 */
public class UserInfo {
    /**
     *
     */
    public String Token;

    /**
     *
     */
    public Long Id;

    /**
     *
     */
    public String Name;

    /**
     *
     */
    public String LoginName;

    /**
     * PcUser=0, MobileUser=1, Device=2, SubSystem=3
     */
    public Integer LoginType;
    /**
     * 客户端自己加的
     */
    public String Password;
}
