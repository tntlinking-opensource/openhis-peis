package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.data.bean.model.DwHarm;
import com.center.medical.data.dao.DwHarmMapper;
import com.center.medical.data.service.DwHarmService;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 单位危害因素(DwHarm)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
@Slf4j
@Service("dwHarmService")
@RequiredArgsConstructor
public class DwHarmServiceImpl extends ServiceImpl<DwHarmMapper, DwHarm> implements DwHarmService {

    private final DwHarmMapper dwHarmMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[单位危害因素]列表
     *
     * @param page  分页参数
     * @param param DwHarm查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DwHarm> getList(PageParam<DwHarm> page, DwHarm param) {
        return dwHarmMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DwHarm getInfoById(String id) {
        return dwHarmMapper.getInfoById(id);
    }


    /**
     * 客户单位危害因素假删
     * @param ids
     * @return
     */
    @Override
    public String removeDwHarm(String ids) {
        String flg = "success";
        // 将获取的多个ID分解
        String id[]=ids.split(",");
        for(int i=0;i<id.length;i++){
            DwHarm dw = dwHarmMapper.selectOne(new QueryWrapper<DwHarm>().eq("id", id[i]).eq("is_delete", 0));
            if(null !=dw){
                //将isDelete设置为1，为删除
                dw.setIsDelete(1);
                this.updateById(dw);
            }else{
                return "删除失败";
            }
        }
        return flg;
    }





    /**
     * 新增或修改
     * @param fh
     * @return
     */
    @Override
    public String saveOrUpdateDwHarm(DwHarm fh) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String name = loginUser.getUsername();
        // 判断是否为空
        if(StringUtils.isBlank(fh.getId())) {
            //判断是否存在重复的数据，即单位名称、部门名称、危害因素不能同时存在
            DwHarm fhn = dwHarmMapper.selectOne(new QueryWrapper<DwHarm>()
                    .eq("company_name", fh.getCompanyName()).eq("company_department",fh.getCompanyDepartment())
                    .eq("harm_name", fh.getHarmName())
                    .and(wrapper -> wrapper.isNull("is_delete").or().eq("is_delete", 0)));
            if (null != fhn) {
                return "保存失败！存在相同的名称";
            }
            else {
                //保存
                //设置isDelete字段为0
                fh.setDbUser(name);
                fh.setIsDelete(0);
                fh.setId(String.valueOf(snowflake.nextId()));
                this.save(fh);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            DwHarm fhn =  dwHarmMapper.selectOne(new QueryWrapper<DwHarm>().eq("id", fh.getId()).eq("is_delete", 0));
            if(ObjectUtils.isNotEmpty(fhn)){
                // 判断名称是否重复
                DwHarm fhns = dwHarmMapper.selectOne(new QueryWrapper<DwHarm>().ne("id", fh.getId())
                        .eq("company_name", fh.getCompanyName()).eq("company_department",fh.getCompanyDepartment())
                        .eq("harm_name",fh.getHarmName() ).eq("is_delete", 0));
                if(ObjectUtils.isEmpty(fhns)){
                    // 更新实体类
                    fh.setDbUser(name);
                    BeanUtils.copyBeanProp(fhn,fh);
                    this.updateById(fhn);
                }else{
                    throw new ServiceException("更新失败! 名称重复");
                }
            }else{
                throw new ServiceException("更新失败! 名称已被删除");
            }
        }

        return "success";
    }
}

