package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.TotalComboParam;
import com.center.medical.statistics.bean.vo.TotalComboVo;
import com.center.medical.statistics.dao.TotalComboMapper;
import com.center.medical.statistics.service.TotalComboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 每月每日套餐统计(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("totalComboService")
@RequiredArgsConstructor
public class TotalComboServiceImpl extends ServiceImpl<TotalComboMapper, Peispatient> implements TotalComboService {

    private final TotalComboMapper totalComboMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TotalComboVo> getList(PageParam<TotalComboVo> page, TotalComboParam param) {
        return totalComboMapper.getList(page, param);
    }


    /**
     * 导出体检套餐统计
     * @param param
     * @return
     */
    @Override
    public List<TotalComboVo> getExportData(TotalComboParam param) {
        List<TotalComboVo> list = totalComboMapper.getExportData(param);
        //设置序号
        for (int i = 0; i < list.size(); i++) {
            TotalComboVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }
}

