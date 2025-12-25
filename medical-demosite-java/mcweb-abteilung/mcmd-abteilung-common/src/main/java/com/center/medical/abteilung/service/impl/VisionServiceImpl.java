package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.dto.GetC13dataDto;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.VisionMapper;
import com.center.medical.abteilung.service.VisionService;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * KS科室检查结果主表(SectionResultMain)表服务实现类
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
@Slf4j
@Service("visionService")
@RequiredArgsConstructor
public class VisionServiceImpl extends ServiceImpl<VisionMapper, SectionResultMain> implements VisionService {

    private final VisionMapper visionMapper;

    /**
    * 分页查询[KS科室检查结果主表]列表
    *
    * @param page 分页参数
    * @param param SectionResultMain查询参数
    * @return 分页数据
    */
    @Override
    public IPage<SectionResultMain> getList(PageParam<SectionResultMain> page, SectionResultMain param) {
        return visionMapper.getList(page, param);
    }

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    @Override
    public SectionResultMain getInfoById(String id){
        return visionMapper.getInfoById(id);
    };

    /**
     * 视力检查
     * @param tjzsfxm
     * @return
     */
    @Override
    public List<GetC13dataDto> getVisiondata(HashMap<String, String> parammap, List<Peispatientfeeitem> tjzsfxm) {
        String ksId = parammap.get("ksId");
        String tjh = parammap.get("tjh");// 体检号
        String tjlx = parammap.get("tjlx");// 体检类型
        return visionMapper.getVisiondata(ksId,tjh,tjlx);
    }
}

