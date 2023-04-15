package cn.csxy.zhxyglxt.controller;


import cn.csxy.zhxyglxt.bean.Teacher;
import cn.csxy.zhxyglxt.service.TeacherService;
import cn.csxy.zhxyglxt.util.MD5;
import cn.csxy.zhxyglxt.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
   // sms/teacherController/getTeachers/1/3?name=小&clazzName=一年一班
    @GetMapping("/getTeachers/{pageNum}/{pageSize}")
    public Result getTeachers(
            @ApiParam("传入分页的页码数") @PathVariable("pageNum") Integer pageNum,
            @ApiParam("传入分页的页数大小") @PathVariable("pageSize") Integer pageSize,
            Teacher teacher){
        Page<Teacher> page=new Page<>(pageNum,pageSize);
        Page<Teacher> page1=teacherService.getTeachers(page,teacher);
        return Result.ok(page1);

    }
   //post sms/teacherController/saveOrUpdateTeacher
    @PostMapping("saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        Integer id = teacher.getId();
        if(id==null||id==0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }


  // DELETE sms/teacherController/deleteTeacher
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}
