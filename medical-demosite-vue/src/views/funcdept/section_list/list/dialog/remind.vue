<template>
  <el-dialog title="查看提醒" :visible.sync="open" width="1580px" append-to-body style="overflow: hidden" class="remind-dialog" :close-on-click-modal="false">
    <el-row style="height: ">
      <el-col :span="7">
        <div class="box content">
          <h3>提醒内容</h3>
          <el-input v-model="patientParams.content" placeholder="提醒内容" type="textarea" :rows="18" readonly> </el-input>
        </div>
        <div class="box people">
          <h3>体检人</h3>
          <el-form :model="patientParams" ref="patientForm" size="small" :inline="true" label-width="70px">
            <el-form-item label="体检号" prop="patientcode">
              <el-input v-model="patientParams.patientcode" clearable style="width: 320px" readonly> </el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="patientname">
              <el-input v-model="patientParams.patientname" clearable style="width: 320px" readonly> </el-input>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <el-col :span="17">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
          <el-form-item label="体检号" prop="patientcode">
            <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery"></el-input>
          </el-form-item>
          <el-form-item label="选择日期">
            <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleQuery"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button class="section-btn-style1" icon="el-icon-search" @click="handleQuery">查询</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table border v-loading="loading" ref="tableData" :data="tableList" height="530px" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column label="体检号" prop="patientcode" align="center" show-overflow-tooltip min-width="120" />
          <el-table-column label="姓名" prop="patientname" align="center" show-overflow-tooltip />
          <el-table-column label="科室" prop="depName" align="center" show-overflow-tooltip />
          <el-table-column label="提醒内容" prop="content" align="center" show-overflow-tooltip min-width="170" />
        </el-table>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getRemindDetails" />
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="open = false">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getRemindPatient, saveRemind, getRemindData } from '@/api/funcdept/section_list/dialog.js'
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 病人信息
      patientParams: {
        content: '',
        patientcode: undefined,
        patientname: undefined,
      },
      // 筛选参数
      queryParams: {
        current: 1,
        size: 10,
        patientcode: undefined,
        date: undefined,
        ksID: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 表格数据
      tableList: [],
      // 表格加载中
      loading: false,
      // 总数
      total: 0,
      // 表格选中的数据
      selectData: [],
    }
  },
  methods: {
    showDialog(patientcode) {
      this.open = true
      this.queryParams.patientcode = patientcode
      this.queryParams.ksID = this.$route.meta.ksID
      this.getRemindDetails()
    },
    // 查询
    handleQuery() {
      this.queryParams.current = 1
      this.getRemindDetails()
    },
    // 获取提醒详情
    getRemindDetails() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0]
        this.queryParams.endTime = this.queryParams.date[1]
      }
      this.patientParams.patientcode = undefined
      this.patientParams.patientname = undefined
      this.loading = true
      getRemindPatient(this.queryParams).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        if (data.records.length != 0) {
          this.patientParams = JSON.parse(JSON.stringify(data.records[0]))
          this.$nextTick(() => {
            this.$refs.tableData.toggleRowSelection(data.records[0])
          })
        } else {
          this.patientParams.content = '暂无提醒'
        }
        this.loading = false
      })
    },
    // 单击某行
    rowClick(row) {
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 左下表格多选框选中数据
    handleSelectionChange(selection) {
      this.selectData = selection.map((item) => item)
      if (selection.length == 1) {
        this.patientParams = selection[0]
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        // this.bpid = "";
      }
    },
  },
}
</script>

<style lang="scss">
.remind-dialog {
  .box {
    padding: 0 15px;
    margin-right: 15px;
    border: 1px solid #c4c4c4;

    h3 {
      font-weight: 600;
      font-size: 16px;
      line-height: 22px;
      color: #333333;
    }
  }

  .content {
    padding-bottom: 20px;
    margin-bottom: 10px;
  }

  .el-textarea__inner {
    background: #f6f7fb;
  }
}
</style>
<style scoped>
.remind-dialog /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
