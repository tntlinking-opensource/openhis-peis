package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDescribeMapper;
import com.center.medical.datamove.common.bean.model.MdDescribe;
import com.center.medical.datamove.common.service.MdDescribeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS科室描述、检查结果表(MdDescribe)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:21
 */
@Slf4j
@Service("mdDescribeService")
@RequiredArgsConstructor
public class MdDescribeServiceImpl extends ServiceImpl<MdDescribeMapper, MdDescribe> implements MdDescribeService {

    private final MdDescribeMapper mdDescribeMapper;

    /**
     * 分页查询[KS科室描述、检查结果表]列表
     *
     * @param page  分页参数
     * @param param MdDescribe查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDescribe> getPage(PageParam<MdDescribe> page, MdDescribe param) {
        return mdDescribeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDescribe getInfoById(String id) {
        return mdDescribeMapper.getInfoById(id);
    }

}


