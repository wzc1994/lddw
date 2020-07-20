package com.wutong.lddw.context;

import com.alibaba.fastjson.JSONObject;
import com.wutong.lddw.api.activity.cyc.CycApi;
import com.wutong.lddw.api.activity.cyc.pojo.CycResult;
import com.wutong.lddw.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class WebFilter implements Filter {

    @Autowired
    private CycApi cycApi;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------------------->过滤器被创建");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uid = request.getParameter("uid");
        String h5token = request.getParameter("h5token");
        String h5openid = request.getParameter("h5openid");
        ParamHandler.clear();
        ParamHandler.set(new SessionParam(uid, h5token, h5openid));
        LogHandler.clear();
        LogHandler.set(new HashMap<String, SessionLog>());
        CycResult result = cycApi.look();
        if ("110".equals(result.getResult())) {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = null;
            try {
                writer = servletResponse.getWriter();
                writer.write(JSONObject.toJSONString(ResponseUtils.fail(result.getMsg())));
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("----------------------->过滤器被销毁");
    }

}
