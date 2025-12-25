<!-- 职业体检处理意见 开发人：麦沃德科技 予安/矢北-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent v-show="showSearch">
      <el-form-item label="体检类别" prop="regimentationNote">
        <el-select v-model="queryParams.regimentationNote" placeholder="请选择" clearable>
          <el-option label="上岗前" value="0"> </el-option>
          <el-option label="在岗期间" value="1"> </el-option>
          <el-option label="离岗时" value="2"> </el-option>
          <el-option label="离岗后" value="3"> </el-option>
          <el-option label="应急" value="4"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="职业危害因素" prop="occupationDiagnosis">
        <input-select :selectData="selectData1" @change="selectChange1" :initialValue="queryParams.occupationDiagnosis"></input-select>
      </el-form-item>
      <el-form-item label="危害因素输入码" prop="inputCode">
        <el-input v-model="queryParams.inputCode" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery"></el-input>
      </el-form-item>
      <el-form-item label="检查结论" prop="occupationSummaryCode">
        <input-select :selectData="selectData2" @change="selectChange2" :initialValue="queryParams.occupationSummary"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['basis:occupation:dealOpinion:add']">添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:occupation:dealOpinion:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:occupation:dealOpinion:remove']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-download" size="mini" @click="handleDownload" v-hasPermi="['basis:occupation:dealOpinion:download']">同步 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdds" v-hasPermi="['basis:occupation:dealOpinion:adds']">批量添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-setting" size="mini" @click="handleSetting" v-hasPermi="['basis:occupation:dealOpinion:setting']">检查结论设置 </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableData" v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" @row-click="rowClick" stripe border height="100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" align="center" width="100px" type="index" />
        <el-table-column label="体检类别" align="center" prop="regimentationNote" show-overflow-tooltip width="120">
          <template slot-scope="scope">
            <span v-if="scope.row.regimentationNote == 0">上岗前</span>
            <span v-else-if="scope.row.regimentationNote == 1">在岗期间</span>
            <span v-else-if="scope.row.regimentationNote == 2">离岗时</span>
            <span v-else-if="scope.row.regimentationNote == 3">离岗后</span>
            <span v-else-if="scope.row.regimentationNote == 4">应急</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column label="禁忌疾病" align="center" prop="diagnosis" show-overflow-tooltip width="160" />
        <el-table-column label="职业危害因素" align="center" prop="mhHarmName" show-overflow-tooltip width="160" />
        <el-table-column label="输入码" align="center" prop="inputCode" show-overflow-tooltip width="160" />
        <el-table-column label="检查结论" align="center" prop="mzsOccupationSummary" show-overflow-tooltip width="160" />
        <el-table-column label="结论依据" align="center" prop="diagnosisFrom" show-overflow-tooltip width="160" />
        <el-table-column label="建议" align="center" prop="summaryText" show-overflow-tooltip width="160" />
        <el-table-column label="操作者" align="center" prop="dbUser" show-overflow-tooltip width="120" />
        <el-table-column label="危害因素种类" align="center" prop="mzchHarmClass" show-overflow-tooltip width="140" />
        <el-table-column label="疑似职业病名称" align="center" prop="occupationDiseast" show-overflow-tooltip width="160" />
        <el-table-column label="对人体的影响" align="center" prop="forPersonInfluence" show-overflow-tooltip width="160" />
        <el-table-column label="收费项目" align="center" prop="miExamfeeitemName" show-overflow-tooltip width="160" />

        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:occupation:dealOpinion:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:occupation:dealOpinion:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="540px" append-to-body :close-on-click-modal="false">
      <div style="max-height: 630px; overflow-y: auto; padding-right: 10px">
        <el-form ref="form" :model="form" :rules="rules" label-width="110px" hide-required-asterisk v-loading="diaLoading">
          <el-form-item label="体检类别" prop="regimentationNote">
            <el-select v-model="form.regimentationNote" placeholder="请选择" style="width: 374px">
              <el-option label="上岗前" value="0"> </el-option>
              <el-option label="在岗期间" value="1"> </el-option>
              <el-option label="离岗时" value="2"> </el-option>
              <el-option label="离岗后" value="3"> </el-option>
              <el-option label="应急" value="4"> </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="危害因素" prop="occupationDiagnosis">
            <input-select ref="selectDataWHYS" :selectData="selectDataWHYS" :initialValue="form.mhHarmName" @change="selectChangeWHYS" v-if="!adds"></input-select>
            <el-tooltip effect="dark" :content="jhysTips" placement="top" v-else :disabled="!jhysTips">
              <search-select ref="jhysData" :selectData="jhysData" :multiple="true" :selectWidth="374" :initialValue="form.mhHarmName" @change="jhysChange"></search-select>
            </el-tooltip>
          </el-form-item>
          <el-form-item label="危害因素代码" prop="occupationDiagnosisCode">
            <el-input v-model="form.occupationDiagnosisCode" placeholder="请输入危害因素代码" />
          </el-form-item>
          <el-form-item label="危害因素分类" prop="mzchHarmClass" v-if="!adds">
            <el-select v-model="form.healthEvaluationClass" placeholder="" style="width: 374px" disabled>
              <el-option :disabled="true" v-for="options in whys" :key="options.harId" :label="options.harmClass" :value="options.harId" />
            </el-select>
          </el-form-item>
          <el-form-item label="检查结论" prop="mzsOccupationSummary">
            <input-select ref="selectData4" :selectData="selectData4" :initialValue="form.mzsOccupationSummary" @change="selectChange4" :selectWidth="374"></input-select>
          </el-form-item>
          <el-form-item label="结论依据" prop="diagnosisFrom">
            <el-input v-model="form.diagnosisFrom" placeholder="请输入结论依据" />
          </el-form-item>
          <el-form-item label="职业禁忌证" prop="diagnosis">
            <el-input v-model="form.diagnosis" placeholder="请输入职业禁忌证" />
          </el-form-item>
          <el-form-item label="建议" prop="summaryText">
            <el-input type="textarea" v-model="form.summaryText" placeholder="请输入建议" />
          </el-form-item>
          <el-form-item label="输入码" prop="inputCode" v-if="!adds">
            <el-input v-model="form.inputCode" placeholder="选择危害因素后自动生成" readonly />
          </el-form-item>
          <el-form-item label="对人体的影响" prop="forPersonInfluence" v-if="!adds">
            <el-input type="textarea" v-model="form.forPersonInfluence" placeholder="选择危害因素后自动生成" :rows="4" resize="none" readonly />
          </el-form-item>
          <el-form-item label="可疑职业病" prop="occupationDiseast">
            <input-select ref="selectData5" :selectData="selectData5" :initialValue="form.occupationDiseastName" @change="selectChange5"></input-select>
          </el-form-item>
          <el-form-item label="收费项目" prop="chargeItem">
            <input-select ref="selectData6" :selectData="selectData6" :onlyValue="true" :initialValue="form.miExamfeeitemName" @change="selectChange6"></input-select>
          </el-form-item>
          <el-form-item label="建议进报告" prop="alwaysInReport">
            <el-checkbox v-model="form.alwaysInReport" border size="medium"></el-checkbox>
          </el-form-item>
          <el-form-item label="职业禁忌症代码" prop="zzjjzdm">
            <el-input v-model="form.zzjjzdm" placeholder="请输入职业禁忌证代码" />
          </el-form-item>
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 检查结论设置对话框 -->
    <setting-dialog ref="settingDialog"></setting-dialog>
  </div>
</template>

<script>
import pinyin from '@/utils/pinyin.js'
import settingDialog from './setting.vue'
import { listData, getInputCode, deleteData, saveData, saveOrUpdateData, selectDangerData, syncData, queryData, resultData, selectClassData } from '@/api/basis/occupation/deal_opinion.js'
export default {
  name: "Deal_opinion",
  components: {
    settingDialog,
  },
  data() {
    return {
      options: [],
      showSearch: true,
      // 加载中
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        regimentationNote: undefined,
        occupationDiagnosis: undefined,
        occupationDiagnosisCode: undefined,
        inputCode: undefined,
        occupationSummary: undefined,
        occupationDiseast: '',
      },
      // 搜索栏职业危害因素参数
      selectData1: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        url: '/sell/createcombo/getJhysData', //请求连接
        secondName: 'harmName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      //搜索栏获取结论
      selectData2: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        url: '/zyVsSummary/getJcjlData', //请求连接
        secondName: 'occupationSummary',
        bindValue: '',
      },
      // 总条数
      total: 0,
      // 表格数据
      tableData: [],

      // ***************弹窗
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 加载中
      diaLoading: false,
      //对话框获取危害因素
      selectDataWHYS: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        url: '/zyVsSummary/getWhysData', //请求连接
        secondName: 'harmName',
        bindValue: '',
      },
      //对话框获取危害因素（批量多选）
      jhysData: {
        placeholder: '请选择...',
        inputTitle: '输入码', // 搜索标题
        inputPlaceholder: '危害因素输入码', // 搜索placeholder
        key: '输入码',
        value: '危害因素名称',
        bindValue: '',
        url: '/zyVsSummary/getWhysDataPage', //请求连接
        firstName: 'harmCode', //接口返回值对应第一列的参数名
        secondName: 'harmName', //接口返回值对应第二列的参数名
      },
      // 接害因素提示文字
      jhysTips: '',
      //对话框获取结论
      selectData4: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        url: '/zyVsSummary/getJcjlData', //请求连接
        secondName: 'occupationSummary',
        bindValue: '',
      },
      // 对话框中可疑职业病参数
      selectData5: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '职业病', //第二列标题
        url: '/zyVsSummary/getZybData', //请求连接
        secondName: 'occupationDiseast',
        selectWidth: 374, //选择器宽度（选填，默认230px）
      },
      // 对话框中收费项目参数
      selectData6: {
        placeholder: '请输入输入码选择',
        value: '收费项目',
        secondName: 'examfeeitemName',
        url: '/items/getAllItemsData', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230px）
        queryData: 'key', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        regimentationNote: [{ required: true, message: '请选择体检类别', trigger: 'change' }],
        occupationDiagnosis: [{ required: true, message: '请选择危害因素', trigger: 'blur' }],
        occupationDiagnosisCode: [{ required: true, message: '请输入危害因素代码', trigger: 'blur' }],
        healthEvaluationClass: [{ required: true, message: '请输入危害分类因素', trigger: 'blur' }],
        mzsOccupationSummary: [{ required: true, message: '请选择检查结论', trigger: 'blur' }],
        diagnosisFrom: [{ required: true, message: '请输入结论依据', trigger: 'blur' }],
        diagnosis: [{ required: true, message: '请输入职业禁忌证', trigger: 'blur' }],
        summaryText: [{ required: true, message: '请输入建议', trigger: 'blur' }],
      },
      adds: false, //是否批量添加
      selection: undefined,
      //危害因素数组
      whys: [],
    }
  },
  created() {
    this.getList()
    selectClassData({ size: 9999 }).then((response) => {
      this.whys = response.data.records
    })
  },
  methods: {
    //新增或编辑危害因素的远程搜索
    remoteMethod() {
      let obj = {
        inputCode: this.form.occupationDiagnosis,
      }
      selectDangerData(obj).then((response) => {
        this.options = response.data
      })
    },
    changeMethod(value) {
      this.form.occupationDiagnosis = value.inputCode
      this.form.healthEvaluationClass = value.harmClass
      this.form.inputCode = value.inputCode
      this.form.forPersonInfluence = value.mbjbJjz
    },
    // 查询分类列表
    getList() {
      this.loading = true
      listData(this.queryParams)
        .then((response) => {
          this.tableData = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((error) => {
          this.loading = false
          console.error(error)
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
    },
    // 添加
    handleAdd() {
      this.reset()
      this.open = true
      this.adds = false
      this.title = '添加'
    },
    // 删除
    handleDelete(row) {
      const postIds = row.id || this.ids
      let that = this
      this.$modal
        .confirm('是否确认删除该数据项？', '提示')
        .then(() => {

          deleteData(postIds).then(() => {
            that.getList()
            that.$modal.msgSuccess('删除成功')
          })
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      let postIds = row.id || this.ids
      this.reset()
      this.open = true
      this.adds = false
      this.title = '编辑'
      queryData(postIds).then(({ data }) => {
        //
        data.mzchHarmClass = data.occupationDiagnosis
        data.alwaysInReport ? (data.alwaysInReport = true) : (data.alwaysInReport = false)
        this.form = data
      })
    },
    // 同步
    handleDownload() {
      let that = this
      this.$modal
        .confirm('确定要同步吗？', '提示')
        .then(() => {
          syncData().then(() => {
            that.$modal.msgSuccess('同步成功')
          })
        })
        .catch(() => { })
    },
    // 批量添加
    handleAdds() {
      this.reset()
      this.open = true
      this.adds = true
      this.title = '批量添加'
    },
    // 检查结论设置
    handleSetting() {
      this.$refs.settingDialog.dialogShow()
    },
    // 输入名称自动生成代码
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 对话框中职业危害因素返回选中的值
    // selectChange3(value) {
    //   this.form.occupationDiagnosis = value.id || undefined;
    //   if(this.form.occupationDiagnosis){
    //     this.selectClassData();
    //   }
    //   this.form.healthEvaluationClass = value.inputCode || undefined
    //   if(this.form.healthEvaluationClass){
    //     this.form.inputCode = pinyin(value.name)
    //   }else{
    //     this.form.inputCode = undefined;
    //     this.form.forPersonInfluence = undefined;
    //   }
    // },
    //根据危害因素获取其输入码
    selectClassData() {
      if (this.form.occupationDiagnosis) {
        let obj = {
          occupationDiagnosis: this.form.occupationDiagnosis,
        }
        getInputCode(obj).then((response) => {
          this.form.inputCode = response.data.harmCode
          this.form.forPersonInfluence = response.data.influence
        })
      }
    },
    // 搜索框中检查结论返回选中的值
    selectChange1(value) {
      this.queryParams.occupationDiagnosis = value.id
    },
    // 搜索框中检查结论返回选中的值
    selectChange2(value) {
      this.queryParams.occupationSummary = value.id
      this.selectData2.bindValue = value.occupationSummary
    },
    // 对话框中检查结论返回选中的值
    selectChangeWHYS(value) {
      this.selectChangeWHYS.bindValue = value.HarmName
      this.form.harmName = value.harmName
      this.form.inputCode = value.inputCode
      this.form.forPersonInfluence = value.affect
      this.form.occupationDiagnosis = value.id
      if (!this.adds) {
        this.form.healthEvaluationClass = value.harmClass
      }
    },
    // 接害因素选择
    jhysChange(value) {
      this.form.occupationDiagnosis = value.map((item) => item.id).join(',')
      this.jhysTips = value.map((item) => item.harmName).join(',')
    },
    // 对话框中检查结论返回选中的值
    selectChange4(value) {

      this.form.occupationSummary = value.id
      this.form.mzsOccupationSummary = value.occupationSummary
      this.selectData4.bindValue = value.occupationSummary
    },
    // 对话框中可疑职业病返回选中的值
    selectChange5(value) {
      this.form.occupationDiseast = value.id
    },
    // 对话框中收费项目返回选中的值
    selectChange6(value) {
      this.selectChange6.bindValue = value.examfeeitemName
      this.form.itemId = value.id
    },
    // 表单重置
    reset() {
      this.form = {
        regimentationNote: undefined,
        occupationDiagnosis: undefined,
        healthEvaluationClass: undefined,
        occupationSummary: undefined,
        mzsOccupationSummary: undefined,
        diagnosisFrom: undefined,
        diagnosis: undefined,
        summaryText: undefined,
        inputCode: undefined,
        forPersonInfluence: undefined,
        occupationDiseast: undefined,
        chargeItem: undefined,
        alwaysInReport: false,
        mhHarmName: undefined,
        jhysName: undefined,
      }
      this.jhysTips = ''
      this.$nextTick(() => {
        if (this.adds) {
          this.$refs.jhysData.initData()
        } else {
          this.$refs.selectDataWHYS.initData()
        }
        this.$refs.selectData4.initData()
        this.$refs.selectData5.initData()
        this.$refs.selectData6.initData()
      })
      this.options = undefined
      // this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection[0]
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    // 取消按钮
    cancel() {
      this.open = false
      // this.reset()
    },
    // 提交按钮
    submitForm: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.diaLoading = true
          if (!this.adds) {
            let form = JSON.parse(JSON.stringify(this.form))
            form.alwaysInReport = form.alwaysInReport ? 1 : 0
            if (this.form.id != undefined) {
              saveOrUpdateData(form)
                .then(() => {
                  this.$modal.msgSuccess('编辑成功')
                  this.getList()
                  this.diaLoading = false
                  this.open = false
                })
                .catch((error) => {
                  console.error(error)
                  this.diaLoading = false
                })
            } else {
              saveOrUpdateData(form)
                .then(() => {
                  this.$modal.msgSuccess('添加成功')
                  this.getList()
                  this.diaLoading = false
                  this.open = false
                })
                .catch((error) => {
                  console.error(error)
                  this.diaLoading = false
                })
            }
          } else {
            let form = [JSON.parse(JSON.stringify(this.form))]
            form[0].alwaysInReport = form.alwaysInReport ? 1 : 0
            saveData(form)
              .then((response) => {
                this.$modal.msgSuccess('批量添加成功')
                this.open = false
                this.diaLoading = false
                this.getList()
              })
              .catch((error) => {
                console.error(error)
                this.diaLoading = false
              })
          }
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped></style>
