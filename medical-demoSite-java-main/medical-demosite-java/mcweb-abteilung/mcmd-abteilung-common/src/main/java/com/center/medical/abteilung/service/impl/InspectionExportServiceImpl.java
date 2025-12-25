package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.param.InspectionExportParam;
import com.center.medical.abteilung.bean.vo.InspectionExportVo;
import com.center.medical.abteilung.dao.InspectionExportMapper;
import com.center.medical.abteilung.service.InspectionExportService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.MdPeispatient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

/**
 * QT体检者表(MdPeispatient)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:10
 */
@Slf4j
@Service("InspectionExportService")
@RequiredArgsConstructor
public class InspectionExportServiceImpl extends ServiceImpl<InspectionExportMapper, MdPeispatient> implements InspectionExportService {

    private final InspectionExportMapper inspectionExportMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<InspectionExportVo> getPage(PageParam<InspectionExportVo> page, InspectionExportParam param) {
        return inspectionExportMapper.getPage(page, param);
    }


    /**
     * 获取检验数据导出数据
     * @param param
     * @return
     */
    @Override
    public List<InspectionExportVo> getExportData(InspectionExportParam param) {
        List<InspectionExportVo> list = inspectionExportMapper.getExportData(param);
        IntStream.range(0, list.size())
                .forEach(i -> list.get(i).setRownum(i + 1));
        return list;
    }
}


