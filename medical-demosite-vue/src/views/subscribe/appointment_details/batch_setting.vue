<!-- 批量设置  开发人：麦沃德科技 予安-->
<template>
  <!-- 弹窗部分 -->
  <el-dialog title="批量设置预约" :visible.sync="open" width="800px" append-to-body>
    <el-form ref="form" :model="form" inline v-loading="loading" label-width="140px" hide-required-asterisk>
      <el-form-item label="预约类型" prop="maxNum">
        <div style="width: 60px">普通会员</div>
      </el-form-item>
      <el-form-item label="预约人数上限" prop="doneNum">
        <el-input-number v-model="form.settingsParam[0].num" controls-position="right" :min="0" style="width: 120px" />
      </el-form-item>
      <el-form-item label="是否开放预约" prop="status">
        <template>
          <el-radio v-model="form.settingsParam[0].status" :label="1">是</el-radio>
          <el-radio v-model="form.settingsParam[0].status" :label="0">否</el-radio>
        </template>
      </el-form-item>
      <el-form-item label="预约类型" prop="maxNum">
        <div style="width: 60px">vip</div>
      </el-form-item>
      <el-form-item label="预约人数上限" prop="doneNum">
        <el-input-number v-model="form.settingsParam[1].num" controls-position="right" :min="0" style="width: 120px" />
      </el-form-item>
      <el-form-item label="是否开放预约" prop="status">
        <template>
          <el-radio v-model="form.settingsParam[1].status" :label="1">是</el-radio>
          <el-radio v-model="form.settingsParam[1].status" :label="0">否</el-radio>
        </template>
      </el-form-item>
      <el-form-item label="预约类型" prop="maxNum">
        <div style="width: 60px">vvip</div>
      </el-form-item>
      <el-form-item label="预约人数上限" prop="doneNum">
        <el-input-number v-model="form.settingsParam[2].num" controls-position="right" :min="0" style="width: 120px" />
      </el-form-item>
      <el-form-item label="是否开放预约" prop="status">
        <template>
          <el-radio v-model="form.settingsParam[2].status" :label="1">是</el-radio>
          <el-radio v-model="form.settingsParam[2].status" :label="0">否</el-radio>
        </template>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { batchSettings } from '@/api/subscribe/appointment_details/appointment_details'

export default {
  data() {
    return {
      loading: false,
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {
        reservationDate: undefined,
        settingsParam: [
          {
            levelId: 1,
            num: 0,
            status: undefined,
          },
          {
            levelId: 2,
            num: 0,
            status: undefined,
          },
          {
            levelId: 3,
            num: 0,
            status: undefined,
          },
        ],
      },
    }
  },
  methods: {
    // 新增
    showDialog(reservationDate) {
      this.reset()
      this.open = true
      this.form.reservationDate = reservationDate
    },
    // 表单重置
    reset() {
      this.form = {
        reservationDate: undefined,
        settingsParam: [
          {
            levelId: 1,
            num: 0,
            status: undefined,
          },
          {
            levelId: 2,
            num: 0,
            status: undefined,
          },
          {
            levelId: 3,
            num: 0,
            status: undefined,
          },
        ],
      }
    },
    // 确定
    handleConfirm() {
      this.loading = true
      batchSettings(this.form)
        .then(() => {
          this.$modal.msgSuccess('批量设置成功')
          this.loading = false
          this.open = false
          this.$emit('getRightTabel')
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
  },
}
</script>
