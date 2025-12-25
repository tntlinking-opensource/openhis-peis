package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTjbbBmiMapper;
import com.center.medical.datamove.common.bean.model.MdTjbbBmi;
import com.center.medical.datamove.common.service.MdTjbbBmiService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS体重指数体检报表(MdTjbbBmi)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:00
 */
@Slf4j
@Service("mdTjbbBmiService")
@RequiredArgsConstructor
public class MdTjbbBmiServiceImpl extends ServiceImpl<MdTjbbBmiMapper, MdTjbbBmi> implements MdTjbbBmiService {

    private final MdTjbbBmiMapper mdTjbbBmiMapper;

    /**
     * 分页查询[KS体重指数体检报表]列表
     *
     * @param page  分页参数
     * @param param MdTjbbBmi查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTjbbBmi> getPage(PageParam<MdTjbbBmi> page, MdTjbbBmi param) {
        return mdTjbbBmiMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTjbbBmi getInfoById(String id) {
        return mdTjbbBmiMapper.getInfoById(id);
    }

}


