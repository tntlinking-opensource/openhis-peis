<!-- 一般检查 开发人： 麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column general-inspection" style="height: 100%; min-height: auto; padding: 0; overflow-y: auto">
    <div class="charge-item">
      <h3 style="margin-top: 0">【收费项目：一般检查 血压 脉搏】</h3>
      <el-form :model="leftData.tjreg" ref="queryForm" size="small" :inline="true" @submit.native.prevent v-show="showSearch" label-width="120px">
        <el-form-item label="是否弃检" prop="isUnchecked">
          <el-select v-model="leftData.tjreg.isUnchecked" placeholder="请选择" style="width: 110px" :disabled="mainDisabled">
            <el-option label="是" :value="1"> </el-option>
            <el-option label="否" :value="0"> </el-option>
          </el-select>
          <span class="empty-width"> </span>
        </el-form-item>
        <el-form-item label="危急值" prop="isDanger">
          <el-select v-model="leftData.tjreg.isDanger" placeholder="请选择" style="width: 110px" :disabled="mainDisabled">
            <el-option label="无" :value="0"> </el-option>
            <el-option label="低" :value="1"> </el-option>
            <el-option label="中" :value="2"> </el-option>
            <el-option label="高" :value="3"> </el-option>
          </el-select>
          <span class="empty-width"> </span>
        </el-form-item>
        <el-form-item label="收缩压(mmHg)" prop="ssy">
          <el-input-number controls-position="right" v-model="leftData.tjreg.ssy" :min="0" :max="250" :precision="0" :readonly="mainDisabled" style="width: 110px" @change="calBlood(1)" @keyup.enter.native="handleKeyEnter(1)" />
          <!-- <span class="empty-width">mmHg</span> -->
        </el-form-item>
        <el-form-item label="舒张压(mmHg)" prop="szy">
          <el-input-number ref="szy" controls-position="right" v-model="leftData.tjreg.szy" :min="0" :max="250" :precision="0" :readonly="mainDisabled" style="width: 110px" @change="calBlood(2)" @keyup.enter.native="handleKeyEnter(2)" />
          <!-- <span class="empty-width">mmHg</span> -->
        </el-form-item>
        <el-form-item label="血压结论" prop="xy">
          <el-input v-model="leftData.tjreg.xy" style="width: 110px" readonly />
          <span class="empty-width" :style="!isXY ? 'color:red;' : ''" v-html="leftData.tjreg.xyms"></span>
        </el-form-item>
        <el-form-item label="脉搏(次/分钟)" prop="mb">
          <el-input-number
            ref="mb"
            controls-position="right"
            v-model="leftData.tjreg.mb"
            :min="0"
            :max="250"
            :precision="0"
            :readonly="mainDisabled"
            style="width: 110px"
            @change="changeConclusion('脉搏:' + leftData.tjreg.mb + '次/分钟;', leftData.tjreg.mb)"
            @keyup.enter.native="handleKeyEnter(3)"
          />
          <!-- <span class="empty-width">次/分钟</span> -->
        </el-form-item>
        <el-form-item label="身高(cm)" prop="sg">
          <el-input-number ref="sg" controls-position="right" v-model="leftData.tjreg.sg" :min="0" :max="250" :precision="2" :readonly="mainDisabled" style="width: 110px" @change="calBMI(1)" @keyup.enter.native="handleKeyEnter(4)" />
          <!-- <span class="empty-width">cm</span> -->
        </el-form-item>
        <el-form-item label="体重(kg)" prop="tz">
          <el-input-number ref="tz" controls-position="right" v-model="leftData.tjreg.tz" :min="0" :max="300" :precision="2" :readonly="mainDisabled" style="width: 110px" @change="calBMI(2)" @keyup.enter.native="handleKeyEnter(5)" />
          <!-- <span class="empty-width">kg</span> -->
        </el-form-item>
        <el-form-item label="体重指数" prop="bmi">
          <el-input v-model="leftData.tjreg.bmi" style="width: 110px" readonly />
          <span class="empty-width" :style="!isBMI ? 'color:red;' : ''" v-html="leftData.tjreg.bmims"></span>
        </el-form-item>
        <el-form-item label="呼吸频率" prop="respiratoryRate">
          <el-input-number
            ref="respiratoryRate"
            controls-position="right"
            v-model="leftData.tjreg.respiratoryRate"
            :min="0"
            :max="250"
            :precision="0"
            :readonly="mainDisabled"
            style="width: 110px"
            @change="changeConclusion('呼吸频率:' + leftData.tjreg.respiratoryRate + '次/分钟;', leftData.tjreg.respiratoryRate)"
            @keyup.enter.native="handleKeyEnter(6)"
          />
          <span class="empty-width">次/分钟</span>
        </el-form-item>
        <el-form-item label="体温测量" prop="temperature">
          <el-input-number
            ref="temperature"
            controls-position="right"
            v-model="leftData.tjreg.temperature"
            :min="0"
            :max="50"
            :precision="1"
            :readonly="mainDisabled"
            style="width: 110px"
            @change="changeConclusion('体温:' + leftData.tjreg.temperature + '℃;', leftData.tjreg.temperature)"
            @keyup.enter.native="handleKeyEnter(7)"
          />
          <span class="empty-width">℃</span>
        </el-form-item>
        <el-form-item label="胸围(cm)" prop="bust">
          <el-input-number
            ref="bust"
            controls-position="right"
            v-model="leftData.tjreg.bust"
            :min="0"
            :max="300"
            :precision="0"
            :readonly="mainDisabled"
            style="width: 110px"
            @change="changeConclusion('胸围:' + leftData.tjreg.bust + 'cm;', leftData.tjreg.bust)"
            @keyup.enter.native="handleKeyEnter(8)"
          />
          <span class="empty-width">cm</span>
        </el-form-item>
        <el-form-item label="营养状况" prop="commonState">
          <el-select ref="commonState" v-model="leftData.tjreg.commonState" placeholder="请选择" style="width: 110px" clearable :disabled="mainDisabled" @change="calCommon">
            <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id"> </el-option>
          </el-select>
          <span class="empty-width"> </span>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格 -->
    <div class="table-box" style="min-height: 200px">
      <el-table v-loading="loading" ref="tableData" :data="giTableData.tableData" @selection-change="handleSelectionChange" @row-click="rowClick" stripe border height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="60px" type="index" />
        <el-table-column label="体检号" align="center" prop="patientcode" show-overflow-tooltip min-width="160" />
        <el-table-column label="姓名" align="center" prop="patientname" show-overflow-tooltip min-width="120" />
        <el-table-column label="性别" align="center" prop="idSex" show-overflow-tooltip min-width="80">
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
    <pagination v-if="giTableData.total" :total="giTableData.total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getListData" />
  </div>
</template>

<script>
import { getRankData, getCommonStateData, getSign } from '@/api/funcdept/section_list/index.js'
export default {
  props: ['leftData', 'mainDisabled', 'giTableData'],
  data() {
    return {
      // 显示筛选
      showSearch: true,
      // 血压是否正常
      // 体重指数是否正常
      // 一般检查左下表格参数
      queryParams: {
        current: 1,
        size: 10,
        ksID: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      loading: false,
      // 营养状况列表
      options: [],
      // 选中的数据
      selectData: [],
    }
  },
  created() {
    getCommonStateData().then(({ data }) => {
      this.options = data
    })
  },
  computed: {
    isXY() {
      if (this.leftData.tjreg.xyms && this.leftData.tjreg.xyms.indexOf('正常') == -1) {
        return false
      } else {
        return true
      }
    },
    isBMI() {
      if (this.leftData.tjreg.bmims && this.leftData.tjreg.bmims.indexOf('正常') == -1) {
        return false
      } else {
        return true
      }
    },
  },
  methods: {
    // 按回车切换到下处
    handleKeyEnter(num) {
      let name = ''
      switch (num) {
        case 1:
          name = 'szy'
          break
        case 2:
          name = 'mb'
          break
        case 3:
          name = 'sg'
          break
        case 4:
          name = 'tz'
          break
        case 5:
          name = 'respiratoryRate'
          break
        case 6:
          name = 'temperature'
          break
        case 7:
          name = 'bust'
          break
        case 8:
          name = 'commonState'
          break
      }
      this.$refs[name].focus()
    },
    // 计算血压结论词
    calBlood(num) {
      if (num == 1) {
        this.changeConclusion('收缩压:' + this.leftData.tjreg.ssy + 'mmHg;', this.leftData.tjreg.ssy)
      } else {
        this.changeConclusion('舒张压:' + this.leftData.tjreg.szy + 'mmHg;', this.leftData.tjreg.szy)
      }
      if (this.leftData.tjreg.ssy && this.leftData.tjreg.szy) {
        this.$emit('changeBtnState', 1)
        this.leftData.tjreg.xy = this.xyjl(this.leftData.tjreg.ssy, this.leftData.tjreg.szy)
        getSign({
          age: this.leftData.patient.age,
          idSex: this.leftData.patient.idSex,
          examValue: this.leftData.tjreg.xy,
          examItemType: 'xy',
        }).then(({ data }) => {
          if (data.tzc) {
            this.leftData.tjreg.xyms = data.tzc
            this.changeConclusion('血压结论:' + this.leftData.tjreg.xyms + ';', this.leftData.tjreg.xyms)
            this.$emit('changeJLC', data)
            this.$emit('changeBtnState', 2)
          }
        })
      }
    },
    xyjl(id_434, id_435) {
      var result
      if (id_434 <= 0 || id_435 <= 0) result = 0
      else if (id_434 >= 150 && id_435 < 90) result = 95
      else if (id_434 >= 140 && id_435 < 90) result = 85
      else if (id_434 >= 180 || id_435 >= 110) result = 75
      else if (id_434 >= 160 || id_435 >= 100) result = 65
      else if (id_434 >= 150 || id_435 >= 95) result = 55
      else if (id_434 >= 140 || id_435 >= 90) result = 45
      else if (id_434 >= 130 && id_435 >= 85) result = 35
      else if (id_434 < 90 || id_435 < 60) result = -15
      else if (id_434 < 120 && id_434 >= 90 && id_435 < 80 && id_435 >= 60) result = 15
      else result = 25
      return result
    },
    // 计算体重指数
    calBMI(num) {
      if (num == 1) {
        if (this.leftData.tjreg.sg) {
          this.changeConclusion('身高:' + this.leftData.tjreg.sg + 'cm;', this.leftData.tjreg.sg)
        }
      } else {
        if (this.leftData.tjreg.tz) {
          this.changeConclusion('体重:' + this.leftData.tjreg.tz + 'kg;', this.leftData.tjreg.tz)
        }
      }
      if (this.leftData.tjreg.sg && this.leftData.tjreg.tz) {
        this.leftData.tjreg.bmi = this.tzzs(this.leftData.tjreg.sg, this.leftData.tjreg.tz)
        this.$emit('changeBtnState', 1)
        getSign({
          age: this.leftData.patient.age,
          idSex: this.leftData.patient.idSex,
          examValue: this.leftData.tjreg.bmi,
          examItemType: 'bmi',
        }).then(({ data }) => {
          if (data.tzc) {
            this.leftData.tjreg.bmims = data.tzc
            this.changeConclusion('体重指数:' + this.leftData.tjreg.bmims + ';', this.leftData.tjreg.bmims)
            this.$emit('changeJLC', data)
            this.$emit('changeBtnState', 2)
          }
        })
      }
    },
    tzzs(ID_13, ID_12) {
      var result
      if (ID_12 > 0 && ID_13 !== null) {
        result = ((ID_12 / ID_13 / ID_13) * 10000).toFixed(2)
      } else {
        result = -1
      }
      return result
    },
    // 根据营养状况修改
    calCommon($event) {
      if (this.leftData.tjreg.commonState) {
        this.$emit('changeBtnState', 1)
        getSign({
          id: this.leftData.tjreg.commonState,
        }).then(({ data }) => {
          if (data.intensiveLevel == 1) {
            this.$emit('changeJLC', data)
          }
          this.$emit('changeBtnState', 2)
          this.changeConclusion('营养状况:' + this.$refs.commonState.selectedLabel + ';', this.$refs.commonState.selectedLabel)
          this.options.forEach((el) => {
            if (el.id == $event) {
              this.leftData.tjreg.commonState = el.name
            }
          })
        })
      }
    },
    // 修改小结
    changeConclusion(text, num) {
      if(num){
        this.$emit('changeConclusion', text, num)
      }
    },
    // 一般检查左下表格
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
    getListData() {
      this.loading = true
      getRankData(this.queryParams).then(({ data }) => {
        this.$emit('getRankData', data.records, data.total)
        this.loading = false
      })
    },
    // 左下表格多选框选中数据
    handleSelectionChange(selection) {
      this.selectData = selection.map((item) => item)
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
      if (col.type != 'selection') 
      this.$refs.tableData.clearSelection() //清空表格数据
      this.$refs.tableData.toggleRowSelection(row, true)
    },
  },
}
</script>

<style lang="scss">
.general-inspection {
  .charge-item {
    padding: 20px;

    h3 {
      margin: 5px 0 10px;
      font-weight: 600;
      font-size: 16px;
      line-height: 22px;
      color: #333333;
    }

    .el-form-item--mini.el-form-item,
    .el-form-item--small.el-form-item {
      margin-bottom: 10px;
    }
  }

  .empty-width {
    display: inline-block;
    width: 120px;
    height: auto;
    padding-left: 20px;
    white-space: nowrap;
  }

  .empty-red {
    color: #ff2727;
  }
}
</style>
<style scoped>
.general-inspection /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
.general-inspection /deep/ .el-table .el-table__cell {
  padding: 0;
}
</style>
