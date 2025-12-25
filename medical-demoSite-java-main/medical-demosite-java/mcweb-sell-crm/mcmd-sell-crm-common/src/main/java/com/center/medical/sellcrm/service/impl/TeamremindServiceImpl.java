package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Customercommunicate;
import com.center.medical.sellcrm.bean.model.Teamremind;
import com.center.medical.sellcrm.bean.param.DataExceptionPeiParam;
import com.center.medical.sellcrm.bean.param.SaveKhgtParam;
import com.center.medical.sellcrm.bean.param.TeamremindParam;
import com.center.medical.sellcrm.bean.vo.DataExceptionPeiVo;
import com.center.medical.sellcrm.dao.CustomercommunicateMapper;
import com.center.medical.sellcrm.dao.CustomerfollowMapper;
import com.center.medical.sellcrm.dao.TeamremindMapper;
import com.center.medical.sellcrm.service.TeamremindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 客户预检跟踪表(Teamremind)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 14:52:38
 */
@Slf4j
@Service("teamremindService")
@RequiredArgsConstructor
public class TeamremindServiceImpl extends ServiceImpl<TeamremindMapper, Teamremind> implements TeamremindService {

    private final TeamremindMapper teamremindMapper;
    private final MapperFacade mapperFacade;
    private final CustomercommunicateMapper customercommunicateMapper;
    private final CustomerfollowMapper customerfollowMapper;

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page            分页参数
     * @param teamremindParam Teamremind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Teamremind> getPage(PageParam<Teamremind> page, TeamremindParam teamremindParam) {
        //是否为领导
        Boolean isLeader = SecurityUtils.isLeader();
        //分中心id
        String fzxId = SecurityUtils.getCId();
        //有没有决策管理的权限
        Boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE);

        //查询9个月前的记录
        DateTime offset = DateUtil.offset(new Date(), DateField.YEAR, -1);
        DateTime offset1 = DateUtil.offset(offset, DateField.MONTH, +3);
        DateTime dateTime = DateUtil.endOfMonth(offset1);
        teamremindParam.setSetSctjksrq(dateTime.toString());

        //所有人都可以看下次沟通日期是今天的
        teamremindParam.setToday(new Date());

        if (!isLeader) {
            //不是领导,并且没有决策管理权限的,看销售经理是自己的，或者下次沟通日期是今天的
            if (!greatLeader) {
                teamremindParam.setXsjlid(SecurityUtils.getUserNo());
                teamremindParam.setToday(new Date());
            }
        } else {
            //是领导,没有决策管理权限的,看本分中心的
            if (!greatLeader) {
                teamremindParam.setFzxid(fzxId);
            }
        }
        return teamremindMapper.getPage(page, teamremindParam);
    }


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Teamremind getInfoById(String id) {
        return teamremindMapper.getInfoById(id);
    }

    /**
     * 保存客户沟通记录
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveKhgtData(SaveKhgtParam param) {
        boolean state = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (ObjectUtils.isNotEmpty(param)) {
            //取得要处理的记录的id,添加处理人、处理时间、处理状态
            Teamremind teamRemind = getInfoById(param.getId());
            //进行数据的修改
            teamRemind.setClr(SecurityUtils.getUsername());
            try {
                teamRemind.setXcgtrq(sdf.parse(param.getXcgtrq()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            teamRemind.setClsj(new Date());
            teamRemind.setClzt(1);
            teamremindMapper.updateById(teamRemind);

            //进行客户沟通数据的保存
            Customercommunicate customerCo = new Customercommunicate();
            customerCo.setKhdwmc(teamRemind.getKhdwmc());
            customerCo.setKhlxdh(teamRemind.getKhlxdh());
            customerCo.setGtjg(param.getGtjg());
            customerCo.setBcgtfs(param.getBcgtfs());
            //设置属性
            try {
                customerCo.setSctjksrq(sdf.parse(param.getSctjksrq()));
                customerCo.setGtrq(sdf.parse(param.getBcgtrq()));
                customerCo.setXcgtrq(sdf.parse(param.getXcgtrq()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            if("0".equals(param.getBcgtfs())){
//                customerCo.setBcgtfs("电话");
//            }else if("1".equals(param.getBcgtfs())){
//                customerCo.setBcgtfs("QQ");
//            }else if("2".equals(param.getBcgtfs())){
//                customerCo.setBcgtfs("面对面");
//            }else{
//                customerCo.setBcgtfs("其它");
//            }

            customerCo.setXsjl(param.getXsjl());
            customerCo.setBz(param.getBz());
            //userNo
            customerCo.setXsjlid(SecurityUtils.getUserNo());
            customerCo.setFzxid(SecurityUtils.getCId());
            int insert = customercommunicateMapper.insert(customerCo);
            if (insert > 0) {
                state = true;
            }
        }
        return state;
    }


    @Override
    public List<Teamremind> getHomeData(TeamremindParam teamremindParam) {
        Date now = new Date();
        //当前用户
        SysUser user = SecurityUtils.getLoginUser().getUser();
        Date today = new Date();
        //调取配置文件获取变量
        int yearText = Integer.parseInt(Constants.YEAR_TEXT);
        int monthText = Integer.parseInt(Constants.MONTH_TEXT);

        //判断是否拥有某种权限
        Boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!greatLeader) {
            String isLeader = user.getIsleader();//是否领导
            String cid = user.getCid();
            if (!"0".equals(isLeader)) {
                teamremindParam.setFzxid(cid);
            } else {
                teamremindParam.setXsjlid(user.getUserNo());
            }
        }

        //设置进去属性
        teamremindParam.setSize(10);
        teamremindParam.setMonthText(12 * yearText - monthText);
        List<Teamremind> bySql = teamremindMapper.findBySql(teamremindParam);

        // TODO: 2022/11/23 这个接口在哪用到的？返回参数要使用原来的吗
//        for(int i=0,l=list.size();i<l;i++){
//            Object[]os=(Object[])list.get(i);
//            Map<String,String> map=new HashMap<String, String>();
//            String flag=os[2].toString();
//            if("1".equals(flag)){
//                map.put("khdwmc", "<font color='red'>"+os[0].toString()+"</font>");
//                map.put("scjcrq", "<font color='red'>"+sdf.format(os[1])+"</font>");
//                map.put("zt", "<font color='red'>需跟踪</font>");
//            }else{
//                map.put("khdwmc", os[0].toString());
//                map.put("scjcrq", sdf.format(os[1]));
//                map.put("zt", "需跟踪");
//            }
//            result.add(map);
//        }

        return bySql;
    }

    /**
     * 获取数据异常体检者数据
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<DataExceptionPeiVo> getDataExceptionPeiPage(PageParam<DataExceptionPeiVo> page, DataExceptionPeiParam param) {
        //没有决策管理权限就只能看自己的
        param.setIdOpendoctor(SecurityUtils.getUserNo());
        return teamremindMapper.getDataExceptionPeiPage(page,param);
    }
}

