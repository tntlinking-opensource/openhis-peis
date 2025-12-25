package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.ZyFhcsGrClassMapper;
import com.center.medical.bean.model.ZyFhcsGrClass;
import com.center.medical.service.ZyFhcsGrClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC个人防护用品种类(ZyFhcsGrClass)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
 */
@Slf4j
@Service("zyFhcsGrClassService")
@RequiredArgsConstructor
public class ZyFhcsGrClassServiceImpl extends ServiceImpl<ZyFhcsGrClassMapper, ZyFhcsGrClass> implements ZyFhcsGrClassService {

    private final ZyFhcsGrClassMapper zyFhcsGrClassMapper;

    /**
     * 分页查询[JC个人防护用品种类]列表
     *
     * @param page  分页参数
     * @param param ZyFhcsGrClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyFhcsGrClass> getList(PageParam<ZyFhcsGrClass> page, ZyFhcsGrClass param) {
        return zyFhcsGrClassMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ZyFhcsGrClass getInfoById(String id) {
        return zyFhcsGrClassMapper.getInfoById(id);
    }

}

