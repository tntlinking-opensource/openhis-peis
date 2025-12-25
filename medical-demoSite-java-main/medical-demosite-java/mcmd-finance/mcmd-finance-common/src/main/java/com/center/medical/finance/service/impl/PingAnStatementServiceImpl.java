package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.PatientListParam;
import com.center.medical.finance.bean.param.PingAnPageParam;
import com.center.medical.finance.bean.vo.PatientListVo;
import com.center.medical.finance.bean.vo.PingAnPageVo;
import com.center.medical.finance.dao.PingAnStatementMapper;
import com.center.medical.finance.service.PingAnStatementService;
import com.center.medical.sellcrm.bean.model.Createorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单表(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-04-03 14:19:37
 */
@Slf4j
@Service("pingAnStatementService")
@RequiredArgsConstructor
public class PingAnStatementServiceImpl extends ServiceImpl<PingAnStatementMapper, Createorder> implements PingAnStatementService {

    private final PingAnStatementMapper pingAnStatementMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PingAnPageVo> getList(PageParam<PingAnPageVo> page, PingAnPageParam param) {
        return pingAnStatementMapper.getList(page, param);
    }


    /**
     * 获取体检者数据
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PatientListVo> getPatientListData(PageParam<PatientListVo> page, PatientListParam param) {
        //已登记
        boolean containUnchecked = param.getContainUnchecked()==1;
        IPage<PatientListVo> iPage = pingAnStatementMapper.getPatientListData(page, param);
        List<PatientListVo> records = iPage.getRecords();
        //设置属性
        for (PatientListVo record : records) {
            String fRegistered = record.getFRegistered() == null ? "0" : record.getFRegistered().toString();
            String line = getLine(record.getFRegistered(), record.getFReadytofinal());
            record.setLine(line);
            record.setLinenum("未检".equals(line) ? 0 : "部分".equals(line) ? 1 : "完成".equals(line) ? 2 : 3);
            //折后价格
            double tcyhj = ObjectUtils.isNotEmpty(record.getTcyhj()) ? record.getTcyhj() : 0.0;
            //如果含统收且未登记
            if (containUnchecked && !"1".equals(fRegistered)) {
                record.setSsts(tcyhj);//统收实收=套餐优惠价
                record.setSsqt(0.0);//其他实收=0.0
                record.setSshj(tcyhj);//合计实收=套餐优惠价
            } else {
                double ssts = record.getSsts();
                double sshj = record.getSshj();
                record.setSsts(ssts);
                record.setSsqt(MathUtil.sub(sshj, ssts));
                record.setSshj(sshj);
            }
        }
        iPage.setRecords(records);
        return iPage;
    }


    private String getLine(Object start, Object finish) {
        boolean begin = start == null ? false : "1".equals(start.toString()) ? true : false;
        boolean end = finish == null ? false : "1".equals(finish.toString()) ? true : false;
        if (begin) {
            if (end) {
                return "完成";
            } else {
                return "部分";
            }
        } else {
            return "未检";
        }
    }


    /**
     * 导出体检人员
     * @param param
     * @return
     */
    @Override
    public List<PatientListVo> exportOrderPatient(PatientListParam param) {
        //已登记
        boolean containUnchecked = param.getContainUnchecked()==1;
        List<PatientListVo> records = pingAnStatementMapper.exportOrderPatient(param);
        //设置属性
        int i = 1;
        for (PatientListVo record : records) {
            //序号
            record.setRownum(i);
            i++;
            String fRegistered = record.getFRegistered() == null ? "0" : record.getFRegistered().toString();
            String line = getLine(record.getFRegistered(), record.getFReadytofinal());
            record.setLine(line);
            record.setLinenum("未检".equals(line) ? 0 : "部分".equals(line) ? 1 : "完成".equals(line) ? 2 : 3);
            //折后价格
            double tcyhj = ObjectUtils.isNotEmpty(record.getTcyhj()) ? record.getTcyhj() : 0.0;
            //如果含统收且未登记
            if (containUnchecked && !"1".equals(fRegistered)) {
                record.setSsts(tcyhj);//统收实收=套餐优惠价
                record.setSsqt(0.0);//其他实收=0.0
                record.setSshj(tcyhj);//合计实收=套餐优惠价
            } else {
                double ssts = record.getSsts();
                double sshj = record.getSshj();
                record.setSsts(ssts);
                record.setSsqt(MathUtil.sub(sshj, ssts));
                record.setSshj(sshj);
            }
        }
        return records;
    }
}

