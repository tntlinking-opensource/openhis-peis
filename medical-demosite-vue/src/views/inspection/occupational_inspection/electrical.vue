<!-- 健康总检-查看详情-电测听 麦沃德科技 开发人:清风 -->
<template>
  <el-dialog title="电测听" class="add-items" :visible.sync="open" width="956px" append-to-body :close-on-click-modal="false">
    <el-button size="mini" type="warning" plain icon="el-icon-search" style="margin-bottom: 8px" @click="checkPic">查看图像</el-button>
    <div style="position: relative">
      <div class="table-head">
        <div class="s1"></div>
        <div class="s2"></div>
        <div class="word1">
          <div>频率</div>
          <div style="margin-left: 8px">(HZ)</div>
        </div>
        <div class="word2">
          <div>结果</div>
          <div style="margin-left: 8px">(DB)</div>
        </div>
        <div class="word3">类型</div>
      </div>

      <table border="1" cellspacing="0" class="show-table">
        <tr>
          <th></th>
          <th>双耳</th>
          <th>500</th>
          <th>1000</th>
          <th>2000</th>
          <th>3000</th>
          <th>4000</th>
          <th>6000</th>
          <th>8000</th>
        </tr>
        <tr>
          <td rowspan="2" style="padding: 0 40px">气导</td>
          <td>左耳</td>
          <td>
            <input class="td-input" :value="tableList.airLeft500" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airLeft500c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airLeft1000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airLeft1000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airLeft2000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airLeft2000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airLeft3000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airLeft3000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airLeft4000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airLeft4000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airLeft6000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airLeft6000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airLeft8000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airLeft8000c }}</span>
          </td>
        </tr>
        <tr>
          <td>右耳</td>
          <td>
            <input class="td-input" :value="tableList.airRight500" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airRight500c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airRight1000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airRight1000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airRight2000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airRight2000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airRight3000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airRight3000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airRight4000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airRight4000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airRight6000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airRight6000c }}</span>
          </td>
          <td>
            <input class="td-input" :value="tableList.airRight8000" type="number" disabled />
            <span class="correct" v-if="correct">{{ correctData.airRight8000c }}</span>
          </td>
        </tr>
        <tr>
          <td rowspan="2">骨导</td>
          <td>左耳</td>
          <td><input class="td-input" :value="tableList.boneLeft500" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneLeft1000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneLeft2000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneLeft3000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneLeft4000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneLeft6000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneLeft8000" type="number" disabled /></td>
        </tr>
        <tr>
          <td>右耳</td>
          <td><input class="td-input" :value="tableList.boneRight500" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneRight1000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneRight2000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneRight3000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneRight4000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneRight6000" type="number" disabled /></td>
          <td><input class="td-input" :value="tableList.boneRight8000" type="number" disabled /></td>
        </tr>
      </table>
      <!-- 表单内容 -->
      <el-form :inline="true">
        <el-form-item label="(听力检测)结果评定">
          <el-input type="textarea" style="width: 740px" readonly :value="testResult" :rows="2" resize></el-input>
        </el-form-item>
        <el-form-item label="(听力检测)备注说明">
          <el-input type="textarea" style="width: 740px" readonly :value="describe" :rows="2" resize></el-input>
        </el-form-item>
      </el-form>
      <h3>【小结】</h3>
      <el-form :model="queryParams" ref="form" size="small" :inline="true" label-width="70px" label-position="left" style="padding: 10px 20px">
        <el-form-item prop="conclusions">
          <el-input class="set-textarea" v-model="form.conclusions" readonly type="textarea" :rows="2" placeholder="请输入内容" style="width: 860px; padding: 0"> </el-input>
        </el-form-item>
        <el-form-item label="检查人" prop="rummagerId">
          <!-- rummagerId -->
          <input-select :selectData="selectData" :initialValue="form.rummager" disabled @change="selectChange" :selectWidth="310"></input-select>
        </el-form-item>
        <el-form-item label="检查时间" prop="rummagerTime" style="margin-left: 90px">
          <el-date-picker v-model="form.rummagerTime" readonly type="datetime" placeholder="选择日期时间" style="width: 310px"> </el-date-picker>
        </el-form-item>
        <el-form-item label="录入人" prop="writeId">
          <el-input v-model="form.writeId" placeholder="" readonly clearable style="width: 310px" />
        </el-form-item>
        <el-form-item label="录入时间" prop="writeTime" style="margin-left: 90px">
          <el-date-picker v-model="form.writeTime" readonly type="datetime" placeholder="选择日期时间" style="width: 310px"> </el-date-picker>
        </el-form-item>
      </el-form>
    </div>
    <generate-image ref="generateImage"></generate-image>
  </el-dialog>
</template>

<script>
import { audio, eSearch, searchDct } from '@/api/inspection/occupational_inspection.js'
import { getDeviation } from '@/api/funcdept/section_list/electro_audiometry.js'
import generateImage from '@/views/funcdept/section_list/list/dialog/generate_image.vue'

export default {
  components: {
    generateImage,
  },
  data() {
    return {
      open: false,
      queryParams: {},
      //select-input组件
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/system/keshi/list', //请求连接
        bindValue: '',
        firstName: 'srm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      patientCode: '',
      testResult: '',
      describe: '',
      form: {
        conclusions: '',
        rummagerTime: '',
        writeId: '',
        writeTime: '',
      },
      ksId: '',
      tableList: [],

      // 体检者信息
      patientInfo: {},
      // 听阈偏差值
      deviation: undefined,
      // 是否显示校正值
      correct: false,
      // 校正值数据
      correctData: {},
    }
  },
  methods: {
    electricalWindow(patientCode) {
      this.open = true
      this.patientCode = patientCode
      this.getAudio()
      this.getDeviation(() => {})
    },
    // 查看图片
    checkPic() {
      this.$refs.generateImage.handleViewImage(this.tableList.airImgPath, this.tableList.boneImgPath)
    },
    // 获取听阈偏差值
    getDeviation(fn) {
      getDeviation().then((res) => {
        this.deviation = res.data
        fn()
      })
    },
    //select选中事件
    selectChange(value) {
      this.form.rummagerId = value
    },
    getAudio() {
      var obj = { patientCode: this.patientCode }
      audio(obj)
        .then((res) => {
          if (res.code == 200) {
            this.ksId = res.data.ksID
          } else {
            this.$message({
              message: res.msg,
              type: 'warning',
            })
          }
        })
        .then(() => {
          this.getsearchDct()
          //    this.getESearch();
        })
        .catch(() => {})
    },
    getESearch() {
      var autoFill = false
      if (this.patientCode.length < 12) {
        autoFill = true
      }
      var obj = {
        patientCode: this.patientCode,
        ksId: this.ksId,
        autoFill,
      }
      eSearch(obj)
        .then((res) => {
          if (res.code) {
          }
        })
        .catch((error) => {
          console.error(error)
        })
    },
    getsearchDct() {
      var autoFill = false
      if (this.patientCode.length < 12) {
        autoFill = true
      }
      var obj = {
        patientCode: this.patientCode,
        ksId: 163,
        autoFill,
      }
      searchDct(obj).then((res) => {
        if (res.code == 200) {
          this.tableList = res.data.audiometry
          this.testResult = this.tableList.testResult
          this.describe = this.tableList.describe
          this.form.conclusions = res.data.main.conclusions
          this.form.rummager = res.data.main.rummager
          this.form.rummagerTime = res.data.main.rummagerTime
          this.form.writeId = res.data.main.writeName
          this.form.writeTime = res.data.main.writeTime
          this.patientInfo = res.data.patient
          this.correct = res.data.patient.idExamtype == 0 ? false : true
          this.getCorrectAll()

          return
          if (res.data.patient.jktjzt == 1) {
            this.$alert('本体检者检查结果已被' + (res.data.patient.doctorfinalNameR == null ? '' : res.data.patient.doctorfinalNameR) + '完成健康总检，不能修改！如有未检项目也不再进行。', '提示', {
              confirmButtonText: '确认',
              type: 'warning',
            })
          } else if (res.data.patient.zytjzt == 1) {
            this.$alert('本体检者检查结果已被' + (res.data.patient.patientnameencoded == null ? '' : res.data.patient.patientnameencoded) + '完成职业总检，不能修改！如有未检项目也不再进行。', '提示', {
              confirmButtonText: '确认',
              type: 'warning',
            })
          } else if (res.data.patient.FFinallocked == 1) {
            this.$alert('本体检者检查结果已被' + (res.data.patient.idDoctorapply == null ? '' : res.data.patient.idDoctorapply) + '锁定，不能修改！如有未检项目也不再进行。', '提示', {
              confirmButtonText: '确认',
              type: 'warning',
            })
          } else if (res.data.patient.idGuidenurse == 1) {
            this.$alert('本体检者检查结果已被' + (res.data.patient.parsedassignedsuiteandfi == null ? '' : res.data.patient.parsedassignedsuiteandfi) + '锁定，不能修改！如有未检项目也不再进行。', '提示', {
              confirmButtonText: '确认',
              type: 'warning',
            })
          } else if (res.data.main && res.data.main.isAudit == '1') {
            this.$alert('该体检号已审核，不能修改！', '提示', {
              confirmButtonText: '确认',
              type: 'warning',
            })
          }
        } else {
          let str = new RegExp('error@')
          let _msg = res.msg.replace(str, '')
          this.$alert(`${_msg}`, '提示', {
            confirmButtonText: '确认',
            type: 'warning',
          })
        }
      })
    },

    // 计算所有校正值
    getCorrectAll() {
      if (!this.patientInfo || this.patientInfo.idExamtype == 0) {
        //健康体检不需要计算
        return
      }
      if (!this.deviation) {
        this.getDeviation(() => {
          this.getCorrectAll()
        })
        return
      }
      this.getCorrect({
        key: '500',
        value: this.tableList.airLeft500,
        name: 'airLeft500c',
      })
      this.getCorrect({
        key: '1k',
        value: this.tableList.airLeft1000,
        name: 'airLeft1000c',
      })
      this.getCorrect({
        key: '2k',
        value: this.tableList.airLeft2000,
        name: 'airLeft2000c',
      })
      this.getCorrect({
        key: '3k',
        value: this.tableList.airLeft3000,
        name: 'airLeft3000c',
      })
      this.getCorrect({
        key: '4k',
        value: this.tableList.airLeft4000,
        name: 'airLeft4000c',
      })
      this.getCorrect({
        key: '6k',
        value: this.tableList.airLeft6000,
        name: 'airLeft6000c',
      })
      this.getCorrect({
        key: '500',
        value: this.tableList.airRight500,
        name: 'airRight500c',
      })
      this.getCorrect({
        key: '1k',
        value: this.tableList.airRight1000,
        name: 'airRight1000c',
      })
      this.getCorrect({
        key: '2k',
        value: this.tableList.airRight2000,
        name: 'airRight2000c',
      })
      this.getCorrect({
        key: '3k',
        value: this.tableList.airRight3000,
        name: 'airRight3000c',
      })
      this.getCorrect({
        key: '4k',
        value: this.tableList.airRight4000,
        name: 'airRight4000c',
      })
      this.getCorrect({
        key: '6k',
        value: this.tableList.airRight6000,
        name: 'airRight6000c',
      })
    },
    // 获取校正值
    getCorrect(e) {
      if (e) {
        if (e.value || e.value === 0) {
          var age = Number(this.patientInfo.age) //体检者年龄
          var sex = this.patientInfo.idSex == 0 ? 'M' : 'F' //体检者性别
          var dev = this.getDev(sex + '_List_' + e.key, age)
          var correctValue = Number(e.value) - dev
          this.correctData[e.name] = correctValue
        } else {
          this.correctData[e.name] = ''
        }
      }
    },
    // 获取校正值
    getDev(key, age) {
      var dev_array = this.deviation[key].split(',')
      var result = 0
      for (var i = 0; i < dev_array.length; i++) {
        var start = Number(dev_array[i].split('-')[0])
        var end = Number(dev_array[i].split('-')[1].split(':')[0])
        if (age >= start && age <= end) {
          result = Number(dev_array[i].split(':')[1])
          break
        }
      }
      return result
    },
  },
}
</script>

<style lang="scss">
.table-head {
  overflow: hidden;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 2;
  width: 116px;
  height: 63px;
  font-size: 12px;

  .s1 {
    position: relative;
    top: 34px;
    left: 24px;
    transform: rotate(43deg);
    border-bottom: 1px solid #000;
  }

  .s2 {
    position: relative;
    top: 44px;
    left: -18px;
    width: 150%;
    transform: rotate(21deg);
    border-top: 1px solid #000;
  }

  .word1 {
    position: absolute;
    top: 3px;
    left: 66px;
  }

  .word2 {
    position: absolute;
    top: 3px;
    left: 18px;
  }

  .word3 {
    position: absolute;
    top: 38px;
    left: 8px;
  }
}

.show-table {
  white-space: nowrap;
  font-size: 14px;
  margin-bottom: 8px;

  th {
    padding: 20px 20px;
  }

  td {
    width: 95px;
    text-align: center;

    .td-input {
      width: 50px;
      height: 40px;
      margin-right: 5px;
      font-size: 16px;
      background-color: #fff;
      text-align: center;
    }
  }
}
</style>

<style scoped>
.set-textarea /deep/ .el-textarea__inner {
  min-height: 128px !important;
  padding: 8px !important;
}
.correct {
  min-width: 32px;
  font-size: 12px;
}
</style>
