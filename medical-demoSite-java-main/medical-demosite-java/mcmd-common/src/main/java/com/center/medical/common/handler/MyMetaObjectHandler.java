package com.center.medical.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-10-13 18:14
 * @description: 主动装配属性默认值
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("主动装配属性默认值insertFill----");
        this.setFieldValByName("createdate", new Date(), metaObject);
        this.setFieldValByName("modifydate", new Date(), metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("主动装配属性默认值updateFill----");
        this.setFieldValByName("modifydate", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
