<!-- 团体预约-添加 开发人：麦沃德科技 予安 -->
<template>
  <!-- 添加或编辑弹窗 -->
  <el-dialog title="编辑" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false" @close="cancel">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" hide-required-asterisk v-loading="addLoading" inline>
      <el-form-item label="团体订单" prop="orderNum">
        <el-input :value="form.orderName" readonly style="width: 340px"></el-input>
      </el-form-item>
      <el-form-item label="预约类型" prop="levelName">
        <el-input :value="form.levelName" readonly style="width: 340px"></el-input>
      </el-form-item>
      <el-form-item label="预约日期" prop="reservationDate">
        <el-input :value="form.reservationDate" readonly style="width: 340px"></el-input>
      </el-form-item>
      <el-form-item label="预约人数" prop="countAm">
        <el-input-number v-model="form.countAm" controls-position="right" :min="0" :precision="0" placeholder="请输入预约人数(上午)" style="width: 340px" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注" style="width: 810px" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getDetails, editListData, getReVipNumber } from '@/api/subscribe/group_appointment.js'
export default {
  data() {
    var checkDate = (rule, value, callback) => {
      if (new Date(this.form.reservationDate.split(' ')[0]) < new Date(this.$getDate().split(' ')[0])) {
        callback(new Error('预约日期不能小于当前日期'))
      } else {
        callback()
      }
    }
    return {
      // 加载中
      addLoading: false,
      // 表单参数
      form: {
        xsjlId: undefined,
        orderName: undefined,
      },
      // 是否显示弹出层
      open: false,
      // 编辑重置的数据
      updateTemp: {},
      // 表单校验
      rules: {
        orderNum: [{ required: true, message: '团体订单不能为空', trigger: 'change' }],
        reservationDate: [
          { required: true, message: '预约日期不能为空', trigger: 'change' },
          { validator: checkDate, trigger: 'change' },
        ],
      },
    }
  },
  methods: {
    // 编辑
    handleUpdate(id) {
      this.addLoading = true
      this.updateTemp = undefined
      this.open = true
      this.title = '编辑'
      getDetails(id)
        .then(({ data }) => {
          if (data.reservationDate) data.reservationDate = data.reservationDate.split(' ')[0]
          this.form = data
          this.updateTemp = JSON.parse(JSON.stringify(data))
          this.addLoading = false
        })
        .catch((error) => {
          console.error(error)
          this.addLoading = false
        })
    },
    // 表单重置
    reset() {
      this.form = this.updateTemp
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.updateTemp = {}
      this.reset()
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let form = JSON.parse(JSON.stringify(this.form))
          form.reservationDate += ' 00:00:00'
          this.addLoading = true
          if (form.levelId == 1) {
            editListData(form)
              .then(() => {
                this.$modal.msgSuccess('编辑成功')
                this.open = false
                this.$emit('getList')
              })
              .catch((error) => {
                console.error(error)
                this.addLoading = false
              })
          } else {
            getReVipNumber({
              endDate: this.form.reservationDate,
              startDate: this.form.reservationDate,
              idOrg: this.form.idOrg,
            }).then(({ data }) => {
              let msg = []
              data.forEach((el) => {
                if (Number(el.reservationNum) >= Number(el.totalNum)) {
                  msg.push(el.reservationDate.slice(0, 10))
                }
              })
              if (msg.length) {
                this.$alert(`所选日期${msg.join('、') + '，' + (this.form.levelId == 2 ? 'vip' : 'vvip')}预约已超量,请提醒审批人进行审批`, '提示')
              }
              editListData(form)
                .then(() => {
                  this.$modal.msgSuccess('编辑成功')
                  this.open = false
                  this.$emit('getList')
                })
                .catch((error) => {
                  console.error(error)
                  this.addLoading = false
                })
            })
          }
        }
      })
    },
  },
}
</script>
