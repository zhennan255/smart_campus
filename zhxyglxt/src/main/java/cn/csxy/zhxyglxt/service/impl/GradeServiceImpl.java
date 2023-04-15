package cn.csxy.zhxyglxt.service.impl;


import cn.csxy.zhxyglxt.bean.Grade;
import cn.csxy.zhxyglxt.mapper.GradeMapper;
import cn.csxy.zhxyglxt.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;
    @Override
    public Page<Grade> selectPageByName(Page<Grade> page, String gradeName) {
        QueryWrapper queryWrapper=new QueryWrapper();
        //如果模糊查询有数据 则添加进去
        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name",gradeName);
        }
        //查询到的数据按id降序
        queryWrapper.orderByDesc("id");
        return gradeMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<Grade> getGrades() {
        return gradeMapper.selectList(null);
    }
}
