package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.TotalAddParam;
import com.center.medical.query.bean.vo.TotalAddVo;
import com.center.medical.query.dao.TotalAddMapper;
import com.center.medical.query.service.TotalAddService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加项情况查询(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("totalAddService")
@RequiredArgsConstructor
public class TotalAddServiceImpl extends ServiceImpl<TotalAddMapper, Peispatient> implements TotalAddService {

    private final TotalAddMapper totalAddMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TotalAddVo> getList(PageParam<TotalAddVo> page, TotalAddParam param) {
        IPage<TotalAddVo> iPage = totalAddMapper.getList(page, param);
        List<TotalAddVo> list = iPage.getRecords();
//        for (TotalAddVo vo : list) {
//            //海关需求 增加一列非统收的加项总金额
//            Double addPrice = totalAddMapper.getTotalAddPrice(vo.getPatientcode());
//            vo.setAddprice(addPrice);
//        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 导出加项查询数据
     *
     * @param param
     * @return
     */
    @Override
    public List<TotalAddVo> getExportData(TotalAddParam param) {
        List<TotalAddVo> list = totalAddMapper.getExportData(param);
//        for (TotalAddVo vo : list) {
//            //海关需求 增加一列非统收的加项总金额
//            Double addPrice = totalAddMapper.getTotalAddPrice(vo.getPatientcode());
//            vo.setAddprice(addPrice);
//        }
        return list;
    }

    /**
     * 获取分页合计
     * @param param
     * @return
     */
    @Override
    public Double getPageTotal(TotalAddParam param) {
        return totalAddMapper.getPageTotal(param);
    }
}

