<!-- 职业总检 麦沃德科技 开发人:清风 -->
<template>
  <div class="app-container flex-direction-column occupational_inspection">
    <el-form :model="form" ref="form" size="small" :inline="true" label-width="80px;" class="no-margin-bottom">
      <el-form-item label="体检号" prop="patientcode">
        <el-input style="width: 160px" placeholder="请输入体检号" clearable v-model="form.patientcode" @keyup.enter.native="handleUpdate({ patientcode: form.patientcode })"></el-input>
      </el-form-item>
      <el-form-item prop="autoFill" style="margin-right: 50px">
        <el-checkbox v-model="form.autoFill"></el-checkbox>
      </el-form-item>
      <el-form-item label="体检阶段" prop="examstate">
        <el-select style="width: 160px" placeholder="请选择体检阶段" v-model="form.examstate" clearable @change="queryWindow">
          <el-option v-for="(item, index) in examstate" :key="index" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="总检状态" prop="zytjzt">
        <el-select style="width: 160px" placeholder="请选择总检状态" v-model="form.zytjzt" clearable @change="queryWindow">
          <el-option v-for="(item, index) in zytjzt" :key="index" :value="item.id" :label="item.text"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检者状态" prop="idPatientclass">
        <el-select style="width: 160px" placeholder="请选择体检者状态" v-model="form.idPatientclass" clearable @change="queryWindow">
          <el-option v-for="(item, index) in idPatientclass" :key="index" :value="item.id" :label="item.text"></el-option></el-select>
      </el-form-item>
      <el-form-item label="总检开始日期" prop="startTime">
        <el-date-picker style="width: 160px" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="form.startTime" @change="queryWindow"></el-date-picker>
      </el-form-item>
      <el-form-item label="总检结束日期" prop="endTime">
        <el-date-picker style="width: 160px" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="form.endTime" @change="queryWindow"></el-date-picker>
      </el-form-item>
      <el-form-item label="团队名称" prop="orgName">
        <el-input style="width: 160px" placeholder="请输入团队名称" v-model="form.orgName" clearable @keyup.enter.native="queryWindow"></el-input>
      </el-form-item>
      <el-form-item label="体检者名称" prop="patientname">
        <el-input style="width: 160px" placeholder="请输入体检者名称" v-model="form.patientname" clearable @keyup.enter.native="queryWindow"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="queryWindow">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh" @click="reset">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-lock" plain @click="lockingWindow(0)" :disabled="single" v-hasPermi="['inspection:occupationalInspection:locking']">锁定</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-unlock" plain @click="unlockWindow(0)" :disabled="single" v-hasPermi="['inspection:occupationalInspection:unlock']">解锁</el-button>
      </el-col>
    </el-row>

    <div class="table-box">
      <el-table id="setTable" ref="tableData" :data="tableData" v-loading="loading" :border="true" :stripe="true" @row-click="rowClick" @selection-change="handleSelectionChange" row-key="id" height="100%" @row-dblclick="doubbleClick">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column type="index" width="60" label="序列" align="center"></el-table-column>
        <!-- <el-table-column prop="severedegree" width="100px" label="重症级" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.severedegree == null || scope.row.severedegree == 0">无</div>
            <div v-else-if="scope.row.severedegree <= 3">低</div>
            <div v-else-if="scope.row.severedegree <= 6">中</div>
            <div v-else-if="scope.row.severedegree <= 9">高</div>
            <div v-else>无</div>
          </template>
</el-table-column> -->
        <el-table-column prop="patientarchiveno" width="120px" label="档案号" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="patientcode" width="140px" label="体检号" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="patientname" width="120px" label="姓名" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="idGuidenurse" width="120px" label="总检锁定" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.idGuidenurse == '1'">锁定</el-tag>
            <el-tag v-else>未锁定</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="zytjzt" width="120px" label="总检完成" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-for="item in zytjzt" :key="item.id">
              <span v-if="item.id == scope.row.zytjzt">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="medicaldate" width="120px" label="体检时间" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="dateregisternotime" width="120px" label="总检时间" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="patientnameencoded" width="120px" label="总检医生" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="zyshys" width="120px" label="职业审核医生" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="dateregister" width="120px" label="登记时间" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="doctorreg" width="120px" label="登记员" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="examsuiteName" width="160px" label="套餐名称" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="orgName" width="120px" label="体检团体" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="idExamtype" width="120px" label="体检类型" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-for="item in idExamtype" :key="item.id">
              <span v-if="item.id == scope.row.idExamtype">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="idPatientclass" width="120px" label="体检者类型" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-for="item in idPatientclass" :key="item.id">
              <span v-if="item.id == scope.row.idPatientclass">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="idcardno" width="160px" label="身份证号" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="idSex" width="80px" label="性别" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-for="item in sexOptions" :key="item.id">
              <span v-if="item.id == scope.row.idSex">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="age" width="80px" label="年龄" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="idMarriage" width="80px" label="婚姻" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-for="item in idMarriage" :key="item.id">
              <span v-if="item.id == scope.row.idMarriage">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="fexamstarted" width="120px" label="已开始体检" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.fexamstarted == 1">已开始体检</div>
            <div v-else>未开始体检</div>
          </template>
        </el-table-column>
        <el-table-column prop="freadytofinal" width="120px" label="分检完成" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-if="scope.row.freadytofinal == 1">已分检</div>
            <div v-else>未分检</div>
          </template>
        </el-table-column>
        <el-table-column prop="FReportprinted" width="120px" label="报告已打印" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-for="item in FReportprinted" :key="item.id">
              <span v-if="item.id == scope.row.FReportprinted">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="FReportfetched" width="120px" label="报告已领取" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <div v-for="item in FReportfetched" :key="item.id">
              <span v-if="scope.row.FReportfetched == item.id">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="200" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['inspection:occupationalInspection:query']">查看</el-button>
            <el-button size="mini" type="text" style="color: #0059ff" @click="lockingWindow(scope.row)" v-hasPermi="['inspection:occupationalInspection:locking']">锁定</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="unlockWindow(scope.row)" v-hasPermi="['inspection:occupationalInspection:unlock']">解锁</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="form.current" :limit.sync="form.size" @pagination="getList" />
    <!-- <detailsItems ref="detailsItems" @getList="getList"></detailsItems> -->
  </div>
</template>

<script>
// import detailsItems from './details.vue'
import { page, lock, unlock } from '@/api/inspection/occupational_inspection.js'

export default {
  name: 'Occupational_inspection',
  // components: { detailsItems },
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
      form: {
        patientcode: '',
        autoFill: true,
        examstate: '',
        zytjzt: '',
        idPatientclass: '',
        startTime: '',
        endTime: '',
        orgName: '',
        patientname: '',
        current: 1,
        size: 50,
      },
      //体检阶段
      examstate: [
        { id: '0', text: '已开始体检' },
        { id: '1', text: '分检完成' },
        { id: '2', text: '总检完成' },
        { id: '3', text: '报告已打印' },
        { id: '4', text: '报告已领取' },
      ],
      tableData: [],
      //总检完成展示数组
      zytjzt: [
        { id: '0', text: '总检开始' },
        { id: '1', text: '总检完成' },
        { id: '2', text: '报告已打印' },
        { id: '3', text: '报告一审通过' },
        { id: '4', text: '报告一审不通过' },
        { id: '5', text: '二审通过' },
        { id: '6', text: '二审不通过' },
        { id: '7', text: '终审通过' },
        { id: '8', text: '终审不通过' },
        { id: '9', text: '报告已交接' },
        { id: '10', text: '报告已通知' },
        { id: '11', text: '报告已领取' },
      ],
      //体检类型
      idExamtype: [
        { id: '0', text: '健康体检' },
        { id: '1', text: '职业体检' },
        { id: '2', text: '综合' },
      ],
      //体检者类型
      idPatientclass: [
        { id: '1', text: '普通' },
        { id: '2', text: 'VIP' },
        { id: '3', text: 'VVIP' },
      ],
      //性别类型
      sexOptions: [
        { id: '0', text: '男' },
        { id: '1', text: '女' },
        { id: '2', text: '综合' },
      ],
      //报告已打印
      FReportprinted: [
        { id: '0', text: '否' },
        { id: '1', text: '是' },
        { id: null, text: '无' },
      ],
      //报告已领取
      FReportfetched: [
        { id: '0', text: '否' },
        { id: '1', text: '是' },
        { id: null, text: '无' },
      ],
      idMarriage: [
        { id: '1', text: '未婚' },
        { id: '2', text: '已婚' },
        { id: '3', text: '离异' },
        { id: '4', text: '丧偶' },
        { id: '5', text: '其他' },
      ],
      patientcode: '',
    }
  },
  mounted() {
    // 最近7天
    // let toData = new Date(new Date().toLocaleDateString()).getTime()
    // let past7daysStart = toData - 7 * 3600 * 24 * 1000
    // this.form.startTime = this.$getDate(past7daysStart).split(' ')[0]
    // this.form.endTime = this.$getDate().split(' ')[0]
    let obj = { ...this.form }
    page(obj).then((res) => {
      if (res.code == 200) {
        this.tableData = res.data.records
        this.form.current = res.data.current
        this.total = res.data.total
      }
    })
  },
  methods: {
    getList() {
      this.loading = true
      let query = JSON.parse(JSON.stringify(this.form))
      if (query.startTime) {
        query.startTime += ' 00:00:00'
      }
      if (query.endTime) {
        query.endTime += ' 23:59:59'
      }
      page(query).then((res) => {
        if (res.code == 200) {
          if (this.form.autoFill && res.data.records.length == 1) {
            this.form.patientcode = res.data.records[0].patientcode
          }
          this.tableData = res.data.records
          this.form.current = res.data.current
          this.total = res.data.total
          this.loading = false
        }
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => {
        return item.id
      })
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.patientcode = selection[0].patientcode
      }
    },
    queryWindow() {
      this.form.current = 1
      this.getList()
    },
    //重置
    reset() {
      // this.form = {
      //   patientcode: undefined,
      //   examstate: undefined,
      //   zytjzt: undefined,
      //   idPatientclass: undefined,
      //   startTime: undefined,
      //   endTime: undefined,
      //   orgName: undefined,
      //   patientname: undefined,
      // }
      this.resetForm('form')
      this.queryWindow()
    },
    //锁定
    lockingWindow(context) {
      if (context == '0') {
        if (this.single) {
          this.$alert('请选中一条记录!', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
            .then(() => { })
            .catch(() => { })
          return
        } else {
          lock({ patientcode: this.patientcode }).then(() => {
            this.$modal.msgSuccess('锁定成功')
            this.getList()
          })
        }
      } else {
        lock({ patientcode: context.patientcode }).then(() => {
          this.$modal.msgSuccess('锁定成功')
          this.getList()
        })
      }
    },
    unlockWindow(context) {
      //unlock
      if (context == '0') {
        if (this.single) {
          this.$alert('请选中一条记录!', '提醒', {
            confirmButtonText: '确定',
            type: 'warning',
          })
            .then(() => { })
            .catch(() => { })
          return
        } else {
          unlock({ patientcode: this.patientcode }).then(() => {
            this.$modal.msgSuccess('解锁成功')
            this.getList()
          })
        }
      } else {
        unlock({ patientcode: context.patientcode }).then(() => {
          this.$modal.msgSuccess('解锁成功')
          this.getList()
        })
      }
    },
    //单击事件选中当前行
    rowClick(row, col) {
      if (col && col.label == '操作') {
        return
      }
      this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    //双击事件跳转当前行详细
    doubbleClick(row) {
      this.handleUpdate(row)
    },
    //查看当前行详细
    handleUpdate(row) {
      var obj = {
        flag: false, //false不需要填0true需要填0
        patientno: row.patientcode,
        ...row,
      }
      if (obj.patientno.length < 12) {
        obj.flag = true
      }
      obj = JSON.stringify(obj)
      const obj1 = { path: 'general_inspection/occupational_inspection_details', name: 'occupationalInspectionDetails' }
      this.$tab.closePage(obj1).then(() => {
        this.$tab.openPage('职业总检详情', '/general_inspection/occupational_inspection_details', { obj, first: 1 })
      })
      // this.$refs.detailsItems.detailsWindow(obj, 1)
    },
  },
}
</script>

<style scoped>
#setTable /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>

<style scoped>
.occupational_inspection /deep/ .el-button span {
  font-size: 16px;
}

.occupational_inspection /deep/ table .cell {
  font-size: 16px !important;
}

.occupational_inspection /deep/ .el-form-item__content {
  font-size: 15px !important;
  color: #000 !important;
}

.occupational_inspection .inspection-details-RD /deep/ .el-input__inner,
.occupational_inspection .inspection-details-RD /deep/ .el-textarea__inner {
  color: #000 !important;
  font-size: 16px !important;
}

/* 修改火狐浏览器字体 */
@-moz-document url-prefix() {

  body,
  .occupational_inspection .el-input__inner,
  .occupational_inspection .el-form .el-form-item__label,
  .occupational_inspection .checkbox {
    font-family: '宋体', Arial, sans-serif;
    background: transparent;
  }

  .occupational_inspection .el-button {
    font-family: '宋体', Arial, sans-serif;
  }

  .el-form .el-form-item__label {
    font-weight: 400;
  }
}
</style>
