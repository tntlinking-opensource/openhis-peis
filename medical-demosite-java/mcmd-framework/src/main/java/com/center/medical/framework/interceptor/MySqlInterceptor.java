package com.center.medical.framework.interceptor;

import com.center.medical.bean.enums.SqlCommandType;
import com.center.medical.bean.model.SqlOptionLog;
import com.center.medical.common.config.SyncTableConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

/**
 * @author: 路飞
 * @date: 2023-05-15 17:28
 * @description: mybatis-plus拦截器
 */
@Slf4j
@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class
        })
})
public class MySqlInterceptor implements Interceptor {

    @Resource
    private SyncTableConfig syncTableConfig;
//    @Resource
//    private BaseMapper<SqlOptionLog> sqlOptionLogBaseMapper;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        log.info("mybatis-plus拦截器invocation：{}", JSONUtil.toJsonStr(invocation));
        Object[] args = invocation.getArgs();
//        MappedStatement mappedStatement1 = (MappedStatement) args[0];
        Object parameter = args[1];
//        log.info("sql操作参数：{}", JSONUtil.toJsonStr(parameter));
//
//        String sql1 = mappedStatement1.getBoundSql(parameter).getSql();
//        log.info("获取拦截的sql语句:{}", sql1);
//        String operationType1 = getOperationType(mappedStatement1);
////        String tableName1 = getTableName(mappedStatement1);
        String primaryKey1 = getPrimaryKey(parameter);
        // 方法一
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //id为执行的mapper方法的全路径名，如com.uv.dao.UserMapper.insertUser
        String id = mappedStatement.getId();

        /**
         * sqlCommandType：sql语句类型
         * @see SqlCommandType
         */
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();
//        log.info("sql语句类型:{}", sqlCommandType);
        if ("INSERT".equals(sqlCommandType) || "UPDATE".equals(sqlCommandType) || "DELETE".equals(sqlCommandType)) {
            Object parameter1 = args[1];
//            log.info("增删改的参数：{}", JSONUtil.toJsonStr(parameter1));
        }

        BoundSql boundSql = statementHandler.getBoundSql();

        //获取到原始sql语句
        String sql = boundSql.getSql();
        String mSql = sql;

//        log.info("将要执行的sql语句MySqlInterceptor：{}", boundSql.getSql());
        //TODO 修改位置

        //注解逻辑判断  添加注解了才拦截
//        Class<?> classType = Class.forName(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf(".")));
//        String mName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1, mappedStatement.getId().length());
//        for (Method method : classType.getDeclaredMethods()) {
//            if (method.isAnnotationPresent(InterceptAnnotation.class) && mName.equals(method.getName())) {
//                InterceptAnnotation interceptorAnnotation = method.getAnnotation(InterceptAnnotation.class);
//                if (interceptorAnnotation.flag()) {
//                    mSql = sql + " limit 2";
//                }
//            }
//        }
        if (StringUtils.isNotEmpty(mSql)) {
            //分中心数据隔离
            if (mSql.contains("#sqltpl#")) {
                mSql = mSql.replaceAll("#sqltpl#", "fzx=2");
            }
        }
//        log.info("mSql:{}", mSql);

        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, mSql);

        //记录操作
        //saveSqlOptionLog(id, mSql);
        return invocation.proceed();

    }

    /**
     * 获取拦截的sql操作的数据表的主键
     *
     * @param parameter
     * @return
     */
    private String getPrimaryKey(Object parameter) {
        if (parameter instanceof Map) {
            Map<String, Object> paramMap = (Map<String, Object>) parameter;
//            log.info("获取拦截的sql操作的数据表的主键:{}", paramMap.get("id"));
            return (String) paramMap.get("id"); // 替换为你的主键字段名
        } else {
            // 如果使用实体类作为参数，可以根据实体类的具体情况获取主键值
            // 例如：return ((YourEntityClass) parameter).getId();
            return null;
        }
    }

    private void saveSqlOptionLog(String id, String mSql) {
        String tableName = "";
//        try {
//            tableName = getTableName(id);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        if (StringUtils.isBlank(tableName)) {
//            return;
//        }

        //判断是否当前操作的表是否是需要同步的表
        boolean online = ZhongkangConfig.isOnline();
        String tables = "";
        if (online) {
            tables = syncTableConfig.getOnline();
        } else {
            tables = syncTableConfig.getOffline();
        }
        List<String> tableList = Arrays.asList(tables.split(","));
//        log.info("当前配置可需同步的数据表：{}、{}、{}", id, tableName, tableList);
//         && tableList.contains(tableName)
//        if (CollectionUtils.isNotEmpty(tableList)) {
        String operationType = "";
        if (id.contains(".insert") || id.contains(".save")) {
            operationType = "插入";
        } else if (id.contains(".update") || id.contains(".edit")) {
            operationType = "更新";
        } else if (id.contains(".delete") || id.contains(".remove")) {
            operationType = "删除";
        } else {
            operationType = "";
        }
//        log.info("数据库操作类型：{}", operationType);
        if (StringUtils.isNotBlank(operationType)) {
            SqlOptionLog sqlOptionLog = new SqlOptionLog();
            sqlOptionLog.setSqlStatement(mSql);
            sqlOptionLog.setOperationType(operationType);
            sqlOptionLog.setOperationTime(new Date());
//            log.info("数据库操作记录：{}", sqlOptionLog);
//                sqlOptionLogBaseMapper.insert(sqlOptionLog);
        }

//        }


    }

    private String getTableName(String id) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        String className = id.substring(0, id.lastIndexOf("."));
        Class<?> entityClass = Class.forName(className);
        Field field = entityClass.getDeclaredField("TABLE_NAME");
        field.setAccessible(true);
        return (String) field.get(null);
    }

    public static void main(String[] args) {
        String sql = "COUNT(*) FROM sys_branch WHERE #sqltpl#";
        sql.replaceAll("#sqltpl#", "fzx=2");
        String result = sql.replaceAll("#FROM#", "wwwww");
        System.out.println(result);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }

    }

    @Override
    public void setProperties(Properties properties) {

    }
}
