package com.center.medical.pacslis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PacsItems;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PacsItemMapper;
import com.center.medical.pacslis.bean.model.PacsPeispatientfeeitem;
import com.center.medical.pacslis.dao.PacsPeispatientfeeitemMapper;
import com.center.medical.pacslis.service.PacsPeispatientfeeitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PACS-体检者收费项目表(PacsPeispatientfeeitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:20
 */
@Slf4j
@Service("pacsPeispatientfeeitemService")
@RequiredArgsConstructor
public class PacsPeispatientfeeitemServiceImpl extends ServiceImpl<PacsPeispatientfeeitemMapper, PacsPeispatientfeeitem> implements PacsPeispatientfeeitemService {

    private final PacsPeispatientfeeitemMapper pacsPeispatientfeeitemMapper;
    private final PacsItemMapper pacsItemMapper;

    /**
     * 分页查询[PACS-体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsPeispatientfeeitem> getList(PageParam<PacsPeispatientfeeitem> page, PacsPeispatientfeeitem param) {
        return pacsPeispatientfeeitemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsPeispatientfeeitem getInfoById(String id) {
        return pacsPeispatientfeeitemMapper.getInfoById(id);
    }


    /**
     * 获取PACS登记信息查询右侧收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    @Override
    public List<PacsPeispatientfeeitem> getExamfeeByPatientCode(String patientCode, String type) {
        QueryWrapper<PacsPeispatientfeeitem> queryWrapper = new QueryWrapper<>();
        if ("0".equals(type)) {
            // 全部显示
            queryWrapper.orderByAsc("createDate");
        } else if ("1".equals(type)) {
            // 显示除去退项的
            queryWrapper.eq("changeItem", 0);
            queryWrapper.orderByAsc("createDate");
        } else if ("2".equals(type)) {
            // 显示退项的
            queryWrapper.eq("changeItem", 1);
            queryWrapper.orderByAsc("modifyDate");
        }
        List<PacsPeispatientfeeitem> list = pacsPeispatientfeeitemMapper.selectList(queryWrapper.eq("id_patient", patientCode));
        for (PacsPeispatientfeeitem pacsPeispatientfeeitem : list) {
            //部位IDs
            PacsItems it = pacsItemMapper.getInfoById(pacsPeispatientfeeitem.getIdExamfeeitem());
            pacsPeispatientfeeitem.setExamfeeitemCodehm(it.getExamfeeitemCodehm());
        }


        return list;
    }
}

