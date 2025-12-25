package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.statistics.bean.param.PositiveQueryParam;
import com.center.medical.statistics.bean.vo.PositiveQueryVo;
import com.center.medical.statistics.dao.PositiveQueryMapper;
import com.center.medical.statistics.service.PositiveQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 职业健康检查结果结论附表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("positiveQueryService")
@RequiredArgsConstructor
public class PositiveQueryServiceImpl extends ServiceImpl<PositiveQueryMapper, Peispatient> implements PositiveQueryService {

    private final PositiveQueryMapper positiveQueryMapper;
    private final HarmMapper harmMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PositiveQueryVo> getList(PageParam<PositiveQueryVo> page, PositiveQueryParam param) {
        //没有决策管理权限就只能看自己的
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            param.setUserNo(SecurityUtils.getUserNo());
            param.setUserName(SecurityUtils.getUsername());
        }
        IPage<PositiveQueryVo> iPage = positiveQueryMapper.getList(page, param);
        List<PositiveQueryVo> records = iPage.getRecords();
        //循环
        for (PositiveQueryVo vo : records) {
            //总工龄
            if (vo.getZgl() != null && !vo.getZgl().toString().equals("0")) {
                BigDecimal hramyear = new BigDecimal(vo.getZgl().toString());
                BigDecimal oneyear = new BigDecimal(12);
                BigDecimal num = hramyear.divide(oneyear, 1, BigDecimal.ROUND_HALF_UP);
                vo.setZgl(num.toString());
            } else if (vo.getZgl().toString().equals("0")) {
                vo.setZgl("0");
            } else {
                vo.setZgl("");
            }
            //接害工龄
            if (vo.getJhgl() != null && !vo.getJhgl().toString().equals("0")) {
                BigDecimal hramyear = new BigDecimal(vo.getJhgl().toString());
                BigDecimal oneyear = new BigDecimal(12);
                BigDecimal num = hramyear.divide(oneyear, 1, BigDecimal.ROUND_HALF_UP);
                vo.setJhgl(num.toString());
            } else if (vo.getJhgl() != null && vo.getJhgl().toString().equals("0")) {
                vo.setJhgl("0");
            } else {
                vo.setJhgl("");
            }
        }
        iPage.setRecords(records);
        return iPage;
    }


    /**
     * 获取检查项目
     *
     * @param obj
     * @return
     */
    @Override
    public String getHarmStr(Object obj) {
        if (obj != null) {
            String[] jhyss = obj.toString().split(",");
            List<Harm> harms = harmMapper.selectList(new QueryWrapper<Harm>().in("id", jhyss));
            if (harms.size() == 0) {
                return StringUtils.EMPTY;
            } else {
                StringBuilder builder = new StringBuilder();
                for (Harm harm : harms) {
                    builder.append(harm.getHarmName() + "、");
                }
                return builder.substring(0, builder.length() - 1);
            }
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return positiveQueryMapper.getInfoById(id);
    }


    /**
     * 导出单位职业健康检查结果附表
     *
     * @param param
     * @return
     */
    @Override
    public List<PositiveQueryVo> getExportData(PositiveQueryParam param) {
        //没有决策管理权限就只能看自己的
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            param.setUserNo(SecurityUtils.getUserNo());
            param.setUserName(SecurityUtils.getUsername());
        }
        List<PositiveQueryVo> list = positiveQueryMapper.getExportData(param);
        //循环
        for (PositiveQueryVo vo : list) {
            //总工龄
            if (vo.getZgl() != null && !vo.getZgl().toString().equals("0")) {
                BigDecimal hramyear = new BigDecimal(vo.getZgl().toString());
                BigDecimal oneyear = new BigDecimal(12);
                BigDecimal num = hramyear.divide(oneyear, 1, BigDecimal.ROUND_HALF_UP);
                vo.setZgl(num.toString());
            } else if (vo.getZgl().toString().equals("0")) {
                vo.setZgl("0");
            } else {
                vo.setZgl("");
            }
            //接害工龄
            if (vo.getJhgl() != null && !vo.getJhgl().toString().equals("0")) {
                BigDecimal hramyear = new BigDecimal(vo.getJhgl().toString());
                BigDecimal oneyear = new BigDecimal(12);
                BigDecimal num = hramyear.divide(oneyear, 1, BigDecimal.ROUND_HALF_UP);
                vo.setJhgl(num.toString());
            } else if (vo.getJhgl() != null && vo.getJhgl().toString().equals("0")) {
                vo.setJhgl("0");
            } else {
                vo.setJhgl("");
            }
        }
        return list;
    }
}

