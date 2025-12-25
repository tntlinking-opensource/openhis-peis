<!-- 折扣对话框 麦沃德科技 予安 -->
<template>
  <el-dialog title="复查额度消费" :visible.sync="open" width="445px" append-to-body :close-on-click-modal="false">
    <el-form :model="limitParams" ref="limitForm" size="small" :inline="true" label-width="90px">
      <el-form-item label="会员卡号" prop="num">
        <el-input v-model="limitParams.num" clearable style="width: 240px" placeholder="请输入会员卡号查询"></el-input>
        <el-button type="primary" @click="limitSearch" size="small">查 询</el-button>
      </el-form-item>
      <el-form-item label="消费金额" prop="cost">
        <el-input v-model="limitParams.cost" readonly style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="剩余金额" prop="residue">
        <el-input v-model="limitParams.residue" readonly style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="卡说明" prop="explain">
        <el-input v-model="limitParams.explain" clearable style="width: 300px" type="textarea" :rows="3" resize="none"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-input v-model="limitParams.note" clearable style="width: 300px" type="textarea" :rows="3" resize="none"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
      <el-button @click="openLimit = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
// import { getReadItems } from '@/api/reception/proceeds.js'

export default {
  data() {
    return {
      // 折扣弹窗
      open: false,
      // 折扣参数
      limitParams: {},
    }
  },
  methods: {
    showDialog() {
      this.open = true
      this.reset()
    },
    // 查询额度
    limitSearch() {},
    // 确定
    handleConfirm() {
      this.cancel()
    },
    reset() {
      this.limitParams = {
        num: undefined,
        cost: undefined,
        residue: undefined,
        explain: undefined,
        note: undefined,
      }
    },
    // 取消
    cancel() {
      this.open = false
    },
  },
}
</script>
