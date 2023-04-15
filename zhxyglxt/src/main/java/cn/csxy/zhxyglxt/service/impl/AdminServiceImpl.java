package cn.csxy.zhxyglxt.service.impl;


import cn.csxy.zhxyglxt.bean.Admin;
import cn.csxy.zhxyglxt.bean.LoginForm;
import cn.csxy.zhxyglxt.mapper.AdminMapper;
import cn.csxy.zhxyglxt.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        Admin admin = adminMapper.selectById(userId);
        return admin;
    }

    @Override
    public Page<Admin> getAllAdmin(Page<Admin> page, String adminName) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if(!StringUtils.isEmpty(adminName)){
            queryWrapper.like("name",adminName);
        }
        return adminMapper.selectPage(page,queryWrapper);
    }
}
