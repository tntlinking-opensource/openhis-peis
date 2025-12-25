package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.data.bean.model.EmphasisAskSymptom;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.dao.EmphasisAskSymptomMapper;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.OccupationSymptomMapper;
import com.center.medical.data.service.EmphasisAskSymptomService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * JC重点询问症状表(EmphasisAskSymptom)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:14
 */
@Slf4j
@Service("emphasisAskSymptomService")
@RequiredArgsConstructor
public class EmphasisAskSymptomServiceImpl extends ServiceImpl<EmphasisAskSymptomMapper, EmphasisAskSymptom> implements EmphasisAskSymptomService {

    private final EmphasisAskSymptomMapper emphasisAskSymptomMapper;
    private final HarmMapper harmMapper;
    private final Snowflake snowflake;
    private final OccupationSymptomMapper occupationSymptomMapper;

    /**
     * 分页查询[JC重点询问症状表]列表
     *
     * @param page  分页参数
     * @param param EmphasisAskSymptom查询参数
     * @return 分页数据
     */
    @Override
    public IPage<EmphasisAskSymptom> getList(PageParam<EmphasisAskSymptom> page, EmphasisAskSymptom param) {
        return emphasisAskSymptomMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public EmphasisAskSymptom getInfoById(String id) {
        return emphasisAskSymptomMapper.getInfoById(id);
    }


    /**
     * 保存或修改数据
     * @param emp
     * @return
     */
    @Override
    public String saveOrUpdateEmphasis(List<EmphasisAskSymptom> emp) {
        for (EmphasisAskSymptom emphasisAskSymptom : emp) {
            // 判断是否存在重复数据，排除删除数据的影响
            EmphasisAskSymptom emptNew = emphasisAskSymptomMapper.selectOne(new QueryWrapper<EmphasisAskSymptom>()
                    .eq("symptom", emphasisAskSymptom.getSymptom()).eq("harmname", emphasisAskSymptom.getHarmname())
                    .eq("disiase_type", emphasisAskSymptom.getDisiaseType()).eq("is_delete", 0));
            if(emptNew!=null){
                // 根据危害因素ID，判断危害因素是否重复
                Harm harm =harmMapper.selectOne(new QueryWrapper<Harm>().eq("is_delete", 0).eq("id", emphasisAskSymptom.getHarmname()));
                // 职业体检类别
                String[] zy = {"上岗前","在岗期间","离职时","离岗后","应急"};
                throw new ServiceException( "保存失败！存在相同的症状名称："
                        +emphasisAskSymptom.getSymptom()+" "+"危害因素："
                        +harm.getHarmName()+" 体检类别："
                        +zy[emphasisAskSymptom.getDisiaseType()]+"重点询问症状。");
            }else{
                emphasisAskSymptom.setIsDelete(0);
                emphasisAskSymptom.setId(String.valueOf(snowflake.nextId()));
                this.save(emphasisAskSymptom);
            }
        }
        return "success";
    }

    /**
     * 保存单个
     * @param emp
     * @return
     */
    @Override
    public String saveEdit(EmphasisAskSymptom emp) {
        // 判断是否存在数据,排除删除数据的影响
        // 判断是否假删、ID是否重复
        EmphasisAskSymptom emps = emphasisAskSymptomMapper.selectOne(new QueryWrapper<EmphasisAskSymptom>().eq("id", emp.getId()).eq("is_delete", 0));
        if(ObjectUtils.isNotEmpty(emps)){
            EmphasisAskSymptom emptNew = emphasisAskSymptomMapper.selectOne(new QueryWrapper<EmphasisAskSymptom>()
                   .ne("id", emp.getId()).eq("symptom", emp.getSymptom()).eq("harmname", emp.getHarmname())
                    .eq("disiase_type", emp.getDisiaseType()).eq("is_delete", 0));
            if(ObjectUtils.isEmpty(emptNew)){
                // 更新实体类
                this.updateById(emp);
            }else{
                String[] zy = {"上岗前","在岗期间","离职时","离岗后","应急"};
                Harm harm =harmMapper.selectOne(new QueryWrapper<Harm>().eq("is_delete", 0).eq("id", emp.getHarmname()));
                throw new ServiceException("更新失败！存在相同的【症状名称："
                        +emp.getSymptom()+" "+"危害因素："
                        +harm.getHarmName()+" "+"体检类别："
                        +zy[emp.getDisiaseType()]+"】重点询问症状");
            }
        }else{
            throw new ServiceException("更新失败,数据已被删除!");
        }
        return "success";
    }


    @Override
    public String removeEmp(String ids) {
        String flg = "success";
        // 将获取的多个ID分解
        String id[]=ids.split(",");
        for(int i=0;i<id.length;i++){
            EmphasisAskSymptom emp = emphasisAskSymptomMapper.selectOne(new QueryWrapper<EmphasisAskSymptom>()
                    .eq("id", id[i]).eq("is_delete", 0));
            if(null !=emp){
                //将isDelete设置为1，为删除
                emp.setIsDelete(1);
                this.updateById(emp);
            }else{
                return "删除失败";
            }
        }
        return flg;
    }



    @Override
    public String synchonize() {
        // TODO: 2022/11/21 同步有问题，待处理
        return "success";
    }


    private boolean executeUpdateSql(String sql) {
        try {
            implementSql(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    //执行sql
    private void implementSql(String sql) {
        emphasisAskSymptomMapper.implementSql(sql);
    }


}

