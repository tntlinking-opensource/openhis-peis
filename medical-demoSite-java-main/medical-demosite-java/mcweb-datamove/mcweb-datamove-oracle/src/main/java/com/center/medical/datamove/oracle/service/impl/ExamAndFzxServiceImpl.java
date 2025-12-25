package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ExamAndFzxMapper;
import com.center.medical.datamove.oracle.bean.model.ExamAndFzx;
import com.center.medical.datamove.oracle.service.ExamAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC检查项目和分中心关联表(ExamAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:38
 */
@Slf4j
@Service("examAndFzxService")
@RequiredArgsConstructor
public class ExamAndFzxServiceImpl extends ServiceImpl<ExamAndFzxMapper, ExamAndFzx> implements ExamAndFzxService {

    private final ExamAndFzxMapper examAndFzxMapper;

    /**
     * 分页查询[JC检查项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ExamAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ExamAndFzx> getPage(PageParam<ExamAndFzx> page, ExamAndFzx param) {
        return examAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ExamAndFzx getInfoById(String id) {
        return examAndFzxMapper.getInfoById(id);
    }

}


