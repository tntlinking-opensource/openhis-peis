<!-- 团检报告审批-审批 开发人：麦沃德科技暴雨/半夏 -->
<template>
  <el-dialog :title="title" :visible.sync="open" class="group-audit" width="1210px" append-to-body>
    <div class="flex-direction-column">
      <el-form ref="form" :model="form" label-width="140px" :inline="true" hide-required-asterisk>
        <el-form-item label="样本名称:" prop="sampleName" style="margin-right: 24px">
          <el-input :value="form.sampleName" readonly style="width: 420px" />
        </el-form-item>
        <el-form-item label="样本类型:" prop="sampleType" style="margin-right: 0">
          <el-input :value="form.sampleType" readonly style="width: 420px" />
        </el-form-item>
        <el-form-item label="团体名称:" prop="orgName" style="margin-right: 24px">
          <el-input :value="form.orgName" readonly style="width: 420px" />
        </el-form-item>
        <el-form-item label="订单号:" prop="orderId" style="margin-right: 0">
          <el-input :value="form.orderId" readonly style="width: 420px" />
        </el-form-item>
        <el-form-item label="开始登记时间:" prop="beginTime" style="margin-right: 24px">
          <el-input :value="form.beginTime" readonly style="width: 420px" />
        </el-form-item>
        <el-form-item label="结束登记时间:" prop="endTime" style="margin-right: 0">
          <el-input :value="form.endTime" readonly style="width: 420px" />
        </el-form-item>
        <el-form-item label="体检团体类型:" prop="idOrgclass" style="margin-right: 24px">
          <el-input :value="setGroupType(form.idOrgclass)" readonly style="width: 420px" />
        </el-form-item>
        <el-form-item label="上岗类型:" prop="sglx" style="margin-right: 0">
          <el-input :value="setMedicalType(form.sglx)" readonly style="width: 420px" />
        </el-form-item>
        <el-form-item label="未通过原因:" prop="spyj" style="margin-right: 0">
          <el-input v-model="form.spyj" placeholder="请输入审批意见" type="textarea" :rows="4" clearable style="width: 1004px" />
        </el-form-item>
      </el-form>
      <!-- 表格 -->
      <div class="table-box" style="min-height: 150px">
        <el-table border v-loading="loading" :data="tableList" height="100%" stripe>
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="体检号" prop="patientcode" min-width="140" align="center" show-overflow-tooltip />
          <el-table-column label="姓名" prop="patientname" min-width="100" align="center" show-overflow-tooltip />
          <el-table-column label="体检状态" prop="tjzt" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="健康状态" prop="jktjzt" min-width="120" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ scope.row.jktjzt ? checkType[scope.row.jktjzt].text : '' }}
            </template>
          </el-table-column>
          <el-table-column label="职业状态" prop="zytjzt" min-width="120" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ scope.row.zytjzt ? checkType[scope.row.zytjzt].text : '' }}
            </template>
          </el-table-column>
          <el-table-column label="性别" prop="idSex" min-width="80" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ ['男', '女'][scope.row.idSex] }}
            </template>
          </el-table-column>
          <el-table-column label="年龄" prop="age" min-width="80" align="center" show-overflow-tooltip />
          <el-table-column label="团体名称" prop="orgName" min-width="200" align="center" show-overflow-tooltip />
          <el-table-column label="部门名称" prop="orgDepart" min-width="140" align="center" show-overflow-tooltip />
        </el-table>
      </div>
      <pagination v-show="total > 0" :total="total" :page.sync="pageInfo.current" :limit.sync="pageInfo.size" @pagination="getList" />
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitFinish(2)">通 过</el-button>
      <el-button type="primary" plain @click="submitFinish(3)">不通过</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { reportFinish, getViewData } from '@/api/groupreport/groupreport_approval'
import { loadPersonData } from '@/api/groupreport/health_profession.js'
import { reportType } from '@/utils/dataList.js'
export default {
  data() {
    return {
      // 遮罩层
      loading: false,
      // 表单参数
      form: {},
      // 表格数据
      tableList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 表单参数-初始
      formReport: {},
      // 上岗类型
      medicalType: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      // 体检团体类型
      groupType: [
        { id: '0', text: '普通客户' },
        { id: '1', text: 'vip客户' },
        { id: '2', text: '流失客户' },
      ],
      // 健康、职业状态
      checkType: reportType(),
      // 分页信息
      pageInfo: {
        current: 1,
        size: 50,
      },
      total: 0,
    }
  },
  methods: {
    // 上岗类型
    setMedicalType(value) {
      for (var i in this.medicalType) {
        if (value == this.medicalType[i].id) {
          return this.medicalType[i].text
        }
      }
      return ''
    },
    // 体检团体类型
    setGroupType(value) {
      for (var i in this.groupType) {
        if (value == this.groupType[i].id) {
          return this.groupType[i].text
        }
      }
      return ''
    },
    // 查询列表
    getList() {
      this.loading = true
      let query = {
        groupId: this.form.groupId,
        orderId: this.form.orderId,
        reportId: this.form.id,
      }
      loadPersonData(query, this.pageInfo)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
      return
      getViewData(this.form.id).then((response) => {
        this.tableList = response.data
        this.loading = false
      })
    },
    // 打开弹窗
    handleShow(row) {
      this.reset()
      this.open = true
      this.title = '团检报告审批'
      this.form = row
      this.formReport = JSON.parse(JSON.stringify(row))
      this.getList()
    },
    // 表单重置
    reset() {
      this.form.spyj = this.formReport.spyj
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 审核按钮
    submitFinish(type) {
      this.$modal
        .confirm(type == 2 ? '确定通过吗？' : '确定不通过吗？')
        .then(() => {
          return reportFinish(this.form.id, this.form.spyj || '无', type)
        })
        .then((res) => {
          this.$modal.msgSuccess('审核已完成！')
          this.open = false
          this.$emit('getList')
        })
        .catch((err) => {
          console.error(err)
        })
    },
  },
}
</script>
<style lang="scss">
.group-audit .el-dialog {
  height: 88%;
}
</style>
