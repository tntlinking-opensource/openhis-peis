<!-- 历史档案查询  开发人：麦沃德科技半夏 / 暴雨-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent class="no-margin-bottom">
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="身份证号" prop="idcardno">
        <el-input v-model="queryParams.idcardno" placeholder="请输入身份证号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box">
      <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="体检号" prop="patientcode" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="姓名" prop="patientname" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="性别" prop="idSex" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="年龄" prop="age" min-width="80" align="center" show-overflow-tooltip />
        <el-table-column label="电话" prop="phone" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="身份证号" prop="idcardno" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="婚姻" prop="marriage" min-width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-if="scope.row.marriage == 1">未婚</div>
            <div v-if="scope.row.marriage == 2">已婚</div>
            <div v-if="scope.row.marriage == 3">离异</div>
            <div v-if="scope.row.marriage == 4">丧偶</div>
            <div v-if="scope.row.marriage == 5">其他</div>
          </template>
        </el-table-column>
        <el-table-column label="出生日期" prop="birthdate" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="单位" prop="orgName" align="center" min-width="180" show-overflow-tooltip />
        <el-table-column label="部门" prop="orgDepart" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="套餐" prop="examsuiteName" align="center" min-width="160" show-overflow-tooltip />
        <el-table-column label="开单医生" prop="doctorapply" align="center" min-width="100" show-overflow-tooltip />
        <el-table-column label="登记时间" prop="dateregister" align="center" min-width="120" show-overflow-tooltip />
        <el-table-column label="总检结论" prop="suggestion" align="center" min-width="120" show-overflow-tooltip />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
  </div>
</template>
<script>
import { getListData } from '@/api/search/bk_patient'

export default {
  name: 'Bk_patient',
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
        khdwmc: undefined,
        ddh: undefined,
        patientcode: undefined,
        patientname: undefined,
      },
      // 排检表格数据
      tableList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
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
      this.resetForm('queryForm')
      this.handleQuery()
    },
  },
}
</script>
<style lang="scss"></style>
