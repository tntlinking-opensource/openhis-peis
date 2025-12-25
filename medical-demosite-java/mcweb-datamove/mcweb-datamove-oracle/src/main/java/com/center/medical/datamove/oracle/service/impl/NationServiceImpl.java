package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.NationMapper;
import com.center.medical.datamove.oracle.bean.model.Nation;
import com.center.medical.datamove.oracle.service.NationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC民族(Nation)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:09
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
    public IPage<Nation> getPage(PageParam<Nation> page, Nation param) {
        return nationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Nation getInfoById(String id) {
        return nationMapper.getInfoById(id);
    }

}


