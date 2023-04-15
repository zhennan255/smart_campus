package cn.csxy.zhxyglxt.service;


import cn.csxy.zhxyglxt.bean.Admin;
import cn.csxy.zhxyglxt.bean.LoginForm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    Page<Admin> getAllAdmin(Page<Admin> page, String adminName);
}
