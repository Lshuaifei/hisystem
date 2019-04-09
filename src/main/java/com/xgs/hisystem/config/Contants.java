package com.xgs.hisystem.config;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/26
 */
public final class Contants {

    /**
     * 防止实例化
     **/
    private Contants() {
    }

    public class url {
        public static final String BAIDU_URL = "http://api.map.baidu.com/location/ip?ak=AsOobWFmNIE65pv9BGGQROiHwDFDHkLT&coor=bd09ll&ip=";
    }

    public class user {

        public static final String USER_OR_PASSWORD_ERROR = "密码错误或用户不存在";

        public static final String EMAIL_STATUS_0 = "账户未激活";

        public static final String ROLE_STATUS_0 = "管理员未审核角色,请等待或联系管理员";

        public static final String ROLE_STATUS_0_BAD = "角色未通过审核";

        public static final String SUCCESS = "SUCCESS";

        public static final String ACCOUNT_EXIST = "账户已存在";

        public static final String PLAIN_PASSWORD_ERROR = "旧密码错误";

        public static final String CHANGE_OK = "修改成功";

        public static final String FAIL = "FAIL";

        public static final String OLD_NO_NEW = "密码确认输入不一致！";
    }

}
