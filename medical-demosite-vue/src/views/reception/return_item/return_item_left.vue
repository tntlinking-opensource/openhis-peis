<template>
  <el-col :span="12" style="margin-right: 3px">
    <div class="flex-direction-column return-item-left" style="overflow-y: auto">
      <el-row style="width: 100%; margin-bottom: 3px; flex: 1; min-height: 300px">
        <div class="flex-direction-column" style="min-height: 300px">
          <!-- 筛选 -->
          <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" @submit.native.prevent class="no-margin-bottom">
            <el-form-item prop="autoFill">
              <el-checkbox v-model="queryParams.autoFill"></el-checkbox>
            </el-form-item>
            <el-form-item label="体检号" prop="patientCode">
              <el-input v-model="queryParams.patientCode" placeholder="请输入体检号" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
            </el-form-item>
            <el-form-item style="margin-bottom: 0">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" v-hasPermi="['reception:returnItem:search']">查询</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="warning" plain icon="el-icon-folder-add" size="mini" :disabled="multiple" @click="handleSave" v-hasPermi="['basis:registration:returnItem:save']">退项保存</el-button>
              <!-- <el-button type="danger" plain icon="el-icon-folder-add" size="mini" @click="handleReCharge" v-hasPermi="['basis:registration:returnItem:reCharge']">反收费</el-button> -->
              <el-button type="primary" plain icon="el-icon-refresh-right" size="mini" @click="handleRefresh" v-hasPermi="['basis:registration:returnItem:refresh']">刷新</el-button>
            </el-form-item>
          </el-form>

          <!-- 上表格 -->
          <div class="table-box" style="min-height: 200px">
            <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
              <el-table-column fixed type="selection" width="50" align="center" />
              <el-table-column fixed label="序列" width="50" type="index" align="center" />
              <el-table-column fixed label="收费项目" prop="examfeeitemName" align="center" show-overflow-tooltip width="200" />
              <el-table-column label="价格" align="center">
                <el-table-column label="原始单价" prop="price" align="center" min-width="100px">
                  <template slot-scope="scope">
                    <span>{{ scope.row.price ? scope.row.price.toFixed(2) : '' }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="优惠单价" prop="factprice" align="center" min-width="120px">
                  <template slot-scope="scope">
                    <span>{{ scope.row.factprice ? scope.row.factprice.toFixed(2) : '' }}</span>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="退费金额" prop="endtuiprice" align="center" min-width="120px">
                <template slot-scope="scope">
                  <el-input-number v-if="scope.row.fFeecharged == 1 && scope.row.changeItem != 1" v-model="scope.row.endtuiprice" placeholder="" size="small" :precision="2" controls-position="right" style="width: 100%" class="endtuiprice"></el-input-number>
                  <span v-else>{{ scope.row.endtuiprice || scope.row.endtuiprice === 0 ? scope.row.endtuiprice.toFixed(2) : '' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="实际金额" prop="actualprice" align="center" min-width="100px">
                <template slot-scope="scope">
                  <span>{{ scope.row.actualprice ? scope.row.actualprice.toFixed(2) : '' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="付款方式" prop="payway" align="center" show-overflow-tooltip min-width="100px">
                <template slot-scope="scope">
                  <span v-if="scope.row.idPayway == 1">现金</span>
                  <span v-else>统收</span>
                </template>
              </el-table-column>
              <el-table-column label="加项医生" prop="name" align="center" show-overflow-tooltip min-width="100px" />
              <el-table-column label="登记" prop="fRegistered" align="center" min-width="60">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.fRegistered == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="退项" prop="changeItem" align="center" min-width="60">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.changeItem == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="已退" prop="fMarkFeereturn" align="center" min-width="60">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.fMarkFeereturn == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="已收" prop="fFeecharged" align="center" min-width="60">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.fFeecharged == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="已发" prop="fLabsendtolis" align="center" min-width="60">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.fLabsendtolis == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="已检" prop="fExaminated" align="center" min-width="60">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.fExaminated == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="弃检" prop="fGiveup" align="center" min-width="60">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.fGiveup == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="迟检" prop="fDelayed" align="center" min-width="60">
                <template slot-scope="scope">
                  <el-checkbox :value="scope.row.fDelayed == 1"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="科室" prop="ksmc" align="center" show-overflow-tooltip min-width="120px" />
              <el-table-column label="登记人" prop="doctorregR" align="center" show-overflow-tooltip min-width="120px" />
              <el-table-column label="收费时间" prop="feechargetime" align="center" show-overflow-tooltip min-width="160px" />
            </el-table>
          </div>
        </div>
      </el-row>

      <!-- 下表格 -->
      <el-row style="width: 100%">
        <el-col :span="24">
          <!-- 表格 -->
          <el-table border v-loading="loading" :data="tableListBelow" height="200px" stripe @selection-change="handleSelectionChangeBelow">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="体检号" prop="patientcode" align="center" min-width="130px" />
            <el-table-column label="姓名" prop="patientname" align="center" min-width="100px" />
            <el-table-column label="性别" prop="idSex" align="center" min-width="80px">
              <template slot-scope="scope">
                <span>{{ ['男', '女'][scope.row.idSex] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" align="center" min-width="80px" />
            <el-table-column label="手机号码" prop="phone" align="center" min-width="150px" />
            <el-table-column label="身份证号" prop="idcardno" align="center" min-width="180px" />
            <el-table-column label="预约" prop="FIsforreserve" align="center" min-width="80px">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.fisforreserve == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="登记" prop="FRegistered" align="center" min-width="80px">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.fregistered == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="登记员" prop="doctorreg" align="center" show-overflow-tooltip min-width="80px" />
          </el-table>
        </el-col>
      </el-row>
    </div>
  </el-col>
</template>

<script>
import { getCustomerData } from '@/api/reception/proceeds.js'
import { updateTui } from '@/api/reception/return_item.js'

export default {
  data() {
    return {
      // 上方表格****************
      // 筛选参数
      queryParams: {
        autoFill: true,
        patientCode: undefined,
        type: 1,
      },
      // 表格加载
      loading: false,
      // 左侧选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表格数据
      tableList: [],
      // 版本号
      version: undefined,

      // 下方表格********************
      // 左侧选中数组
      idsBelow: [],
      // 非单个禁用
      singleBelow: true,
      // 非多个禁用
      multipleBelow: true,
      tableListBelow: [],
    }
  },
  created() {
    this.queryParams.patientCode = this.$route.params.patientCode ? JSON.parse(JSON.stringify(this.$route.params.patientCode)) : ''
    if (this.queryParams.patientCode) {
      this.getList()
    }
    this.bus.$on('handleGetList', () => {
      this.getList()
    })
  },
  beforeDestroy() {
    this.bus.$off('handleGetList')
  },
  methods: {
    // 获取体检者与收费项目信息
    getList() {
      this.loading = true
      this.tableList = []
      this.tableListBelow = []
      let queryParams = JSON.parse(JSON.stringify(this.queryParams))
      // queryParams.autoFill = queryParams.autoFill ? 1 : 0
      getCustomerData(queryParams)
        .then(({ data }) => {
          if (data && data.patientData) {
            this.version = data.version
            this.tableListBelow.push(data.patientData)
            if (data.examfeeitemData.length) {
              this.tableList = data.examfeeitemData
            }
            this.queryParams.patientCode = data.patientData.patientcode
            this.loading = false
            this.bus.$emit('handleRefundRight', data.patientData, data.version)
          }
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 查询
    handleQuery() {
      if (!this.queryParams.patientCode) {
        this.$modal.msgWarning('请输入体检号')
        return
      }
      this.getList()
    },
    // 退项保存
    handleSave() {
      if (!this.tableListBelow.length) {
        this.$alert('体检者信息不存在', '提示')
        return
      }
      var row1 = this.tableListBelow[0]
      var patientCode = row1.patientcode
      var patientname = row1.patientname
      // 没有体检号不能进行操作
      if (patientCode == '') {
        this.$alert('请先完成登记', '提示')
        return
      }
      var data = this.tableList
      // 是否可以删除
      var isDelete = 0
      // 是否存在退费金额大于原价
      var exMax = false
      var rows = this.ids
      // 套餐内的收费项目
      var tcsfItem = ''
      for (var i = 0; i < rows.length; i++) {
        // 退费金额
        if (rows[i].endtuiprice == null) {
          this.$alert(rows[i].examfeeitemName + ' 收费项目的退费金额存在问题！', '提示')
          return
        }
        if (rows[i].endtuiprice > 0) {
          this.$alert(rows[i].examfeeitemName + ' 收费项目的退费金额不能大于0！', '提示')
          return
        }
        if (rows[i].price + rows[i].endtuiprice < 0) {
          exMax = true
        }
        /*if (rows[i].endtuiprice <0 && rows[i].idPayway == 5) {
                            this.$alert(rows[i].examfeeitemName+" 收费项目是统收项目，不可以退费！");
                            return;
                        }*/
        // 已检状态
        if (rows[i].fExaminated == 1) {
          this.$alert(rows[i].examfeeitemName + ' 收费项目已经检查，不可以退项！', '提示')
          return
        }
        // 弃检
        if (rows[i].fGiveup == 1) {
          this.$alert(rows[i].examfeeitemName + ' 收费项目已经弃检，不可以退项！', '提示')
          return
        }
        // 退项状态
        if (rows[i].changeItem == 1) {
          this.$alert(rows[i].examfeeitemName + ' 收费项目已经退项，不可以退项！', '提示')
          return
        }
        // 没有收费状态
        if (rows[i].fFeecharged == null || rows[i].fFeecharged != 1) {
          this.$alert(rows[i].examfeeitemName + ' 收费项目没有收费，不可以退项，只能删除！', '提示')
          return
        }
        // 套餐里的收费项目
        if (rows[i].isMintc == 1) {
          isDelete = true
          tcsfItem += rows[i].examfeeitemName + '、'
        }
      }
      var title = '确定要将选择的收费项目退项？'
      // 遍历执行删除操作
      if (exMax) title = '存在退项金额多于原始价格的收费项目，确定要退项？'
      else if (isDelete) {
        title = tcsfItem.substring(0, tcsfItem.length - 1) + ' 收费项目部分属于套餐，确定要退项？'
      }
      this.$confirm(title, '提示')
        .then(() => {
          let itemList = JSON.parse(JSON.stringify(this.tableList))
          let selectList = this.ids.map((item) => item.id)
          itemList.forEach((el) => {
            if (el._state != 'added') {
              el._state = 'modified'
            }
            if (selectList.includes(el.id)) {
              el.changeItem = 1
            }
          })
          updateTui({
            patientcode: this.tableListBelow[0].patientcode,
            patientname: this.tableListBelow[0].patientname,
            version: this.version,
            itemList,
          }).then(() => {
            this.$message({
              type: 'success',
              message: '退项成功!',
            })
            this.getList()
          })
        })
        .catch(() => {})
    },
    // 反收费
    handleReCharge() {
      this.$confirm('确定要反收费？', '提示', {
        type: 'warning',
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
        })
        .catch(() => {})
    },
    // 刷新
    handleRefresh() {
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 单击某行
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },

    // 下方表格********************************
    // 多选框选中数据
    handleSelectionChangeBelow(selection) {
      this.idsBelow = selection.map((item) => item.id)
      this.singleBelow = selection.length != 1
      this.multipleBelow = !selection.length
    },
  },
}
</script>

<style lang="scss">
.return-item-left {
  overflow-y: auto;
}
.return-item-left .el-table--medium .el-table__cell {
  padding: 0;
  height: 32px;
  .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
    background: transparent;
  }
  .text-left .el-input__inner {
    text-align: left !important;
  }
  .el-input__inner {
    padding: 0 8px;
    border-width: 0;
    text-align: center;
    background: transparent;

    &:focus {
      border-width: 1px;
      text-align: left;
    }
  }
  .endtuiprice .el-input__inner {
    text-align: left;
  }
  .el-input-number.is-controls-right .el-input-number__increase,
  .el-input-number.is-controls-right .el-input-number__decrease {
    border: none;
    background-color: transparent;
  }
}
</style>
