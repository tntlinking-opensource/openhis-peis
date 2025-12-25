package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Financeinput;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.dao.FinanceinputMapper;
import com.center.medical.sellcrm.bean.dto.ImportYearTargetDto;
import com.center.medical.sellcrm.bean.model.Leadertarget;
import com.center.medical.sellcrm.bean.param.ImportTargetParam;
import com.center.medical.sellcrm.bean.param.LTSaOrUpParam;
import com.center.medical.sellcrm.bean.param.LeadertargetParam;
import com.center.medical.sellcrm.bean.vo.GetXsAndDataVo;
import com.center.medical.sellcrm.bean.vo.LTSummaryVo;
import com.center.medical.sellcrm.dao.LeadertargetMapper;
import com.center.medical.sellcrm.service.LeadertargetService;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 领导目标表(Leadertarget)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:36
 */
@Slf4j
@Service("leadertargetService")
@RequiredArgsConstructor
public class LeadertargetServiceImpl extends ServiceImpl<LeadertargetMapper, Leadertarget> implements LeadertargetService {

    private final LeadertargetMapper leadertargetMapper;
    private final SysUserMapper sysUserMapper;
    private final FinanceinputMapper financeinputMapper;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;

    private final ISysUserService iSysUserService;

    /**
     * 分页查询[领导目标表]列表
     *
     * @param page  分页参数
     * @param leadertargetParam Leadertarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Leadertarget> getList(PageParam<Leadertarget> page, LeadertargetParam leadertargetParam) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        //如果不是领导或没有决策管理权限的就把userNo放进去
        if (!isLeader){
            leadertargetParam.setUserNo(SecurityUtils.getUserNo());
        }
        return leadertargetMapper.getList(page, leadertargetParam);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Leadertarget getInfoById(String id) {
        return leadertargetMapper.getInfoById(id);
    }

    /**
     * 返回年份
     * @return
     */
    @Override
    public List getAllYear() {
        List data = new ArrayList();
        Map record=new HashMap();
        String yearStr= Constants.YEAR;
        Calendar c = Calendar.getInstance();
        int yearEnd = c.get(Calendar.YEAR);
        int yearStart = StringUtils.isNotEmpty(yearStr)?Integer.parseInt(yearStr):yearEnd;
        while(yearStart<=yearEnd){
            record=new HashMap();
            record.put("pid", "1");
            record.put("id", yearStart);
            record.put("year", yearStart);
            data.add(record);
            yearStart++;
        }
        return data;
    }

    /**
     * 获取总结数据
     * @param param
     * @return
     */
    @Override
    public List<LTSummaryVo> getSummaryData(LeadertargetParam param) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        //如果不是领导或没有决策管理权限的就把userNo放进去
        if (!isLeader){
            param.setUserNo(SecurityUtils.getUserNo());
        }
        return leadertargetMapper.getSummaryData(param);
    }

    /**
     * 新增或修改数据
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(LTSaOrUpParam param) {
        Leadertarget leaderTarget = mapperFacade.map(param, Leadertarget.class);
        boolean b = false;
        //判断id是否存在进行保存或编辑
        if(StringUtils.isEmpty(leaderTarget.getId())){
            //进行保存操作
            if (StringUtils.isNotEmpty(param.getLeaUserId())){
                leaderTarget.setXsjlid(param.getLeaUserId());
                String cid = sysUserMapper.selectUserByUserNo(param.getLeaUserId()).getCid();
                leaderTarget.setFzxid(cid);
            }
            leaderTarget.setCreatedate(new Date());
            b = this.save(leaderTarget);
        }else{
            //进行编辑操作
            Leadertarget leaderT = leadertargetMapper.getInfoById(param.getId());
            if(ObjectUtils.isNotEmpty(leaderT)){
                leaderTarget.setModifydate(new Date());
                //进行数据的更新操作
                b = this.updateById(leaderTarget);
            }
        }
        return b;
    }


    /**
     * 判断是否已经制定了目标
     * @param lyear
     * @param luserId
     * @return
     */
    @Override
    public Boolean isLeaderYearTarget(String lyear, String luserId) {
        boolean state = false;
        //获取当前登录用户所在的分中心
        String fzxId = SecurityUtils.getCId();
        //根据用户id和年份确定唯一实体,若实体存在说明之前领导为其制定过目标
        long i = leadertargetMapper.selectCount(new QueryWrapper<Leadertarget>()
                .eq("xsjlid", luserId).eq("year", lyear).eq("fzxid", fzxId));
        if(i>0){
            state = true;
        }
        return state;
    }


    /**
     * 根据年份获取相应销售关联的数据
     * @param leaderUserid
     * @param leaderYear
     * @return
     */
    @Override
    public GetXsAndDataVo getXsAndData(List<String> leaderUserid, String leaderYear) {
        //获取当前登录用户的分中心
        String fzxId = SecurityUtils.getCId();
        //属性
        Double dyjdsjwce = 0d,dejdsjwce= 0d,dsjdsjwce = 0d,dijdsjwce = 0d,ndzwce = 0d;
        String dyjdsjwceStr="",dejdsjwceStr="",dsjdsjwceStr="",dijdsjwceStr="",ndzwceStr="";
        for(int i=0;i<leaderUserid.size();i++){
            Financeinput financeInput = financeinputMapper.selectOne(new QueryWrapper<Financeinput>()
                    .eq("xsjlid", leaderUserid.get(i)).eq("year", leaderYear).eq("fzxid", fzxId));
            //获取数据格式为：xx,xx,xx,
            if(ObjectUtils.isEmpty(financeInput)){
                financeInput = new Financeinput();
            }
            //第一季度实际完成额
            dyjdsjwce = Double.valueOf(financeInput.getYy()==null?"0":financeInput.getYy())
                    +Double.valueOf(financeInput.getEy()==null?"0":financeInput.getEy())
                    +Double.valueOf(financeInput.getSy()==null?"0":financeInput.getSy());
            dyjdsjwceStr += String.valueOf(dyjdsjwce)+",";
            //第二季度实际完成额
            dejdsjwce = Double.valueOf(financeInput.getIy()==null?"0":financeInput.getIy())
                    +Double.valueOf(financeInput.getWy()==null?"0":financeInput.getWy())
                    +Double.valueOf(financeInput.getLy()==null?"0":financeInput.getLy());
            dejdsjwceStr += String.valueOf(dejdsjwce)+",";
            //第三季度实际完成额
            dsjdsjwce = Double.valueOf(financeInput.getQy()==null?"0":financeInput.getQy())
                    +Double.valueOf(financeInput.getAy()==null?"0":financeInput.getAy())
                    +Double.valueOf(financeInput.getJy()==null?"0":financeInput.getJy());
            dsjdsjwceStr += String.valueOf(dsjdsjwce)+",";
            //第四季度实际完成额
            dijdsjwce = Double.valueOf(financeInput.getOy()==null?"0":financeInput.getOy())
                    +Double.valueOf(financeInput.getNy()==null?"0":financeInput.getNy())
                    +Double.valueOf(financeInput.getDy()==null?"0":financeInput.getDy());
            dijdsjwceStr += String.valueOf(dijdsjwce)+",";
            //年度总完成额
            ndzwce = dyjdsjwce+dejdsjwce+dsjdsjwce+dijdsjwce;
            ndzwceStr += String.valueOf(ndzwce)+",";
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
     * 查询导出的销售年度目标数据
     * @param leadertargetParam
     * @return
     */
    @Override
    public List<Leadertarget> getExportData(LeadertargetParam leadertargetParam) {
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        //如果不是领导或没有决策管理权限的就把userNo放进去
        if (!isLeader){
            leadertargetParam.setUserNo(SecurityUtils.getUserNo());
        }
        return leadertargetMapper.getExportData(leadertargetParam);
    }

    /**
     * 导入年度目标
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importYearTarget(ImportTargetParam param) {
        String year = param.getYear();
        //读取数据
        ExcelUtil<ImportYearTargetDto> util = new ExcelUtil<ImportYearTargetDto>(ImportYearTargetDto.class);
        List<ImportYearTargetDto> list = null;
        try {
            list = util.importExcel(param.getFile().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("文件内容解析失败！" + JSONUtil.toJsonStr(e));
        }
        if (CollectionUtil.isEmpty(list)) {
            throw new ServiceException("名单导入失败：导入名单中不存在人员信息");
        }

        //导入数据
        List<Leadertarget> leadertargetList = new ArrayList<>();
        int i = 2;
        for (ImportYearTargetDto dto : list) {
            if (Objects.isNull(dto)) continue;
            Leadertarget leadertarget = new Leadertarget();
            leadertarget.setYear(year);
            //销售是否存在
            if (StringUtils.isNotEmpty(dto.getXsjlName())){
                SysUser sysUser = iSysUserService.getUserByUserName(dto.getXsjlName());
                if (ObjectUtils.isEmpty(sysUser)){
                    throw new ServiceException("第"+i+"行,销售经理名称未查询到");
                }
                //查询之前是否指定了年度目标
                Long count = leadertargetMapper.selectCount(new LambdaQueryWrapper<Leadertarget>()
                        .eq(Leadertarget::getYear, year)
                        .eq(Leadertarget::getXsjlid, sysUser.getUserNo()));
                if (count>0){
                    throw new ServiceException("第"+i+"行,销售经理:"+dto.getXsjlName()+",已制定过年度目标");
                }
                leadertarget.setXsjlid(sysUser.getUserNo());
            }else {
                throw new ServiceException("第"+i+"行,销售经理不能为空");
            }

            //分中心是否存在
            if (StringUtils.isNotEmpty(dto.getFzxName())){
                SysBranch branch = iSysBranchService.getByBranchName(dto.getFzxName());
                if (ObjectUtils.isEmpty(branch)){
                    throw new ServiceException("第"+i+"行,分中心名称未查询到");
                }
                leadertarget.setFzxid(branch.getBranchId());
            }else {
                throw new ServiceException("第"+i+"行,分中心名称不能为空");
            }

            //全年目标
            if (ObjectUtils.isNotEmpty(dto.getTargetAmount())){
                leadertarget.setNdmb(dto.getTargetAmount());
            }else {
                throw new ServiceException("第"+i+"行,全年目标不能为空");
            }


            //加入插入数据集合
            leadertargetList.add(leadertarget);
            i++;
        }
        this.saveBatch(leadertargetList);
        return Boolean.TRUE;
    }
}

