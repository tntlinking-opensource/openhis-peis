package com.center.medical.pacs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PacsInspectCharge;
import com.center.medical.bean.model.PacsItemPart;
import com.center.medical.bean.model.PacsItems;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PacsInspectChargeMapper;
import com.center.medical.dao.PacsItemPartMapper;
import com.center.medical.pacs.bean.param.PacsItemsExamSaveParam;
import com.center.medical.pacs.bean.param.PacsItemsListParam;
import com.center.medical.pacs.bean.param.PacsItemsSaveParam;
import com.center.medical.pacs.bean.vo.PacsItemExamPageVo;
import com.center.medical.pacs.bean.vo.PacsItemExamListVo;
import com.center.medical.pacs.bean.vo.PacsItemsListVo;
import com.center.medical.pacs.bean.vo.PacsItemsVo;
import com.center.medical.pacs.dao.PacsItemsBasicMapper;
import com.center.medical.pacs.service.PacsItemsBasicService;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-31 14:30
 */
@Service
@RequiredArgsConstructor
public class PacsItemsBasicServiceImpl extends ServiceImpl<PacsItemsBasicMapper, PacsItems> implements PacsItemsBasicService {
    private final PacsItemPartMapper pacsItemPartMapper;
    private final Snowflake snowflake;
    private final PacsInspectChargeMapper pacsInspectChargeMapper;

    @Override
    public IPage<PacsItemsListVo> getPage(PageParam pageParam, PacsItemsListParam pacsItemsListParam){
        return baseMapper.getPage(pageParam,pacsItemsListParam);
    }

    @Override
    public IPage<PacsItemExamPageVo> getExamPage(PageParam pageParam, String id){
        return baseMapper.getExamPage(pageParam,id);
    }

    @Override
    public List<PacsItemExamListVo> getExamList(String id){
        return baseMapper.getExamList(id);
    }

    @Override
    public  void delete(List<String>ids){
        List<PacsItems> pacsItemsList=baseMapper.selectList(
                new QueryWrapper<PacsItems>()
                    .in("id",ids)
        );
        for(PacsItems pacsItems:pacsItemsList){
            pacsItems.setIsDelete(1);
            baseMapper.updateById(pacsItems);
        }
    }

    @Override
    public PacsItemsVo selectOne(String id){
        PacsItems pacsItems=baseMapper.selectById(id);
        return BeanUtil.toBean(pacsItems,PacsItemsVo.class);
    }

    @Override
    @Transactional
    public void saOrUp(PacsItemsSaveParam pacsItemsSaveParam){
        String itemId=pacsItemsSaveParam.getId();
        if(StringUtil.isEmpty(itemId)){
            if(
                    baseMapper.selectCount(
                            new QueryWrapper<PacsItems>()
                                .eq("is_delete",0)
                            .eq("examfeeitem_name",pacsItemsSaveParam.getExamfeeitemName())
                            .eq("id_depart",pacsItemsSaveParam.getIdDepart())
                    ).intValue()>0
            ){
                throw new ServiceException("保存失败,科室为"+pacsItemsSaveParam.getDepartName()+"、名称为"+pacsItemsSaveParam.getExamfeeitemName()+"的收费项目已存在。");
            }
            PacsItems pacsItems=BeanUtil.toBean(pacsItemsSaveParam,PacsItems.class);
            itemId=String.valueOf(snowflake.nextId());
            pacsItems.setIsDelete(0);
            pacsItems.setId(itemId);
            pacsItems.setXXxdm(SecurityUtils.getUserNo());
            baseMapper.insert(pacsItems);
        }else{
            if(
                    baseMapper.selectCount(
                            new QueryWrapper<PacsItems>()
                                    .eq("is_delete",0)
                                    .eq("examfeeitem_name",pacsItemsSaveParam.getExamfeeitemName())
                                    .eq("id_depart",pacsItemsSaveParam.getIdDepart())
                                    .ne("id",itemId)
                    ).intValue()>0
            ){
                throw new ServiceException("保存失败,科室为"+pacsItemsSaveParam.getDepartName()+"、名称为"+pacsItemsSaveParam.getExamfeeitemName()+"的收费项目已存在。");
            }

            pacsItemPartMapper.delete(
                    new QueryWrapper<PacsItemPart>()
                        .eq("item_id",itemId)
            );

            PacsItems pacsItems=baseMapper.selectById(itemId);
            if(pacsItems.getIsDelete().intValue()==1)throw new ServiceException("更新失败，收费项目已被删除");
            BeanUtil.copyProperties(pacsItemsSaveParam,pacsItems);
            baseMapper.updateById(pacsItems);
        }

        List<PacsItemsExamSaveParam> exams=pacsItemsSaveParam.getExams();
        for(PacsItemsExamSaveParam exam:exams){
            String state=exam.get_state();
            if("removed".equals(state)){
                PacsInspectCharge pacsInspectCharge=pacsInspectChargeMapper.selectById(exam.getId());
                pacsInspectCharge.setIsDelete(1);
                pacsInspectChargeMapper.updateById(pacsInspectCharge);
            }else if("modified".equals(state)){
                PacsInspectCharge pacsInspectCharge=pacsInspectChargeMapper.selectById(exam.getId());
                pacsInspectCharge.setInspectId(exam.getInspectId());
                pacsInspectCharge.setOrderIndex(exam.getOrderIndex());
                pacsInspectChargeMapper.updateById(pacsInspectCharge);
            }else if("added".equals(state)){
                PacsInspectCharge pacsInspectCharge=new PacsInspectCharge();
                pacsInspectCharge.setOrderIndex(exam.getOrderIndex());
                pacsInspectCharge.setInspectId(exam.getInspectId());
                pacsInspectCharge.setIsDelete(0);
                pacsInspectCharge.setChargeId(itemId);
                pacsInspectCharge.setId(String.valueOf(snowflake.nextId()));
                pacsInspectChargeMapper.insert(pacsInspectCharge);
            }
        }

        String examfeeitemCodehm=pacsItemsSaveParam.getExamfeeitemCodehm();
        if(StrUtil.isNotEmpty(examfeeitemCodehm)){
            String[] partIds=examfeeitemCodehm.split(",");
            for(String partId:partIds){
                if(StrUtil.isNotEmpty(partId)){
                    PacsItemPart pacsItemPart=new PacsItemPart();
                    pacsItemPart.setItemId(itemId);
                    pacsItemPart.setPartId(partId);
                    pacsItemPart.setId(String.valueOf(snowflake.nextId()));
                    pacsItemPartMapper.insert(pacsItemPart);
                }
            }
        }
    }
}
