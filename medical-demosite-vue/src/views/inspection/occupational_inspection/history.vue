<!-- 职业总检-查看详情-历史 麦沃德科技 开发人:清风 -->
<template>
  <div class="add-container">
    <el-dialog title="历史" class="add-items" :visible.sync="open" width="1600px" @close="handleClose" v-if="open" destroy-on-close append-to-body
      :close-on-click-modal="false">
      <div style="width:100%; height:806px; display:flex; flex-direction:row;">
        <div style="width:520px; display: flex; flex-direction: column;">
          <el-form size="small" :inline="true" v-show="showSearch" @submit.native.prevent>
            <el-form-item>
              <el-button type="warning" icon="el-icon-time" size="mini" @click="createReport">生成报告</el-button>
              <el-button type="success" icon="el-icon-edit-outline" :disabled="fpdf" size="mini" @click="queryReport">查看报告</el-button>
            </el-form-item>
          </el-form>
          <div style="width:520px; flex-grow: 1;">
            <el-table id="setTable" ref="tableData" :data="tableData" v-loading="loading" @row-click="rowClick"
              max-width="520px" :border="true" :stripe="true" @selection-change="handleSelectionChange" height="100%">
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
              <el-table-column prop="patientcode" label="体检号" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="medicaltype" label="体检类型" show-overflow-tooltip align="center">
                <template slot-scope="scope">
                  <div v-if="scope.row.medicaltype == 0">上岗前</div>
                  <div v-else-if="scope.row.medicaltype == 1">在岗期间</div>
                  <div v-else-if="scope.row.medicaltype == 2">离岗时</div>
                  <div v-else-if="scope.row.medicaltype == 3">离岗后</div>
                  <div v-else-if="scope.row.medicaltype == 4">应急</div>
                </template>
              </el-table-column>
              <el-table-column prop="orgName" label="体检单位" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column prop="totalTime" label="总检时间" show-overflow-tooltip align="center"></el-table-column>
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />
        </div>
        <div style="flex-grow:1; height:100%; margin-left: 16px;">
          <el-tabs v-model="name" id="setTabs" type="card" v-loading="loading">
            <el-tab-pane label="综述" name="first" class="setstyle">
              <el-input type="textarea" :value="selection.summarize" readonly
                style="padding:16px; border: 0;height: 100%;"></el-input>
            </el-tab-pane>
            <el-tab-pane label="职业结论及建议" name="second" class="setstyle">
              <el-input type="textarea" :value="selection.offer" readonly
                style="padding:16px; border: 0;height: 100%;"></el-input>
            </el-tab-pane>
            <el-tab-pane label="职业阳性结果" name="third" class="setstyle">
              <el-input type="textarea" :value="selection.posistive" readonly
                style="padding:16px; border: 0;height: 100%;"></el-input>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getHistoryData } from "@/api/inspection/health_inspection.js"
import { createReportData } from '@/api/preview/individual_report.js'

export default {
  data() {
    return {
      open: false,
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 10,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      tableData: [],
      name: 'first',
      //选中事件
      selection: {},

      //补充字段
      id:"",
      fpdf: false
    }
  },
  methods: {
    // 生成报告
    createReport(){
      if(this.selection.patientcode){
        const clLoading = this.$loading({ target: '#mainBox' })
        createReportData({
          dh: 1, //0 健康 1 职业
          isJy: 0,
          patientcode:  this.selection.patientcode,
          showAllImage: 0,
        }).then(() => {
          this.$modal.msgSuccess('生成报告成功', '提示')
          clLoading.close()
        }).catch(() => {
          clLoading.close()
        })
        this.historyWindow(this.id);
      } else {
        this.$alert('请选择体检号', '提示', {
          confirmButtonText: '确定',
          type: 'warning'
        }).then(() => {}).catch(() => { });
      }
    },
    // 查看报告
    queryReport(){
      console.log('urlPdf:', this.selection.urlPdf);
      if (!this.selection.urlPdf) {
        this.$alert('请先生成报告', '提示')
        return
      }
      let url = this.$getCookie('imgPath') + this.selection.urlPdf
      window.open(url, '_blank')
      return
    },
    historyWindow(id) {
      this.open = true;
      this.selection={
        summarize:"",
        offer:"",
        posistive:"",
      }
      this.id = id;
      var obj = {
        current: 1,
        dh: 1,
        id,
        size: 10,
      }
      getHistoryData(obj).then((res) => {
        if (res.code == 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
          if(res.data.fpdf == 1){
            this.fpdf = true;
          } else {
            this.fpdf = false;
          }
        } else {
          this.$message({
            message: res.msg,
            type: "warning"
          })
        }
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
      if (selection.length == 1) {
        this.selection = selection[0];
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection();//清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop());//最后一条数据
      };
    },
    getList() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
      }, 500)
    },
    //表格行单击事件
    rowClick(row) {
      this.$refs.tableData.toggleRowSelection(row);//选中当前行
    },
    //关闭弹窗
    handleClose() {
      this.tableData = [];
    }
  }
}
</script>

<style scoped>
.setstyle{
  border: 1px solid rgb(225,225,225);
  border-top: 0px;
  height:761px;
}
#setTabs /deep/ .el-tabs__header {
  margin-bottom: 0 !important;
}

#setTabs /deep/ .el-tabs__item {
  height: 44px;
}

#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}

#setTabs /deep/ .el-tabs__item:focus.is-active.is-focus:not(:active) {
  box-shadow: none;
}

#setTabs /deep/ .el-textarea__inner {
  border: 0;
  height: 100%;
}</style>