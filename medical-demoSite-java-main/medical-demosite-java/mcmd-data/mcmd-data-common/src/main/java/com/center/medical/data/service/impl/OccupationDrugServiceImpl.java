package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.DrugAndFzx;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dao.DrugAndFzxMapper;
import com.center.medical.data.bean.model.OccupationDrug;
import com.center.medical.data.dao.OccupationDrugMapper;
import com.center.medical.data.service.OccupationDrugService;
import com.center.medical.system.dao.BranchMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * JC职业病/禁忌症(OccupationDrug)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:19
 */
@Slf4j
@Service("occupationDrugService")
@RequiredArgsConstructor
public class OccupationDrugServiceImpl extends ServiceImpl<OccupationDrugMapper, OccupationDrug> implements OccupationDrugService {

    private final OccupationDrugMapper occupationDrugMapper;
    private final DrugAndFzxMapper drugAndFzxMapper;
    private final Snowflake snowflake;
    private final BranchMapper branchMapper;

    /**
     * 分页查询[JC职业病/禁忌症]列表
     *
     * @param page  分页参数
     * @param param OccupationDrug查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OccupationDrug> getList(PageParam<OccupationDrug> page, OccupationDrug param) {
        return occupationDrugMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OccupationDrug getInfoById(String id) {
        return occupationDrugMapper.getInfoById(id);
    }

    /**
     * 保存或更新
     * @param drug 实体对象
     * @return
     */
    @Override
    @Transactional
    public Boolean saveOrUpdateOccup(OccupationDrug drug) {
        String id=drug.getId();
        if(StringUtils.isNotEmpty(id)){
            OccupationDrug old = occupationDrugMapper.getInfoById(id);
            if(ObjectUtils.isEmpty(old)){
                throw new ServiceException("保存失败，数据已被删除！");
            }
            //根据条件删除实体对象
            drugAndFzxMapper.deleteById(id);
            BeanUtils.copyProperties(drug, old, new String[]{});
            //修改
            old.setModifydate(new Date());
            updateById(old);
        }else{
            drug.setCreatedate(new Date());
            drug.setId(String.valueOf(snowflake.nextId()));
            save(drug);
            id = drug.getId();
        }
        //是否是公共的：0或null.否 1.是
        if(drug.getIsPublic() == 1){
            List<Branch> branches = branchMapper.selectList(new QueryWrapper<Branch>().eq("is_delete",0));
            for(Branch branch:branches){
                DrugAndFzx haf = new DrugAndFzx(id, String.valueOf(branch.getId()),0);//新增或修改后，需重新同步
                haf.setCreatedate(new Date());
                haf.setId(String.valueOf(snowflake.nextId()));
                drugAndFzxMapper.insert(haf);
            }
        }else{
            String fzxIds = drug.getFzxIds();
            if(StringUtils.isNotEmpty(fzxIds)){
                String [] fzxs=fzxIds.split(",");
                for(String fzxId:fzxs){
                    DrugAndFzx haf = new DrugAndFzx(id, fzxId,0);
                    haf.setCreatedate(new Date());
                    haf.setId(String.valueOf(snowflake.nextId()));
                    drugAndFzxMapper.insert(haf);
                }
            }
        }
        return Boolean.TRUE;
    }


    @Override
    public String removeOccupa(String ids) {
        String[] arr = ids.split(",");
        occupationDrugMapper.delete(new QueryWrapper<OccupationDrug>().in("id",arr));
        drugAndFzxMapper.delete(new QueryWrapper<DrugAndFzx>().in("drug_id", arr));
        return "success";
    }
}

