package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCarmanageMapper;
import com.center.medical.datamove.common.bean.model.MdCarmanage;
import com.center.medical.datamove.common.service.MdCarmanageService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检车管理(MdCarmanage)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
@Slf4j
@Service("mdCarmanageService")
@RequiredArgsConstructor
public class MdCarmanageServiceImpl extends ServiceImpl<MdCarmanageMapper, MdCarmanage> implements MdCarmanageService {

    private final MdCarmanageMapper mdCarmanageMapper;

    /**
     * 分页查询[体检车管理]列表
     *
     * @param page  分页参数
     * @param param MdCarmanage查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCarmanage> getPage(PageParam<MdCarmanage> page, MdCarmanage param) {
        return mdCarmanageMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCarmanage getInfoById(String id) {
        return mdCarmanageMapper.getInfoById(id);
    }

}


