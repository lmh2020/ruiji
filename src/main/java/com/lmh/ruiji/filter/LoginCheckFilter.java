package com.lmh.ruiji.filter;

import com.alibaba.fastjson.JSON;
import com.lmh.ruiji.common.BaseContext;
import com.lmh.ruiji.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 检查用户是否拦截成功
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        //向下转型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请的url
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}", requestURI);
        //定义不需要处理的内容有静态资源、登录/退出请求页面
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        //判断请求是否需要处理
        boolean check = check(urls, requestURI);
        //不需要处理直接放行
        if (check) {
            filterChain.doFilter(request, response);
            return;
        }
        //判断登录状态，如果已登录直接放行
        if (request.getSession().getAttribute("employee") != null) {
            Long id = (Long)request.getSession().getAttribute("employee");
            //使用 BaseContext工具类，给ThreadLocal设置id


            BaseContext.setCurrentId(id);
            filterChain.doFilter(request, response);
            return;
        }

        //如果没有登录，通过输出流的方式向客户端响应
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 进行路径匹配，检查本次请求是否需要请求
     *
     * @param requestUri
     * @return
     */
    public boolean check(String[] urls, String requestUri) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUri);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
