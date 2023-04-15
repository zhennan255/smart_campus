package cn.csxy.zhxyglxt.service.impl;


import cn.csxy.zhxyglxt.bean.LoginForm;
import cn.csxy.zhxyglxt.bean.Teacher;
import cn.csxy.zhxyglxt.mapper.TeacherMapper;
import cn.csxy.zhxyglxt.service.TeacherService;
import cn.csxy.zhxyglxt.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        return teacherMapper.selectById(userId);
    }

    @Override
    public Page<Teacher> getTeachers(Page<Teacher> page, Teacher teacher) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if(!StringUtils.isEmpty(teacher.getClazzName())){
            queryWrapper.like("clazz_name",teacher.getClazzName());
        }
        if(!StringUtils.isEmpty(teacher.getName())){
            queryWrapper.like("name",teacher.getName());
        }
        return teacherMapper.selectPage(page,queryWrapper);
    }
}
