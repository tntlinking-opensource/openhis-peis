package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZyHarmClass;
import com.center.medical.data.dao.DwHarmMapper;
import com.center.medical.data.dao.ZyHarmClassMapper;
import com.center.medical.data.service.ZyHarmClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

/**
 * 职业危害因素分类(ZyHarmClass)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:00
 */
@Slf4j
@Service("zyHarmClassService")
@RequiredArgsConstructor
public class ZyHarmClassServiceImpl extends ServiceImpl<ZyHarmClassMapper, ZyHarmClass> implements ZyHarmClassService {

    private final ZyHarmClassMapper zyHarmClassMapper;
    private final MapperFacade mapperFacade;
    private final DwHarmMapper dwHarmMapper;
    private final Snowflake snowflake;


    /**
     * 分页查询[职业危害因素分类]列表
     *
     * @param page  分页参数
     * @param param ZyHarmClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyHarmClass> getList(PageParam<ZyHarmClass> page, ZyHarmClass param) {
        return zyHarmClassMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ZyHarmClass getInfoById(String id) {
        return zyHarmClassMapper.getInfoById(id);
    }

    @Override
    public String saveOrUpdateDwHarm(ZyHarmClass zh) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String name = loginUser.getUsername();
        // 判断是否为空
        if (StringUtils.isBlank(zh.getId())) {
            //判断是否存在重复的危害因素种类名称,排除删除数据有相同因素名称的影响
            ZyHarmClass zyNew = zyHarmClassMapper.selectOne(new QueryWrapper<ZyHarmClass>()
                    .eq("harm_class", zh.getHarmClass())
                    .and(wrapper -> wrapper.isNull("is_delete").or().eq("is_delete", 0)));
            if (null != zyNew) {
                return "保存失败！【" + zh.getHarmClass() + "】 名称重复";
            } else {
                //保存
                //设置isDelete字段为0
                zh.setDbUser(name);
                zh.setIsDelete(0);
//                zh.setId(String.valueOf(snowflake.nextId()));
                this.save(zh);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            ZyHarmClass zyNew = zyHarmClassMapper.selectOne(new QueryWrapper<ZyHarmClass>().eq("id", zh.getId()).eq("is_delete", 0));
            if (zyNew != null) {
                // 判断名称是否重复
                ZyHarmClass zyNews = zyHarmClassMapper.selectOne(new QueryWrapper<ZyHarmClass>()
                        .ne("id", zh.getId()).eq("harm_class", zh.getHarmClass()).eq("is_delete", 0));
                if (zyNews == null) {
                    // 更新实体类
                    BeanUtils.copyBeanProp(zyNew, zh);
                    this.updateById(zyNew);
                } else {
                    throw new ServiceException("更新失败：【" + zh.getHarmClass() + "】 名称重复");
                }
            } else {
                throw new ServiceException("更新失败：【" + zh.getHarmClass() + "】 名称已被删除");
            }
        }

        return "success";
    }


    @Override
    public IPage<ZyHarmClass> findAllHarmclass(PageParam<ZyHarmClass> page, String inputCode) {
        QueryWrapper<ZyHarmClass> queryWrapper = new QueryWrapper<ZyHarmClass>();
        //有筛选条件就加上筛选条件
        if (StringUtils.isNotEmpty(inputCode)) {
            queryWrapper.like("input_code", inputCode);
        }
        queryWrapper.eq("is_delete", 0);
        PageParam<ZyHarmClass> zyHarmClassPageParam = zyHarmClassMapper.selectPage(page, queryWrapper);
        return zyHarmClassPageParam;
    }
}

