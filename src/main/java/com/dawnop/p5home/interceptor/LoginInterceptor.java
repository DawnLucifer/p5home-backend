package com.dawnop.p5home.interceptor;

import com.alibaba.fastjson.JSONObject;

import com.dawnop.p5home.commons.AccessToken;
import com.dawnop.p5home.commons.EncryptUtils;
import com.dawnop.p5home.commons.Result;
import com.dawnop.p5home.entity.User;
import com.dawnop.p5home.service.UserService;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    UserService userService;

    private static final String METHOD = "OPTIONS";
    private static final String TOKEN = "token"; //token参数

    private static final String[] RELEASE = {""};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1:直接放行OPTIONS请求
        String method = request.getMethod();
        if (StringUtils.equalsAnyIgnoreCase(METHOD, method)) {
            return true;
        }

        //1-1:添加放行的请求
        String uri = request.getRequestURI();
        for (String release : RELEASE) {
            if (StringUtils.equalsAnyIgnoreCase(release, uri)) {
                return true;
            }
        }


        //2：验证请求头中是否有token
        String tokenHeader = request.getHeader(TOKEN);
        if (StringUtils.isEmpty(tokenHeader)) {
            //如果token为空，则返回没有啊访问权限
            this.noAuth(response, Result.NO_TOKEN);
            return false;
        }

        try {
            //3:解析token --> {username":"","expire":12342345}
            String jsonToken = EncryptUtils.decrypt(tokenHeader);
            AccessToken accessToken = JSONObject.parseObject(jsonToken, AccessToken.class);
            long expire = accessToken.getExpire();
            if (System.currentTimeMillis() - expire >= 0) {
                this.noAuth(response, Result.TOKEN_EXPIRE);
                return false;
            }

            //4:如果token没有过期，则验证用户名
            User user = userService.queryUsername(accessToken.getUsername());
            if (ObjectUtils.isEmpty(user)) {
                this.noAuth(response, Result.NO_AUTH_MSG);
                return false;
            }

            //5:所有验证都通过
            accessToken = new AccessToken(accessToken.getUsername());
            String newJsonToken = JSONObject.toJSONString(accessToken);
            response.setHeader(TOKEN, EncryptUtils.encrypt(newJsonToken));

        } catch (Exception e) {
            this.noAuth(response, "服务器异常");
            return false;
        }
        return true;
    }

    //返回无权限访问
    public void noAuth(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        Result result = Result.noAuth(msg);
        String json = JSONObject.toJSONString(result);
        writer.print(json);
        writer.flush();
        writer.close();

    }


//    public static void main(String[] args) {
//        AccessToken token = new AccessToken("et");
//        String enctypt = EncryptUtil.encrypt(JSONObject.toJSONString(token));
//        System.out.println(enctypt);
//    }


}
