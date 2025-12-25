package com.center.medical.framework.interceptor;

import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.center.medical.framework.handler.MyDataPermissionHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * @author: 路飞船长
 * @date: 2023/3/18 15:25
 * @description: 数据权限配置工具
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchScopeInterceptor extends JsqlParserSupport implements InnerInterceptor {

    /**
     * 数据权限处理器
     */
    private MyDataPermissionHandler dataPermissionHandler;

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
//        log.info("将要执行的sql语句：{}", JSONUtil.toJsonStr(boundSql.getSql()));

//        log.info("将要执行的sql语句的参数：{}", JSONUtil.toJsonStr(boundSql.getParameterObject()));
//        log.info("ms:{}", ms);
//        log.info("parameter:{}", parameter);
//        log.info("rowBounds:{}", rowBounds);
//        log.info("resultHandler:{}", resultHandler);
//        log.info("boundSql:{}", boundSql);

        //TODO wait 判断当前用户需不需要进行分中心权限数据隔离处理
//        if (Objects.isNull(SecurityUtils.getAuthentication())){
//            log.info("当前操作是系统自行操作，无需做权限判断");
//            return;
//        }
//        if (Objects.nonNull(SecurityUtils.getAuthentication()) && SecurityUtils.hasRole("admin")){
//            log.info("当前用户是admin");
//            return;
//        }
//
//        if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) {
//            return;
//        }
//        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
//        mpBs.sql(this.parserSingle(mpBs.sql(), ms.getId()));
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
//        SelectBody selectBody = select.getSelectBody();
//        if (selectBody instanceof PlainSelect) {
//            this.setWhere((PlainSelect) selectBody, (String) obj);
//        } else if (selectBody instanceof SetOperationList) {
//            SetOperationList setOperationList = (SetOperationList) selectBody;
//            List<SelectBody> selectBodyList = setOperationList.getSelects();
//            selectBodyList.forEach(s -> this.setWhere((PlainSelect) s, (String) obj));
//        }
    }

    /**
     * 设置 where 条件
     *
     * @param plainSelect  查询对象
     * @param whereSegment 查询条件片段
     */
    private void setWhere(PlainSelect plainSelect, String whereSegment) {

//        Expression sqlSegment = this.dataPermissionHandler.getSqlSegment(plainSelect, whereSegment);
//        if (null != sqlSegment) {
//            plainSelect.setWhere(sqlSegment);
//        }
    }
}
