<!-- 新增客户 麦沃德科技 开发人: 清风 / 暴雨 -->
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
        <el-form size="small" :inline="true" label-width="80px" class="no-margin-bottom">
          <el-form-item label="单位名称" style="margin-right: 16px">
            <el-input style="width: 230px" v-model="queryParams.khdwmc" clearable placeholder="请输入单位名称"></el-input>
          </el-form-item>
          <el-form-item label="输入码" style="margin-right: 16px">
            <el-input style="width: 230px" v-model="queryParams.khdwsrm" clearable placeholder="请输入客户单位输入码"></el-input>
          </el-form-item>
          <el-form-item label="联系人" style="margin-right: 16px">
            <el-input style="width: 230px" v-model="queryParams.khdwlxr" clearable placeholder="请输入联系人"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="queryWindow()" v-hasPermi="['customer:newCustomers:query']">查看</el-button>
          </el-col>
        </el-row>

        <div class="table-box">
          <el-table ref="tableData" :data="tableData" v-loading="loading" :border="true" :stripe="true" row-key="id" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
            <el-table-column prop="khdwmc" label="客户单位名称" align="center"></el-table-column>
            <el-table-column prop="khdwsrm" label="客户单位输入码" align="center"></el-table-column>
            <el-table-column prop="khdwlxr" label="客户单位联系人" align="center"></el-table-column>
            <el-table-column prop="xsjldh" label="销售经理电话" align="center"></el-table-column>
            <el-table-column prop="khdh" label="客服电话" align="center"></el-table-column>
            <el-table-column prop="khdwzcdz" label="客户单位注册地址" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="xsjl" label="销售经理" align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
        <handleItems ref="handleItems"></handleItems>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllYear, getListData } from '@/api/customer/new_customers.js'
import handleItems from '@/views/customer/new_customers/handle'
export default {
  name:'New_customers',
  components: { handleItems },
  data() {
    return {
      ids: [],
      single: '',
      multiple: '',
      yearOptions: [],
      total: 30,
      loading: false,
      loadEnd: true,
      form: {
        khdwmc: '',
        dwsrm: '',
        dwlxr: '',
      },
      queryParams: {
        current: 1,
        size: 10,
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
        xb: undefined,
        idDepart: undefined,
        examfeeitemCode: undefined,
        idLabtype: undefined,
        year: undefined,
        khdwmc: undefined,
        khdwsrm: undefined,
        khdwlxr: undefined,
      },
      tableData: [
        {
          id: '',
          khdwmc: '',
          khdwsrm: '',
          khdwlxr: '',
          xsjldh: '',
          khdh: '',
          khdwzcdz: '',
          xsjl: '',
        },
      ],
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
        this.tableData = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 获取年份
    getYears() {
      this.loading = true
      getAllYear().then(({ data }) => {
        this.yearOptions = data
        this.queryParams.year = data[data.length - 1].year
        this.getList()
        this.loading = false
      })
    },
    // 选择年份
    handlerChange(value) {
      this.queryParams.current = 1
      this.queryParams.year = value
      this.getList()
    },
    //查询
    handleQuery() {
      this.queryParams.current = 1
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
    //选中的数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    //重置
    resetQuery() {
      this.queryParams.khdwmc = undefined
      this.queryParams.khdwsrm = undefined
      this.queryParams.khdwlxr = undefined
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
