package cn.csxy.zhxyglxt.controller;


import cn.csxy.zhxyglxt.bean.Clazz;
import cn.csxy.zhxyglxt.service.ClazzService;
import cn.csxy.zhxyglxt.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;
    // sms/clazzController/getClazzsByOpr/1/3?gradeName=&name=
    @GetMapping("/getClazzsByOpr/{pageNum}/{pageSize}")
    public Result getClazzsByOpr(@PathVariable("pageNum") Integer pageNum,
                                 @PathVariable("pageSize")Integer pageSize,
                                 String gradeName,
                                 String name){
        Page<Clazz> page=new Page<>(pageNum,pageSize);
        Page<Clazz> page1=clazzService.selectPageByGradeNameAndName(page,gradeName,name);
        return Result.ok(page1);
    }
    //	/sms/clazzController/saveOrUpdateClazz
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }
    //DELETE sms/clazzController/deleteClazz  [1,2,3]
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }
    //GET /sms/clazzController/getClazzs
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzs=clazzService.getClazzs();
        return Result.ok(clazzs);
    }
}
