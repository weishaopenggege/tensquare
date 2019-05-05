package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wsp
 * @date 2019/5/5 20:29
 */

@RestController
@RequestMapping("/base")
@CrossOrigin // 解决跨域问题
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * 增加
     * @param label
     * @return
     */
    @RequestMapping(value = "/label",method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.save(label);
        return Result.success("添加成功");
    }
    /**
     * 删除
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/label/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return Result.success("删除成功");
    }

    /**
     * 修改
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(value = "/label/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId, @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return Result.success("修改成功");
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(value = "/label",method = RequestMethod.GET)
    public Result findAll(){
        List<Label> labelList=labelService.findAll();
        return Result.success("查询成功",labelList);
    }

    /**
     * 通过编号查询
     */
    @RequestMapping(value = "/label/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId){
        Label label = labelService.findById(labelId);
        return Result.success("查询成功", label);
    }
}
