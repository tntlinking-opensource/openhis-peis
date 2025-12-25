package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOccupationDiseastMapper;
import com.center.medical.datamove.common.bean.model.MdOccupationDiseast;
import com.center.medical.datamove.common.service.MdOccupationDiseastService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病名称(MdOccupationDiseast)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
@Slf4j
@Service("mdOccupationDiseastService")
@RequiredArgsConstructor
public class MdOccupationDiseastServiceImpl extends ServiceImpl<MdOccupationDiseastMapper, MdOccupationDiseast> implements MdOccupationDiseastService {

    private final MdOccupationDiseastMapper mdOccupationDiseastMapper;

    /**
     * 分页查询[JC职业病名称]列表
     *
     * @param page  分页参数
     * @param param MdOccupationDiseast查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOccupationDiseast> getPage(PageParam<MdOccupationDiseast> page, MdOccupationDiseast param) {
        return mdOccupationDiseastMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOccupationDiseast getInfoById(String id) {
        return mdOccupationDiseastMapper.getInfoById(id);
    }

}


