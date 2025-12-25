package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.GeneQueryParam;
import com.center.medical.query.bean.vo.GeneQueryVo;
import com.center.medical.query.dao.GeneQueryMapper;
import com.center.medical.query.service.GeneQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2023-10-25 09:06:03
 */
@Slf4j
@Service("geneQueryService")
@RequiredArgsConstructor
public class GeneQueryServiceImpl extends ServiceImpl<GeneQueryMapper, Peispatient> implements GeneQueryService {

    private final GeneQueryMapper geneQueryMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GeneQueryVo> getPage(PageParam<GeneQueryVo> page, GeneQueryParam param) {
        return geneQueryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return geneQueryMapper.getInfoById(id);
    }

    /**
     * 导出新产品数据查询
     * @param param
     * @return
     */
    @Override
    public List<GeneQueryVo> getExportData(GeneQueryParam param) {
        List<GeneQueryVo> list = geneQueryMapper.getExportData(param);
        for (int i = 0; i < list.size(); i++) {
            GeneQueryVo geneQueryVo = list.get(i);
            geneQueryVo.setRownum(i+1);
        }
        return list;
    }
}

