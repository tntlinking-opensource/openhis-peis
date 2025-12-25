package com.center.medical.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.member.bean.param.ELSaOrUpParam;
import com.center.medical.member.bean.param.ElReportParam;
import com.center.medical.member.bean.param.SaveMergeParam;
import com.center.medical.member.bean.vo.ElReportVo;
import com.center.medical.member.bean.vo.EleInfoListVo;
import com.center.medical.member.dao.ElectronicReportMapper;
import com.center.medical.member.service.ElectronicReportService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientarchiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 体检者档案表(Peispatientarchive)表服务实现类
 *
 * @author ay
 * @since 2023-02-27 11:31:30
 */
@Slf4j
@Service("electronicReportService")
@RequiredArgsConstructor
public class ElectronicReportServiceImpl extends ServiceImpl<ElectronicReportMapper, Peispatientarchive> implements ElectronicReportService {

    private final ElectronicReportMapper electronicReportMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final PeispatientService peispatientService;
    private final PeispatientarchiveService peispatientarchiveService;

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ElReportVo> getList(PageParam<ElReportVo> page, ElReportParam param) {
        return electronicReportMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientarchive getInfoById(String id) {
        return electronicReportMapper.getInfoById(id);
    }




    /**
     * 如果存在关联了app的档案，优先保留关联app的档案，否则保留最新的一条
     * 用户在合并时，选择要保留的姓名、电话、身份证号
     * 其他设置成isDelete=1，仅在查看报告时有用
     * 由于本页面不会显示已被合并假删的档案，所以在绑定线上档案时不能绑定到已假删的档案
     * 如果有两条或以上关联了app的档案被合并，也不允许。关联了app的档案必须被保留。
     * 但是存在旧手机号已注册app，换新手机号后，不能登录旧手机号修改电话，新手机号也注册app。
     * 如果出现有两条或以上关联了app的档案被合并，就让用户选择一条
     * ids:所有选择的app的档案id
     * id：绑定了app的档案id，如果有多条，就是用户选择的档案id
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveMerge(SaveMergeParam param) {
        List<String> ids = param.getIds();
        //体检者档案表
        List<Peispatientarchive> archives = peispatientarchiveMapper.selectList(new QueryWrapper<Peispatientarchive>()
                .orderByDesc("createdate").in("id", ids));
        //取出属性
        String patientname = param.getPatientname();
        String phone = param.getPhone();
        String idcardno = param.getIdcardno();
        String id = param.getId();

        String oldPatientname = "";
        String oldPhone = "";
        String oldIdcardno = "";
        if (StringUtils.isNotEmpty(id)) {
            Peispatientarchive archive = peispatientarchiveMapper.getInfoById(id);
            //是否删除
            if (archive.getIsDelete() != null && archive.getIsDelete() == 1) {
                throw new ServiceException("数据已改变，请刷新重试！");
            }
            oldPatientname = archive.getPatientname();
            oldPhone = archive.getPhone();
            oldIdcardno = archive.getIdcardno();
            archive.setPatientname(patientname);
            archive.setPhone(phone);
            archive.setIdcardno(idcardno);
            //遍历
            for (Peispatientarchive a : archives) {
                if (a.getIsDelete() != null && a.getIsDelete() == 1) {
                    throw new ServiceException("数据已改变，请刷新重试！");
                }
                //重复跳过
                if (id.equals(a.getId())) continue;
                //更新体检者表
                Peispatient peispatient = new Peispatient();
                peispatient.setPatientarchiveno(archive.getPatientarchiveno());
                peispatientService.update(peispatient,
                        new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientarchiveno,a.getPatientarchiveno()));
                a.setIsDelete(1);
            }
            //更新档案表
            peispatientarchiveService.updateBatchById(archives);
        } else {
            //体检者档案表
            Peispatientarchive archive = archives.get(0);
            oldPatientname = archive.getPatientname();
            oldPhone = archive.getPhone();
            oldIdcardno = archive.getIdcardno();
            archive.setPatientname(patientname);
            archive.setPhone(phone);
            archive.setIdcardno(idcardno);
            id = archive.getId();
            for (int i = 0; i < archives.size(); i++) {
                Peispatientarchive a = archives.get(i);
                //是否删除
                if (a.getIsDelete() != null && a.getIsDelete() == 1) {
                    throw new ServiceException("数据已改变，请刷新重试！");
                }
                if (i > 0) {
                    //更新体检者数据
                    Peispatient peispatient = new Peispatient();
                    peispatient.setPatientarchiveno(archive.getPatientarchiveno());
                    peispatientService.update(peispatient,
                            new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientarchiveno,a.getPatientarchiveno()));
                    a.setIsDelete(1);
                }
            }
            //更新档案表
            peispatientarchiveService.updateBatchById(archives);
        }
        return Boolean.TRUE;
        // TODO: 2023/2/27  记录日志
        //记录日志
//        executeSave(new OperateLog(OperateLog.SAVE_MERGE,"参与合并档案ids:"+ids+";合并至档案id:"+id
//                +";patientname:"+patientname+";phone:"+phone+";idcardno:"+idcardno
//                +";原patientname:"+oldPatientname+";原phone:"+oldPhone+";原idcardno:"+oldIdcardno+";"));
    }

    /**
     * 编辑-保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(ELSaOrUpParam param) {

        String id = param.getId();
        String patientname = param.getPatientname();
        String phone = param.getPhone();
        if (StringUtils.isEmpty(patientname)) {
            throw new ServiceException("姓名不能为空！");
        }
        if (StringUtils.isEmpty(phone)) {
            throw new ServiceException("电话不能为空！");
        }
        String oldPatientname = "";
        String oldPhone = "";
        //档案表
        Peispatientarchive archive = peispatientarchiveMapper.getInfoById(id);
        oldPatientname = archive.getPatientname();
        oldPhone = archive.getPhone();
        archive.setPatientname(patientname);
        archive.setPhone(phone);
        peispatientarchiveMapper.updateById(archive);
        return Boolean.TRUE;

        // TODO: 2023/2/27  记录日志
//        //记录日志
//        executeSave(new OperateLog(OperateLog.EDIT_MERGE
//                ,"档案id:"+id+";姓名："+oldPatientname+"->"+patientname
//                +";电话:"+oldPhone+"->"+phone+";")
//        );
    }

    /**
     * 档案下体检者明细数据
     * @param page
     * @param patientarchiveno
     * @return
     */
    @Override
    public IPage<EleInfoListVo> getEleInfoListData(PageParam<EleInfoListVo> page, String patientarchiveno) {
        return electronicReportMapper.getEleInfoListData(page,patientarchiveno);
    }
}

