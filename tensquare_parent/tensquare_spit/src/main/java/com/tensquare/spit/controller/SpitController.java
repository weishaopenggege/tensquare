package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wsp
 * @date 2019/5/8 21:12
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> spitList = spitService.findAll();
        return Result.success("查询成功",spitList);
    }

    /**
     * 根据id查询数据
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findOne(@PathVariable String spitId){
        Spit spit = spitService.findById(spitId);
        return Result.success("查询成功", spit);
    }
    /**
     * 增加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit ){
        spitService.add(spit);
        return Result.success("增加成功");
    }
    /**
     * 修改
     */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public Result update(@RequestBody Spit spit, @PathVariable String id){
        spit.set_id(id);
        spitService.update(spit);
        return Result.success("修改成功");
    }
    /**
     * 根据id删除
     */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id ){
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 根据上级ID查询吐槽分页数据
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/comment/{parentId}/{page}/{size}",method=RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentId, @PathVariable int page, @PathVariable int size){
        Page<Spit> pageList = spitService.findByParentId(parentId, page, size);
        return Result.success("查询成功",new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }

    /**
     * 点赞
     * @param id
     * @return
     */
    @RequestMapping(value="/thumbup/{id}",method=RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String id){
        String userid = "1012";
        Object o = redisTemplate.opsForValue().get("thumbup_" + userid + "_" + id);
        if (o != null) {
            return new Result(false,StatusCode.REPERROR,"你已经点过赞了");
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set("thumbup_" + userid + "_" + id,"1");
        return Result.success("点赞成功");
    }
}
