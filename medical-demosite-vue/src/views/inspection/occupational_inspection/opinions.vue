<!-- 职业总检-查看详情-职业处理意见 麦沃德科技 开发人:清风 -->
<template>
  <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false">
    <el-form :inline="true" type="mini" ref="form" :model="search" label-width="80px">
      <el-form-item label="体检类别">
        <el-select v-model="search.regimentationNote" disabled>
          <el-option v-for="item in regimentationNote" :key="item.id" :value="item.id" :label="item.text"></el-option>
        </el-select>
        <!-- <el-input v-model="search.regimentationNote" readonly style="width:160px;"></el-input> -->
      </el-form-item>
      <el-form-item label="检查结论">
        <el-select v-model="search.occupationSummary" value-key="id" @change="selectHandle" style="width: 160px">
          <el-option v-for="item in occupationSummary" :value="item.id" :key="item.id" :label="item.occupationSummary"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="危害因素">
        <!-- <input-select :selectData="conclusionCode" :initialValue="search.occupationDiagnosisName" @change="conclusionCodeChange"></input-select> -->
        <search-select :selectData="conclusionCode" :initialValue="search.occupationDiagnosisName ? search.occupationDiagnosisName.split(',') : []" :multiple="true" selectWidth="220" @change="conclusionCodeChange"></search-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryData">查询</el-button>
      </el-form-item>
    </el-form>
    <div class="table-box" style="height: 465px">
      <el-table ref="tableList" :data="tableList" border v-loading="loading" stripe height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
        <el-table-column prop="occupationDiagnosis" label="危害因素" align="center"></el-table-column>
        <el-table-column prop="occupationSummary" label="职业状况结论" align="center"></el-table-column>
        <el-table-column prop="zyjjzdm" label="禁忌疾病" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="kyzyb" label="可疑职业病" align="center"></el-table-column>
        <el-table-column prop="summaryText" label="处理意见" align="center" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="save">保存</el-button>
      <el-button type="primary" plain @click="queryData">刷新</el-button>
      <el-button @click="cancel">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { showZySummaryService, searchTreatmentsuggestion, saveTreatment } from '@/api/inspection/occupational_inspection.js'

export default {
  data() {
    return {
      open: false,
      title: '职业处理意见',
      search: {
        regimentationNote: null, //体检类别
        occupationSummary: '', //检查结论
        id: '', //上传id
        occupationDiagnosis: '', //危害因素
        occupationDiagnosisName: '',
        patientno: '', //体检号
      },
      tableList: [],
      regimentationNote: [
        { id: 0, text: '上岗前' },
        { id: 1, text: '在岗期间' },
        { id: 2, text: '离岗时' },
        { id: 3, text: '离岗后' },
        { id: 4, text: '应急' },
      ],
      occupationSummary: [],
      conclusionCode: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        url: '/SignInInspection/getJhysData', //职业处理意见结论词查询
        bindValue: [],
        firstName: 'harmCode', //选择列1
        secondName: 'harmName', //选择列2
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      selection: [],
      ids: [], //选中的id数组
      loading: false,
      // 已添加的处理意见列表
      opinionList: [],
    }
  },
  methods: {
    selectHandle(value) {
      this.$delete(this.search, 'id')
      this.$set(this.search, 'id', value)
    },
    cancel() {
      this.search = {
        occupationDiagnosisName: '',
      }
      this.occupationSummary = []
      this.resetForm('search')
      this.open = false
    },
    // 保存
    save() {
      if (!this.selection.length) {
        this.$alert('请选择一条记录', '提示', {
          confirmButtonText: '确定',
        })
        return
      }
      // 2024-09-20 注释相同危害因素校验
      // for (let i = 0; i < this.selection.length - 1; i++) {
      //   let firstName = this.selection[i].occupationDiagnosis
      //   for (let j = i + 1; j < this.selection.length; j++) {
      //     if (firstName == this.selection[j].occupationDiagnosis) {
      //       this.$alert(`存在相同的危害因素:${firstName}`, '提示')
      //       return
      //     }
      //   }
      // }
      var obj = {
        data: this.ids,
        patientno: this.search.patientno,
      }
      saveTreatment(obj).then((res) => {
        if (res.code == 200) {
          this.open = false
          this.$emit('refreshParents', '1')
        }
      })
    },
    openOptionsWindow(obj, jhys, opinionList) {
      this.occupationSummary = []
      this.opinionList = opinionList
      var array = []
      for (var i = 0; i < obj.harms.length; i++) {
        array[i] = obj.harms[i].split(':')
      }
      this.open = true
      this.search.patientno = obj.patientno
      if (array.length && array[0][0] != '') {
        this.search.regimentationNote = array[0][1]
        this.search.occupationDiagnosisName = array[2][1]
      } else {
        this.search.occupationDiagnosisName = ''
      }
      this.search.occupationDiagnosis = jhys
      console.log(jhys);
      this.conclusionCode.bindValue = jhys.split(',')
      // this.search.occupationDiagnosis = array[2][1]
      this.regimentationNote.forEach((element) => {
        if (this.search.regimentationNote == element.text) {
          this.search.regimentationNote = element.id
        }
      })
      //获取下拉职业病检查结论
      showZySummaryService().then((res) => {
        if (res.code == 200) {
          res.data.forEach((element) => {
            this.occupationSummary = [...this.occupationSummary, element]
            this.occupationSummary.forEach((el) => {
              if (el.inputCode == obj.occupationSummary) {
                this.search.occupationSummary = el.occupationSummary
                this.search.id = el.id
              }
            })
          })
        } else {
          this.$message({
            message: res.msg,
            type: 'warning',
          })
        }
        this.queryData()
      })
    },
    //查询
    queryData() {
      this.loading = true
      // console.log(this.search.occupationDiagnosis);
      var obj = {
        occupationDiagnosis: this.search.occupationDiagnosis || null,
        occupationSummary: this.search.id || null, //occupationSummary显示id上传
        patientno: this.search.patientno || null,
        regimentationNote: this.search.regimentationNote,
      }
      searchTreatmentsuggestion(obj)
        .then((res) => {
          if (res.code == 200) {
            this.tableList = res.data
            this.loading = false
          }
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    //选中行事件
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selection = selection
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    conclusionCodeChange(value) {
      if (value.length) {
        this.search.occupationDiagnosis = value.map((item) => item.id).join(',')
        this.search.occupationDiagnosisName = value.map((item) => item.harmName).join(',')
      } else {
        this.search.occupationDiagnosis = ''
        this.search.occupationDiagnosisName = ''
      }
    },
  },
}
</script>
