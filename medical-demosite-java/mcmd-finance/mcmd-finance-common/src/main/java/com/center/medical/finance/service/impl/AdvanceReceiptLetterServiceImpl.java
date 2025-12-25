package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ReceiptLetterParam;
import com.center.medical.finance.bean.vo.ReceiptLetterVo;
import com.center.medical.finance.dao.AdvanceReceiptLetterMapper;
import com.center.medical.finance.service.AdvanceReceiptLetterService;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者团体任务(Peisorgreservation)服务实现类
 *
 * @author ay
 * @since 2024-02-19 15:50:20
 */
@Slf4j
@Service("advanceReceiptLetterService")
@RequiredArgsConstructor
public class AdvanceReceiptLetterServiceImpl extends ServiceImpl<AdvanceReceiptLetterMapper, Peisorgreservation> implements AdvanceReceiptLetterService {

    private final AdvanceReceiptLetterMapper advanceReceiptLetterMapper;

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReceiptLetterVo> getPage(PageParam<ReceiptLetterVo> page, ReceiptLetterParam param) {
        IPage<ReceiptLetterVo> iPage = advanceReceiptLetterMapper.getPage(page, param);
        List<ReceiptLetterVo> list = iPage.getRecords();
        //约定体检总金额=套餐价格+计划来检人数
        for (ReceiptLetterVo vo : list) {
            vo.setTotalMoney(getMoney(vo.getZhjg(),vo.getNumberOfPeople()));
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peisorgreservation getInfoById(String id) {
        return advanceReceiptLetterMapper.getInfoById(id);
    }



    /*
    约定体检总金额=套餐价格+计划来检人数
     */
    public Double getMoney(Double zhjg,Double numberOfPeople){
        if(zhjg!=null&&numberOfPeople!=null){
            return MathUtil.multiply(zhjg,numberOfPeople);
        }else{
            return 0.0;
        }
    }

    /**
     * 导出应收预收函证数据
     * @param param
     * @return
     */
    @Override
    public List<ReceiptLetterVo> getExportData(ReceiptLetterParam param) {
        List<ReceiptLetterVo> list = advanceReceiptLetterMapper.getExportData(param);
        //约定体检总金额 = 套餐价格 + 计划来检人数
        for (ReceiptLetterVo vo : list) {
            vo.setTotalMoney(getMoney(vo.getZhjg(),vo.getNumberOfPeople()));
            vo.setActualInspectionAmount(MathUtil.multiply(vo.getZhjg(),vo.getNumberOfPeopleRegistered()));
        }
        return list;
    }
}

