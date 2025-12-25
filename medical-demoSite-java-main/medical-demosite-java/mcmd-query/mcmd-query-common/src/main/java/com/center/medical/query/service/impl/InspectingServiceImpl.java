package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.InspectingParam;
import com.center.medical.query.bean.vo.InspectingVo;
import com.center.medical.query.bean.vo.LoadFormVo;
import com.center.medical.query.dao.InspectingMapper;
import com.center.medical.query.service.InspectingService;
import com.center.medical.service.PeispatientPhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 在检人员信息(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("inspectingService")
@RequiredArgsConstructor
public class InspectingServiceImpl extends ServiceImpl<InspectingMapper, Peispatient> implements InspectingService {

    private final InspectingMapper inspectingMapper;
    private final PeispatientPhotoService peispatientPhotoService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<InspectingVo> getList(PageParam<InspectingVo> page, InspectingParam param) {
        return inspectingMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public LoadFormVo getInfoById(String id) {
        LoadFormVo info = inspectingMapper.getInfoById(id);
        //获取图片
        info.setKey16(peispatientPhotoService.getPictureByCode(info.getKey3()));
        return info;
    }


    /**
     * 导出在检人员名单 数据
     *
     * @param param
     * @return
     */
    @Override
    public List<InspectingVo> getExportData(InspectingParam param) {
        return inspectingMapper.getExportData(param);
    }
}

