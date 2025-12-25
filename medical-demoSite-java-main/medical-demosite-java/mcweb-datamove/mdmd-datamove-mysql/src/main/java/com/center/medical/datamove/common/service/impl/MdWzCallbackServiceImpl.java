package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzCallbackMapper;
import com.center.medical.datamove.common.bean.model.MdWzCallback;
import com.center.medical.datamove.common.service.MdWzCallbackService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——复查随访(MdWzCallback)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:22
 */
@Slf4j
@Service("mdWzCallbackService")
@RequiredArgsConstructor
public class MdWzCallbackServiceImpl extends ServiceImpl<MdWzCallbackMapper, MdWzCallback> implements MdWzCallbackService {

    private final MdWzCallbackMapper mdWzCallbackMapper;

    /**
     * 分页查询[KS问诊——复查随访]列表
     *
     * @param page  分页参数
     * @param param MdWzCallback查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzCallback> getPage(PageParam<MdWzCallback> page, MdWzCallback param) {
        return mdWzCallbackMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzCallback getInfoById(String id) {
        return mdWzCallbackMapper.getInfoById(id);
    }

}


