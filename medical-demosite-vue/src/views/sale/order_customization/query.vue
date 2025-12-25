<!-- 订单定制-查看套餐  麦沃德科技 开发人:清风/予安 -->
<template>
  <el-dialog title="查看套餐" :visible.sync="open" width="1400px" style="min-height: 800px" append-to-body :close-on-click-modal="false">
    <div class="check-combo" id="dialogMain" ref="comboInfo" :style="autoWidth ? 'overflow-x: auto' : ''">
      <!-- 顶部订单信息表格 -->
      <table class="tableBox" border="1">
        <tr class="tableTr">
          <td colspan="3" style="font-size: 18px; font-weight: 600; position: relative; padding: 10px 0; height: 80px">
            <img src="@/assets/logo/logo.png" alt="" style="width: 65px; height: 65px; position: absolute; top: 50%; left: 10px; transform: translateY(-50%)" />
            {{ form.tcbt }}
          </td>
        </tr>
        <tr class="tableTd">
          <td style="width: 300px"><label for="">计划检期: </label>{{ form.jhjq }}</td>
          <td><label for="">计划人数: </label>{{ form.jhrs }}人</td>
          <td><label for="">折扣: </label>{{ form.zk ? form.zk.toFixed(1) : '' }}折</td>
        </tr>
        <tr class="tableTd">
          <td><label for="">单位名称: </label>{{ form.khdwmc }}</td>
          <td><label for="">单位联系人: </label>{{ form.dwlxr }}</td>
          <td><label for="">电话: </label>{{ form.lxrdh }}</td>
        </tr>
        <tr class="tableTd">
          <td><label for="">销售经理: </label>{{ form.xsjl }}</td>
          <td colspan="2"><label for="">销售经理电话: </label>{{ form.xsjldh }}</td>
        </tr>
      </table>
      <!-- 订单项目数据表格 -->
      <table class="check-tableBox" border="1">
        <tr class="tableTr">
          <th colspan="1" class="check-td">项目</th>
          <th colspan="1" class="check-td">检查目的</th>
          <th colspan="1" class="check-td" v-for="item in form.tcjcList" :key="item.id">
            {{ item.tjtcmc }}
          </th>
          <th colspan="1" class="check-td">单价</th>
          <th colspan="1" class="check-td">成本价</th>
        </tr>
        <tr class="check-tableTd" v-if="showJhys">
          <td class="w-black">体检类别</td>
          <td></td>
          <td class="w-black" v-for="(item, index) in form.tcjcList" :key="index">{{ ['上岗前', '在岗期间', '离职时', '离岗后', '应急'][item.zytjlb] }}</td>
          <td></td>
        </tr>
        <tr class="check-tableTd">
          <td class="w-black">接害因素</td>
          <td></td>
          <td class="w-black" v-for="(item, index) in form.jhysList" :key="index">{{ item }}</td>
          <td></td>
        </tr>
        <tbody class="" v-for="(item, index) in form.sfxmData" :key="index">
          <tr>
            <td class="item-name" :colspan="4 + form.tcjcList.length">
              {{ Object.keys(form.sfxmData[index])[0] }}
            </td>
          </tr>
          <tr class="check-tableTd" v-for="(item2, index2) in form.sfxmData[index][Object.keys(form.sfxmData[index])[0]]" :key="index2">
            <td class="w-black">
              {{ form.sfxmData[index][Object.keys(form.sfxmData[index])[0]][index2].itemName }}
            </td>
            <td style="width: 360px; text-align: left">
              {{ form.sfxmData[index][Object.keys(form.sfxmData[index])[0]][index2].checkYy }}
            </td>
            <td v-for="(val, index3) in form.sfxmData[index][Object.keys(form.sfxmData[index])[0]][index2].mou" :key="index3" style="color: #0059ff; font-size: 20px">
              {{ val ? '★' : '' }}
            </td>
            <td style="width: 80px">
              {{ form.sfxmData[index][Object.keys(form.sfxmData[index])[0]][index2].price }}
            </td>
            <td style="width: 80px">
              {{ form.sfxmData[index][Object.keys(form.sfxmData[index])[0]][index2].costprice }}
            </td>
          </tr>
        </tbody>
        <tbody>
          <tr class="tableTr">
            <th colspan="1" class="check-td"></th>
            <th colspan="1" class="check-td">项目合计</th>
            <th colspan="1" class="check-td" v-for="item in form.tcjcList" :key="item.id">
              {{ item.tcysjg }}
            </th>
            <th colspan="1" class="check-td">元</th>
          </tr>
          <tr class="tableTr">
            <th colspan="1" class="check-td"></th>
            <th colspan="1" class="check-td">优惠价</th>
            <th colspan="1" class="check-td" v-for="item in form.tcjcList" :key="item.id">
              {{ item.zhjg }}
            </th>
            <th colspan="1" class="check-td">元</th>
          </tr>
          <tr class="tableTr">
            <th colspan="1" class="check-td"></th>
            <th colspan="1" class="check-td">成本价</th>
            <th colspan="1" class="check-td" v-for="item in form.tcjcList" :key="item.id">
              {{ item.totalCostprice }}
            </th>
            <th colspan="1" class="check-td">元</th>
          </tr>
          <tr class="tableTr">
            <th colspan="1" class="check-td"></th>
            <th colspan="1" class="check-td">折扣率</th>
            <th colspan="1" class="check-td" v-for="item in form.tcjcList" :key="item.id">
              {{ item.tczk }}
            </th>
            <th colspan="1" class="check-td">折</th>
          </tr>
          <tr class="tableTr">
            <th colspan="1" class="check-td"></th>
            <th colspan="1" class="check-td">变动成本率</th>
            <th colspan="1" class="check-td" v-for="item in form.tcjcList" :key="item.id">{{ ((item.totalCostprice / item.zhjg) * 100).toFixed(2) }}%</th>
            <th colspan="1" class="check-td"></th>
          </tr>
          <tr>
            <td class="item-name" :colspan="4 + form.tcjcList.length">其他（以下项目不参与折扣统计）</td>
          </tr>
          <tr class="check-tableTd" v-for="(item, index) in form.qcDataList" :key="index">
            <td class="w-black">
              {{ item.sfxmmc }}
            </td>
            <td style="width: 360px; text-align: left">
              {{ item.jcyy }}
            </td>
            <td v-for="(val, index2) in item.mou" :key="index2" style="color: #0059ff; font-size: 20px">
              {{ val ? '★' : '' }}
            </td>
            <td style="width: 80px">
              {{ item.jg }}
            </td>
          </tr>
          <tr class="tableTr">
            <th :colspan="4 + form.tcjcList.length" class="check-td">联系人: {{ form.xsjl + ' ' + (form.xsjldh || '') }}</th>
          </tr>
        </tbody>
      </table>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { viewCombo } from '@/api/sale/order_customization.js'

export default {
  props: ['autoWidth'],
  data() {
    return {
      open: false,
      // 套餐数据
      form: {
        // 套餐列表
        tcjcList: [],
        // 初始折扣
        zk: 10,
      },
      // 是否展示接害因素
      showJhys: false,
      // 是否为打印
      isPrint: false,
    }
  },
  methods: {
    queryWindow(orderId, itemIndex) {
      this.isPrint = itemIndex ? true : false
      this.form = {
        tcjcList: [],
        zk: 10,
      }
      this.open = true
      const loading = this.$loading({ target: '#dialogMain' })
      viewCombo({ id: orderId })
        .then(({ data }) => {
          if (itemIndex) {
            // 接害因素
            for (let i = data.jhysList.length - 1; i >= 0; i--) {
              if (!itemIndex.includes(i)) {
                this.$delete(data.jhysList, i)
              }
            }
            // 检查项目
            for (let i = data.tcjcList.length - 1; i >= 0; i--) {
              if (!itemIndex.includes(i)) {
                this.$delete(data.tcjcList, i)
              }
            }
            // 其他项目
            for (let i = data.qcDataList.length - 1; i >= 0; i--) {
              for (let j = data.qcDataList[i].mou.length - 1; j >= 0; j--) {
                if (!itemIndex.includes(j)) {
                  this.$delete(data.qcDataList[i].mou, j)
                }
              }
              if (!data.qcDataList[i].mou.includes(1)) {
                this.$delete(data.qcDataList, i)
              }
            }
            // 删除对应关系
            for (let i = data.sfxmData.length - 1; i >= 0; i--) {
              for (let k = data.sfxmData[i][Object.keys(data.sfxmData[i])[0]].length - 1; k >= 0; k--) {
                for (let j = data.sfxmData[i][Object.keys(data.sfxmData[i])[0]][k].mou.length - 1; j >= 0; j--) {
                  if (!itemIndex.includes(j)) {
                    this.$delete(data.sfxmData[i][Object.keys(data.sfxmData[i])[0]][k].mou, j)
                  }
                }
                if (!data.sfxmData[i][Object.keys(data.sfxmData[i])[0]][k].mou.includes(1)) {
                  this.$delete(data.sfxmData[i][Object.keys(data.sfxmData[i])[0]], k)
                }
              }
              if (!data.sfxmData[i][Object.keys(data.sfxmData[i])[0]].length) {
                this.$delete(data.sfxmData, i)
              }
            }
          }
          this.form = data
          for (let val of data.jhysList) {
            if (val) {
              this.showJhys = true
              break
            }
          }
          loading.close()
          if (itemIndex) {
            this.$nextTick(() => {
              this.$print(this.$refs.comboInfo)
              this.cancel()
            })
          }
        })
        .catch(() => {
          loading.close()
        })
    },
    cancel() {
      this.open = false
    },
  },
}
</script>

<style scoped>
.tableTr {
  text-align: center;
}
.tableTd label {
  margin-left: 20px;
}
.setTag {
  display: block;
  width: 100%;
}
#setTag /deep/ .el-form-item__content {
  width: 100%;
  background-color: transparent;
}
.tableBox {
  min-width: 820px;
  width: 100%;
  height: 200px;
  border-collapse: collapse;
  text-align: left;
  margin-bottom: 20px;
  border-color: rgb(221, 221, 221);
}
.tableBox1 {
  width: 820px;
  height: 248px;
  border-collapse: collapse;
  text-align: center;
  border-color: rgb(221, 221, 221);
}
#setColumn /deep/ .setColumn {
  background-color: #f6fbff !important;
  color: #0059ff;
}
</style>

<style lang="scss" scope>
.check-combo {
  overflow-x: auto;

  .check-tableBox {
    min-width: 820px;
    width: 100%;
    border-collapse: collapse;
    text-align: center;
    border-color: rgb(221, 221, 221);
    .check-td {
      height: auto !important;
      font-size: 14px;
      line-height: 20px;
      font-weight: 600;
      padding: 10px 0;
    }
    .item-name {
      padding: 10px;
      background: #f6fbff;
      border: 0.5px solid #c4c4c4;
      color: #0059ff;
    }
    td.w-black {
      font-weight: 600;
    }
    .check-tableTd {
      td {
        padding: 14px;
      }
    }
  }
}
</style>
