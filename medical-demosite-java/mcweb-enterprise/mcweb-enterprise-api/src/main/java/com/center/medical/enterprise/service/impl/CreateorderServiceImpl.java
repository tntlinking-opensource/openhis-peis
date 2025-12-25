package com.center.medical.enterprise.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.enterprise.bean.model.Createorder;
import com.center.medical.enterprise.bean.param.CreateOrderInfoDataParam;
import com.center.medical.enterprise.bean.param.CreateOrderInfoItemParam;
import com.center.medical.enterprise.bean.param.GetOrderListParam;
import com.center.medical.enterprise.bean.param.PeipatientDataParam;
import com.center.medical.enterprise.bean.vo.*;
import com.center.medical.enterprise.common.core.domain.entity.SysUser;
import com.center.medical.enterprise.common.util.PageParam;
import com.center.medical.enterprise.common.util.Render;
import com.center.medical.enterprise.common.utils.SecurityUtils;
import com.center.medical.enterprise.mapper.CreateorderMapper;
import com.center.medical.enterprise.service.CreateorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单表(MdCreateorder)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
@Slf4j
@Service("mdCreateorderService")
@RequiredArgsConstructor
public class CreateorderServiceImpl extends ServiceImpl<CreateorderMapper, Createorder> implements CreateorderService {

    private final CreateorderMapper createorderMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param MdCreateorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createorder> getPage(PageParam<Createorder> page, Createorder param) {
        return createorderMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createorder getInfoById(String id) {
        return createorderMapper.getInfoById(id);
    }

    /**
     * 获取订单详情
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<CreateOrderInfoDataVo> getInfoListData(PageParam<CreateOrderInfoDataVo> page, CreateOrderInfoDataParam param) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String customerId = user.getCustomerId();
        if (!"1".equals(customerId)){
            param.setCustomerId(customerId);
        }
        return createorderMapper.getInfoListData(page,param);
    }

    /**
     * 获取套餐详情
     * @param page
     * @param id
     * @return
     */
    @Override
    public IPage<CreateOrderInfoMealVo> getInfoMealData(PageParam<CreateOrderInfoMealVo> page, String id) {
        IPage<CreateOrderInfoMealVo> iPage = createorderMapper.getInfoMealData(page, id);
        List<CreateOrderInfoMealVo> list = iPage.getRecords();
        for (CreateOrderInfoMealVo vo : list) {
            vo.setTjlx(Render.getTjlx(vo.getTjlx()));
            vo.setSyxb(Render.getObjSex(vo.getSyxb()));
            vo.setSfyhtc(Render.getSfyhtc(vo.getSfyhtc()));
            vo.setZytjlb(Render.getMedicalType(vo.getZytjlb()));
            if (StringUtils.isNotEmpty(vo.getJhys())){
                String harmName = createorderMapper.getHarmByIds(vo.getJhys().split(","));
                vo.setJhys(harmName);
            }
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 获取套餐详情
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<CreateOrderInfoItemlVo> getInfoItemData(PageParam<CreateOrderInfoItemlVo> page, CreateOrderInfoItemParam param) {
        IPage<CreateOrderInfoItemlVo> iPage = createorderMapper.getInfoItemData(page, param);
        List<CreateOrderInfoItemlVo> list = iPage.getRecords();
        for (CreateOrderInfoItemlVo vo : list) {
            vo.setXb(Render.getObjSex(vo.getXb()));
            vo.setTjlx(Render.getTjlx(vo.getTjlx()));
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 获取订单列表
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<GetOrderListVo> getOrderList(PageParam<GetOrderListVo> page, GetOrderListParam param) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String customerId = user.getCustomerId();
        if (!"1".equals(customerId)){
            param.setCustomerId(customerId);
        }
        return createorderMapper.getOrderList(page,param);
    }

    /**
     * 获取体检者数据
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PeipatientDataVo> getPeipatientDataList(PageParam<PeipatientDataVo> page, PeipatientDataParam param) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String customerId = user.getCustomerId();
        if (!"1".equals(customerId)){
            param.setCustomerId(customerId);
        }
        IPage<PeipatientDataVo> iPage = createorderMapper.getPeipatientDataList(page, param);
        List<PeipatientDataVo> list = iPage.getRecords();
        for (PeipatientDataVo vo : list) {
            vo.setJktjzt(getTjzt(vo.getJktjztNum()));
            vo.setZytjzt(getTjzt(vo.getZytjztNum()));
            vo.setTjzt(generateTjzt(vo.getFRegistered(),vo.getFReadytofinal(),vo.getJktjztNum()));
        }
        return iPage;
    }



    private String generateTjzt(Integer f_examstarted,Integer f_readytofinal,Integer jktjzt) {
        String tjzt = "";
        if (f_examstarted.equals(1)) {
            if (f_readytofinal.equals(1)) {
                if (ObjectUtils.isNotEmpty(jktjzt)){
                    if (jktjzt.equals(1)) {
                        tjzt = "总检完成";
                    } else if (jktjzt >= 2 && jktjzt < 11) {
                        tjzt = "报告已打印";
                    } else if (jktjzt.equals(11)) {
                        tjzt = "报告已领取";
                    } else {
                        tjzt = "分检完成";
                    }
                }else {
                    tjzt = "总检未开始";
                }
            } else {
                tjzt = "已开始体检";
            }
        }
        return tjzt;
    }

    private String getTjzt(Integer e) {
        if (ObjectUtils.isEmpty(e)) {
            return "总检未开始";
        } else if (e == 0) {
            return "总检开始";
        } else if (e == 1) {
            return "总检完成";
        } else if (e == 2) {
            return "报告已打印";
        } else if (e == 3) {
            return "报告一审通过";
        } else if (e == 4) {
            return "报告一审不通过";
        } else if (e == 5) {
            return "二审通过";
        } else if (e == 6) {
            return "二审不通过";
        } else if (e == 7) {
            return "终审通过";
        } else if (e == 8) {
            return "终审不通过";
        } else if (e == 9) {
            return "报告已交接";
        } else if (e == 10) {
            return "报告已通知";
        } else if (e == 11) {
            return "报告已领取";
        } else {
            return null;
        }
    }
}


