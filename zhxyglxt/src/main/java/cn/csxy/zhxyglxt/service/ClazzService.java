package cn.csxy.zhxyglxt.service;


import cn.csxy.zhxyglxt.bean.Clazz;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ClazzService extends IService<Clazz> {
    Page<Clazz> selectPageByGradeNameAndName(Page<Clazz> page, String gradeName, String name);

    List<Clazz> getClazzs();
}
