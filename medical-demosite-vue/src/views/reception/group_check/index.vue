<!-- 团检加/弃项 开发人：麦沃德科技暴雨/予安 -->
<template>
  <el-row style="height: 100%">
    <!--左侧页面-->
    <el-col :span="15" style="height: 100%">
      <div class="app-container flex-direction-column" style="padding-right: 0">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
          <el-form-item label="分中心" prop="branchIds">
            <el-select v-model="queryParams.branchIds" placeholder="请选择分中心" clearable style="width: 160px" @change="handleQuery">
              <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="订单号" prop="ddh">
            <el-input v-model="queryParams.ddh" placeholder="请输入订单号" clearable style="width: 140px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="团体名称" prop="khdwmcid">
            <input-select :selectData="currencyData" @change="currencyDataChange" :initialValue="queryParams.khdwmcid"></input-select>
          </el-form-item>
          <el-form-item label="套餐名称" prop="tcCode">
            <el-input v-model="queryParams.tcCode" placeholder="请输入拼音码" clearable style="width: 140px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="姓名" prop="inputCode">
            <el-input v-model="queryParams.inputCode" placeholder="请输入拼音码" clearable style="width: 135px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable style="width: 125px">
              <el-option label="男" :value="0" />
              <el-option label="女" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="婚姻状况" prop="idMarriage">
            <el-select v-model="queryParams.idMarriage" placeholder="请选择" clearable style="width: 110px">
              <el-option label="未婚" :value="1" />
              <el-option label="已婚" :value="2" />
              <el-option label="离异" :value="3" />
              <el-option label="丧偶" :value="4" />
              <el-option label="其他" :value="5" />
            </el-select>
          </el-form-item>
          <el-form-item label="年龄从" prop="minAge">
            <el-input v-model="queryParams.minAge" placeholder="请输入" clearable style="width: 110px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="年龄到" prop="maxAge">
            <el-input v-model="queryParams.maxAge" placeholder="请输入" clearable style="width: 110px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-lock" :disabled="multiple" @click="handleDisable(1)" v-hasPermi="['reception:groupCheck:jinjian']">禁检 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-unlock" :disabled="multiple" @click="handleDisable(0)" v-hasPermi="['reception:groupCheck:nojinjian']">反禁检 </el-button>
          </el-col>
        </el-row>
        <!-- 表格 -->
        <div class="table-box">
          <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick" @select="selectClick">
            <el-table-column fixed type="selection" min-width="55" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="订单号" prop="ddh" min-width="80" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.ddh == -1 ? '' : scope.row.ddh }}</span>
              </template>
            </el-table-column>
            <el-table-column label="团体名称" prop="orgName" min-width="200" align="center" show-overflow-tooltip />
            <el-table-column label="分组名称" prop="groupname" min-width="120" align="center" show-overflow-tooltip />
            <el-table-column label="体检号" prop="patientcode" min-width="120" align="center" />
            <el-table-column label="姓名" prop="patientname" min-width="100" align="center" />
            <el-table-column label="性别" prop="idSex" min-width="80" align="center">
              <template slot-scope="scope">
                <span>{{ ['男', '女'][scope.row.idSex] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="婚姻" prop="idMarriage" min-width="80" align="center">
              <template slot-scope="scope">
                <span>{{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][scope.row.idMarriage] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="年龄" prop="age" min-width="80" align="center" />
            <el-table-column label="出生日期" prop="birthdate" min-width="100" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.birthdate ? scope.row.birthdate.split(' ')[0] : '' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="体检类型" prop="idExamtype" min-width="100" align="center">
              <template slot-scope="scope">
                <span>{{ ['健康体检', '职业体检', '综合', '复查'][scope.row.idExamtype] }}</span>
              </template>
            </el-table-column>
            <el-table-column label="体检套餐" prop="tjtcmc" min-width="150" align="center" show-overflow-tooltip />
            <el-table-column label="体检类别" prop="medicaltype" min-width="100" align="center">
              <template slot-scope="scope">
                <span>{{ ['上岗前', '在岗期间', '离岗时', '离岗后', '应急'][scope.row.medicaltype] || '' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否禁检" prop="ispaused" min-width="100" align="center">
              <template slot-scope="scope">
                <span :style="{ color: scope.row.ispaused == 1 ? 'red' : '' }">{{ ['否', '是'][scope.row.ispaused] || '' }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" :page-sizes="[100, 200, 500]" />
      </div>
    </el-col>
    <!--右侧页面-->
    <el-col :span="9" style="height: 100%">
      <div class="app-container flex-direction-column">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAddRow" v-hasPermi="['reception:groupCheck:add']">加项</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-circle-close" :disabled="multipleR" @click="handleDeleteRow" v-hasPermi="['reception:groupCheck:remove']">退项 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-scissors" :disabled="singleR" @click="handleDiscount" v-hasPermi="['reception:groupCheck:discount']">折扣 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-folder-checked" @click="handleSave" v-hasPermi="['reception:groupCheck:save']">保存 </el-button>
          </el-col>
        </el-row>
        <div class="table-box group-check">
          <el-table ref="tableDataR" border v-loading="loadingR" :data="tableListR" height="100%" stripe @selection-change="handleSelectionChangeR" @row-click="rowClickR">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="收费项目" prop="examfeeitemName" min-width="150" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <div v-if="scope.row.noChange">{{ scope.row.examfeeitemName }}</div>
                <input-select v-else :selectData="itemData" selectSize="mini" @change="itemChange($event, scope.$index)" :initialValue="scope.row.examfeeitemName"> </input-select>
              </template>
            </el-table-column>
            <el-table-column label="价格" align="center">
              <el-table-column label="原始价格" prop="price" min-width="100" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.price ? scope.row.price.toFixed(1) : '0.0' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="优惠单价" prop="factprice" min-width="100" align="center">
                <template slot-scope="scope">
                  <el-input-number size="mini" controls-position="right" v-model="scope.row.factprice" :precision="1" placeholder="请输入" style="width: 100%" />
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column label="登记" prop="FRegistered" min-width="120" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.FRegistered == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="退项" prop="changeItem" min-width="120" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.changeItem == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="已退" prop="FMarkFeereturn" min-width="120" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.FMarkFeereturn == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="已收" prop="FFeecharged" min-width="120" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.FFeecharged == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="已发" prop="FLabsendtolis" min-width="120" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.FLabsendtolis == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="已检" prop="FExaminated" min-width="120" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.FExaminated == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="弃检" prop="FGiveup" min-width="120" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.FGiveup == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="迟检" prop="FDelayed" min-width="120" align="center">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.FDelayed == 1"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="科室" prop="ksName" min-width="120" align="center"> </el-table-column>
          </el-table>
        </div>
      </div>
    </el-col>
    <!-- 对话框 -->
    <discount ref="discount" @discountConfirm="discountConfirm"></discount>
  </el-row>
</template>

<script>
import { getListData, savePausedStatus, getExamfeeByPatientCode, saveOrUpdateItems2, compareMinTcContent } from '@/api/reception/group_check.js'
import { getBranchData } from '@/api/statistical/professionchecks/conclusion_table.js' //获取分中心
import { getUserCid } from '@/api/system/branch.js'

import discount from './discount'
export default {
  name: 'Group_check',
  components: { discount },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 200,
        branchIds: undefined,
        ddh: undefined,
        khdwmcid: undefined,
        tcCode: undefined,
        inputCode: undefined,
        sex: undefined,
        idMarriage: undefined,
        minAge: undefined,
        maxAge: undefined,
      },
      currencyData: {
        placeholder: '请输入',
        value: '团体名称', //第二列标题
        url: '/sell/customer/getAllOrg', //请求连接
        selectWidth: 180, //选择器宽度（选填，默认230）不加px,传100%则为100%
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 左侧表格数据
      tableList: [],
      // 右侧表格
      tableListR: [],

      tempListR: [],

      tableIndexR: 0,
      idsR: [],
      singleR: true,
      multipleR: true,
      // 右侧收费项目参数
      itemData: {
        placeholder: '',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        third: '性别', //第三列标题
        url: '/reception/groupAdd/getListData', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px
        firstName: 'sfxmsrm',
        secondName: 'examfeeitemName',
        thirdName: 'xb', //接口返回值对应第三列的参数名
        isSex: true,
      },
      // 右侧表格加载中
      loadingR: false,
      // 分中心列表
      fzxOptions: [],
      // 是否为线上
      isOnline: false,
    }
  },
  created() {
    this.queryParams.branchIds = this.$getCookie('cid')
    this.isOnline = this.$getCookie('isOnline') == '1' ? true : false
    if (this.isOnline) {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
    } else {
      getUserCid().then(({ data }) => {
        this.fzxOptions = data
      })
    }
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      getListData(this.queryParams)
        .then(({ data }) => {
          this.tableList = data.records
          this.total = data.total
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
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
      this.tableListR = []
    },
    // 选择标本类型
    currencyDataChange(value) {
      this.queryParams.khdwmcid = value.id
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple = !selection.length
      this.$nextTick(() => {
        this.handleRightList()
      })
    },
    // 单击某行
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 勾选
    selectClick(selection) {
      if (selection.length) {
        this.handleSelectionChange(selection)
      } else this.tableListR = []
    },
    // 禁检
    handleDisable(type) {
      let world = ''
      if (type) {
        world = '确定要禁检?'
      } else {
        world = '确定要反禁检？'
      }
      this.$confirm(world, (type == 1 ? '' : '反') + '禁检提示', {
        type: 'warning',
      })
        .then(() => {
          let ids = this.ids.map((item) => item.id)
          savePausedStatus({
            ids,
            paused: type,
          }).then(() => {
            this.$modal.msgSuccess('操作成功')
            this.getList()
            this.tableListR = []
          })
        })
        .catch(() => {})
    },
    // 获取右侧收费项目
    handleRightList() {
      if (this.ids.length && this.ids.slice(-1)[0].patientcode) {
        this.loadingR = true
        getExamfeeByPatientCode({
          patientCode: this.ids.slice(-1)[0].patientcode,
          type: 1,
        })
          .then(({ data }) => {
            this.loadingR = false
            data.forEach((el) => {
              el.tableIndexR = 'tableIndexR' + this.tableIndexR++
              el.noChange = 1
            })
            this.tableListR = data
            this.tempListR = JSON.parse(JSON.stringify(data))
          })
          .catch(() => {
            this.loadingR = false
          })
      }
    },
    // 收费项目返回值
    itemChange($event, index) {
      $event.idExamfeeitem = JSON.parse(JSON.stringify($event.id))
      $event.id = undefined
      $event.price = $event.unitprice
      $event.factprice = $event.unitprice
      this.tableListR[index] = $event
      this.tableListR = JSON.parse(JSON.stringify(this.tableListR))
    },
    // 右侧选中行
    handleSelectionChangeR(selection) {
      this.idsR = selection.map((item) => item)
      this.singleR = selection.length != 1
      this.multipleR = !selection.length
    },
    // 单击某行
    rowClickR(row) {
      this.$refs.tableDataR.clearSelection() //清空表格数据
      this.$refs.tableDataR.toggleRowSelection(row, true)
    },
    // 增加行
    handleAddRow() {
      if (!this.ids.length || (this.ids.length && !this.ids.slice(-1)[0].patientcode)) {
        this.$alert('请选择体检人员列表记录', '提示')
        return
      }
      let obj = {
        tableIndexR: 'tableIndexR' + this.tableIndexR++,
        examfeeitemName: undefined,
        price: undefined,
        factprice: undefined,
        FRegistered: undefined,
        changeItem: undefined,
        FMarkFeereturn: undefined,
        FFeecharged: undefined,
        FLabsendtolis: undefined,
        FExaminated: undefined,
        FGiveup: undefined,
        FDelayed: undefined,
        idKs: undefined,
        numMutex: undefined,
      }
      this.tableListR.unshift(obj)
    },
    // 保存
    handleSave() {
      if (!this.ids.length || (this.ids.length && !this.ids.slice(-1)[0].patientcode)) {
        this.$alert('请选择体检人员列表记录', '提示')
        return
      }
      let groupid = this.ids[0].groupid
      let groupids = this.ids.map((item) => item.groupid)
      if (!groupids.every((item) => item == groupid)) {
        this.$alert('请选择同一分组的体检人员', '提示')
        return
      }

      for (let i = 0; i < this.tableListR.length; i++) {
        if (!this.tableListR[i].idExamfeeitem) {
          this.$delete(this.tableListR, i)
          continue
        }
        if (this.tableListR[i].changeItem != 1 && this.tableListR[i].factprice < 0) {
          this.$alert("收费项目<font color='red'>" + this.tableListR[i].examfeeitemName + '</font>优惠价不能少于0', '提示', {
            dangerouslyUseHTMLString: true,
          })
          return
        }
        if (!this.tableListR[i].id) {
          this.tableListR[i].state = 'added'
        } else {
          this.tableListR[i].state = 'modified'
        }
        for (let j = i + 1; j < this.tableListR.length; j++) {
          if (this.tableListR[i].idExamfeeitem == this.tableListR[j].idExamfeeitem) {
            this.$alert("存在相同的收费项目：<font color='red'>" + this.tableListR[i].examfeeitemName + '</font>', '提示', {
              dangerouslyUseHTMLString: true,
            })
            return
          }
        }
      }
      let griddata = JSON.parse(JSON.stringify(this.tableListR))
      this.tempListR.forEach((val) => {
        let has = false
        griddata.forEach((el) => {
          el.idKs = el.idDepart
          if (val.id == el.id) {
            has = true
          }
        })
        if (!has) {
          val.state = 'removed'
          griddata.unshift(val)
        }
      })
      this.loadingR = true
      let ids = this.ids.map((item) => item.id)
      saveOrUpdateItems2({
        groupid,
        ids,
        griddata,
      })
        .then(() => {
          this.loadingR = false
          this.handleRightList()
        })
        .catch(() => {
          this.loadingR = false
        })
    },
    // 删除选中（退项）
    handleDeleteRow() {
      let error = ''
      let ids = []
      this.idsR.forEach((el) => {
        if (el.examfeeitemName == '个检报告工本费') {
          error = '个检报告工本费不可以退项'
          return
        }
        if (el.factprice > 0) {
          error = el.examfeeitemName + '优惠价不能多于0'
          return
        }
        ids.push(el.idExamfeeitem)
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      compareMinTcContent({
        griddata: [this.ids.slice(-1)[0]],
        ids,
      }).then(() => {
        this.$confirm('确定要退项？', '提示')
          .then(() => {
            this.idsR.forEach((val) => {
              for (let i = this.tableListR.length - 1; i >= 0; i--) {
                if (this.tableListR[i].tableIndexR == val.tableIndexR) {
                  this.$delete(this.tableListR, i)
                }
              }
              this.tableListR = JSON.parse(JSON.stringify(this.tableListR))
            })
          })
          .catch(() => {})
      })
    },
    //折扣
    handleDiscount() {
      this.$refs.discount.handleDiscount(this.idsR[0])
    },
    // 执行折扣
    discountConfirm(price) {
      this.tableListR.forEach((el) => {
        if (el.tableIndexR == this.idsR[0].tableIndexR) {
          el.factprice = price
        }
      })
      this.$modal.msgSuccess('修改成功')
    },
  },
}
</script>
<style lang="scss">
.group-check .el-table--medium .el-table__cell {
  padding: 0;
  .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
    background: transparent;
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
  .el-input-number__decrease,
  .el-input-number__increase {
    display: none;
  }
}
</style>
