package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Wsp
 * @date 2019/5/13 15:31
 */
//@FeignClient注解用于指定从哪个服务中调用功能  ，
// 注意  里面的名称与被调用的服务名保持一致，并且不能包含下划线。
@FeignClient("tensquare-base")
public interface LabelClient {

    //@RequestMapping注解用于对被调用的微服务进行地址映射。
    // 注意   @PathVariable注解一定要指定参数名称，否则出错
    @RequestMapping(value = "/label/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "labelId") String labelId);
}
