<!-- 登记信息查询 开发人：麦沃德科技暴雨 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog title="查看登记详细信息" :visible.sync="open" width="1880px" append-to-body>
    <!-- 主体 -->
    <el-row :gutter="10" class="mb8" id="checkMain">
      <!-- 左侧 -->
      <index-left :patientData="patientData" :payOptions="payOptions" :noticeOptions="noticeOptions" :nationOptions="nationOptions" :memberOptions="memberType"></index-left>
      <!-- 右侧 -->
      <index-right :tableList="tableList"></index-right>
    </el-row>
  </el-dialog>
</template>
<script>
import { getPatientData } from '@/api/search/check_query.js'
import { getPaywayData, getNationData } from '@/api/reception/prepare_order.js'
import { getIssueWayData } from '@/api/reception/registration.js'

import indexLeft from './index_left.vue'
import indexRight from './index_right.vue'
export default {
  components: {
    indexLeft,
    indexRight,
  },
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 体检者信息
      patientData: {},
      // 支付方式列表
      payOptions: [],
      // 通知方式列表
      noticeOptions: [],
      // 民族列表
      nationOptions: [],
      // 右侧表格列表
      tableList: [],
      // 会员类型
      memberType:[],
    }
  },
  methods: {
    // 打开对话框
    async showDialog(patientCode) {
      this.memberType = (await this.$getLevelList()).data
      this.open = true
      const loading = this.$loading({ target: '#checkMain' })
      if (!this.payOptions.length) {
        getPaywayData().then(({ data }) => {
          this.payOptions = data
          this.patientData = JSON.parse(JSON.stringify(this.patientData))
          if (this.tableList.length) {
            this.tableList.forEach((el) => {
              el.price = Number(el.price).toFixed(1)
              el.factprice = Number(el.factprice).toFixed(1)
              this.payOptions.forEach((val) => {
                if (el.idPayway == val.id) {
                  el.payWayName = val.fzx
                  return
                }
              })
            })
            this.tableList = JSON.parse(JSON.stringify(this.tableList))
          }
        })
        getIssueWayData().then(({ data }) => {
          this.noticeOptions = data
          this.patientData = JSON.parse(JSON.stringify(this.patientData))
        })
        getNationData().then(({ data }) => {
          this.nationOptions = data
          this.patientData = JSON.parse(JSON.stringify(this.patientData))
        })
      }
      getPatientData({ patientCode })
        .then(({ data }) => {
          this.patientData = data
          this.tableList = data.examfeeitemData
          if (this.payOptions.length) {
            this.tableList.forEach((el) => {
              el.price = Number(el.price).toFixed(1)
              el.factprice = Number(el.factprice).toFixed(1)
              this.payOptions.forEach((val) => {
                if (el.idPayway == val.id) {
                  el.payWayName = val.fzx
                }
              })
            })
            this.tableList = JSON.parse(JSON.stringify(this.tableList))
          }
          loading.close()
        })
        .catch(() => {
          loading.close()
        })
    },
    // 表单重置
    reset() {
      this.resetForm('queryExam')
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
  },
}
</script>
