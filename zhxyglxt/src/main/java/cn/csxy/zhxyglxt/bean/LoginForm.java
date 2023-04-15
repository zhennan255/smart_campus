package cn.csxy.zhxyglxt.bean;

import lombok.Data;

@Data
public class LoginForm {
    //用来接收登陆页面的参数
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
