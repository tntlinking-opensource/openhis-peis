package com.center.medical.sync.service;

import com.center.medical.system.bean.model.SysTableConfig;

import java.util.List;
import java.util.Map;

/**
 * 线上数据同步操作业务接口
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
public interface SyncSqlRunService {

    /**
     * 执行sql查询列表语句
     *
     * @param sql 可执行的sql语句
     * @return
     */
    List<Map<String, Object>> queryForList(String sql);

    /**
     * 执行sql查询数量
     *
     * @param sql 可执行的sql语句
     * @return
     */
    Integer queryCount(String sql);

    /**
     * 执行sql查询一条记录语句
     *
     * @param sql 可执行的sql语句
     * @return
     */
    Map<String, Object> queryForMap(String sql);

    /**
     * 执行sql查询一条记录语句，并返回对应的类
     *
     * @param sql   可执行的sql语句
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型
     */
    <T> T queryForObject(String sql, Class<T> clazz);

    /**
     * 执行增删改操作
     *
     * @param sql 可执行的sql语句
     * @return
     */
    Integer updateSql(String sql);

    /**
     * 执行增删改操作
     *
     * @param tableName 数据表名
     * @param sqlData   执行数据
     * @return
     */
    Integer updateOrInsert(String tableName, Map<String, Object> sqlData);

    /**
     * 根据配置和数据对象获取分中心ID
     *
     * @param tc
     * @param item
     * @return
     */
    List<String> getCid(SysTableConfig tc, Map<String, Object> item);

    /**
     * 构建更新语句
     *
     * @param tableName   表名
     * @param pk          主键名
     * @param queryResult 更新内容
     * @return
     */
    String generateUpdateSql(String tableName, String pk, List<Map<String, Object>> queryResult);

    /**
     * 构建插入语句
     *
     * @param tableName   表名
     * @param queryResult 数据内容
     * @return
     */
    String generateInsertSql(String tableName, List<Map<String, Object>> queryResult);

    /**
     * 构建删除语句
     *
     * @param tableName 表名
     * @param pkName    主键名
     * @param pk        主键值
     * @return
     */
    String generateDeleteSql(String tableName, String pkName, String pk);
}

