<!-- 销售员回款 麦沃德科技 开发人:清风 / 暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <div style="display: flex; flex: 1">
      <div class="rightBorder">
        <div style="text-align: left">
          <el-tag class="tagBorder">年份</el-tag>
        </div>
        <el-radio-group v-model="option" @change="handlerChange" style="width: 160px; display: flex; flex-direction: column">
          <el-radio-button id="radioBox" v-for="item in yearOptions" :key="item.id" :label="item.year" :value="item.id"></el-radio-button>
        </el-radio-group>
      </div>

      <div class="flex-direction-column" style="flex: 1">
        <el-form :inline="true" label-width="80px" @submit.native.prevent>
          <el-form-item label="销售经理">
            <el-input style="width: 230px" v-model="queryParams.xsjl" placeholder="请输入销售经理" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="mini" icon="el-icon-search" @click="handlerChanges">搜索</el-button>
            <el-button size="mini" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="handleOperate(1)" :disabled="single" v-hasPermi="['finance:salesAccounting:paymentBySalesperson:entry']">财务录入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button size="mini" type="success" icon="el-icon-edit-outline" plain @click="handleOperate(2)" :disabled="single" v-hasPermi="['finance:salesAccounting:paymentBySalesperson:edit']">编辑</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="handleOperate(3)" :disabled="single" v-hasPermi="['finance:salesAccounting:paymentBySalesperson:query']">查看</el-button>
          </el-col>
        </el-row>
        <div class="table-box" style=" height: calc(100% - 35px)">
          <el-table ref="tableData" :data="tableData" v-loading="loading" align="center" :border="true" :stripe="true" @selection-change="handleSelectionChange" @row-click="rowClick" row-key="id" height="100%" style="width: 100%; max-width: 1660px;">
            <el-table-column type="selection"></el-table-column>
            <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
            <el-table-column prop="xsjl" min-width="120px" label="销售经理" align="center"></el-table-column>
            <el-table-column prop="yy" min-width="120px" label="一月(元)" align="center"></el-table-column>
            <el-table-column prop="ey" min-width="120px" label="二月(元)" align="center"></el-table-column>
            <el-table-column prop="sy" min-width="120px" label="三月(元)" align="center"></el-table-column>
            <el-table-column prop="iy" min-width="120px" label="四月(元)" align="center"></el-table-column>
            <el-table-column prop="wy" min-width="120px" label="五月(元)" align="center"></el-table-column>
            <el-table-column prop="ly" min-width="120px" label="六月(元)" align="center"></el-table-column>
            <el-table-column prop="qy" min-width="120px" label="七月(元)" align="center"></el-table-column>
            <el-table-column prop="ay" min-width="120px" label="八月(元)" align="center"></el-table-column>
            <el-table-column prop="jy" min-width="120px" label="九月(元)" align="center"></el-table-column>
            <el-table-column prop="oy" min-width="120px" label="十月(元)" align="center"></el-table-column>
            <el-table-column prop="ny" min-width="120px" label="十一月(元)" align="center"></el-table-column>
            <el-table-column prop="dy" min-width="120px" label="十二月(元)" align="center"></el-table-column>
            <el-table-column prop="bz" min-width="120px" label="备注" align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="handlerChanges" />
        <!-- 添加或修改对话框 -->
        <add-items ref="addItems" @getList="handlerChanges"></add-items>
        <edit-items ref="editItems" @getList="handlerChanges"></edit-items>
        <look-items ref="lookItems"></look-items>
      </div>
    </div>
  </div>
</template>

<script>
import { FinanceInput, getAllYears, getListData, isEdit, isView } from '@/api/finance/sales_accounting/payment_by_salesperson'
import addItems from './add'
import editItems from './edit'
import lookItems from './look'
export default {
  components: { addItems, editItems, lookItems },
  data() {
    return {
      showSearch: true,
      ids: [],
      single: true,
      multiple: true,
      option: '',
      yearOptions: [],
      total: 0,
      loading: false,
      loadEnd: true,
      userid: undefined,
      form: {
        xsjl: '',
      },
      queryParams: {
        current: 1,
        size: 10,
        xsjl: '',
        listYear: undefined,
      },
      tableData: [],
    }
  },
  created() {
    this.getAllYear()
    // this.getList()
  },
  methods: {
    // 操作按钮
    handleOperate(type) {
      if (!this.ids[0]) {
        this.$alert('请先执行财务录入', '提示')
        return
      }
      let forms = {
        fuserId: this.userid,
        fyear: this.queryParams.listYear,
      }
      if (this.queryParams.listYear == undefined) {
        this.$alert('请先选择年份！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      if (type == 1) {
        FinanceInput(forms).then((response) => {
          if (response.data == 'success') {
            this.$refs.addItems.handleAdd(forms)
          } else {
            this.$modal.msgWarning('当前选中人员已进行过财务录入,请勿重复录入')
          }
        })
      } else if (type == 2) {
        isEdit(forms).then((response) => {
          if (response.data == 'success') {
            this.$refs.editItems.handleAdd(this.ids)
          } else {
            this.$modal.msgWarning('当前选中人员无编辑权限')
          }
        })
      } else {
        isView(forms).then((response) => {
          if (response.data == 'success') {
            this.$refs.lookItems.handleAdd(this.ids)
          } else {
            this.$modal.msgWarning('当前选中人员无查看权限')
          }
        })
      }
    },
    resetQuery() {
      this.queryParams.xsjl = ''
      this.handlerChange()
    },
    //获取年份
    getAllYear() {
      this.loading = true
      getAllYears()
        .then((response) => {
          this.yearOptions = response.data
          this.option = response.data[response.data.length - 1].id
          this.handlerChanges()
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    //根据年份查询
    handlerChange(value) {
      if (value) this.queryParams.listYear = value
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    handlerChanges() {
      this.loading = true
      getListData(this.queryParams).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.userid = selection.map((item) => item.userid).toString()
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
  },
}
</script>

<style scoped>
#radioBox /deep/ .el-radio-button__inner {
  text-align: left;
  width: 100%;
  border: 0px solid transparent;
  border-radius: 0;
}
#radioBox /deep/ .el-radio-button {
  box-shadow: inset !important;
}
.rightBorder {
  width: 160px;
  height: 104%;
  margin: -12px 16px 0 0;
  border-right: 16px solid rgb(246, 247, 251);
  display: inline-block;
  border: 0px solid transparent;
  box-shadow: none !important;
}
.tagBorder {
  margin-top: 12px;
  /* margin-left: 12px;  */
  background-color: transparent;
  border: 0 solid transparent;
  color: black;
  font-size: 16px;
  font-weight: 600;
}
</style>
