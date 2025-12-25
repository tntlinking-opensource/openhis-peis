<!-- 统计管理 开发人：麦沃德科技半夏/予安 -->
<template>
  <div class="app-container target-situation">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent style="background-color: #fff; padding: 10px 16px">
      <el-form-item label="对比年份" prop="startTime" style="margin-bottom: 0">
        <el-date-picker v-model="queryParams.startTime" type="month" placeholder="选择日期" value-format="yyyy-MM" :clearable="false"> </el-date-picker>
      </el-form-item>
      <el-form-item label="对比" prop="endTime" style="margin-bottom: 0">
        <el-date-picker v-model="queryParams.endTime" type="month" placeholder="选择日期" value-format="yyyy-MM" :clearable="false"> </el-date-picker>
      </el-form-item>
      <el-form-item style="margin-bottom: 0">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="getListData">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div id="tableMain">
      <div class="table-box" v-for="table in tableList" :key="table.title">
        <div class="title">{{ table.title }}</div>
        <div class="table">
          <table border="0" cellspacing="0" class="itemTab">
            <thead>
              <tr>
                <th colspan="2" style="min-width: 170px">指标</th>
                <th v-for="name in table.name" :key="name" style="min-width: 110px">{{ name }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(list, num) in table.list" :key="num">
                <td :colspan="list.year ? 1 : 2" :rowspan="getRowspan(table.list, num)" v-if="num == 0 || (num > 0 && list.index != table.list[num - 1].index)" style="font-weight: 600">{{ list.index }}</td>
                <td v-if="list.year" style="min-width: 60px">{{ list.year }}</td>
                <td v-for="(item, index) in list.value" :key="index">{{ item }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getList } from '@/api/finance/financial_statements/target_situation.js'
export default {
  name: 'targetSituation',
  data() {
    return {
      // 筛选参数
      queryParams: {
        startTime: undefined,
        endTime: undefined,
      },
      // 数据
      tableList: [],
      // 加载
      loading: false,
    }
  },
  created() {
    let startTime = this.$getDate().split(' ')[0].slice(0, 7)
    let endTime = ''
    this.queryParams.startTime = startTime
    let month = startTime.slice(-2)
    let year = this.$getDate().split(' ')[0].slice(0, 4)
    if (month == '01') {
      year -= 1
      month = '12'
    } else {
      month = parseInt(month) - 1
      if (month < 10) {
        month = '0' + month
      }
    }
    endTime = year + '-' + month
    this.queryParams.endTime = endTime
    this.getListData()
  },
  methods: {
    //获取数据
    getListData() {
      let nowYear = this.$getDate().split(' ')[0].slice(0, 7)
      if (this.queryParams.startTime == this.queryParams.endTime) {
        this.$alert('对比月份不能相同', '提示')
        return
      } else if (this.queryParams.startTime > nowYear || this.queryParams.endTime > nowYear) {
        this.$alert('对比月份不能超过当前月份', '提示')
        return
      }
      let queryParams = {
        branchIds: this.$getCookie('cid'),
        startTime: undefined,
        endTime: undefined,
        type: 1,
      }
      if (this.queryParams.startTime) {
        queryParams.startTime = this.queryParams.startTime + '-1 00:00:00'
      }
      if (this.queryParams.endTime) {
        queryParams.endTime = this.queryParams.endTime + '-1 23:59:59'
      }
      const loading = this.$loading({ target: '#tableMain' })
      for (let type = 1; type < 9; type++) {
        getList({ ...queryParams, type })
          .then((res) => {
            this.tableList[type - 1] = res.data[0]
            if (this.tableList[7]) {
              for (var i in this.tableList[7].list) {
                if ((this.tableList[7].list[i].year = null)) {
                  this.tableList[7].list[i].year = this.tableList[7].list[0].year
                }
              }
            }
            this.tableList = JSON.parse(JSON.stringify(this.tableList))
            loading.close()
          })
          .catch((error) => {
            console.error(error)
            loading.close()
          })
      }
    },
    getRowspan(list, index) {
      var number = 1
      for (var i = index + 1; i < list.length; i++) {
        if (list[index].index == list[i].index) {
          number++
        } else {
          break
        }
      }
      return number
    },
    //获取年份方法
    formatDate(date) {
      date = new Date(date)
      let myyear = date.getFullYear() - 1
      let mymonth = date.getMonth()

      mymonth < 10 ? (mymonth = '0' + mymonth) : mymonth

      return `${myyear}-${mymonth}`
    },
    //获取年份方法
    formatDate2(date) {
      date = new Date(date)
      let myyear = date.getFullYear() - 2
      let mymonth = date.getMonth()

      mymonth < 10 ? (mymonth = '0' + mymonth) : mymonth

      return `${myyear}-${mymonth}`
    },
    // 重置
    resetQuery() {
      this.queryParams = {
        branchIds: $getCookie('cid'),
        startTime: undefined,
        endTime: undefined,
      }
      this.getListData()
    },
  },
}
</script>

<style lang="scss">
.target-situation {
  background: transparent;
  padding: 0;
  .table-box {
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

    &:nth-child(5n + 1) table {
      th {
        background: #00a3ff;
      }

      tbody tr:nth-child(2n + 1) td {
        background: #f6fdff;
      }
    }

    &:nth-child(5n + 2) table {
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
</style>
