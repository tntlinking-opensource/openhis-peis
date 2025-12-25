package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOutsideMainMapper;
import com.center.medical.datamove.common.bean.model.MdOutsideMain;
import com.center.medical.datamove.common.service.MdOutsideMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送登记结果主表(MdOutsideMain)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
@Slf4j
@Service("mdOutsideMainService")
@RequiredArgsConstructor
public class MdOutsideMainServiceImpl extends ServiceImpl<MdOutsideMainMapper, MdOutsideMain> implements MdOutsideMainService {

    private final MdOutsideMainMapper mdOutsideMainMapper;

    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param MdOutsideMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOutsideMain> getPage(PageParam<MdOutsideMain> page, MdOutsideMain param) {
        return mdOutsideMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOutsideMain getInfoById(String id) {
        return mdOutsideMainMapper.getInfoById(id);
    }

}


