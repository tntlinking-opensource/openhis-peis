package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReviewProjectMapper;
import com.center.medical.datamove.common.bean.model.MdReviewProject;
import com.center.medical.datamove.common.service.MdReviewProjectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ZJ复查项目表(MdReviewProject)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
@Slf4j
@Service("mdReviewProjectService")
@RequiredArgsConstructor
public class MdReviewProjectServiceImpl extends ServiceImpl<MdReviewProjectMapper, MdReviewProject> implements MdReviewProjectService {

    private final MdReviewProjectMapper mdReviewProjectMapper;

    /**
     * 分页查询[ZJ复查项目表]列表
     *
     * @param page  分页参数
     * @param param MdReviewProject查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReviewProject> getPage(PageParam<MdReviewProject> page, MdReviewProject param) {
        return mdReviewProjectMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReviewProject getInfoById(String id) {
        return mdReviewProjectMapper.getInfoById(id);
    }

}


