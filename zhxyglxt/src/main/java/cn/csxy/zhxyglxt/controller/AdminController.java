package cn.csxy.zhxyglxt.controller;


import cn.csxy.zhxyglxt.bean.Admin;
import cn.csxy.zhxyglxt.service.AdminService;
import cn.csxy.zhxyglxt.util.MD5;
import cn.csxy.zhxyglxt.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    private AdminService adminService;
    //	http://localhost:9002/sms/adminController/getAllAdmin/1/3?    adminName=a
    @GetMapping("/getAllAdmin/{pageNum}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("传入分页的页码数") @PathVariable("pageNum") Integer pageNum,
            @ApiParam("传入分页的页数大小") @PathVariable("pageSize") Integer pageSize,
           String adminName){
        Page<Admin> page=new Page<>(pageNum,pageSize);
        Page<Admin> page1=adminService.getAllAdmin(page,adminName);
        return Result.ok(page1);
    }
    //POST
    //	http://localhost:9002/sms/adminController/saveOrUpdateAdmin  admin
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        Integer id = admin.getId();
        if(id==null||id==0){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }
    // DELETE
    //	http://localhost:9002/sms/adminController/deleteAdmin List<Integer> ids
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
