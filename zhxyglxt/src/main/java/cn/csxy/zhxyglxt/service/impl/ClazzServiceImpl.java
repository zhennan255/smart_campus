package cn.csxy.zhxyglxt.service.impl;


import cn.csxy.zhxyglxt.bean.Clazz;
import cn.csxy.zhxyglxt.mapper.ClazzMapper;
import cn.csxy.zhxyglxt.service.ClazzService;
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
public class ClazzServiceImpl  extends ServiceImpl<ClazzMapper, Clazz>implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Override
    public Page<Clazz> selectPageByGradeNameAndName(Page<Clazz> page, String gradeName, String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("grade_name",gradeName);
        }
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        return clazzMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<Clazz> getClazzs() {
        return clazzMapper.selectList(null);
    }
}
