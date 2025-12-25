<template>
  <el-dialog title="查看列队" :visible.sync="open" width="1400px" append-to-body :close-on-click-modal="false" :destroy-on-close="true" class="section-queue-dia">
    <!-- 第一行筛选 -->
    <div class="flex">
      <div class="no-margin-bottom first-line">
        <div>
          <span class="top-style">体检状态</span>
          <select class="select-style" v-model="queryParams.status" @change="handleQuery">
            <option :value="''"></option>
            <option v-for="item in physicalStatus" :key="item.value" :value="item.value">{{ item.label }}</option>
          </select>
        </div>
        <div>
          <span class="top-style">体检者类型</span>
          <select class="select-style" v-model="queryParams.idPatientclass" @change="handleQuery">
            <option :value="''"></option>
            <option v-for="item in memberType" :key="item.levelId" :value="item.levelId">{{ item.levelName }}</option>
          </select>
        </div>
        <div>
          <span class="top-style">体检类型</span>
          <select class="select-style" v-model="queryParams.idExamtype" @change="handleQuery">
            <option :value="''"></option>
            <option v-for="item in physicalType" :key="item.value" :value="item.value">{{ item.label }}</option>
          </select>
        </div>
        <div>
          <span class="top-style">体检号</span>
          <input class="input-style" v-model="queryParams.patientcode" placeholder="请输入体检号" @keyup.enter="handleQuery" />
        </div>
        <div>
          <span class="top-style">姓名</span>
          <input class="input-style" v-model="queryParams.patientname" placeholder="请输入体检号" @keyup.enter="handleQuery" />
        </div>
      </div>
      <div style="margin-bottom: 18px">
        <span>当前登录人:{{ logPerson }}</span>
        <input type="checkbox" v-model="queryParams.dependBy" style="width: 16px; height: 16px; cursor: pointer" @change="handleQuery" />
      </div>
    </div>
    <!-- 第二行筛选 -->
    <div class="no-margin-bottom first-line" style="margin-bottom: 4px">
      <div>
        <span class="top-style">选择日期</span>
        <el-date-picker v-model="queryParams.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleQuery"></el-date-picker>
      </div>
      <div>
        <span class="top-style">性别</span>
        <select class="select-style" v-model="queryParams.sex" style="width: 160px" @change="handleQuery">
          <option :value="''"></option>
          <option value="0">男</option>
          <option value="1">女</option>
        </select>
      </div>
      <div>
        <span class="top-style">是否异常</span>
        <select class="select-style" v-model="queryParams.posistive" style="width: 160px" @change="handleQuery">
          <option :value="''"></option>
          <option value="0">正常</option>
          <option value="1">异常</option>
        </select>
      </div>
      <div>
        <el-button class="section-btn-style1" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button class="section-btn-style2" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </div>
    </div>
    <!-- 表格 -->
    <el-table v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" stripe border height="500px" @row-dblclick="rowDdblclick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序列" align="center" width="60px" type="index" />
      <el-table-column label="体检号" align="center" prop="patientcode" show-overflow-tooltip width="160" />
      <el-table-column label="姓名" align="center" prop="patientname" show-overflow-tooltip width="120" />
      <el-table-column label="性别" align="center" prop="idSex" show-overflow-tooltip width="60">
        <template slot-scope="scope">
          {{ ['男', '女'][scope.row.idSex] }}
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" prop="age" show-overflow-tooltip width="60" />
      <el-table-column label="体检者类型" align="center" prop="idPatientclass" show-overflow-tooltip width="110" />
      <el-table-column label="体检类型" align="center" prop="idExamtype" show-overflow-tooltip width="100">
        <template slot-scope="scope">
          {{ ['健康', '职业', '综合'][scope.row.idExamtype] }}
        </template>
      </el-table-column>
      <el-table-column label="体检状态" align="center" prop="status" show-overflow-tooltip width="100">
        <template slot-scope="scope">
          <span :style="{ color: scope.row.status == 1 ? 'green' : 'red' }">{{ ['未检', '已检'][scope.row.status] || '未检' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否异常" align="center" prop="posistive" show-overflow-tooltip width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.posistive == 1" style="color: red">异常</span>
          <span v-else style="color: green">正常</span>
        </template>
      </el-table-column>
      <el-table-column label="审核结果" align="center" prop="conclusions" show-overflow-tooltip width="100" />
      <el-table-column label="弃检项目" align="center" prop="qjxm" show-overflow-tooltip width="100" />
      <el-table-column label="迟检项目" align="center" prop="cjxm" show-overflow-tooltip width="100" />
      <el-table-column label="补检项目" align="center" prop="bjxm" show-overflow-tooltip width="100" />
    </el-table>
    <!-- 分页 -->
    <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="handleQuery" />
  </el-dialog>
</template>

<script>
import Cookies from 'js-cookie'

import { physicalStatus, physicalType } from '@/utils/dataList.js'
import { getRankData } from '@/api/funcdept/section_list/index.js'
export default {
  props: ['ksID'],
  data() {
    return {
      // 打开主弹窗
      open: false,
      // 体检状态选项
      physicalStatus: physicalStatus(),
      // 体检者类型选项
      memberType: [],
      // 体检类型选项
      physicalType: physicalType(),
      // 当前登录人
      logPerson: '',
      // 筛选参数
      queryParams: {
        current: 1,
        size: 20,
        ksID: undefined,
        status: undefined,
        idPatientclass: undefined,
        idExamtype: undefined,
        patientcode: undefined,
        patientname: undefined,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        sex: undefined,
        posistive: undefined,
        // 通过登录人姓名模糊查询
        dependBy: false,
      },
      // 表格加载中
      loading: false,
      // 总数
      total: 0,
      // 表格数据
      tableData: [],
      // 表格选中的数据
      selectData: [],
    }
  },
  methods: {
    async showDialog() {
      this.open = true
      this.memberType = (await this.$getLevelList()).data
      this.logPerson = decodeURIComponent(Cookies.get('username'))
      this.queryParams.ksID = this.$route.meta.ksID
      this.queryParams.current = 1
      this.getCurrentDay()
    },
    getCurrentDay() {
      let date = new Date()
      //获取当前时间的年份转为字符串 
      let year = date.getFullYear().toString() //'2019'
      //获取月份，由于月份从0开始，此处要加1，判断是否小于10，如果是在字符串前面拼接'0'
      let month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1).toString() : (date.getMonth() + 1).toString() //'04'
      //获取天，判断是否小于10，如果是在字符串前面拼接'0'
      let da = date.getDate() < 10 ? '0' + date.getDate().toString() : date.getDate().toString() //'1
      let day = year + '-' + month + '-' + da
      this.queryParams.date = [day, day]


      // let date = new Date();
      // let year = date.getFullYear().toString(); // '2019'

      // let month = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1).toString() : (date.getMonth() + 1).toString(); // '04'

      // let da = date.getDate() < 10 ? '0' + date.getDate().toString() : date.getDate().toString(); // '01'

      // let currentDay = year + '-' + month + '-' + da;

      // let lastWeekDate = new Date(date);
      // lastWeekDate.setDate(date.getDate() - 7); // Subtract 7 days

      // let lastWeekYear = lastWeekDate.getFullYear().toString();
      // let lastWeekMonth = (lastWeekDate.getMonth() + 1) < 10 ? '0' + (lastWeekDate.getMonth() + 1).toString() : (lastWeekDate.getMonth() + 1).toString();
      // let lastWeekDa = lastWeekDate.getDate() < 10 ? '0' + lastWeekDate.getDate().toString() : lastWeekDate.getDate().toString();

      // let lastWeekDay = lastWeekYear + '-' + lastWeekMonth + '-' + lastWeekDa;

      // this.queryParams.date = [lastWeekDay, currentDay];
      this.handleQuery()
    },
    // 查询 
    handleQuery() {
      this.loading = true
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0]
        this.queryParams.endTime = this.queryParams.date[1]
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      getRankData(this.queryParams).then(({ data }) => {
        this.tableData = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 重置
    resetQuery() {
      this.queryParams.status = ''
      this.queryParams.idPatientclass = ''
      this.queryParams.idExamtype = ''
      this.queryParams.patientcode = ''
      this.queryParams.patientname = ''
      this.queryParams.dependBy = ''
      this.queryParams.sex = ''
      this.queryParams.posistive = ''
      this.getCurrentDay()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectData = selection.map((item) => item)
    },
    // 双击表格某行
    rowDdblclick(row, column, event) {
      this.loading = true
      this.open = false
      this.loading = false
      const name = 'rankDb' + this.$route.meta.ksID
      this.bus.$emit(name, row.patientcode, this.$route.meta.ksID)
    },
  },
}
</script>

<style lang="scss" scoped>
.section-queue-dia {
  .flex {
    display: flex;
    align-items: center;
    justify-content: space-between;

    span {
      font-size: 14px;
      line-height: 20px;
      letter-spacing: 0.04em;
      color: #000000;
      margin-right: 5px;
    }
  }
  .first-line {
    display: flex;
    flex-wrap: wrap;
    .select-style {
      width: 160px;
      height: 30px;
      margin-right: 10px;
      font-size: 16px;
    }
    .input-style {
      width: 160px;
      height: 30px;
      font-size: 16px;
    }
    .top-style {
      // width: 70px;
      margin: 0 5px;
      color: #000;
      font-size: 16px;
      font-weight: 600;
    }
  }
}
</style>

<style scoped>
.section-queue-dia /deep/ .el-button span {
  font-size: 16px;
}
.section-queue-dia /deep/ .el-input__inner,
.section-queue-dia /deep/ .el-textarea__inner {
  font-size: 16px;
}
.section-queue-dia /deep/ table .cell {
  font-size: 16px !important;
}

.section-queue-dia /deep/ .el-form-item__content {
  font-size: 15px !important;
  color: #000 !important;
}
</style>
