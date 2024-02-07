package com.sinlff.server.config;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;

@Slf4j
@Component
public class MyInnerInterceptor extends JsqlParserSupport implements InnerInterceptor {

    @PostConstruct
    private void init(){
        log.info("MyInnerInterceptor初始化");
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        // 这里固定这么写就可以了
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
            return;
        }
        PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
        mpBs.sql(parserMulti(mpBs.sql(), null));
    }

    /**
     * 该方法由JsqlParserSupport提供,主要用于通过API的方式操作SQL
     * 思路:通过API构建出新的条件,并将新的条件和之前的条件拼接在一起
     */
    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        // 解析SQL
        Select selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;

        // 构建eq对象
        EqualsTo equalsTo = new EqualsTo(new Column("email"), new StringValue("tom.qq.com"));
        // 将原来的条件和新构建的条件合在一起
        AndExpression andExpression = new AndExpression(plainSelect.getWhere(), equalsTo);
        // 重新封装where条件
        plainSelect.setWhere(andExpression);
    }

/*
    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        insert.getColumns().add(new Column("name"));
        ((ExpressionList) insert.getWithItemsList()).getExpressions().add(new StringValue("tom"));
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        update.addUpdateSet(new Column("name"), new StringValue("tom"));
    }

    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        // 删除新增条件和查询一样,不做演示
    }
 */
}


