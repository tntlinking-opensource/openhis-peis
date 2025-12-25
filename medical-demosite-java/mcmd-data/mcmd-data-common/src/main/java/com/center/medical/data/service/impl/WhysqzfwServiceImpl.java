package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.Whysqzfw;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.WhysqzfwMapper;
import com.center.medical.data.service.WhysqzfwService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * JC危害因素取值范围(Whysqzfw)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:52
 */
@Slf4j
@Service("whysqzfwService")
@RequiredArgsConstructor
public class WhysqzfwServiceImpl extends ServiceImpl<WhysqzfwMapper, Whysqzfw> implements WhysqzfwService {

    private final WhysqzfwMapper whysqzfwMapper;
    private final HarmMapper harmMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[JC危害因素取值范围]列表
     *
     * @param page  分页参数
     * @param param Whysqzfw查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Whysqzfw> getList(PageParam<Whysqzfw> page, Whysqzfw param) {
        return whysqzfwMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Whysqzfw getInfoById(String id) {
        return whysqzfwMapper.getInfoById(id);
    }


    @Override
    public String saveOrUpdateWhysqzfw(Whysqzfw fw) {
        // 判断是否为空
        if (StringUtils.isBlank(fw.getId())) {
            //判断是否存在重复的危害因素名称,排除删除数据有相同因素名称的影响
            Whysqzfw fwn = whysqzfwMapper.selectOne(new QueryWrapper<Whysqzfw>()
                    .eq("harm_name", fw.getHarmName()).eq("jc_id", fw.getJcId())
                    .eq("sex", fw.getSex()).eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(fwn)) {
                // 以下是通过存入的ID，获取文本信息，给用户提醒
                String sex[] = {"男", "女", "通用"};
                Harm harm = harmMapper.selectOne(new QueryWrapper<Harm>().eq("is_delete", 0).eq("id", fw.getHarmName()));
                Basexamltem basExamLtem = basexamltemMapper.selectOne(new QueryWrapper<Basexamltem>().eq("is_delete", 0).eq("id", fw.getJcId()));
                return "保存失败！存在【危害因素名称：" + harm.getHarmName() +
                        "项目名称：" + basExamLtem.getExamitemName() +
                        "性别：" + sex[fw.getSex()] + "】数据相同。";
            } else {
                //保存
                //设置isDelete字段为0
                fw.setIsDelete(0);
                fw.setId(String.valueOf(snowflake.nextId()));
                this.save(fw);
            }
            // 编辑
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            Whysqzfw fwn = whysqzfwMapper.selectOne(new QueryWrapper<Whysqzfw>().eq("id", fw.getId())
                    .eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(fwn)) {
                // 判断名称是否重复
                Whysqzfw fwns = whysqzfwMapper.selectOne(new QueryWrapper<Whysqzfw>()
                        .eq("harm_name", fw.getHarmName()).eq("jc_id", fw.getJcId())
                        .eq("sex", fw.getSex()).eq("is_delete", 0));
                if (fwns == null) {
                    // 更新实体类
                    fw.setModifydate(new Date());
                    this.updateById(fw);
                } else {
                    // 以下是通过存入的ID，获取文本信息，给用户提醒
                    String sex[] = {"男", "女", "通用"};
                    Harm harm = harmMapper.selectOne(new QueryWrapper<Harm>().eq("is_delete", 0)
                            .eq("id", fw.getHarmName()));
                    Basexamltem basExamLtem = basexamltemMapper.selectOne(new QueryWrapper<Basexamltem>()
                            .eq("is_delete", 0).eq("id", fw.getJcId()));
                    throw new ServiceException("更新失败！存在【危害因素名称：" + harm.getHarmName() +
                            " 项目名称：" + basExamLtem.getExamitemName() +
                            " 性别：" + sex[fw.getSex()] + "】数据相同。");
                }
            } else {
                throw new ServiceException("更新失败！数据已被删除");
            }
        }
        return "success";
    }


    @Override
    public String removeWhysqzfw(String ids) {
        String flg = "success";
        // 将获取的多个ID分解
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            Whysqzfw whys = whysqzfwMapper.selectOne(new QueryWrapper<Whysqzfw>()
                    .eq("id", id[i]).eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(whys)) {
                //将isDelete设置为1，为删除
                whys.setIsDelete(1);
                this.updateById(whys);
            } else {
                throw new ServiceException("删除失败");
            }
        }
        return flg;
    }
}

