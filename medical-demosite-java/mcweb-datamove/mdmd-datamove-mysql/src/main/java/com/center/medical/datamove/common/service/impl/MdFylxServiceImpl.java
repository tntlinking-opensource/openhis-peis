package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFylxMapper;
import com.center.medical.datamove.common.bean.model.MdFylx;
import com.center.medical.datamove.common.service.MdFylxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC费用类型(MdFylx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
@Slf4j
@Service("mdFylxService")
@RequiredArgsConstructor
public class MdFylxServiceImpl extends ServiceImpl<MdFylxMapper, MdFylx> implements MdFylxService {

    private final MdFylxMapper mdFylxMapper;

    /**
     * 分页查询[JC费用类型]列表
     *
     * @param page  分页参数
     * @param param MdFylx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFylx> getPage(PageParam<MdFylx> page, MdFylx param) {
        return mdFylxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFylx getInfoById(String id) {
        return mdFylxMapper.getInfoById(id);
    }

}


