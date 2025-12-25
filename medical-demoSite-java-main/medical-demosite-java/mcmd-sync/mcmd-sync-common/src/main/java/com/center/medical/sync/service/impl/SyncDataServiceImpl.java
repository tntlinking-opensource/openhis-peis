package com.center.medical.sync.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.service.CodeCorrespondingService;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.sync.bean.param.GenerateCodeParam;
import com.center.medical.sync.dao.SyncDataMapper;
import com.center.medical.sync.service.SyncDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步数据操作(SyncData)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
@Slf4j
@Service("syncLcDataService")
@RequiredArgsConstructor
public class SyncDataServiceImpl extends ServiceImpl<SyncDataMapper, SyncData> implements SyncDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final SyncDataMapper syncDataMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final CodeCorrespondingService codeCorrespondingService;

    /**
     * 分页查询[同步数据操作]列表
     *
     * @param page  分页参数
     * @param param SyncData查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SyncData> getPage(PageParam<SyncData> page, SyncData param) {
        return syncDataMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SyncData getInfoById(Long id) {
        return syncDataMapper.getInfoById(id);
    }


    /**
     * 定时任务生成体检号
     *
     * @param param
     * @return
     */
    @Override
    public List<String> generateCode(GenerateCodeParam param) {
        List<String> list = new ArrayList<>();
        //生成体检号
        for (int i = 0; i < param.getCount(); i++) {
            String patientCode = "";
            do {
                patientCode = CodeUtil.generateCode(param.getJm(), param.getVersion());
                //判断体检号是否存在
            } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getPatientcode, patientCode)) > 0);
            if (isExistByPatientCode(patientCode)) {
                throw new ServiceException("保存失败：生成体检号失败！");
            }
            list.add(patientCode);
        }
        return list;
    }


    /**
     * @param patientCode
     * @return boolean
     * @Title: 判断体检号是否存在
     * @author zhanghj
     * @since 2016-8-11 V 1.0
     */
    private boolean isExistByPatientCode(String patientCode) {
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        return null != peispatient;
    }


    /**
     * 定时任务生成档案号
     * @param param
     * @return
     */
    @Override
    public List<String> generateArchiveCode(GenerateCodeParam param) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < param.getCount(); i++) {
            String archivNo = "";
            do {
                archivNo = CodeUtil.getArchiveCode(param.getJm(), param.getVersion());
                //判断档案号是否存在
            } while (peispatientarchiveMapper.selectCount(new LambdaQueryWrapper<Peispatientarchive>()
                    .eq(Peispatientarchive::getPatientarchiveno, archivNo)) > 0);
            list.add(archivNo);
        }
        return list;
    }
}

