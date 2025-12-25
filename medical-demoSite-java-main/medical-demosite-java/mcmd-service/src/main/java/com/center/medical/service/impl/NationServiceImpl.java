package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.NationMapper;
import com.center.medical.bean.model.Nation;
import com.center.medical.service.NationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC民族(Nation)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:04
 */
@Slf4j
@Service("nationService")
@RequiredArgsConstructor
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements NationService {

    private final NationMapper nationMapper;

    /**
     * 分页查询[JC民族]列表
     *
     * @param page  分页参数
     * @param param Nation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Nation> getList(PageParam<Nation> page, Nation param) {
        return nationMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Nation getInfoById(String id) {
        return nationMapper.getInfoById(id);
    }

}

