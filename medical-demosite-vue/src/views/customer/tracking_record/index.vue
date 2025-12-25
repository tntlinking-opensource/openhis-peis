<!-- 预检跟踪记录 麦沃德科技 开发人:清风/暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="small" :inline="true" class="no-margin-bottom">
      <el-form-item style="margin-right: 16px" label="单位名称">
        <el-input style="width: 230px" v-model="form.khdwmc" placeholder="请输入单位名称" clearable></el-input>
      </el-form-item>
      <el-form-item style="margin-right: 16px" label="联系电话">
        <el-input style="width: 230px" v-model="form.khlxdh" placeholder="请输入联系电话" clearable></el-input>
      </el-form-item>
      <el-form-item style="margin-right: 16px" label="销售经理">
        <el-input style="width: 230px" v-model="form.xsjl" placeholder="请输入销售经理" clearable></el-input>
      </el-form-item>
      <el-form-item style="margin-right: 16px" label-width="88px" label="沟通日期" clearable>
        <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" type="datetime" style="width: 200px" v-model="form.gtrq" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item style="margin-right: 16px" label-width="115px" label="下次沟通日期" clearable>
        <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" type="datetime" style="width: 200px" v-model="form.xcgtrq" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-upload2" plain @click="queryWindow()" :disabled="single" v-hasPermi="['customer:trackingRecord:query']">查看</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" row-key="id" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="khdwmc" label="客户单位名称" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="khlxdh" label="客户联系电话" align="center"></el-table-column>
        <el-table-column prop="sctjksrq" label="上次体检开始日期" align="center"></el-table-column>
        <el-table-column prop="gtjg" label="沟通结果" align="center"></el-table-column>
        <el-table-column prop="gtrq" label="沟通日期" align="center"></el-table-column>
        <el-table-column prop="xcgtrq" label="下次沟通日期" align="center"></el-table-column>
        <el-table-column prop="bcgtfs" label="本次沟通方式" align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.bcgtfs == 0">电话</div>
            <div v-if="scope.row.bcgtfs == 1">QQ</div>
            <div v-if="scope.row.bcgtfs == 2">面对面</div>
            <div v-if="scope.row.bcgtfs == 3">其它</div>
          </template>
        </el-table-column>
        <el-table-column prop="xsjl" label="销售经理" align="center"></el-table-column>
        <el-table-column prop="bz" label="备注" align="center"></el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="form.current" :limit.sync="form.size" @pagination="getList" />
    <queryItems ref="queryItems"></queryItems>
  </div>
</template>

<script>
import queryItems from './query.vue'
import { listPrinttype } from '@/api/customer/tracking_record'
export default {
  name:'Tracking_record',
  components: { queryItems },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
      },
      form: {
        current: 1,
        size: 10,
        khdwmc: '',
        khlxdh: '',
        xsjl: '',
        gtrq: '',
        xcgtrq: '',
        date: '',
      },
      tableData: [],
      selection: {},
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      listPrinttype(this.form).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection[0]
      }
    },
    // 搜索
    handleQuery() {
      this.form.current = 1
      if (this.form.date) {
        this.form.startTime = this.form.date[0] + ' 00:00:00'
        this.form.endTime = this.form.date[1] + ' 23:59:59'
      } else {
        this.form.startTime = undefined
        this.form.endTime = undefined
      }
      this.getList()
    },
    //重置
    resetQuery() {},
    queryWindow() {
      this.$refs.queryItems.queryWindow(this.selection.id)
    },
  },
}
</script>
