package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.NotifySmsExamMapper;
import com.center.medical.datamove.oracle.bean.model.NotifySmsExam;
import com.center.medical.datamove.oracle.service.NotifySmsExamService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 来检短信提醒表(NotifySmsExam)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:13
 */
@Slf4j
@Service("notifySmsExamService")
@RequiredArgsConstructor
public class NotifySmsExamServiceImpl extends ServiceImpl<NotifySmsExamMapper, NotifySmsExam> implements NotifySmsExamService {

    private final NotifySmsExamMapper notifySmsExamMapper;

    /**
     * 分页查询[来检短信提醒表]列表
     *
     * @param page  分页参数
     * @param param NotifySmsExam查询参数
     * @return 分页数据
     */
    @Override
    public IPage<NotifySmsExam> getPage(PageParam<NotifySmsExam> page, NotifySmsExam param) {
        return notifySmsExamMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public NotifySmsExam getInfoById(String id) {
        return notifySmsExamMapper.getInfoById(id);
    }

}


