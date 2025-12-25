package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.bean.vo.NotificationmethodVo;
import com.center.medical.data.dao.NotificationmethodMapper;
import com.center.medical.data.service.NotificationmethodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 通知方式（领取方式）表(Notificationmethod)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:33
 */
@Slf4j
@Service("notificationmethodService")
@RequiredArgsConstructor
public class NotificationmethodServiceImpl extends ServiceImpl<NotificationmethodMapper, Notificationmethod> implements NotificationmethodService {

    private final NotificationmethodMapper notificationmethodMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[通知方式（领取方式）表]列表
     *
     * @param page  分页参数
     * @param param Notificationmethod查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Notificationmethod> getList(PageParam<Notificationmethod> page, Notificationmethod param) {
        return notificationmethodMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Notificationmethod getInfoById(String id) {
        return notificationmethodMapper.getInfoById(id);
    }


    /**
     * 添加或修改
     *
     * @param odis 实体对象
     * @return
     */
    @Override
    public String saveOrUpdateNotificationmethod(Notificationmethod odis) {

        //获取当前用户
        String name = SecurityUtils.getLoginUser().getUsername();
        // 判断是否为空
        if (StringUtils.isBlank(odis.getId())) {
            //判断是否存在重复的职业名称,排除删除数据有相同名称的影响
            List<Notificationmethod> harmNew = notificationmethodMapper.selectList(new QueryWrapper<Notificationmethod>().eq("method_name", odis.getMethodName())
                    .eq("is_delete", 0));
            if (CollectionUtils.isNotEmpty(harmNew)) {
                throw new ServiceException("保存失败！存在相同的名称");
            } else {
                //保存
                odis.setCreater(name);
                //设置isDelete字段为0
                odis.setIsDelete(0);
                odis.setCreatedate(new Date());
                odis.setId(String.valueOf(snowflake.nextId()));
                this.save(odis);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            Notificationmethod harmNew = notificationmethodMapper.selectOne(new QueryWrapper<Notificationmethod>().eq("id", odis.getId()).eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(harmNew)) {
                // 判断名称是否重复
                Notificationmethod harmNews = notificationmethodMapper.selectOne(new QueryWrapper<Notificationmethod>().ne("id", odis.getId())
                        .eq("method_name", odis.getMethodName()).eq("is_delete", 0));
                if (ObjectUtils.isEmpty(harmNews)) {
                    // 更新实体类
                    odis.setModifydate(new Date());
                    this.updateById(odis);
                } else {
                    throw new ServiceException("更新失败：" + odis.getMethodName() + "名称重复");
                }
            } else {
                throw new ServiceException("对象已删除，请刷新页面");
            }
        }

        return "success";
    }


    @Override
    public String removeNotificationmethod(String ids) {
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            Notificationmethod zyoc = notificationmethodMapper.selectOne(new QueryWrapper<Notificationmethod>().eq("id", id[i]).eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(zyoc)) {
                //将isDelete设置为1，为删除
                zyoc.setIsDelete(1);
                zyoc.setModifydate(new Date());
                this.updateById(zyoc);
            } else {
                throw new ServiceException("删除失败");
            }
        }
        return "success";
    }

    /**
     * 获取所有的通知方式
     * @return
     */
    @Override
    public List<NotificationmethodVo> getIssueWayData() {
        return notificationmethodMapper.getIssueWayData();
    }
}

