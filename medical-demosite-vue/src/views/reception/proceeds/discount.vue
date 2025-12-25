<!-- 折扣对话框 麦沃德科技 予安 -->
<template>
  <el-dialog title="折扣或优惠价" :visible.sync="openDiscount" width="630px" append-to-body style="overflow: hidden" :close-on-click-modal="false">
    <el-form :model="discountParams" ref="discountForm" size="small" label-width="135px">
      <el-form-item label="优惠折扣方式" prop="way">
        <el-radio v-model="discountParams.way" label="1">批量项目折扣率</el-radio>
        <el-select v-model="discountParams.choose" placeholder="请选择" style="width: 300px" :disabled="discountParams.way != 1" @change="chooseChange">
          <el-option label="所有[未收费]的项目，但不包含[统收]及[禁止打折]项目" value="1"> </el-option>
          <el-option label="批量(多选)所有[未收费]的项目，但不包含[统收]及[禁止打折]项目" value="2" :disabled="!selectTable.length"> </el-option>
          <el-option label="所有[未收费]的项目，但不包含[统收]项目" value="3"> </el-option>
          <el-option label="所有[未收费]的项目" value="4"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label=" " prop="rate">
        <div style="display: flex; align-items: center">
          <el-radio v-model="discountParams.way" label="2" :disabled="!selectTable.length">当前项目折扣率</el-radio>
          <div style="width: 300px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap">{{ discountParams.itemName }}</div>
        </div>
      </el-form-item>
      <el-form-item label=" " prop="price">
        <div style="display: flex; align-items: center">
          <el-radio v-model="discountParams.way" label="3" :disabled="!selectTable.length">当前项目优惠价</el-radio>
          <div style="width: 300px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap">{{ discountParams.itemName }}</div>
        </div>
      </el-form-item>
      <el-form-item :label="(discountParams.way == 1 && discountParams.choose == '2' ? '批量' : discountParams.way == 1 ? '所有' : '当前') + '项目原始价格'" prop="originalPrice">
        <!-- <el-input v-model="discountParams.originalPrice" clearable type="number" ></el-input> -->
        <div style="width: 450px; padding-left: 15px">{{ discountParams.originalPrice ? Number(discountParams.originalPrice).toFixed(2) : '0.00' }}</div>
      </el-form-item>
      <el-form-item label="当前或批量扣率%" prop="deRate">
        <el-input-number v-if="discountParams.way != 3" v-model="discountParams.deRate" clearable style="width: 450px" :precision="1" :min="0" controls-position="right" @change="rateChange(discountParams.deRate)"></el-input-number>
        <div v-else style="padding-left: 15px">{{ discountParams.deRate ? discountParams.deRate.toFixed(1) : '0.0' }}</div>
      </el-form-item>
      <el-form-item :label="(discountParams.way == 1 && discountParams.choose == '2' ? '批量' : discountParams.way == 1 ? '所有' : '当前') + '项目折后价格'" prop="truePrice">
        <el-input-number v-if="discountParams.way == 3" v-model="discountParams.truePrice" clearable style="width: 450px" :precision="2" :min="0" controls-position="right" @change="priceChange(discountParams.truePrice)"></el-input-number>
        <div v-else style="padding-left: 15px">{{ discountParams.truePrice ? Number(discountParams.truePrice).toFixed(2) : '0.00' }}</div>
      </el-form-item>
    </el-form>
    <el-tag style="width: 100%; height: auto; white-space: inherit" type="danger">价格圆整至：1角 说明：无折扣则扣率为0%，九折为10%，八折为20%，...，免费为100%</el-tag>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="discountConfirm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getReadItems } from '@/api/reception/proceeds.js'

export default {
  data() {
    return {
      // 折扣弹窗
      openDiscount: false,
      // 折扣参数
      discountParams: {},
      // 选择的数据
      selectTable: [],
      // 全部列表数据
      tableList: [],
    }
  },
  watch: {
    'discountParams.way': {
      handler(newVal) {
        if (newVal == 1) {
          this.chooseChange()
        } else {
          this.discountParams.originalPrice = this.selectTable[0].price
          this.discountParams.truePrice = this.selectTable[0].factprice
          this.discountParams.deRate = ((this.discountParams.originalPrice - this.discountParams.truePrice) / this.discountParams.originalPrice) * 100
        }
      },
      // immediate: true, 
    },
  },
  methods: {
    showDialog(selectTable, tableList) {
      this.openDiscount = true
      this.reset()
      this.selectTable = selectTable
      this.tableList = tableList
      if (selectTable.length) {
        this.discountParams.itemName = selectTable[0].examfeeitemName
        this.discountParams.originalPrice = selectTable[0].price.toFixed(2)
        this.discountParams.truePrice = selectTable[0].factprice.toFixed(2)
        this.discountParams.deRate = ((this.discountParams.originalPrice - this.discountParams.truePrice) / this.discountParams.originalPrice) * 100
      } else {
        this.discountParams.way = '1'
      }
      this.chooseChange()
    },
    // 折扣率选项改变
    chooseChange() {
      if (this.selectTable.length && this.discountParams.choose == '2') {
        let originalPrice = 0
        let truePrice = 0
        this.selectTable.forEach((el) => {
          if (el.FFeecharged != '1') {
            originalPrice += el.price
            truePrice += el.factprice
          }
        })
        this.discountParams.originalPrice = originalPrice
        this.discountParams.truePrice = truePrice
      } else {
        let originalPrice = 0
        let truePrice = 0
        this.tableList.forEach((el) => {
          if (el.FFeecharged != '1') {
            originalPrice += el.price
            truePrice += el.factprice
          }
        })
        this.discountParams.originalPrice = originalPrice
        this.discountParams.truePrice = truePrice
      }
      this.discountParams.deRate = ((this.discountParams.originalPrice - this.discountParams.truePrice) / this.discountParams.originalPrice) * 100
    },
    // 折扣率改变
    rateChange(value) {
      this.discountParams.truePrice = this.discountParams.originalPrice - this.discountParams.originalPrice * value * 0.01
    },
    // 折后价格改变
    priceChange(value) {
      if (this.selectTable.length) this.discountParams.deRate = 100 - (value / this.discountParams.originalPrice) * 100
    },
    // 折扣确定
    discountConfirm() {
      let ids = ''
      if (this.discountParams.way == 1 && this.discountParams.choose != 2) {
        ids = this.tableList.map((item) => item.idExamfeeitem).join(',')
      } else if (this.discountParams.way == 1 && this.discountParams.choose == 2) {
        ids = this.selectTable.map((item) => item.idExamfeeitem).join(',')
      } else {
        ids = this.selectTable[0].idExamfeeitem
      }
      getReadItems({ ids }).then(({ data }) => {
        this.$emit('executeDiscount', this.discountParams, data)
        this.openDiscount = false
      })
    },
    reset() {
      this.discountParams = {
        way: '3',
        choose: '1',
        itemName: undefined,
        originalPrice: '',
        deRate: 0.0,
        truePrice: 0.0,
      }
    },
    // 取消
    cancel() {
      this.openDiscount = false
    },
  },
}
</script>
