package cn.csxy.zhxyglxt.service;


import cn.csxy.zhxyglxt.bean.Grade;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GradeService extends IService<Grade> {
    Page<Grade> selectPageByName(Page<Grade> page, String gradeName);

    List<Grade> getGrades();
}
