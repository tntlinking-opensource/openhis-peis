package com.center.medical.reception.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.OutsideCheckin;
import com.center.medical.reception.bean.vo.PictureDataVo;
import com.center.medical.reception.dao.OutsideCheckinMapper;
import com.center.medical.reception.service.OutsideCheckinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KS外送项目图片关联表(OutsideCheckin)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:35
 */
@Slf4j
@Service("outsideCheckinService")
@RequiredArgsConstructor
public class OutsideCheckinServiceImpl extends ServiceImpl<OutsideCheckinMapper, OutsideCheckin> implements OutsideCheckinService {

    private final OutsideCheckinMapper outsideCheckinMapper;

    /**
     * 分页查询[KS外送项目图片关联表]列表
     *
     * @param page  分页参数
     * @param param OutsideCheckin查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OutsideCheckin> getList(PageParam<OutsideCheckin> page, OutsideCheckin param) {
        return outsideCheckinMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OutsideCheckin getInfoById(String id) {
        return outsideCheckinMapper.getInfoById(id);
    }

    /**
     * 获取与图片结果关联项目
     *
     * @param patientcode
     * @return
     */
    @Override
    public List<PictureDataVo> getPictureData(String patientcode) {
        return outsideCheckinMapper.getPictureData(patientcode);
    }
}

