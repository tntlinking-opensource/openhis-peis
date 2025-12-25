package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ChargeDistributeParam;
import com.center.medical.statistics.bean.vo.CDGetTotalVo;
import com.center.medical.statistics.bean.vo.ChargeDistributeVo;
import com.center.medical.statistics.dao.ChargeDistributeMapper;
import com.center.medical.statistics.service.ChargeDistributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;

/**
 * 收费项目分布情况(Peispatientfeeitem)表服务实现类
 *
 * @author ay
 * @since 2023-04-18 19:47:26
 */
@Slf4j
@Service("chargeDistributeService")
@RequiredArgsConstructor
public class ChargeDistributeServiceImpl extends ServiceImpl<ChargeDistributeMapper, Peispatientfeeitem> implements ChargeDistributeService {

    private final ChargeDistributeMapper chargeDistributeMapper;

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ChargeDistributeVo> getList(PageParam<ChargeDistributeVo> page, ChargeDistributeParam param) {
        IPage<ChargeDistributeVo> iPage = chargeDistributeMapper.getList(page, param);
        List<ChargeDistributeVo> list = iPage.getRecords();

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        //设置属性
        for (ChargeDistributeVo vo : list) {
            double ying = vo.getCharge4()==null?0.0:Double.parseDouble(vo.getCharge4().toString());
            double shi = vo.getCharge3()==null?0.0:Double.parseDouble(vo.getCharge3().toString());
            String zk = ying==0?"":(numberFormat.format((ying - shi) / ying * 100) + "%");
            vo.setCharge6(zk);

            int zong = vo.getCharge2()==null?0:Integer.parseInt(vo.getCharge2().toString());
            int ge = vo.getCharge7()==null?0:Integer.parseInt(vo.getCharge7().toString());
            int tuan = vo.getCharge9()==null?0:Integer.parseInt(vo.getCharge9().toString());// 团体预定
            vo.setCharge8(zong - ge - tuan);
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 导出体检收费项目分布
     * @param param
     * @return
     */
    @Override
    public List<ChargeDistributeVo> exportData(ChargeDistributeParam param) {
        List<ChargeDistributeVo> list = chargeDistributeMapper.exportData(param);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        //设置属性
        for (int i = 0; i < list.size(); i++) {
            ChargeDistributeVo vo = list.get(i);
            //序号
            vo.setRownum(i+1);
            double ying = vo.getCharge4()==null?0.0:Double.parseDouble(vo.getCharge4().toString());
            double shi = vo.getCharge3()==null?0.0:Double.parseDouble(vo.getCharge3().toString());
            String zk = ying==0?"":(numberFormat.format((ying - shi) / ying * 100) + "%");
            //折扣
            vo.setCharge6(zk);

            int zong = vo.getCharge2()==null?0:Integer.parseInt(vo.getCharge2().toString());
            int ge = vo.getCharge7()==null?0:Integer.parseInt(vo.getCharge7().toString());
            int tuan = vo.getCharge9()==null?0:Integer.parseInt(vo.getCharge9().toString());
            //总-个-团
            vo.setCharge8(zong - ge - tuan);
        }
        return list;
    }


    /**
     * 获取总数
     * @param param
     * @return
     */
    @Override
    public CDGetTotalVo getTotal(ChargeDistributeParam param) {
        return chargeDistributeMapper.getTotal(param);
    }
}

