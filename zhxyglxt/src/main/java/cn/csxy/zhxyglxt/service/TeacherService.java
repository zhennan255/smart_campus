package cn.csxy.zhxyglxt.service;


import cn.csxy.zhxyglxt.bean.LoginForm;
import cn.csxy.zhxyglxt.bean.Teacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    Page<Teacher> getTeachers(Page<Teacher> page, Teacher teacher);
}
