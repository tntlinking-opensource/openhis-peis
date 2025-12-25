<!-- 健康总检 麦沃德科技 开发人:清风/予安 -->
<template>
  <div class="app-container flex-direction-column health-inspection">
    <el-form :model="queryParams" ref="queryForm" class="no-margin-bottom" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="体检号" prop="patientcode">
        <el-input style="width: 160px" placeholder="请输入体检号" @keyup.enter.native="handleCheck({ patientcode: queryParams.patientcode })" v-model="queryParams.patientcode" clearable> </el-input>
      </el-form-item>
      <el-form-item prop="autoFill" style="margin-right: 50px">
        <el-checkbox v-model="queryParams.autoFill"></el-checkbox>
      </el-form-item>
      <el-form-item label="分检开始日期" prop="startFjTime">
        <el-date-picker style="width: 160px" type="date" placeholder="开始日期" value-format="yyyy-MM-dd" v-model="queryParams.startFjTime" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="—" prop="endFjTime">
        <el-date-picker style="width: 160px" type="date" placeholder="结束日期" value-format="yyyy-MM-dd" v-model="queryParams.endFjTime" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检阶段" prop="examstate">
        <el-select style="width: 160px" placeholder="请选择" v-model="queryParams.examstate" clearable @change="handleQuery">
          <el-option v-for="item in examstateOptions" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="总检状态" prop="jktjzt">
        <el-select style="width: 160px" placeholder="请选择" v-model="queryParams.jktjzt" clearable @change="handleQuery">
          <el-option v-for="item in jktjztOptions" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检者类型" prop="idPatientclass">
        <el-select style="width: 160px" placeholder="请选择" v-model="queryParams.idPatientclass" clearable @change="handleQuery">
          <el-option v-for="item in memberOptions" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="总检开始日期" prop="startTime">
        <el-date-picker style="width: 160px" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" v-model="queryParams.startTime" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="总检结束日期" prop="endTime">
        <el-date-picker style="width: 160px" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" v-model="queryParams.endTime" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="团队名称" prop="orgName">
        <el-input style="width: 160px" placeholder="请输入团体名称" v-model="queryParams.orgName" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="体检者名称" prop="patientname">
        <el-input style="width: 160px" placeholder="请输入体检者名称" v-model="queryParams.patientname" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="登记开始日期" prop="startRegTime">
        <el-date-picker style="width: 160px" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" v-model="queryParams.startRegTime" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="登记结束日期" prop="endRegTime">
        <el-date-picker style="width: 160px" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" v-model="queryParams.endRegTime" @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-lock" plain @click="lockingWindow()" :disabled="single" v-hasPermi="['inspection:healthInspection:locking']">锁定</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-unlock" plain @click="unlockWindow()" :disabled="single" v-hasPermi="['inspection:healthInspection:unlock']">解锁</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <div class="table-box">
      <el-table id="setTable" :data="tableData" v-loading="loading" border stripe @row-click="rowClick" @selection-change="handleSelectionChange" row-key="id" height="100%" ref="tableData" @row-dblclick="rowDblclick">
        <el-table-column type="selection" width="55px" align="center"></el-table-column>
        <el-table-column type="index" width="60px" label="序列" align="center"></el-table-column>
        <el-table-column prop="patientcode" width="140px" label="体检号" align="center"></el-table-column>
        <el-table-column prop="patientname" width="120px" label="姓名" align="center"></el-table-column>
        <el-table-column width="120px" label="已开始体检" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.fexamstarted == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="120px" label="分检完成" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.freadytofinal == 1">完成</el-tag>
            <el-tag type="danger" v-else>未完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="120px" label="总检锁定" align="center">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.ffinallocked == 1">锁定</el-tag>
            <el-tag type="primary" v-else>未锁定</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="jktjzt" width="120px" label="总检完成" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.jktjzt != -1">{{ ['总检开始', '总检完成', '报告已打印', '报告一审通过', '报告一审不通过', '二审通过', '二审不通过', '终审通过', '终审不通过', '报告已交接', '报告已通知', '报告已领取'][scope.row.jktjzt] }}</span>
            <span v-else>总检未开始</span>
          </template>
        </el-table-column>
        <el-table-column prop="datefinalexamed" width="160px" label="总检时间" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="doctorfinalNameR" width="120px" label="总检医生" align="center"></el-table-column>
        <el-table-column prop="dateregister" width="160px" label="登记时间" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="doctorreg" width="120px" label="登记员" align="center"></el-table-column>
        <el-table-column prop="examsuiteName" width="160px" label="套餐名称" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="orgName" width="120px" label="体检团体" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="idExamtype" width="120px" label="体检类型" align="center">
          <template slot-scope="scope">
            <span>{{ ['健康体检', '职业体检', '综合', '复查'][scope.row.idExamtype] }}</span>
          </template>
        </el-table-column>
        <el-table-column width="120px" label="体检者类型" align="center">
          <template slot-scope="scope">
            <span>{{ ['', '普通会员', 'VIP', 'VVIP'][scope.row.idPatientclass] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="idcardno" width="180px" label="身份证号" align="center"></el-table-column>
        <el-table-column width="80px" label="性别" align="center">
          <template slot-scope="scope">
            <span>{{ ['男', '女', '通用'][scope.row.idSex] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="age" width="80px" label="年龄" align="center"></el-table-column>
        <el-table-column width="80px" label="婚姻" align="center">
          <template slot-scope="scope">
            <span>{{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][scope.row.idPatientclass] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="200" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #0059ff" @click="handleCheck(scope.row, true)" v-hasPermi="['inspection:healthInspection:query']">查看</el-button>
            <el-button size="mini" type="text" style="color: #ffba00" @click="lockingWindow(scope.row)" v-hasPermi="['inspection:healthInspection:locking']">锁定</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="unlockWindow(scope.row)" v-hasPermi="['inspection:healthInspection:unlock']">解锁</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- <detailsItems ref="detailsItems" @getList="getList"></detailsItems> -->
  </div>
</template>

<script>
// import detailsItems from './details.vue'

import { listHealth, isLock } from '@/api/inspection/health_inspection.js'
export default {
  name: 'Health_inspection',
  // components: { detailsItems },
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 体检阶段
      examstateOptions: [
        { id: 0, text: '未开始总检' },
        { id: 1, text: '开始总检' },
        { id: 2, text: '总检完成' },
      ],
      // 体检阶段
      jktjztOptions: [
        { id: -1, text: '总检未开始' },
        { id: 0, text: '总检开始' },
        { id: 1, text: '总检完成' },
        { id: 2, text: '报告已打印' },
        { id: 3, text: '报告一审通过' },
        { id: 4, text: '报告一审不通过' },
        { id: 5, text: '二审通过' },
        { id: 6, text: '二审不通过' },
        { id: 7, text: '终审通过' },
        { id: 8, text: '终审不通过' },
        { id: 9, text: '报告已交接' },
        { id: 10, text: '报告已通知' },
        { id: 11, text: '报告已领取' },
      ],
      // 体检者类型
      memberOptions: [
        { id: 1, text: '普通会员' },
        { id: 2, text: 'VIP' },
        { id: 3, text: 'VVIP' },
      ],

      loading: false,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      queryParams: {
        current: 1,
        size: 50,
        patientcode: undefined,
        examstate: undefined,
        startFjTime: undefined,
        endFjTime: undefined,
        jktjzt: undefined,
        idPatientclass: undefined,
        startTime: undefined,
        endTime: undefined,
        startRegTime: undefined,
        endRegTime: undefined,
        orgName: undefined,
        patientname: undefined,
        autoFill: true,
      },
      tableData: [],
      selection: {},
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取数据
    getList() {
      this.loading = true
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      queryParams.startFjTime = queryParams.startFjTime ? queryParams.startFjTime + ' 00:00:00' : undefined
      queryParams.endFjTime = queryParams.endFjTime ? queryParams.endFjTime + ' 23:59:59' : undefined
      queryParams.startTime = queryParams.startTime ? queryParams.startTime + ' 00:00:00' : undefined
      queryParams.endTime = queryParams.endTime ? queryParams.endTime + ' 23:59:59' : undefined
      queryParams.startRegTime = queryParams.startRegTime ? queryParams.startRegTime + ' 00:00:00' : undefined
      queryParams.endRegTime = queryParams.endRegTime ? queryParams.endRegTime + ' 23:59:59' : undefined
      listHealth(queryParams).then(({ data }) => {
        if (this.queryParams.patientcode && this.queryParams.autoFill && data.records.length == 1) {
          this.queryParams.patientcode = data.records[0].patientcode
        }
        this.tableData = data.records
        this.total = data.total
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
    // 选择表格项
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      this.selection = selection
      if (selection.length == 1) {
        this.selection = selection
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      } else if (selection.length == 0) {
        this.selection = []
      }
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && col.label == '操作') {
        return
      }
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 双击表格跳转详情
    rowDblclick(row) {
      this.handleCheck(row)
    },
    handleCheck(row, isTrue) {
      let flag = false
      if (row?.patientcode?.length < 12) {
        flag = true
      }
      const obj = { path: 'general_inspection/health_inspection_details', name: 'Health_inspection_details' }
      this.$tab.closePage(obj).then(() => {
        this.$tab.openPage('健康总检详情', '/general_inspection/health_inspection_details', { patientcode: row.patientcode, flag, examstate: this.queryParams.examstate })
      })
    },
    // 锁定
    lockingWindow(row) {
      var query = {
        ids: [],
        state: '',
      }
      let rowLen = this.selection[0] || row
      if (rowLen) {
        for (var i = 0; i < this.tableData.length; i++) {
          if (rowLen.id == this.tableData[i].id && this.tableData[i].ffinallocked == 0) {
            query.state = 1
            if (this.selection.length > 0) {
              query.ids = this.selection[0].patientcode
            } else {
              query.ids = row.patientcode
            }
            this.getIsLock(query)
            return
          } else if (rowLen.id == this.tableData[i].id && this.tableData[i].ffinallocked == 1) {
            this.$alert('该数据已锁定', '提示', {
              confirmButtonText: '确定',
              type: 'warning',
            })
              .then(() => { })
              .catch(() => { })
          }
        }
      } else {
        this.$alert('请选中一条记录！', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
        })
          .then(() => { })
          .catch(() => { })
      }
    },
    getIsLock(query) {
      isLock(query)
        .then(() => {
          this.$message({
            message: '操作成功',
            type: 'success',
          })
          this.getList()
          this.selection = []
        })
        .catch(() => { })
    },
    // 解锁
    unlockWindow(row) {
      var query = {
        ids: [],
        state: '',
      }
      let rowLen = this.selection[0] || row
      if (rowLen) {
        for (var i = 0; i < this.tableData.length; i++) {
          if (rowLen.id == this.tableData[i].id && this.tableData[i].ffinallocked == 1) {
            query.state = 0
            query.ids = rowLen.patientcode
            JSON.parse(JSON.stringify(query.ids))
            this.getIsLock(query)
            return
          } else if (rowLen.id == this.tableData[i].id && this.tableData[i].ffinallocked == 0) {
            this.$alert('该数据已解锁', '提示', {
              confirmButtonText: '确定',
              type: 'warning',
            })
              .then(() => {
                return
              })
              .catch(() => { })
          }
        }
      } else {
        this.$alert('请选中一条记录！', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
        })
          .then(() => { })
          .catch(() => { })
      }
    },
  },
}
</script>

<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}

.health-inspection /deep/ .el-button span {
  font-size: 16px;
}

.health-inspection /deep/ .el-input__inner,
.health-inspection /deep/ .el-textarea__inner {
  font-size: 16px;
}

.health-inspection /deep/ table .cell {
  font-size: 16px !important;
}

.health-inspection /deep/ .el-form-item__content {
  font-size: 15px !important;
  color: #000 !important;
}

.el-input__inner,
.el-textarea__inner {
  color: #000 !important;
  font-size: 16px !important;
}

/* 修改火狐浏览器字体 */
@-moz-document url-prefix() {

  body,
  .el-input__inner,
  .el-button,
  .el-form .el-form-item__label,
  .checkbox {
    font-family: '宋体', Arial, sans-serif;
  }

  .el-form .el-form-item__label {
    font-weight: 400;
  }
}
</style>
