package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdGroupReviewNoticeMapper;
import com.center.medical.datamove.common.bean.model.MdGroupReviewNotice;
import com.center.medical.datamove.common.service.MdGroupReviewNoticeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量职业健康检查复查通知书(MdGroupReviewNotice)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
@Slf4j
@Service("mdGroupReviewNoticeService")
@RequiredArgsConstructor
public class MdGroupReviewNoticeServiceImpl extends ServiceImpl<MdGroupReviewNoticeMapper, MdGroupReviewNotice> implements MdGroupReviewNoticeService {

    private final MdGroupReviewNoticeMapper mdGroupReviewNoticeMapper;

    /**
     * 分页查询[批量职业健康检查复查通知书]列表
     *
     * @param page  分页参数
     * @param param MdGroupReviewNotice查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdGroupReviewNotice> getPage(PageParam<MdGroupReviewNotice> page, MdGroupReviewNotice param) {
        return mdGroupReviewNoticeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdGroupReviewNotice getInfoById(String id) {
        return mdGroupReviewNoticeMapper.getInfoById(id);
    }

    ;

}


