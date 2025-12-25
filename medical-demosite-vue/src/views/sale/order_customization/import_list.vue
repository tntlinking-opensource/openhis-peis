<!-- 导入名单 开发人：麦沃德科技矢北、予安 -->
<template>
  <el-dialog title="导入名单" class="import-list-dialog" :visible.sync="open" width="90%" append-to-body>
    <el-form size="small" :inline="true" ref="form" :model="form">
      <el-form-item>
        <file-upload ref="uploadFile" :uploadData="uploadData" :uploadModel="1" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
      </el-form-item>
      <el-form-item>
        <el-button size="mini" type="success" icon="el-icon-upload2" @click="uploadFile">上传</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" v-hasPermi="['sale:orderCustomization:addList']" @click="addRightWindow">增加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" :disabled="multiple" @click="handleDelete" icon="el-icon-delete" v-hasPermi="['sale:orderCustomization:deleteList']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-receiving" v-hasPermi="['sale:orderCustomization:saveList']" @click="savesWindow">保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-refresh-left" @click="handleRefresh" v-hasPermi="['sale:orderCustomization:flushed']">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-download" @click="handelDownload" v-hasPermi="['sale:chargeCategory:downloadTemplate']">名单模板下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <label>分组选择：</label>
        <el-select size="mini" @change="getDetail" v-model="form.id" placeholder="">
          <el-option v-for="item in groupList" :key="item.id" :label="item.orgreservationgroupname" :value="item.id"></el-option>
        </el-select>
      </el-col>
      <el-col :span="1.5">
        <label>摸板选择：</label>
        <el-select size="mini" @change="typeChange" v-model="form.typeID" placeholder="">
          <el-option v-for="item in typeList" :key="item.id" :label="item.text" :value="item.id"></el-option>
        </el-select>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-form ref="tableRules" style="height: 100%" :model="modelTableList" :rules="tableRules">
        <el-table ref="tableList" id="tableRightList" v-loading="loadingRight" @selection-change="handleRightSelectionChange" :data="modelTableList.tableList" :border="true" :stripe="true" style="min-height: 444px" height="100%">
          <el-table-column fixed="left" type="selection" align="center"></el-table-column>
          <el-table-column fixed="left" type="index" label="序列" width="55" align="center"></el-table-column>
          <el-table-column fixed="left" width="120px" prop="groupName" label="分组名称" align="center"></el-table-column>
          <el-table-column fixed="left" width="100px" label="已生成" align="center">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.checkboxcolumn" style="pointer-events: none"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column fixed="left" width="120px" prop="patientcode" label="体检号" align="center"></el-table-column>
          <el-table-column fixed="left" width="100px" prop="patientname" label="姓名" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.patientname" @blur="blurHandle($event.target.value, scope.row)" style="width: 100%"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="pinyin" width="100px" label="拼音" align="center"></el-table-column>
          <el-table-column prop="idcardno" width="180px" label="身份证号" align="center">
            <template slot-scope="scope">
              <el-form-item :prop="'tableList.' + scope.$index + '.idcardno'" :rules="tableRules.idcardno">
                <el-input v-model="scope.row.idcardno" placeholder="请输入" style="width: 100%" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="idSex" width="100px" label="性别" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.idSex">
                <el-option v-for="item in idSex" :key="item.id" :label="item.text" :value="item.id"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="age" width="120px" label="年龄" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.age" :controls="false" style="width: 100%" :min="0"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column prop="phone" width="120px" label="手机号码" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.phone" style="width: 100%"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="birthdate" width="140px" label="出生日期" align="center">
            <template slot-scope="scope">
              <el-tooltip placement="top">
                <div slot="content">{{ scope.row.birthdate }}</div>
                <el-date-picker v-model="scope.row.birthdate" :key="scope.$index + 'create'" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期"></el-date-picker>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column width="120px" prop="idMarriage" label="婚姻" align="center">
            <template slot-scope="scope">
              <el-form-item :prop="'tableList.' + scope.$index + '.idMarriage'">
                <el-select v-model="scope.row.idMarriage">
                  <el-option v-for="item in idMarriage" :key="item.id" :label="item.text" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="idNation" width="120px" label="民族" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.idNation">
                <el-option v-for="item in idNation" :key="item.id" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="cultural" width="120px" label="文化程度" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.cultural">
                <el-option v-for="item in cultural" :key="item.id" :label="item.text" :value="item.id"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="orgDepart" width="120px" label="部门" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orgDepart" style="width: 100%"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="workno" width="120px" label="工号" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.workno" style="width: 100%"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="trades" width="120px" label="工种" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.trades" style="width: 100%"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="workDate" width="140px" label="参加工作时间" align="center">
            <template slot-scope="scope">
              <el-tooltip placement="top">
                <div slot="content">{{ scope.row.workDate }}</div>
                <el-date-picker v-model="scope.row.workDate" style="width: 100%" :key="scope.$index + 'work'" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期"></el-date-picker>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column prop="zgl" width="120px" label="总工龄(月)" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.zgl" :controls="false" style="width: 100%" :min="0"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column prop="harmDate" width="140px" label="从事工种时间" align="center">
            <template slot-scope="scope">
              <el-tooltip placement="top">
                <div slot="content">{{ scope.row.harmDate }}</div>
                <el-date-picker v-model="scope.row.harmDate" :key="scope.$index + 'harm'" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期"></el-date-picker>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column prop="jhgl" width="120px" label="接害工龄(月)" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.jhgl" :controls="false" style="width: 100%" :min="0"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column prop="medicaltype" width="120px" label="体检类别" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.medicaltype">
                <el-option v-for="item in medicaltype" :key="item.id" :value="item.id" :label="item.text"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="xzfzx" width="120px" label="已下载分中心" align="center"></el-table-column>
          <el-table-column prop="note" width="120px" label="备注" align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.note" ref="notes"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="wechatCode" width="120px" label="体检码" align="center"></el-table-column>
          <el-table-column prop="isWechatNoticed" width="120px" label="已通知" align="center">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.isWechatNoticed" style="pointer-events: none"></el-checkbox>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </div>
  </el-dialog>
</template>
<script>
import IDValidator from '@/utils/IDValidator.js'
import { getImportData, getGroupDetail, editOrder, saveOrUpdatePatientBcOrder, getNationDataOrder, removeAll, getMarriageDataOrder } from '@/api/sale/order_customization.js'
import { marriageType } from '@/utils/dataList.js'
import pinyin from '@/utils/pinyin.js'
import GB2260 from '@/utils/GB2260.js'
export default {
  data() {
    let idcardno = (rule, value, callback) => {
      if (value) {
        var Validator = new IDValidator(GB2260)
        if (!Validator.isValid(value)) {
          callback(new Error('身份证号输入不合法'))
        } else {
          callback()
        }
      } else {
        callback(new Error('证件号不能为空'))
      }
    }
    return {
      // 打开对话框
      open: false,
      // 表单数据
      form: {
        id: undefined,
        typeID: undefined,
      },
      iId: undefined,
      // 遮罩层
      loading: false,
      modelTableList: {
        tableList: [],
      },
      open: false,
      form: {
        ddh: '',
        idOrg: '',
        idOrgclass: '',
        idSalesperson: '',
        fFinished: '',
        datereservation: '',
        planenddate: '',
        countexaminee: '',
        phone: '',
        tjm: '',
        address: '',
        qtxz: '',
      },
      tableData: [],
      modelTableList: {
        tableList: [],
      },
      idsLeft: [],
      singleLeft: true,
      multipleLeft: true,
      idsRight: [],

      // 显示搜索条件
      showSearch: true,
      loadingLeft: false,
      loadingRight: false,
      // 查询参数
      total: 30,

      queryParams: {
        current: 1,
        size: 10,
      },
      //体检套餐 input-select
      mealData: {
        placeholder: '请输入输入码选择',
        key: '套餐名称简称', //第一列标题
        value: '体检类型', //第二列标题
        url: '/sell/itemListOnline/getAllTcForOrder', //请求连接
        bindValue: '',
        firstName: 'tjtcmc',
        secondName: 'tjlx',
      },
      //付款方式 input-select
      fzxArray: [], //分中心
      zffsArray: [],
      id: undefined,
      idOrgclass: [
        {
          id: '0',
          text: '普通客户',
        },
        {
          id: '1',
          text: 'vip客户',
        },
        {
          id: '2',
          text: '流失客户',
        },
      ],
      idExamtype: [
        {
          id: '1',
          text: '职业',
        },
        {
          id: '2',
          text: '综合',
        },
        {
          id: '0',
          text: '健康',
        },
      ],
      idPayway: [], //付款方式
      griddata: [],
      selection: [],
      rowId: undefined,
      idSex: [
        {
          id: 0,
          text: '男',
        },
        {
          id: 1,
          text: '女',
        },
      ],
      idMarriage: [],
      idNation: undefined,
      cultural: [
        {
          id: 0,
          text: '小学',
        },
        {
          id: 1,
          text: '初中',
        },
        {
          id: 2,
          text: '技校',
        },
        {
          id: 3,
          text: '职高',
        },
        {
          id: 4,
          text: '高中',
        },
        {
          id: 5,
          text: '中专',
        },
        {
          id: 6,
          text: '大专',
        },
        {
          id: 7,
          text: '大学',
        },
        {
          id: 8,
          text: '研究生以上',
        },
      ],
      medicaltype: [
        { id: '0', text: '上岗前' },
        { id: '1', text: '在岗期间' },
        { id: '2', text: '离岗时' },
        { id: '3', text: '离岗后' },
        { id: '4', text: '应急' },
      ],
      griddataRight: [],
      deleteWarningMsg: false,
      cleanUp: '',
      rightSelection: [],
      //表单验证--身份证非空验证
      tableRules: {
        idcardno: [{ validator: idcardno, required: true, trigger: 'blur' }],
      },
      //导入模板
      importWindowDialog: false,
      modelTypeOptions: [
        { id: 4, text: '职业模板' },
        { id: 3, text: '健康模板' },
      ],
      dialogForm: {
        id: '',
        modelType: 4,
      },
      formLoading: false,
      // 上传组件参数
      uploadData: {
        url: '/reception/order/importPatientBatch', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1, //文件上传个数限制
        fileType: ['xls', 'xlsx'], //文件类型
        data: {}, //上传时附带的额外参数
      },

      //新增右边点击行
      rightRow: [],
      idOrgRw: undefined,

      //表格数据
      tableList: [],
      // 非多个禁用
      multiple: true,
      saveData: {
        formData: {},
        gridData: [],
        id: undefined,
      },
      groupName: '',
      // 上传组件参数
      uploadData: {
        url: '/reception/order/importPatientBatch', //文件上传地址
        fileType: ['xls', 'xlsx'], //文件类型
        data: {
          id: null,
          modelType: 3,
        }, //上传时附带的额外参数
      },
      typeList: [
        { text: '线下职业', id: 0 },
        { text: '线下健康', id: 1 },
        { text: '人员名单导入', id: 2 },
        { text: '线上健康', id: 3 },
        { text: '线上职业', id: 4 },
      ],
      queryData: {
        idOrgreservationgroup: undefined,
      },
      // 婚姻列表
      marriageList: marriageType(),
      // 列表索引
      tableIndex: 0,
      // 删除时保存的临时数据
      tempList: [],
      selection: [],
      // 任务分组列表
      groupList: [],
    }
  },
  created() {
    this.goGetNationDataOrder()
    this.goGetMarriageDataOrder()
  },
  methods: {
    getDetail(val) {
      let name
      for (var i in this.groupList) {
        if (this.groupList[i].id == val) this.uploadData.data.id = this.groupList[i].idOrgreservation
        name = this.groupList[i].orgreservationgroupname
      }
      this.saveData.id = this.form.id
      this.groupName = name
      //更改保存名单的id

      //赋值查询的id
      this.queryData.idOrgreservationgroup = this.form.id
      this.loadingRight = true
      getGroupDetail(this.queryData).then((response) => {
        if (response.data.records.length == 0) {
          this.modelTableList.tableList = []
        } else {
          this.modelTableList.tableList = response.data.records
          for (var i in this.modelTableList.tableList) {
            this.modelTableList.tableList[i].groupName = name
            this.$set(this.modelTableList.tableList[i], 'pinyin', pinyin(this.modelTableList.tableList[i].patientname))
          }
          let array = JSON.parse(JSON.stringify(response.data.records))
          this.griddataRight = array.map((el) => {
            if (el.id) {
              el.checkboxcolumn = true
            }
            el.pinyin = pinyin(el.patientname)
            return el
          })
        }
        this.loadingRight = false
      })
    },
    getRightList() {
      this.$refs.tableRules.clearValidate()
      this.loadingRight = true
      getGroupDetail(this.queryData).then((response) => {
        if (response.data.records.length == 0) {
          this.modelTableList.tableList = []
        } else {
          this.modelTableList.tableList = response.data.records
          for (var i in this.modelTableList.tableList) {
            this.modelTableList.tableList[i].groupName = this.groupName
            this.$set(this.modelTableList.tableList[i], 'pinyin', pinyin(this.modelTableList.tableList[i].patientname))
          }
          let array = JSON.parse(JSON.stringify(response.data.records))
          this.griddataRight = array.map((el) => {
            if (el.id) {
              el.checkboxcolumn = true
            }
            el.pinyin = pinyin(el.patientname)
            return el
          })
        }
        this.loadingRight = false
      })
    },
    //获取右边数据
    //右表失去焦点获取拼音
    blurHandle(value, row) {
      let pinyinStr = ''
      for (var index in this.modelTableList.tableList) {
        if (row.id == undefined && this.modelTableList.tableList[index].sort == row.sort) {
          pinyinStr = pinyin(value)
          this.$delete(this.modelTableList.tableList[index], 'pinyin')
          this.$set(this.modelTableList.tableList[index], 'pinyin', pinyinStr)
        } else if (this.modelTableList.tableList[index].sort == row.sort && row.id != undefined) {
          pinyinStr = pinyin(value)
          this.$delete(this.modelTableList.tableList[index], 'pinyin')
          this.$set(this.modelTableList.tableList[index], 'pinyin', pinyinStr)
        }
      }
    },
    //获取民族数据
    goGetNationDataOrder() {
      getNationDataOrder().then((res) => {
        if (res.code == 200) {
          this.idNation = res.data
        }
      })
    },
    // 获取列表
    getList(id) {
      this.modelTableList.tableList = []
      // this.loading = true
      // getImportData({ id })
      //   .then(({ data }) => {
      //     this.tempList = JSON.parse(JSON.stringify(data))
      //     data.forEach((el) => {
      //       el.tableIndex = this.tableIndex++
      //       el.state = 'modified'

      //     })

      //     this.modelTableList.tableList = data
      //     for (var i in this.modelTableList.tableList) {
      //       this.$set(this.modelTableList.tableList[i], "pinyin", pinyin(this.modelTableList.tableList[i].patientname));
      //
      //     }
      //     this.loading = false
      //   })
      //   .catch((error) => {
      //     this.loading = false
      //
      //   })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    //打开按钮
    handleShow(id, groupList) {
      this.iId = id
      this.groupList = groupList
      this.open = true
      editOrder({ id: this.iId }).then((res) => {
        if (res.code == 200) {
          if (res.data.peisorgreservation) {
            this.saveData.formData = res.data.peisorgreservation
          } else {
            this.saveData.formData = res.data
          }
          this.saveData.formData.tjm = res.data.tjm
          this.saveData.formData.fFinished = res.data.fFinished || res.data.FFinished
          this.saveData.formData.saleName = res.data.saleName
          this.saveData.formData.ordernum = res.data.ordernum
        }
        this.handleRefresh()
      })
    },
    typeChange(val) {
      this.uploadData.data.modelType = val
    },

    //右表新增列
    addRightWindow() {
      if (!this.form.id) {
        this.$message({
          message: '请先选择分组信息',
          type: 'warning',
        })
        return
      }
      if (this.form.fFinished == '1') {
        this.$message({
          message: '订单已结束，不能添加人员。',
          type: 'warning',
        })
        return
      }

      let sort = 0
      if (this.griddataRight.length > 0) sort = this.griddataRight.length + 1
      let obj = { idSex: 0, state: 'added', groupName: this.groupName, age: null, jhgl: null, zgl: null, idMarriage: undefined, sort }
      this.griddataRight.push(obj)
      this.modelTableList.tableList.push(obj)
    },
    // 保存
    handleSave() {
      let error = ''
      this.tableList.forEach((el, index) => {
        if (!el.id) {
          if (!error && !el.patientname) {
            error = '第' + (index + 1) + '行姓名不能为空'
          } else if (!error && !el.idSex) {
            error = '第' + (index + 1) + '行性别不能为空'
          } else if (!error && !el.age) {
            error = '第' + (index + 1) + '行年龄不能为空'
          } else if (!error && !el.phone) {
            error = '第' + (index + 1) + '行手机号不能为空'
          } else if (!error && !el.idcardno) {
            error = '第' + (index + 1) + '行身份证号不能为空'
          }
        }
      })
      if (error) {
        this.$modal.msgWarning(error)
        return
      }
      var data = this.saveData
      data.gridData = []

      for (var i in this.tableList) {
        if (this.tableList[i].state == 'added') {
          data.gridData.push(this.tableList[i])
        }
      }
      // let coGridDataDtos = JSON.parse(JSON.stringify(this.tableList))
      // let ids = coGridDataDtos.map((item) => item.id)
      // this.tempList.forEach((el) => {
      //   if (!ids.includes(el.id)) {
      //     el.state = 'removed'
      //     coGridDataDtos.push(el)
      //     this.saveData.gridData.push(el)
      //   }
      // })

      this.loading = true
      saveOrUpdatePatientBcOrder(data)
        .then(() => {
          this.$modal.msgSuccess('保存成功')
          this.loading = false
          this.handleRefresh()
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    //婚姻下拉列表
    goGetMarriageDataOrder() {
      getMarriageDataOrder().then((res) => {
        if (res.code == 200) {
          this.idMarriage = []
          let id = Object.keys(res.data)
          let text = Object.values(res.data)
          for (var i = 0; i < id.length; i++) {
            let objs = {}
            objs.id = Number(id[i])
            objs.text = text[i]
            this.idMarriage.push(objs)
          }
        }
      })
    },
    //右表保存
    savesWindow() {
      // 判断是否编辑过
      for (let i = 0; i < this.modelTableList.tableList.length; i++) {
        for (let j = 0; j < this.griddataRight.length; j++) {
          if (this.modelTableList.tableList[i].id == this.griddataRight[j].id) {
            if (JSON.stringify(this.modelTableList.tableList[i]) != JSON.stringify(this.griddataRight[j])) {
              this.modelTableList.tableList[i].state = 'modified'
            }
          }
        }
      }
      var allData = this.modelTableList.tableList

      var hash = {}
      var sex = false
      var hasRepeated = false
      var repeatRows = []
      for (var z = 0; z < allData.length; z++) {
        var row = allData[z]
        // 判断人员重复录入
        var compare = ''
        if (row.idcardno != null) {
          compare += row.idcardno + '&'
        } else {
          compare = row.idSex + '&' + row.age + '&' + (row.phone ? row.phone : null) + '&' + (row.orgDepart ? row.orgDepart : null) + '&' + (row.trades ? row.trades : null) + '&' + (row.patientname ? row.patientname : null)
        }

        var s = this.getIdCard(allData[z].idcardno, 2)
        if (s == '男') {
          this.modelTableList.tableList[z].idSex = 0
        } else if (s == '女') {
          this.modelTableList.tableList[z].idSex = 1
        }

        if (hash[compare] && (row.patientcode == null || row.patientcode == '')) {
          if (row.idcardno != null) {
            this.modelTableList.tableList.forEach((r, i) => {
              if (r.idcardno == row.idcardno) {
                r.cla = 'red-row'
                repeatRows.push(row)
                this.modelTableList.tableList[i] = r
              }
            })
          } else {
            this.modelTableList.tableList.forEach((r, i) => {
              if (r.patientname == row.patientname) {
                r.cla = 'red-row'
                repeatRows.push(row)
                this.modelTableList.tableList[i] = r
              }
            })
          }
          hasRepeated = true
        }
        hash[compare] = true
      }
      if ((sex, name)) {
        this.$message.error('存在人员信息性别与身份证不符')
        return
      }
      if (hasRepeated) {
        this.$alert('存在信息相同的人员！')
        return
      }
      let data = []
      data = this.modelTableList.tableList.filter((row) => {
        if (row.state == 'modified' || !row.id) {
          return true
        }
      })
      if (data.length == 0) {
        this.$alert('没有需要保存的体检者！')
        return
      }
      if (data.length == 0) {
        this.$alert('体检者信息为空，不能保存！')
        return
      }
      for (var i = 0, l = data.length; i < l; i++) {
        //遍历data
        var row = data[i]
        // if(row.id == ""||typeof(row.id)== "undefined"){

        //     row.state="added";
        //     // this.modelTableList.tableList[i] = Object.assign(this.modelTableList.tableList[i], row);
        //     this.$set(this.modelTableList.tableList[i],"state","added")
        // }
        if (!row.workDate) {
          row.workDate = new Date().getTime()
        }
        if (!row.harmDate) {
          row.harmDate = new Date().getTime()
        }
      }
      this.$refs['tableRules'].validate((val) => {
        if (val) {
          let _data = []
          _data = this.modelTableList.tableList.filter((row) => {
            if (typeof row.state != 'undefined') {
              return true
            }
          })
          let obj = {
            griddata: _data,
            formdata: this.saveData.formData,
            id: this.form.id,
          }
          saveOrUpdatePatientBcOrder(obj)
            .then((res) => {
              if (res.code == 200) {
                this.$message.success('保存成功')
                this.getRightList()
              }
            })
            .catch(() => {
              this.getRightList()
            })
        } else {
          this.$alert('页面存在必填项或者填写格式存在问题！', '提示', {
            confirmButtonText: '确定',
          })
        }
      })
    },
    // 删除
    handleDelete() {
      this.$confirm('是否确认删除?', '提示')
        .then(() => {
          // this.ids.forEach((el) => {
          //   for (let i = this.tableList.length - 1; i >= 0; i--) {
          //     if (el.tableIndex == this.tableList[i].tableIndex) {
          //       this.$delete(this.tableList, i)
          //     }
          //   }
          // })
          let query = {
            id: this.uploadData.data.id,
            ids: '',
          }
          this.selection.forEach((el) => {
            query.ids = query.ids + el.id + ','
          })
          query.ids.substring(0, query.ids.length - 1)
          removeAll(query).then((res) => {
            this.$modal.msgSuccess(res.msg)
            this.getRightList()
          })
        })
        .catch(() => {})
    },
    getIdCard(IdCard, type) {
      if (type === 1) {
        //获取出生日期
        birthday = IdCard.substring(6, 10) + '-' + IdCard.substring(10, 12) + '-' + IdCard.substring(12, 14)
        return birthday
      }
      if (type === 2) {
        //获取性别
        if (parseInt(IdCard.substr(16, 1)) % 2 == 1) {
          return '男'
        } else {
          return '女'
        }
      }
      if (type === 3) {
        //获取年龄
        var ageDate = new Date()
        var month = ageDate.getMonth() + 1
        var day = ageDate.getDate()
        var age = ageDate.getFullYear() - IdCard.substring(6, 10) - 1
        if (IdCard.substring(10, 12) < month || (IdCard.substring(10, 12) == month && IdCard.substring(12, 14) <= day)) {
          age++
        }
        if (age <= 0) {
          age = 1
        }
        return age
      }
    },
    handleRightSelectionChange(selection) {
      this.idsRight = selection.map((item) => item.sort)

      this.multiple = !selection.length
      this.selection = selection
      if (selection) {
        this.rightSelection = selection
      } else {
        this.rightSelection = []
      }
    },
    // 刷新
    handleRefresh() {
      this.getList(this.iId)
      this.form.id = undefined
    },
    // 上传文件成功
    uploadFinish(value) {
      if (value == 1) {
        this.$modal.msgSuccess('导入人员信息成功', '提醒')
        this.getRightList()
      }
      this.loading = false
    },
    // 名单模板下载
    handelDownload() {
      this.downloadStatic('/static/stencil/namelist.xlsx', '导入名单模板.xlsx')
    },
    // 提交
    uploadFile() {
      if (this.form.id == undefined || this.form.typeID == undefined) {
        this.$modal.msgWarning('请先选择分组或是摸板')
        return
      }
      var msg = this.$refs.uploadFile.isUpload()
      if (msg === true) {
        this.loading = true
        this.$refs.uploadFile.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
        this.loading = false
      }
    },
  },
}
</script>
<style scoped>
.import-list-dialog {
}

.setinput {
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 200px;
}

#setTextarea /deep/ .el-textarea__inner {
  height: 100px;
}

/* #tableLeftData /deep/ .el-table__header .el-checkbox {
  display: none;
} */
#tableLeftData /deep/ .el-table__cell,
#tableRightList /deep/ .el-table__cell {
  padding: 0;
}

#tableLeftData /deep/ .cell,
#tableRightList /deep/ .cell {
  border: none;
  padding: 0;
}

#tableLeftData /deep/ .el-input__inner {
  padding: 0 15px;
}

#tableLeftData /deep/ .el-input-number__decrease,
#tableRightList /deep/ .el-input-number__decrease {
  width: 24px;
}

#tableLeftData /deep/ .el-input-number__increase,
#tableRightList /deep/ .el-input-number__increase {
  width: 24px;
}

#tableLeftData /deep/ .el-date-editor .el-input__inner {
  padding-left: 30px;
}

/* #tableRightList /deep/ .el-table__header .el-checkbox {
  display: none;
} */
#tableRightList /deep/ .el-input__inner {
  height: 36px;
  max-height: 36px !important;
  overflow-y: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

#dialogMsg /deep/ .el-dialog__body {
  padding-top: 0;
  padding-bottom: 0;
}

#tableRightList /deep/ .el-form-item--medium {
  margin-bottom: 0px;
}

#tableRightList /deep/ .el-form-item__error {
  display: none;
}

#importDialog /deep/ .el-input__inner {
  border-radius: 0px;
}
</style>
