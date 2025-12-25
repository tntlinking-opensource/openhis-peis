package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdNationMapper;
import com.center.medical.datamove.common.bean.model.MdNation;
import com.center.medical.datamove.common.service.MdNationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC民族(MdNation)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
@Slf4j
@Service("mdNationService")
@RequiredArgsConstructor
public class MdNationServiceImpl extends ServiceImpl<MdNationMapper, MdNation> implements MdNationService {

    private final MdNationMapper mdNationMapper;

    /**
     * 分页查询[JC民族]列表
     *
     * @param page  分页参数
     * @param param MdNation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdNation> getPage(PageParam<MdNation> page, MdNation param) {
        return mdNationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdNation getInfoById(String id) {
        return mdNationMapper.getInfoById(id);
    }

    ;

}


