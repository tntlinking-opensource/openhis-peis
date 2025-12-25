<!-- 预约详情 开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column appointment-particulars">
    <!-- 日期选择器 -->
    <el-form :model="queryParams1" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心">
        <el-select v-model="queryParams1.branchId" placeholder="请选择分中心" clearable @change="handleQuery">
          <el-option v-for="item in branchIdOptions" :key="item.branchId" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
      <el-form-item label="预约时间" prop="startDate">
        <el-date-picker v-model="queryParams1.startDate" style="width: 190px" value-format="yyyy-MM-dd" type="date" :clearable="false" @change="handleQuery"> </el-date-picker>
      </el-form-item>
      <el-form-item label="-" prop="endDate">
        <el-date-picker v-model="queryParams1.endDate" style="width: 190px" value-format="yyyy-MM-dd" type="date" :clearable="false" @change="handleQuery"> </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="groupExport" v-hasPermi="['subscribe:appointmentParticulars:teamExport']">团队预约信息导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="peopleExport" v-hasPermi="['subscribe:appointmentParticulars:personalExport']">人员预约信息导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="dateExport(1)" v-hasPermi="['subscribe:appointmentParticulars:dateExport']">日期预约信息导出</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="dateExport(2)" v-hasPermi="['subscribe:appointmentParticulars:teamDateExport']">团队预约信息按时间导出</el-button>
      </el-col> -->
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-row style="height: 100%">
        <el-col :span="12" style="height: 100%" class="flex-direction-column">
          <div class="table-box">
            <el-table ref="tableList1" border v-loading="loading1" :data="tableList1" stripe height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="序列" width="60" type="index" align="center" />
              <el-table-column label="预约日期" prop="reservationDate" align="center" width="120">
                <template slot-scope="scope">
                  {{ scope.row.reservationDate ? scope.row.reservationDate.split(' ')[0] : '' }}
                </template>
              </el-table-column>
              <el-table-column label="星期" prop="weekName" align="center" />
              <el-table-column label="普通(上午)" align="center">
                <el-table-column label="预约人数" prop="count1Am" align="center" width="120"> </el-table-column>
                <el-table-column label="上限" prop="count1MaxAm" align="center" width="120"> </el-table-column>
              </el-table-column>
              <el-table-column label="普通(下午)" align="center">
                <el-table-column label="预约人数" prop="count1Pm" align="center" width="120"> </el-table-column>
                <el-table-column label="上限" prop="count1MaxPm" align="center" width="120"> </el-table-column>
              </el-table-column>
              <el-table-column label="VIP" align="center">
                <el-table-column label="预约人数" prop="count2" align="center" width="120"> </el-table-column>
                <el-table-column label="上限" prop="count2Max" align="center" width="120"> </el-table-column>
              </el-table-column>
              <el-table-column label="VVIP" align="center">
                <el-table-column label="预约人数" prop="count3" align="center" width="120"> </el-table-column>
                <el-table-column label="上限" prop="count3Max" align="center" width="120"> </el-table-column>
              </el-table-column>
            </el-table>
          </div>
          <!-- 分页器 -->
          <pagination :total="total1" :page.sync="queryParams1.current" :limit.sync="queryParams1.size" @pagination="getList" />
        </el-col>

        <el-col :span="12" style="height: 100%; display: flex; flex-direction: column; padding-left: 8px">
          <div class="flex-direction-column" style="height: 52%">
            <div style="padding-left: 20px">
              <!-- 日期选择器 -->
              <el-form :model="queryParams1" ref="queryForm" size="mini" :inline="true" class="no-margin-bottom">
                <el-form-item label="体检号">
                  <el-input v-model="queryParams2.patientcode" placeholder="请输入" style="width: 130px"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                  <el-input v-model="queryParams2.realName" placeholder="请输入" style="width: 130px"></el-input>
                </el-form-item>
                <el-form-item label="客户类型">
                  <el-select v-model="queryParams2.levelId" placeholder="请选择" style="width: 130px">
                    <el-option v-for="item in patientType" :key="item.levelId" :label="item.levelName" :value="item.levelId" />
                  </el-select>
                </el-form-item>
                <el-form-item prop="notDisplayVirtual" label="隐藏虚拟">
                  <el-checkbox v-model="queryParams2.notDisplayVirtual" :true-label="1" :false-label="0"></el-checkbox>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" icon="el-icon-search" size="mini" :disabled="!selectDate" @click="handleQuery2">搜索</el-button>
                  <el-button class="zk-btn-style2" icon="el-icon-upload2" size="mini" :disabled="!selectDate" @click="handleExportVIP">导出VIP</el-button>
                </el-form-item>
              </el-form>
            </div>
            <div class="table-box">
              <el-table border v-loading="loading2" :data="tableList2" stripe height="100%">
                <el-table-column type="selection" width="45" align="center" />
                <el-table-column label="序列" width="55" type="index" align="center" />
                <el-table-column prop="reservationDate" label="预约时间段" width="120" align="center" show-overflow-tooltip />
                <el-table-column prop="patientcode" label="体验号" width="120" align="center" show-overflow-tooltip />
                <el-table-column prop="realName" label="姓名" align="center" show-overflow-tooltip />
                <el-table-column prop="xsjl" label="开单医生" align="center" />
                <el-table-column prop="questionnaire" label="问卷状态" align="center" min-width="100">
                  <template slot-scope="scope">
                    <el-tag type="danger" v-if="scope.row.questionnaire == 0">未填写</el-tag>
                    <el-tag type="success" v-else>已填写</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="levelName" label="客户类型" align="center" />
                <el-table-column prop="sex" label="性别" align="center">
                  <template slot-scope="scope">
                    {{ ['男', '女', '通用'][scope.row.sex] }}
                  </template>
                </el-table-column>
                <el-table-column prop="age" label="年龄" align="center" />
                <el-table-column prop="idcard" label="证件号码" width="150" align="center" show-overflow-tooltip />
                <el-table-column prop="mobile" label="电话" width="110" align="center" show-overflow-tooltip />
                <el-table-column prop="khdwmc" label="单位" width="150" align="center" show-overflow-tooltip />
                <el-table-column prop="remark" label="备注" width="150" align="center" show-overflow-tooltip />
              </el-table>
              <!-- 分页器 -->
            </div>
            <pagination :total="total2" :page.sync="queryParams2.current" :limit.sync="queryParams2.size" @pagination="getList2" />
          </div>
          <div class="flex-direction-column" style="height: 48%">
            <div class="table-box">
              <el-table border v-loading="loading3" :data="tableList3" stripe height="100%">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="序列" width="55" type="index" align="center" />
                <el-table-column label="订单号" prop="orderNum" align="center" />
                <el-table-column label="订单名称" prop="orderName" align="center" width="180" show-overflow-tooltip />
                <el-table-column label="客户类型" prop="levelName" align="center" />
                <el-table-column label="套餐" prop="tjtcmc" align="center" width="120" show-overflow-tooltip />
                <el-table-column label="销售经理" prop="xsjlName" align="center" />
                <el-table-column label="预约人" prop="creator" align="center" />
                <el-table-column label="预约人数(上午)" prop="countAm" align="center" />
                <el-table-column label="预约人数(下午)" prop="countPm" align="center" />
                <el-table-column label="备注" prop="remark" align="center" />
              </el-table>
            </div>
            <!-- 分页器 -->
            <pagination :total="total3" :page.sync="queryParams3.current" :limit.sync="queryParams3.size" @pagination="getList3" />
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getListData } from '@/api/subscribe/appointment_particulars.js'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
import { getListData as getListData2 } from '@/api/subscribe/my_appointment/my_appointment'
import { getListData as getListData3 } from '@/api/subscribe/group_appointment.js'
import { getUserCid } from '@/api/system/branch.js'
export default {
  name: 'Appointment_particulars',
  data() {
    return {
      //分中心
      branchIdOptions: [],
      loading1: false,
      loading2: false,
      loading3: false,
      queryParams1: {
        current: 1,
        size: 20,
        branchId: undefined,
        startDate: undefined,
        endDate: undefined,
      },
      queryParams2: {
        current: 1,
        size: 20,
        notDisplayVirtual: 1,
        reservationDate: undefined,
        patientcode: undefined,
        realName: undefined,
        levelId: undefined,
        branchIds: undefined,
      },
      queryParams3: {
        current: 1,
        size: 20,
        subName: undefined,
        date: undefined,
      },
      tableList1: [],
      tableList2: [],
      tableList3: [],
      total1: 0,
      total2: 0,
      total3: 0,
      // 选中的id列表
      ids: [],
      // 选中的id列表
      sectionList: [],
      // 体检者类型列表
      patientType: [],
      // 是否为线上
      isOnline: false,
      // 是否选则日期
      selectDate: false,
    }
  },
  async created() {
    this.queryParams1.branchId = this.$getCookie('cid').toString()
    this.isOnline = this.$getCookie('isOnline') == 1 ? true : false
    this.queryParams1.startDate = this.$getDate().split(' ')[0]
    this.queryParams1.endDate = this.$getDate().split(' ')[0]
    this.patientType = (await this.$getLevelList()).data
    if (this.isOnline) {
      getBranchData().then((response) => {
        this.branchIdOptions = response.data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.branchIdOptions = data
      })
    }
    this.handleQuery()
  },
  methods: {
    // 搜索
    handleQuery() {
      this.queryParams1.current = 1
      this.getList()
    },
    // 左侧查询列表
    getList() {
      this.loading1 = true
      let queryParams1 = JSON.parse(JSON.stringify(this.queryParams1))
      queryParams1.startDate = queryParams1.startDate ? queryParams1.startDate + ' 00:00:00' : undefined
      queryParams1.endDate = queryParams1.endDate ? queryParams1.endDate + ' 23:59:59' : undefined
      getListData(queryParams1)
        .then(({ data }) => {
          if (data) {
            this.tableList1 = data.records
            this.total1 = data.total
          }
          this.loading1 = false
        })
        .catch((error) => {
          console.error(error)
          this.loading1 = false
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.sectionList = selection.map((item) => item)
      this.selectDate = false
      if (selection && selection.length) {
        this.selectDate = true
        if (selection.length == 1) {
          this.handleQuery2()
          this.handleQuery3()
        } else {
          this.$refs.tableList1.clearSelection()
          this.$refs.tableList1.toggleRowSelection(selection[selection.length - 1])
        }
      } else {
        this.tableList2 = []
        this.tableList3 = []
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList1.clearSelection()
      this.$refs.tableList1.toggleRowSelection(row)
    },
    // 搜索
    handleQuery2() {
      this.queryParams2.current = 1
      this.getList2()
    },
    // 右上表格查询列表
    getList2() {
      this.loading2 = true
      this.queryParams2.reservationDate = this.sectionList[this.sectionList.length - 1].reservationDate
      this.queryParams2.branchIds = this.queryParams1.branchId
      getListData2(this.queryParams2)
        .then(({ data }) => {
          if (data) {
            data.records.forEach((el) => {
              el.reservationDate = el.reservationDate ? el.reservationDate.split(' ')[1] : ''
            })
            this.tableList2 = data.records
            this.total2 = data.total
          }
          this.loading2 = false
        })
        .catch((error) => {
          console.error(error)
          this.loading2 = false
        })
    },
    // 导出VIP数据
    handleExportVIP() {
      this.download('/reservation/pf/vipExport', this.queryParams2, this.queryParams2.reservationDate.slice(0, 10) + `-VIP列表.xlsx`)
    },
    // 搜索
    handleQuery3() {
      this.queryParams3.current = 1
      this.getList3()
    },
    // 右下表格查询列表
    getList3() {
      this.loading3 = true
      let queryParams1 = JSON.parse(JSON.stringify(this.queryParams1))
      queryParams1.startDate = this.sectionList[this.sectionList.length - 1].reservationDate
      queryParams1.endDate = this.sectionList[this.sectionList.length - 1].reservationDate.slice(0, 10) + ' 23:59:59'
      getListData3({ ...queryParams1, ...this.queryParams3 })
        .then(({ data }) => {
          if (data) {
            this.tableList3 = data.records
            this.total3 = data.total
          }
          this.loading3 = false
        })
        .catch((error) => {
          console.error(error)
          this.loading3 = false
        })
    },
    //团队预约信息导出
    groupExport() {
      this.download('/reservation/detail/groupExport', this.queryParams1, `团队预约信息详情.xlsx`)
    },
    // 人员信息导出
    peopleExport() {
      if (!this.sectionList || !this.sectionList.length) {
        this.$alert('请先选择一个日期', '提示')
        return
      }

      let queryParams1 = JSON.parse(JSON.stringify(this.queryParams1))
      queryParams1.startDate = this.sectionList[this.sectionList.length - 1].reservationDate
      queryParams1.endDate = this.sectionList[this.sectionList.length - 1].reservationDate.slice(0, 10) + ' 23:59:59'
      this.download('/reservation/detail/personalExport', { ...queryParams1, ...this.queryParams2 }, `${this.sectionList[0].reservationDate}人员预约信息详情.xlsx`)
    },
    // 导出
    dateExport(type) {
      let queryParams1 = JSON.parse(JSON.stringify(this.queryParams1))
      queryParams1.startDate = queryParams1.startDate ? queryParams1.startDate + ' 00:00:00' : undefined
      queryParams1.endDate = queryParams1.endDate ? queryParams1.endDate + ' 23:59:59' : undefined
      let url = ''
      let fileName = ''
      if (type == 1) {
        // 日期导出
        url = '/reservation/detail/exportDetailTotal'
        fileName = this.queryParams1.startDate + '-' + this.queryParams1.endDate + '日期预约信息详情'
      } else {
        // 团队预约信息导出
        url = '/reservation/detail/groupExportDuring'
        fileName = this.queryParams1.startDate + '-' + this.queryParams1.endDate + '团队预约信息按时间段详情'
      }
      this.download(url, queryParams1, fileName + `.xlsx`)
    },
  },
}
</script>
<style scoped>
.appointment-particulars /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
