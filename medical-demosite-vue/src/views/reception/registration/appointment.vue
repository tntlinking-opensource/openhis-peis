<template>
  <!-- 弹窗部分 -->
  <el-dialog title="完成预约" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" label-width="110px" :inline="true" hide-required-asterisk>
      <el-form-item label="预约类型" prop="levelId">
        <el-select v-model="form.levelId" placeholder="请选择预约类型" @change="typeChange" clearable style="width: 350px" disabled>
          <el-option v-for="options in memberTypeList" :key="options.levelId" :label="options.levelName" :value="options.levelId" />
        </el-select>
      </el-form-item>
      <el-form-item label="姓名" prop="realName">
        <el-input v-model="form.realName" placeholder="请输入体检者姓名" style="width: 350px" />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select style="width: 350px" v-model="form.sex" placeholder="请选择性别">
          <el-option label="男" :value="0"></el-option>
          <el-option label="女" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="身份证号" prop="idcard">
        <el-input v-model="form.idcard" placeholder="请输入体检者身份证号" style="width: 350px" />
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="form.mobile" placeholder="请输入体检者手机号" style="width: 350px" />
      </el-form-item>
      <el-form-item label="预约备注" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入预约备注" style="width: 350px" />
      </el-form-item>
      <el-form-item label="预约日期" prop="reservationDate">
        <el-date-picker style="width: 350px" v-model="form.reservationDates" placeholder="请选择预约日期" type="date" value-format="yyyy-MM-dd" @change="getTimeslot(false)"> </el-date-picker>
      </el-form-item>
      <el-form-item label="预约分中心" prop="fzx">
        <el-select v-model="form.branchId" placeholder="请选择预约的分中心" disabled ref="fzx" style="width: 350px">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
        </el-select>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="getTimeslot(false)">查询当日预约时间段</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-circle-close" @click="handleCancel">取消预约</el-button>
      </el-col>
    </el-row>
    <el-tag style="width: 100%; font-size: 15px">请选择预约时间段</el-tag>
    <el-table ref="tables" border :data="examList" stripe v-loading="loading" height="300px" @selection-change="handleSelectionChanges" @row-click="handleRowClick">
      <el-table-column type="selection" align="center" />
      <el-table-column label="序列" type="index" width="60" align="center" />
      <el-table-column label="分中心" prop="branchName" align="center" show-overflow-tooltip min-width="90" />
      <el-table-column label="预约类型" prop="levelName" align="center" show-overflow-tooltip min-width="80" />
      <el-table-column label="预约状态" prop="status" align="center" show-overflow-tooltip min-width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.ableNum == 0" style="color: red">已约满</span>
          <span v-if="scope.row.status == 1 && scope.row.ableNum != 0" style="color: green">开放预约</span>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" prop="startTime" align="center" show-overflow-tooltip min-width="80" />
      <el-table-column label="结束时间" prop="endTime" align="center" show-overflow-tooltip min-width="80" />
      <el-table-column label="人数上限" prop="maxNum" align="center" show-overflow-tooltip min-width="80" />
      <el-table-column label="已预约人数" prop="doneNum" align="center" show-overflow-tooltip min-width="90" />
      <el-table-column label="可预约人数" prop="ableNum" align="center" show-overflow-tooltip min-width="90" />
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" v-if="!hiddenBtn">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getListUserData, getLists, saOrUp, getReservation, cancelApi } from '@/api/subscribe/my_appointment/my_appointment'
export default {
  props: ['fzxOptions', 'memberTypeList'],
  data() {
    return {
      open: false,
      // 加载中
      loading: false,
      ///弹出框的表
      examList: [],
      // 表单数据
      form: {},
      // 表单效验
      rules: {
        khdwmcid: [{ required: true, message: '客户单位名称不能为空', trigger: 'change' }],
        remindTime: [{ required: true, message: '提醒时间不能为空', trigger: 'change' }],
        content: [{ required: true, message: '提醒内容不能为空', trigger: 'change' }],
      },
      // 隐藏确定按钮
      hiddenBtn: true,
      // 提交防抖
      debounceBtn: false,
    }
  },
  methods: {
    showDialog(info) {
      this.open = true
      this.form = info
      this.debounceBtn = false
      this.hiddenBtn = true
      getListUserData().then((response) => {
        this.levelIdOptions = response.data
      })
      getReservation({ patientcode: info.patientcode }).then(({ data }) => {
        if (data) {
          this.form.id = data.id
          this.form.reservationNo = data.reservationNo
          if (data.remark) {
            this.form.remark = data.remark
          }
          if (data.status != 1) {
            this.hiddenBtn = true
            this.$alert('当前体检号已预约', '提示')
          } else {
            this.hiddenBtn = false
          }
          let reservationDate = data.reservationDate
          if (reservationDate) {
            this.form.reservationDates = reservationDate.split(' ')[0]
            this.getTimeslot(data.timeId)
          } else {
            this.getTimeslot(false)
          }
        } else {
          this.hiddenBtn = false
          this.getTimeslot(false)
        }
      })
    },
    typeChange() {
      for (var i in this.levelIdOptions) {
        if (this.levelIdOptions[i].levelId == this.form.levelId) {
          this.form.levelName = this.levelIdOptions[i].levelName
        }
      }
    },
    handleSelectionChanges(selection) {
      // this.levelIds = selection.map((item) => item.levelId).toString()
      // this.branchIds = selection.map((item) => item.branchId).toString()
      // 单选
      if (selection.length > 1) {
        this.$refs.tables.clearSelection()
        this.$alert('请选择一个时间段进行预约!', '提示')
      } else if (selection.length == 1) {
        this.form.timeId = selection[0].id
        this.form.reservationDate = this.form.reservationDates.slice(0, 10) + ' ' + selection[0].startTime
      }
    },
    handleRowClick(row, col) {
      if (col.type != 'selection') this.$refs.tables.clearSelection()
      this.$refs.tables.toggleRowSelection(row)
    },
    //获取当日时间段
    getTimeslot(id) {
      if (!this.form.reservationDates) {
        this.$alert('请选择预约日期后查询!', '提示', {
          confirmButtonText: '确定',
        })
      } else if (!this.form.branchId) {
        this.$alert('请选择预约分中心后查询!', '提示', {
          confirmButtonText: '确定',
        })
      } else {
        this.loading = true
        console.log(this.form.reservationDates)
        let param = {
          reservationDate: this.form.reservationDates.slice(0, 10) + ' 00:00:00',
          branchId: this.form.branchId,
          levelId: this.form.levelId,
        }
        getLists(param)
          .then((response) => {
            this.examList = response.data
            if (id) {
              this.examList.forEach((el) => {
                if (el.id == id) {
                  this.$nextTick(() => {
                    this.$refs.tables.clearSelection()
                    this.$refs.tables.toggleRowSelection(el)
                  })
                }
              })
            }
            this.loading = false
          })
          .catch((err) => {
            console.error(err)
            this.loading = false
          })
      }
    },
    // 取消预约
    handleCancel() {
      if (!this.form.reservationNo) {
        this.$alert('当前体检者未预约', '提示')
        return
      }
      this.$confirm('确认取消当前预约吗?', '提示')
        .then(() => {
          cancelApi({
            reservationNo: this.form.reservationNo,
            modifier: this.$getCookie('userNo'),
          }).then(() => {
            this.$modal.msgSuccess('取消预约成功')
            this.open = false
          })
        })
        .catch(() => { })
    },
    //弹窗保存按钮
    submitForm() {
      if (this.debounceBtn) {
        this.$modal.msgWarning('提交中，请勿重复点击')
        return
      }

      // 表单验证
      if (this.form.timeId == undefined) {
        this.$alert('请选择一个时间段进行预约 !', '提示', {
          confirmButtonText: '确定',
        })
        return;
      } else if (this.form.mobile == undefined || this.form.idcard == undefined || this.form.realName == undefined) {
        this.$alert('请完善预约信息后提交(信息不能留空) !', '提示', {
          confirmButtonText: '确定',
        })
        return;
      } else {
        this.debounceBtn = true

        saOrUp(this.form)
          .then((response) => {
            this.$modal.msgSuccess('预约成功!')
            this.open = false
          })
          .catch((err) => {
            console.error(err)
            this.$modal.msgError('预约失败，请重试')
          })
          .finally(() => {
            setTimeout(() => {
              this.debounceBtn = false
            }, 1000)
          })
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
  },
}
</script>
