package com.center.medical.framework.interceptor;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.center.medical.bean.enums.SqlCommandType;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.sync.service.SyncSqlRunService;
import com.center.medical.system.bean.model.SysTableConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 数据同步之数据变化拦截器：拦截增删改的操作，根据表数据同步配置记录需要同步的数据
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
@Slf4j
//@Intercepts({
//        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
//})
public class SyncDataInterceptor2 implements Interceptor, ApplicationContextAware {

    private MetaObjectHandler metaObjectHandler;
    private ApplicationContext applicationContext;

    @Autowired
    private LoadProperties loadProperties;
    @Autowired
    private Snowflake snowflake;
    private SyncSqlRunService syncSqlRunService;

    public SyncDataInterceptor2(MetaObjectHandler metaObjectHandler) {
        this.metaObjectHandler = metaObjectHandler;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 进行？的替换
     */
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 未知参数，替换？防止错位
                        sql = sql.replaceFirst("\\?", "unknown");
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 如果参数是String，则添加单引号
     * 如果参数是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
     */
    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            // 获取xml中的一个select/update/insert/delete节点，是一条SQL语句
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = null;
            // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            String id = mappedStatement.getId(); // 获取到节点的id,即sql语句的id
            BoundSql boundSql = mappedStatement.getBoundSql(parameter); // BoundSql就是封装myBatis最终产生的sql类
            Configuration configuration = mappedStatement.getConfiguration(); // 获取节点的配置
            String sql = showSql(configuration, boundSql); // 获取到最终的sql语句
            log.info("mybatis拦截解析: \n ID: \t{}\n SQL: \t{}", id, sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 执行完上面的任务后，不改变原有的sql执行过程
        return invocation.proceed();


////        log.error("当前系统：{}", loadProperties.name);
//        if (StringUtils.equals(loadProperties.name, "datamove")
//                || StringUtils.equals(loadProperties.name, "jindu")
////                || StringUtils.equals(loadProperties.name, "admin")
//        ) {
//            return invocation.proceed();
//        }
//
//        Object result = null;
//        Object[] args = invocation.getArgs();
//        MappedStatement mappedStatement = (MappedStatement) args[0];
//        Object parameter = args[1];
//        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
//
//        Object parameterObject = boundSql.getParameterObject();
//        log.info("SQL语句中的parameterObject：{}", parameterObject);
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        log.info("SQL语句中的parameterMappings：{}", parameterMappings);
//        //替换sql中的占位符？
//
//
//        int optType = SqlCommandType.getNum(getOperationType(mappedStatement));
//        log.info("当前sql操作类型：{}", optType);
//        if (optType != 2 && optType != 3 && optType != 4) {
//            //非增删改跳过拦截
//            return invocation.proceed();
//        }
//
//        // 获取完整的 SQL 语句
//        String sql = boundSql.getSql();
//
//        String tableName = extractTableName(sql, optType);
//        log.info("sql的表名：{}", tableName);
//        String pk = "";
//        Integer isAll = 0;
//        SysTableConfig tc;
//        if (StringUtils.isBlank(tableName)) {
//            return invocation.proceed();
//        } else {
//            tc = RedisUtil.get(Constants.SYS_TABLE_CONFIG + tableName);
////            log.info("数据表的配置信息：{}", tc);
//            if (!needSync(tc)) {
//                return invocation.proceed();
//            }
//            pk = tc.getKeyName();
////            cid = tc.getCidName();
//            isAll = tc.getNeedSync() == 1 ? 1 : 0;
//        }
//        SyncData syncData = new SyncData();
//        syncData.setOptType(optType);
//        syncData.setBizDb("medicalcenter");
//        syncData.setBizTable(tableName);
//        syncData.setBizModifydate(new Date());
//        syncData.setIsAll(isAll);
//        syncData.setStatus(0);
////        if (isAll == 0) {
////            String cidValue = getKeyValue(parameter, cid, optType);
////            syncData.setBranchIds(cidValue);
////        }
//        //获取SQL语句的操作类型
//        switch (optType) {
//            case 1:
//                //查询
//                break;
//            case 2:
//                //插入
//                result = invocation.proceed();
//                String pkValue = getKeyValue(parameter, pk, optType);
//                syncData.setBizId(pkValue);
//                RedisSetUtil.addToSortedSet(Constants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
//                log.info("执行插入同步数据:{}、{}", syncData);
//                break;
//            case 3:
//                //更新
//                String whereClause = extractWhereClause(sql);
//                log.info("完整的 SQL:{}、{}", sql, whereClause);
////                if (StringUtils.isNotBlank(whereClause)) {
////                    //构建完整的where条件
////                    String keyValue1 = getKeyValue(parameter, pk, optType);
////                    log.info("构建完整的where条件:{}", keyValue1);
////                    syncSqlRunService = applicationContext.getBean(SyncSqlRunService.class);
////                    String selectSql = "SELECT " + pk + " FROM " + tc.getTableName() + " WHERE " + whereClause;
////                    log.info("查询更新涉及的更新记录：{}", selectSql);
////                    try {
////                        List<Map<String, Object>> updateItems = syncSqlRunService.queryForList(selectSql);
////                        log.info("拦截器拦截更新涉及的更新记录：{}", updateItems);
////                        result = invocation.proceed();
////                        if (CollectionUtil.isNotEmpty(updateItems)) {
////                            String keyValue = CollectionUtil.join(updateItems, ",");
////                            String updateId = snowflake.nextIdStr();
////                            RedisUtil.set(Constants.SYNC_DATA_UPDATE + updateId, updateItems, DateUtil.currentSeconds());
////                            syncData.setBizId(keyValue + "," + updateId);
////                            RedisSetUtil.addToSortedSet(Constants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
////                            log.info("执行更新操作的主键:{}", keyValue);
////                        }
////                    } catch (EmptyResultDataAccessException e) {
////                        // 处理查询结果为空的情况
////                        log.info("处理查询结果为空的情况");
////                    }
////                }
//                result = invocation.proceed();
//                syncData.setBizId(getKeyValue(parameter, pk, optType));
//                RedisSetUtil.addToSortedSet(Constants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
//                log.info("执行更新操作的主键:{}", syncData.getBizId());
//                break;
//            case 4:
//                //TODO 删除
//                String keyValue = getKeyValue(parameter, pk, optType);
//                if (StringUtils.isNotBlank(keyValue)) {
//                    syncSqlRunService = applicationContext.getBean(SyncSqlRunService.class);
//                    log.info("SELECT * FROM " + tc.getTableName() + " WHERE " + tc.getKeyName() + " IN ( " + keyValue + ")");
//                    try {
//                        List<Map<String, Object>> delItems = syncSqlRunService.queryForList("SELECT * FROM " + tc.getTableName() + " WHERE " + tc.getKeyName() + " IN ( " + keyValue + ")");
//                        log.info("拦截器拦截删除操作：{}", delItems);
//                        result = invocation.proceed();
//                        if (CollectionUtil.isNotEmpty(delItems)) {
//                            String delId = snowflake.nextIdStr();
//                            RedisUtil.set(Constants.SYNC_DATA_DELETE + delId, delItems, DateUtil.currentSeconds());
//                            syncData.setBizId(keyValue + "," + delId);
//                            RedisSetUtil.addToSortedSet(Constants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
//                            log.info("执行删除操作的主键:{}", keyValue);
//                        }
//                    } catch (EmptyResultDataAccessException e) {
//                        // 处理查询结果为空的情况
//                        log.info("处理查询结果为空的情况");
//                    }
//
//                }
//                break;
//            case 5:
//                //刷新
//                result = invocation.proceed();
//                break;
//            default:
//                //未知或无法确定
//                result = invocation.proceed();
//        }
//
//        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }

    /**
     * 解析 SQL 语句，提取表名信息
     *
     * @param sql
     * @return
     */
    private String extractTableName(String sql, int optType) {
        // 解析 SQL 语句，提取表名信息
        // 这里使用简单的正则表达式示例来提取表名，适用于常见的情况
        // 请根据实际情况修改正则表达式或采用更复杂的解析方式
        String regex = "";
        switch (optType) {
            case 2:
                regex = "INSERT INTO\\s+(\\S+)";
                break;
            case 3:
                regex = "UPDATE\\s+(\\S+)";
                break;
            case 4:
                regex = "DELETE FROM\\s+(\\S+)";
                break;
            default:
                return null;
        }
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            String tn = matcher.group(1);
            // 截取的表名可能包含“(”，需要截取“(”之前的字符
            if (StrUtil.contains(tn, "(")) {
                return StrUtil.subBefore(tn, "(", false);
            }
            if (StrUtil.contains(tn, "`")) {
                return StrUtil.sub(tn, 1, tn.length() - 1);
            }
            return tn;
        }
        return null;
    }

    /**
     * 判断当前操作表是否需要同步
     *
     * @param tc
     * @return
     */
    private Boolean needSync(SysTableConfig tc) {
        if (Objects.isNull(tc) || tc.getNeedSync() == 0) {
            return Boolean.FALSE;
        }
        boolean online = ZhongkangConfig.isOnline();
        if (Objects.nonNull(tc) && tc.getNeedSync() != 0) {
            if (online) {
                //线上系统，判断该表是否是双向同步和线上同步线下
                if (tc.getSyncType() == 1 || tc.getSyncType() == 3) {
                    return Boolean.TRUE;
                }

            } else {
                //线下系统，判断该表是否是双向同步和线下同步线上
                if (tc.getSyncType() == 2 || tc.getSyncType() == 3) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 获取拦截的sql操作的数据表的主键
     *
     * @param parameter
     * @return
     */
    private String getKeyValue(Object parameter, String key, Integer optType) {
        //log.info("获取拦截的sql操作的数据表的主键parameter:{}、{}、{}", key, StrUtil.toCamelCase(key), JSONUtil.toJsonStr(parameter));
        String pkValue = "";
        switch (optType) {
            case 2:
                Map map = JSONUtil.parseObj(JSONUtil.toJsonStr(parameter)).toBean(Map.class);
                if (map.keySet().contains("param1")) {
                    Map bean = JSONUtil.parseObj(JSONUtil.toJsonStr(map.get("param1"))).toBean(Map.class);
                    pkValue = StrUtil.str(bean.get(StrUtil.toCamelCase(key)), "utf-8");
                } else {
                    pkValue = StrUtil.str(map.get(StrUtil.toCamelCase(key)), "utf-8");
                }
                break;
            case 3:
                // 构建完整的where条件
//                log.info("更新操作的参数1:{}", parameter);
                if (parameter instanceof Collection) {
                    log.info("更新操作的参数1:{}", parameter);
                    List<?> entities = (List<?>) parameter;
//                    log.info("更新操作的参数1:{}", entities);
                    for (Object entity : entities) {
//                        log.info("更新操作的参数1:{}", entity);
                        if (entity != null) {
                            if (key != null) {
                                //idValue = extractIdValue(entity, key);
                                System.out.println("Updated/Deleted ID: ");
                            }
                        }
                    }
                } else if (parameter instanceof Map) {
                    Map<?, ?> paramMap = (Map<?, ?>) parameter;
                    log.info("更新操作的参数2:{}", parameter);
                    if (paramMap.keySet().contains(key)) {
                        pkValue = StrUtil.str(paramMap.get(StrUtil.toCamelCase(key)), "utf-8");
                        break;
                    }
                    Object entity = null;
                    if (paramMap.keySet().contains("param1")) {
                        entity = paramMap.get("param1");
                    } else if (paramMap.keySet().contains("et")) {
                        entity = paramMap.get("et");
                    }

                    if (entity != null) {
                        if (key != null) {
                            Field[] fields = entity.getClass().getDeclaredFields();
                            for (Field field : fields) {
                                // Check if the field is the primary key field (e.g., annotated with @TableId or named "id")
                                if (field.isAnnotationPresent(TableId.class) || field.getName().equalsIgnoreCase(key)) {
                                    field.setAccessible(true);
                                    try {
                                        pkValue = StrUtil.str(field.get(entity), "utf-8");
                                        break;
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    log.info("更新操作的参数3:{}", parameter);
                }
                break;
            case 4:
                Map<?, List<String>> paramMap = (Map<?, List<String>>) parameter;
                log.info("删除操作的参数4：{}", paramMap);
                List<String> values = new ArrayList<>();
                if (paramMap.keySet().contains("param1")) {
                    values = paramMap.get("param1");
                } else if (paramMap.keySet().contains("coll")) {
                    values = paramMap.get("coll");
                }
                pkValue = values.stream().map(value -> "'" + value + "'").collect(Collectors.joining(","));
                break;
            default:

        }
        //log.info("获取拦截的sql操作的数据表的主键:{}", pkValue);
        return pkValue; // 替换为你的主键字段名
    }


    /**
     * *数据库操作类型
     *
     * @param mappedStatement
     * @return
     * @see SqlCommandType
     */
    private String getOperationType(MappedStatement mappedStatement) {
        String operationType = mappedStatement.getSqlCommandType().toString();
        //log.info("数据库操作类型：{}", operationType);
        return operationType;

    }


    private String extractWhereClause(String sql) {
        int whereIndex = sql.indexOf("WHERE");
        if (whereIndex >= 0) {
            return sql.substring(whereIndex + 5);
        }
        return "";
    }

    /**
     * 插入操作
     *
     * @param mappedStatement
     * @return
     */
    private String insert(MappedStatement mappedStatement) {
        String operationType = mappedStatement.getSqlCommandType().toString();
        log.info("数据库操作类型：{}、{}", operationType);
        return operationType;

    }

    /**
     * 更新操作
     *
     * @param mappedStatement
     * @return
     */
    private String update(MappedStatement mappedStatement) {
        String operationType = mappedStatement.getSqlCommandType().toString();
        log.info("数据库操作类型：{}、{}", operationType);
        return operationType;

    }

    /**
     * 删除操作
     *
     * @param mappedStatement
     * @return
     */
    private String delete(MappedStatement mappedStatement) {
        String operationType = mappedStatement.getSqlCommandType().toString();
        log.info("数据库操作类型：{}、{}", operationType);
        return operationType;

    }


    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        // Do nothing
    }
}
