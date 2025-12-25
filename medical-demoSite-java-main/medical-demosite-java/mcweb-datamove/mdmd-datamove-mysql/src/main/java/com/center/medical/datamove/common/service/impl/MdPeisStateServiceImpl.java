package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeisStateMapper;
import com.center.medical.datamove.common.bean.model.MdPeisState;
import com.center.medical.datamove.common.service.MdPeisStateService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者上传状态(MdPeisState)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:08
 */
@Slf4j
@Service("mdPeisStateService")
@RequiredArgsConstructor
public class MdPeisStateServiceImpl extends ServiceImpl<MdPeisStateMapper, MdPeisState> implements MdPeisStateService {

    private final MdPeisStateMapper mdPeisStateMapper;

    /**
     * 分页查询[体检者上传状态]列表
     *
     * @param page  分页参数
     * @param param MdPeisState查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeisState> getPage(PageParam<MdPeisState> page, MdPeisState param) {
        return mdPeisStateMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeisState getInfoById(String id) {
        return mdPeisStateMapper.getInfoById(id);
    }

}


