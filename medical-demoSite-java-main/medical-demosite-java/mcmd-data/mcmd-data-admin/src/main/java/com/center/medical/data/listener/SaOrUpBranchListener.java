package com.center.medical.data.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.SaOrUpBranchEvent;
import com.center.medical.data.dao.ConclusionAndFzxMapper;
import com.center.medical.data.dao.ExamAndFzxMapper;
import com.center.medical.data.dao.ItemsAndFzxMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:47
 * @description: 分中心新增更新监听事件，为新中心生成一些基础数据
 */
@Slf4j
@Component("createDataSaOrUpBranchListener")
@AllArgsConstructor
public class SaOrUpBranchListener {

    private final ItemsAndFzxMapper itemsAndFzxMapper;
    private final ConclusionAndFzxMapper conclusionAndFzxMapper;
    private final ExamAndFzxMapper examAndFzxMapper;

    @EventListener(SaOrUpBranchEvent.class)
    public void createDataListener(SaOrUpBranchEvent event) {
        log.info("分中心新增更新监听事件,为新中心生成一些基础数据：{}", JSONUtil.toJsonStr(event));
        Integer flag = event.getFlag();
        if (Objects.equals(flag, 1)) {
            //新中心生成一些基础数据
            //插入公共收费项目分中心关联记录
            itemsAndFzxMapper.addWithBrandId(event.getBranchId());

            //插入公共检查项目分中心关联记录
            examAndFzxMapper.addWithBrandId(event.getBranchId());

            //插入公共结伦词分中心关联记录
            conclusionAndFzxMapper.addWithBrandId(event.getBranchId());
        }
    }
}
