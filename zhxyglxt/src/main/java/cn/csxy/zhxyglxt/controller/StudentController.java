package cn.csxy.zhxyglxt.controller;


import cn.csxy.zhxyglxt.bean.Student;
import cn.csxy.zhxyglxt.service.StudentService;
import cn.csxy.zhxyglxt.util.MD5;
import cn.csxy.zhxyglxt.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;
    //GET /sms/studentController/getStudentByOpr/1/3?name=&clazzName=
    @GetMapping("/getStudentByOpr/{pageNum}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("传入分页的页码数") @PathVariable("pageNum") Integer pageNum,
            @ApiParam("传入分页的页数大小") @PathVariable("pageSize") Integer pageSize,
            Student student){
        Page<Student> page=new Page<>(pageNum,pageSize);
        Page<Student> page1=studentService.getStudentByOpr(page,student);
        return Result.ok(page1);
    }
    // POST  http://localhost:9002/sms/studentController/addOrUpdateStudent
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        Integer id = student.getId();
        if(id==null||id==0) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);


        return Result.ok();
    }
   // http://localhost:9001/sms/studentController/delStudentById
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> ids){
        studentService.removeByIds(ids);
        return Result.ok();
    }
}
