package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.bean.model.Orderandcombo;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.ApproveTjtcDataParam;
import com.center.medical.sellcrm.bean.vo.OrderMealVo;
import com.center.medical.sellcrm.dao.CreatecomboMapper;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.sellcrm.dao.OrderandcomboMapper;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.sellcrm.service.OrderandcomboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单与套餐关联表(Orderandcombo)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:08
 */
@Slf4j
@Service("orderandcomboService")
@RequiredArgsConstructor
public class OrderandcomboServiceImpl extends ServiceImpl<OrderandcomboMapper, Orderandcombo> implements OrderandcomboService {

    private final OrderandcomboMapper orderandcomboMapper;
    private final CreatemealMapper createmealMapper;
    private final HarmMapper harmMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final CreatecomboMapper createcomboMapper;

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandcombo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Orderandcombo> getPage(PageParam<Orderandcombo> page, Orderandcombo param) {
        return orderandcomboMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Orderandcombo getInfoById(String id) {
        return orderandcomboMapper.getInfoById(id);
    }

    /**
     * 编辑默认加载订单关联的套餐
     *
     * @param page
     * @param ddId
     * @param isCopy
     * @return
     */
    @Override
    public IPage<Orderandcombo> getTjtcData(PageParam<Orderandcombo> page, String ddId, Integer isCopy) {
        IPage<Orderandcombo> iPage = orderandcomboMapper.getTjtcData(page, ddId, isCopy);
        return iPage;
    }


    /**
     * 获取审核订单下关联的套餐
     *
     * @param page
     * @param approveTjtcDataParam
     * @return
     */
    @Override
    public Map getApproveTjtcData(PageParam<Orderandcombo> page, ApproveTjtcDataParam approveTjtcDataParam) {
        //拼接接害因素名称
        String jhName = "";

        List<Orderandcombo> newList = new ArrayList<>();


        PageParam<Orderandcombo> iPage = orderandcomboMapper.selectPage(page, new QueryWrapper<Orderandcombo>().eq("ddid", approveTjtcDataParam.getApprddId()));
        List<Orderandcombo> list = iPage.getRecords();

        for (Orderandcombo orderAndCombo : list) {

            //添加筛选条件
            QueryWrapper<Createmeal> queryWrapper = new QueryWrapper<Createmeal>()
                    .and(wrapper -> wrapper.isNull("forbidden").or().eq("forbidden", 0));;
            QueryWrapper<Createcombo> queryWrapper2 = new QueryWrapper<Createcombo>()
                    .and(wrapper -> wrapper.isNull("is_ban").or().eq("is_ban", 0));;
            //体检套餐名称
            if (StringUtils.isNotEmpty(approveTjtcDataParam.getTjtcmc())) {
                queryWrapper.like("tjtcmc", approveTjtcDataParam.getTjtcmc());
                queryWrapper2.like("tjtcmc", approveTjtcDataParam.getTjtcmc());
            }
            //体检套餐输入码
            if (StringUtils.isNotEmpty(approveTjtcDataParam.getTjtcsrm())) {
                queryWrapper.like("tjtcsrm", approveTjtcDataParam.getTjtcsrm());
                queryWrapper2.like("tjtcsrm", approveTjtcDataParam.getTjtcsrm());
            }
            //体检类型
            if (StringUtils.isNotEmpty(approveTjtcDataParam.getTjlx())) {
                queryWrapper.eq("tjlx", approveTjtcDataParam.getTjlx());
                queryWrapper2.eq("tjlx", approveTjtcDataParam.getTjlx());
            }
            //适用性别
            if (StringUtils.isNotEmpty(approveTjtcDataParam.getSyxb())) {
                queryWrapper.eq("syxb", approveTjtcDataParam.getSyxb());
                queryWrapper2.eq("syxb", approveTjtcDataParam.getSyxb());
            }
            //折后价格
            if (ObjectUtils.isNotEmpty(approveTjtcDataParam.getZhjg())) {
                queryWrapper.eq("zhjg", approveTjtcDataParam.getZhjg());
                queryWrapper2.eq("zhjg", approveTjtcDataParam.getZhjg());
            }

            orderAndCombo.setId(orderAndCombo.getTcid());
            if ("0".equals(orderAndCombo.getCombostate())) {
                //从普通套餐表中取数据
                Createmeal createMeal = createmealMapper.selectOne(queryWrapper.eq("id", orderAndCombo.getTcid()));
                if (ObjectUtils.isNotEmpty(createMeal)) {
                    //设置属性
                    orderAndCombo.setTjtcmc(createMeal.getTjtcmc());
                    orderAndCombo.setTjlx(createMeal.getTjlx());
                    orderAndCombo.setTjtcjc(createMeal.getTjtcjc());
                    orderAndCombo.setTjtcsrm(createMeal.getTjtcsrm());
                    orderAndCombo.setJhys(createMeal.getJhys());
                    if (StringUtils.isNotEmpty(createMeal.getJhys())) {
                        String[] jhysData = createMeal.getJhys().split(",");
                        for (int i = 0; i < jhysData.length; i++) {
                            //拼接接害因素名称
                            Harm harm = harmMapper.getInfoById(jhysData[i]);
                            if (harm != null) {
                                jhName += harm.getHarmName() + ",";
                            }
                        }
                        orderAndCombo.setJhysName(StringUtils.isNotEmpty(jhName) ? jhName.substring(0, jhName.length() - 1) : jhName);
                        orderAndCombo.setJhysV(createMeal.getJhys());
                        jhName = "";
                    }
                    orderAndCombo.setSyxb(createMeal.getSyxb());
                    orderAndCombo.setTcysjg(createMeal.getTcysjg());
                    orderAndCombo.setZhjg(createMeal.getZhjg());
                    orderAndCombo.setTczk(createMeal.getTczk());
                    //获取客户单位单位名称
                    Sellcustomer sc = sellcustomerMapper.getInfoById(createMeal.getKhdwmcid());
                    orderAndCombo.setKhdwmc(ObjectUtils.isEmpty(sc) ? "" : sc.getKhdwmc());
                    orderAndCombo.setSfybd(createMeal.getSfybd());
                    orderAndCombo.setSfyhtc(createMeal.getSfyhtc());
                    orderAndCombo.setNlz(createMeal.getNlz());
                    orderAndCombo.setNld(createMeal.getNld());
                    orderAndCombo.setFkfs(createMeal.getFkfs());
                    orderAndCombo.setZytjlb(String.valueOf(createMeal.getZytjlb()));
                    orderAndCombo.setCombostate(createMeal.getCombostate());
                    newList.add(orderAndCombo);
                }
            } else {
                //从最小套餐表中取数据
                Createcombo createCombo = createcomboMapper.selectOne(queryWrapper2.eq("id", orderAndCombo.getTcid()));
                if (ObjectUtils.isNotEmpty(createCombo)) {
                    orderAndCombo.setTjtcmc(createCombo.getTjtcmc());
                    orderAndCombo.setTjlx(createCombo.getTjlx());
                    orderAndCombo.setTjtcjc(createCombo.getTjtcjc());
                    orderAndCombo.setTjtcsrm(createCombo.getTjtcsrm());
                    if (StringUtils.isNotEmpty(createCombo.getJhys())) {
                        String[] jhysData = createCombo.getJhys().split(",");
                        for (int i = 0; i < jhysData.length; i++) {
                            //拼接接害因素名称
                            jhName += harmMapper.getInfoById(jhysData[i]).getHarmName() + ",";
                        }
                        orderAndCombo.setJhys(jhName.substring(0, jhName.length() - 1));
                        orderAndCombo.setJhysV(createCombo.getJhys());
                        jhName = "";
                    }
                    orderAndCombo.setSyxb(createCombo.getSyxb());
                    orderAndCombo.setTcysjg(createCombo.getTcysjg());
                    orderAndCombo.setTczk(createCombo.getTczk());
                    orderAndCombo.setSfyhtc(createCombo.getSfyhtc());
                    orderAndCombo.setNlz(createCombo.getNlz());
                    orderAndCombo.setNld(createCombo.getNld());
                    orderAndCombo.setZytjlb(createCombo.getZytjlb());
                    orderAndCombo.setCombostate(createCombo.getCombostate());
                    newList.add(orderAndCombo);
                }
            }
        }
        Map map = new HashMap();
        map.put("data", newList);
        map.put("total", iPage.getTotal());
        return map;
    }

    /**
     * 团检专属卡-套餐搜索
     *
     * @param id
     * @param key
     * @return
     */
    @Override
    public List<OrderMealVo> getOrderMealData(String id, String key) {
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return orderandcomboMapper.getOrderMealData(id, key);
    }


}

