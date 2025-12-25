package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeisOlMapper;
import com.center.medical.bean.model.PeisOl;
import com.center.medical.service.PeisOlService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 体检者线上信息(PeisOl)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:34
 */
@Slf4j
@Service("peisOlService")
@RequiredArgsConstructor
public class PeisOlServiceImpl extends ServiceImpl<PeisOlMapper, PeisOl> implements PeisOlService {

    private final PeisOlMapper peisOlMapper;

    /**
     * 分页查询[体检者线上信息]列表
     *
     * @param page  分页参数
     * @param param PeisOl查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisOl> getList(PageParam<PeisOl> page, PeisOl param) {
        return peisOlMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeisOl getInfoById(String id) {
        return peisOlMapper.getInfoById(id);
    }

    /**
     * 通知成功改变状态
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tagNoticeWechatCode(String id) {
        PeisOl peisOl = peisOlMapper.getInfoById(id);
        peisOl.setIsWechatNoticed(1);
        peisOl.setWechatNoticeType("1");
        peisOl.setWechatNoticeTime(new Date());
        peisOlMapper.updateById(peisOl);
    }
}

