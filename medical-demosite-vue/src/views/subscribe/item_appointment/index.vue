<!-- 项目预约明细  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="预约时间从" prop="startTime">
        <el-date-picker v-model="queryParams.startTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择"></el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="endTime">
        <el-date-picker v-model="queryParams.endTime" style="width: 160px" value-format="yyyy-MM-dd" type="date" placeholder="请选择"></el-date-picker>
      </el-form-item>
      <el-form-item label="" prop="containSingle">
        <el-checkbox :true-label="1" :false-label="0" v-model="queryParams.containSingle" label="含个检"></el-checkbox>
      </el-form-item>
      <el-form-item label="科室" prop="divisionId">
        <input-select :selectData="selectData1" @change="selectChange1"></input-select>
      </el-form-item>
      <el-form-item label="收费项目" prop="itemId">
        <input-select :selectData="selectData2" @change="selectChange2"></input-select>
      </el-form-item>
      <el-button class="zk-btn-style1" icon="el-icon-search" @click="handleQuery">搜索</el-button>
      <el-button class="zk-btn-style2" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      <el-button class="zk-btn-style2" icon="el-icon-upload2" @click="handleUpload" v-hasPermi="['subscribe:appointmentReview:approved']">导出</el-button>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe :span-method="objectSpanMethod">
        <el-table-column type="selection" width="45" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="预约日期" prop="reservationDate" align="center" show-overflow-tooltip />
        <el-table-column label="科室" prop="dept" align="center" show-overflow-tooltip />
        <el-table-column label="收费项目" prop="item" align="center" show-overflow-tooltip />
        <el-table-column label="体检形式" prop="sfwc" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ ['内检', '外检'][scope.row.sfwc] }}
          </template>
        </el-table-column>
        <el-table-column label="普通(上午)" align="center" show-overflow-tooltip>
          <el-table-column label="预约数量" prop="am" align="center" show-overflow-tooltip />
          <el-table-column label="上限" prop="amlimit" align="center" show-overflow-tooltip />
        </el-table-column>
        <el-table-column label="普通(下午)" align="center" show-overflow-tooltip>
          <el-table-column label="预约数量" prop="pm" align="center" show-overflow-tooltip />
          <el-table-column label="上限" prop="pmlimit" align="center" show-overflow-tooltip />
        </el-table-column>
        <el-table-column label="VIP" align="center" show-overflow-tooltip>
          <el-table-column label="预约数量" prop="vip" align="center" show-overflow-tooltip />
          <el-table-column label="上限" prop="viplimit" align="center" show-overflow-tooltip />
        </el-table-column>
        <el-table-column label="VVIP" align="center" show-overflow-tooltip>
          <el-table-column label="预约数量" prop="vvip" align="center" show-overflow-tooltip />
          <el-table-column label="上限" prop="vviplimit" align="center" show-overflow-tooltip />
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/subscribe/item_appointment'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        containSingle: 1,
        divisionId: undefined,
        itemId: undefined,
      },
      // 科室筛选数据
      selectData1: {
        placeholder: '请输入输入码选择',
        value: '科室', //第二列标题
        url: 'reception/register/getKsData', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        bindValue: '', //初始值
      },
      // 收费项目筛选数据
      selectData2: {
        placeholder: '请输入输入码选择',
        value: '收费项目', //第二列标题
        url: 'items/getAllItemsData', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px,传100%则为100%
        bindValue: '', //初始值
        secondName: 'examfeeitemName', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 排检表格数据
      tableList: [],
      // 计算表格合并索引
      cellList: [],
      cellList2: [],
    }
  },
  created() {
    this.queryParams.startTime = this.$getDate().slice(0, 10)
    this.queryParams.endTime = this.$getDate().slice(0, 10)
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      this.loading = true
      getListData(this.queryParams).then(({ data }) => {
        this.computeCell(data.records)
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.selectData1.bindValue = ''
      this.selectData2.bindValue = ''
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 计算要合并的单元格
    computeCell(tableBody) {
      this.cellList = []
      this.cellList2 = []
      let same = ''
      let same2 = ''
      tableBody.forEach((el, index) => {
        if (el.reservationDate != same) {
          this.cellList.push(index)
          same = el.reservationDate
        }
        if (el.dept != same2) {
          this.cellList2.push(index)
          same2 = el.dept
        }
      })
    },
    // 合并相同表格项目
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex == 2) {
        if (this.cellList.includes(rowIndex)) {
          let index = this.cellList.indexOf(rowIndex)
          let rowspan = undefined
          if (index == this.cellList.length - 1) {
            rowspan = this.tableList.length - this.cellList[index]
          } else {
            rowspan = this.cellList[index + 1] - this.cellList[index]
          }
          return {
            rowspan,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }
      if (columnIndex == 3) {
        if (this.cellList2.includes(rowIndex)) {
          let index = this.cellList2.indexOf(rowIndex)
          let rowspan = undefined
          if (index == this.cellList2.length - 1) {
            rowspan = this.tableList.length - this.cellList2[index]
          } else {
            rowspan = this.cellList2[index + 1] - this.cellList2[index]
          }
          return {
            rowspan,
            colspan: 1,
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }
    },
    // 科室筛选方法返回选中的值
    selectChange1(value) {
      this.queryParams.divisionId = value.id
      this.selectData1.bindValue = value.name
    },
    // 收费项目筛选方法返回选中的值
    selectChange2(value) {
      this.queryParams.itemId = value.id
      this.selectData2.bindValue = value.examfeeitemName
    },
    // 导出
    handleUpload() {
      if (this.queryParams.startTime) {
        this.queryParams.startTime = this.queryParams.startTime.slice(0, 10) + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        this.queryParams.endTime = this.queryParams.endTime.slice(0, 10) + ' 23:59:59'
      }
      this.download('/reservation/sortQuery/export', this.queryParams, `项目预约明细.xlsx`)
    },
  },
}
</script>
<style lang="scss" scoped>
.charts-box {
  width: 100%;
  height: 43%;
  border: 1px solid #c4c4c4;
  padding: 16px;
}
</style>
