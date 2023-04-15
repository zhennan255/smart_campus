package cn.csxy.zhxyglxt.controller;


import cn.csxy.zhxyglxt.bean.Admin;
import cn.csxy.zhxyglxt.bean.LoginForm;
import cn.csxy.zhxyglxt.bean.Student;
import cn.csxy.zhxyglxt.bean.Teacher;
import cn.csxy.zhxyglxt.service.AdminService;
import cn.csxy.zhxyglxt.service.StudentService;
import cn.csxy.zhxyglxt.service.TeacherService;
import cn.csxy.zhxyglxt.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    // POST /sms/system/headerImgUpload
    //文件上传
    @PostMapping("/headerImgUpload")
    public Result  headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile){
        //更改文件的名字 防止文件名冲突
        String s = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(i);
        String newFileName=s+substring;
        //保存文件
        String partFilePath="F:/xhyxcode/smart_campus/zhxyglxt/target/classes/public/upload/"+newFileName;
        try {
            multipartFile.transferTo(new File(partFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把保存文件的路径响应给浏览器
        String path="upload/"+newFileName;
        return Result.ok(path);
    }
    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token){
        Map<String,Object> map=new HashMap<>();
        //判断token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        if(expiration){
            //token过期
            Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        switch (userType){
            case 1:
               Admin admin= adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student= studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher= teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }
    @GetMapping("/getVerifiCodeImage")
    //获取验证码
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取验证码
        String verifiCode=new String(CreateVerifiCodeImage.getVerifiCode());
        //把验证码保存到session域中
        request.getSession().setAttribute("verifiCode",verifiCode);
        //把图片响应到浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  @PostMapping("login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){
      String loginVerifiCode = loginForm.getVerifiCode();
      HttpSession session = request.getSession();
      String sessionVerifiCode = (String)session.getAttribute("verifiCode");
      //验证码过期
      if("".equals(sessionVerifiCode)||sessionVerifiCode==null){
          //给浏览器提示
          return Result.fail().message("验证码失效，请刷新重试");
      }
      //验证码不正确
      if(!loginVerifiCode.equalsIgnoreCase(sessionVerifiCode)){
          //给浏览器提示
          return Result.fail().message("验证码错误,请重新输入");
      }
      //验证码验证完 删除session域中的验证码
      session.removeAttribute("verifiCode");
      //验证用户的类型和账户，密码
      Map<String, Object> map=new HashMap<>();
      //判断用户的类型是哪个
     switch (loginForm.getUserType()){
         case 1:
           Admin admin=adminService.login(loginForm);
             try {
                 if(admin==null){
                     //根据用户名和密码在数据库查询不到数据
                     throw  new RuntimeException("用户名或密码不正确，请重新输入");
                 }else{
                     //根据用户名和密码在数据库查询到数据，生成一个token,把token保存到map中，响应到浏览器
                     map.put("token",JwtHelper.createToken(admin.getId().longValue(),1));
                     return Result.ok(map);
                 }
             } catch (RuntimeException e) {
                 e.printStackTrace();
                 return Result.fail().message(e.getMessage());
             }
         case 2:
             Student student=studentService.login(loginForm);
             try {
                 if(student==null){
                     throw  new RuntimeException("用户名或密码不正确，请重新输入");
                 }else{
                     map.put("token",JwtHelper.createToken(student.getId().longValue(),2));
                     return Result.ok(map);
                 }
             } catch (RuntimeException e) {
                 e.printStackTrace();
                 return Result.fail().message(e.getMessage());
             }
         case 3:
             Teacher teacher=teacherService.login(loginForm);
             try {
                 if(teacher==null){
                     throw  new RuntimeException("用户名或密码不正确，请重新输入");
                 }else{
                     map.put("token",JwtHelper.createToken(teacher.getId().longValue(),3));
                     return Result.ok(map);
                 }
             } catch (RuntimeException e) {
                 e.printStackTrace();
                 return Result.fail().message(e.getMessage());
             }
     }
     return Result.fail().message("查无此用户");

  }

    /*

    * 修改密码的处理器
    * POST  /sms/system/updatePwd/123456/admin
    *       /sms/system/updatePwd/{oldPwd}/{newPwd}
    *       请求参数
                oldpwd
                newPwd
                token 头
            响应的数据
                Result OK data= null

    * */
    @PostMapping("updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @PathVariable("oldPwd") String oldPwd,
            @PathVariable("newPwd") String newPwd,
            @RequestHeader("token") String token
    ){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.fail().message("token已经过期");
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        String encrypt = MD5.encrypt(oldPwd);
        String encrypt1 = MD5.encrypt(newPwd);
        switch (userType){
            case 1:
                QueryWrapper<Admin> queryWrapper=new QueryWrapper();
                queryWrapper.eq("id",userId)
                            .eq("password",encrypt);
                Admin admin = adminService.getOne(queryWrapper);
                if(admin==null){
                    return Result.fail().message("原密码不正确");
                }
                admin.setPassword(encrypt1);
                adminService.saveOrUpdate(admin);
                break;
            case 2:
                QueryWrapper<Student> queryWrapper1=new QueryWrapper();
                queryWrapper1.eq("id",userId)
                        .eq("password",encrypt);
               Student student= studentService.getOne(queryWrapper1);
                if(student==null){
                    return Result.fail().message("原密码不正确");
                }
                student.setPassword(encrypt1);
                studentService.saveOrUpdate(student);
                break;
            case 3:
                QueryWrapper<Teacher> queryWrapper2=new QueryWrapper();
                queryWrapper2.eq("id",userId)
                        .eq("password",encrypt);
               Teacher teacher= teacherService.getOne(queryWrapper2);
                if(teacher==null){
                    return Result.fail().message("原密码不正确");
                }
                teacher.setPassword(encrypt1);
                teacherService.saveOrUpdate(teacher);

                break;
        }
        return Result.ok();
    }
}
