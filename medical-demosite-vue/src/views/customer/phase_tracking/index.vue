<!-- 阶段跟踪 麦沃德科技 开发人 清风/矢北 -->
<template>
  <div class="app-container flex-direction-column phase-tracking">
    <el-form size="small" ref="queryForm" :model="queryParams" :inline="true" label-width="80px" class="no-margin-bottom">
      <el-form-item label="单位名称" prop="khdwmc" style="margin-right: 16px">
        <el-input style="width: 230px" v-model="queryParams.khdwmc" placeholder="请输入单位名称"></el-input>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjl" style="margin-right: 16px">
        <el-input style="width: 230px" v-model="queryParams.xsjl" placeholder="请输入销售经理名称"></el-input>
      </el-form-item>
      <el-form-item label="跟进阶段" prop="gjjd" style="margin-right: 16px">
        <el-select style="width: 230px" v-model="queryParams.gjjd" placeholder="请选择">
          <el-option v-for="options in gjjdOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="searchButton">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="queryWindow" :disabled="single" v-hasPermi="['customer:phaseTracking:query']">查看</el-button>
      </el-col>
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>
    <!-- 表格数据 -->
    <dragRow left-size="70%" right-size="30%">
      <template #leftBox>
        <div class="item-box">
          <div class="flex-direction-column">
            <div class="table-box">
              <el-table :data="tableData" height="100%" ref="leftTable" @selection-change="handleSelectionChange" @row-click="rowClick" v-loading="loading" :border="true" :stripe="true" row-key="id">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="khdwmc" label="客户单位名称" align="center"></el-table-column>
                <el-table-column prop="zt" label="主题" align="center"></el-table-column>
                <el-table-column prop="xsjl" label="销售经理" align="center"></el-table-column>
                <el-table-column prop="khdh" label="跟进阶段" align="center">
                  <template slot-scope="scope">
                    <span v-if="scope.row.gjjd == 0">需求交流</span>
                    <span v-else-if="scope.row.gjjd == 1">方案谈判</span>
                    <span v-else-if="scope.row.gjjd == 2">价格谈判</span>
                    <span v-else-if="scope.row.gjjd == 3">确认体检</span>
                    <span v-else-if="scope.row.gjjd == 4">合同签订</span>
                    <span v-else></span>
                  </template>
                </el-table-column>
                <el-table-column prop="ksrq" label="开始日期" align="center"></el-table-column>
                <el-table-column prop="jsrq" label="结束日期" align="center"></el-table-column>
                <el-table-column prop="jxsj" label="进行天数" align="center"></el-table-column>
                <el-table-column prop="gjnr" label="跟进内容" align="center"></el-table-column>
                <el-table-column prop="fzx" label="分中心" align="center" show-overflow-tooltip></el-table-column>
              </el-table>
            </div>
            <!-- 分页 -->
            <el-pagination
              background
              :pager-count="5"
              :current-page="queryParams.current"
              hide-on-single-page
              style="margin: 10px 5px"
              :page-sizes="[10, 20, 30, 50]"
              :page-size="queryParams.size"
              layout="sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            >
            </el-pagination>
          </div>
        </div>
      </template>

      <template #rightBox>
        <!-- 表2 -->
        <div class="item-box">
          <div class="flex-direction-column">
            <div class="table-box">
              <el-table :data="rightDataTable" ref="rightDataTable" v-loading="loadingR" @selection-change="handleSelectionChange2" :border="true" :stripe="true" row-key="id" height="100%">
                <el-table-column prop="khdwmc" label="客户单位名称" align="center"></el-table-column>
                <el-table-column prop="zt" label="主题" align="center"></el-table-column>
                <el-table-column prop="xsjl" label="销售经理" align="center"></el-table-column>
                <el-table-column prop="khdh" label="跟进阶段" align="center">
                  <template slot-scope="scope">
                    <span v-if="scope.row.gjjd == 0">需求交流</span>
                    <span v-else-if="scope.row.gjjd == 1">方案谈判</span>
                    <span v-else-if="scope.row.gjjd == 2">价格谈判</span>
                    <span v-else-if="scope.row.gjjd == 3">确认体检</span>
                    <span v-else-if="scope.row.gjjd == 4">合同签订</span>
                    <span v-else></span>
                  </template>
                </el-table-column>
                <el-table-column prop="ksrq" label="开始日期" align="center" min-width="100"></el-table-column>
                <el-table-column prop="jsrq" label="结束日期" align="center" min-width="100"></el-table-column>
                <el-table-column prop="jxsj" label="进行天数" align="center"></el-table-column>
                <el-table-column prop="gjnr" label="跟进内容" align="center" min-width="120"></el-table-column>
                <el-table-column prop="fzx" label="分中心" align="center" show-overflow-tooltip></el-table-column>
              </el-table>
            </div>
          </div>
        </div>
      </template>
    </dragRow>
    <lookUp ref="lookUp"></lookUp>
  </div>
</template>

<script>
import dragRow from '@/components/DragRow'
import { getList, getRightData } from '@/api/customer/phase_tracking'
import lookUp from './lookUp.vue'
export default {
  name:'Phase_tracking',
  components: { dragRow, lookUp },
  data() {
    return {
      leftParams: {
        id: undefined,
      },
      total: 0,
      loading: false,
      ids: [], //选中的数组
      single: true, //必选且单选
      multiple: '', //必选
      queryParams: {
        current: 1,
        size: 10,
        khdwmc: '',
        gjjd: '',
        xsjl: '',
      },
      loading: true,
      // 右侧表格加载中
      loadingR: false,
      // 跟进阶段0.需求交流
      // 1.方案谈判
      // 2.价格谈判
      // 3.体检确认
      // 4.合同签订
      gjjdOptions: [
        {
          id: 0,
          text: '需求交流',
        },
        {
          id: 1,
          text: '方案谈判',
        },
        {
          id: 2,
          text: '价格谈判',
        },
        {
          id: 3,
          text: '确认体检',
        },
        {
          id: 4,
          text: '合同签订',
        },
      ],
      //参数
      selection: {},
      form: {
        dwmc: '',
        dwsrm: '',
      },
      tableData: [],
      rightDataTable: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.selection = selection[0]
        this.ids.push(selection[0].khdwmcid)
        this.getRightList()
        this.single = selection.length != 1
      } else if (selection.length > 1) {
        this.$refs.leftTable.clearSelection() //清空表格数据
        this.$refs.leftTable.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        this.selection = []
        this.ids = []
        this.rightDataTable = []
      }
    },
    // 单击左表格
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.leftTable.clearSelection()
      this.$refs.leftTable.toggleRowSelection(row)
    },
    handleSelectionChange2(selection) {
      if (selection.length == 1) {
        this.selection = selection[0]
        this.ids.push(selection[0].id)
      } else if (selection.length > 1) {
        this.$refs.rightDataTable.clearSelection() //清空表格数据
        this.$refs.rightDataTable.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        this.selection = []
        this.ids = []
      }
      this.single = selection.length != 1
    },

    reset() {
      this.resetForm('queryForm')
      this.getList()
    },
    // 分页条数发生改变
    handleSizeChange(val) {
      if (this.queryParams.current * val > this.total) {
        this.queryParams.current = 1
      }
      this.queryParams.size = val
      this.getList()
    },
    // 分页页码发生改变
    handleCurrentChange(val) {
      this.queryParams.current = val
      this.getList()
    },
    searchButton() {
      this.getList()
    },
    getRightList() {
      this.loadingR = true
      getRightData(this.ids[0])
        .then((response) => {
          this.rightDataTable = response.data
          this.loadingR = false
        })
        .catch((error) => {
          console.error(error)
          this.loadingR = false
        })
    },
    //详情
    getList() {
      this.loading = true
      var queryParams = this.queryParams
      getList(queryParams).then((response) => {
        this.tableData = response.data.records
        this.loading = false
        this.total = response.data.total
      })
    },
    queryWindow() {
      this.$refs.lookUp.openDia(this.selection.id)
    },
  },
}
</script>

<style scoped>
::v-deep .el-table__expand-icon .el-icon-arrow-right:before {
  content: '+';
  color: #1890ff;
}

.item-box {
  background: #fff;
  height: 100%;
}

.phase-tracking /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
