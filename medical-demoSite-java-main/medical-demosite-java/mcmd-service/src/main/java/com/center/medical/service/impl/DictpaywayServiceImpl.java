package com.center.medical.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Dictpayway;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.DictpaywayMapper;
import com.center.medical.service.DictpaywayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * JC支付方式表(Dictpayway)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
@Slf4j
@Service("dictpaywayService")
@RequiredArgsConstructor
public class DictpaywayServiceImpl extends ServiceImpl<DictpaywayMapper, Dictpayway> implements DictpaywayService {

    private final DictpaywayMapper dictpaywayMapper;
    //    private final KingdeepaywayMapper kingdeepaywayMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param Dictpayway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Dictpayway> getList(PageParam<Dictpayway> page, Dictpayway param) {
        return dictpaywayMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Dictpayway getInfoById(String id) {
        return dictpaywayMapper.getInfoById(id);
    }


    /**
     * 新增或修改数据
     *
     * @param odis 实体对象
     * @return
     */
    @Override
    public String saveOrUpdateDictpayway(Dictpayway odis) {
//        //个检支付手段
//        if(StringUtils.isNotEmpty(odis.getThingKingdeeNumber())){
//            Kingdeepayway king = kingdeepaywayMapper.getInfoById(odis.getThingKingdeeNumber());
//            odis.setThingKingdeePaywayname(king.getAccountName());
//            odis.setThingKingdeeUseStatus(king.getUseStatusId());
//        }
//        //团体支付手段
//        if(StringUtils.isNotEmpty(odis.getGroupKingdeeNumber())){
//            Kingdeepayway king = kingdeepaywayMapper.getInfoById(odis.getThingKingdeeNumber());
//            odis.setThingKingdeePaywayname(king.getAccountName());
//            odis.setThingKingdeeUseStatus(king.getUseStatusId());
//        }
//        //团体支付手段
//        if(StringUtils.isNotEmpty(odis.getPosKingdeeNumber())){
//            Kingdeepayway king = kingdeepaywayMapper.getInfoById(odis.getThingKingdeeNumber());
//            odis.setThingKingdeePaywayname(king.getAccountName());
//            odis.setThingKingdeeUseStatus(king.getUseStatusId());
//        }
        // 判断是否为空
        if (StringUtils.isBlank(odis.getId())) {
            //判断是否存在重复的职业名称,排除删除数据有相同名称的影响
            Dictpayway harmNew = dictpaywayMapper.selectOne(new QueryWrapper<Dictpayway>().eq("payway_name", odis.getPaywayName()).eq("is_delete", 0));
            if (null != harmNew) {
                throw new ServiceException("保存失败！存在相同的名称");
            } else {
                //设置isDelete字段为0
                odis.setIsDelete(0);
                odis.setCreatedate(new Date());
                odis.setId(String.valueOf(snowflake.nextId()));
                this.save(odis);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            Dictpayway harmNew = dictpaywayMapper.selectOne(new QueryWrapper<Dictpayway>().eq("id", odis.getId())
                    .eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(harmNew)) {
                // 判断名称是否重复
                Dictpayway harmNews = dictpaywayMapper.selectOne(new QueryWrapper<Dictpayway>().ne("id", odis.getId())
                        .eq("payway_name", odis.getPaywayName()).eq("is_delete", 0));
                if (ObjectUtils.isEmpty(harmNews)) {
                    // 更新实体类
                    odis.setModifydate(new Date());
                    this.updateById(odis);
                } else {
                    throw new ServiceException("更新失败：<font color='red'>" + odis.getPaywayName() + "</font> 名称重复");
                }
            } else {
                throw new ServiceException("对象已删除，请刷新页面");
            }
        }
        return "success";
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @Override
    public String removeDictpayway(String ids) {
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            Dictpayway zyoc = dictpaywayMapper.selectOne(new QueryWrapper<Dictpayway>().eq("id", id[i]).eq("is_delete", 0));
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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String upgrade() {
//        Map<String,Object> mapType;
//        try {
//            String url = FileTypePath.KING_DEE_URL;
//            if(StringUtils.isEmpty(url)){
//                throw new ServiceException( "doc_config.properties里的KingdeeUrl未配置");
//            }
//            url = url+"upgradeKingdeePayWay";
//            // TODO: 2022/11/18 外网请求，等待处理
//            Map<String, String> result = DownUtil.doGet(url);
//            mapType = JSON.parseObject(result.get("msg"),Map.class);
//            //验证的是与本地搭建的服务的代码
//            if(!"成功".equals(mapType.get("msg").toString())){
//                return url+mapType.get("msg").toString();
//            }
//            if(!"200".equals(mapType.get("code").toString())){
//                return url+mapType.get("code").toString();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "金蝶获取失败："+e.getMessage();
//        }
//        return mapType.get("data").toString();

        return "外网请求，等待处理";
    }
}

