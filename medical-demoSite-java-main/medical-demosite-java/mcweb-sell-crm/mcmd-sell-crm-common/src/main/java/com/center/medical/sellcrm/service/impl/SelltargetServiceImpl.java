package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Financeinput;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.FinanceinputMapper;
import com.center.medical.sellcrm.bean.model.Selltarget;
import com.center.medical.sellcrm.bean.param.SellDateParam;
import com.center.medical.sellcrm.bean.param.SelltargetParam;
import com.center.medical.sellcrm.bean.vo.*;
import com.center.medical.sellcrm.dao.SelltargetMapper;
import com.center.medical.sellcrm.service.SelltargetService;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * XS销售目标(Selltarget)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:23
 */
@Slf4j
@Service("selltargetService")
@RequiredArgsConstructor
public class SelltargetServiceImpl extends ServiceImpl<SelltargetMapper, Selltarget> implements SelltargetService {

    private final SelltargetMapper selltargetMapper;
    private final FinanceinputMapper financeinputMapper;
    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param selltargetParam Selltarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SelltargetVo> getPage(PageParam<SelltargetVo> page, SelltargetParam selltargetParam) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        //如果不是领导或没有决策管理权限的就把userNo放进去
        if (!isLeader){
            selltargetParam.setUserNo(SecurityUtils.getUserNo());
        }
        return selltargetMapper.getPage(page, selltargetParam);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Selltarget getInfoById(String id) {
        return selltargetMapper.getInfoById(id);
    }

    /**
     * 获取总结数据
     * @param selltargetParam
     * @return
     */
    @Override
    public List<SelltargetVo> getSummaryData(SelltargetParam selltargetParam) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        //如果不是领导或没有决策管理权限的就把userNo放进去
        if (!isLeader){
            selltargetParam.setUserNo(SecurityUtils.getUserNo());
        }
        return selltargetMapper.getSummaryData(selltargetParam);
    }

    /**
     * 数据保存或编辑
     * @param sellTarget
     * @return
     */
    @Override
    public Boolean saOrUp(Selltarget sellTarget) {
        boolean b = false;
        if(ObjectUtils.isEmpty(sellTarget.getId())){
            //保存操作
            String fzxId = SecurityUtils.getCId();
            sellTarget.setXsjlid(sellTarget.getUserid());
            sellTarget.setFzxid(fzxId);
            sellTarget.setCreatedate(new Date());
            b = this.save(sellTarget);
        }else{
            //编辑操作
            sellTarget.setModifydate(new Date());
            b = this.updateById(sellTarget);

        }
        return b;
    }

    /**
     * 获取销售人员关联的数据
     * @param selldatayear
     * @param selldatauserid
     * @return
     */
    @Override
    public GetXsAndDataVo getXsAndData(String selldatayear, String selldatauserid) {

        //获取当前登录用户的分中心
        String fzxId = SecurityUtils.getCId();
        String[] sellUserid = selldatauserid.split(",");
        Double dyjdsjwce=0d,dejdsjwce=0d,dsjdsjwce=0d,dijdsjwce=0d,ndsjzwce=0d;
        String dyjdsjwceStr="",dejdsjwceStr="",dsjdsjwceStr="",dijdsjwceStr="",ndzwceStr="";
        for(int i=0;i<sellUserid.length;i++){
            //销售财务录入表
            Financeinput financeInput = financeinputMapper.selectOne(new QueryWrapper<Financeinput>().eq("xsjlid", sellUserid[i])
                    .eq("year", selldatayear).eq("fzxid", fzxId));
            //获取数据格式为：xx,xx,xx,
            if(ObjectUtils.isEmpty(financeInput)){
                financeInput = new Financeinput();
            }
            //第一季度实际完成额
            dyjdsjwce=Double.valueOf(financeInput.getYy()==null?"0":financeInput.getYy())+Double.valueOf(financeInput.getEy()==null?"0":financeInput.getEy())+Double.valueOf(financeInput.getSy()==null?"0":financeInput.getSy());
            dyjdsjwceStr+=String.valueOf(dyjdsjwce)+",";
            //第二季度实际完成额
            dejdsjwce=Double.valueOf(financeInput.getIy()==null?"0":financeInput.getIy())+Double.valueOf(financeInput.getWy()==null?"0":financeInput.getWy())+Double.valueOf(financeInput.getLy()==null?"0":financeInput.getLy());
            dejdsjwceStr+=String.valueOf(dejdsjwce)+",";
            //第三季度实际完成额
            dsjdsjwce=Double.valueOf(financeInput.getQy()==null?"0":financeInput.getQy())+Double.valueOf(financeInput.getAy()==null?"0":financeInput.getAy())+Double.valueOf(financeInput.getJy()==null?"0":financeInput.getJy());
            dsjdsjwceStr+=String.valueOf(dsjdsjwce)+",";
            //第四季度实际完成额
            dijdsjwce=Double.valueOf(financeInput.getOy()==null?"0":financeInput.getOy())+Double.valueOf(financeInput.getNy()==null?"0":financeInput.getNy())+Double.valueOf(financeInput.getDy()==null?"0":financeInput.getDy());
            dijdsjwceStr+=String.valueOf(dijdsjwce)+",";
            //年度总完成额
            ndsjzwce=dyjdsjwce+dejdsjwce+dsjdsjwce+dijdsjwce;
            ndzwceStr+=String.valueOf(ndsjzwce)+",";
        }
        GetXsAndDataVo getXsAndDataVo = new GetXsAndDataVo();
        //第一季度实际完成额
        getXsAndDataVo.setDyjdsjwce(dyjdsjwceStr.substring(0, dyjdsjwceStr.length()-1));
        //第二季度实际完成额
        getXsAndDataVo.setDejdsjwce(dejdsjwceStr.substring(0,dejdsjwceStr.length()-1));
        //第三季度实际完成额
        getXsAndDataVo.setDsjdsjwce(dsjdsjwceStr.substring(0,dsjdsjwceStr.length()-1));
        //第四季度实际完成额
        getXsAndDataVo.setDsjdsjwce(dijdsjwceStr.substring(0,dijdsjwceStr.length()-1));
        //年度总完成额
        getXsAndDataVo.setNdzwce(ndzwceStr.substring(0,ndzwceStr.length()-1));
        return getXsAndDataVo;

    }


    /**
     * 不分页查全部
     * @param selltargetParam
     * @return
     */
    @Override
    public List<SelltargetVo> getAllList(SelltargetParam selltargetParam) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        //如果不是领导或没有决策管理权限的就把userNo放进去
        if (!isLeader){
            selltargetParam.setUserNo(SecurityUtils.getUserNo());
        }
        return selltargetMapper.getAllList(selltargetParam);
    }


    /**
     * 搜索查询
     * @param param
     * @return
     */
    @Override
    public IPage<SellDateVo> getSellDatePage(PageParam<SellDateVo> page,SellDateParam param) {

        return null;
    }


    @Override
    public SellDateVo getXsAndSellDate(SellDateParam sellDateParam) {

        return null;
    }


    /**
     * IPage<SelltargetVo>
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SellDateVo> getSellDateYear(PageParam<SellDateVo> page, SellDateParam param) {
        //没权限就只能看自己的
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!isLeader) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        //计算完成率
        IPage<SellDateVo> iPage = selltargetMapper.getSellDateYear(page, param);
        List<SellDateVo> list = iPage.getRecords();

        for (SellDateVo vo : list) {
            //完成进度
            vo.setCompletion1(MathUtil.getPercent(vo.getComplete(),vo.getNdmb()));
            vo.setCompletion2(MathUtil.getPercent(vo.getComplete2(),vo.getNdmb2()));

            //年同比 = (本期 -同期) / 同期
            vo.setGrowth(MathUtil.getPercent(MathUtil.sub(vo.getComplete2(),vo.getComplete()),vo.getComplete()));
        }
        return iPage;
    }


    /**
     * 销售同期对比季度
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SellDateQuarterVo> getSellDateQuarter(PageParam<SellDateQuarterVo> page, SellDateParam param) {
        //没权限就只能看自己的
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!isLeader) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        IPage<SellDateQuarterVo> iPage = selltargetMapper.getSellDateQuarter(page, param);
        List<SellDateQuarterVo> list = iPage.getRecords();
        for (SellDateQuarterVo vo : list) {

            //年同比 = (本期 -同期) / 同期
            vo.setGrowth1(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete1(),vo.getComplete1()),vo.getComplete1()));
            vo.setGrowth2(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete2(),vo.getComplete2()),vo.getComplete2()));
            vo.setGrowth3(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete3(),vo.getComplete3()),vo.getComplete3()));
            vo.setGrowth4(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete4(),vo.getComplete4()),vo.getComplete4()));
        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 销售同期对比月
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SellDateMonthVo> getSellDateMonth(PageParam<SellDateMonthVo> page, SellDateParam param) {
        //没权限就只能看自己的
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!isLeader) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        IPage<SellDateMonthVo> iPage = selltargetMapper.getSellDateMonth(page, param);
        List<SellDateMonthVo> list = iPage.getRecords();
        for (SellDateMonthVo vo : list) {

            //年同比 = (本期 -同期) / 同期
            vo.setGrowth1(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete1(),vo.getComplete1()),vo.getComplete1()));
            vo.setGrowth2(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete2(),vo.getComplete2()),vo.getComplete2()));
            vo.setGrowth3(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete3(),vo.getComplete3()),vo.getComplete3()));
            vo.setGrowth4(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete4(),vo.getComplete4()),vo.getComplete4()));
            vo.setGrowth5(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete5(),vo.getComplete5()),vo.getComplete5()));
            vo.setGrowth6(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete6(),vo.getComplete6()),vo.getComplete6()));
            vo.setGrowth7(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete7(),vo.getComplete7()),vo.getComplete7()));
            vo.setGrowth8(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete8(),vo.getComplete8()),vo.getComplete8()));
            vo.setGrowth9(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete9(),vo.getComplete9()),vo.getComplete9()));
            vo.setGrowth10(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete10(),vo.getComplete10()),vo.getComplete10()));
            vo.setGrowth11(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete11(),vo.getComplete11()),vo.getComplete11()));
            vo.setGrowth12(MathUtil.getPercent(MathUtil.sub(vo.getEndComplete12(),vo.getComplete12()),vo.getComplete12()));
        }
        iPage.setRecords(list);
        return iPage;
    }
}

