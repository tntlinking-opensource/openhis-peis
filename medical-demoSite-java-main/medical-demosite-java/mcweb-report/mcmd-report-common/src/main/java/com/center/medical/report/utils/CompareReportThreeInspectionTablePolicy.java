package com.center.medical.report.utils;

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.List;

public class CompareReportThreeInspectionTablePolicy extends DynamicTableRenderPolicy{
    @SuppressWarnings("unchecked")
	@Override
    public void render(XWPFTable table, Object data) {
        if (null == data) return;
        List<RowRenderData> detailData = (List<RowRenderData>) data;
        table.removeRow(1);//删除填充起始行
        // 循环插入行
        for (int i = 0; i < detailData.size(); i++) {
            XWPFTableRow insertNewTableRow = table.insertNewTableRow(i+1);
            for (int j = 0; j < 7; j++) insertNewTableRow.createCell();

            MiniTableRenderPolicy.Helper.renderRow(table, i+1, detailData.get(i));
        }
    }
}
