package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientarchiveMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientarchive;
import com.center.medical.datamove.common.service.MdPeispatientarchiveService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者档案表(MdPeispatientarchive)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:14
 */
@Slf4j
@Service("mdPeispatientarchiveService")
@RequiredArgsConstructor
public class MdPeispatientarchiveServiceImpl extends ServiceImpl<MdPeispatientarchiveMapper, MdPeispatientarchive> implements MdPeispatientarchiveService {

    private final MdPeispatientarchiveMapper mdPeispatientarchiveMapper;

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientarchive> getPage(PageParam<MdPeispatientarchive> page, MdPeispatientarchive param) {
        return mdPeispatientarchiveMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientarchive getInfoById(String id) {
        return mdPeispatientarchiveMapper.getInfoById(id);
    }

}


