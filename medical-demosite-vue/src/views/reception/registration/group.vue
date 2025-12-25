<template>
  <el-dialog title="团体列表" :visible.sync="open" width="1580px" append-to-body style="overflow:hidden" :close-on-click-modal="false">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input-number v-model="queryParams.numorgresv" controls-position="right" style="width:150px"
          @keyup.enter.native="handleQuery"></el-input-number>
      </el-form-item>
      <el-form-item label="团体名称" prop="khdwmcid">
        <input-select :selectData="selectData" @change="selectChange" :initialValue="queryParams.khdwmcid"></input-select>
      </el-form-item>
      <el-form-item label="销售经理" prop="khdwlxr">
        <el-input v-model="queryParams.khdwlxr" placeholder="请输入团体联系人" clearable style="width: 150px"
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="销售联系方式" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入团体联系方式" clearable style="width: 150px"
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="套餐名称" prop="tjtcmc">
        <el-input v-model="queryParams.tjtcmc" placeholder="请输入套餐名称" clearable style="width: 150px"
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="套餐输入码" prop="tjtcsrm">
        <el-input v-model="queryParams.tjtcsrm" placeholder="请输入套餐输入码" clearable style="width: 150px"
          @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table border ref="table" id="setTable" v-loading="loading" :data="tableList" height="485px" stripe
      @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="submitForm">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="订单号" prop="num" align="center" width="120" />
      <el-table-column label="团体单位名称" prop="orgName" align="center" show-overflow-tooltip width="170" />
      <el-table-column label="团体分组" prop="groupname" align="center" show-overflow-tooltip />
      <el-table-column label="销售经理" prop="xsjl" align="center" />
      <el-table-column label="销售联系方式" prop="phone" align="center" />
      <el-table-column label="团体地址" prop="address" align="center" show-overflow-tooltip />
      <el-table-column label="体检套餐" prop="tjtcmc" align="center" show-overflow-tooltip />
      <el-table-column label="创建时间" prop="date" align="center" />
    </el-table>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getGroupForOrgData } from "@/api/reception/registration";
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 筛选参数
      queryParams: {
        current: 1,
        size: 10,
        numorgresv: undefined,
        khdwmcid: undefined,
        khdwlxr: undefined,
        phone: undefined,
        tjtcmc: undefined,
        tjtcsrm: undefined,
      },
      // 表格数据
      tableList: [],
      // 选中数据
      selectRow: undefined,
      // 表格加载
      loading: false,
      // 总数
      total: 0,
      // 团体名称参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',//第一列标题
        value: '团体名称',//第二列标题
        url: '/abteilung/CrisisValue/getListData',//请求连接
        selectWidth: 150,//选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'khdwsrm',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc',//接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',//向接口传递的参数名(不传默认为'inputCode')
      },
    }
  },
  methods: {
    // 打开弹窗
    showDialog() {
      this.open = true
      this.handleQuery()
    },
    // 获取表格数据
    getList() {
      this.loading = true;
      getGroupForOrgData(this.queryParams).then(response => {
        this.tableList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 查询
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 选择体检团体返回值
    selectChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    // 表格选中
    handleSelectionChange(selection) {
      if (selection.length == 0) {
        this.selectRow = undefined
      } else if (selection.length == 1) {
        this.selectRow = selection[0]
      } else if (selection.length > 1) {
        this.$refs.table.clearSelection() //清空表格数据
        this.$refs.table.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col && col.type == "selection") {
        return
      }
      this.$refs.table.clearSelection();
      this.$refs.table.toggleRowSelection(row);
      this.selectRow = row;
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    // 提交按钮
    submitForm() {
      if(!this.selectRow) {
        this.$modal.alertWarning("请选中一条记录！");
        return
      }
      this.open = false;
      this.$emit("setGroupUnit", this.selectRow)
    },
  },
}
</script>

<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>