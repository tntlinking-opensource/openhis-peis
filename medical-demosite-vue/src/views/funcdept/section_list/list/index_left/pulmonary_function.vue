<!-- 肺功能 开发人： 麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column pulmonary-function" style="height: 100%; min-height: auto; padding: 0; overflow-y: auto">
    <div class="charge-item">
      <h3>【收费项目：肺功能检查】</h3>
      <el-form :model="selectOption" ref="selectOption" size="small" :inline="true" label-width="130px">
        <el-form-item label="是否弃检" prop="isUnchecked">
          <el-select v-model="selectOption.isUnchecked" placeholder="请选择" style="width: 250px" :disabled="mainDisabled" @change="selectChangePF">
            <el-option label="是" value="1"> </el-option>
            <el-option label="否" value="0"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="危急值" prop="isDanger">
          <el-select v-model="selectOption.isDanger" placeholder="请选择" style="width: 250px" :disabled="mainDisabled" @change="selectChangePF">
            <el-option label="无" value="0"> </el-option>
            <el-option label="低" value="1"> </el-option>
            <el-option label="中" value="2"> </el-option>
            <el-option label="高" value="3"> </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <div class="add-table">
      <el-table :data="tableData" @selection-change="handleSelectionChange" style="margin: -1px">
        <el-table-column label="" prop="" width="70" />
        <el-table-column label="项目名称" prop="projectName" min-width="270" align="left" style="padding-left: 20px" />
        <el-table-column label="实测值" prop="actual" min-width="120" align="left">
          <template slot-scope="scope">
            <el-input-number size="mini" v-model="scope.row.actual" :min="0" controls-position="right" :precision="2" style="width: 100%" :disabled="mainDisabled" @change="calcPercent(scope.$index, 1)" />
          </template>
        </el-table-column>
        <el-table-column label="预测值" prop="forecast" min-width="120" align="left">
          <template slot-scope="scope">
            <el-input-number size="mini" v-model="scope.row.forecast" :min="0" controls-position="right" :precision="2" style="width: 100%" :disabled="mainDisabled" @change="calcPercent(scope.$index)" />
          </template>
        </el-table-column>
        <el-table-column label="百分比" prop="percentage" min-width="120" align="left">
          <template slot-scope="scope">
            <el-input-number size="mini" v-model="scope.row.percentage" :min="0" controls-position="right" :precision="2" style="width: 100%" disabled />
          </template>
        </el-table-column>
        <el-table-column label="单位" prop="unit" min-width="120" align="left" />
      </el-table>
    </div>
    <!-- 表格 -->
    <div class="table-box" style="min-height: 150px">
      <el-table ref="tableData" v-loading="loading" :data="tableData5.tableData" @selection-change="handleSelectionChange" @row-click="rowClick" stripe border height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="60px" type="index" />
        <el-table-column label="体检号" align="center" prop="patientcode" show-overflow-tooltip min-width="160" />
        <el-table-column label="姓名" align="center" prop="patientname" show-overflow-tooltip min-width="120" />
        <el-table-column label="性别" align="center" prop="idSex" min-width="80">
          <template slot-scope="scope">
            {{ ['男', '女'][scope.row.idSex] }}
          </template>
        </el-table-column>
        <el-table-column label="年龄" align="center" prop="age" show-overflow-tooltip min-width="80" />
        <el-table-column label="体检者类型" align="center" prop="idPatientclass" show-overflow-tooltip min-width="120" />
        <el-table-column label="体检类型" align="center" prop="idExamtype" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            {{ ['健康', '职业', '综合'][scope.row.idExamtype] }}
          </template>
        </el-table-column>
        <el-table-column label="体检状态" align="center" prop="tjzt" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <span :style="scope.row.status == 1 ? 'color:green;' : 'color:red;'">{{ ['未检', '已检'][scope.row.status] || '未检' }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-if="tableData5.total" :total="tableData5.total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getListData" />
  </div>
</template>

<script>
import { getRankData } from '@/api/funcdept/section_list/index.js'
import { getLungSign } from '@/api/funcdept/section_list/pulmonary_function.js'

export default {
  props: ['leftData', 'mainDisabled', 'tableData5'],
  data() {
    return {
      // 检查参数
      queryParams: {
        current: 1,
        size: 20,
        ksID: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 选择是否弃检、危急值
      selectOption: {
        isUnchecked: '0',
        isDanger: '0',
      },
      // 表格加载中
      loading: false,
      // 中间表格数据
      tableData: [
        { projectName: '用力肺活量(FVC)', actual: undefined, forecast: undefined, percentage: undefined, unit: 'L' },
        { projectName: '1秒用力呼气容积(FEV1)', actual: undefined, forecast: undefined, percentage: undefined, unit: 'L' },
        { projectName: '1秒用力呼气容积/用力肺活量(FEV1%G)', actual: undefined, forecast: undefined, percentage: undefined, unit: '%' },
        { projectName: '最大呼气中期流速(MMEF)', actual: undefined, forecast: undefined, percentage: undefined, unit: 'L/s' },
        { projectName: '25%呼气中段流量(FEF25%)', actual: undefined, forecast: undefined, percentage: undefined, unit: 'L/s' },
        { projectName: '50%呼气中段流量(FEF50%)', actual: undefined, forecast: undefined, percentage: undefined, unit: 'L/s' },
        { projectName: '75%呼气中段流量(FEF75%)', actual: undefined, forecast: undefined, percentage: undefined, unit: 'L/s' },
      ],
      // 下方表格数据
      tableList: [],
      // 选中的数据
      selectList: [],
    }
  },
  watch: {
    leftData: {
      handler(newValue) {
        this.tableData[0].actual = newValue.fvc || undefined
        this.tableData[0].forecast = newValue.predictFvc || undefined
        this.tableData[0].percentage = newValue.percentageFvc || undefined
        this.tableData[1].actual = newValue.fev || undefined
        this.tableData[1].forecast = newValue.predictFev || undefined
        this.tableData[1].percentage = newValue.percentageFev || undefined
        this.tableData[2].actual = newValue.fevFvc || undefined
        this.tableData[2].forecast = newValue.predictFevFvc || undefined
        this.tableData[2].percentage = newValue.percentageFevFvc || undefined
        this.tableData[3].actual = newValue.mmef || undefined
        this.tableData[3].forecast = newValue.predictMmef || undefined
        this.tableData[3].percentage = newValue.percentageMmef || undefined
        this.tableData[4].actual = newValue.feffc || undefined
        this.tableData[4].forecast = newValue.predictFeffc || undefined
        this.tableData[4].percentage = newValue.percentageFeffc || undefined
        this.tableData[5].actual = newValue.feffb || undefined
        this.tableData[5].forecast = newValue.predictFeffb || undefined
        this.tableData[5].percentage = newValue.percentageFeffb || undefined
        this.tableData[6].actual = newValue.feffa || undefined
        this.tableData[6].forecast = newValue.predictFeffa || undefined
        this.tableData[6].percentage = newValue.percentageFeffa || undefined
        this.selectOption.isUnchecked = newValue.isUnchecked || '0'
        this.selectOption.isDanger = newValue.isDanger || '0'
      },
    },
    tableData: {
      deep: true,
      handler(newValue) {
        this.$emit('handleTableData5Info', newValue)
      },
    },
  },
  methods: {
    // 获取数据
    getListData() {
      this.loading = true
      getRankData(this.queryParams).then(({ data }) => {
        this.$emit('getRankData', data.records, data.total)
        this.loading = false
      })
    },
    // 计算对应的百分比数值
    calcPercent(index, isSecond) {
      if (this.tableData[index].actual && this.tableData[index].forecast) {
        this.tableData[index].percentage = ((Number(this.tableData[index].actual) / Number(this.tableData[index].forecast)) * 100).toFixed(2)
      } else {
        this.tableData[index].percentage = 0
      }
      
      this.$emit('changeLeftData', this.tableData)
      if (index == 0 || (index == 1 && isSecond)) {
        this.excuteCalc()
      }
    },
    // 执行计算
    excuteCalc(isSummary) {
      var FVC = this.tableData[0].actual
      var percentageFVC = this.tableData[0].percentage
      var FEV1 = this.tableData[1].actual
      if (FVC && FEV1) {
        var y = (Number(FEV1) * 100) / Number(FVC)
        this.tableData[2].actual = y
        if (!percentageFVC) {
          return
        }
        var x = Number(percentageFVC)
        var key
        // 24-12-2 修改范围值 去掉 = 
        if (y <= 70 && x < 80) {
          key = 'hh'
        } else if (y > 70 && x < 80) {
          key = 'js'
        } else if (y <= 70 && x > 80) {
          key = 'bs'
        } else {
          key = 'zc'
        }
        // if (y <= 70 && x <= 80) {
        //   key = 'hh'
        // } else if (y > 70 && x <= 80) {
        //   key = 'js'
        // } else if (y <= 70 && x > 80) {
        //   key = 'bs'
        // } else {
        //   key = 'zc'
        // }
        getLungSign({ key }).then(({ data }) => {
          this.$emit('changeConclusion', data.jcxm + ':' + data.tzc)
          this.$emit('changeJLC', data)
        })
      } else if (isSummary) {
        this.$modal.msgWarning('请输入FVC及FEV1实测值')
      }
    },
    // 选择是否弃检、危急值返回父组件
    selectChangePF() {
      this.$emit('selectChangePF', this.selectOption)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectList = selection.map((item) => item)
      if (selection.length == 1) {
        let patientcode = selection[0].patientcode
        this.$emit('getSectionData', patientcode, this.$route.meta.ksID)
      } else if (selection.length > 1) {
        this.$refs.tableData.clearSelection() //清空表格数据
        this.$refs.tableData.toggleRowSelection(selection.pop()) //最后一条数据
      }
    },
    // 单击某行
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
    // 肺功能左下表格
    searchTableData(date) {
      this.queryParams.ksID = this.$route.meta.ksID
      if (date) {
        this.queryParams.startTime = date[0]
        this.queryParams.endTime = date[1]
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.queryParams.current = 1
      this.loading = true
      getRankData(this.queryParams).then(({ data }) => {
        this.$emit('getRankData', data.records, data.total)
        this.loading = false
      })
    },
  },
}
</script>

<style lang="scss">
.pulmonary-function {
  h3 {
    margin: 5px 0 10px;
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
  }

  .charge-item {
    padding: 20px;
    padding-bottom: 0;

    .el-form-item--mini.el-form-item,
    .el-form-item--small.el-form-item {
      margin-bottom: 10px;
    }

    h3 {
      margin: 0 0 10px;
    }
  }

  .add-table {
    border: 1px solid #d4d6d9;

    .table-btn {
      padding: 16px 20px;
    }

    .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
      background: transparent;
    }

    .el-input__inner {
      padding: 0 8px;
      text-align: left;
      background: transparent;

      &:focus {
        border-width: 1px;
      }
    }
  }
  .el-table--medium .el-table__cell {
    padding: 4px;
  }
}
</style>
