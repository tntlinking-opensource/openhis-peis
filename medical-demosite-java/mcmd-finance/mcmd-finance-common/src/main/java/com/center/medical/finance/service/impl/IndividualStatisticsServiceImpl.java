package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ISListDataParam;
import com.center.medical.finance.bean.param.ISPageParam;
import com.center.medical.finance.bean.vo.ExportOneVo;
import com.center.medical.finance.bean.vo.ISListDataVo;
import com.center.medical.finance.bean.vo.ISPageVo;
import com.center.medical.finance.dao.IndividualStatisticsMapper;
import com.center.medical.finance.service.IndividualStatisticsService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserDepMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-06 10:40:40
 */
@Slf4j
@Service("individualStatisticsService")
@RequiredArgsConstructor
public class IndividualStatisticsServiceImpl extends ServiceImpl<IndividualStatisticsMapper, Peispatient> implements IndividualStatisticsService {

    private final IndividualStatisticsMapper individualStatisticsMapper;
    private final SysUserMapper sysUserMapper;
    private final SysUserDepMapper sysUserDepMapper;
    private final SysDeptMapper sysDeptMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ISPageVo> getList(PageParam<ISPageVo> page, ISPageParam param) {
        boolean hasks = ObjectUtils.isNotEmpty(param.getType()) || StringUtils.isNotEmpty(param.getKsid());
        param.setHasks(hasks ? 1 : 0);
        if (ObjectUtils.isNotEmpty(param.getIsContainNuclein()) && param.getIsContainNuclein() == 0){
            param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
        }
        if (ObjectUtils.isNotEmpty(param.getIsContainVaccine()) && param.getIsContainVaccine() == 0){
            param.setYmks("ff8080817bc96399017bd2c73a0c5e96");
        }
        Boolean caiwu = SecurityUtils.hasRole(RoleAuthName.CAIWU);
        IPage<ISPageVo> iPage = individualStatisticsMapper.getList(page, param);
        List<ISPageVo> list = iPage.getRecords();
        for (ISPageVo vo : list) {
            Double yjhj = vo.getYjhj();
            Double sshj = ObjectUtils.isEmpty(vo.getSshj()) ? 0.0 : vo.getSshj();
            //折扣（实收合计/原价合计）
            vo.setZk(String.format("%.2f", sshj / yjhj));
            //客单价（实收合计/人数）
            vo.setKdj(String.format("%.2f", sshj / vo.getCount()));
            //没有财务权限隐藏成本价
            if (!caiwu){
                vo.setCostprice(null);
            }
        }
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return individualStatisticsMapper.getInfoById(id);
    }

    /**
     * 导出个检销售统计
     *
     * @param param
     * @return
     */
    @Override
    public List getExportData(ISPageParam param) {
        param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
        //勾选一条点击导出
        List<ExportOneVo> list = individualStatisticsMapper.getExportData(param);
        for (ExportOneVo vo : list) {
            Double yj = vo.getYj();
            //折扣 = 实收 / 原价
            Double zk = yj == 0 ? null : new Double(String.format("%.1f",
                    Double.parseDouble(String.valueOf(vo.getSs() == null ? 0 : vo.getSs())) /
                            Double.parseDouble(String.valueOf(vo.getYj() == null ? 1 : vo.getYj()))));
            vo.setZk(zk);
        }
        return list;
    }


    /**
     * 导出多条
     *
     * @param param
     * @return
     */
    @Override
    public List<ISPageVo> getExportsData(ISPageParam param) {
        boolean hasks = ObjectUtils.isNotEmpty(param.getType()) || StringUtils.isNotEmpty(param.getKsid());
        param.setHasks(hasks ? 1 : 0);
        if (ObjectUtils.isNotEmpty(param.getIsContainNuclein()) && param.getIsContainNuclein() == 0){
            param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
        }
        if (ObjectUtils.isNotEmpty(param.getIsContainVaccine()) && param.getIsContainVaccine() == 0){
            param.setYmks("ff8080817bc96399017bd2c73a0c5e96");
        }
        Boolean caiwu = SecurityUtils.hasRole(RoleAuthName.CAIWU);
        List<ISPageVo> list = individualStatisticsMapper.getExportsData(param);
        for (ISPageVo vo : list) {
            Double yjhj = vo.getYjhj();
            Double sshj = ObjectUtils.isEmpty(vo.getSshj()) ? 0.0 : vo.getSshj();
            //折扣（实收合计/原价合计）
            vo.setZk(String.format("%.2f", sshj / yjhj));
            //客单价（实收合计/人数）
            vo.setKdj(String.format("%.2f", sshj / vo.getCount()));
            //没有财务权限隐藏成本价
            if (!caiwu){
                vo.setCostprice(null);
            }
        }
        return list;
    }


    /**
     * 获取关联的数据
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ISListDataVo> getListData(PageParam<ISListDataVo> page, ISListDataParam param) {
        param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
        IPage<ISListDataVo> iPage = individualStatisticsMapper.getListData(page, param);
        List<ISListDataVo> records = iPage.getRecords();
        for (ISListDataVo vo : records) {
            //折扣率
            String zk = "0";
            if (0 != vo.getYj()) {
                zk = String.format("%.1f", vo.getSs()/vo.getYj());
            }
            vo.setZk(zk);
        }
        iPage.setRecords(records);
        return iPage;
    }

    /**
     * 导出右侧关联数据
     * @param param
     * @return
     */
    @Override
    public List<ISListDataVo> exportListData(ISListDataParam param) {
        param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
        List<ISListDataVo> list = individualStatisticsMapper.exportListData(param);
        for (ISListDataVo vo : list) {
            //折扣率
            String zk = "0";
            if (0 != vo.getYj()) {
                zk = String.format("%.1f", vo.getSs()/vo.getYj());
            }
            vo.setZk(zk);
        }
        return list;
    }
}

