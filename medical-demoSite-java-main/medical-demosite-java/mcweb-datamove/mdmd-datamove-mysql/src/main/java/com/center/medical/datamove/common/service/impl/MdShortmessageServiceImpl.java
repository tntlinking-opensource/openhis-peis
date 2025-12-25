package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdShortmessageMapper;
import com.center.medical.datamove.common.bean.model.MdShortmessage;
import com.center.medical.datamove.common.service.MdShortmessageService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC短信信息表(MdShortmessage)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:43
 */
@Slf4j
@Service("mdShortmessageService")
@RequiredArgsConstructor
public class MdShortmessageServiceImpl extends ServiceImpl<MdShortmessageMapper, MdShortmessage> implements MdShortmessageService {

    private final MdShortmessageMapper mdShortmessageMapper;

    /**
     * 分页查询[JC短信信息表]列表
     *
     * @param page  分页参数
     * @param param MdShortmessage查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdShortmessage> getPage(PageParam<MdShortmessage> page, MdShortmessage param) {
        return mdShortmessageMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdShortmessage getInfoById(String id) {
        return mdShortmessageMapper.getInfoById(id);
    }

}


