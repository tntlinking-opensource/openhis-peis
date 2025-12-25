<!-- 检查项目设置添加  开发人：麦沃德科技半夏/清风 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" class="add-inspect" width="95%" append-to-body :destroy-on-close="true" @close="closeHandle" :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="120px" hide-required-asterisk v-loading="popLoading">
      <el-form-item label="名称" prop="examitemName">
        <el-input v-model="form.examitemName" placeholder="请输入名称" clearable style="width: 220px" @input="nameChange" />
      </el-form-item>
      <el-form-item label="输入码" prop="inputCode">
        <el-input v-model="form.inputCode" placeholder="输入名称后自动生成" readonly clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="简称" prop="examitemNameabr">
        <el-input v-model="form.examitemNameabr" placeholder="请输入简称" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择类型" style="width: 220px">
          <el-option v-for="item in typeOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="科室" prop="divisionId">
        <input-select :selectData="ksData" :initialValue="form.name" selectWidth="220" @change="ksChange"></input-select>
      </el-form-item>
      <el-form-item label="检查项目类型" prop="idExamitemtype">
        <input-select :selectData="inspectionTypeData" :initialValue="form.examitemtypeName" selectWidth="220" @change="inspectionTypeChange"></input-select>
      </el-form-item>
      <el-form-item label="男性体征上限" prop="valuemalemax">
        <el-input-number v-model="form.valuemalemax" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="男性体征下限" prop="valuemalemin">
        <el-input-number v-model="form.valuemalemin" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="女性体征上限" prop="valuefemalemax">
        <el-input-number v-model="form.valuefemalemax" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="女性体征下限" prop="valuefemalemin">
        <el-input-number v-model="form.valuefemalemin" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="自动完成" prop="flabByhand">
        <el-checkbox v-model="form.flabByhand" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="结论词（高值）" prop="idConclusionhi">
        <input-select :selectData="jlcHighData" selectWidth="220" :initialValue="form.idConclusionhiName" @change="jlcHighChange"></input-select>
      </el-form-item>
      <el-form-item label="结论词（低值）" prop="idConclusionlo">
        <input-select :selectData="jlcLowData" selectWidth="220" :initialValue="form.idConclusionloName" @change="jlcLowChange"></input-select>
      </el-form-item>
      <el-form-item label="排序" prop="disporder">
        <el-input-number v-model="form.disporder" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="名称进小结" prop="isName">
        <el-checkbox v-model="form.isName" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="男/女标示" prop="fmale">
        <el-select v-model="form.fmale" placeholder="请选择标示" style="width: 220px">
          <el-option v-for="item in forMaleOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="危急值上限" prop="valuedangerousmax">
        <el-input-number v-model="form.valuedangerousmax" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="危急值下限" prop="valuedangerousmin">
        <el-input-number v-model="form.valuedangerousmin" controls-position="right" :min="0" style="width: 220px" />
      </el-form-item>
      <el-form-item label="描述进小结" prop="isDesc">
        <el-checkbox v-model="form.isDesc" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="自由输入" prop="fentryonly">
        <el-checkbox v-model="form.fentryonly" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="结论词（阳性）" prop="idConclusionpo">
        <input-select :selectData="jlcPositiveData" selectWidth="220" :initialValue="form.idConclusionpoName" @change="jlcPositiveChange"></input-select>
      </el-form-item>
      <el-form-item label="结论词（阴性）" prop="idConclusionne">
        <input-select :selectData="jlcNegativeData" selectWidth="220" :initialValue="form.idConclusionneName" @change="jlcNegativeChange"></input-select>
      </el-form-item>
      <el-form-item label="是否公共" prop="isPublic">
        <el-checkbox v-model="form.isPublic" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="分中心" prop="fzxIds">
        <el-select v-model="form.fzxIds" placeholder="请选择标示" :disabled="isRead" style="width: 220px">
          <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="接口代码" prop="interfaceCode">
        <el-input v-model="form.interfaceCode" placeholder="请输入接口代码" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="打印名称" prop="examitemNameprn">
        <el-input v-model="form.examitemNameprn" placeholder="请输入打印名称" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="手动获取" prop="flabNowait">
        <el-checkbox v-model="form.flabNowait" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="收费项目可重复" prop="fcanDup">
        <el-checkbox v-model="form.fcanDup" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="描述进结果" prop="patientbaseinfodisporder">
        <el-checkbox v-model="form.patientbaseinfodisporder" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="检验标示重置" prop="examitemCodehm">
        <el-select v-model="form.examitemCodehm" placeholder="请选择标示" style="width: 220px" clearable>
          <el-option v-for="item in examitemOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="艾迪康代码" prop="examitemCode3">
        <!-- <input-select :selectData="adkData" :initialValue="form.adiconName" selectWidth="220" @change="adkChange"></input-select> -->
        <el-autocomplete popper-class="my-autocomplete-inspect" v-model="form.examitemCode3" :fetch-suggestions="querySearch" style="width: 220px" placeholder="请输入内容" @select="handleSelect">
          <template slot-scope="{ item }">
            <div style="display: flex">
              <div class="name">{{ item.id }}</div>
              <el-tooltip class="item" effect="dark" :content="item.text" placement="right-start">
                <div class="addr">{{ item.text }}</div>
              </el-tooltip>
            </div>
          </template>
        </el-autocomplete>
      </el-form-item>
      <el-form-item label="是否外送" prop="expressionoccudisease">
        <el-checkbox v-model="form.expressionoccudisease" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
      <el-form-item label="检查结果类型" prop="valuetype">
        <el-select v-model.trim="form.valuetype" placeholder="请选择标示" style="width: 220px" clearable>
          <el-option v-for="item in valueTypeOptions" :key="item.id" :label="item.text" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="计量单位" prop="valueunit">
        <el-input v-model.trim="form.valueunit" placeholder="请输入计量单位" clearable style="width: 220px" />
      </el-form-item>
      <el-form-item label="东软Pacs关联" prop="devicetypePositionCheckitem">
        <search-select :selectData="searchData" :initialValue="form.devicetypePositionCheckitem" selectWidth="220" :multiple="true" @change="searchChange"></search-select>
      </el-form-item>
      <el-form-item label="是否显示单位" prop="addUnit">
        <el-checkbox v-model="form.addUnit" style="width: 220px" border :true-label="1" :false-label="0" />
      </el-form-item>
    </el-form>
    <div class="add-table">
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="table-btn">
        <el-col :span="1.5">
          <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAddRow">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDeleteRow">删除 </el-button>
        </el-col>
      </el-row>
      <el-form ref="tableRules" :model="modelTableList" :rules="tableRules">
        <el-table border ref="tableData" id="tableData" row-key="id" :data="modelTableList.tableList" @selection-change="handleSelectionChange" @row-click="rowClick" style="margin: -1px; min-height: 60px; min-height: 500px" height="100%">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="顺序" min-width="130" align="center">
            <template slot-scope="scope">
              <el-form-item :prop="'tableList.' + scope.$index + '.orderindex'" :rules="tableRules.orderindex">
                <el-input-number size="mini" v-model="scope.row.orderindex" placeholder="请输入" :min="0" controls-position="right" style="width: 100%" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="体征词名称" min-width="120" align="center">
            <template slot-scope="scope">
              <el-form-item :prop="'tableList.' + scope.$index + '.tzcname'" :rules="tableRules.tzcname">
                <el-input size="mini" v-model="scope.row.tzcname" placeholder="请输入" style="width: 100%" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="是否阳性结果" prop="isPositive" min-width="120" align="center">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.isPositive" :active-value="1" :inactive-value="0" />
            </template>
          </el-table-column>
          <el-table-column label="体征详细描述" prop="bodyDetail" min-width="153" align="center">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.bodyDetail" placeholder="请输入" style="width: 100%" />
            </template>
          </el-table-column>
          <el-table-column label="体征详细描述（职业病）" prop="bodyDetailZy" min-width="180" align="center">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.bodyDetailZy" placeholder="请输入" style="width: 100%" />
            </template>
          </el-table-column>
          <el-table-column label="结论词" prop="resultId" min-width="196" align="center">
            <template slot-scope="scope">
              <input-select :selectData="jlcTableData" :hiddenClear="true" @focus="handlerClickInput(scope.row)" :initialValue="scope.row.name" selectWidth="160" :current-index="scope.row.id" @change="jlcTableChange($event, scope.$index)"></input-select>
            </template>
          </el-table-column>
          <el-table-column label="可疑疾病重症级0到9级" prop="intensiveLevel" min-width="160" align="center">
            <template slot-scope="scope">
              <el-input-number size="mini" v-model="scope.row.intensiveLevel" placeholder="请输入" :controls="false" :min="0" :max="9" style="width: 100%" />
              <!-- <el-input size="mini" v-model="scope.row.intensiveLevel" placeholder="请输入" style="width: 100%" /> -->
            </template>
          </el-table-column>
          <el-table-column label="默认" prop="isDefault" min-width="120" align="center">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.isDefault" :active-value="1" :inactive-value="0" />
            </template>
          </el-table-column>
          <el-table-column label="不进小结" prop="isInSummary" min-width="120" align="center">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.isInSummary" :active-value="1" :inactive-value="0" />
            </template>
          </el-table-column>
          <el-table-column label="互斥分组（异组互斥）" min-width="160" align="center">
            <template slot-scope="scope">
              <el-form-item :prop="'tableList.' + scope.$index + '.otherMutex'">
                <el-input size="mini" :precision="0" v-model="scope.row.otherMutex" placeholder="请输入" style="width: 100%" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="互斥分组（同组同正整数编号互斥）" prop="numMutex" min-width="240" align="center">
            <template slot-scope="scope">
              <el-input-number size="mini" :precision="0" v-model="scope.row.numMutex" placeholder="请输入" :controls="false" :min="0" :max="99" style="width: 100%" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getBasexamltem, addBasexamltem, updateBasexamltem, getFeatureListData, getBranchData, getAdiconSelectData } from '@/api/basis/inspect'
import pinyin from '@/utils/pinyin.js'
export default {
  data() {
    const valuemalemax = (rule, value, callback) => {
      if (Number(value) >= this.form.valuemalemin) {
        callback()
      } else {
        callback('输入值低于男性体征下限')
      }
    }
    const valuefemalemax = (rule, value, callback) => {
      if (Number(value) >= this.form.valuefemalemin) {
        callback()
      } else {
        callback('输入值低于女性体征下限')
      }
    }
    const valuedangerousmax = (rule, value, callback) => {
      if (Number(value) >= this.form.valuedangerousmin) {
        callback()
      } else {
        callback('输入值不能低于危急值下限')
      }
    }
    const valueunit = (rule, value, callback) => {
      if (this.form.valuetype == '1' && value == '') {
        callback('数值型单位不能为空')
      } else {
        callback()
      }
    }
    const orderindex = (rule, value, callback) => {
      if (value == '' || value == undefined) {
        callback('输入值不能为空!')
      } else {
        callback()
      }
    }
    const tzcname = (rule, value, callback) => {
      if (value == '' || value == undefined) {
        callback('体征词名称:不能为空!')
      } else {
        callback()
      }
    }
    const otherMutex = (rule, value, callback) => {
      var re = new RegExp('^[a-zA-Z\_]+$')
      if (re.test(value) && value && value.length < 3) {
        callback()
      } else if (value && re.test(value)) {
        this.$alert('互斥分组（异组互斥）:不能超过 2 个字符,请修改后重新操作!', '提示', {
          confirmButtonText: '确 认',
          type: 'warning',
        })
        callback('互斥分组（异组互斥）:不能超过 2 个字符,请修改后重新操作!')
      } else {
        this.$alert('互斥分组（异组互斥）必须输入英文', '提示', {
          confirmButtonText: '确 认',
          type: 'warning',
        })
        callback('互斥分组（异组互斥）必须输入英文')
      }
    }
    return {
      //表格结论词
      jlcTableData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/basconclusion/getConclusion', //请求连接
        bindValue: '',
        queryData: 'key',
      },
      // 输入码选择
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/basconclusion/getConclusion', //请求连接
        bindValue: '',
        queryData: 'key',
      },
      //结论词高值
      jlcHighData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/basconclusion/getConclusion', //请求连接
        bindValue: '',
        queryData: 'key',
      },
      //结论词低值
      jlcLowData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/basconclusion/getConclusion', //请求连接
        bindValue: '',
        queryData: 'key',
      },
      //结论词阳性
      jlcPositiveData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/basconclusion/getConclusion', //请求连接
        bindValue: '',
        queryData: 'key',
      },
      //结论词阴性
      jlcNegativeData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/basconclusion/getConclusion', //请求连接
        bindValue: '',
        queryData: 'key',
      },
      //艾迪康
      adkData: {
        placeholder: '请输入拼音码,进行选择',
        key: '代码', //第一列标题
        value: '项目名称', //第二列标题
        url: '/basexamltem/getAdiconSelectData', //请求连接
        bindValue: '',
        firstName: 'id',
        secondName: 'text',
      },
      // 科室选择
      ksData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/system/keshi/list', //请求连接
        bindValue: '',
        firstName: 'srm',
      },
      // 检查项目类型选择
      inspectionTypeData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '项目类型', //第二列标题
        url: '/basexamltemtype/list', //请求连接
        bindValue: '',
        secondName: 'examitemtypeName',
      },
      // 关联搜索数据
      searchData: {
        placeholder: '',
        inputTitle: '输入部位', // 搜索标题
        inputPlaceholder: '输入部位查询', // 搜索placeholder
        key: 'id', //第一列标题
        value: '科室', //第二列标题
        thirdName: '部位', //第三列标题
        fourthName: '方法', //第四列标题
        url: '/basexamltem/getPostionCheckItemData', //请求连接
        firstName: 'id', //接口返回值对应第一列的参数名
        secondName: 'checkitemnname', //接口返回值对应第二列的参数名
        thirdData: 'devicetypename', //接口返回值对应第三列的参数名
        fourthData: 'studypositionname', //接口返回值对应第四列的参数名
        params: {},
      },
      // 体检类型列表
      typeOptions: [
        {
          id: 0,
          text: '健康体检',
        },
        {
          id: 1,
          text: '职业体检',
        },
        {
          id: 2,
          text: '综合',
        },
      ],
      // 分中心
      fzxOptions: [],
      // 性别列表
      forMaleOptions: [
        {
          id: 0,
          text: '男',
        },
        {
          id: 1,
          text: '女',
        },
        {
          id: 2,
          text: '通用',
        },
      ],
      // 检验标示列表
      examitemOptions: [
        { id: '1', text: '<' },
        { id: '2', text: '>' },
        { id: '3', text: '<>' },
      ],
      // 检查结果类型
      valueTypeOptions: [
        { id: '01', text: '数值型' },
        { id: '02', text: '字符型' },
        { id: '03', text: '枚举型' },
      ],
      // 表格列表
      modelTableList: {
        tableList: [],
        basexamltemSignList: [],
      },
      // 非多个禁用
      multiple: true,
      // 选中数组
      ids: [],
      // 表单参数
      form: {},
      // 弹出层标题
      title: '',
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        examitemName: [{ required: true, message: '名称不能为空', trigger: 'change' }],
        inputCode: [{ required: true, message: '输入码不能为空', trigger: 'change' }],
        examitemNameabr: [{ required: true, message: '简称不能为空', trigger: 'change' }],
        type: [{ required: true, message: '体检类型不能为空', trigger: 'change' }],
        divisionId: [{ required: true, message: '科室不能为空', trigger: 'change' }],
        idExamitemtype: [{ required: true, message: '检查项目类型不能为空', trigger: 'change' }],
        examitemNameprn: [{ required: true, message: '打印名称不能为空', trigger: 'change' }],
        valueunit: [{ validator: valueunit, trigger: 'blur' }],
        valuemalemax: [{ validator: valuemalemax, trigger: 'blur' }],
        valuefemalemax: [{ validator: valuefemalemax, trigger: 'blur' }],
        valuedangerousmax: [{ validator: valuedangerousmax, trigger: 'blur' }],
      },
      //表格验证
      tableRules: {
        orderindex: [{ validator: orderindex, required: true, trigger: 'change' }],
        tzcname: [{ validator: tzcname, required: true, trigger: 'change' }],
        otherMutex: [{ validator: otherMutex, required: true, trigger: 'blur' }],
      },
      row: {},
      isRead: false,
      // 表格索引
      tableIndex: 0,
    }
  },
  computed: {
    isPublicWatch() {
      return this.form.isPublic
    },
  },
  watch: {
    isPublicWatch(newVal, oldVal) {
      if (newVal == '1') {
        this.isRead = true
        this.form.fzxIds = ''
      } else if (newVal == '0') {
        this.isRead = false
      }
    },
  },
  created() {
    // this.handleAddRow()
  },
  methods: {
    // 艾迪康数据
    querySearch(queryString, cb) {
      // 调用 callback 返回建议列表的数据
      getAdiconSelectData(queryString).then(({ data }) => {
        cb(data)
      })
    },
    handleSelect(item) {
      this.form.examitemCode3 = item.id
    },
    //表格结论词
    jlcTableChange($event, index) {
      // for (let i in this.modelTableList.tableList) {
      //   if (id == this.modelTableList.tableList[i].id) {
      //     this.$set(this.modelTableList.tableList[i], 'resultId', value.name)
      //     // this.$set(this.modelTableList.tableList[i], 'name', value.name)
      //   }
      // }
      this.modelTableList.tableList[index].name = $event.name
      this.modelTableList.tableList[index].resultId = $event.id
    },
    // 输入码选择
    divisionIdChange(value) {
      // this.form.divisionId = value
      // this.selectData.bindValue = value
    },
    //结论词高值
    jlcHighChange(value) {
      this.form.idConclusionhi = value.id
      this.jlcHighData.bindValue = value.name
    },
    //结论词低值
    jlcLowChange(value) {
      this.form.idConclusionlo = value.id
      this.jlcLowData.bindValue = value.name
    },
    //结论此阳性
    jlcPositiveChange(value) {
      this.form.idConclusionpo = value.id
      this.jlcPositiveData.bindValue = value.name
    },
    //结论此阴性
    jlcNegativeChange(value) {
      this.form.idConclusionne = value.id
      this.jlcNegativeData.bindValue = value.name
    },
    //艾迪康结论词
    adkChange(value) {
      this.form.examitemCode3 = value.id
      this.adkData.bindValue = value.name
    },
    // 检查项目类型选择
    inspectionTypeChange(value) {
      this.form.idExamitemtype = value.id
      this.inspectionTypeData.bindValue = value.name
    },
    // 科室选择
    ksChange(value) {
      this.form.divisionId = value.id
      this.ksData.bindValue = value.name
    },
    // 搜索选择
    searchChange(value) {
      this.form.devicetypePositionCheckitem = value
      this.searchData.bindValue = value
    },
    // 添加
    handleAdd() {
      this.popData = undefined
      this.reset()
      this.getInitalData()
      this.open = true
      this.title = '添加检查项目'
    },
    // 编辑
    async handleUpdate(row) {
      // this.reset();
      this.row = row
      this.popData = undefined
      const id = this.row.id
      let obj = { id: this.row.id, size: 999 }
      this.open = true
      this.title = '编辑'
      this.popLoading = true
      this.modelTableList.basexamltemSignList = [] //重置basexamltemSignList
      let pass = await this.getInitalData()
      if (!pass) return
      getBasexamltem(id)
        .then((response) => {
          this.popData = JSON.parse(JSON.stringify(response.data))
          this.popLoading = false
          this.form = response.data
          if (this.form.fzxIds != '') this.form.fzxIds = Number(this.form.fzxIds)
          getFeatureListData(obj).then((res) => {
            if (res.code == 200) {
              this.modelTableList.tableList = res.data.records
              let array = res.data.records
              array.forEach((el) => {
                el._state = 'modified'
                this.modelTableList.basexamltemSignList.push(el)
              })
            }
          })
        })
        .catch(() => {
          this.popLoading = false
        })
    },
    //获取初始化的数据
    getInitalData() {
      getBranchData().then((res) => {
        this.fzxOptions = res.data
      })
      return true
    },
    // 增加行
    handleAddRow() {
      var list = {
        // id: this.modelTableList.tableList.length + 1,
        tableIndex: this.tableIndex++,
        orderindex: undefined,
        tzcname: undefined,
        isPositive: 0,
        bodyDetail: undefined,
        bodyDetailZy: undefined,
        resultId: undefined,
        intensiveLevel: 0,
        isDefault: undefined,
        isInSummary: undefined,
        otherMutex: undefined,
        numMutex: undefined,
        _state: 'added',
      }
      this.modelTableList.tableList.push(list)
      this.modelTableList.basexamltemSignList.push(list)
    },
    // 删除选中
    handleDeleteRow() {
      this.ids.forEach((val) => {
        this.modelTableList.tableList.forEach((item, i) => {
          if (item.tableIndex == val) {
            this.$delete(this.modelTableList.tableList, i)
          }
        })
        this.modelTableList.basexamltemSignList.forEach((item, index) => {
          if (item.tableIndex == val) {
            this.$set(this.modelTableList.basexamltemSignList[index], '_state', 'removed')
          }
        })
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.tableIndex)
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableData.clearSelection()
      this.$refs.tableData.toggleRowSelection(row)
    },
    //关闭回调
    closeHandle() {
      this.form = {
        examitemName: undefined,
        inputCode: undefined,
        examitemNameabr: undefined,
        type: undefined,
        divisionId: undefined,
        idExamitemtype: undefined,
        valuemalemax: 0,
        valuemalemin: 0,
        valuefemalemax: 0,
        valuefemalemin: 0,
        forLabByhand: 0,
        idConclusionhi: undefined,
        idConclusionlo: undefined,
        disporder: undefined,
        isName: 0,
        forMale: 2,
        valuedangerousmax: 0,
        valuedangerousmin: 0,
        isDesc: 0,
        fEntryonly: 0,
        idConclusionpo: undefined,
        idConclusionne: undefined,
        isPublic: 1,
        fzxIds: undefined,
        interfaceCode: undefined,
        examitemNameprn: undefined,
        FLabNowait: 0,
        fCanDup: 0,
        patientbaseinfodisporder: 0,
        examitemCodehm: undefined,
        examitemCode3: undefined,
        expressionoccudisease: 0,
        valuetype: undefined,
        valueunit: undefined,
        devicetypePositionCheckitem: undefined,
        addUnit: 1,
      }
      this.ksData.bindValue = ''
      this.inspectionTypeData.bindValue = ''
      this.jlcHighData.bindValue = ''
      this.jlcLowData.bindValue = ''
      this.jlcPositiveData.bindValue = ''
      this.jlcNegativeData.bindValue = ''
      this.adkData.bindValue = ''
      this.selectData.bindValue = undefined
      // this.searchData.bindValue = undefined
      this.modelTableList.tableList = []
      this.modelTableList.basexamltemSignList = []
      this.resetForm('form')
    },
    // 表单重置
    reset() {
      if (this.popData) {
        this.form = JSON.parse(JSON.stringify(this.popData))
        this.handleUpdate(this.row)
        return
      }
      this.form = {
        examitemName: undefined,
        inputCode: undefined,
        examitemNameabr: undefined,
        type: undefined,
        divisionId: undefined,
        idExamitemtype: undefined,
        valuemalemax: 0,
        valuemalemin: 0,
        valuefemalemax: 0,
        valuefemalemin: 0,
        forLabByhand: 0,
        idConclusionhi: undefined,
        idConclusionlo: undefined,
        disporder: undefined,
        isName: 0,
        forMale: 2,
        valuedangerousmax: 0,
        valuedangerousmin: 0,
        isDesc: 0,
        fEntryonly: 0,
        idConclusionpo: undefined,
        idConclusionne: undefined,
        isPublic: 1,
        fzxIds: undefined,
        interfaceCode: undefined,
        examitemNameprn: undefined,
        FLabNowait: 0,
        fCanDup: 0,
        patientbaseinfodisporder: 0,
        examitemCodehm: undefined,
        examitemCode3: undefined,
        expressionoccudisease: 0,
        valuetype: undefined,
        valueunit: undefined,
        devicetypePositionCheckitem: undefined,
        addUnit: 1,
      }
      this.ksData.bindValue = ''
      this.inspectionTypeData.bindValue = ''
      this.jlcHighData.bindValue = ''
      this.jlcLowData.bindValue = ''
      this.jlcPositiveData.bindValue = ''
      this.jlcNegativeData.bindValue = ''
      this.adkData.bindValue = ''
      this.selectData.bindValue = undefined
      // this.searchData.bindValue = undefined
      this.modelTableList.tableList = []
      this.modelTableList.basexamltemSignList = []
      this.resetForm('form')
    },
    // 打印项目分类名称改变
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 取消按钮
    cancel() {
      this.open = false
      // this.reset();
    },
    // 提交按钮
    submitForm() {
      var hash = {}
      var data = this.modelTableList.tableList
      let error = ''
      for (var i = 0; i < data.length; i++) {
        if (!error && !data[i].orderindex) {
          error = '第' + (i + 1) + '条数据的顺序不能为空'
        } else if (!error && !data[i].tzcname) {
          error = '第' + (i + 1) + '条数据的体征词不能为空'
        }
        if (data[i].tzcname != undefined && hash[data[i].tzcname]) {
          this.$message({
            message: '存在相同的体征词名称',
            type: 'warning',
          })
          return
        }
        hash[data[i].tzcname] = true
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      this.$refs['form'].validate((valid) => {
        //表单验证
        if (valid) {
          //判断是否进入表格验证
          this.$refs['tableRules'].validate((val) => {
            if (val) {
              if (this.form.id != null) {
                let basexamltemSignList = JSON.parse(JSON.stringify(this.modelTableList.basexamltemSignList))
                basexamltemSignList.forEach((el, i) => {
                  el.isPositive = el.isPositive ? 1 : 0 //阳性
                  el.isDefault = el.isDefault ? 1 : 0 //默认
                  el.isInSummary = el.isInSummary ? 1 : 0 //不进小结
                  el.name = el.tzcname
                })
                let obj = {
                  ...this.form,
                  basexamltemSignList,
                }
                updateBasexamltem(obj).then((response) => {
                  this.$modal.msgSuccess('修改成功')
                  this.open = false
                  this.$emit('getList')
                })
              } else {
                this.modelTableList.tableList.forEach((el) => {
                  el.name = el.tzcname
                })
                let obj = {
                  ...this.form,
                  basexamltemSignList: this.modelTableList.tableList,
                }
                addBasexamltem(obj).then((response) => {
                  this.$modal.msgSuccess('新增成功')
                  this.open = false
                  this.$emit('getList')
                })
              }
            }
          })
        }
      })
    },
    handlerClickInput(row) {
      this.$refs.tableData.toggleRowSelection(row, true)
    },
  },
}
</script>
<style lang="scss">
.add-inspect {
  .el-dialog {
    min-width: 800px;
  }

  .el-form-item {
    margin-bottom: 20px;
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
      border-width: 0;
      text-align: center;
      background: transparent;

      &:focus {
        border-width: 1px;
      }
    }
  }
}
#tableData {
  .cell {
    max-height: 39px;
  }
  .el-input__inner {
    max-height: 28px;
    border-width: 1px !important;
  }
  .el-form-item--medium {
    margin-bottom: 3px;
  }
  .el-form-item__error {
    display: none;
  }
}
.my-autocomplete-inspect {
  width: auto !important;
  .name {
    width: 100px;
    margin-right: 8px;
  }
  .addr {
    width: 200px;
    overflow: hidden; /*超出部分隐藏*/
    text-overflow: ellipsis; /* 超出部分显示省略号 */
    white-space: nowrap; /*规定段落中的文本不进行换行 */
  }
}
</style>
