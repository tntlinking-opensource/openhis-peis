package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeisOlMapper;
import com.center.medical.datamove.common.bean.model.MdPeisOl;
import com.center.medical.datamove.common.service.MdPeisOlService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者线上信息(MdPeisOl)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
@Slf4j
@Service("mdPeisOlService")
@RequiredArgsConstructor
public class MdPeisOlServiceImpl extends ServiceImpl<MdPeisOlMapper, MdPeisOl> implements MdPeisOlService {

    private final MdPeisOlMapper mdPeisOlMapper;

    /**
     * 分页查询[体检者线上信息]列表
     *
     * @param page  分页参数
     * @param param MdPeisOl查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeisOl> getPage(PageParam<MdPeisOl> page, MdPeisOl param) {
        return mdPeisOlMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeisOl getInfoById(String id) {
        return mdPeisOlMapper.getInfoById(id);
    }

}


