package com.center.medical.pacs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.Base64ToMultipartFile;
import com.center.medical.common.utils.file.MultipartFileUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.pacs.bean.dto.PacsAbteilungPatientListDto;
import com.center.medical.pacs.bean.param.*;
import com.center.medical.pacs.bean.vo.*;
import com.center.medical.pacs.dao.PacsAbteilungMapper;
import com.center.medical.pacs.service.PacsAbteilungService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.dao.ComboexamitemMapper;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author xhp
 * @since 2023-03-15 9:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PacsAbteilungServiceImpl extends ServiceImpl<PacsAbteilungMapper, PacsResult> implements PacsAbteilungService {
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PacsResultMapper pacsResultMapper;
    private final SysUserMapper sysUserMapper;
    private final SysDeptMapper sysDeptMapper;
    private final ISysConfigService iSysConfigService;
    private final AttachmentMapper attachmentMapper;
    private final AttachmentConfigMapper attachmentConfigMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final ItemsMapper itemsMapper;
    private final PeispatientMapper peispatientMapper;
    private final PacsSectionResultTwoMapper pacsSectionResultTwoMapper;
    private final PacsBasexamltemSignMapper pacsBasexamltemSignMapper;
    private final PacsItemMapper pacsItemMapper;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final ComboexamitemMapper comboexamitemMapper;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final ISysDeptService iSysDeptService;
    private final ISysBranchService iSysBranchService;
    private final PeispatientfeeitemService peispatientfeeitemService;

    private final PeispatientarchiveMapper peispatientarchiveMapper;

    @Autowired
    private LoadProperties loadProperties;

    @Override
    public IPage<PacsAbteilungPatientListVo> getPatientList(PageParam page, PatientListParam patientListParam) {
        IPage<PacsAbteilungPatientListDto> patientListDtoIPage = baseMapper.getPatientList(page, patientListParam);
        IPage<PacsAbteilungPatientListVo> pageList = patientListDtoIPage.convert(
                pacsAbteilungPatientListDto -> BeanUtil.toBean(pacsAbteilungPatientListDto, PacsAbteilungPatientListVo.class)
        );
//        if ("24".equals(patientListParam.getDeptNo())) {
//            //获取DR的图片地址
//            for (PacsAbteilungPatientListVo record : pageList.getRecords()) {
//                List<Attachment> list = attachmentService.list(new LambdaQueryWrapper<Attachment>().select(Attachment::getDcmPath).eq(Attachment::getPatientcode, record.getPatientcode()).isNotNull(Attachment::getDcmPath));
//                record.setDicomImgs(list.stream().map(item -> {
//                    return StringUtils.substringBefore(item.getDcmPath(), ",");
//                }).collect(Collectors.joining(",")));
//                record.setImg(list.size());
//                record.setJcstatus(CollectionUtil.isEmpty(list)?0:1);
//            }
//        }
        //添加序号
        int current = page.getCurrent() == 0 ? 0 : Math.toIntExact(page.getSize() * (page.getCurrent() - 1));
        int i = 1;
        for (PacsAbteilungPatientListVo record : pageList.getRecords()) {
            record.setRownum(current + i);
            i++;
        }
        return pageList;
    }

    @Override
    public List<PacsAbteilungItemListVo> getItemList(String patientcode, String deptNo) {
        return baseMapper.getItemList(patientcode, deptNo);
    }

    @Override
    public List<PacsAbteilungSignListVo> getSignList(String patientcode, String itemId) {
        return baseMapper.getSignList(patientcode, itemId);
    }

    @Override
    public List<PacsAbteilungAbteilunListVo> getAbteilunList(String patientcode, String userNo) {
        return baseMapper.getAbteilunList(patientcode, userNo);
    }

    @Override
    public IPage<PacsAbteilungHistoryListVo> getHistoryList(PageParam page, String patientcode, String deptNo,String describe) {
        //2025.9.4 要求查询所有科室的数据,将deptNo参数设为null
        deptNo = null;
        if (iSysConfigService.oldSystemOpen()) {
            List<PacsAbteilungHistoryListVo> historyList1 = baseMapper.getHistoryListMysql(patientcode,deptNo,describe);
            //通过身份证查询老系统档案id
            Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
            List<String> id = peispatientarchiveMapper.getOldInfoByIdCardNo(peispatient.getIdcardno());
            //通过档案查询oracle数据
            //查询oracle数据
            List<PacsAbteilungHistoryListVo> historyList2 = baseMapper.getHistoryListOracle(patientcode,id,describe,peispatient.getIdcardno(),deptNo);
            historyList1.addAll(historyList2);
            //查询oracle历史数据
//                List<PacsAbteilungHistoryListVo> historyList3 = baseMapper.getHistoryListOracleHis(patientcode,id,describe,peispatient.getIdcardno());
//                historyList1.addAll(historyList3);
            // 使用Collections.sort()方法进行排序
            Comparator<PacsAbteilungHistoryListVo> comparator = new Comparator<PacsAbteilungHistoryListVo>() {
                @Override
                public int compare(PacsAbteilungHistoryListVo obj1, PacsAbteilungHistoryListVo obj2) {
                    // 处理空值情况
                    if (obj1.getDateregister() == null && obj2.getDateregister() == null) {
                        return 0;
                    } else if (obj1.getDateregister() == null) {
                        return 1;
                    } else if (obj2.getDateregister() == null) {
                        return -1;
                    }
                    // 默认比较规则
                    return obj2.getDateregister().compareTo(obj1.getDateregister());
                }
            };
            Collections.sort(historyList1, comparator);
            //设置分页返回数据
            Page pages = getPages((int) page.getCurrent(), (int) page.getSize(), historyList1);
            return pages;
        } else {
            IPage<PacsAbteilungHistoryListVo> iPage = baseMapper.getHistoryListAll(page, patientcode, deptNo);
            return iPage;
        }
    }

    /**
     * 手动分页
     *
     * @param currentPage
     * @param pageSize
     * @param list
     * @return
     */
    private Page getPages(Integer currentPage, Integer pageSize, List list) {
        Page page = new Page();
        if (list == null) {
            return null;
        }
        int size = list.size();

        if (pageSize > size) {
            pageSize = size;
        }
        if (pageSize != 0) {
            // 求出最大页数，防止currentPage越界
            int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;

            if (currentPage > maxPage) {
                currentPage = maxPage;
            }
        }
        // 当前页第一条数据的下标
        int curIdx = currentPage > 1 ? (currentPage - 1) * pageSize : 0;

        List pageList = new ArrayList();

        // 将当前页的数据放进pageList
        for (int i = 0; i < pageSize && curIdx + i < size; i++) {
            pageList.add(list.get(curIdx + i));
        }

        page.setCurrent(currentPage).setSize(pageSize).setTotal(list.size()).setRecords(pageList);
        return page;
    }


    @Override
    public PacsAbteilungItemSearchVo search(String feeitemId) {
        PacsAbteilungItemSearchVo pacsAbteilungItemSearchVo = new PacsAbteilungItemSearchVo();
        Peispatientfeeitem peispatientfeeitem = peispatientfeeitemMapper.selectById(feeitemId);
        String patientcode = peispatientfeeitem.getIdPatient();
        String itemid = peispatientfeeitem.getIdExamfeeitem();//基础收费项目id
        PacsResult pacsResult = pacsResultMapper.selectOne(
                new QueryWrapper<PacsResult>()
                        .eq("patientcode", patientcode)
                        .eq("item_id", itemid)
        );
        String deptNo = peispatientfeeitem.getIdKs();
        SysDept sysDept = sysDeptMapper.getByDeptNo(deptNo);

        //检查结果，保存过才会有
        PacsAbteilungItemSearchMainVo pacsAbteilungItemSearchMainVo = new PacsAbteilungItemSearchMainVo();
        pacsAbteilungItemSearchVo.setPacsAbteilungItemSearchMainVo(pacsAbteilungItemSearchMainVo);
        if (pacsResult != null) {
            pacsAbteilungItemSearchMainVo.setConclusions(pacsResult.getExamresultsummary());
            pacsAbteilungItemSearchMainVo.setDescription(pacsResult.getExamresultdesc());
            String rummager = pacsResult.getExamdoctor();
            pacsAbteilungItemSearchMainVo.setRummager(rummager);
            if (StrUtil.isNotEmpty(rummager)) {
                SysUser sysUser = sysUserMapper.selectUserByUserName(rummager);
                if (sysUser != null) {
                    pacsAbteilungItemSearchMainVo.setRummagerId(sysUser.getUserNo());
                }
            }
            pacsAbteilungItemSearchMainVo.setRummagerTime(pacsResult.getExamdatetime());
            String writer = pacsResult.getAuditDoctor();
            pacsAbteilungItemSearchMainVo.setWriter(writer);
            if (StrUtil.isNotEmpty(writer)) {
                SysUser sysUser = sysUserMapper.selectUserByUserName(writer);
                if (sysUser != null) {
                    pacsAbteilungItemSearchMainVo.setWriteId(sysUser.getUserNo());
                }
            }
            pacsAbteilungItemSearchMainVo.setWriteTime(pacsResult.getWriteDate());
            pacsAbteilungItemSearchMainVo.setIsAudit(peispatientfeeitem.getFExaminated());
        }

        //图片
        String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
        String imgConf = "0";//图片显示方式
        boolean sortPacsPath = false;//是否需要调整排序方式
        String visitPath = null;//路径配置，优先级别最高
        String mappingPath = null;
        if (StrUtil.isNotEmpty(pacsConfig)) {
            JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
            String imageType = jsonObject.getStr("imageType");
            if (StrUtil.isNotEmpty(imageType)) {
                imgConf = imageType;
            }
            sortPacsPath = "1".equals(jsonObject.getStr("sortPacsPath"));
            visitPath = jsonObject.getStr("visitPath");
            mappingPath = jsonObject.getStr("mappingPath");
        }
        List<PacsAbteilungItemSearchImgVo> imgs = new ArrayList<>();
        pacsAbteilungItemSearchVo.setImgs(imgs);
        boolean isCC = getIsCc(sysDept);//是否是彩超科室
        if ("0".equals(imgConf) || !isCC) {
            List<Attachment> atts = null;
            if (isCC) {
                atts = attachmentMapper.selectList(
                        new QueryWrapper<Attachment>()
                                .orderByAsc("file_path")
                                .eq("patientcode", patientcode)
                                .eq("dep_id", deptNo)
                                .eq("pacs_fee_item_id", feeitemId)
                );
            } else {
                atts = attachmentMapper.selectList(
                        new QueryWrapper<Attachment>()
                                .orderByDesc("file_path")
                                .eq("patientcode", patientcode)
                                .eq("dep_id", deptNo)
                                .eq("pacs_fee_item_id", feeitemId)
                );
            }
            if (atts.size() > 0) {
                if (sortPacsPath) sortHuainan(atts, isCC);
                for (int i = 0; i < atts.size(); i++) {
                    Attachment att = atts.get(i);
                    //可能不是一个config（登记一段时间后回来重新拍）
                    AttachmentConfig pac = attachmentConfigMapper.selectById(att.getConfigId());
                    PacsAbteilungItemSearchImgVo pacsAbteilungItemSearchImgVo = new PacsAbteilungItemSearchImgVo();
                    pacsAbteilungItemSearchImgVo.setId(att.getId());
                    if (StrUtil.isNotEmpty(visitPath) && StrUtil.isNotEmpty(mappingPath)) {
                        pacsAbteilungItemSearchImgVo.setSrc(visitPath + att.getFilePath());
                        pacsAbteilungItemSearchImgVo.setDcmsrc(visitPath + att.getDcmPath());
                        pacsAbteilungItemSearchImgVo.setPath(mappingPath + att.getFilePath());
                    } else {
                        if (pac != null) {
                            pacsAbteilungItemSearchImgVo.setSrc(pac.getVisitPath() + att.getFilePath());
                            pacsAbteilungItemSearchImgVo.setDcmsrc(pac.getVisitPath() + att.getDcmPath());
                            pacsAbteilungItemSearchImgVo.setPath(pac.getMappingPath() + att.getFilePath());
                        }
                    }
                    pacsAbteilungItemSearchImgVo.setInReport(att.getInReport() == null ? 0 : att.getInReport());
                    imgs.add(pacsAbteilungItemSearchImgVo);
                }
            }
        } else if ("1".equals(imgConf)) {
            //选择项目时显示本科室本体号所有图片，选中进报告的关联到当前项目上
            List<Attachment> atts = null;
            if (isCC) {
                atts = attachmentMapper.selectList(
                        new QueryWrapper<Attachment>()
                                .orderByAsc("file_path")
                                .eq("patientcode", patientcode)
                                .eq("dep_id", deptNo)
                );
            } else {
                atts = attachmentMapper.selectList(
                        new QueryWrapper<Attachment>()
                                .orderByDesc("file_path")
                                .eq("patientcode", patientcode)
                                .eq("dep_id", deptNo)
                );
            }
            if (atts.size() > 0) {
                if (sortPacsPath) sortHuainan(atts, isCC);
                for (int i = 0; i < atts.size(); i++) {
                    Attachment att = atts.get(i);
                    AttachmentConfig pac = attachmentConfigMapper.selectById(att.getConfigId());
                    PacsAbteilungItemSearchImgVo pacsAbteilungItemSearchImgVo = new PacsAbteilungItemSearchImgVo();
                    pacsAbteilungItemSearchImgVo.setId(att.getId());
                    if (StrUtil.isNotEmpty(visitPath) && StrUtil.isNotEmpty(mappingPath)) {
                        pacsAbteilungItemSearchImgVo.setSrc(visitPath + att.getFilePath());
                        pacsAbteilungItemSearchImgVo.setDcmsrc(visitPath + att.getDcmPath());
                        pacsAbteilungItemSearchImgVo.setPath(mappingPath + att.getFilePath());
                    } else {
                        if (pac != null) {
                            pacsAbteilungItemSearchImgVo.setSrc(pac.getVisitPath() + att.getFilePath());
                            pacsAbteilungItemSearchImgVo.setDcmsrc(pac.getVisitPath() + att.getDcmPath());
                            pacsAbteilungItemSearchImgVo.setPath(pac.getMappingPath() + att.getFilePath());
                        }
                    }
                    //这个项目进报告的图片显示打钩
                    pacsAbteilungItemSearchImgVo.setInReport((feeitemId.equals(att.getPacsFeeItemId())
                            && att.getInReport() != null
                            && att.getInReport().intValue() == 1) ? 1 : 0);
                    imgs.add(pacsAbteilungItemSearchImgVo);
                }
            }
        }

        //历史
        PacsAbteilungItemSearchHistoryVo pacsAbteilungItemSearchHistoryVo = new PacsAbteilungItemSearchHistoryVo();
        pacsAbteilungItemSearchVo.setPacsAbteilungItemSearchHistoryVo(pacsAbteilungItemSearchHistoryVo);
        PageParam page = new PageParam();
        page.setSize(1);
        IPage<PacsAbteilungHistoryListVo> iPage = baseMapper.getHistoryList(page, patientcode, deptNo);
        List<PacsAbteilungHistoryListVo> pacsAbteilungHistoryListVos = iPage.getRecords();
        if (pacsAbteilungHistoryListVos.size() > 0) {
            PacsAbteilungHistoryListVo pacsAbteilungHistoryListVo = pacsAbteilungHistoryListVos.get(0);
            pacsAbteilungItemSearchHistoryVo.setConclusions(pacsAbteilungHistoryListVo.getConclusions());
            pacsAbteilungItemSearchHistoryVo.setDescription(pacsAbteilungHistoryListVo.getDescription());
        }else {
            //查询老系统
            if (iSysConfigService.oldSystemOpen()){
                //查询老系统历史数据
                Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
                PacsAbteilungHistoryListVo vo = baseMapper.getHistorySummary(peispatient.getIdcardno(),deptNo);
                if (ObjectUtils.isNotEmpty(vo)){
                    pacsAbteilungItemSearchHistoryVo.setConclusions(vo.getConclusions());
                    pacsAbteilungItemSearchHistoryVo.setDescription(vo.getDescription());
                }
            }
        }

        return pacsAbteilungItemSearchVo;
    }

    /**
     * 淮南中心图片名称按字符串排序会有问题，要同时满足淮南新的和旧的CT设备生成的图片名正常排序。
     */
    void sortHuainan(List<Attachment> pacsAttachments, final boolean asc) {
        Collections.sort(pacsAttachments, new Comparator<Attachment>() {
            @Override
            public int compare(Attachment o1, Attachment o2) {
                String path1 = o1.getFilePath();
                String path2 = o2.getFilePath();
                return asc ? fillAll(path1).compareTo(fillAll(path2)) : fillAll(path2).compareTo(fillAll(path1));
            }

            public String fill(String str) {
                int l = 40;
                int length = str.length();
                if (l <= length) return str;
                StringBuilder sb = new StringBuilder();
                int z = l - length;
                for (int i = 0; i < z; i++) {
                    sb.append("0");
                }
                sb.append(str);
                return sb.toString();
            }

            public String fillAll(String str) {
                String[] arr = str.split("\\.");
                StringBuilder sb = new StringBuilder();
                for (String s : arr) {
                    sb.append(fill(s));
                }
                return sb.toString();
            }
        });
    }

    @Override
    @Transactional
    public void saveOrUpdate(PacsAbteilungSaveParam pacsAbteilungSaveParam) {
        log.info("影像科室审核参数:{}",pacsAbteilungSaveParam);
        String feeitemId = pacsAbteilungSaveParam.getFeeitemId();
        Peispatientfeeitem peispatientfeeitem = peispatientfeeitemMapper.selectById(feeitemId);
        String patientcode = peispatientfeeitem.getIdPatient();
        check(patientcode);
        String deptNo = peispatientfeeitem.getIdKs();
        String itemId = peispatientfeeitem.getIdExamfeeitem();
        Integer shortCode = peispatientfeeitem.getShortCode();
        Items items = itemsMapper.selectById(itemId);
        String examfeeitemCode = items.getExamfeeitemCode();
        PacsItems pacsItems = pacsItemMapper.selectOne(
                new QueryWrapper<PacsItems>()
                        .eq("examfeeitem_code", examfeeitemCode)
                        .eq("is_delete", 0)
        );
        String pacsItemsId = pacsItems.getId();
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
        String currentUserNo = SecurityUtils.getUserNo();
        String currentUsername = sysUserMapper.getUserByNo(currentUserNo).getUserName();
        SysDept sysDept = sysDeptMapper.getByDeptNo(deptNo);
        boolean isCc = getIsCc(sysDept);

        //pacsresult
        PacsResult pacsResultParam = new PacsResult();
        pacsResultParam.setDepId(pacsAbteilungSaveParam.getDeptNo());
        pacsResultParam.setShortCode(shortCode);
        pacsResultParam.setItemName(items.getExamfeeitemNameprn());
        pacsResultParam.setItemId(itemId);
        pacsResultParam.setPatientname(peispatient.getPatientname());
        String writeId = pacsAbteilungSaveParam.getWriteId();
        SysUser sysUserWriter = sysUserMapper.getUserByNo(writeId);
        pacsResultParam.setAuditDoctor(sysUserWriter.getUserName());
        pacsResultParam.setPatientcode(patientcode);
        pacsResultParam.setExamdatetime(pacsAbteilungSaveParam.getRummagerTime());
        pacsResultParam.setExamdoctor(pacsAbteilungSaveParam.getRummagerName());
        pacsResultParam.setExamfeeitemCode(items.getExamfeeitemCode());
        pacsResultParam.setExamresultdesc(pacsAbteilungSaveParam.getDescription());
        pacsResultParam.setExamresultsummary(pacsAbteilungSaveParam.getConclusions());
        pacsResultParam.setExamresultisnormal("0");
        pacsResultParam.setFResulttransfered(0);
        pacsResultParam.setIsNewPacs(1);
        pacsResultParam.setPacsItemId(pacsItemsId);
        pacsResultParam.setWriteDate(pacsAbteilungSaveParam.getWriteTime());
        pacsResultParam.setUsername(currentUsername);
        PacsResult pacsResult = pacsResultMapper.selectOne(
                new QueryWrapper<PacsResult>()
                        .eq("patientcode", patientcode)
                        .eq("item_id", itemId)
        );
        if (ObjectUtils.isEmpty(pacsResult)) {
            pacsResultMapper.insert(pacsResultParam);
        } else {
            pacsResultParam.setId(pacsResult.getId());
            pacsResultMapper.updateById(pacsResultParam);
        }

        //attachment
        if (isCc) {
            String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
            String imgConf = "0";//图片显示方式
            if (StrUtil.isNotEmpty(pacsConfig)) {
                JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
                String imageType = jsonObject.getStr("imageType");
                if (StrUtil.isNotEmpty(imageType)) {
                    imgConf = imageType;
                }
            }
            List<PacsAbteilungSaveImgParam> imgs = pacsAbteilungSaveParam.getImgs();
            if ("0".equals(imgConf)) {
                Set<String> imgids = new HashSet<String>();
                for (PacsAbteilungSaveImgParam pacsAbteilungSaveImgParam : imgs) {
                    String attId = pacsAbteilungSaveImgParam.getId();
                    imgids.add(attId);
                    Attachment attachment = attachmentMapper.selectById(attId);
                    attachment.setInReport(pacsAbteilungSaveImgParam.getInReport());
                    attachmentMapper.updateById(attachment);
                }
                if (imgids.size() > 0) {
                    attachmentMapper.delete(
                            new QueryWrapper<Attachment>()
                                    .eq("patientcode", patientcode)
                                    .eq("dep_id", deptNo)
                                    .eq("pacs_fee_item_id", feeitemId)
                                    .notIn("id", imgids)
                    );
                } else {
                    attachmentMapper.delete(
                            new QueryWrapper<Attachment>()
                                    .eq("patientcode", patientcode)
                                    .eq("dep_id", deptNo)
                                    .eq("pacs_fee_item_id", feeitemId)
                    );
                }
            } else {
                Set<String> imgids = new HashSet<String>();
                for (PacsAbteilungSaveImgParam pacsAbteilungSaveImgParam : imgs) {
                    String attId = pacsAbteilungSaveImgParam.getId();
                    imgids.add(attId);
                    int inReport = pacsAbteilungSaveImgParam.getInReport();
                    Attachment attachment = attachmentMapper.selectById(attId);
                    if (inReport == 1) {
                        attachment.setPacsFeeItemId(feeitemId);
                    } else {
                        if (attachment.getPacsFeeItemId() != null && attachment.getPacsFeeItemId().equals(feeitemId)) {
                            attachment.setPacsFeeItemId(null);
                        }
                    }
                    attachment.setInReport(pacsAbteilungSaveImgParam.getInReport());
                    attachmentMapper.updateById(attachment);
                }
                if (imgids.size() > 0) {
                    attachmentMapper.delete(
                            new QueryWrapper<Attachment>()
                                    .eq("patientcode", patientcode)
                                    .eq("dep_id", deptNo)
                                    .notIn("id", imgids)
                    );
                } else {
                    attachmentMapper.delete(
                            new QueryWrapper<Attachment>()
                                    .eq("patientcode", patientcode)
                                    .eq("dep_id", deptNo)
                    );
                }
            }
        }

        //如果是审核操作，修改状态
        if (pacsAbteilungSaveParam.getType() == 2) {
            //收费项目已检
            peispatientfeeitem.setFExaminated(1);
            peispatientfeeitemMapper.updateById(peispatientfeeitem);

            /**如果是彩超，并且是审核，将所有已保存未审核的项目设为已审核*/
            if (isCc) {
                List<PacsResult> pacsResults = pacsResultMapper.selectList(
                        new QueryWrapper<PacsResult>()
                                .eq("patientcode", patientcode)
                                .eq("dep_id", deptNo)
                                .ne("item_id", itemId)
                );
                for (PacsResult pr : pacsResults) {
                    Peispatientfeeitem pi = peispatientfeeitemMapper.selectOne(
                            new QueryWrapper<Peispatientfeeitem>()
                                    .eq("id_patient", patientcode)
                                    .eq("id_examfeeitem", pr.getItemId())
                                    .eq("change_item", 0)
                                    .eq("f_feecharged", 1)
                                    .eq("f_giveup", 0)
                                    .isNull("f_transferedhl7")
                                    .eq("sfjj", 0)
                    );
                    if (pi != null && pi.getFExaminated() == 0) {
                        pi.setFExaminated(1);
                        pi.setExaminatetime(pacsAbteilungSaveParam.getRummagerTime());
                        peispatientfeeitemMapper.updateById(pi);
                    }
                }
            }

            //判断科室项目是否全部已检
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(
                    new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_ks", deptNo)
                            .eq("change_item", 0)
                            .eq("id_patient", patientcode)
                            .eq("f_giveup", 0)
                            .eq("f_feecharged", 1)
                            .isNull("f_transferedhl7")
                            .eq("f_examinated", 0)
                            .eq("sfjj", 0)
            );
            boolean isAudit = peispatientfeeitems.size() == 0;
            if (isAudit) {
                SectionResultMain sectionResultMain = sectionResultMainMapper.selectOne(
                        new QueryWrapper<SectionResultMain>()
                                .eq("patientcode", patientcode)
                                .eq("dep_id", deptNo)
                );
                if (sectionResultMain == null) {
                    sectionResultMain = new SectionResultMain();
                    sectionResultMain.setPatientcode(patientcode);
                    sectionResultMain.setDepId(deptNo);
                    sectionResultMain.setShortCode(shortCode);
                    sectionResultMainMapper.insert(sectionResultMain);
                }
                String tjlx = peispatient.getIdExamtype();
                StringBuilder[] cons = getConclusions(tjlx, patientcode, deptNo, peispatient.getJhys(), peispatient.getMedicaltype());
                sectionResultMain.setRummagerId(pacsAbteilungSaveParam.getRummagerId());
                sectionResultMain.setRummagerName(pacsAbteilungSaveParam.getRummagerName());
                sectionResultMain.setWriteTime(pacsAbteilungSaveParam.getWriteTime());
                sectionResultMain.setAuditTime(pacsAbteilungSaveParam.getRummagerTime());
                sectionResultMain.setRummagerTime(pacsAbteilungSaveParam.getRummagerTime());
                sectionResultMain.setAuditId(pacsAbteilungSaveParam.getWriteId());
                sectionResultMain.setAuditName(sysUserWriter.getUserName());
                sectionResultMain.setWriteId(writeId);
                sectionResultMain.setConclusions(cons[0].toString());
                sectionResultMain.setIsFinish(0);//未上传
                if ("1".equals(tjlx) || "3".equals(tjlx)) {
                    sectionResultMain.setZyConclusions(sectionResultMain.getConclusions());
                } else if ("2".equals(tjlx)) {
                    sectionResultMain.setZyConclusions(cons[1] == null ? null : cons[1].toString());
                }
                sectionResultMain.setIsAudit(1);
                sectionResultMainMapper.updateById(sectionResultMain);

                //修改分检完成状态
                if (outsideMainService.getAllSfxmtzjStatus(patientcode)) {
                    peispatient.setFReadytofinal(1);//0:已备单 1:分检完成
                    peisStateService.setScbs(patientcode, 0);
                    peispatient.setReadytofinalDate(new Date());
                    List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientcode);
                    for (Peispatientfeeitem other : other_items) {
                        other.setFExaminated(1);//设置未关联科室项目为已检
                    }
                    peispatientfeeitemService.updateBatchById(other_items);
                }
                peispatient.setFExamstarted(1);
                peispatientMapper.updateById(peispatient);

            }
        }

        /**是否职业*/
        Integer tjlx = null;
        String idExamType = peispatient.getIdExamtype();
        if ("2".equals(idExamType)) {//按接害因素、职业体检类型匹配(对接体检软件)
            String jhys = peispatient.getJhys();//接害因素
            String medicaltype = peispatient.getMedicaltype();//职业检查类型
            List<Comboexamitem> eis = comboexamitemMapper.selectList(
                    new QueryWrapper<Comboexamitem>()
                            .in("harm_id", jhys.split(","))
                            .eq("medical_type", medicaltype)
            );
            for (Comboexamitem cei : eis) {
                if (cei.getItemId().equals(itemId)) {
                    tjlx = 1;
                    break;
                }
            }
            if (tjlx == null) {
                tjlx = 0;
            }
        } else if ("0".equals(idExamType)) {
            tjlx = 0;
        } else {
            tjlx = 1;
        }

        //体征词
        pacsSectionResultTwoMapper.delete(
                new QueryWrapper<PacsSectionResultTwo>()
                        .eq("charges_id", pacsItemsId)
                        .eq("patientcode", patientcode)
        );
        List<PacsAbteilungSaveSignParam> pacsAbteilungSaveSignParams = pacsAbteilungSaveParam.getJlcdata();
        for (PacsAbteilungSaveSignParam pacsAbteilungSaveSignParam : pacsAbteilungSaveSignParams) {
            String tzcid = pacsAbteilungSaveSignParam.getTzcid();
            PacsBasexamltemSign basexamltemSign = pacsBasexamltemSignMapper.selectById(tzcid);
            PacsSectionResultTwo sectionResultTwo = new PacsSectionResultTwo();
            sectionResultTwo.setVerdictId(basexamltemSign.getInspectId());
            sectionResultTwo.setNodule(basexamltemSign.getId());
            sectionResultTwo.setPosistive(basexamltemSign.getIsPositive());
            sectionResultTwo.setPatientcode(patientcode);
            sectionResultTwo.setBasconclusionId(basexamltemSign.getResultId());
            sectionResultTwo.setDivisionId(deptNo);
            sectionResultTwo.setChargesId(pacsItemsId);
            sectionResultTwo.setShortCode(shortCode);
            sectionResultTwo.setTjlx(tjlx);
            pacsSectionResultTwoMapper.insert(sectionResultTwo);
        }
    }

    /**
     * 判断是否是彩超科室
     *
     * @return
     */
    boolean getIsCc(SysDept sysDept) {
        return "US".equals(sysDept.getJklx());
    }

    @Override
    @Transactional
    public void reverse(PacsAbteilungReverseParam pacsAbteilungReverseParam) {
        String feeitemId = pacsAbteilungReverseParam.getFeeitemId();
        Peispatientfeeitem peispatientfeeitem = peispatientfeeitemMapper.selectById(feeitemId);
        if (peispatientfeeitem.getFExaminated() == null || peispatientfeeitem.getFExaminated() != 1) {
            throw new ServiceException("反审核失败，项目未审核，不能反审核！");
        }
        String patientcode = peispatientfeeitem.getIdPatient();
        check(patientcode);
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
        String itemId = peispatientfeeitem.getIdExamfeeitem();
        String currentUsername = SecurityUtils.getUsername();
        String deptNo = peispatientfeeitem.getIdKs();
        PacsResult pacsResult = pacsResultMapper.selectOne(
                new QueryWrapper<PacsResult>()
                        .eq("patientcode", patientcode)
                        .eq("item_id", itemId)
        );
        if (StrUtil.isNotEmpty(pacsResult.getAuditDoctor()) && !currentUsername.equals(pacsResult.getAuditDoctor())) {
            throw new ServiceException("只有审核人" + ("【" + pacsResult.getAuditDoctor() + "】") + "可以反审核！");
        }

        peispatientfeeitem.setFExaminated(0);
        peispatientfeeitemMapper.updateById(peispatientfeeitem);

        SectionResultMain sectionResultMain = sectionResultMainMapper.selectOne(
                new QueryWrapper<SectionResultMain>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", deptNo)
        );
        if (sectionResultMain != null) {
            sectionResultMain.setIsAudit(0);
            StringBuilder[] cons = getConclusions(peispatient.getIdExamtype(), patientcode, deptNo, peispatient.getJhys(), peispatient.getMedicaltype());
            sectionResultMain.setIsFinish(0);
            sectionResultMain.setConclusions(cons[0].toString());
            sectionResultMain.setZyConclusions(cons[1] == null ? null : cons[1].toString());
            sectionResultMainMapper.updateById(sectionResultMain);
        }

        peisStateService.setScbs(patientcode, 0);

        peispatient.setFReadytofinal(0);
        peispatientMapper.updateById(peispatient);
    }

    @Override
    public void check(String patientcode) {
        Peispatient tjPatient = peispatientMapper.getByPatientCode(patientcode);
        if (tjPatient == null) {
            throw new ServiceException("该体检号尚未登记！");
        }
        if (tjPatient.getFRegistered() == null || tjPatient.getFRegistered() != 1) {
            throw new ServiceException("该体检号尚未登记！");
        }
        if (tjPatient.getJktjzt() != null && tjPatient.getJktjzt() == 1) {
            throw new ServiceException("本体检者检查结果已被" + (tjPatient.getDoctorfinalNameR() == null ? "" : tjPatient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (tjPatient.getZytjzt() != null && tjPatient.getZytjzt() == 1) {
            throw new ServiceException("本体检者检查结果已被" + (tjPatient.getPatientnameencoded() == null ? "" : tjPatient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (tjPatient.getFFinallocked() != null && tjPatient.getFFinallocked() == 1) {
            throw new ServiceException("本体检者检查结果已被" + (tjPatient.getIdDoctorapply() == null ? "" : tjPatient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (tjPatient.getIdGuidenurse() != null && tjPatient.getIdGuidenurse() == 1) {
            throw new ServiceException("本体检者检查结果已被" + (tjPatient.getParsedassignedsuiteandfi() == null ? "" : tjPatient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (tjPatient.getFPaused() != null && tjPatient.getFPaused().intValue() == 1) {
            throw new ServiceException("该体检号已被禁检！");
        }
    }

    @Override
    public List<AllUserDataVo> getDoctorList(String deptNo, String inputCode) {
        String cId = SecurityUtils.getCId();
        if (ObjectUtils.isNotEmpty(inputCode)) {
            inputCode = inputCode.trim();
        }
        return sysUserMapper.allDoctors(cId, deptNo, inputCode);
    }

    @Override
    public List<PacsAbteilungImgVo> getImgList(String feeitemId) {
        List<PacsAbteilungImgVo> pacsAbteilungImgVos = new ArrayList<>();
        Peispatientfeeitem peispatientfeeitem = peispatientfeeitemMapper.selectById(feeitemId);
        String patientcode = peispatientfeeitem.getIdPatient();
        String deptNo = peispatientfeeitem.getIdKs();
//        String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
//        String visitPath = null;//路径配置，优先级别最高
//        String mappingPath = null;
//        if (StrUtil.isNotEmpty(pacsConfig)) {
//            JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
//            visitPath = jsonObject.getStr("visitPath");
//            mappingPath = jsonObject.getStr("mappingPath");
//        }

        List<Attachment> attachments = attachmentMapper.selectList(
                new QueryWrapper<Attachment>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", deptNo)
                        .eq("pacs_fee_item_id", feeitemId)
                        .orderByAsc("file_sort")
                        .orderByAsc("memo")
        );
        for (Attachment att : attachments) {
//            AttachmentConfig pac = attachmentConfigMapper.selectById(att.getConfigId());
            PacsAbteilungImgVo pacsAbteilungImgVo = new PacsAbteilungImgVo();
            pacsAbteilungImgVo.setId(att.getId());
//            if (StrUtil.isNotEmpty(visitPath) && StrUtil.isNotEmpty(mappingPath)) {
//                pacsAbteilungImgVo.setSrc(visitPath + att.getFilePath());
//                pacsAbteilungImgVo.setDcmsrc(visitPath + att.getDcmPath());
//                pacsAbteilungImgVo.setPath(mappingPath + att.getFilePath());
//            } else {
//                if (pac != null) {
//                    pacsAbteilungImgVo.setSrc(pac.getVisitPath() + att.getFilePath());
//                    pacsAbteilungImgVo.setDcmsrc(pac.getVisitPath() + att.getDcmPath());
//                    pacsAbteilungImgVo.setPath(pac.getMappingPath() + att.getFilePath());
//                }
//            }
            pacsAbteilungImgVo.setSrc(att.getFilePath());
            pacsAbteilungImgVo.setDcmsrc(att.getDcmPath());
            pacsAbteilungImgVo.setPath(att.getFilePath());
            //这个项目进报告的图片显示打钩
            pacsAbteilungImgVo.setInReport((feeitemId.equals(att.getPacsFeeItemId())
                    && att.getInReport() != null
                    && att.getInReport().intValue() == 1) ? 1 : 0);
            pacsAbteilungImgVos.add(pacsAbteilungImgVo);
        }
        return pacsAbteilungImgVos;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public StringBuilder[] getConclusions(String tjlx, String patientcode, String deptNo, String jhys, String medicaltype) {
        HashMap<String, Comboexamitem> ceis = null;
        if ("2".equals(tjlx)) {//按接害因素、职业体检类型匹配
            List<Comboexamitem> eis = comboexamitemMapper.selectList(
                    new QueryWrapper<Comboexamitem>()
                            .eq("medical_type", medicaltype)
                            .in("harm_id", jhys.split(","))
            );
            ceis = new HashMap<String, Comboexamitem>();
            for (Comboexamitem cei : eis) {
                ceis.put(cei.getItemId(), cei);
            }
        }
        StringBuilder conclusions = new StringBuilder();
        StringBuilder zyConclusions = null;
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(
                new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", patientcode)
                        .eq("id_ks", deptNo)
                        .eq("f_examinated", 1)
                        .eq("change_item",0)
        );
        List<PacsResult> prs = new ArrayList<PacsResult>();//所有已检的
        for (Peispatientfeeitem feeitem : feeitems) {
            PacsResult pr = pacsResultMapper.selectOne(
                    new QueryWrapper<PacsResult>()
                            .eq("patientcode", patientcode)
                            .eq("item_id", feeitem.getIdExamfeeitem())
            );
            if (pr != null) {
                prs.add(pr);
            }
        }
        String rn = "\n";//换行符
        for (PacsResult pr : prs) {
            if (pr.getExamresultdesc() != null) {
                conclusions.append("[" + pr.getItemName() + "]所见：" + rn);
                conclusions.append(pr.getExamresultdesc() + rn);
            }
            if (pr.getExamresultsummary() != null) {
                conclusions.append("[" + pr.getItemName() + "]提示：" + rn);
                conclusions.append(pr.getExamresultsummary() + rn);
            }
        }
        if (ceis != null) {
            zyConclusions = new StringBuilder();
            for (PacsResult pr : prs) {
                Items tjitem = itemsMapper.selectById(pr.getItemId());
                if (ceis.get(tjitem.getId()) != null) {
                    if (pr.getExamresultdesc() != null) {
                        zyConclusions.append("[" + pr.getItemName() + "]所见：" + rn);
                        zyConclusions.append(pr.getExamresultdesc() + rn);
                    }
                    if (pr.getExamresultsummary() != null) {
                        zyConclusions.append("[" + pr.getItemName() + "]提示：" + rn);
                        zyConclusions.append(pr.getExamresultsummary() + rn);
                    }
                }
            }
        }
        return new StringBuilder[]{conclusions, zyConclusions};
    }

    @Override
    public PacsAbteilungPatientTotalVo getPatientTotal(PatientListParam patientListParam) {
        return baseMapper.getPatientTotal(patientListParam);
    }

    @Override
    @Transactional
    public void deleteAttachments(List<String> ids) {
        for (String id : ids) {
            Attachment attachment = attachmentMapper.selectById(id);
            if (attachment == null) continue;
            attachmentService.deleteFile(attachment.getFilePath());
            attachmentService.deleteFile(attachment.getDcmPath());
        }
    }

    @Override
    @Transactional
    public void deleteByFeeitemId(String id) {
        List<Attachment> attachments = attachmentMapper.selectList(
                new QueryWrapper<Attachment>()
                        .eq("fee_item_id", id)
        );
        for (Attachment attachment : attachments) {
            attachmentService.deleteFile(attachment.getFilePath());
            //删除dcm文件
            if (StringUtils.isNotEmpty(attachment.getDcmPath())){
                attachmentService.deleteFile(attachment.getDcmPath());
            }
        }
    }

    @Override
    @Transactional
    public PacsAbteilungSaveScreenshotVo uploadScreenshot(PacsAbteilungSaveScreenshotParam param) {
        String feeitemId = param.getFeeitemId();
        Peispatientfeeitem peispatientfeeitem = peispatientfeeitemMapper.selectById(feeitemId);
        String patientcode = peispatientfeeitem.getIdPatient();
        Integer shortCode = peispatientfeeitem.getShortCode();
        String deptNo = peispatientfeeitem.getIdKs();
        String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
        int inReport = 0;
        if (StrUtil.isNotEmpty(pacsConfig)) {
            JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
            Integer inReportConfig = jsonObject.getInt("inReport");
            if (inReportConfig != null) {
                inReport = inReportConfig.intValue();
            }
        }

        Attachment attachment = new Attachment();
        attachment.setInReport(inReport);
        attachment.setPatientcode(patientcode);
        attachment.setShortCode(shortCode);
        attachment.setFeeItemId(feeitemId);
        attachment.setPacsFeeItemId(feeitemId);
        attachment.setDepId(deptNo);
        SysBranch branch = iSysBranchService.getDefaultBranch();
        attachment.setBranchId(branch.getBranchId());
        attachment.setType(1);
        attachment.setStatus(0);
        attachment.setCreatedate(new Date());
        attachment.setFileType("PACS");
        attachment.setMemo("彩超截图");


        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value())
                + "/"
                + iSysDeptService.getByDeptNo(deptNo).getInputCode()
                + "/"
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "/"
                + patientcode;
        if (StrUtil.isNotBlank(param.getBase64())) {
            String base64 = param.getBase64();
            Base64ToMultipartFile multipartFile = MultipartFileUtil.base64ConvertMultipartFile(base64);
            String extName = multipartFile.getExtension();
            try {
                attachmentService.uploadFile(multipartFile, attachment, baseDir, extName, null, false);
            } catch (IOException e) {
                String errorMsg = "保存彩超截图失败";
                log.error(errorMsg, e);
                throw new ServiceException(errorMsg);
            }
        } else {
            MultipartFile multipartFile = param.getFile();
            String fileName = multipartFile.getOriginalFilename();
            String extName = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
            log.info("文件类型：{}、{}", fileName, extName);
            try {
                attachmentService.uploadFile(multipartFile, attachment, baseDir, extName, null, false);
            } catch (IOException e) {
                String errorMsg = "保存彩超截图失败";
                log.error(errorMsg, e);
                throw new ServiceException(errorMsg);
            }
        }


        attachmentService.savePicture(attachment);

        PacsAbteilungSaveScreenshotVo pacsAbteilungSaveScreenshotVo = new PacsAbteilungSaveScreenshotVo();
        pacsAbteilungSaveScreenshotVo.setId(attachment.getId());
        pacsAbteilungSaveScreenshotVo.setSrc(attachment.getFilePath());
        pacsAbteilungSaveScreenshotVo.setInReport(attachment.getInReport());
        return pacsAbteilungSaveScreenshotVo;
    }


    /**
     * 图片设置进报告
     * @param param
     * @return
     */
    @Override
    public Boolean setInReport(SetInReportParam param) {
        List<String> ids = param.getIds();
        for (String id : ids) {
            Attachment attachment = attachmentMapper.selectById(id);
            if (attachment == null){
                throw new ServiceException("图片不存在");
            }
            attachment.setInReport(param.getInReport());
            attachmentMapper.updateById(attachment);
        }
        return Boolean.TRUE;
    }
}
