<!-- 流失客户 麦沃德科技 开发人: 清风/暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <div style="display: flex; flex: 1">
      <div style="width: 160px; height: 104%; margin: -12px 16px 0 0; border-right: 16px solid rgb(246, 247, 251); display: inline-block">
        <div style="text-align: left">
          <el-tag style="margin-top: 12px; background-color: transparent; border: 0 solid transparent; color: black; font-size: 16px; font-weight: 600">年份</el-tag>
        </div>
        <el-radio-group v-model="queryParams.year" @change="handlerChange" style="width: 160px; display: flex; flex-direction: column">
          <el-radio-button id="radioBox" v-for="item in yearOptions" :label="item.year" :value="item.year" :key="item.id"></el-radio-button>
        </el-radio-group>
      </div>
      <div class="flex-direction-column" style="flex: 1">
        <el-form size="small" :inline="true" :model="queryParams" class="no-margin-bottom">
          <el-form-item label="客户单位名称">
            <input-select ref="inputSelect" :selectData="revPersonIdData" clearable selectWidth="180" @change="issueIdChange"></input-select>
          </el-form-item>
          <!-- <el-form-item label="上次合同签订时间">
            <el-date-picker style="width: 230px" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="queryParams.htqdrq"></el-date-picker>
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button size="mini" type="primary" plain icon="el-icon-plus" @click="queryWindow()" v-hasPermi="['customer:lostCustomers:query']">查看</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button size="mini" type="primary" plain icon="el-icon-delete" :disabled="single" @click="handleConfirm" v-hasPermi="['customer:lostCustomers:delete']">确认流失</el-button>
          </el-col>
        </el-row>

        <div class="table-box">
          <el-table ref="tableData" :data="tableData" v-loading="loading" :border="true" :stripe="true" row-key="id" height="calc(100% - 60px)" @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
            <el-table-column prop="khdwmc" label="客户单位名称" align="center"></el-table-column>
            <el-table-column prop="khdwsrm" label="客户单位输入码" align="center"></el-table-column>
            <el-table-column prop="khdwlxr" label="客户单位联系人" align="center"></el-table-column>
            <el-table-column prop="khdh" label="客服电话" align="center"></el-table-column>
            <el-table-column prop="dateregister" label="客户创建时间" align="center"></el-table-column>
            <el-table-column prop="khzt" label="状态" align="center">
              <template slot-scope="scope">
                {{ ['潜在', '正式', '释放'][scope.row.khzt] }}
              </template>
            </el-table-column>
            <el-table-column prop="khdwzcdz" label="客户单位注册地址" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="htqdrq" label="上次签订合同时间" align="center"></el-table-column>
          </el-table>
        </div>

        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        <handleItems ref="handleItems"></handleItems>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllYear, getListData } from '@/api/customer/lost_customers'
import { setBan } from '@/api/customer/customer_list'
import handleItems from '@/views/customer/lost_customers/handle'
export default {
  name: 'Lost_customers',
  components: { handleItems },
  data() {
    return {
      ids: [],
      single: '',
      multiple: '',
      yearOptions: [],
      total: 0,
      loading: false,
      queryParams: {
        current: 1,
        size: 20,
        year: undefined,
        khdwsrm: undefined,
        khdwlxr: undefined,
        khdwmc: undefined,
        khdwmcid: undefined,
        htqdrq: undefined,
      },
      revPersonIdData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '客户单位名称', //第二列标题
        url: '/sell/sellpact/loseCust/getKhdwmcData', //请求连接
        bindValue: '',
        queryData: 'key',
        firstName: 'khdwsrm',
        secondName: 'khdwmc',
      },
      tableData: [],
      // 选择的数据
      selectInfo: [],
    }
  },
  created() {
    this.getYears()
  },
  methods: {
    // 获取数据
    getList() {
      this.loading = true
      getListData(this.queryParams).then(({ data }) => {
        this.tableData = data.records || []
        this.total = data.total
        this.loading = false
      })
    },
    //搜索
    search() {
      this.queryParams.current = 1
      this.getList()
    },
    // 获取年份
    getYears() {
      this.loading = true
      getAllYear().then(({ data }) => {
        this.yearOptions = data
        this.queryParams.year = data[data.length - 1].year
        this.loading = false
        this.getList()
      })
    },
    // 选择年份
    handlerChange(value) {
      this.queryParams.current = 1
      this.queryParams.year = value
      this.getList()
    },
    //查看
    queryWindow() {
      if (this.ids == 0 || this.single) {
        this.$alert('请选择一条记录！', '提醒', {
          confirmButtonText: '确定',
          type: 'warning',
        })
        return
      }
      this.$refs.handleItems.handleWindow(this.ids)
    },
    // 确认流失
    handleConfirm() {
      let err = ''
      this.selectInfo.forEach((el) => {
        if (el.khzt == 2) {
          err = '客户 ' + el.khdwmc + ' 已经被释放至公共池,无法操作'
        }
      })
      if (err) {
        this.$alert(err, '提示')
        return
      }
      this.$confirm('是否执行确认流失客户操作?', '提示')
        .then(() => {
          this.loading = true
          setBan({
            ids: this.ids.join(','),
            state: 0,
          })
            .then((res) => {
              this.$modal.msgSuccess('操作成功')
              this.loading = false
              this.getList()
            })
            .catch((err) => {
              console.error(err)
              this.loading = false
            })
        })
        .catch(() => {})
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selectInfo = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    //输入码选项
    issueIdChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    //重置
    resetQuery() {
      ;(this.queryParams.khdwmc = undefined), (this.queryParams.khdwsrm = undefined), (this.queryParams.khdwlxr = undefined)
      this.$refs.inputSelect.initData('')
    },
  },
}
</script>

<style scoped>
#radioBox /deep/ .el-radio-button__inner {
  text-align: left;
  width: 140px;
  border: 0px solid transparent;
  border-radius: 0;
}

#radioBox /deep/ .el-radio-button {
  box-shadow: inset !important;
}
</style>
