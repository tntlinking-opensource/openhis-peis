package com.center.medical.finance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.VariableCostRateDto;
import com.center.medical.finance.bean.param.HSPageParam;
import com.center.medical.finance.bean.param.TotalListParam;
import com.center.medical.finance.bean.vo.HSPageVo;
import com.center.medical.finance.bean.vo.TotalListVo;
import com.center.medical.finance.dao.HealthyStatisticsMapper;
import com.center.medical.finance.service.HealthyStatisticsService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import com.center.medical.sellcrm.service.*;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售提成核算-销售团检统计(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-04-04 16:53:57
 */
@Slf4j
@Service("healthyStatisticsService")
@RequiredArgsConstructor
public class HealthyStatisticsServiceImpl extends ServiceImpl<HealthyStatisticsMapper, Createorder> implements HealthyStatisticsService {

    private final HealthyStatisticsMapper healthyStatisticsMapper;
    private final CreateorderService createorderService;
    private final OrderandcomboService orderandcomboService;
    private final CreatemealService createmealService;
    private final CreatecomboService createcomboService;
    private final PeisorgreservationService peisorgreservationService;
    private final PeisorgreservationgroupService peisorgreservationgroupService;
    private final PeispatientService peispatientService;
    private final SysUserMapper sysUserMapper;



    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HSPageVo> getList(PageParam<HSPageVo> page, HSPageParam param) {
        if (SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE) && SecurityUtils.hasRole(RoleAuthName.CAIWU)) {
            //决策管理和财务查所有的数据
        }else if (SecurityUtils.isLeader()){
            //领导查他的下级数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }else {
            //查询自己的订单
            param.setUserName(SecurityUtils.getUsername());
            param.setUserNo(SecurityUtils.getUserNo());
        }
        IPage<HSPageVo> iPage = healthyStatisticsMapper.getList(page, param);
        List<HSPageVo> list = iPage.getRecords();
//        for (HSPageVo vo : list) {
//            //获取变动成本率  太慢了,而且好像算的也有问题，暂时注释掉
//            Double variableCostRate = getVariableCostRate(vo.getId());
//            vo.setVariableCostRate(variableCostRate);
//        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 获取变动成本率
     * @param id 订单id
     * @return
     */
    private Double getVariableCostRate(String id) {
        Double costprice = 0.0;
        Double zhjg = 0.0;

        Peisorgreservation peisorgreservations = peisorgreservationService.getOne(new LambdaQueryWrapper<Peisorgreservation>().eq(Peisorgreservation::getDdh,id));
        //获取成本和优惠价
        List<VariableCostRateDto> dtos = healthyStatisticsMapper.getVariableCostRate(id,peisorgreservations.getId());
        for (VariableCostRateDto dto : dtos) {
            costprice = Arith.add(costprice,Arith.mul(dto.getCostprice(),dto.getNum()));
            zhjg = Arith.add(zhjg,Arith.mul(dto.getZhjg(),dto.getNum()));
        }
        //（A人数xA成本+B人数xB成本价）/（A人数XA实收+B人数XB实收）
        return Arith.div(costprice,zhjg);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createorder getInfoById(String id) {
        return healthyStatisticsMapper.getInfoById(id);
    }


    /**
     * 查询右边列表
     *
     * @param param
     * @return
     */
    @Override
    public List<TotalListVo> getTotalList(TotalListParam param) {
        List<TotalListVo> data = new ArrayList<>();
        if (ObjectUtils.isEmpty(param.getFzxId())) {
            //不存在分中心,获取当前登录用户分中心id
            param.setFzxId(SecurityUtils.getCId());
        }
        List<TotalListVo> list = healthyStatisticsMapper.getTotalList(param);
        TotalListVo vo = null;
        //订单排序
        int j = 1;
        //统收&合计&其它
        double ssts = 0;//Render.getDouble(os[5])
        double sshj = 0;//Render.getDouble(os[4])
        double ssqt = 0;
        double ssjz = 0;//Render.getDouble(os[6])
        String ddh = "";
        for (int i = 0; i < list.size(); i++) {
            TotalListVo os = list.get(i);
            /**os[0]订单号
             * os[1]备单日期
             * os[2]顾客登记日期
             * os[3]客户单位名称
             * os[4]合计  其它=os[4]-os[5]
             * os[5]统收
             * os[6]记账
             *
             * */
            if (i == 0 && j == 1) {
                os.setId(j);
                ddh = os.getOrder();
                sshj = os.getHj();
                ssts = os.getTs();
                //其他 = 合计 - 统收
                ssqt = MathUtil.sub(sshj, ssts);
                //其他
                os.setQt(ssqt);
                //拷贝
                vo = new TotalListVo();
                BeanUtil.copyProperties(os, vo);
                if (i == (list.size() - 1)) {
                    data.add(vo);
                }
            } else {
                if (ddh.equals(os.getOrder())) {

                    //合计
                    sshj = MathUtil.add(sshj, os.getHj());
                    vo.setHj(sshj);
                    //统收
                    ssts = MathUtil.add(ssts, os.getTs());
                    vo.setTs(ssts);
                    //其它
                    ssqt = MathUtil.add(ssqt, MathUtil.sub(os.getHj(), os.getTs()));
                    vo.setQt(ssqt);
                    //记账
                    ssjz = MathUtil.add(ssjz, os.getJz());
                    vo.setJz(ssjz);

                    if (i == (list.size() - 1)) {
                        data.add(vo);
                    }
                } else {
                    data.add(vo);

                    ddh = os.getOrder();
                    sshj = os.getHj();
                    ssts = os.getTs();
                    ssqt = MathUtil.sub(sshj, ssts);

                    vo = new TotalListVo();
                    vo.setQt(ssqt);
                    vo.setOrder(ddh);
                    vo.setKhdwmc(os.getKhdwmc());
                    vo.setRegDate(os.getRegDate());
                    vo.setFirstDate(os.getFirstDate());
                    vo.setHj(sshj);
                    vo.setTs(ssts);
                    j = j + 1;
                    vo.setId(j);

                    if (i == (list.size() - 1)) {
                        data.add(os);
                    }
                }

            }
        }

        return data;
    }

    /**
     * 导出销售团检统计
     *
     * @param param
     * @return
     */
    @Override
    public List<HSPageVo> getExportData(HSPageParam param) {
        if (SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE) && SecurityUtils.hasRole(RoleAuthName.CAIWU)) {
            //决策管理和财务查所有的数据
        }else if (SecurityUtils.isLeader()){
            //领导查他的下级数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }else {
            //查询自己的订单
            param.setUserName(SecurityUtils.getUsername());
            param.setUserNo(SecurityUtils.getUserNo());
        }
        List<HSPageVo> list = healthyStatisticsMapper.getExportData(param);
        for (int i = 0; i < list.size(); i++) {
            HSPageVo vo = list.get(i);
            vo.setRownum(i+1);
        }
//        for (HSPageVo vo : list) {
//            //加项费用、加项人数
//            JiaXiangDto jiaXiang = healthyStatisticsMapper.getJiaXiang(vo.getDdh(), param);
//            vo.setJxrs(jiaXiang.getCount());
//            vo.setJxfy(jiaXiang.getFactprice());
//        }
        return list;
    }
}

