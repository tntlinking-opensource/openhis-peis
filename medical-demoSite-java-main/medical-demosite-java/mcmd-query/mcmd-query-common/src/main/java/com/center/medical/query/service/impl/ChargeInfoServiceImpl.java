package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.ChargeInfoParam;
import com.center.medical.query.bean.vo.ChargeInfoPageVo;
import com.center.medical.query.bean.vo.FinanceAmountVo;
import com.center.medical.query.dao.ChargeInfoMapper;
import com.center.medical.query.service.ChargeInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("chargeInfoService")
@RequiredArgsConstructor
public class ChargeInfoServiceImpl extends ServiceImpl<ChargeInfoMapper, Peispatient> implements ChargeInfoService {

    private final ChargeInfoMapper chargeInfoMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ChargeInfoPageVo> getList(PageParam<ChargeInfoPageVo> page, ChargeInfoParam param) {
        return chargeInfoMapper.getList(page, param);
    }


    /**
     * 导出收费信息查询
     *
     * @param param
     * @return
     */
    @Override
    public List<ChargeInfoPageVo> getExportData(ChargeInfoParam param) {
        List<ChargeInfoPageVo> list = chargeInfoMapper.getExportData(param);
        for (int i = 0; i < list.size(); i++) {
            ChargeInfoPageVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }


    /**
     * 获取合计数据
     * @param param
     * @return
     */
    @Override
    public FinanceAmountVo financeCountAmount(ChargeInfoParam param) {
        return chargeInfoMapper.financeCountAmount(param);
    }
}

