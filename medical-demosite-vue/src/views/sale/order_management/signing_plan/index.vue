<!-- 签单计划 麦沃德科技 开发人:暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryParams" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="业务员" prop="name">
        <input-select :selectWidth="150" :selectData="saleSelectData" @change="saleSelectChange"></input-select>
      </el-form-item>
      <el-form-item label="订单名称" prop="ddmc">
        <el-input placeholder="请输入查询" clearable v-model="queryParams.ddmc" style="width:150px;"></el-input>
      </el-form-item>
      <el-form-item label="客户单位名称" prop="khdwmc">
        <el-input placeholder="请输入签单计划名称" clearable v-model="queryParams.khdwmc" style="width:150px;"></el-input>
      </el-form-item>
      <el-form-item label="当前状态" prop="status">
        <el-select v-model="queryParams.status" style="width:150px ;" placeholder="">
          <el-option label="失效 " :value="-1"></el-option>
          <el-option label="待提交" :value="0"></el-option>
          <el-option label="撞单处理中" :value="1"></el-option>
          <el-option label="待审核" :value="2"></el-option>
          <el-option label="审核通过" :value="3"></el-option>
          <el-option label="审核驳回" :value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收费日期">
        <el-date-picker v-model="queryParams.date" style="width: 300px;" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon- refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handerWindow">申请签单计划
        </el-button>
      </el-col>
    </el-row>

    <div class="table-box" style="display: flex; flex-direction: row;">
      <el-table :data="tableData" style="display:inline-block;flex-grow: 1;" v-loading="loading" :border="true" :stripe="true" height="100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" align="center" fixed="left"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center" fixed="left"></el-table-column>
        <el-table-column prop="progress" label="进度比例" min-width="160px" align="center" fixed="left">
          <el-progress :text-inside="true" :stroke-width="26" :percentage="70"></el-progress>
        </el-table-column>
        <el-table-column prop="progres" label="进度" min-width="100px" align="center" fixed="left">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.progres == 1">开始跟踪</el-tag>
            <el-tag v-else>开始跟踪</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="orderType" label="签单类型" min-width="100px" align="center" fixed="left"></el-table-column>
        <el-table-column prop="khdwmc" label="单位名称" min-width="160px" align="center"></el-table-column>
        <el-table-column prop="khdwdz" label="地址" min-width="160px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="状态" min-width="100px" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1" type="success">保护中</el-tag>
            <el-tag v-else type="danger">已撞单</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="newOrder" label="是否新单" min-width="60px" align="center">
          <template slot-scope="scope">

            <span v-if="scope.row.newOrder == 1" icon="el-icon-check" style="color: red;">×</span>
            <span v-else-if="scope.row.newOrder == 0" style="color: #409EFF;">√</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column prop="salesName" label="业务员" min-width="100px" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="deptName" label="所属部门" min-width="160px" align="center"></el-table-column>
        <el-table-column label="操作" align="center" min-width="160px" fixed="right" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button size="mini" type="text" style="color: #0059FF" @click="handleView(scope.row)">查看</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <handerItems ref="handerItems" @getFatherList="getList"></handerItems>
  </div>
</template>

<script>
import handerItems from "./hander.vue"
import { getListData, deleteListData, getDetailData, updatedData, addItem } from "@/api/sale/order_management/signing_plan.js"
export default {
  components: { handerItems },
  data() {
    return {
      showSearch: true,
      loading: false,
      // ids:[],//选中的数组
      single: true,//必选且单选
      multiple: true,//必选
      total: 10,//总数
      queryParams: {
        current: 1,//初始页数
        size: 20,//页值
        ddmc: '',
        khdwmc: '',
        status: '',
        date: [],
      },
      tableData: [
      ],
      // 业务员输入码选择
      saleSelectData: {
        placeholder: '请输入输入码选择',
        value: '客户单位名称', //第二列标题
        url: '/system/user/getListByKey', //请求连接
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        bindValue: '',
        firstName: 'username', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
    }
  },
  mounted() {

  },
  created() {
    this.getList();
  },
  methods: {
    //查询列表
    getList() {
      this.loading = true;
      getListData(this.queryParams).then(response => {
        this.tableData = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    //删除按钮
    handleDelete(row) {

      var id = row.id
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return deleteListData(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
        .catch(() => { });
    },
    //编辑按钮
    handleUpdate(row) {
      var id = row.id
      this.$refs.handerItems.handerWindow(id)
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + " 00:00:00"
        this.queryParams.endTime = this.queryParams.date[1] + " 23:59:59"
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.getList();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.rows = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    // 重置
    resetQuery() {
      this.queryParams.date = []
      this.queryParams.startTime = undefined
      this.queryParams.endTime = undefined
      this.resetForm("queryParams");
      this.handleQuery();
    },
    //打开弹窗
    handerWindow() {
      this.$refs.handerItems.handerWindow();
    },
    //业务员名称返回值
    saleSelectChange(value) {
      this.saleSelectData.bindValue = value.username
      this.queryParams.salesId = value.id
      // this.form.ddmc = value.khdwmc + new Date().getFullYear()
      // this.form.khdwdh = value.khdh
    },

  }
}
</script>

