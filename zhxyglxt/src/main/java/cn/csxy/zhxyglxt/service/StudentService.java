package cn.csxy.zhxyglxt.service;


import cn.csxy.zhxyglxt.bean.LoginForm;
import cn.csxy.zhxyglxt.bean.Student;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    Page<Student> getStudentByOpr(Page<Student> page, Student student);
}
