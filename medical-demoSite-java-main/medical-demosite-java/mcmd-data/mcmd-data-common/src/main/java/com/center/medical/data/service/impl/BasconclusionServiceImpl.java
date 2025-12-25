package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Branch;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.model.ConclusionAndFzx;
import com.center.medical.data.bean.vo.BasconclusionVo;
import com.center.medical.data.dao.BasconclusionMapper;
import com.center.medical.data.service.BasconclusionService;
import com.center.medical.data.service.ConclusionAndFzxService;
import com.center.medical.system.dao.BranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 总检结论词(Basconclusion)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:18
 */
@Slf4j
@Service("basconclusionService")
@RequiredArgsConstructor
public class BasconclusionServiceImpl extends ServiceImpl<BasconclusionMapper, Basconclusion> implements BasconclusionService {

    private final BasconclusionMapper basconclusionMapper;
    private final ConclusionAndFzxService conclusionAndFzxService;
    private final SysDeptMapper sysDeptMapper;
    private final Snowflake snowflake;
    private final BranchMapper branchMapper;

    /**
     * 分页查询[总检结论词]列表
     *
     * @param page  分页参数
     * @param param Basconclusion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Basconclusion> getList(PageParam<Basconclusion> page, Basconclusion param) {
        return basconclusionMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Basconclusion getInfoById(String id) {
        Basconclusion basconclusion = basconclusionMapper.getInfoById(id);
        if (ObjectUtils.isNotEmpty(basconclusion) && StringUtils.isNotEmpty(basconclusion.getFzxIds())) {
            List<Branch> branchList = branchMapper.selectList(new QueryWrapper<Branch>()
                    .in("branch_id", basconclusion.getFzxIds()));
            StringBuilder str = new StringBuilder();
            for (Branch branch : branchList) {
                str.append(branch.getFzx() + ",");
            }
            basconclusion.setFzx(str.toString());
        }
        return basconclusion;
    }

    /**
     * 根据拼音获取结论词
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<Basconclusion> getConclusion(PageParam<Basconclusion> page, String key) {
        //搜索条件
        QueryWrapper<Basconclusion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.like("input_code", key.trim().toUpperCase());
        }
        return basconclusionMapper.selectPage(page, queryWrapper);
    }


    /**
     * 检查项目查询 下拉数据
     *
     * @param page
     * @param key
     * @param id
     * @return
     */
    @Override
    public IPage<Basconclusion> getInspectConclusion(PageParam<Basconclusion> page, String key, String id) {
        return basconclusionMapper.getInspectConclusion(page, key, id);
    }

    /**
     * 删除结伦词及中间表
     *
     * @param ids
     * @return
     */
    @Override
    public boolean removebasconclusion(List<String> ids) {
        //根据ids查询全部
        List<Basconclusion> bas = basconclusionMapper.selectList(new QueryWrapper<Basconclusion>().in("id", ids));
        //查询有没有数据管理的权限
        Boolean frole = SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE);
        boolean b1 = false;
        for (Basconclusion b : bas) {
            if (ObjectUtils.isNotEmpty(b.getIsPublic()) && b.getIsPublic().intValue() == 1 && !frole) {
                throw new ServiceException("删除失败，结伦词" + b.getName() + "是公共数据，您没有修改公共数据的权限！");
            }
            b.setIsDelete(1);
            b.setModifydate(new Date());
            b1 = updateById(b);
        }
        //JC结伦词和分中心关联表
        ConclusionAndFzx conclusionAndFzx = new ConclusionAndFzx();
        conclusionAndFzx.setTbzt(0);
        conclusionAndFzx.setModifydate(new Date());
        boolean b2 = conclusionAndFzxService.update(conclusionAndFzx, new UpdateWrapper<ConclusionAndFzx>().in("conclusion_id", ids));
        return b1 && b2;
    }


    /**
     * 保存或更新
     *
     * @param basconclusion
     * @return
     */
    @Override
    public Boolean saOrUp(Basconclusion basconclusion) {
        //获取汉字的拼音首字母放入输入码
        basconclusion.setInputCode(ToolUtil.getHanziPinyinHeadChar(basconclusion.getName()));
        String id = "";
        //id为空就是添加
        if (StringUtils.isEmpty(basconclusion.getId())) {
            basconclusion.setIsDelete(0);
            basconclusion.setStatus(1);
            basconclusion.setCreater(SecurityUtils.getLoginUser().getUsername());
            SysDept dep = sysDeptMapper.getByDeptNo(basconclusion.getDivisionId());
            if (ObjectUtils.isNotEmpty(dep)) {
                basconclusion.setDepName(dep.getDeptName());
            }
            //保存
            basconclusion.setCreatedate(new Date());
//            basconclusion.setId(String.valueOf(snowflake.nextId()));
            this.save(basconclusion);
            id = basconclusion.getId();
            log.info("增加结论词,增加{}" + basconclusion.getName());

        } else {
            //有id就是修改
            id = basconclusion.getId();
            Basconclusion baseLtem = basconclusionMapper.selectOne(new QueryWrapper<Basconclusion>()
                    .eq("id", id).eq("is_delete", 0));
            // 判断是否存在
            if (ObjectUtils.isNotEmpty(baseLtem)) {
                if (ObjectUtils.isNotEmpty(baseLtem.getIsPublic()) && baseLtem.getIsPublic().intValue() == 1 && !SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE)) {
                    throw new ServiceException("删除失败，该收费项目是公共数据，您没有修改公共数据的权限！");
                }
                //删除中间表
                conclusionAndFzxService.remove(new QueryWrapper<ConclusionAndFzx>().eq("conclusion_id", id));
                //部门表
                SysDept dep = sysDeptMapper.getByDeptNo(basconclusion.getDivisionId());
                if (ObjectUtils.isNotEmpty(dep)) {
                    basconclusion.setDepName(dep.getDeptName());
                }
                //修改
                basconclusion.setIsDelete(0);
                basconclusion.setModifydate(new Date());
                this.updateById(basconclusion);

            } else {
                // 不存在
                throw new ServiceException("操作失败：" + basconclusion.getName()
                        + " 不存在，已经被删除");
            }

            log.info("编辑结论词,编辑{}" + basconclusion.getName());
//
        }
        //如果是公开的
        if (basconclusion.getIsPublic() == 1) {
            List<Branch> branches = branchMapper.selectList(new QueryWrapper<Branch>().eq("is_delete", 0));
            List<ConclusionAndFzx> iafs = new ArrayList<ConclusionAndFzx>();
            for (Branch branch : branches) {
                //新建对象并把数据放入集合中批量更新
                ConclusionAndFzx iaf = new ConclusionAndFzx(String.valueOf(snowflake.nextId()), new Date(), id, branch.getBranchId(), 0);//新增或修改后，需重新同步
                iafs.add(iaf);
            }
            conclusionAndFzxService.saveBatch(iafs);
        } else {
            //不是公开的
            String fzxIds = basconclusion.getFzxIds();
            if (StringUtils.isNotEmpty(fzxIds)) {
                String[] fzxs = fzxIds.split(",");
                List<ConclusionAndFzx> iafs = new ArrayList<ConclusionAndFzx>();
                for (String fzxId : fzxs) {
                    ConclusionAndFzx iaf = new ConclusionAndFzx(String.valueOf(snowflake.nextId()), new Date(), id, fzxId, 0);
                    iafs.add(iaf);
                }
                conclusionAndFzxService.saveBatch(iafs);
            }
        }
        return true;
    }

    /**
     * 审核
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean audit(List<String> ids) {
        //查询
        List<Basconclusion> cs = basconclusionMapper.selectList(new QueryWrapper<Basconclusion>().in("id", ids));
        List<ConclusionAndFzx> iafs = new ArrayList<ConclusionAndFzx>();
        List<Branch> branches = branchMapper.selectList(new QueryWrapper<Branch>().eq("is_delete", 0));
        Date auditTime = new Date();
        String auditer = SecurityUtils.getLoginUser().getUsername();
        //删除中间表
        conclusionAndFzxService.remove(new QueryWrapper<ConclusionAndFzx>().in("conclusion_id", ids));

        //设置属性
        for (Basconclusion c : cs) {
            c.setIsPublic(1);
            c.setAuditStatus(1);
            c.setAuditTime(auditTime);
            c.setAuditer(auditer);
            for (Branch branch : branches) {
                //中间表
                ConclusionAndFzx iaf = new ConclusionAndFzx(String.valueOf(snowflake.nextId()), new Date(), c.getId(),branch.getBranchId(), 0);//新增或修改后，需重新同步
                iafs.add(iaf);
            }

        }
        //批量插入
        conclusionAndFzxService.saveBatch(iafs);
        //批量更新
        updateBatchById(cs);

        return Boolean.TRUE;
    }

    /**
     * 健康+职业 结论词下拉
     *
     * @param page
     * @param srm
     * @return
     */
    @Override
    public IPage<BasconclusionVo> getConclusionListData(PageParam<BasconclusionVo> page, String srm) {
        //去除空格大写
        if (ObjectUtils.isNotEmpty(srm)) {
            srm = srm.trim();
        }
        return basconclusionMapper.getConclusionListData(page, srm);
    }
}

