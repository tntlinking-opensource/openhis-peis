package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.TDPageParam;
import com.center.medical.finance.bean.vo.TDPageVo;
import com.center.medical.finance.dao.TallyDetailMapper;
import com.center.medical.finance.service.TallyDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 记账管理-记账结算明细(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
@Slf4j
@Service("tallyDetailService")
@RequiredArgsConstructor
public class TallyDetailServiceImpl extends ServiceImpl<TallyDetailMapper, Peispatient> implements TallyDetailService {

    private final TallyDetailMapper tallyDetailMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TDPageVo> getList(PageParam<TDPageVo> page, TDPageParam param) {
        return tallyDetailMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return tallyDetailMapper.getInfoById(id);
    }


    /**
     * 导出记帐结算明细
     *
     * @param param
     * @return
     */
    @Override
    public List<TDPageVo> getExportData(TDPageParam param) {
        return tallyDetailMapper.getExportData(param);
    }
}

