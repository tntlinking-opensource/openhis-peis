<!-- 备单  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column prepare-order">
    <!-- 待处理事项 -->
    <el-row :gutter="10" class="mb8 show-list">
      <el-col :span="1.5" v-for="(item, index) in showList" :key="index">
        <div class="show-item">
          <img :src="require('@/assets/images/prepare_order/Group_' + (index == 0 ? 'blue' : index == 1 ? 'green' : 'red') + '.png')" alt="" />
          <div>
            <div class="blue" :class="[{ green: index == 1 }, { red: index > 1 }]">
              {{ item.num }}
            </div>
            <div class="tips">{{ item.tips }}</div>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="订单号" prop="ddh">
        <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 200px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="客户单位" prop="khdwmcid">
        <el-select v-model="queryParams.khdwmcid" filterable remote clearable placeholder="请输入关键词" :remote-method="clientSearch" :loading="clintLoading" @clear="clintOptions = []" style="width: 200px">
          <el-option v-for="item in clintOptions" :key="item.id" :label="item.khdwmc" :value="item.id"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客户单位名称" prop="khdwmc">
        <el-input v-model="queryParams.khdwmc" placeholder="请输入单位名称" clearable style="width: 200px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="团体ID" prop="intId">
        <el-input v-model="queryParams.intId" placeholder="请输入团体ID" clearable style="width: 200px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="销售经理" prop="xsjlid">
        <el-select v-model="queryParams.xsjlid" filterable remote clearable placeholder="请输入销售经理" :remote-method="saleSearch" :loading="saleLoading" @clear="saleOptions = []" style="width: 200px">
          <el-option v-for="item in saleOptions" :key="item.id" :label="item.username" :value="item.id"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="接单经理" prop="kdzlName">
        <el-input v-model="queryParams.kdzlName" placeholder="开单助理姓名" clearable style="width: 200px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="按订单排序" prop="sortByOrder">
        <el-checkbox v-model="queryParams.sortByOrder"></el-checkbox>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['reception:prepareOrder:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-refresh-right" @click="handleRefresh" v-hasPermi="['reception:prepareOrder:refresh']">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-tickets" :disabled="single" @click="handlePrepare" v-hasPermi="['reception:prepareOrder:prepare']">已备单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-switch-button" plain @click="overWindow(1)" v-hasPermi="['sale:orderPreparation:over']">结束</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="danger" icon="el-icon-circle-close" plain @click="overWindow(0)" v-hasPermi="['sale:orderPreparation:over']">反结束</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-document" :disabled="single" @click="handleCombo" v-hasPermi="['reception:prepareOrder:combo']">查看套餐</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-refresh-left" :disabled="single" @click="handleReset" v-hasPermi="['reception:prepareOrder:reset']">状态重置</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleUpload" v-hasPermi="['reception:prepareOrder:upload']">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" @click="handleHealth" v-hasPermi="['reception:prepareOrder:health']">健康导入模板下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" @click="handleOccupation" v-hasPermi="['reception:prepareOrder:occupation']">职业导入模板下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" :disabled="single" @click="handleGuide" v-hasPermi="['reception:prepareOrder:guide']">导出应急导引单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-download" @click="handleImportOrder" v-hasPermi="['reception:prepareOrder:order']">导入订单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-message" :disabled="single" @click="handleNotification" v-hasPermi="['reception:registerList:notification']">检前短信通知</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-refresh" :disabled="single" @click="handleSyncOrder" v-hasPermi="['reception:registerList:syncPerson']">同步订单下的体检者</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-refresh" :disabled="single" @click="handleSyncOrder1" v-hasPermi="['reception:registerList:syncOrders']">订单同步</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-download" :disabled="single" @click="downloadList" >下载名单</el-button>
      </el-col>
    </el-row>

    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @row-dblclick="rowDbClick">
        <el-table-column type="selection" width="55" align="center" />
        <!-- <el-table-column label="序列" width="55" type="index" align="center" /> -->
        <el-table-column label="序列" prop="rownum" align="center" width="55" />
        <el-table-column label="订单号" prop="ddh" align="center" show-overflow-tooltip width="120" />
        <el-table-column label="订单名称" prop="ddmc" align="center" show-overflow-tooltip width="170" />
        <el-table-column label="团体ID" prop="intId" align="center" show-overflow-tooltip width="100" />
        <el-table-column label="团体单位名称" prop="khdwmc" align="center" show-overflow-tooltip width="200" />
        <el-table-column label="结束状态" prop="finish" align="center" width="80">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.finish == 1">已结束</el-tag>
            <el-tag type="success" v-else>未结束</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="tbzt" align="center" show-overflow-tooltip width="120">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.isOnline == 1">线上备单</el-tag>
            <el-tag type="success" v-else-if="scope.row.tbzt == 1">已备单</el-tag>
            <el-tag v-else-if="scope.row.bgzt == 4">变更未备单</el-tag>
            <el-tag type="danger" v-else>未备单</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="销售经理" prop="xsjlmc" align="center" show-overflow-tooltip width="120" />
        <el-table-column label="接单经理" prop="kdzlName" align="center" show-overflow-tooltip width="120" />
        <el-table-column label="前台须知" prop="qtxz" align="center" show-overflow-tooltip width="240" />
        <el-table-column label="变更备注" prop="bgmemo" align="center" show-overflow-tooltip width="160" />
        <el-table-column label="提交审核日期" prop="submitTime" align="center" show-overflow-tooltip width="120">
          <template slot-scope="scope">
            {{ scope.row.submitTime ? scope.row.submitTime.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="完成日期" prop="bdrq" align="center" show-overflow-tooltip width="120">
          <template slot-scope="scope">
            {{ scope.row.bdrq ? scope.row.bdrq.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="任务预定日期" prop="jhjqc" align="center" show-overflow-tooltip width="120">
          <template slot-scope="scope">
            {{ scope.row.jhjqc ? scope.row.jhjqc.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="计划结束日期" prop="jhjqd" align="center" show-overflow-tooltip width="120">
          <template slot-scope="scope">
            {{ scope.row.jhjqd ? scope.row.jhjqd.split(' ')[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column label="预计人数" prop="plancount" align="center" show-overflow-tooltip width="120" />
        <el-table-column label="已备单人数" prop="acount" align="center" show-overflow-tooltip width="120" />
        <el-table-column label="团体地址" prop="khdwzcdz" align="center" show-overflow-tooltip width="120" />
        <el-table-column label="名单数量" prop="urls" align="center" show-overflow-tooltip width="120" />
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />

    <!-- 编辑对话框 -->
    <!-- <edit-dialog ref="editDiolog" @getList="getList">
      <template #editLeft>
        <edit-left-item :idExamsuite="ids[0]"></edit-left-item>
      </template>
      <template #editRight>
        <edit-right-item :nationList="nationList" :orderId="ids[0]"> </edit-right-item>
      </template>
    </edit-dialog> -->

    <!-- 查看套餐弹窗 -->
    <check-combo ref="checkCombo"></check-combo>

    <!-- 导入订单对话框 -->
    <el-dialog title="导入订单" :visible.sync="orderOpen" width="500px" append-to-body :close-on-click-modal="false" destroy-on-close>
      <el-form v-loading="orderLoading" @submit.native.prevent>
        <el-form-item label="订单号">
          <el-input ref="orderInput" v-model="orderNum" type="text" autosize placeholder="请填写订单号,英文逗号分隔" @keyup.enter.native="selectOrder"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="selectOrder">确 定</el-button>
        <el-button @click="cancelOrder">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getDate } from '@/utils/getDate.js'
import { getListData, getStatistics, getAllOrg, getAllUserSql2, getNationData, getNetList, markTbzt, returnToZero, importData, appointmentSMSByDddh, syncOrderData, syncOrderData1 } from '@/api/reception/prepare_order.js'
import { finishOrder } from '@/api/sale/order_preparation.js'
import { getCookie } from '@/utils/getCookie'
import editDialog from './edit/index.vue'
import checkCombo from './checkCombo.vue'
export default {
  name: 'Prepare_order',
  components: {
    editDialog,
    checkCombo,
  },
  props: [],
  data() {
    return {
      // 展示列表
      showList: [
        {
          num: 0,
          tips: '已备单',
        },
        {
          num: 0,
          tips: '线上备单',
        },
        {
          num: 0,
          tips: '未备单',
        },
        {
          num: 0,
          tips: '变更未备单',
        },
      ],
      // 客户单位加载中
      clintLoading: false,
      // 客户单位选项
      clintOptions: [],
      // 销售经理加载中
      saleLoading: false,
      // 销售经理选项
      saleOptions: [],
      // 接单经理加载中
      receiveLoading: false,
      // 接单经理选项
      receiveOptions: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        ddh: undefined,
        khdwmcid: undefined,
        khdwmc: undefined,
        intId: undefined,
        xsjlid: undefined,
        kdzlName: undefined,
        sortByOrder: undefined,
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      selectInfo: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 民族数据列表(传递给编辑页面)
      nationList: [],
      // 表格数据
      tableList: [],

      // 导入订单对话框
      orderOpen: false,
      // 导入加载中
      orderLoading: false,
      // 订单号
      orderNum: undefined,
    }
  },
  computed: {
    // 主题色
    theme() {
      return this.$store.state.settings.theme
    },
  },
  watch: {},
  created() {
    this.getList()
  },
  methods: {
    // 下载名单
    downloadList() {
      var query = {
        id: this.ids[0],
      }
      this.download(
        '/sell/createorder/filesDownload',
        {
          ...query,
        },
        `收费信息查询.zip`
      )
    },
    // 查询列表
    getList() {
      this.loading = true
      let query = JSON.parse(JSON.stringify(this.queryParams))
      query.sortByOrder = query.sortByOrder ? 1 : 0
      getListData(query).then(({ data }) => {
        this.tableList = data.records
        this.total = data.total
        this.loading = false
      })
      // 获取备单数量
      getStatistics(query).then(({ data }) => {
        this.showList[0].num = data.ybd
        this.showList[1].num = data.xsbd
        this.showList[2].num = data.wbd
        this.showList[3].num = data.bgwbd
      })
    },
    // 客户单位搜索
    clientSearch(key) {
      if (key !== '') {
        this.clintLoading = true
        getAllOrg({
          key,
          size: 999,
        }).then(({ data }) => {
          this.clintLoading = false
          this.clintOptions = data.records
        })
      }
    },
    // 销售经理搜索
    saleSearch(key) {
      if (key !== '') {
        this.saleLoading = true
        getAllUserSql2({
          key,
          size: 999,
        }).then(({ data }) => {
          this.saleLoading = false
          this.saleOptions = data
        })
      }
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 编辑
    handleUpdate() {
      this.$tab.closePage({ path: '/reception/prepare_order/edit', name: 'PrepareOrderEdit' }).then(() => {
        this.$router.push({ name: 'PrepareOrderEdit', query: { orderId: this.ids[0], orderName: this.selectInfo[0].ddmc } })
      })
    },
    // 刷新
    handleRefresh() {
      this.getList()
    },
    // 已备单
    handlePrepare() {
      this.$confirm("确定要标记为'已备单'吗？", '提示', {
        type: 'warning',
      })
        .then(() => {
          this.loading = true
          markTbzt({
            ids: this.ids[0],
          })
            .then(() => {
              this.$modal.msgSuccess('操作成功')
              this.loading = false
              this.getList()
            })
            .catch(() => {
              this.loading = false
            })
        })
        .catch(() => {})
    },
    //结束
    overWindow(type) {
      if (this.single) {
        this.$message({
          message: '请选择一条或多条数据！',
          type: 'warning',
        })
        return
      }
      this.$confirm('确定要' + (type == 1 ? '' : '反') + '结束备单吗', '提醒', {
        type: 'warning',
      })
        .then(() => {
          const ids = this.ids.join(',')
          this.loading = true
          finishOrder({
            ids,
            type,
          })
            .then(() => {
              this.$modal.msgSuccess('已更改为' + (type == 1 ? '已' : '未') + '结束')
              this.loading = false
              this.getList()
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch(() => {})
    },
    // 查看套餐
    handleCombo() {
      this.$refs.checkCombo.showDialog(this.ids[0])
    },
    // 状态重置
    handleReset() {
      this.$confirm('确定要重置所选订单的下载状态吗？', '提示', {
        type: 'warning',
      })
        .then(() => {
          this.loading = true
          returnToZero({
            cid: this.$getCookie('cid'),
            orderIds: this.ids[0],
          })
            .then(() => {
              this.$modal.msgSuccess('操作成功')
              this.loading = false
              this.getList()
            })
            .catch(() => {
              this.loading = false
            })
        })
        .catch(() => {})
    },
    // 导出
    handleUpload() {
      this.download(
        '/reception/order/export',
        {
          ...this.queryParams,
        },
        `团体体检备单${getDate()}.xlsx`
      )
    },
    // 健康导入模板下载
    handleHealth() {
      this.downloadStatic('/static/stencil/bdModel_jk.xlsx', '健康体检名单模板.xlsx')
    },
    // 职业导入模板下载
    handleOccupation() {
      this.downloadStatic('/static/stencil/bdModel_zy.xlsx', '职业体检名单模板.xlsx')
    },
    // 导出应急导引单
    handleGuide() {
      this.download(
        '/reception/order/exportGuidanceList',
        {
          id: this.ids[0],
        },
        `应急导引单${getDate()}.rar`
      )
    },
    // 导入订单
    handleImportOrder() {
      this.orderOpen = true
      this.$nextTick(() => {
        this.$refs.orderInput.focus()
      })
    },
    // 检前短信通知
    handleNotification() {
      this.$confirm('确定要取消发送检前短信吗?', '提示')
        .then(() => {
          appointmentSMSByDddh({ ddh: this.selectInfo[0].ddh }).then(() => {
            this.$modal.msgSuccess('发送成功')
          })
        })
        .catch(() => {})
    },
    // 同步订单下的体检者
    handleSyncOrder() {
      this.loading = true
      this.$modal.msgWarning('同步中')
      syncOrderData(this.selectInfo[0].ddh)
        .then(() => {
          this.$modal.msgSuccess('同步完成')
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
     // 订单同步
     handleSyncOrder1() {
      this.loading = true
      this.$modal.msgWarning('同步中')
      syncOrderData1(this.selectInfo[0].ddh)
        .then(() => {
          this.$modal.msgSuccess('同步完成')
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 确认导入
    selectOrder() {
      if (!this.orderNum) {
        this.$alert('请输入订单号', '提示')
        this.$refs.orderInput.focus()
        return
      }
      this.orderLoading = true
      importData({ ddh: this.orderNum.split(',') })
        .then((res) => {
          if (res.code == 200) {
            this.$modal.msgSuccess(res.data)
            this.cancelOrder()
            this.getList()
          } else {
            this.$alert(res.msg, '提示')
          }
          this.orderLoading = false
        })
        .catch((error) => {
          console.error(error)
          this.orderLoading = false
        })
    },
    // 取消导入
    cancelOrder() {
      this.orderNum = undefined
      this.orderOpen = false
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selectInfo = selection.map((item) => item)
      this.single = selection.length != 1
      if (selection.length == 1) {
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 单击选中某行
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 双击某行
    rowDbClick() {
      this.handleUpdate()
    },
  },
}
</script>
<style lang="scss" scoped>
.show-list {
  width: 110%;
  padding: 4px 10px;
  padding-left: 0;
  background-color: #f6f7fb;
  transform: translateX(-20px) translateY(-20px);

  .show-item {
    display: flex;
    align-items: center;
    width: 185px;
    padding: 15px 0 15px 24px;
    background-color: #fff;
    border-radius: 5px;

    img {
      width: 48px;
      height: 48px;
      margin-right: 16px;
    }

    .blue {
      color: #0059ff;
    }

    .green {
      color: #00af66;
    }

    .red {
      color: #ff6161;
    }

    .tips {
      font-size: 14px;
      line-height: 20px;
      color: #999999;
      white-space: nowrap;
    }
  }
}
</style>
<style scoped>
.prepare-order /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>
