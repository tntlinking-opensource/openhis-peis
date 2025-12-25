<!-- 视力辨色力 开发人： 麦沃德科技 予安/清风 -->
<template>
  <div class="app-container flex-direction-column" style="height: 100%; min-height: auto; padding: 0; overflow-y: auto">
    <!-- <div class="charge-item">
      <h3 style="margin-top: 0;" v-if="leftData.sldataCheck">【{{leftData.sldataCheck[0].sfxmmc}}】</h3>
      <div v-if="leftData.sldataCheck">
        <div v-if="leftData.sldataCheck[0].fentryonly == 'shuru'">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="false" @submit.native.prevent id="setsljc"
            v-show="showSearch" label-width="130px" v-if="leftData.sldataCheck">
            <template v-for="(item,index) in leftData.sldataCheck" >
              <el-form-item :label="item.jcxmmc" prop="left" v-if="item.fentryonly == 'shuru'" :key="index">
                <el-input v-model="queryParams.visitionList[index].inputResult" :readonly="mainDisabled" placeholder="请输入"
                 style="width:60%;" clearable />
              </el-form-item>
            </template>
          </el-form>
        </div>
        <div v-else-if="leftData.sldataCheck[0].fentryonly == 'gouxuan' || leftData.sldataCheck[5].fentryonly == 'gouxuan'">
          <label class="setLabel" v-if="leftData.sldataCheck[0].fentryonly == 'gouxuan'">{{ leftData.sldataCheck[0].jcxmmc }}</label>
          <label class="setLabel" v-else-if="leftData.sldataCheck[5].fentryonly == 'gouxuan'">{{ leftData.sldataCheck[5].jcxmmc }}</label>
          <el-checkbox-group  v-model="checkBox" @change="itemChangeCheck(checkBox)"
            :style="{ 'pointer-events': mainDisabled ? 'none' : '' }">
            <div v-for="(checkedItem,checkedIndex) in leftData.sldataCheck" :key="checkedIndex" class="setCheckbox">
              <el-checkbox :label="checkedItem.sname"
                style="min-width: 150px;"></el-checkbox>
            </div>
          </el-checkbox-group>
          <el-input v-model="queryParams.jcms" :readonly="mainDisabled" placeholder="请输入" clearable type="textarea"
              rows="2" />
        </div>
      </div>
    </div> -->
    <div class="charge-item" v-for="(item, index) in leftData" :key="index">
      <h3 style="margin-top: 0" v-if="item.fentryonly != 'shuru'">【{{ item.sfxmmc }}:{{ item.jcxmmc }}】</h3>
      <el-form :model="item" size="small" :inline="true" @submit.native.prevent label-width="100px" :style="{ 'pointer-events': mainDisabled ? 'none' : '' }">
        <template v-if="item.fentryonly != 'shuru'">
          <el-form-item label="是否弃检" prop="qij">
            <el-select v-model="item.qij" placeholder="请选择" style="width: 250px" size="mini">
              <el-option label="否" :value="0"> </el-option>
              <el-option label="是" :value="1"> </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="危急值" prop="wjzjb">
            <el-select v-model="item.wjzjb" placeholder="请选择" style="width: 250px" size="mini">
              <el-option label="无" value="0"> </el-option>
              <el-option label="低" value="1"> </el-option>
              <el-option label="中" value="2"> </el-option>
              <el-option label="高" value="3"> </el-option>
            </el-select>
          </el-form-item>
        </template>
        <el-form-item :label="item.jcxmmc" v-if="item.fentryonly == 'shuru'">
          <el-input :ref="'input' + index" v-model="item.inputResult" placeholder="请输入" clearable style="width: 640px; font-size: 18px; font-weight: 600" @keyup.enter.native="handleNextInput(index)" />
        </el-form-item>
        <el-form-item prop="checkList" v-else style="width: 100%">
          <el-checkbox-group v-model="item.checkList" @change="itemChange(item, index)" style="width: calc(100% - 32px); margin-left: 32px">
            <div style="display: flex; justify-content: space-between">
              <div v-for="columnIndex in 3" :key="columnIndex">
                <div v-for="(value, index2) in item.optionList" :key="index2">
                  <el-checkbox :label="value.sname" style="display: flex; width: 220px; white-space: wrap; margin-bottom: 8px" v-if="index2 >= Math.ceil((item.optionList.length / 3) * (columnIndex - 1)) && index2 < Math.ceil((item.optionList.length / 3) * columnIndex)"> </el-checkbox>
                </div>
              </div>
            </div>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="检查描述" prop="jcms" v-if="item.fentryonly != 'shuru'">
          <el-input v-model="item.jcms" placeholder="请输入" clearable style="width: 640px; font-size: 18px; font-weight: 600" type="textarea" rows="3" :readonly="mainDisabled" />
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  props: ['leftData', 'mainDisabled', 'carbon', 'patientcode'],
  data() {
    return {
      // 显示筛选
      showSearch: true,
      // DOB值参数
      queryParams: {
        left: undefined,
        jcms: undefined,
        radio: '', //辨色力选择
        jcms: '',
        visitionList: [
          { jcxmmc: '', inputResult: '' },
          { jcxmmc: '', inputResult: '' },
          { jcxmmc: '', inputResult: '' },
          { jcxmmc: '', inputResult: '' },
        ],
      },
      radio: '', //单选
      checkBox: [], //多选
      sldataCheck: [],
      jlcData: [],
      getList: {}, //jsonData参数
      dataList: [], //data参数
      jsonData: [], //jsonData参数
      change: true,
    }
  },
  created() {
    this.bus.$off('handleAuditSljc')
    this.bus.$on('handleAuditSljc', () => {
      this.handleAudit()
    })
    this.bus.$off('conclusionsHandle')
    this.bus.$on('conclusionsHandle', (isAudit) => {
      this.conclusionsHandle(isAudit)
    })
  },
  beforeDestroy() {
    this.bus.$off('handleAuditSljc')
    this.bus.$off('conclusionsHandle')
  },
  methods: {
    // 疾病问题多选
    itemChange(item, index) {
      this.$emit('itemChangeSLJC', item, index)
    },
    // 回车切换下一个input
    handleNextInput(index) {
      let array = []
      this.leftData.forEach((el, index2) => {
        if (el.fentryonly == 'shuru') {
          array.push(index2)
        }
      })
      let current = array.indexOf(index)
      if (current != -1 && current != array.length - 1){
        this.$refs['input' + array[current + 1]][0].focus()
      }
    },

    //小结
    conclusionsHandle(isAudit) {
      let conclusions = []
      this.leftData.forEach((el) => {
        if (el.fentryonly == 'shuru') {
          if (el.inputResult) conclusions.push(`${el.jcxmmc}:${el.inputResult}`)
        } else {
          if (el.checkList.length) conclusions.push(`${el.jcxmmc}:${el.checkList.join('、')}`)
        }
      })
      this.bus.$emit('conclusionsSljc', conclusions.join(';') + '。', isAudit)
    },
    //处理queryParams
    setQueryParams() {
      if (this.leftData.sldataed) {
        for (var index in this.leftData.sldataed) {
          this.$set(this.queryParams.visitionList[index], 'inputResult', this.leftData.sldataed[index] != null ? this.leftData.sldataed[index].inputResult : null)
        }
      }
    },
    // 发送审核数据
    handleAudit() {
      let jsonData = []
      let dataList = []
      for (var i = 0; i < this.leftData.length; i++) {
        let signList = ''
        let inspectDescribe = ''
        let objJson = {
          sfqj: '',
          wjz: '0',
          wjzjb: '无',
        }
        if (this.leftData[i].fentryonly == 'shuru') {
          signList = this.leftData[i].inputResult
          inspectDescribe = this.leftData[i].jcxmmc + ':' + signList
          objJson = Object.assign(objJson, {
            sfxmId: this.leftData[i].sfxmId,
            jcxmId: this.leftData[i].tjxmId,
            tzcId: this.leftData[i].tzcId,
            jlcId: this.leftData[i].jlcId,
            jcms: inspectDescribe,
            inputResult: signList,
          })
          jsonData.push(objJson)
          dataList.push({
            inspectDescribe,
            feeId: this.leftData[i].sfxmId || '',
            depId: this.$route.meta.ksID || '',
            itemId: this.leftData[i].tjxmId || '',
            itemName: this.leftData[i].jcxmmc || '',
            signList,
            patientcode: this.patientcode,
          })
          continue
        } else {
          signList = this.leftData[i].checkList.join(';')
          inspectDescribe = this.leftData[i].jcms
          this.leftData[i].checkList.forEach((el) => {
            this.leftData[i].optionList.forEach((els) => {
              if (el == els.sname) {
                let _objJson = {
                  sfqj: '',
                  wjz: '0',
                  wjzjb: '无',
                  sfxmId: els.sfxmId,
                  jcxmId: els.tjxmId,
                  tzcId: els.tzcId,
                  jlcId: els.jlcId,
                  jcms: inspectDescribe,
                }
                jsonData.push(_objJson)
              }
            })
          })
        }
        dataList.push({
          inspectDescribe,
          depId: this.$route.meta.ksID,
          feeId: this.leftData[i].sfxmId || '',
          itemId: this.leftData[i].tjxmId || '',
          itemName: this.leftData[i].jcxmmc || '',
          signList,
          patientcode: this.patientcode,
        })
      }
      var obj = {
        data: dataList,
        jsonData,
      }
      this.$emit('handleAudit', obj)
    },
    // 疾病问题多选
    itemChangeCheck(value) {
      this.change = false
      if (value.length == 0) {
        this.jlcData = []
        this.bus.$emit('checkboxSljc', this.jlcData)
        return
      }
      this.radio = []
      var strLength = value[value.length - 1]
      if (strLength == '正常' || strLength == '未检') {
        this.checkBox = strLength == '正常' ? ['正常'] : ['未检']
      } else if (strLength == '色盲' || strLength == '色弱') {
        if (JSON.stringify(this.checkBox).includes('正常')) {
          for (var index in this.checkBox) {
            if (this.checkBox[index] == '正常') {
              this.$delete(this.checkBox, index)
            }
          }
        } else if (JSON.stringify(this.checkBox).includes('未检')) {
          for (var index in this.checkBox) {
            if (this.checkBox[index] == '未检') {
              this.$delete(this.checkBox, index)
            }
          }
        }
      }
      this.checkboxSljc()
    },
    checkboxSljc() {
      for (var j in this.checkBox) {
        for (var i in this.queryParams.visitionList) {
          if (this.checkBox[j] == this.queryParams.visitionList[i].sname) {
            if (this.checkBox.length == 2) {
              this.jlcData[j] = {
                jcxmId: this.queryParams.visitionList[i].tjxmId,
                jcxm: this.queryParams.visitionList[i].jcxmmc,
                jlcId: this.queryParams.visitionList[i].jlcId,
                jlcName: this.queryParams.visitionList[i].jlcmc,
                sfxmId: this.queryParams.visitionList[i].sfxmId,
                tjxmId: this.queryParams.visitionList[i].tjxmId,
                tzcId: this.queryParams.visitionList[i].tzcId,
                tzc: this.queryParams.visitionList[i].sname,
                state: 'addad',
              }
            } else if (this.checkBox.length == 1) {
              this.jlcData = []
              this.jlcData[0] = {
                jcxmId: this.queryParams.visitionList[i].tjxmId,
                jcxm: this.queryParams.visitionList[i].jcxmmc,
                jlcId: this.queryParams.visitionList[i].jlcId,
                jlcName: this.queryParams.visitionList[i].jlcmc,
                sfxmId: this.queryParams.visitionList[i].sfxmId,
                tjxmId: this.queryParams.visitionList[i].tjxmId,
                tzcId: this.queryParams.visitionList[i].tzcId,
                tzc: this.queryParams.visitionList[i].sname,
                state: 'addad',
              }
            }
          }
        }
      }
      this.bus.$emit('checkboxSljc', this.jlcData)
    },
    //实时监听checkBox
    watchCheckbox() {
      var args = []
      for (var i in this.leftData.jlcData) {
        if (this.leftData.jlcData[i].jcxm == '辨色力' && (this.leftData.jlcData[i].jlcId == '131' || this.leftData.jlcData[i].jlcId == '130')) {
          args.push(this.leftData.jlcData[i].jlcName)
        }
      }
      if (args.length == 1) {
        this.checkBox = args[0] == '色盲' ? ['色盲'] : ['色弱']
      } else if (args.length == 2) {
        this.checkBox = args
      } else if (this.leftData.checkBox && this.change) {
        if (this.leftData.checkBox.tzcmc == '正常') {
          this.checkBox = ['正常']
        } else if (this.leftData.checkBox.tzcmc == '未检') {
          this.checkBox = ['未检']
        } else {
          this.checkBox = []
        }
      } else {
        this.checkBox = []
      }
    },
  },
}
</script>

<style lang="scss" scoped>
h3 {
  margin: 5px 0 10px;
  font-weight: 600;
  font-size: 16px;
  line-height: 22px;
  color: #333333;
}

.charge-item {
  padding: 20px;
  padding-top: 0;
  padding-bottom: 0;
  &:first-child {
    padding-top: 20px;
  }

  .el-checkbox-group {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    margin: 16px 0;

    .setCheckbox {
      display: inline-block;
    }
  }

  .setLabel {
    display: block;
    width: 130px;
    text-align: right;
  }
}
</style>

<style lang="scss">
#setsljc {
  #setsjlckd {
    width: 100%;

    .el-form-item__content {
      width: calc(100% - 130px);
    }
  }

  .el-textarea__inner {
    min-height: 58px !important;
  }
}
</style>
