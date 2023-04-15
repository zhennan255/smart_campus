package cn.csxy.zhxyglxt.controller;


import cn.csxy.zhxyglxt.bean.Grade;
import cn.csxy.zhxyglxt.service.GradeService;
import cn.csxy.zhxyglxt.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;
    //查找和模糊查询
    //sms/gradeController/getGrades/1/3?gradeName=%E4%B8%89
    @ApiOperation("获取年级信息，根据年级姓名模糊查询")
    @GetMapping("/getGrades/{pageNum}/{pageSize}")
    public Result getGrades(
            @ApiParam("传入分页的页码数") @PathVariable("pageNum") Integer pageNum,
            @ApiParam("传入分页的页数大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("传入模糊查询的年级姓名")String gradeName){
        Page<Grade> page=new Page<>(pageNum,pageSize);
        Page<Grade> pageService=gradeService.selectPageByName(page,gradeName);
        return Result.ok(pageService);
    }

    //sms/gradeController/deleteGraded
    @ApiOperation("删除一条或多条数据")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("以json格式传入一个id数组ids")@RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("更改年级和保存年级信息，当id为空时，为保存信息，id有值时，为更改信息")
    //sms/gradeController//saveOrUpdateGrade
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("以json格式传入一个grade对象")@RequestBody Grade grade){
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }
    // /sms/gradeController/getGrades
   @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades=gradeService.getGrades();
        return Result.ok(grades);
   }
}
