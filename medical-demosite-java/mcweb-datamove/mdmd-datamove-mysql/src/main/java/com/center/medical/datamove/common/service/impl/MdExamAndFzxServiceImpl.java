package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdExamAndFzxMapper;
import com.center.medical.datamove.common.bean.model.MdExamAndFzx;
import com.center.medical.datamove.common.service.MdExamAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 检查项目和分中心关联表(MdExamAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
@Slf4j
@Service("mdExamAndFzxService")
@RequiredArgsConstructor
public class MdExamAndFzxServiceImpl extends ServiceImpl<MdExamAndFzxMapper, MdExamAndFzx> implements MdExamAndFzxService {

    private final MdExamAndFzxMapper mdExamAndFzxMapper;

    /**
     * 分页查询[检查项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdExamAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdExamAndFzx> getPage(PageParam<MdExamAndFzx> page, MdExamAndFzx param) {
        return mdExamAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdExamAndFzx getInfoById(String id) {
        return mdExamAndFzxMapper.getInfoById(id);
    }

}


