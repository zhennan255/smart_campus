package cn.csxy.zhxyglxt.service.impl;


import cn.csxy.zhxyglxt.bean.LoginForm;
import cn.csxy.zhxyglxt.bean.Student;
import cn.csxy.zhxyglxt.mapper.StudentMapper;
import cn.csxy.zhxyglxt.service.StudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = studentMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        return studentMapper.selectById(userId);
    }

    @Override
    public Page<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if(!StringUtils.isEmpty(student.getClazzName())){
            queryWrapper.like("clazz_name",student.getClazzName());
        }
        if(!StringUtils.isEmpty(student.getName())){
            queryWrapper.like("name",student.getName());
        }
        return studentMapper.selectPage(page,queryWrapper);
    }
}
