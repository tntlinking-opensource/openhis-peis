<!-- 撞单排查首页  开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form ref="form" size="small" :inline="true" :model="form" class="no-margin-bottom">
      <el-form-item label="订单ID" prop="ddId">
        <el-input v-model="form.ddId" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="	创建日期" prop="date">
        <el-date-picker v-model="form.date" style="width: 360px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"> </el-date-picker>
      </el-form-item>

      <el-form-item label="是否撞单" prop="dealResult">
        <el-select v-model="form.dealResult" placeholder="">
          <el-option label="否" :value="0"></el-option>
          <el-option label="是" :value="1"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" placeholder="">
          <el-option label="未处理" :value="0"></el-option>
          <el-option label="已处理" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon- refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-s-check" v-hasPermi="['sale:bumpOrder:preview']" :disabled="single" @click="view">撞单审核</el-button>
      </el-col>
    </el-row>
    <div class="table-box" ref="tableBox">
      <el-table border v-loading="loading" :row-key="getRowKeys" @expand-change="expandChange" :expand-row-keys="expands" @selection-change="handleSelectionChange" :data="tableList" height="100%" stripe>
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="#" type="expand" align="center" width="55">
          <div style="padding: 0 30px">
            <el-table :data="bumpData" align="center" border stripe>
              <el-table-column label="订单相似度" prop="rate" style="padding: 0 10px" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                  <div style="padding: 0 50px">
                    <el-progress :width="50" :text-inside="true" :stroke-width="20" :percentage="scope.row.rate"></el-progress>
                  </div>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="ddmc" label="撞单名称"> </el-table-column>
              <el-table-column align="center" prop="planIds" label="撞单id"> </el-table-column>
            </el-table>
          </div>
        </el-table-column>
        <el-table-column label="订单名称" prop="ddmc" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="订单ID" prop="ddId" min-width="100px" align="center" show-overflow-tooltip> </el-table-column>

        <el-table-column label="是否撞单" prop="dealResult" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.dealResult == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="处理人名称" prop="dealerName" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="状态" prop="status" min-width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status == 1">已经处理</el-tag>
            <el-tag type="danger" v-else>未处理</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="处理日期" prop="dealDate" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="创建日期" prop="createdate" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="备注" prop="remark" min-width="100px" align="center" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="180" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="showDialog(scope.row)">撞单排查</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <detail ref="detail" @getList="getList"></detail>
    <handLook ref="handLook" @getList="getList"></handLook>
    <pagination v-show="total > 0" :total="total" :page.sync="form.current" :limit.sync="form.size" @pagination="getList" />
  </div>
</template>

<script>
import { getListData } from '@/api/sale/order_management/order_management.js'
import detail from './detail'
import handLook from './view'
export default {
  name:'Bump_order',
  components: { detail, handLook },
  data() {
    return {
      //展开的行数
      expands: [],
      //查询参数
      form: {
        current: 1,
        size: 10,
        date: [],
        ddId: '',
        dealResult: '',
        status: '',
      },

      bumpData: [],
      // 表格展开时加载
      expandLoading: false,
      //遮罩层
      loading: false,
      //表格数据
      tableList: [],
      single: true,
      data: [],
      //总数
      total: 1,
    }
  },

  created() {
    this.getList()
  },
  methods: {
    view() {
      // this.$router.push({ name: 'bumpOrderDetail' })

      this.$refs.handLook.handleLook(this.data)
    },
    showDialog(row) {
      if (row.status == 1) {
        this.$modal.msgWarning('此订单已经完成撞单审核，无法修改！')
      } else {
        this.$refs.detail.handleShow(row)
      }
      // this.$router.push({ name: 'bumpOrderDetail' })
    },
    handleQuery() {
      this.form.current = 1
      if (this.form.date) {
        this.form.startTime = this.form.date[0] + ' 00:00:00'
        this.form.endTime = this.form.date[1] + ' 23:59:59'
      } else {
        this.form.startTime = undefined
        this.form.endTime = undefined
      }

      this.getList()
    },
    // 获取row的key值
    getRowKeys(row) {
      return row.id
    },
    resetQuery() {
      this.resetForm('form')
      this.handleQuery()
    },
    //查询列表
    getList() {
      this.loading = true
      getListData(this.form).then((response) => {
        this.tableList = response.data.records
        for (var i in this.tableList) {
          if (this.tableList[i].conflictOrderList[0]) {
            this.tableList[i].ddmc = this.tableList[i].conflictOrderList[0].ddmc
          }
        }

        this.total = response.data.total
        this.loading = false
      })
    },

    //行展开数据
    expandChange(row, expandedRows) {
      if (expandedRows.length) {
        if (expandedRows.length) {
          this.expands = []
          if (row) {
            this.expands.push(row.id)
          }
        } else {
          this.expands = []
        }
        this.bumpData = []
        if (row) {
          const bumpid = row.planIds.split(',')
          const rate = row.rate.split(',')
          for (var i in bumpid) {
            var list = {
              planIds: undefined,
              rate: undefined,
              ddmc: undefined,
            }
            this.bumpData.push(list)
            if (bumpid[i]) {
              this.bumpData[i].planIds = bumpid[i]
              var s = Number(i) + 1
              this.bumpData[i].ddmc = row.conflictOrderList[s].ddmc
            }
            if (rate[i]) {
              this.bumpData[i].rate = rate[i]
            }
          }
          //转换rate为需要的类型
          for (var i in this.bumpData) {
            if (this.bumpData[i].rate) {
              this.bumpData[i].rate = Number(this.bumpData[i].rate.replace('%', ''))
            }
          }
        }
      } else {
        this.bumpData = []
      }
    },
    handleSelectionChange(selection) {
      if (selection.length > 0) {
        this.data = selection[0].conflictOrderList
      }

      this.single = selection.length != 1
    },
  },
}
</script>

<style scoped></style>
