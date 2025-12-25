package com.center.medical.sync.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.sync.service.SyncSqlRunService;
import com.center.medical.system.bean.model.SysTableConfig;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.SysTableConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据库操作实现
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
@Slf4j
@Service("syncSqlRunService")
@RequiredArgsConstructor
public class SyncSqlRunServiceImpl implements SyncSqlRunService {

    private final JdbcTemplate jdbcTemplate;
    private final SysTableConfigService sysTableConfigService;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ISysBranchService iSysBranchService;

    /**
     * 执行sql查询列表语句
     *
     * @param sql 可执行的sql语句
     * @return
     */
    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        // log.info("数据同步查询sql：{}", sql);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            for (String key : map.keySet()) {
                Object value = map.get(key);
                if (value instanceof java.sql.Date || value instanceof java.time.LocalDateTime) {
                    if (value != null) {
                        map.put(key, value.toString());
                    }
                }
            }
        }
        return maps;
    }

    /**
     * 执行sql查询数量
     *
     * @param sql 可执行的sql语句
     * @return
     */
    @Override
    public Integer queryCount(String sql) {
//        log.info("执行sql查询数量：{}", sql);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * 执行sql查询一条记录语句
     *
     * @param sql 可执行的sql语句
     * @return
     */
    @Override
    public Map<String, Object> queryForMap(String sql) {
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
//        log.error("查询结果转换时间格式1：{}", JSONUtil.toJsonStr(map));
        for (String key : map.keySet()) {
            Object value = map.get(key);
//            log.error("查询结果转换时间格式1.2：{}、{}", key, value);
//            log.error("查询结果转换时间格式2：{}、{}、{}、{}", key, value, value instanceof java.sql.Timestamp, value.getClass().getName());
            if (value instanceof java.sql.Date || value instanceof java.time.LocalDateTime) {
                if (value != null) {
//                    log.info("查询结果转换时间格式3：{}", value.toString());
                    map.put(key, value.toString());
                }
            }

        }
//        log.error("查询结果转换时间格式4：{}", JSONUtil.toJsonStr(map));
        return map;
    }

    /**
     * 执行sql查询一条记录语句，并返回对应的类
     *
     * @param sql   可执行的sql语句
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型
     */
    @Override
    public <T> T queryForObject(String sql, Class<T> clazz) {
        return jdbcTemplate.queryForObject(sql, clazz);
    }

    /**
     * 执行增删改操作
     *
     * @param sql 可执行的sql语句
     * @return
     */
    @Override
    public Integer updateSql(String sql) {
//        log.info("数据同步执行增删改操作：{}", sql);
        return jdbcTemplate.update(sql); //result返回结果，0代表影响行数为0（即失败），1代表影响行数为1（即成功）
    }

    /**
     * 执行增删改操作
     *
     * @param tableName 数据表名
     * @param sqlData   执行数据
     * @return
     */
    @Override
    public Integer updateOrInsert(String tableName, Map<String, Object> sqlData) {
//        log.info("数据库操作实现1.updateOrInsert：{}、{}", tableName, sqlData);

        //INSERT操作的构造字段，如：(id, name, age)
        Set<String> keySet = sqlData.keySet();
        String keyStr = CollectionUtil.join(keySet, ", ");
        //构造INSERT操作的字段值占位符，如：(:id, :name, :age)
        List<String> keySet1 = keySet.stream().map(item -> ":" + item).collect(Collectors.toList());
        String insertValueStr = CollectionUtil.join(keySet1, ", ");
        //构造UPDATE操作的key和字段值占位符，如：(:id, :name, :age)
        List<String> keySet2 = keySet.stream().map(item -> item + " =:" + item).collect(Collectors.toList());
        String updateValueStr = CollectionUtil.join(keySet2, ", ");

        //字段值
        List<String> keys = keySet.stream().collect(Collectors.toList());
        List<Object> values = sqlData.values().stream().collect(Collectors.toList());

        String sql = "INSERT INTO " + tableName + " (" + keyStr + ") VALUES (" + insertValueStr + ") ON DUPLICATE KEY UPDATE " + updateValueStr;
//        log.info("数据库操作实现2.updateOrInsert：{}", sql);

        MapSqlParameterSource params = new MapSqlParameterSource();
        for (int i = 0; i < keySet.size(); i++) {
            params.addValue(keys.get(i), values.get(i));
        }
//        log.info("数据库操作实现.updateOrInsert：{}", params);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * 根据配置和数据对象获取分中心ID
     *
     * @param tc
     * @param item
     * @return
     */
    @Override
    public List<String> getCid(SysTableConfig tc, Map<String, Object> item) {
        if (StringUtils.isNotBlank(tc.getCidName())) {
            if (StringUtils.isNotBlank((String) item.get(tc.getCidName()))) {
//                log.info("同步的分中心1：{}", item.get(tc.getCidName()));
                //判断是否满足直接使用：tc.getCidName()的条件
//                Boolean flag = Boolean.FALSE;
//                switch (tc.getTableName()) {
//                    case "md_peispatient":
//                        String patientcode = (String) item.get("patientcode");
//                        if (patientcode.startsWith("APP")) {
//                            flag = Boolean.TRUE;
//                        }
//                        break;
//                    default:
//                        break;
//                }
//                if (!flag) {
                String branchId = (String) item.get(tc.getCidName());
                if (StringUtils.isNotBlank(branchId)) {
//                    log.info("分中心字段：{}、{}", branchId, Arrays.stream(branchId.split(",")).collect(Collectors.toList()));
                    List<String> cids = new ArrayList<>();
                    for (String s : branchId.split(",")) {
                        //判断该分中心是否开启同步
                        if (StringUtils.isNotBlank(s) && iSysBranchService.isOpened(s)) {
                            cids.add(s);
                        }
                    }
                    return cids;

                }
                return null;
//                }XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX,
            }

        }
        if (Objects.nonNull(tc.getPid())) {
            SysTableConfig ptc = sysTableConfigService.getInfoById(tc.getPid());
            if (Objects.nonNull(ptc)) {
                //查询同步数据对象
                String sqlStr = String.format("SELECT * FROM " + ptc.getTableName() + " WHERE " + tc.getPkeyName() + " = '" + item.get(tc.getCurKeyName()) + "'");
                List<Map<String, Object>> maps = queryForList(sqlStr);
                List<String> cidList = new ArrayList<>();
                maps.forEach(i -> {
                    List<String> cids = getCid(ptc, i);
                    if (CollectionUtil.isNotEmpty(cids)) {
                        cidList.addAll(cids);
                    }

                });
//                log.info("同步的分中心2：{}", cidList);
                return cidList;
            }

        }
        return null;

    }

    /**
     * 构建更新语句
     *
     * @param tableName   表名
     * @param pk          主键名
     * @param queryResult 更新内容
     * @return
     */
    @Override
    public String generateUpdateSql(String tableName, String pk, List<Map<String, Object>> queryResult) {
//        log.warn("数据同步构建的更新sql语句：{}、{}、{}", tableName, pk, queryResult);
        StringBuilder updateSql = new StringBuilder();
        for (Map<String, Object> row : queryResult) {
            StringBuilder setClause = new StringBuilder();
            StringBuilder whereClause = new StringBuilder();

            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String columnName = entry.getKey();
                Object value = entry.getValue();

                // 构建 SET 子句
                if (!columnName.equals(pk)) {  // 排除主键列
                    if (setClause.length() > 0) {
                        setClause.append(", ");
                    }
                    setClause.append(columnName).append(" = ").append(escapeValue(value));
                }

                // 构建 WHERE 子句
                if (columnName.equals(pk)) {
                    whereClause.append(pk + " = ").append(escapeValue(value));
                }
            }

            // 构建 UPDATE 语句
            if (setClause.length() > 0 && whereClause.length() > 0) {
                String updateStatement = String.format("UPDATE %s SET %s WHERE %s;",
                        tableName, setClause.toString(), whereClause.toString());
                updateSql.append(updateStatement).append("\n");
            }
        }
        String updateSqlStr = updateSql.toString();
        log.warn("数据同步构建的更新sql语句：{}", updateSqlStr);
        return updateSqlStr;
    }

    private String escapeValue(Object value) {

        if (value == null) {
            return "NULL";
        }
        // 根据值的类型进行适当的转义和引号处理
        // log.warn("参数值的数据类型：{}", value.getClass().getSimpleName());
        if (value instanceof String) {
            return "'" + escapeString((String) value) + "'";
        } else if (value instanceof java.sql.Date || value instanceof java.sql.Time || value instanceof java.sql.Timestamp || value instanceof LocalDateTime
                || value instanceof Date || value instanceof LocalDate || value instanceof LocalTime) {
            return "'" + value + "'";
        }

        // 处理其他数据类型的转义和引号处理

        return value.toString();
    }

    private String escapeString(String value) {
        // 处理字符串中的特殊字符
        value = value.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
        return value;
    }


    /**
     * 构建插入语句
     *
     * @param tableName   表名
     * @param queryResult 数据内容
     * @return
     */
    @Override
    public String generateInsertSql(String tableName, List<Map<String, Object>> queryResult) {
//        log.warn("数据同步构建的插入sql语句：{}、{}、{}", tableName, queryResult);

        //获取表字段名
        Set<String> columnNames = queryResult.get(0).keySet();
//        log.warn("数据同步构构建的插入sql语句的表字段名：{}", columnNames);
        String insertSqlTemplate = "INSERT INTO " + tableName + "(" +
                String.join(", ", columnNames) +
                ") VALUES ";
        //构建值
        String insertValues = queryResult.stream()
                .map(row -> "(" + row.values().stream().map(i -> escapeValue(i)).collect(Collectors.joining(", ")) + ")")
                .collect(Collectors.joining(", "));

        String insertSqlStr = insertSqlTemplate + insertValues;
        log.warn("数据同步构构建的插入sql语句：{}", insertSqlStr);
        return insertSqlStr;
    }


    /**
     * 构建删除语句
     *
     * @param tableName 表名
     * @param pkName    主键名
     * @param pk        主键值
     * @return
     */
    @Override
    public String generateDeleteSql(String tableName, String pkName, String pk) {
//        log.warn("数据同步构建删除的sql语句：{}、{}、{}", tableName, pkName, pk);
        if (!pk.startsWith("'")) {
            pk = "'" + pk + "'";
        }
        String delSqlStr = "DELETE FROM " + tableName + " WHERE " + pkName + " IN (" + pk + ")";
//        log.warn("数据同步构构建的删除sql语句：{}", delSqlStr);
        return delSqlStr;
    }

}

