package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdNotifySmsExamMapper;
import com.center.medical.datamove.common.bean.model.MdNotifySmsExam;
import com.center.medical.datamove.common.service.MdNotifySmsExamService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 来检短信提醒表(MdNotifySmsExam)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
@Slf4j
@Service("mdNotifySmsExamService")
@RequiredArgsConstructor
public class MdNotifySmsExamServiceImpl extends ServiceImpl<MdNotifySmsExamMapper, MdNotifySmsExam> implements MdNotifySmsExamService {

    private final MdNotifySmsExamMapper mdNotifySmsExamMapper;

    /**
     * 分页查询[来检短信提醒表]列表
     *
     * @param page  分页参数
     * @param param MdNotifySmsExam查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdNotifySmsExam> getPage(PageParam<MdNotifySmsExam> page, MdNotifySmsExam param) {
        return mdNotifySmsExamMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdNotifySmsExam getInfoById(String id) {
        return mdNotifySmsExamMapper.getInfoById(id);
    }

}


