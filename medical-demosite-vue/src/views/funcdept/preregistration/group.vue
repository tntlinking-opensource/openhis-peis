<!-- 检完签到 开发人：麦沃德科技暴雨 -->
<template>
  <el-dialog title="团体列表" :visible.sync="open" width="1580px" append-to-body style="overflow: hidden">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="订单号" prop="numorgresv">
        <el-input v-model="queryParams.numorgresv" placeholder="请输入订单号" clearable style="width: 150px" @keyup.enter.native="handleQuery" type="number"></el-input>
      </el-form-item>
      <el-form-item label="团体名称" prop="khdwmc">
        <el-autocomplete v-model="queryParams.khdwmc" v-loading="nameLoading" :fetch-suggestions="querySearchAsync" placeholder="请输入团体名称" @select="handleSelect" style="width: 150px"></el-autocomplete>
      </el-form-item>
      <el-form-item label="销售经理" prop="khdwlxr">
        <el-input v-model="queryParams.khdwlxr" placeholder="请输入团体联系人" clearable style="width: 150px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="销售联系方式" prop="khdh">
        <el-input v-model="queryParams.khdh" placeholder="请输入团体联系方式" clearable style="width: 150px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="套餐名称" prop="tjtcmc">
        <el-input v-model="queryParams.khdh" placeholder="请输入套餐名称" clearable style="width: 150px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="套餐输入码" prop="tjtcsrm">
        <el-input v-model="queryParams.tjtcsrm" placeholder="请输入套餐输入码" clearable style="width: 150px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table border v-loading="loading" :data="tableList" max-height="500px" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" width="55" type="index" align="center" />
      <el-table-column label="订单号" prop="ddh" align="center" show-overflow-tooltip width="120" />
      <el-table-column label="团体单位名称" prop="orgName" align="center" show-overflow-tooltip width="170" />
      <el-table-column label="团体分组" prop="groupname" align="center" show-overflow-tooltip />
      <el-table-column label="销售经理" prop="khdwlxr" align="center" show-overflow-tooltip />
      <el-table-column label="销售联系方式" prop="khdh" align="center" show-overflow-tooltip />
      <el-table-column label="团体地址" prop="address" align="center" show-overflow-tooltip />
      <el-table-column label="体检套餐" prop="tjtcmc" align="center" show-overflow-tooltip />
      <el-table-column label="创建时间" prop="date" align="center" show-overflow-tooltip />
    </el-table>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="open = false">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 筛选参数
      queryParams: {
        current: 1,
        size: 20,
        numorgresv: undefined,
        khdwmc: undefined,
        khdwlxr: undefined,
        khdh: undefined,
        tjtcmc: undefined,
        tjtcsrm: undefined,
      },
      // 团体名称loading
      nameLoading: false,
      // 表格数据
      tableList: [],
      // 表格加载
      loading: false,
      // 总数
      total: 0,
    }
  },
  methods: {
    showDialog() {
      this.open = true
    },
    // 获取表格数据
    getList() {
      this.loading = true
    },
    // 搜索团体名称
    querySearchAsync(str, callback) {
      // str为用户输入
      callback([
        { value: '三全鲜食（北新泾店）', address: '长宁区新渔路144号' },
        { value: 'Hot honey 首尔炸鸡（仙霞路）', address: '上海市长宁区淞虹路661号' },
      ])
    },
    // 点击选中建议项时触发
    handleSelect(item) {},
    // 查询
    handleQuery() {
      this.$modal.msg('查询成功')
    },
    // 表格选中
    handleSelectionChange() {},
  },
}
</script>

<style></style>
