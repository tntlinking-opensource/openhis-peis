<!-- 职业总检--确认复查项目和职业处理意见 麦沃德科技 开发人:清风 -->
<template>
  <div class="add-container">
    <el-dialog title="确认复查项目和职业处理意见" class="add-items" :visible.sync="open" width="750px" @close="handleClose" append-to-body :close-on-click-modal="false">
      <div style="width: 460px; height: 430px; display: inline-block; vertical-align: top">
        <div class="table-box" style="height: 100%">
          <el-table :data="tableData" v-loading="loadingData" :border="true" :stripe="true" height="100%">
            <el-table-column prop="occupationDiagnosis" width="90px" label="危害因素" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column prop="occupationSummary" width="100px" label="健康状况结论" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column prop="zyjjzdm" width="90px" label="禁忌疾病" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column prop="kyzyb" width="90px" label="可疑职业病" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column prop="summaryText" width="90px" label="处理意见" show-overflow-tooltip align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="totalData > 0" :total="totalData" :page.sync="queryParamsData.current" :limit.sync="queryParamsData.size" @pagination="goGetTreatmentData" />
      </div>
      <div style="width: 210px; height: 430px; display: inline-block">
        <div class="table-box" style="height: 100%">
          <el-table :data="tableList" v-loading="loadingList" :border="true" :stripe="true" height="100%">
            <el-table-column prop="examfeeitemName" label="复查收费项目" align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
        <pagination v-show="totalList > 0" :total="totalList" :page.sync="queryParamsList.current" :limit.sync="queryParamsList.size" @pagination="goGetRightItemByPeople" />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="chooseDoctor">确认</el-button>
        <el-button @click="cancleOutside">取消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="选择医生" class="add-items" :visible.sync="openChildDialog" width="1000px" @close="handleClose" append-to-body :close-on-click-modal="false">
      <template>
        <!-- <div class="chooseHarm">危害因素分类：<div class="chooseHarmchild"></div>需要选择其他总检医生！</div> -->
        <div class="chooseHarm" v-html="harmData.test"></div>
      </template>
      <el-form :inline="true" size="mini" style="text-align: left; margin: 16px 8px 290px" @submit.native.prevent>
        <el-form-item label="总检医生" id="form">
          <el-select ref="showDoctor" :value="showDoctor" multiple @click.native="getSelectOption" @change="change" style="width: 860px" clearable>
            <div class="select">
              <div style="width: 100%; padding: 8px 8px 16px" id="harmId">
                <span>姓名</span>
                <el-input v-model="search.username" style="display: inline-block; width: 80px; margin: 0 8px"></el-input>
                <span>危害因素分类</span>
                <el-select :value="search.harmClass" multiple @click.native="getSelectOption" @change="handleHazardChange" :popper-append-to-body="isPopperAppendToBody" style="display: inline-block; width: 420px; margin: 0 8px">
                  <div class="setoptionhead">
                    <div class="value">输入码</div>
                    <div class="label">分类名称</div>
                  </div>
                  <template v-if="isSelectOption">
                    <el-option class="option-select" v-for="item in hazardTypeData" :key="item.id" :value="item.id" :label="item.name">
                      <!-- <el-checkbox class="setCheck" ></el-checkbox> -->
                      <div class="value">{{ item.inputCode }}</div>
                      <div class="label">{{ item.name }}</div>
                    </el-option>
                  </template>
                </el-select>
                <div style="float: right">
                  <el-button type="primary" style="margin: 0 8px" @click="searchDoctor">查询</el-button>
                  <el-button @click="selectDialog">关闭</el-button>
                </div>
              </div>
              <div class="setoptionhead">
                <div class="value">姓名</div>
                <div class="label">职业危害因素分类</div>
              </div>
              <template v-if="isSelectOption">
                <el-option v-for="(item, index) in optionSelect" :disabled="item.userName == 'empty'" :key="item.userNo" :value="item.userNo" :label="item.userName">
                  <div class="option-select" @click="getCheckbox(index)">
                    <!-- <el-checkbox style="height:34px; line-height:34px;" :checked="isChecked[index]"></el-checkbox> -->
                    <div class="value">{{ item.userName }}</div>
                    <div class="label">{{ item.classnames }}</div>
                  </div>
                </el-option>
              </template>
              <pagination :total="queryParams.total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getLqryData(scope.row.id)" />
            </div>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确认</el-button>
        <el-button @click="cancleInside">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getTreatmentData, getRightItemByPeople, findAllHarmclass, getDoctorData, checkHarm } from '@/api/inspection/occupational_inspection.js'
import { createReportData } from '@/api/preview/individual_report.js'
export default {
  data() {
    return {
      isPopperAppendToBody: false,
      open: false,
      loadingData: false,
      loadingList: false,
      tableData: [],
      tableList: [],
      patientno: '',
      queryParams: {
        current: 1,
        size: 10,
        total: 0,
      },
      queryParamsData: {
        current: 10,
        size: 1,
      },
      queryParamsList: {
        current: 10,
        size: 1,
      },
      totalData: 0,
      totalList: 0,
      // 医生 
      doctorData: {
        placeholder: '请输入输入码选择',
        key: '科室码',
        value: '科室名称',
        url: '/basconclusion/findAllDepartment',
        bindValue: '', //初始值(必传)
      },
      openChildDialog: false,
      harmData: {
        test: '',
      },
      //select表
      optionSelect: [{ userNo: '' }],
      hazardTypeData: [],
      search: {
        username: '',
        harmClass: [],
      },
      showDoctor: '',
      loadingTable: false,
      hazerdId: '',
      isChecked: [],
      checkedAll: false,
      checkedHazards: [],
      isShow: 'block',
      isSelectOption: true,
    }
  },
  methods: {
    // 关闭下拉框
    selectDialog() {
      this.isShow = 'none'
      this.isSelectOption = false
      this.$refs.showDoctor.blur()
    },
    //点击打开内下拉框
    getSelectOption() {
      this.isSelectOption = true
    },
    save() {
      if (!this.showDoctor) {
        this.$alert(this.harmData.test, '提示', {
          dangerouslyUseHTMLString: true,
        })
        return
      }
      localStorage.setItem('occupationalInspectionDoc', JSON.stringify(this.showDoctor))
      this.$emit('savehealthWindow', this.showDoctor)
    },
    handleClose() {
      this.showDoctor = ''
      this.optionSelect = [{ userNo: '' }]
      this.hazardTypeData = []
    },
    openConfirmWindow(patientno) {
      this.patientno = patientno
      this.goGetTreatmentData()
      this.goGetRightItemByPeople()
      this.getFindAllHarmclass()
      this.gocheckHarm()
    },
    gocheckHarm() {
      checkHarm(this.patientno).then((res) => {
        if ((res.data.status = 'success')) {
          this.harmData.test = res.data.msg
        } else if ((res.data.status = 'review')) {
          this.harmData.test = res.data.msg
        } else {
          this.harmData.test = res.data.msg
        }
      })
    },
    closeOpenDialog(msg) {
      this.$emit('audit', false)
      this.$emit('unaudit', true)
      // this.$emit('doctors', this.showDoctor)
      this.openChildDialog = false
      this.open = false
      this.$confirm(`${msg}`, '提示', {
        type: 'success',
      })
        .then(() => {
          createReportData({
            dh: 1,
            isJy: 0,
            patientcode: this.patientno,
            showAllImage: 0,
          }).then(() => {
            this.$modal.msgSuccess('生成报告成功', '提示')
          })
          this.$emit('detailsWindow')
        })
        .catch(() => {
          this.$emit('detailsWindow')
        })
    },
    getFindAllHarmclass() {
      findAllHarmclass()
        .then((res) => {
          if (res.code == 200) {
            this.hazardTypeData = res.data.records
          }
        })
        .then(() => {
          this.searchDoctor()
        })
    },
    goGetTreatmentData() {
      var obj = {
        patientno: this.patientno,
      }
      getTreatmentData(obj).then((res) => {
        if (res.code == 200) {
          if (res.msg != '职业处理意见表没有值!') {
            this.open = true
          } else {
            this.openChildDialog = true
          }
          this.tableData = res.data
          if (res.total) {
            this.totalData = res.total
          }
        }
      })
    },
    goGetRightItemByPeople() {
      getRightItemByPeople({
        patientno: this.patientno,
      }).then((res) => {
        if (res.code == 200) {
          this.tableList = res.data
          if (res.total) {
            this.totalList = res.total
          }
        }
      })
    },
    //查询医生
    searchDoctor() {
      var obj = {
        username: this.search.username,
        harmClass: '',
      }
      if (this.search.harmClass.length >= 1) {
        for (let index in this.search.harmClass) {
          obj.harmClass = obj.harmClass + this.search.harmClass[index] + ','
        }
        obj.harmClass = obj.harmClass.substring(0, obj.harmClass.length - 1)
      }
      getDoctorData(obj).then((res) => {
        this.optionSelect = res.data.records
        this.showDoctor = localStorage.getItem('occupationalInspectionDoc') ? JSON.parse(localStorage.getItem('occupationalInspectionDoc')) : ''
        if (this.optionSelect.length == 0) {
          this.optionSelect = [
            {
              userName: 'empty',
              classnames: '暂无数据',
            },
          ]
        }
        this.queryParams.current = res.data.pages
        this.queryParams.size = res.data.size
        this.queryParams.total = res.data.total
      })
    },

    //input-select change事件
    doctorDataChange() {},
    chooseDoctor() {
      this.search.harmClass = ''
      this.openChildDialog = true
      this.getFindAllHarmclass()
    },
    //内选择框事件
    handleHazardChange($event) {
      this.search.harmClass = $event
    },
    handleSelectionChange(selection) {
      if (selection.length == 1) {
        this.showDoctor += selection[0].name
      }
    },
    //内check选中事件
    changeHazerd(id) {
      this.hazerdId = id
    },
    //外select选中事件
    change($event) {
      this.showDoctor = $event
    },
    getCheckbox(index) {
      if (!this.isChecked[index]) {
        this.isChecked[index] = true
      } else {
        this.isChecked[index] = false
      }
    },
    cancleOutside() {
      this.open = false
    },
    cancleInside() {
      this.openChildDialog = false
    },
  },
}
</script>

<style lang="scss">
.setoptionhead {
  display: flex;
  font-size: 14px;
  padding: 0 20px;

  .value {
    width: 35%;
    color: #8492a6;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .label {
    flex: 1;
    color: #8492a6;
    text-align: center;
    overflow: hidden;
    max-width: 200px;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

.option-select {
  display: flex;
  font-size: 12px;

  .value {
    width: 35%;
    color: #8492a6;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    padding: 0 8px;
  }

  .label {
    flex: 1;
    color: #8492a6;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    padding: 0 8px;
    max-width: 200px;
  }
}
.chooseHarm {
  width: 100%;
  text-align: left;
  padding: 0 8px;
  color: #333;
  .chooseHarmchild {
    display: inline;
    color: #ff2727;
  }
}
</style>
<style scoped>
#harmId /deep/ .el-input__inner {
  overflow: hidden;
  max-height: 28px;
}
/* #harmId /deep/ .el-select-dropdown.is-multiple .el-select-dropdown__item.selected::after{
    display: none;
} */
#harmId /deep/ .el-select .el-select__tags > span {
  display: flex;
  overflow: hidden;
}
</style>
