package com.center.medical.pacs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.model.PacsBasexamltemSign;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PacsBasexamltemSignMapper;
import com.center.medical.pacs.bean.model.PacsBasexamltem;
import com.center.medical.pacs.bean.param.PacsBasexamltemListParam;
import com.center.medical.pacs.bean.param.PacsBasexamltemSaveParam;
import com.center.medical.pacs.bean.param.PacsBasexamltemSignSaveParam;
import com.center.medical.pacs.bean.vo.PacsBasexamltemSignVo;
import com.center.medical.pacs.bean.vo.PacsBasexamltemVo;
import com.center.medical.pacs.bean.vo.PacsConclusionVo;
import com.center.medical.pacs.dao.PacsBasexamltemMapper;
import com.center.medical.pacs.service.PacsBasexamltemService;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-29 8:03
 */
@Service
@RequiredArgsConstructor
public class PacsBasexamltemServiceImpl extends ServiceImpl<PacsBasexamltemMapper, PacsBasexamltem> implements PacsBasexamltemService {
    private final SysDeptMapper sysDeptMapper;
    private final PacsBasexamltemSignMapper pacsBasexamltemSignMapper;
    private final Snowflake snowflake;

    @Override
    public IPage<PacsBasexamltemVo> getPage(PageParam page, PacsBasexamltemListParam pacsBasexamltemListParam) {
        return baseMapper.getPage(page, pacsBasexamltemListParam);
    }

    @Override
    public IPage<PacsBasexamltemSignVo> getSignPage(PageParam page, String id){
        return baseMapper.getSignPage(page,id);
    }

    @Override
    public List<PacsBasexamltemSignVo> getSignList(String id){
        return baseMapper.getSignList(id);
    }

    @Override
    @Transactional
    public void delete(List<String> ids){
        List<PacsBasexamltem> pacsBasexamltems=baseMapper.selectList(
                new QueryWrapper<PacsBasexamltem>()
                    .in("id",ids)
        );
        for(PacsBasexamltem pacsBasexamltem:pacsBasexamltems){
            pacsBasexamltem.setIsDelete(1);
            baseMapper.updateById(pacsBasexamltem);
            List<PacsBasexamltemSign> pacsBasexamltemSigns=pacsBasexamltemSignMapper.selectList(
                    new QueryWrapper<PacsBasexamltemSign>()
                        .eq("inspect_id",pacsBasexamltem.getId())
                        .eq("is_delete",0)
            );
            for(PacsBasexamltemSign pacsBasexamltemSign:pacsBasexamltemSigns){
                pacsBasexamltemSign.setIsDelete(1);
                pacsBasexamltemSignMapper.updateById(pacsBasexamltemSign);
            }
        }
    }

    @Override
    public PacsBasexamltemVo getInfoById(String id){
        PacsBasexamltem pacsBasexamltem=baseMapper.selectById(id);
        if(pacsBasexamltem.getIsDelete().intValue()==1){
            throw new ServiceException("检查项目已被删除");
        }
        PacsBasexamltemVo pacsBasexamltemVo= BeanUtil.toBean(pacsBasexamltem,PacsBasexamltemVo.class);
        String deptNo=pacsBasexamltemVo.getDivisionId();
        if(StrUtil.isNotEmpty(deptNo)){
            SysDept sysDept=sysDeptMapper.getByDeptNo(deptNo);
            if(sysDept!=null){
                pacsBasexamltemVo.setDeptName(sysDept.getDeptName());
            }
        }
        return pacsBasexamltemVo;
    }

    @Override
    @Transactional
    public void saOrUp(PacsBasexamltemSaveParam pacsBasexamltemSaveParam){
        String examId=pacsBasexamltemSaveParam.getId();
        SysDept sysDept=sysDeptMapper.getByDeptNo(pacsBasexamltemSaveParam.getDivisionId());
        String deptName=sysDept.getDeptName();
        String examType=ExamType.getName(pacsBasexamltemSaveParam.getType());
        String examName=pacsBasexamltemSaveParam.getExamitemName();
        if(StrUtil.isEmpty(examId)){
            if(
                    baseMapper.selectCount(
                        new QueryWrapper<PacsBasexamltem>()
                                .eq("examitem_name",examName)
                                .eq("is_delete",0)
                                .eq("division_id",pacsBasexamltemSaveParam.getDivisionId())
                                .eq("type",pacsBasexamltemSaveParam.getType())
                    ).intValue()>0
            ){
                throw new ServiceException("保存失败，科室为"+deptName+"、体检类型为"+examType+"、名称为"+examName+"的检查项目已经存在");
            }

            PacsBasexamltem pacsBasexamltem=BeanUtil.toBean(pacsBasexamltemSaveParam,PacsBasexamltem.class);
            pacsBasexamltem.setId( String.valueOf(snowflake.nextId()));
            pacsBasexamltem.setIsDelete(0);
            baseMapper.insert(pacsBasexamltem);
            examId=pacsBasexamltem.getId();
        }else{
            if(
                    baseMapper.selectCount(
                            new QueryWrapper<PacsBasexamltem>()
                                    .eq("examitem_name",examName)
                                    .eq("is_delete",0)
                                    .eq("division_id",pacsBasexamltemSaveParam.getDivisionId())
                                    .eq("type",pacsBasexamltemSaveParam.getType())
                                    .ne("id",examId)
                    ).intValue()>0
            ){
                throw new ServiceException("保存失败，科室为"+deptName+"、体检类型为"+examType+"、名称为"+examName+"的检查项目已经存在");
            }

            PacsBasexamltem pacsBasexamltem=baseMapper.selectById(examId);
            BeanUtil.copyProperties(pacsBasexamltemSaveParam,pacsBasexamltem,"id");
            baseMapper.updateById(pacsBasexamltem);
        }


        List<PacsBasexamltemSignSaveParam> signs=pacsBasexamltemSaveParam.getSigns();
        for(PacsBasexamltemSignSaveParam sign:signs){
            if ("removed".equals(sign.get_state())) {
                PacsBasexamltemSign pacsBasexamltemSign=pacsBasexamltemSignMapper.selectById(sign.getId());
                pacsBasexamltemSign.setIsDelete(1);
                pacsBasexamltemSignMapper.updateById(pacsBasexamltemSign);
            }else if("modified".equals(sign.get_state())){
                PacsBasexamltemSign pacsBasexamltemSign=pacsBasexamltemSignMapper.selectById(sign.getId());
                if(pacsBasexamltemSign.getIsDelete().intValue()==1){
                    throw new ServiceException("保存失败，体征词"+sign.getName()+"已被删除");
                }
                if(
                        pacsBasexamltemSignMapper.selectCount(
                                new QueryWrapper<PacsBasexamltemSign>()
                                        .eq("is_delete",0)
                                        .eq("inspect_id",examId)
                                        .eq("name",sign.getName())
                                        .ne("id",sign.getId())
                        ).intValue()>0
                ){
                    throw new ServiceException("保存失败，体征词"+sign.getName()+"重复");
                }
                BeanUtil.copyProperties(sign,pacsBasexamltemSign,"id");
                pacsBasexamltemSign.setInputCode(ToolUtil.getHanziPinyinHeadChar(pacsBasexamltemSign.getName()));
                pacsBasexamltemSignMapper.updateById(pacsBasexamltemSign);
            }else if("added".equals(sign.get_state())){
                if(
                        pacsBasexamltemSignMapper.selectCount(
                                new QueryWrapper<PacsBasexamltemSign>()
                                        .eq("is_delete",0)
                                        .eq("inspect_id",examId)
                                        .eq("name",sign.getName())
                        ).intValue()>0
                ){
                    throw new ServiceException("保存失败，体征词"+sign.getName()+"重复");
                }
                PacsBasexamltemSign pacsBasexamltemSign=BeanUtil.toBean(sign,PacsBasexamltemSign.class);
                pacsBasexamltemSign.setInspectId(examId);
                pacsBasexamltemSign.setId(String.valueOf(snowflake.nextId()));
                pacsBasexamltemSign.setInputCode(ToolUtil.getHanziPinyinHeadChar(pacsBasexamltemSign.getName()));
                pacsBasexamltemSign.setIsDelete(0);
                pacsBasexamltemSignMapper.insert(pacsBasexamltemSign);
            }
        }
    }

    @Override
    public IPage<PacsConclusionVo> getConclusionList(PageParam pageParam,String inputCode){
        return baseMapper.getConclusionList(pageParam,inputCode);
    }
}
