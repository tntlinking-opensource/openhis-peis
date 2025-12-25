<!-- 医生工作量  开发人：麦沃德科技 予安/清风 -->
<template>
  <el-tabs type="card" class="app-container flex-direction-column division-doctor-main" v-model="activeName" @tab-click="tabClick">
    <el-tab-pane v-for="item in tabsData" :key="item.index" :label="item.title" :name="item.index" class="flex-direction-column" style="height: 100%">
      <!-- 筛选 -->
      <el-form :model="queryParams" ref="queryParams" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
        <el-form-item prop="startTime" label="选择日期">
          <el-date-picker v-model="queryParams.startTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
        </el-form-item>
        <el-form-item prop="endTime" label="至">
          <el-date-picker v-model="queryParams.endTime" style="width: 180px" value-format="yyyy-MM-dd" type="date" placeholder="请选择" clearable> </el-date-picker>
        </el-form-item>
        <el-form-item label="检查项目" prop="examId" v-show="item.index == 0">
          <input-select :selectData="selectData" @change="selectChange"></input-select>
        </el-form-item>
        <el-form-item label="收费项目" prop="itemId" v-show="item.index == 1">
          <input-select :selectData="selectData2" @change="selectChange2"></input-select>
        </el-form-item>
        <el-form-item label="科室" prop="ks" v-show="item.index == 2">
          <input-select :selectData="selectData4" @change="selectChange4"></input-select>
        </el-form-item>
        <el-form-item label="医师" prop="name">
          <input-select :selectData="selectData3" @change="selectChange3"></input-select>
        </el-form-item>
        <el-form-item label="分中心" prop="fzx">
          <el-select v-model="queryParams.fzx" clearable style="width: 160px">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <el-button type="warning" plain icon="el-icon-upload2" size="mini" @click="handleExport(1)" v-if="item.index == 1">导出工作量(PACS)</el-button>
          <el-button type="warning" plain icon="el-icon-upload2" size="mini" @click="handleExport(2)" v-if="item.index == 1">导出工作量明细(PACS)</el-button>
          <el-button type="warning" plain icon="el-icon-upload2" size="mini" @click="handleExport(3)" v-if="item.index == 2">导出工作量(其他)</el-button>
          <el-button type="warning" plain icon="el-icon-upload2" size="mini" @click="handleExport(4)" v-if="item.index == 2">导出工作量明细(其他)</el-button>
        </el-form-item>
      </el-form>
      <drag-row left-size="60%" right-size="40%">
        <template #leftBox>
          <!-- 表格 -->
          <div class="table-box" v-loading="loading" style="height: 92%">
            <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="序列" width="55" type="index" align="center" />
              <el-table-column label="检查日期" show-overflow-tooltip prop="rummagerTime" align="center" min-width="120" />
              <el-table-column label="星期" prop="dayForWeek" align="center" min-width="80" />
              <el-table-column label="科室" prop="depName" align="center" min-width="160" v-if="item.index != 0" />
              <el-table-column label="收费项目" prop="examName" align="center" min-width="160" v-if="item.index == 1" />
              <el-table-column label="检查项目" prop="examName" align="center" min-width="160" v-if="item.index == 0" show-overflow-tooltip />
              <el-table-column label="医生" prop="username" align="center" min-width="120" />
              <el-table-column label="总工作量" prop="gzlTotal" align="center" min-width="120" />
              <el-table-column label="个检工作量" prop="gzlGr" align="center" min-width="120" />
              <el-table-column label="团检工作量" prop="gzlTt" align="center" min-width="120" />
              <el-table-column label="普通会员工作量" prop="common" align="center" min-width="120" />
              <el-table-column label="vip工作量" prop="vip" align="center" min-width="120" />
              <el-table-column label="vvip工作量" prop="vvip" align="center" min-width="120" />
              <el-table-column label="原始金额" prop="oriTotal" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="原始金额个人" prop="oriGr" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="原始金额团体" prop="oriTt" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="优惠金额" prop="yhTotal" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="优惠金额个人" prop="yhGr" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="优惠金额团体" prop="yhTt" align="center" min-width="120" v-if="item.index != 0" />
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="paginationHandle" />
        </template>
        <template #rightBox>
          <!-- 表格 -->
          <div class="table-box" v-loading="rightLoading" style="height: 92%">
            <el-table border v-loading="rightLoading" :data="tableData" height="100%" stripe @selection-change="handleSelectionChange">
              <el-table-column label="科室" prop="depName" align="center" min-width="160" v-if="item.index != 0" />
              <el-table-column label="收费项目" prop="examName" align="center" min-width="160" v-if="item.index == 1" />
              <el-table-column label="检查项目" prop="examName" align="center" min-width="160" v-if="item.index == 0" show-overflow-tooltip />
              <el-table-column label="医生" prop="username" align="center" min-width="120" />
              <el-table-column label="总工作量" prop="gzlTotal" align="center" min-width="120" />
              <el-table-column label="个检工作量" prop="gzlGr" align="center" min-width="120" />
              <el-table-column label="团检工作量" prop="gzlTt" align="center" min-width="120" />
              <el-table-column label="普通会员工作量" prop="common" align="center" min-width="120" />
              <el-table-column label="vip工作量" prop="vip" align="center" min-width="120" />
              <el-table-column label="vvip工作量" prop="vvip" align="center" min-width="120" />
              <el-table-column label="原始金额" prop="oriTotal" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="原始金额个人" prop="oriGr" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="原始金额团体" prop="oriTt" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="优惠金额" prop="yhTotal" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="优惠金额个人" prop="yhGr" align="center" min-width="120" v-if="item.index != 0" />
              <el-table-column label="优惠金额团体" prop="yhTt" align="center" min-width="120" v-if="item.index != 0" />
            </el-table>
          </div>
          <!-- 分页 -->
          <pagination v-show="rightTotal > 0" :total="rightTotal" :page.sync="queryRightParams.current" :limit.sync="queryRightParams.size" @pagination="paginationHandleRight" />
        </template>
      </drag-row>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import dragRow from '@/components/DragRow'
import { analyseTest, analyseTestTotal, getBranchData, analysePacs, analysePacsTotal, analyse, analyseTotal } from '@/api/statistical/workload/division_doctor.js'
export default {
  components: { dragRow },
  props: [],
  data() {
    return {
      // tabs数据
      tabsData: [
        { title: '检验科', index: '0' },
        { title: 'PACS科室', index: '1' },
        { title: '其他科室', index: '2' },
      ],
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        startTime: undefined,
        endTime: undefined,
        examId: undefined,
        itemId: undefined,
        ks: undefined,
        name: undefined,
        fzx: undefined,
      },
      queryRightParams: {
        current: 1,
        size: 20,
        dateRange: undefined,
        examId: undefined,
        itemId: undefined,
        ks: undefined,
        name: undefined,
        fzx: undefined,
      },
      // 检查项目参数
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '项目名称', //第二列标题
        url: '/statistics/divisionDoctor/getInspectExamData', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: '', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'examitemName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 收费项目参数
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        url: '/items/page', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: 'sfxmsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'examfeeitemName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'sfxmsrm', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 医师参数
      selectData3: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '医师姓名', //第二列标题
        url: '/drugstore/prescribe/getDoctor', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: '', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: '', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 科室参数
      selectData4: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/reception/register/getKsData', //请求连接
        selectWidth: 160, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(必传)
        firstName: '', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: '', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: '', //向接口传递的参数名(不传默认为'inputCode')
      },
      //分中心
      fzxOptions: [],
      // 遮罩层
      loading: true,
      rightLoading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 右
      rightTotal: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
      // 右表数据
      tableData: [],
      searchData: {},
      // 初始tabs索引
      activeName: '2',
      // tabs索引
      tabsIndex: '2',
    }
  },
  created() {
    //获取基础数据对象
    getBranchData().then((res) => {
      this.fzxOptions = res.data
    })
    this.queryParams.startTime = this.$getDate().split(' ')[0]
    this.queryParams.endTime = this.$getDate().split(' ')[0]
    this.handleQuery()
  },
  methods: {
    //左表分页查询
    paginationHandle() {
      this.searchData.current = this.queryParams.current
      this.searchData.size = this.queryParams.size
      if (this.tabsIndex == '0') {
        this.getList()
      } else if (this.tabsIndex == '1') {
        this.getPacsKsData()
      } else if (this.tabsIndex == '2') {
        this.getOtherKsData()
      }
    },
    //右表分页查询
    paginationHandleRight() {
      this.searchData.current = this.queryRightParams.current
      this.searchData.size = this.queryRightParams.size
      if (this.tabsIndex == '0') {
        this.getRightList()
      } else if (this.tabsIndex == '1') {
        this.getPacsKsDataRight()
      } else if (this.tabsIndex == '2') {
        this.getOtherKsDataRight()
      }
    },
    // 切换标签页
    tabClick($event) {
      this.tabsIndex = $event.index
      this.tabsData.forEach((el) => {
        if (el.title == $event) {
          this.tabsIndex = el.index
          return
        }
      })
      if ($event.index == '0') {
        this.getList()
        this.getRightList()
      } else if ($event.index == '1') {
        this.getPacsKsData()
        this.getPacsKsDataRight()
      } else if ($event.index == '2') {
        this.getOtherKsData()
        this.getOtherKsDataRight()
      }
    },
    //获取Pacs科室数据
    getPacsKsData() {
      this.loading = true
      analysePacs(this.searchData)
        .then((res) => {
          this.tableList = res.data.records
          for (var i = 0; i < this.tableList.length; i++) {
            if (this.tableList[i].rummagerTime == null) {
              continue
            }
            this.tableList[i].rummagerTime = this.tableList[i].rummagerTime.substring(0, this.tableList[i].rummagerTime.length - 9)
          }
          this.total = res.data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    //获取Pacs科室数据右
    getPacsKsDataRight() {
      this.rightLoading = true
      analysePacsTotal(this.searchData)
        .then((res) => {
          this.tableData = res.data.records
          this.rightLoading = false
          this.rightTotal = res.data.total
        })
        .catch(() => {
          this.rightLoading = false
        })
    },
    //获取其他科室数据
    getOtherKsData() {
      this.loading = true
      analyse(this.searchData)
        .then((res) => {
          this.tableList = res.data.records
          for (var i = 0; i < this.tableList.length; i++) {
            if (this.tableList[i].rummagerTime == null) {
              continue
            }
            this.tableList[i].rummagerTime = this.tableList[i].rummagerTime.substring(0, this.tableList[i].rummagerTime.length - 9)
          }
          this.total = res.data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    //获取其他科室数据右
    getOtherKsDataRight() {
      this.rightLoading = true
      analyseTotal(this.searchData)
        .then((res) => {
          this.tableData = res.data.records
          this.rightTotal = res.data.total
          this.rightLoading = false
        })
        .catch(() => {
          this.rightLoading = false
        })
    },
    // 查询列表
    getList() {
      this.loading = true
      analyseTest(this.searchData)
        .then((response) => {
          this.tableList = response.data.records
          for (var i = 0; i < this.tableList.length; i++) {
            if (this.tableList[i].rummagerTime == null) {
              continue
            }
            this.tableList[i].rummagerTime = this.tableList[i].rummagerTime.substring(0, this.tableList[i].rummagerTime.length - 9)
          }
          this.total = response.data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 右表查询列表
    getRightList() {
      this.rightLoading = true
      analyseTestTotal(this.searchData)
        .then((response) => {
          this.tableData = response.data.records
          this.rightTotal = response.data.total
          this.rightLoading = false
        })
        .catch(() => {
          this.rightLoading = false
        })
    },
    // 检查项目选择返回值
    selectChange(value) {
      this.queryParams.examId = value.id
      this.queryRightParams.examId = value.id
    },
    // 收费项目选择返回值
    selectChange2(value) {
      this.queryParams.itemId = value.id
      this.queryRightParams.itemId = value.id
    },
    // 医师选择返回值
    selectChange3(value) {
      this.queryParams.name = value.id
      this.queryRightParams.name = value.id
    },
    // 科室返回值
    selectChange4(value) {
      // this.参数 = value
      this.queryParams.ks = value.id
      this.queryRightParams.ks = value.id
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.queryRightParams.current = 1
      this.searchData = {
        current: 1,
        size: 20,
        branchIds: this.queryParams.fzx,
        ks: this.queryParams.ks,
        examId: this.queryParams.examId,
        itemId: this.queryParams.itemId,
        name: this.queryParams.name,
      }
      this.searchData.startTime = this.queryParams.startTime ? this.queryParams.startTime + ' 00:00:00' : undefined
      this.searchData.endTime = this.queryParams.endTime ? this.queryParams.endTime + ' 23:59:59' : undefined
      if (this.tabsIndex == '0') {
        this.getList()
        this.getRightList()
      } else if (this.tabsIndex == '1') {
        this.getPacsKsData()
        this.getPacsKsDataRight()
      } else if (this.tabsIndex == '2') {
        this.getOtherKsData()
        this.getOtherKsDataRight()
      }
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.$delete(this.selectData, 'bindValue')
      this.$delete(this.selectData2, 'bindValue')
      this.$delete(this.selectData3, 'bindValue')
      this.$delete(this.selectData4, 'bindValue')
      this.$set(this.selectData, 'bindValue', '')
      this.$set(this.selectData2, 'bindValue', '')
      this.$set(this.selectData3, 'bindValue', '')
      this.$set(this.selectData4, 'bindValue', '')
      this.handleQuery()
    },
    // 导出
    handleExport(type) {
      let url = '/statistics/divisionDoctor/'
      let name = '导出科室工作量'
      switch (type) {
        case 1:
          url += 'exportPacs '
          name += '(PACS)'
          break
        case 2:
          url += 'exportPacsInfo  '
          name += '明细(PACS)'
          break
        case 3:
          url += 'exportOther '
          name += '(其他)'
          break
        case 4:
          url += 'exportOtherInfo  '
          name += '明细(其他)'
          break
        default:
          break
      }
      this.download(
        url,
        {
          ...this.queryParams,
        },
        name + '.xlsx'
      )
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
  },
}
</script>
<style lang="scss">
.division-doctor-main {
  .el-tabs__content {
    height: 100%;
  }
  .el-table .cell {
    font-size: 14px;
    color: #000;
  }
}
</style>
