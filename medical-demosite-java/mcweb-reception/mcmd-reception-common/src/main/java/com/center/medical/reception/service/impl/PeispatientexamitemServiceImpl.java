package com.center.medical.reception.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.reception.bean.param.DIGriddataParam;
import com.center.medical.reception.bean.param.DivisionInspectionParam;
import com.center.medical.reception.dao.PeispatientexamitemMapper;
import com.center.medical.reception.service.PeispatientexamitemService;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * LIS结果(LisPacs数据)(Peispatientexamitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:50
 */
@Slf4j
@Service("peispatientexamitemService")
@RequiredArgsConstructor
public class PeispatientexamitemServiceImpl extends ServiceImpl<PeispatientexamitemMapper, Peispatientexamitem> implements PeispatientexamitemService {

    private final PeispatientexamitemMapper peispatientexamitemMapper;
    private final SysDeptMapper sysDeptMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;

    /**
     * 分页查询[LIS结果(LisPacs数据)]列表
     *
     * @param page  分页参数
     * @param param Peispatientexamitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatientexamitem> getList(PageParam<Peispatientexamitem> page, Peispatientexamitem param) {
        return peispatientexamitemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Peispatientexamitem getInfoById(String id) {
        return peispatientexamitemMapper.getInfoById(id);
    }


    /**
     * 检验科结果分页查询
     *
     * @param divisionInspectionParam
     * @return
     */
    @Override
    public IPage<Peispatientexamitem> searchDivision(PageParam<Peispatientexamitem> page, DivisionInspectionParam divisionInspectionParam) {
        return peispatientexamitemMapper.searchDivision(page, divisionInspectionParam);
    }


    /**
     * 设置艾迪康代码,获取检验科体检者收费项目列表数据
     *
     * @param patientcode
     * @return
     */
    @Override
    public List<Peispatientfeeitem> getAdiconGridData(String patientcode) {
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientcode).eq("id_ks", "19")
                .eq("f_feecharged", 1).isNull("f_transferedhl7")
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0));

        return feeitems;
    }

    /**
     * 获取收费项目表格数据
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<Peispatientexamitem> getgriddata(PageParam<Peispatientexamitem> page, DIGriddataParam param) {
        if (StringUtils.isEmpty(param.getKsId())) {
            SysDept dept = sysDeptMapper.selectDeptByName("检验科");
            param.setKsId(String.valueOf(dept.getDeptNo()));
        }
        return peispatientexamitemMapper.getgriddata(page, param);
    }
}

