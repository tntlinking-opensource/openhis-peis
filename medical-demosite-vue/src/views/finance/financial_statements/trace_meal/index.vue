<!-- 活动套餐追踪进度  开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container target-situation">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent style="background-color: #fff; padding: 10px 16px" class="no-margin-bottom">
      <el-form-item label="对比年份" prop="now" style="margin-bottom: 0">
        <el-date-picker v-model="queryParams.now" type="month" placeholder="选择日期" value-format="yyyy-MM"> </el-date-picker>
      </el-form-item>
      <el-form-item label="套餐筛选" style="margin-bottom: 0">
        <input-select ref="selectData1" :selectData="selectData" @change="selectChange($event, 1)"></input-select>
      </el-form-item>
      <el-form-item label="~" style="margin-bottom: 0">
        <input-select ref="selectData2" :selectData="selectData" @change="selectChange($event, 2)"></input-select>
      </el-form-item>
      <el-form-item label="~" style="margin-bottom: 0">
        <input-select ref="selectData3" :selectData="selectData" @change="selectChange($event, 3)"></input-select>
      </el-form-item>
      <el-form-item style="margin-bottom: 0">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="getListData">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div id="tableMain">
      <div class="table-box">
        <div class="table">
          <table border="0" cellspacing="0" class="itemTab">
            <thead>
              <tr>
                <th colspan="1" style="min-width: 140px">排名</th>
                <th colspan="1" style="min-width: 140px">中心</th>
                <th v-for="name in name" :key="name" style="min-width: 110px">{{ name }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(list, num) in totalData" :key="num">
                <td>{{ list.index }}</td>
                <td>{{ list.fzx }}</td>
                <td v-for="(item, index) in list.data" :key="index">{{ item }}</td>
                <!-- <td :colspan="list.year ? 1 : 2" :rowspan="getRowspan(table.list, num)"
                  v-if="num == 0 || (num > 0 && list.index != table.list[num - 1].index)" style="font-weight: 600">{{
                    list.index }}</td>
                <td v-if="list.year" style="min-width: 60px">{{ list.year }}</td>
                <td v-for="(item, index) in list.value" :key="index">{{ item }}</td> -->
              </tr>
              <tr style="">
                <td :colspan="2">今日总计</td>
                <td :colspan="2">{{ today.toLocaleString('zh-CN', options) }}</td>
                <td :colspan="2">累计总计</td>
                <td :colspan="2">{{ total.toLocaleString('zh-CN', options) }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getData } from '@/api/finance/financial_statements/trace_meal.js'
export default {
  name: 'Trace_meal',
  data() {
    return {
      // 表格数
      name: [],
      // 筛选参数
      queryParams: {
        startTime: undefined,
        now: undefined,
        ids1: undefined,
        ids2: undefined,
        ids3: undefined,
      },
      // 套餐筛选参数
      selectData: {
        placeholder: '请输入输入码选择',
        value: '套餐名称', //第二列标题
        url: '/finance/statements/activityMeal/getActivityMeal', //请求连接
        bindValue: '', //初始值
        secondName: 'tjtcmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'tjtcmc', //向接口传递的参数名(不传默认为'inputCode')
      },

      total: 0,
      today: 0,
      totalData: [],
      fzx: [],
      tc1: [],
      tc2: [],
      tc3: [],
      popData: [],
      // 数据
      tableList: [],
      // 加载
      loading: false,
      //
      options: {
        style: 'currency',
        currency: 'CNY',
      },
    }
  },
  created() {
    let date = this.$getDate().split(' ')[0].slice(0, 11)
    this.queryParams.now = date
  },
  methods: {
    // 套餐筛选
    selectChange(value, i) {
      if (i == 1) {
        this.queryParams.ids1 = value.id
      } else if (i == 2) {
        this.queryParams.ids2 = value.id
      } else if (i == 3) {
        this.queryParams.ids3 = value.id
      }
    },
    //获取数据
    getListData() {
      if (!this.queryParams.ids1 || !this.queryParams.ids2 || !this.queryParams.ids3) {
        this.$modal.msgWarning('请选择三项进行比对')
        return
      }
      this.totalData = []
      this.fzx = []
      let ids = ''
      ids = this.queryParams.ids1 + ',' + this.queryParams.ids2 + ',' + this.queryParams.ids3
      let queryParams = {
        ids: ids,
        now: this.queryParams.now,
      }
      const loading = this.$loading({ target: '#tableMain' })
      getData(queryParams)
        .then((res) => {
          // this.popData = JSON.parse(JSON.stringify(res.data))
          this.popData = res.data
          var fzxdata = this.popData[0]
          for (var i in fzxdata) {
            this.fzx.push(fzxdata[i].fzx)
          }
          for (var i in this.fzx) {
            let data = {
              fzx: this.fzx[i],
              index: Number(i) + 1,
              data: [],
            }
            this.totalData.push(data)
          }
          //修改套餐名称
          this.getName()
          //将分中心放进一个数组

          loading.close()
        })
        .catch((error) => {
          console.error(error)
          loading.close()
        })
    },
    getName() {
      const options = {
        style: 'currency',
        currency: 'CNY',
      }
      this.name = []
      this.total = 0
      this.today = 0
      if (this.popData[0]) {
        this.tc1 = this.popData[0]
        this.name.push('今日' + this.tc1[0].tjtcmc + '收入(元)')
        this.name.push('累计' + this.tc1[0].tjtcmc + '收入(元)')
        for (var i in this.popData[0]) {
          this.totalData[i].data.push(Number(this.tc1[i].amount).toLocaleString('zh-CN', options))
          this.totalData[i].data.push(Number(this.tc1[i].amountTotal).toLocaleString('zh-CN', options))
          this.today += Number(this.tc1[i].amount)
          this.total += Number(this.tc1[i].amountTotal)
        }
      }
      if (this.popData[1]) {
        this.tc2 = this.popData[1]
        this.name.push('今日' + this.tc2[i].tjtcmc + '收入(元)')
        this.name.push('累计' + this.tc2[i].tjtcmc + '收入(元)')
        for (var i in this.popData[1]) {
          this.totalData[i].data.push(Number(this.tc2[i].amount).toLocaleString('zh-CN', options))
          this.totalData[i].data.push(Number(this.tc2[i].amountTotal).toLocaleString('zh-CN', options))
          this.today += Number(this.tc2[i].amount)
          this.total += Number(this.tc2[i].amountTotal)
        }
      }
      if (this.popData[2]) {
        this.tc3 = this.popData[2]
        this.name.push('今日' + this.tc3[0].tjtcmc + '收入(元)')
        this.name.push('累计' + this.tc3[0].tjtcmc + '收入(元)')
        for (var i in this.popData[2]) {
          this.totalData[i].data.push(Number(this.tc3[i].amount).toLocaleString('zh-CN', options))
          this.totalData[i].data.push(Number(this.tc3[i].amountTotal).toLocaleString('zh-CN', options))
          this.today += Number(this.tc3[i].amount)
          this.total += Number(this.tc3[i].amountTotal)
        }
      }
    },
    // 重置
    resetQuery() {
      this.queryParams = {
        now: this.$getDate().split(' ')[0].slice(0, 11),
        ids1: undefined,
        ids2: undefined,
        ids3: undefined,
      }
      this.$refs.selectData1.initData()
      this.$refs.selectData2.initData()
      this.$refs.selectData3.initData()
      this.selectData.bindValue = ''
    },
  },
}
</script>

<style lang="scss">
.target-situation {
  background: transparent;
  padding: 0;

  #tableMain {
    height: 100%;

    .table-box {
      height: 100%;
      background: #fff;
      padding: 12px 16px 16px;
      border-radius: 5px;
      margin-top: 16px;

      .title {
        font-size: 16px;
        font-weight: 600;
      }

      .table {
        width: 100%;
        overflow: auto;
      }

      table {
        width: 100%;
        margin-top: 16px;
        border: 1px solid #cccccc;
        border-collapse: collapse;
        text-align: center;

        th,
        td {
          word-break: break-all;
          border: 1px solid #cccccc;
          padding: 10px 6px;
          font-size: 14px;
          line-height: 20px;
          white-space: pre-wrap;
          color: #333333;
        }

        th {
          color: #fff;
        }

        tbody tr:nth-child(2n) td {
          background: #f9f9f9;
        }
      }

      &:nth-child(5n) table {
        th {
          background: #5470c6;
        }

        tbody tr:nth-child(2n + 1) td {
          background: #f6fcff;
        }
      }

      &:nth-child(5n + 2) table {
        th {
          background: #00a3ff;
        }

        tbody tr:nth-child(2n + 1) td {
          background: #f6fdff;
        }
      }

      &:nth-child(5n + 1) table {
        th {
          background: #ff5e5e;
        }

        tbody tr:nth-child(2n + 1) td {
          background: #fff6f6;
        }
      }

      &:nth-child(5n + 3) table {
        th {
          background: #ffab5e;
        }

        tbody tr:nth-child(2n + 1) td {
          background: #fffbf6;
        }
      }

      &:nth-child(5n + 4) table {
        th {
          background: #00c058;
        }

        tbody tr:nth-child(2n + 1) td {
          background: #f6fffa;
        }
      }
    }
  }
}
</style>
