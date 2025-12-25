package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.model.ExamAndFzx;
import com.center.medical.data.bean.param.BaseExamItemParam;
import com.center.medical.data.bean.vo.ExamsByItemVo;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.data.service.BasexamltemSignService;
import com.center.medical.data.service.ExamAndFzxService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * JC检查项目表(Basexamltem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:28
 */
@Slf4j
@Service("basexamltemService")
@RequiredArgsConstructor
public class BasexamltemServiceImpl extends ServiceImpl<BasexamltemMapper, Basexamltem> implements BasexamltemService {

    private final BasexamltemMapper basexamltemMapper;
    private final SysDeptMapper sysDeptMapper;
    private final Snowflake snowflake;
    private final ExamAndFzxService examAndFzxService;
    private final MapperFacade mapperFacade;
    private final BasexamltemSignService basexamltemSignService;
    private final SysBranchMapper sysBranchMapper;


    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param Basexamltem查询参数
     * @return 分页数据
     */
    @Override

    public IPage<Basexamltem> getList(PageParam<Basexamltem> page, BaseExamItemParam param) {
        return basexamltemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Basexamltem getInfoById(String id) {
        return basexamltemMapper.getInfoById(id);
    }

    /**
     * 根据inputCode获取检查项目名称
     *
     * @param page
     * @param inputCode
     * @return
     */
    @Override
    public IPage<Basexamltem> getAllJcid(PageParam<Basexamltem> page, String inputCode) {
        PageParam<Basexamltem> basexamltemPageParam = basexamltemMapper.getAllJcid(page, inputCode);
        return basexamltemPageParam;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUp(Basexamltem basexamltem) {
        Date now = new Date();
        String id = "";
        String divisionId = basexamltem.getDivisionId();
        int type = basexamltem.getType();
        String typeName = ExamType.getName(type);//0 == type ? "健康体检" : 1 == type ? "职业检查" : "综合";
        log.info("体检类型：{}、{}", type, typeName);

        //获取科室
        SysDept dept = sysDeptMapper.getByDeptNo(divisionId);
        if (Objects.isNull(dept)) {
            throw new ServiceException("保存失败，科室已被删除！");
        }
        String deptName = dept.getDeptName();

        if (StringUtils.isBlank(basexamltem.getId())) {
            // 新增
            // 是否存在相同的
            if (basexamltemMapper.selectCount(new LambdaQueryWrapper<Basexamltem>()
                    .eq(Basexamltem::getExamitemName, basexamltem.getExamitemName())
                    .eq(Basexamltem::getDivisionId, basexamltem.getDivisionId())
                    .eq(Basexamltem::getType, type)
                    .eq(Basexamltem::getIsDelete, 0)) > 0) {
                throw new ServiceException("保存失败：科室为" + deptName + "、体检类型为" + typeName + "、名称为【" + basexamltem.getExamitemName() + "】的检查项目已经存在");
            }
            // 保存检查项目
            basexamltem.setIsDelete(0);
            basexamltem.setCreatedate(now);
            id = String.valueOf(snowflake.nextId());
            basexamltem.setId(id);
            basexamltemMapper.insert(basexamltem);

            //TODO syncData 执行数据同步

        } else {
            // 编辑
            id = basexamltem.getId();
            examAndFzxService.remove(new LambdaQueryWrapper<ExamAndFzx>()
                    .eq(ExamAndFzx::getExamId, id));
            Basexamltem biDb = basexamltemMapper.selectOne(new LambdaQueryWrapper<Basexamltem>()
                    .eq(Basexamltem::getId, id).eq(Basexamltem::getIsDelete, 0));
            // 判断是否存在
            if (Objects.isNull(biDb)) {
                // 不存在
                throw new ServiceException("更新失败：" + basexamltem.getExamitemName() + "不存在，已经被删除");
            } else {
                if (Objects.nonNull(biDb.getIsPublic()) && Objects.equals(biDb.getIsPublic(), 1) && !SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE)) {
                    throw new ServiceException("保存失败，该检查项目是公共数据，您没有修改公共数据的权限！");
                }
                // 是否存在相同的
                if (basexamltemMapper.selectCount(new LambdaQueryWrapper<Basexamltem>()
                        .ne(Basexamltem::getId, basexamltem.getId())
                        .eq(Basexamltem::getExamitemName, basexamltem.getExamitemName())
                        .eq(Basexamltem::getDivisionId, basexamltem.getDivisionId())
                        .eq(Basexamltem::getType, type)
                        .eq(Basexamltem::getIsDelete, 0)) > 0) {
                    throw new ServiceException("保存失败：科室为" + deptName + "、体检类型为" + typeName + "、名称为【" + basexamltem.getExamitemName() + "】的检查项目已经存在");
                }
                basexamltem.setIsDelete(0);
                //复制属性
                basexamltem.setModifydate(now);
                basexamltemMapper.updateById(basexamltem);

                //TODO syncData 执行数据同步
            }
        }

        // 体征词操作
        List<BasexamltemSign> signList = basexamltem.getBasexamltemSignList();
        for (BasexamltemSign sign : signList) {
            sign.setInputCode(ToolUtil.getHanziPinyinHeadChar(sign.getName().trim()));
            if ("removed".equals(sign.get_state())) {
                //删除操作
                if (StringUtils.isNotBlank(sign.getId())) {
                    BasexamltemSign signDb = basexamltemSignService.getOne(new LambdaQueryWrapper<BasexamltemSign>()
                            .eq(BasexamltemSign::getId, sign.getId()).eq(BasexamltemSign::getIsDelete, 0));
                    // 判断是否存在
                    if (Objects.nonNull(signDb)) {
                        signDb.setIsDelete(1);
                        signDb.setModifydate(now);
                        // 更新实体类
                        basexamltemSignService.updateById(signDb);

                        //TODO syncData 执行数据同步
                    }
                }
            } else if ("modified".equals(sign.get_state())) {
                //更新操作
                BasexamltemSign signDb = basexamltemSignService.getOne(new LambdaQueryWrapper<BasexamltemSign>()
                        .eq(BasexamltemSign::getId, sign.getId()).eq(BasexamltemSign::getIsDelete, 0));
                // 判断是否存在
                if (Objects.nonNull(signDb)) {
                    // 判断名称是否重复
                    if (basexamltemSignService.count(new LambdaQueryWrapper<BasexamltemSign>()
                            .eq(BasexamltemSign::getId, sign.getId()).eq(BasexamltemSign::getIsDelete, 0)
                            .eq(BasexamltemSign::getName, sign.getName()).eq(BasexamltemSign::getInspectId, sign.getInspectId())) > 0) {
                        // 重复
                        throw new ServiceException("更新失败：【" + sign.getName() + "】体征词已经重复");
                    }
                    sign.setIsDelete(0);
                    sign.setModifydate(now);
                    // 更新实体类
                    basexamltemSignService.updateById(sign);

                    //TODO syncData 执行数据同步
                }
            } else if ("added".equals(sign.get_state())) {
                // 判断名称是否重复
                if (basexamltemSignService.count(new LambdaQueryWrapper<BasexamltemSign>()
                        .eq(BasexamltemSign::getId, sign.getId()).eq(BasexamltemSign::getIsDelete, 0)
                        .eq(BasexamltemSign::getName, sign.getName()).eq(BasexamltemSign::getInspectId, sign.getInspectId())) > 0) {
                    // 重复
                    throw new ServiceException("增加失败：【" + sign.getName() + "】体征词已经重复");
                }
                sign.setInspectId(id);
                sign.setIsDelete(0);
                // 保存实体类
                basexamltemSignService.save(sign);

                //TODO syncData 执行数据同步
            }
        }

        //公共项目操作
        if (Objects.equals(basexamltem.getIsPublic(), 1)) {
            //获取所有分中心，并添加项目与分中心关联记录
            List<SysBranch> branches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getIsDelete, 0));
            List<ExamAndFzx> iafs = new ArrayList<ExamAndFzx>();
            for (SysBranch branch : branches) {
                ExamAndFzx iaf = new ExamAndFzx();//新增或修改后，需重新同步
                iaf.setId(String.valueOf(snowflake.nextId()));
                iaf.setExamId(id);
                iaf.setFzxId(branch.getBranchId());
                iaf.setTbzt(0);
                iaf.setCreatedate(now);
                iafs.add(iaf);
            }
            examAndFzxService.saveBatch(iafs);

            //TODO syncData 执行数据同步
        } else {
            //非公共，添加绑定的项目与分中心关联记录
            String fzxIds = basexamltem.getFzxIds();
            if (StringUtils.isNotBlank(fzxIds)) {
                String[] fzxs = fzxIds.split(",");
                List<ExamAndFzx> iafs = new ArrayList<ExamAndFzx>();
                for (String fzxId : fzxs) {
                    ExamAndFzx iaf = new ExamAndFzx();//新增或修改后，需重新同步
                    iaf.setId(String.valueOf(snowflake.nextId()));
                    iaf.setExamId(id);
                    iaf.setFzxId(fzxId);
                    iaf.setTbzt(0);
                    iaf.setCreatedate(now);
                    iafs.add(iaf);
                }
                examAndFzxService.saveBatch(iafs);

                //TODO syncData 执行数据同步
            }
        }

        return Boolean.TRUE;
    }

    /**
     * 删除操作
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean delete(List<String> ids) {
        // 批量删除
        boolean frole = SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE);

        List<Basexamltem> list = basexamltemMapper.selectList(new LambdaQueryWrapper<Basexamltem>()
                .in(Basexamltem::getId, ids).eq(Basexamltem::getIsDelete, 0));

        for (Basexamltem basExamLtem : list) {
            if (Objects.nonNull(basExamLtem.getIsPublic()) && Objects.equals(basExamLtem.getIsPublic(), 1) && !frole) {
                throw new ServiceException("保存失败，检查项目【" + basExamLtem.getExamitemName() + "】是公共数据，您没有修改公共数据的权限！");
            }
            basExamLtem.setIsDelete(1);
            basexamltemMapper.updateById(basExamLtem);
            //删除关联的体征词
            basexamltemSignService.removeByInspectId(basExamLtem.getId());
            //TODO 添加详细操作日志
            //operate("删除检查项目", "删除", basExamLtem.getExamitemName());

            //TODO syncData 执行数据同步
        }
        //将关联的分中心记录设置为未同步
        List<ExamAndFzx> eafs = examAndFzxService.list(new LambdaQueryWrapper<ExamAndFzx>().in(ExamAndFzx::getExamId, ids));
        for (ExamAndFzx eaf : eafs) {
            eaf.setTbzt(0);
        }
        examAndFzxService.updateBatchById(eafs);

        //TODO syncData 执行数据同步
        return Boolean.TRUE;
    }


    /**
     * 营养状况下拉数据
     *
     * @return
     */
    @Override
    public List<BasexamltemSign> getCommonStateData() {
        List<BasexamltemSign> signs = basexamltemSignService.list(new QueryWrapper<BasexamltemSign>()
                .eq("inspect_id", Constants.YBZKID).eq("is_delete", 0));
        return signs;
    }

    /**
     * 获取右侧表格子表格数据
     *
     * @param page
     * @param id
     * @return
     */
    @Override
    public IPage<ExamsByItemVo> getExamsByItemId(PageParam<ExamsByItemVo> page, String id) {
        return basexamltemMapper.getExamsByItemId(page, id);
    }
}

