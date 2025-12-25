<template>
  <el-col :span="12" class="order-table-left" style="margin-right: 10px">
    <!-- 筛选 -->
    <el-form size="small" :inline="true" style="padding: 12px 14px 0">
      <el-form-item style="margin-bottom: 0">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5" v-if="isOnline || showBtn || isOnlineTB">
            <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleLeftAdd" v-hasPermi="['reception:prepareOrder:edit:add']">添加</el-button>
          </el-col>
          <el-col :span="1.5" v-if="isOnline || showBtn">
            <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleLeftDelete" :disabled="multiple" v-hasPermi="['reception:prepareOrder:edit:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5" v-if="isOnline || showBtn || isOnlineTB">
            <el-button type="warning" plain size="mini" icon="el-icon-folder-add" @click="handleLeftSave" v-hasPermi="['reception:prepareOrder:edit:save']">保存</el-button>
          </el-col>
          <el-col :span="1.5" v-if="isOnline || showBtn">
            <el-button type="primary" plain size="mini" icon="el-icon-lock" :disabled="multiple" @click="handleForbidden(1)" v-hasPermi="['reception:prepareOrder:edit:jj']">禁检</el-button>
          </el-col>
          <el-col :span="1.5" v-if="isOnline || showBtn">
            <el-button type="primary" plain size="mini" icon="el-icon-unlock" :disabled="multiple" @click="handleForbidden(0)" v-hasPermi="['reception:prepareOrder:edit:fjj']">反禁检</el-button>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item label="工种名称" style="margin-bottom: 8px">
        <el-select v-model="typeName" filterable remote clearable placeholder="请输入工种关键字" :remote-method="typeSearch" :loading="typeLoading" @clear="typeOptions = []" style="width: 160px">
          <el-option v-for="item in typeOptions" :key="item.id" :label="item.typeName" :value="item.id"> </el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" size="mini" @click="setWorktype" :disabled="multiple">设置工种</el-button>
      <el-button type="primary" size="mini" @click="setPayWay('1')" :disabled="multiple">设为现金</el-button>
      <el-button type="primary" size="mini" @click="setPayWay('5')" :disabled="multiple">设为统收</el-button>
      <el-button type="primary" size="mini" @click="setRepeat" :disabled="multiple">设为重复</el-button>
      <el-form-item label="套餐名称" style="margin-bottom: 4px; margin-left: 8px">
        <el-input size="mini" v-model="tjtcmc" style="width: 160px" placeholder="回车查询" clearable @keyup.enter.native="handleSearchName" @clear="handleSearchName"></el-input>
      </el-form-item>
      <el-button type="primary" size="mini" @click="setAddList" :disabled="single">设置现场加名单</el-button>
    </el-form>
    <!-- 表格 -->
    <div style="flex: auto; max-height: 100%; min-height: 0">
      <el-table ref="tableData" class="left-table" border :data="leftTableList" @selection-change="handleSelectionChange" style="margin: 0 -1px -1px" height="100%" @row-click="rowClick" @select="selectClick" v-loading="tableLoading" :row-class-name="forbid2Red">
        <el-table-column type="selection" width="45" align="center" fixed />
        <el-table-column label="序列" type="index" width="45" align="center" />
        <el-table-column label="分组名称" prop="orgreservationgroupname" min-width="160" align="center">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="scope.row.orgreservationgroupname" placement="top">
              <el-input size="mini" v-model="scope.row.orgreservationgroupname" placeholder="请输入" style="width: 100%" />
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="体检套餐" prop="tcid" min-width="240" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row._exist">{{ scope.row.tjtcmc || scope.row.tcName }}</span>
            <template v-else>
              <el-tooltip effect="dark" :content="scope.row.tjtcmc || scope.row.tcName" placement="top">
                <input-select :selectData="selectData2" :initialValue="scope.row.tjtcmc || scope.row.tcName" @change="selectChange2($event, scope)" :tjlxTypeThird="true" :queryParams="{ idExamsuite }"></input-select>
              </el-tooltip>
            </template>
          </template>
        </el-table-column>
        <el-table-column label="体检类型" prop="idExamtype" min-width="80" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ ['健康', '职业', '综合', '复查'][scope.row.idExamtype] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="优惠价" prop="zhjg" min-width="90" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.zhjg ? Number(scope.row.zhjg).toFixed(2) : '0.00' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="付款方式" prop="idPayway" min-width="110" align="center">
          <template slot-scope="scope">
            <el-select v-model="scope.row.idPayway" filterable placeholder="请选择">
              <el-option v-for="item in payOptions" :key="item.id" :label="item.fzx" :value="item.id"> </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="分组条件" align="center">
          <el-table-column label="组类" prop="grouptype" min-width="100" align="center">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.grouptype" placeholder="请输入" style="width: 100%" />
            </template>
          </el-table-column>
          <el-table-column label="男" prop="fMale" min-width="60" align="center">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fMale == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="女" prop="fFemale" min-width="60" align="center">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fFemale == 1"></el-checkbox>
            </template>
          </el-table-column>
          <!-- <el-table-column label="已婚" prop="fHasmarried" min-width="60" align="center">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fHasmarried == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="未婚" prop="fNevermarried" min-width="60" align="center">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fNevermarried == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="优先" prop="dispOrder" min-width="60" align="center">
            <template slot-scope="scope">
              <el-input type="number" size="mini" v-model="scope.row.dispOrder" style="width: 100%" />
            </template>
          </el-table-column>
          <el-table-column label="年龄自" prop="nlz" min-width="70" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.nlz || scope.row.agemin }}</span>
            </template>
          </el-table-column>
          <el-table-column label="年龄至" prop="nld" min-width="70" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.nld || scope.row.agemax }}</span>
            </template>
          </el-table-column> -->
        </el-table-column>
        <!-- <el-table-column label="计划日期" prop="dateexamplanned" min-width="170" align="center">
          <template slot-scope="scope">
            <el-date-picker v-model="scope.row.dateexamplanned" type="date" placeholder="选择日期" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
          </template>
        </el-table-column> -->
        <el-table-column label="危害分组" prop="harmGroup" min-width="160" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.harmName || scope.row.jhysn }}</span>
          </template>
        </el-table-column>
        <el-table-column label="可重复" prop="idPatientclass3" min-width="70" align="center">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.idPatientclass3"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="套餐禁检" prop="forbidden" min-width="70" align="center">
          <template slot-scope="scope">
            <el-checkbox :value="scope.row.forbidden" :true-label="1" :false-label="0"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="分组禁检" prop="groupLimit" min-width="70" align="center">
          <template slot-scope="scope">
            <el-checkbox :value="scope.row.groupLimit" :true-label="1" :false-label="0"></el-checkbox>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination :total="total" :page.sync="pageInfo.current" :limit.sync="pageInfo.size" :page-size="100" :page-sizes="[50, 100, 150]" @pagination="getTableData(formdata.id)" />
    <add-list ref="addList"></add-list>
  </el-col>
</template>

<script>
import { getBaseWorktypeSql, getGroupData, saveOrUpdateGroup, updateNotifyRemoteOrder, setWorktype, setRepeated, getPatientData, getAllTcForOrder, updateGroupLimit } from '@/api/reception/prepare_order.js'
import addList from './add_list.vue'
export default {
  components: {
    addList,
  },
  props: ['idExamsuite', 'isOnline', 'isOnlineTB'],
  data() {
    return {
      // 顶部订单数据
      formdata: {},
      // **********左侧表格相关
      // 工种名称 
      typeName: '',
      // 加载中
      typeLoading: false,
      // 工种列表
      typeOptions: [],
      // 表格数据
      leftTableList: [],
      // 状态改变前表格数据
      tempList: [],
      // 表格索引
      tableIndex: 0,
      // 表格加载中
      tableLoading: false,
      // 选中的索引
      selectIndex: [],
      selectData: [],
      // 选中数量
      selectNum: 0,
      // 表格内体检套餐参数
      selectData2: {
        placeholder: '请选择体检套餐',
        key: '', //第一列标题
        value: '套餐名称简称', //第二列标题
        third: '体检类型', //第三列标题（没有传空）
        url: '/reception/order/getAllTcForOrder', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px
        secondName: 'tjtcjc', //接口返回值对应第二列的参数名(不传默认为'name')
        thirdName: 'tjlx', //接口返回值对应第三列的参数名
        queryData: 'key',
      },
      // 表格内支付方式参数
      payOptions: [
        { fzx: '现金', id: '1' },
        { fzx: '统收', id: '5' },
      ],
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,

      // 当前客户体检套餐最后一项
      lastCombo: {
        fkfs: undefined,
      },
      // 当前客户所有体检套餐
      allCombo: [],
      // 当前选中的表格数据
      nowRows: [],
      // 展示按钮
      showBtn: false,
      // 套餐名称筛选
      tjtcmc: '',
      //定义新变量-当前表格高度
      scrollTop: null,
      // 分页总数
      total: 0,
      pageInfo: {
        current: 1,
        size: 100,
      },
    }
  },
  mounted() {
    if (this.$getCookie('cid') == '402848e370fa7e5d01710b6be8713d90' || this.$getCookie('cid') == 'ff8080817ee18637017ee77dc0322d8c' || window.location.href.includes('http://XXX.XXX.XXX')) {
      this.showBtn = true
    }
    this.bus.$on('getLeftData', (data) => {
      data.fFinished = data.FFinished
      this.formdata = data
      this.leftTableList = []
      this.tempList = []
      this.tableIndex = 0
      this.ids = []
      this.selectIndex = []
      if (data.id) {
        this.getTableData(data.id)
      }
      getAllTcForOrder({
        idExamsuite: this.idExamsuite,
        size: 1100,
      }).then(({ data }) => {
        this.lastCombo = data.records[data.records.length - 1]
        this.allCombo = data.records
      })
    })
    this.bus.$on('returnRight', (length = 0) => {
      this.handleRightLength(length)
    })
  },
  beforeDestroy() {
    this.$refs.tableData.bodyWrapper.removeEventListener(
      'scroll',
      (res) => {
        let height = res.target
        this.scrollTop = height.scrollTop
      },
      false
    )
    this.bus.$off('getLeftData')
    this.bus.$off('returnRight')
  },
  activated() {
    if (this.nowRows) {
      let nowRows = {}
      if (Array.isArray(this.nowRows)) {
        if (this.nowRows.length) {
          nowRows = this.nowRows[this.nowRows.length - 1]
        } else {
          return
        }
      } else {
        nowRows = this.nowRows
      }
      this.rowClick(nowRows)
      // this.bus.$emit('handlePeopleData', nowRows, this.formdata)
    }
    this.saveScroll()
  },
  methods: {
    // 设置表格滚动条位置
    saveScroll() {
      this.$nextTick(() => {
        setTimeout(() => {
          var scrollTop = this.$el.querySelector('.el-table__body-wrapper')
          scrollTop.scrollTop = this.scrollTop
        }, 50)
      })
    },
    // 获取表格数据
    getTableData(idOrgRw) {
      this.tableLoading = true
      getGroupData({ idOrgRw, tjtcmc: this.tjtcmc, current: this.pageInfo.current, size: this.pageInfo.size, })
        .then(({ data }) => {
          if (data.records && data.records.length) {
            this.tempList = JSON.parse(JSON.stringify(data.records))
            data.records.forEach((el) => {
              el.tableIndex = 'tableIndex' + this.tableIndex++
              el._exist = true
              if (el.idPatientclass3 == 1) {
                el.idPatientclass3 = true
              } else {
                el.idPatientclass3 = false
              }
            })
            this.leftTableList = data.records
            this.total = data.total
            this.$refs.tableData.bodyWrapper.addEventListener(
              'scroll',
              (res) => {
                let height = res.target
                this.scrollTop = height.scrollTop
              },
              false
            )
          } else {
            this.leftTableList = []
            this.total = 0
          }
          this.tableLoading = false
        })
        .catch(() => {
          this.tableLoading = false
        })
    },
    // 查询工种
    typeSearch(typeName) {
      if (typeName != '') {
        this.typeLoading = true
        getBaseWorktypeSql({
          size: 100,
          typeName,
        }).then(({ data }) => {
          this.typeLoading = false
          this.typeOptions = data.records
        })
      }
    },
    // 设置工种
    setWorktype() {
      this.$confirm(this.typeName ? '确定要修改所选分组下所有体检者的工种吗？' : '没有选择工种，确定要删除所选分组下所有体检者的工种吗？', '提示', {
        type: 'warning',
      })
        .then(() => {
          setWorktype({
            id: this.typeName,
            ids: this.ids.join(','),
          }).then(() => {
            this.$modal.msgSuccess('设置成功')
            this.bus.$emit('handlePeopleData', this.selectData[this.selectData.length - 1], this.formdata)
          })
        })
        .catch(() => {})
    },
    // 设置付款方式
    setPayWay(type) {
      let text = ''
      text = type == 1 ? '是否将所选分组设为现金付款方式？' : '是否将所选分组设为统收付款方式？'
      this.$confirm(text, '提示', {
        type: 'warning',
      })
        .then(() => {
          this.selectData.forEach((el) => {
            this.leftTableList.forEach((val) => {
              if (el.tableIndex == val.tableIndex) {
                val.idPayway = type
              }
            })
          })
        })
        .catch(() => {})
    },
    // 设置重复
    setRepeat() {
      this.$confirm('是否将所选套餐变更为可重复状态?', '提示', {
        type: 'warning',
      })
        .then(() => {
          setRepeated({
            ids: this.ids.join(','),
          }).then(() => {
            this.$modal.msgSuccess('设置成功')
            
            this.selectData.forEach((el) => {
            this.leftTableList.forEach((val) => {
              if (el.tableIndex == val.tableIndex) {
                val.idPatientclass3 = true
              } 
            })
          })
          })
          
        })
        .catch(() => {})
    },
    // 搜索套餐名称
    handleSearchName() {
      if (!this.leftTableList.length) {
        this.$alert('暂无查询数据', '提示')
        return
      }
      this.getTableData(this.formdata.id)
    },
    // 设置现场加名单
    setAddList() {
      this.$refs.addList.handleShow({ idOrg: this.formdata.peisorgreservation.idOrg, groupId: this.ids[0] })
    },
    // 禁检/反禁检
    handleForbidden(type) {
      this.$confirm(`是否执行${type == 1 ? '禁检' : '反禁检'}操作?`, '提示')
        .then(() => {
          this.loading = true
          updateGroupLimit({
            ids: this.ids.join(','),
            type,
          })
            .then(() => {
              this.$modal.msgSuccess('操作成功')
              this.getTableData(this.formdata.id)
            })
            .catch((error) => {
              console.error(error)
              this.loading = false
            })
        })
        .catch(() => {})
    },
    // 禁检设置底色为红
    forbid2Red({ row }) {
      if (row.groupLimit == 1) {
        return 'limit-yellow'
      } else if (row.forbidden == 1) {
        return 'limit-red'
      } else {
        return ''
      }
    },
    // 左侧表格中体验套餐返回值
    selectChange2(event, scope) {
      let pay = undefined
      if (this.leftTableList[scope.$index].idPayway) {
        pay = JSON.parse(JSON.stringify(this.leftTableList[scope.$index].idPayway))
      }
      let group = undefined
      if (this.leftTableList[scope.$index].orgreservationgroupname) {
        group = JSON.parse(JSON.stringify(this.leftTableList[scope.$index].orgreservationgroupname))
      }
      let groupClass = undefined
      if (this.leftTableList[scope.$index].grouptype) {
        group = JSON.parse(JSON.stringify(this.leftTableList[scope.$index].grouptype))
      }
      let dispOrder = undefined
      if (this.leftTableList[scope.$index].dispOrder) {
        dispOrder = JSON.parse(JSON.stringify(this.leftTableList[scope.$index].dispOrder))
      }
      this.leftTableList[scope.$index] = event
      this.leftTableList[scope.$index].id = undefined
      this.leftTableList[scope.$index].fMale = event.syxb != 1 ? 1 : 0
      this.leftTableList[scope.$index].fFemale = event.syxb != 0 ? 1 : 0
      this.leftTableList[scope.$index].fHasmarried = event.sfyhtc != 1 ? 1 : 0
      this.leftTableList[scope.$index].fNevermarried = event.sfyhtc != 0 ? 1 : 0
      this.leftTableList[scope.$index].agemin = event.agemin ? event.agemin : event.nlz
      this.leftTableList[scope.$index].agemax = event.agemax ? event.agemax : event.nld
      this.leftTableList[scope.$index].idExamtype = event.tjlx
      this.leftTableList[scope.$index].harmGroup = event.jhys
      this.leftTableList[scope.$index].tcName = event.tjtcmc
      this.leftTableList[scope.$index].tableIndex = 'tableIndex' + this.tableIndex++
      if (pay) {
        this.leftTableList[scope.$index].idPayway = pay
      } else {
        this.leftTableList[scope.$index].idPayway = event.fkfs == 1 ? '1' : '5'
      }
      if (group) {
        this.leftTableList[scope.$index].orgreservationgroupname = group
      }
      if (groupClass) {
        this.leftTableList[scope.$index].grouptype = groupClass
      }
      if (dispOrder) {
        this.leftTableList[scope.$index].dispOrder = dispOrder
      }
      this.leftTableList = JSON.parse(JSON.stringify(this.leftTableList))
    },
    // 左侧多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selectIndex = selection.map((item) => item.tableIndex)
      this.selectData = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length && selection[selection.length - 1].forbidden) {
        if (selection[selection.length - 1].tjtcmc || selection[selection.length - 1].tcName) {
          this.$alert('当前选择项目已被禁检,将不能再添加体检人员', '提示')
        }
      }
    },
    // 单击选中某行
    rowClick(row, col) {
      if (col?.type != 'selection') this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
      if (this.nowRows != row) {
        this.bus.$emit('handlePeopleData', row, this.formdata)
        this.nowRows = row
      }
    },
    selectClick(row) {
      if (this.nowRows == row) {
        return
      }
      this.nowRows = row
      if (this.selectNum > row.length) {
        this.bus.$emit('handlePeopleData')
      } else {
        this.bus.$emit('handlePeopleData', row[row.length - 1], this.formdata)
      }
      this.selectNum = row.length
    },
    // 左侧添加
    handleLeftAdd() {
      if (!this.allCombo.length) {
        this.$alert('该订单下没有对应套餐', '提示')
        return
      }
      let comboIds = this.leftTableList.length ? this.leftTableList.map((item) => item.idExamsuite) : ''
      let hasAll = true
      this.loading = true
      this.allCombo.forEach((el, index) => {
        if (!comboIds.includes(el.id)) {
          hasAll = false
          let list = {
            tableIndex: 'tableIndex' + this.tableIndex++,
            orgreservationgroupname: el.tjtcmc,
            tjtcmc: undefined,
            idExamtype: undefined,
            zhjg: undefined,
            idPayway: undefined,
            grouptype: undefined,
            fMale: undefined,
            fFemale: undefined,
            fHasmarried: undefined,
            fNevermarried: undefined,
            dispOrder: 1,
            agemin: undefined,
            agemax: undefined,
            dateexamplanned: undefined,
            harmGroup: undefined,
            idPatientclass2: undefined,
            forbidden: undefined,
          }
          this.leftTableList.push(list)
          this.selectChange2(el, { $index: this.leftTableList.length - 1 })
        }
        if (hasAll && index == this.allCombo.length - 1) {
          let list = {
            tableIndex: 'tableIndex' + this.tableIndex++,
            orgreservationgroupname: undefined,
            tjtcmc: undefined,
            idExamtype: undefined,
            zhjg: undefined,
            idPayway: undefined,
            grouptype: undefined,
            fMale: undefined,
            fFemale: undefined,
            fHasmarried: undefined,
            fNevermarried: undefined,
            dispOrder: 1,
            agemin: undefined,
            agemax: undefined,
            dateexamplanned: undefined,
            harmGroup: undefined,
            idPatientclass2: undefined,
            forbidden: undefined,
          }
          // if (this.lastCombo && this.lastCombo.fkfs) {
          //   list.idPayway = this.lastCombo.fkfs
          //   list.orgreservationgroupname = this.lastCombo.tjtcmc
          // }
          this.leftTableList.push(list)
          // if (this.lastCombo) {
          //   this.selectChange2(this.lastCombo, {
          //     $index: this.leftTableList.length - 1,
          //   })
          // }
        }
      })
    },
    // 左侧删除
    async handleLeftDelete() {
      let isReady = []
      for (let i of this.ids) {
        if (i) {
          const res = await getPatientData({
            idOrgreservationgroup: i,
            inputCode: '',
            size: 999,
          })
          if (res.data.records.length) {
            this.$alert(`所选项目下包含体检人员,请删除后重试`, '提示')
            return
          } else {
            isReady.push('a')
          }
        } else {
          isReady.push('a')
        }
        if (isReady.length == this.ids.length) {
          const postIds = this.selectIndex
          this.$modal.confirm('是否确认删除该数据项？').then(() => {
            postIds.forEach((val) => {
              for (let i = this.leftTableList.length - 1; i >= 0; i--) {
                if (val == this.leftTableList[i].tableIndex) {
                  this.$delete(this.leftTableList, i)
                }
              }
            })
            this.leftTableList = JSON.parse(JSON.stringify(this.leftTableList))
          })
        }
      }
    },
    handleLeftSave() {
      let forbiddenName = []
      for (var i = 0; i < this.leftTableList.length; i++) {
        if (!this.leftTableList[i].orgreservationgroupname) {
          this.$alert('分组名称:不能为空,请修改后重新操作', '提示')
          return
        }
        //判断支付方式
        if (!this.leftTableList[i].idPayway) {
          this.$alert('支付方式:不能为空,请修改后重新操作', '提示')
          return
        }
        //判断套餐名称
        if (!this.leftTableList[i].tcName) {
          this.$alert('套餐名称:不能为空,请修改后重新操作', '提示')
          return
        }
        // 判断套餐是否禁用
        if (this.leftTableList[i].forbidden) {
          forbiddenName.push(this.leftTableList[i].tjtcmc)
        }
        this.leftTableList[i].orgreservationgroupname = this.leftTableList[i].orgreservationgroupname.trim()
        if (this.leftTableList.length > 1) {
          for (var j = i + 1; j < this.leftTableList.length; j++) {
            if (this.leftTableList[j].orgreservationgroupname) {
              this.leftTableList[j].orgreservationgroupname = this.leftTableList[j].orgreservationgroupname.trim()
            }
            if (this.leftTableList[i].orgreservationgroupname == this.leftTableList[j].orgreservationgroupname) {
              this.$alert("存在相同的 <font color='red'>" + this.leftTableList[i].orgreservationgroupname + '</font> 分组名称', '提示', {
                dangerouslyUseHTMLString: true,
              })
              return
            }
            if (this.leftTableList[i].id == this.leftTableList[j].id) {
              if (!this.leftTableList[i].id && !this.leftTableList[j].id) {
              } else {
                this.$alert("存在相同的 <font color='red'>" + (this.leftTableList[i].tcName || this.leftTableList[i].tjtcmc) + '</font> 体检套餐', '提示', {
                  dangerouslyUseHTMLString: true,
                })
                return
              }
            }
          }
        }
      }
      if (forbiddenName.length) {
        this.$alert(`体检套餐${forbiddenName.join('、')}已被禁用，将不能再添加体检人员`, '提示')
      }
      let griddata = []
      let tableList = this.leftTableList.map((item) => item.id)
      this.tempList.forEach((el) => {
        if (!tableList.includes(el.id)) {
          el.state = 'removed'
          griddata.push(el)
        }
      })
      let temp = this.tempList.map((item) => item.id)
      this.leftTableList.forEach((el) => {
        if (!temp.includes(el.id)) {
          el.state = 'added'
          el.idExamsuite = el.tcid
        } else {
          el.state = 'modified'
        }
        griddata.push(el)
      })
      if (this.formdata.peisorgreservation) {
        this.formdata = {
          ...this.formdata.peisorgreservation,
          ...this.formdata,
        }
        this.formdata.peisorgreservation = undefined
        this.formdata.ffinished = undefined
      }
      griddata.forEach((el) => {
        el.idPatientclass3 = el.idPatientclass3 ? 1 : 0
        el.idPatientclass2 = el.idPatientclass3
        el.forbidden = el.forbidden ? 1 : 0
      })
      this.formdata.FFinished = undefined
      saveOrUpdateGroup({
        formdata: this.formdata,
        griddata,
      }).then(() => {
        this.selectNum = 0
        this.$message({
          message: '保存成功',
          type: 'success',
          duration: 500,
          onClose: () => {
            this.$confirm('是否需要通知销售已备单', '通知提示', {
              type: 'warning',
            })
              .then(() => {
                updateNotifyRemoteOrder({
                  orderId: this.idExamsuite,
                  fzxId: this.formdata.fzxid,
                }).then(() => {
                  this.$message({
                    type: 'success',
                    message: '操作成功!',
                  })
                })
              })
              .catch(() => {})
          },
        })
        // 更新tempList以反映当前页面的最新状态
        this.tempList = JSON.parse(JSON.stringify(this.leftTableList))
        // 重新加载当前页面数据
        if (this.formdata.id) {
          this.getTableData(this.formdata.id)
        } else {
          this.$emit('getDetails')
        }
      })
    },
  },
}
</script>
<style lang="scss">
.order-table-left {
  flex: auto;
  display: flex;
  flex-direction: column;
  .left-table {
    .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
      background: transparent;
    }

    .el-input__inner {
      padding: 0 8px;
      border-width: 0 !important;
      text-align: center;
      background: transparent;

      &:focus {
        border-width: 1px !important;
        text-align: left;
      }
    }
    .limit-red {
      background-color: rgba($color: #ff0000, $alpha: 0.8);
    }
    .limit-yellow {
      background-color: rgba($color: #cf602c, $alpha: 0.8);
    }
  }
}
</style>
