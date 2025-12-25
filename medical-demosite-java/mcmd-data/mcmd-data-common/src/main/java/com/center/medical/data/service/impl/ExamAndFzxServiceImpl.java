package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ExamAndFzx;
import com.center.medical.data.dao.ExamAndFzxMapper;
import com.center.medical.data.service.ExamAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 检查项目和分中心关联表(ExamAndFzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 10:52:59
 */
@Slf4j
@Service("examAndFzxService")
@RequiredArgsConstructor
public class ExamAndFzxServiceImpl extends ServiceImpl<ExamAndFzxMapper, ExamAndFzx> implements ExamAndFzxService {

    private final ExamAndFzxMapper examAndFzxMapper;

    /**
     * 分页查询[检查项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ExamAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ExamAndFzx> getList(PageParam<ExamAndFzx> page, ExamAndFzx param) {
        return examAndFzxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id ExamAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public ExamAndFzx getInfoById(String id) {
        return examAndFzxMapper.getInfoById(id);
    }

}

