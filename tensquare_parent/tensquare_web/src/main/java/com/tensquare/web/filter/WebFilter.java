package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wsp
 * @date 2019/5/14 20:47
 */
public class WebFilter extends ZuulFilter {

    //filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
    //- pre：	可以在请求被路由之前调用
    //- route：在路由请求时候被调用
    //- post：在route和error过滤器之后被调用
    //- error：处理请求时发生错误时被调用
    @Override
    public String filterType() {
        return "pre";  // 前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;    // 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("zuul过滤器");
        //向header中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取header
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if(authorization!=null){
            requestContext.addZuulRequestHeader("Authorization",authorization);
        }
        return null;
    }
}
