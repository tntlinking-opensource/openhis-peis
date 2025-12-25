package com.center.medical.statistics.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.PTTotalListParam;
import com.center.medical.statistics.bean.param.PersonalTotalParam;
import com.center.medical.statistics.bean.vo.PTTotalListVo;
import com.center.medical.statistics.bean.vo.PersonalTotalVo;
import com.center.medical.statistics.dao.PersonalTotalMapper;
import com.center.medical.statistics.service.PersonalTotalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("personalTotalService")
@RequiredArgsConstructor
public class PersonalTotalServiceImpl extends ServiceImpl<PersonalTotalMapper, Peispatient> implements PersonalTotalService {

    private final PersonalTotalMapper personalTotalMapper;
    @Resource
    private LoadProperties loadProperties;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PersonalTotalVo> getList(PageParam<PersonalTotalVo> page, PersonalTotalParam param) {
        //没有决策管理和财务权限
        Boolean b = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE) && SecurityUtils.hasRole(RoleAuthName.CAIWU);
        if (!b) {
            param.setUserName(SecurityUtils.getUsername());
            param.setUserNo(SecurityUtils.getUserNo());
        }
        //胶州不查询复查的
        if (StringUtils.equals(loadProperties.name, "jiaozhou")){
            param.setNoReview(1);
        }
        IPage<PersonalTotalVo> iPage = personalTotalMapper.getList(page, param);
        List<PersonalTotalVo> list = iPage.getRecords();
        DecimalFormat df = new DecimalFormat("0.00");
        //设置属性
        for (PersonalTotalVo vo : list) {
            Double counts = vo.getCounts();
            Double price = vo.getPrice();
            Double fastPrice = vo.getFastprice();
            //折扣
            vo.setZk(df.format(price == 0 ? 0 : fastPrice / price));
            //客单价
            vo.setKdj(df.format(fastPrice == 0 ? 0 : fastPrice / counts));

            //没有权限隐藏成本价
            if (!b){
                vo.setCostprice(null);
            }
        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 导出销售团检统计
     *
     * @param param
     * @return
     */
    @Override
    public List<PersonalTotalVo> getExportData(PersonalTotalParam param) {
        //没有决策管理和财务权限
        Boolean b = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE) && SecurityUtils.hasRole(RoleAuthName.CAIWU);
        if (!b) {
            param.setUserName(SecurityUtils.getUsername());
            param.setUserNo(SecurityUtils.getUserNo());
        }
        //胶州不查询复查的
        if (StringUtils.equals(loadProperties.name, "jiaozhou")){
            param.setNoReview(1);
        }
        List<PersonalTotalVo> list = personalTotalMapper.getExportData(param);
        DecimalFormat df = new DecimalFormat("0.00");
        //设置属性
        for (PersonalTotalVo vo : list) {
            Double counts = vo.getCounts();
            Double price = vo.getPrice();
            Double fastPrice = vo.getFastprice();
            //折扣
            vo.setZk(df.format(price == 0 ? 0 : fastPrice / price));
            //客单价
            vo.setKdj(df.format(fastPrice == 0 ? 0 : fastPrice / counts));
            //没有权限隐藏成本价
            if (!b){
                vo.setCostprice(null);
            }
        }
        return list;
    }


    /**
     * 查询右边列表
     *
     * @param param
     * @return
     */
    @Override
    public List<PTTotalListVo> getTotalList(PTTotalListParam param) {
        List<PTTotalListVo> data = new ArrayList<>();
        //分中心为空并且是线上，给一个默认的分中心
        if (ObjectUtils.isEmpty(param.getBranchIds()) && ZhongkangConfig.isOnline()) {
            List<String> brandids = new ArrayList<>();
            brandids.add(SecurityUtils.getCId());
            param.setBranchIds(brandids);
        }
        List<PTTotalListVo> list = personalTotalMapper.getTotalList(param);

        PTTotalListVo vo = null;
        //订单排序
        int j = 1;
        //统收&合计&其它
        double ssts = 0;//Render.getDouble(os[5])
        double sshj = 0;//Render.getDouble(os[4])
        double ssqt = 0;
        double ssjz = 0;//Render.getDouble(os[6])
        String ddh = "";
        for (int i = 0; i < list.size(); i++) {
            PTTotalListVo os = list.get(i);
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
                vo = new PTTotalListVo();
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

                    vo = new PTTotalListVo();
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
}

