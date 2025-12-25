<!-- 预检跟踪 麦沃德科技 开发人: 清风/暴雨 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="单位名称" style="margin-right: 16px">
        <el-input style="width: 180px" placeholder="请输入单位名称" v-model="form.khdwmc" clearable></el-input>
      </el-form-item>
      <el-form-item label="联系电话" style="margin-right: 16px">
        <el-input style="width: 180px" placeholder="请输入联系电话" v-model="form.khlxdh" clearable></el-input>
      </el-form-item>
      <el-form-item label="处理人" style="margin-right: 16px">
        <el-input style="width: 160px" placeholder="请输入处理人" v-model="form.clr" clearable></el-input>
      </el-form-item>
      <el-form-item label="上次体检日期" label-width="115px" style="margin-right: 16px">
        <el-date-picker value-format="yyyy-MM-dd" type="date" style="width: 180px" placeholder="选择日期" clearable v-model="form.startTime"></el-date-picker>
      </el-form-item>
      <el-form-item label="下次沟通日期" label-width="115px" style="margin-right: 16px">
        <el-date-picker value-format="yyyy-MM-dd" type="date" style="width: 180px" placeholder="选择日期" clearable v-model="form.endTime"></el-date-picker>
      </el-form-item>
      <el-form-item label="来检状态" style="margin-right: 16px">
        <el-select style="width: 200px" v-model="form.isExamed" clearable>
          <el-option :key="0" label="未来检" :value="0" />
          <el-option :key="1" label="已来检" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="handleWindow()" :disabled="single" v-hasPermi="['customer:inspectionTracking:handle']">处理</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="success" icon="el-icon-edit-outline" plain @click="inspectedWindow()" :disabled="single" v-hasPermi="['customer:inspectionTracking:inspected']">已来检</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange" row-key="id" height="100%">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="khdwmc" label="客户单位名称" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="khdwlxr" label="客户单位联系人" align="center"></el-table-column>
        <el-table-column prop="khlxdh" label="客户联系电话" align="center"></el-table-column>
        <el-table-column prop="sctjksrq" label="上次体检开始时间" align="center"></el-table-column>
        <el-table-column prop="clr" label="处理人" align="center"></el-table-column>
        <el-table-column prop="xcgtrq" label="下次沟通日期" align="center"></el-table-column>
        <el-table-column prop="clsj" label="处理时间" align="center"></el-table-column>
        <el-table-column label="处理状态" align="center" prop="clzt">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.clzt == 0" type="danger">未处理</el-tag>
            <el-tag v-else>已处理</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="来捡状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isExamed == 1">已来检</el-tag>
            <el-tag type="danger" v-else>未来检</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="form.current" :limit.sync="form.size" @pagination="getList" />
    <handleItems ref="handleItems" @getList="getList"></handleItems>
  </div>
</template>

<script>
import handleItems from './handle.vue'
import { getList, setExamed } from '@/api/customer/inspection_tracking'
export default {
  name:'Inspection_tracking',
  components: { handleItems },
  data() {
    return {
      selection: {},
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
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
        xb: undefined,
        idDepart: undefined,
        examfeeitemCode: undefined,
        idLabtype: undefined,
      },
      form: {
        current: 1,
        size: 10,
        khdwmc: '',
        khlxdh: '',
        clr: '',
        startTime: '',
        endTime: '',
        isExamed: '',
      },
      tableData: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getList(this.form).then((response) => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.loading = false
      })
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
    handleWindow() {
      if (!this.single) {
        this.$confirm('确定处理该记录?', '处理提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.$refs.handleItems.handleWindow(JSON.parse(JSON.stringify(this.rows[0])))
          })
          .catch(() => {})
      }
    },
    //设置已检
    inspectedWindow() {
      this.$confirm('确定将所选记录设置为已来检吗?', '处理提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          setExamed(this.ids).then((response) => {})
          this.$modal.msgSuccess('设置成功!')
          this.getList()
        })
        .catch(() => {})
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.rows = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>
