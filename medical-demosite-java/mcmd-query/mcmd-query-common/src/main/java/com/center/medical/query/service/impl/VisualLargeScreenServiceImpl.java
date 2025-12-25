package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.query.bean.dto.OverViewDto;
import com.center.medical.query.bean.vo.GetHomePageDataVo;
import com.center.medical.query.dao.VisualLargeScreenMapper;
import com.center.medical.query.service.VisualLargeScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 可视化大屏(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-05-20 11:00:32
 */
@Slf4j
@Service("visualLargeScreenService")
@RequiredArgsConstructor
public class VisualLargeScreenServiceImpl extends ServiceImpl<VisualLargeScreenMapper, Peispatient> implements VisualLargeScreenService {

    private final VisualLargeScreenMapper visualLargeScreenMapper;
    private final PeispatientMapper peispatientMapper;



    /**
     * 查询首页数据
     * @param param
     * @return
     */
    @Override
    public GetHomePageDataVo getHomePageData(BaseParam param) {
        GetHomePageDataVo vo = new GetHomePageDataVo();
        //体检中心概况
        OverViewDto overViewDto = getOverView(param);
        vo.setOverView(overViewDto);
        return vo;
    }


    /**
     * 获取体检中心概况
     * @param param
     * @return
     */
    private OverViewDto getOverView(BaseParam param) {
        //体检中心概况
        OverViewDto dto = visualLargeScreenMapper.getOverViewDto(param);
        return dto;
    }
}

