<template>
  <el-col :span="12" style="height: 100%">
    <div class="flex-direction-column proceeds-right">
      <!-- 上方统计数据 -->
      <el-row type="flex" justify="space-between" style="width: 100%">
        <el-col :span="24" style="margin-bottom: 8px">
          <div class="statistics">
            <div class="item" v-for="(item, index) in statisticsList" :key="index">
              <div class="title">{{ item.title }}</div>
              <div class="number" :style="{ '--theme': theme }">{{ index == 0 ? item.count : item.count.toFixed(2) }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
      <!-- 中间表格信息 -->
      <div class="table-box flex-direction-column">
        <el-form size="small" :inline="true">
          <el-form-item style="margin-bottom: 0">
            <el-col :span="1.5">
              <el-button type="danger" plain size="mini" icon="el-icon-folder-add" @click="handleReverse" v-hasPermi="['reception:proceeds:reverse']">反登记</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="success" plain size="mini" icon="el-icon-scissors" @click="handleDiscount" v-hasPermi="['reception:proceeds:discount']">折扣</el-button>
            </el-col>
            <!-- <el-col :span="1.5">
              <el-button type="primary" plain size="mini" icon="el-icon-circle-check" @click="handleSubscribe" v-hasPermi="['reception:proceeds:subscribe']">完成预约</el-button>
            </el-col> -->
            <el-col :span="1.5">
              <el-button type="primary" plain size="mini" icon="el-icon-circle-check" @click="handleRegister" v-hasPermi="['reception:proceeds:register']">完成登记</el-button>
            </el-col>
          </el-form-item>
        </el-form>
        <div class="table-show">
          <el-table ref="tableData" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column fixed type="selection" width="55" align="center" />
            <el-table-column fixed label="序列" width="55" type="index" align="center" />
            <el-table-column fixed label="收费项目" prop="examfeeitemName" align="center" min-width="200" show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-if="scope.row.fRegistered == 1"> {{ scope.row.examfeeitemName }} </span>
                <input-select
                  :ref="'item' + scope.$index"
                  :selectData="itemData"
                  selectSize="mini"
                  :queryParams="{ thiredTj: info.idExamtype, isBan: 0, thiredSex: info.idSex, numorgresv: info.numorgresv }"
                  @change="onExamfeeitemChanged"
                  :initialValue="scope.row.examfeeitemName"
                  :current-index="scope.$index"
                  v-else
                ></input-select>
              </template>
            </el-table-column>
            <el-table-column label="价格" align="center">
              <el-table-column label="原始单价" prop="price" align="center" min-width="100">
                <template slot-scope="scope">
                  {{ Number(scope.row.price).toFixed(2) || '0.00' }}
                </template>
              </el-table-column>
              <el-table-column label="优惠单价" prop="factprice" align="center" min-width="120">
                <template slot-scope="scope">
                  <div v-if="scope.row.fRegistered == 1 || scope.row.fFeecharged == 1" style="padding: 0 8px">{{ scope.row.factprice ? scope.row.factprice.toFixed(2) : '' }}</div>
                  <el-input-number v-else v-model="scope.row.factprice" controls-position="right" :precision="2" size="small" class="text-left" style="width: 100%; text-align: left" />
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column label="付款方式" prop="idPayway" align="center" show-overflow-tooltip min-width="100">
              <template slot-scope="scope">
                <div v-for="item in payOptions" :key="item.id">
                  <span v-if="scope.row.idPayway == item.id">{{ item.fzx }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="加项医师" prop="name" align="center" min-width="90">
              <template slot-scope="scope">
                <span v-if="scope.row.fRegistered == 1">{{ scope.row.name }}</span>
                <input-select :ref="'jxys' + scope.$index" v-else :selectData="jxysData" selectSize="mini" :initialValue="scope.row.name" @change="jxysChange" :current-index="scope.$index"></input-select>
              </template>
            </el-table-column>
            <el-table-column label="登记" prop="fRegistered" align="center" min-width="60">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.fRegistered == 1"></el-checkbox>
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
            <!-- <el-table-column label="拒检" prop="sfjj" align="center" min-width="60">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.sfjj==1"></el-checkbox>
              </template>
            </el-table-column> -->
            <el-table-column label="加项" prop="sfjx" align="center" min-width="60">
              <template slot-scope="scope">
                <el-checkbox :value="scope.row.sfjx == 1"></el-checkbox>
              </template>
            </el-table-column>
            <!-- <el-table-column label="备注" prop="feeitemdesc" align="center" min-width="185px">
              <template slot-scope="scope">
                <el-input v-model="scope.row.feeitemdesc" style="min-width: 160px" size="small"></el-input>
              </template>
            </el-table-column> -->
            <el-table-column label="科室" prop="ksmc" align="center" show-overflow-tooltip min-width="120" />
            <el-table-column label="登记人" prop="doctorregR" align="center" min-width="125px"> </el-table-column>
            <el-table-column label="收费时间" prop="feechargetime" align="center" min-width="160" />
          </el-table>
        </div>
      </div>
    </div>
    <discount ref="discount" @executeDiscount="executeDiscount"></discount>
  </el-col>
</template>

<script>
import { setUnRegister, handleInsert } from '@/api/reception/proceeds.js'

import discount from './discount.vue'
export default {
  props: ['payOptions', 'registrantList'],
  components: {
    discount,
  },
  data() {
    return {
      // 数据列表
      statisticsList: [
        { title: '总数', count: 0 },
        { title: '原始单价合计', count: 0 },
        { title: '优惠单价合计', count: 0 },
        { title: '待收费合计', count: 0 },
        { title: '实收', count: 0 },
        { title: '应收', count: 0 },
      ],
      // 表格加载
      loading: false,
      // 表格数据
      tableList: [],
      // 表格选中的数据
      selectTable: [],
      // 体检数据
      info: {},
      // 收费项目参数
      itemData: {
        placeholder: '',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        third: '价格', //第三列标题
        url: '/items/page', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px
        firstName: 'sfxmsrm',
        secondName: 'examfeeitemName',
        thirdName: 'unitprice', //接口返回值对应第三列的参数名
      },
      // 加项医生参数
      jxysData: {
        placeholder: '',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        url: '/reception/register/getJxys', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px
        firstName: 'inputCode',
        secondName: 'name',
        queryData: 'key',
      },
      // 体检号
      patientCode: undefined,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  created() {
    this.bus.$on('handleProceedsRight', (data) => {
      for (let i = 0; i < 6; i++) {
        this.statisticsList[i].count = 0
      }
      this.info = data
      this.patientCode = data.patientData.patientcode
      this.tableList = data.examfeeitemData
      this.statisticsList[0].count = data.examfeeitemData.length
      data.examfeeitemData.forEach((el) => {
        this.statisticsList[1].count += el.price
        this.statisticsList[2].count += el.factprice
        if (el.fFeecharged == 1) {
          this.statisticsList[4].count += el.factprice
        }
        this.statisticsList[5].count += el.factprice
      })
      this.statisticsList[3].count = this.statisticsList[5].count - this.statisticsList[4].count
    })
    this.bus.$on('handleProceedsRightLoading', (loading) => {
      this.loading = loading
    })
  },
  beforeDestroy() {
    this.bus.$off('handleProceedsRight')
    this.bus.$off('handleProceedsRightLoading')
  },
  methods: {
    // 反登记
    handleReverse() {
      if (!this.patientCode) {
        this.$alert('体检者信息不存在', '提示')
        return
      }
      this.$confirm('确定要反登记？', '提示').then(() => {
        setUnRegister({
          patientCode: this.patientCode,
        }).then(() => {
          this.$modal.msgSuccess('反登记成功')
          this.bus.$emit('HandleReverseRight')
        })
      }).catch(() => {})
    },
    // 打开折扣弹窗 
    handleDiscount() {
      if (!this.patientCode) {
        this.$alert('折扣失败：收费项目不存在！', '提示')
        return
      }
      let error = ''
      this.selectTable.forEach((el) => {
        if (el.fFeecharged == 1) {
          error = '折扣失败：收费项目已收费！'
          return
        }
      })
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.$refs.discount.showDialog(this.selectTable, this.tableList)
    },
    // 执行折扣
    executeDiscount(data, responseText) {
      if (data.way == 1 && data.choose != 2) {
        this.tableList.forEach((el) => {
          if (el.examfeeitemName && el.price && el.FFeecharged != 1 && (data.choose == 4 ? true : data.choose == 3 ? el.idPayway != 5 : (el.idPayway != 5 && responseText[el.idExamfeeitem]))) {
            el.factprice = el.price * (1 - data.deRate * 0.01)
          }
        })
      } else if (data.way == 1 && data.choose == 2) {
        this.selectTable.forEach((val) => {
          this.tableList.forEach((el) => {
            if (val.idExamfeeitem == el.idExamfeeitem && el.examfeeitemName && el.price && el.fFeecharged != 1 && !(el.idPayway == 5 || !responseText[el.idExamfeeitem])) {
              el.factprice = el.price * (1 - data.deRate * 0.01)
            }
          })
        })
      } else {
        this.selectTable[0].factprice = data.truePrice
      }
    },
    // 加项医生选择
    jxysChange(value, index) {
      this.tableList[index].jxys = value.id
      this.tableList[index].name = value.name
    },
    // 收费项目选择
    onExamfeeitemChanged(data, index) {
      if (!data) {
        this.tableList[index].idExamfeeitem = ''
        this.tableList[index].examfeeitemName = ''
        if (this.tableList[index].sfjx) {
          this.tableList[index]._state = 'added'
        }
        return
      }
      //获取数据信息
      //根据行号获取相应的行
      var row = this.tableList[index]
      if (data != undefined) {
        // 判断是否与性别相符
        var itemTyepForSex = data.xb
        if (itemTyepForSex != 2 && itemTyepForSex != this.info.patientData.idSex) {
          this.$modal.alertWarning('选择的收费项目不适用该体检者，性别不匹配！', '提醒')
          this.$refs[`item${index}`].initData()
          return
        }
        var examV = this.info.patientData.idExamtype
       
        if (examV != 0 && examV != 2 && examV != 3 && data.tjlx != examV) {
          this.$modal.alertWarning('选择的收费项目不适用该体检者，体检类型不匹配！', '提醒')
          this.$refs[`item${index}`].initData()
          return
        }
        //传值
        row.idExamfeeitem = data.id
        row.examfeeitemName = data.examfeeitemName
        row.price = data.unitprice
        row.factprice = row.price
        row.count = 1
        row.idPayway = 1
        row.idKs = data.idDepart
        row.FFeechargedinttrans = data.xb
        //更新行
        this.tableList[index] = row
      }
    },
    // 完成预约
    handleSubscribe() {
      this.$modal.msg('完成预约')
    },
    // 完成登记
    handleRegister() {
      if (!this.patientCode) {
        this.$alert('体检者信息不存在', '提示')
        return
      }
      let error = ''
      let errIndex = ''
      this.tableList.forEach((el, index) => {
        if (!error && el.sfjx && !el.name) {
          error = el.examfeeitemName + '为加项,请选择加项医师'
          errIndex = index
        }
      })
      if (error) {
        this.$alert(error, '提示')
        this.$refs['jxys' + errIndex].setFocus()
        return
      }
      this.loading = true
      let info = JSON.parse(JSON.stringify(this.info))
      info.examfeeitemData = undefined
      info = {
        ...info,
        ...info.patientData,
      }
      info.patientData = undefined
      info.itemList = this.tableList
      info.itemList.forEach((el) => {
        if (el._state != 'added') {
          el._state = 'modified'
        }
      })
      handleInsert(info)
        .then(() => {
          this.$modal.msgSuccess('完成登记')
          this.bus.$emit('HandleReverseRight')
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 表格选中
    handleSelectionChange(val) {
      this.selectTable = val.map((item) => item)
    },
    // 单击某行
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
  },
}
</script>

<style lang="scss" scoped>
.table-show {
  flex: 1;
  overflow-y: auto;
}

.statistics {
  display: flex;
  justify-content: space-between;
  height: 96px;
  padding: 10px 0;

  .item {
    min-width: 130px;
    height: 100%;
    padding: 12px 12px;
    background: #f7f8fa;
    border-radius: 10px;

    .title {
      font-size: 12px;
      line-height: 17px;
      color: #858586;
      margin-bottom: 5px;
    }

    .number {
      font-weight: 600;
      font-size: 24px;
      line-height: 34px;
      color: #{'var(--theme)'} !important;
    }
  }
}

.check-pic {
  color: #0059ff;

  &:hover {
    cursor: pointer;
  }
}
</style>
<style lang="scss">
.proceeds-right .el-table--medium .el-table__cell {
  padding: 2px 0;
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
  .el-input-number.is-controls-right .el-input-number__increase,
  .el-input-number.is-controls-right .el-input-number__decrease {
    border: none;
    background-color: transparent;
  }
}
</style>
