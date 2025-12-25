<template>
  <el-col :span="12" class="order-table-right">
    <!-- 操作按钮 -->
    <el-row :gutter="12" class="mb8" style="margin-left: 10px; margin-top: 14px">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" :disabled="forbidden" v-hasPermi="['reception:prepareOrder:edit:add2']">添加 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleDelete" :disabled="multiple" v-hasPermi="['reception:prepareOrder:edit:remove2']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" @click="handleClear" v-hasPermi="['reception:prepareOrder:edit:clear']">清除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-folder-add" @click="handleImport" :disabled="forbidden" v-hasPermi="['reception:prepareOrder:edit:import']">导入名单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit-outline" @click="handleSave(0)" v-hasPermi="['reception:prepareOrder:edit:register']">预登记</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-message" @click="handleSMS" :disabled="multiple" v-hasPermi="['reception:prepareOrder:edit:sms']">来检短信提醒</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-search" @click="handleNotYet" v-hasPermi="['reception:prepareOrder:edit:notYet']">未生成</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" @click="handleskipLevel(1)">跨级预约</el-button>
        <!-- v-hasPermi="['reception:prepareOrder:edit:skipLevel']" -->
      </el-col>
    </el-row>
    <!-- 筛选 -->
    <el-form size="small" :inline="true" style="padding: 0 20px">
      <el-form-item label="姓名" style="margin-bottom: 4px">
        <el-input size="mini" v-model="searchName" style="width: 160px" placeholder="姓名或拼音回车" clearable @keyup.enter.native="handleSearchName" @clear="handleSearchName"></el-input>
      </el-form-item>
      <el-form-item label="会员类型" prop="patientClass" style="margin-bottom: 4px">
        <el-select size="mini" v-model="patientClass" placeholder="请选择会员类型" style="width: 170px">
          <el-option label="普通会员" value="1"></el-option>
          <el-option label="VIP" value="2"></el-option>
          <el-option label="VVIP" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="sortField" style="margin-bottom: 4px">
        <el-checkbox v-model="sortField" @change="handlePatientData(tcInfo.id)">姓名排序</el-checkbox>
      </el-form-item>
      <el-form-item label="" style="margin-bottom: 4px">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleSettings" v-hasPermi="['reception:prepareOrder:edit:settings']">批量设置 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-folder-add" @click="handleSave(1)" v-hasPermi="['reception:prepareOrder:edit:save2']">保存 </el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="right-table">
      <div style="height: 100%">
        <el-table ref="rightTable" border :data="tableList" @selection-change="handleSelectionChange" height="100%" @row-dblclick="dbclick" v-loading="loading" @row-click="rowClick">
          <el-table-column type="selection" width="45" align="center" fixed="left" />
          <el-table-column label="序列" prop="rownum" width="55" align="center" />
          <el-table-column label="分组名称" prop="groupName" min-width="100" align="center" show-overflow-tooltip> </el-table-column>
          <el-table-column label="已生成" prop="isEnd" min-width="65" align="center">
            <template slot-scope="scope">
              <div class="checkbox1">
                <input type="checkbox" :checked="scope.row.patientcode != ''" disabled />
              </div>
            </template>
          </el-table-column>
          <el-table-column label="已登记" prop="fRegistered" min-width="65" align="center">
            <template slot-scope="scope">
              <div class="checkbox1">
                <input type="checkbox" :checked="scope.row.fregistered == 1" disabled />
              </div>
            </template>
          </el-table-column>
          <el-table-column label="体检号" prop="patientcode" min-width="120" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.patientcode }}</span>
            </template>
          </el-table-column>
          <el-table-column label="统收限额" prop="tsLimit" min-width="120" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.tsLimit || '请输入' }}</span>
              <input v-else size="mini" v-model="scope.row.tsLimit" placeholder="请输入" style="width: 100%" clearable />
            </template>
          </el-table-column>
          <el-table-column label="姓名" prop="patientname" min-width="120" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.patientname || '请输入' }}</span>
              <input id="patientname" v-else size="mini" v-model="scope.row.patientname" placeholder="请输入" style="width: 100%" @blur="nameChange(scope.row.patientname, scope.$index)" />
            </template>
          </el-table-column>
          <el-table-column label="拼音" prop="pinyin" min-width="90" align="center" show-overflow-tooltip> </el-table-column>
          <el-table-column label="性别" prop="idSex" min-width="120" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ ['男', '女'][scope.row.idSex] }}</span>
              <select v-else v-model="scope.row.idSex" @change="changeSex(scope.row.idSex, scope.$index)">
                <option :value="0">男</option>
                <option :value="1">女</option>
              </select>
            </template>
          </el-table-column>
          <el-table-column label="身份证号" prop="idcardno" min-width="210" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.idcardno }}</span>
              <input v-else size="mini" v-model="scope.row.idcardno" placeholder="请输入" clearable @blur="idcardnoChange(scope.$index)" />
            </template>
          </el-table-column>
          <el-table-column label="分中心" prop="hospitalcode" width="120" align="center">
            <template slot-scope="scope">
              <select v-model="scope.row.hospitalcode" placeholder="" :disabled="scope.row.fregistered == 1">
                <option v-for="item in fzxOptions" :key="item.id" :value="item.branchId">{{ item.fzx }}</option>
              </select>
            </template>
          </el-table-column>
          <el-table-column label="会员类型" prop="idPatientclass" min-width="130" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ ['', '普通会员', 'VIP', 'VVIP'][scope.row.idPatientclass] }}</span>
              <select v-else v-model="scope.row.idPatientclass">
                <option value="1">普通会员</option>
                <option value="2">VIP</option>
                <option value="3">VVIP</option>
              </select>
            </template>
          </el-table-column>
          <el-table-column label="SABC等级" prop="sabc" min-width="130" align="center">
            <template slot-scope="scope">
              <select v-model="scope.row.sabc">
                <option value="S">S</option>
                <option value="A">A</option>
                <option value="B">B</option>
                <option value="C">C</option>
              </select>
            </template>
          </el-table-column>
          <el-table-column label="年龄" prop="age" min-width="120" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.age }}</span>
              <input v-else size="mini" v-model="scope.row.age" style="width: 100%" placeholder="请输入" type="number" />
            </template>
          </el-table-column>
          <el-table-column label="手机号码" prop="phone" min-width="160" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.phone }}</span>
              <input v-else size="mini" v-model="scope.row.phone" style="width: 100%" placeholder="请输入" type="tel" clearable />
            </template>
          </el-table-column>
          <el-table-column label="工种" min-width="160" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.trades }}</span>
              <input-select v-else :selectData="selectData" @change="handleWorkTypeChange($event, scope.$index)" :initialValue="scope.row.trades"></input-select>
              <!-- <el-select v-model="scope.row.trades" filterable remote clearable placeholder="请输入工种关键字" :remote-method="typeSearch" :loading="typeLoading" @change="handleWorkTypeChange(scope)" @clear="typeOptions = []" style="width: 100%">
                <el-option v-for="item in typeOptions" :key="item.id" :label="item.typeName" :value="item.typeName"> </el-option>
              </el-select> -->
            </template>
          </el-table-column>
          <el-table-column label="出生日期" prop="birthdate" min-width="180" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.birthdate }}</span>
              <el-date-picker v-else v-model="scope.row.birthdate" type="date" placeholder="选择日期" style="width: 150px" value-format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="婚姻" prop="idMarriage" min-width="120" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ ['', '未婚', '已婚', '离异', '丧偶', '其他'][scope.row.idMarriage] }}</span>
              <select v-else v-model="scope.row.idMarriage" placeholder="请选择">
                <option v-for="item in idMarriageOptions" :key="item.value" :value="item.value">{{ item.label }}</option>
              </select>
            </template>
          </el-table-column>
          <el-table-column label="民族" prop="idNation" min-width="150" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.nationName }}</span>
              <select v-else v-model="scope.row.idNation" placeholder="请选择">
                <option v-for="item in nationList" :key="item.id" :value="item.id">{{ item.name }}</option>
              </select>
            </template>
          </el-table-column>
          <el-table-column label="文化程度" prop="cultural" min-width="120" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">
                {{ scope.row.cultural ? culturalOptions[scope.row.cultural].label : '' }}
              </span>
              <select v-else v-model="scope.row.cultural" placeholder="请选择">
                <option v-for="item in culturalOptions" :key="item.value" :value="item.value">{{ item.label }}</option>
              </select>
            </template>
          </el-table-column>
          <el-table-column label="部门" prop="orgDepart" min-width="150" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.orgDepart }}</span>
              <input v-else size="mini" v-model="scope.row.orgDepart" style="width: 100%" placeholder="请输入" clearable />
            </template>
          </el-table-column>
          <template v-if="tcInfo.idExamtype == 1 || tcInfo.idExamtype == 2">
            <el-table-column label="工号" min-width="160" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.workno }}</span>
                <input v-else size="mini" v-model="scope.row.workno" style="width: 100%" placeholder="请输入" clearable />
              </template>
            </el-table-column>
            <el-table-column label="参加工作时间" min-width="180" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.workDate }}</span>
                <el-date-picker v-else v-model="scope.row.workDate" type="datetime" placeholder="选择日期" style="width: 150px" value-format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="总工龄(月)" min-width="160" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.zgl }}</span>
                <input v-else size="mini" v-model="scope.row.zgl" style="width: 100%" placeholder="请输入" clearable />
              </template>
            </el-table-column>
            <el-table-column label="从事工种时间" min-width="180" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.harmDate }}</span>
                <el-date-picker v-else v-model="scope.row.harmDate" type="datetime" placeholder="选择日期" style="width: 150px" value-format="yyyy-MM-dd HH:mm:ss"> </el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="接害工龄(月)" min-width="160" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.jhgl }}</span>
                <input v-else size="mini" v-model="scope.row.jhgl" style="width: 100%" placeholder="请输入" clearable />
              </template>
            </el-table-column>
            <el-table-column label="体检类别" min-width="120" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.medicaltype || scope.row.medicaltype === '0' ? medicaltypeOptions[scope.row.medicaltype].label : '' }}</span>
                <select v-else v-model="scope.row.medicaltype" placeholder="请选择">
                  <option v-for="item in medicaltypeOptions" :key="item.value" :value="item.value">{{ item.label }}</option>
                </select>
              </template>
            </el-table-column>
          </template>
          <el-table-column label="备注" prop="note" min-width="220" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.fregistered == 1 || checkIndex != scope.$index">{{ scope.row.note }}</span>
              <input v-else size="mini" v-model="scope.row.note" style="width: 100%" placeholder="请输入" />
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <pagination :total="total" :page.sync="pageInfo.current" :limit.sync="pageInfo.size" :page-size="100" :page-sizes="[50, 100, 150]" @pagination="handlePatientData(tcInfo.id)" />

    <!-- 确认清除对话框 -->
    <el-dialog title="注意" width="500px" :visible.sync="clearDialog" append-to-body :close-on-click-modal="false" @close="cancel">
      <div style="line-height: 30px">
        <div>
          注意：【<span style="color: red">{{ clearInfo.tips }}</span>】
        </div>
        <div>请输入以下内容：</div>
        <div style="display: flex">
          <div style="flex: 1">确认执行清除操作</div>
          <span style="color: red; cursor: pointer" @click="clearConfirm = '确认执行清除操作'">填入</span>
        </div>
        <el-input v-model="clearConfirm" type="textarea"></el-input>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirm" :disabled="clearConfirm.trim() != '确认执行清除操作'">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 导入对话框 -->
    <edit-right-import ref="editImport" @handleFreshen="handleFreshen"></edit-right-import>
    <!-- 来检短信提醒 -->
    <edit-right-sMS ref="editSMS"></edit-right-sMS>
    <!-- 预约审批内容对话框 -->
    <appointment-approval ref="appointmentApproval" @handlePatientData="handlePatientData(tcInfo.id)"></appointment-approval>
  </el-col>
</template>

<script>
import { getPatientData, saveOrUpdatePatientBc, saveOrUpdatePatient, updatehy, removeAll, getToBeSubmitted, getBaseWorktypeSql } from '@/api/reception/prepare_order.js'

import pinyin from '@/utils/pinyin.js'
import { getDate } from '@/utils/getDate.js'
import { getCultural } from '@/utils/dataList.js'

import editRightImport from './edit_right_import.vue'
import editRightSMS from './edit_right_SMS.vue'
import appointmentApproval from './appointment_approval.vue'
export default {
  components: {
    editRightImport,
    editRightSMS,
    appointmentApproval,
  },
  props: ['nationList', 'orderId', 'orderName', 'fzxOptions'],
  data() {
    return {
      // 顶部订单数据
      formdata: {},
      // **********右侧表格相关
      // 会员类型
      patientClass: '',
      // **********按钮相关
      // 清除对话框
      clearDialog: false,
      // 确认清除文字
      clearConfirm: '',
      clearInfo: {
        tips: '',
      },
      // 表格内民族参数
      selectData: {
        placeholder: '请输入',
        key: '输入码', //第一列标题
        value: '民族', //第二列标题
        url: '', //请求连接
        selectWidth: 120, //选择器宽度（选填，默认230）不加px
      },
      // 表格内婚姻选项
      idMarriageOptions: [
        { label: '未婚', value: 1 },
        { label: '已婚', value: 2 },
        { label: '离异', value: 3 },
        { label: '丧偶', value: 4 },
        { label: '其他', value: 5 },
      ],
      culturalOptions: getCultural(),
      // 加载中
      loading: false,
      // 分页总数
      total: 0,
      pageInfo: {
        current: 1,
        size: 100,
      },
      // 表格数据
      tableList: [],
      tempList: [],
      // 表格索引
      tableIndex: 0,
      // 选中的索引
      selectIndex: [],
      // 选中的数据
      selectInfo: [],
      // 所选套餐信息
      tcInfo: {},
      // 表格内体检套餐参数
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 分组ID
      fzId: undefined,
      // 套餐是否禁用
      forbidden: false,
      // 是否为条件查询
      isSearch: false,
      // 查询姓名参数
      searchName: '',
      // 搜索条件下的表格数据
      searchTableData: [],
      // 选择点击的数据索引
      checkIndex: -1,
      // 按姓名排序
      sortField: '',
      // 保存防抖
      saveDebounce: false,
      // 工种搜索框信息
      selectData: {
        placeholder: '请输入工种名称',
        value: '名称', //第二列标题
        url: '/reception/order/getBaseWorktypeSql', //请求连接
        selectWidth: '100%', //选择器宽度（选填，默认230）不加px,传100%则为100%
        bindValue: '', //初始值
        secondName: 'typeName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'typeName', //向接口传递的参数名(不传默认为'inputCode')
      },
      // 搜索工种加载中
      typeLoading: false,
      // 工种列表
      typeOptions: [],
      // 体检类别
      medicaltypeOptions: [
        { label: '上岗前', value: '0' },
        { label: '在岗期间', value: '1' },
        { label: '离岗时', value: '2' },
        { label: '离岗后', value: '3' },
        { label: '应急', value: '4' },
      ],
      //定义新变量-当前表格高度
      scrollTop: null,
    }
  },
  watch: {
    searchTableData: {
      handler(newVal) {
        newVal.forEach((el) => {
          this.tableList.forEach((val) => {
            if (el.tableIndex == val.tableIndex) {
              val = el
            }
          })
        })
      },
    },
  },
  mounted() {
    this.bus.$on('handlePeopleData', (row, formdata) => {
      this.tableIndex = 0
      this.tcInfo = {}
      this.tableList = []
      this.tempList = []
      this.forbidden = false
      this.formdata = formdata
      if (row && row.id) {
        this.$nextTick(() => {
          this.tcInfo = row
          this.pageInfo.current = 1
          this.handlePatientData(row.id)
          this.forbidden = row.groupLimit || row.forbidden ? true : false
        })
      }
    })
    this.bus.$on('handleDataId', (id) => {
      this.fzId = id
    })
  },
  activated() {
    this.saveScroll()
  },
  beforeDestroy() {
    this.$refs.rightTable.bodyWrapper.removeEventListener(
      'scroll',
      (res) => {
        let height = res.target
        this.scrollTop = height.scrollTop
      },
      false
    )
    this.bus.$off('handlePeopleData')
    this.bus.$off('handleDataId')
  },
  methods: {
    // 设置表格滚动条位置
    saveScroll() {
      this.$nextTick(() => {
        setTimeout(() => {
          var scrollTop = this.$el.querySelector('.el-table__body-wrapper')
          scrollTop.scrollTop = this.scrollTop
        }, 50)
      })
    },
    // 获取体检者信息
    handlePatientData(id) {
      this.loading = true
      getPatientData({
        idOrgreservationgroup: id,
        inputCode: '',
        patientname: this.searchName,
        ...this.pageInfo,
        sortField: this.sortField ? 'pinyin' : '',
        sortOrder: this.sortField ? 'asc' : '',
      })
        .then(({ data }) => {
          // let hospitalcode = this.$getCookie('cid')
          data.records.forEach((el) => {
            el.tableIndex = 'tableIndex' + this.tableIndex++
            el.groupName = this.tcInfo.orgreservationgroupname
            el.pinyin = pinyin(el.patientname)
            if (el.medicaltype == 'null') {
              el.medicaltype = null
            }
            // if (!el.hospitalcode) {
            //   el.hospitalcode = hospitalcode
            // }
          })
          this.tempList = JSON.parse(JSON.stringify(data.records))
          this.tableList = data.records
          this.total = data.total
          this.loading = false
          this.$refs.rightTable.bodyWrapper.addEventListener(
            'scroll',
            (res) => {
              let height = res.target
              this.scrollTop = height.scrollTop
            },
            false
          )
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 性别改变
    changeSex(sex, index) {
      if ((this.tcInfo.fMale != 1 && sex == 0) || (this.tcInfo.fFemale != 1 && sex == 1)) {
        this.$alert('所选性别与套餐不符', '提示')
        if (this.tcInfo.fMale == 1) {
          this.tableList[index].idSex = 0
        } else {
          this.tableList[index].idSex = 1
        }
      }
    },
    // 获取姓名拼音
    nameChange(value, index) {
      this.tableList[index].pinyin = pinyin(value)
    },
    // 表格中民族返回值
    selectChange(event, index) {
      this.tableList[index - 1].idNation = event
    },
    // 表格中付款方式返回值
    selectChange3(event, index) {
      this.tableList[index - 1].idPayway = event
    },
    // 身份证号改变性别
    idcardnoChange(index) {
      let idCard = this.tableList[index].idcardno
      if (!idCard) {
        return
      }
      if (idCard.length == 15 || idCard.length == 18) {
        if (parseInt(idCard.slice(-2, -1)) % 2 == 1) {
          this.tableList[index].idSex = 0
        } else {
          this.tableList[index].idSex = 1
        }
      }
    },
    // 搜索姓名
    handleSearchName() {
      if (!this.tableList.length) {
        this.$alert('人员列表为空', '提示')
        return
      }
      this.handlePatientData(this.tcInfo.id)
      return
      if (!this.searchName) {
        this.isSearch = false
      }
      this.searchTableData = []
      if (!this.tableList.length) {
        return
      }
      this.isSearch = true
      let pinyinName = pinyin(this.searchName)
      this.tableList.forEach((el) => {
        if (el.patientname.includes(this.searchName)) {
          this.searchTableData.push(el)
        }
      })
      if (!this.searchTableData.length) {
        this.tableList.forEach((el) => {
          if (el.pinyin.includes(pinyinName)) {
            this.searchTableData.push(el)
          }
        })
      }
    },
    // 查询工种
    typeSearch(typeName) {
      if (typeName != '') {
        this.typeLoading = true
        getBaseWorktypeSql({
          size: 100,
          typeName,
        }).then(({ data }) => {
          this.typeLoading = false
          this.typeOptions = data.records
        })
      }
    },
    // 切换工种列表
    handleWorkTypeChange(value, index) {
      this.tableList[index].worktypeId = value.id
      this.tableList[index].trades = value.typeName
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.selectIndex = selection.map((item) => item.tableIndex)
      this.selectInfo = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (!selection.length) {
        this.checkIndex = -1
      }
    },
    // 单击选中某行
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.rightTable.clearSelection() //清空表格数据
      this.$refs.rightTable.toggleRowSelection(row, true)
      this.tableList.forEach((el, index) => {
        if (el.tableIndex == row.tableIndex) {
          this.checkIndex = index
        }
      })
    },
    // 双击行跳转
    dbclick(row, column, event) {
      if (row.patientcode) {
        let name = 'Registration'
        const obj = { path: '/reception/registration/index', name }
        this.$tab.closePage(obj).then(() => {
          this.$router.push({ name, params: { patientcode: row.patientcode } })
        })
      }
    },
    // 添加
    handleAdd() {
      if (!this.tcInfo.id) {
        this.$alert('请先选择保存后的套餐', '提示')
        return
      }
      let idSex = ''
      if (this.tcInfo.fMale == 1) {
        idSex = 0
      } else {
        idSex = 1
      }
      let list = {
        tableIndex: 'tableIndex' + this.tableIndex++,
        groupName: this.tcInfo.orgreservationgroupname,
        // hospitalcode: this.$getCookie('cid'),
        // 2024-8-29 新增数据设置分中心字段为空
        hospitalcode: '',
        isEnd: '',
        fRegistered: '',
        patientcode: '',
        patientname: '',
        pinyin: '',
        idSex,
        idPatientclass: '1',
        age: 0,
        phone: '',
        birthdate: getDate(),
        idcardno: '',
        idMarriage: '',
        idNation: '',
        cultural: '',
        orgDepart: '',
        note: '',
        harmDate: getDate(),
        workDate: getDate(),
      }
      this.tableList.unshift(list)
      this.$nextTick(() => {
        this.$refs.rightTable.clearSelection() //清空表格数据
        this.$refs.rightTable.toggleRowSelection(this.tableList[0], true)
        this.checkIndex = 0
        this.$nextTick(() => {
          document.getElementById('patientname').focus()
        })
      })
    },
    // 删除
    handleDelete() {
      let error = ''
      this.selectIndex.forEach((el) => {
        this.tableList.forEach((val) => {
          if (el == val.tableIndex && val.patientcode) {
            error = val.patientname
          }
        })
      })
      if (error) {
        this.$alert(error + ' 已经生成体检号，不可删除', '提示')
        return
      }
      const postIds = this.selectIndex
      this.$modal.confirm('是否确认删除该数据项？').then(() => {
        postIds.forEach((val) => {
          for (let i = this.tableList.length - 1; i >= 0; i--) {
            if (val == this.tableList[i].tableIndex) {
              this.$delete(this.tableList, i)
            }
          }
        })
        this.tableList = JSON.parse(JSON.stringify(this.tableList))
        this.$modal.msgSuccess('删除成功')
      })
    },
    // 清除
    handleClear() {
      if (!this.tcInfo.id) {
        this.$alert('请先选择保存后的套餐', '提示')
        return
      }
      this.clearInfo.tips = ''
      if (this.selectInfo.length) {
        let list = []
        for (let i = 0; i < this.selectInfo.length; i++) {
          if (this.selectInfo[i].fregistered == 1) {
            this.$alert('体检者' + this.selectInfo[i].patientname + '已登记,不可清除')
            return
          }
          list.push(this.selectInfo[i].patientname)
        }
        this.clearInfo.tips = '当前选择体检者为' + list.join('，') + ',一旦清除无法恢复'
      } else {
        this.clearInfo.tips = '当前未选择体检者,执行此操作将清除该订单下所有未登记的数据,一旦清除无法恢复'
      }
      this.clearDialog = true
    },
    // 确认清除
    confirm() {
      let ids = []
      if (this.selectInfo.length) {
        this.selectInfo.forEach((el) => {
          ids.push(el.id)
        })
      } else {
        ids = []
      }
      removeAll({
        id: this.tcInfo.id,
        ids: ids.join(','),
      }).then(() => {
        this.$message({
          message: '清除成功',
          type: 'success',
        })
        this.tableList = []
        this.handlePatientData(this.tcInfo.id)
        this.cancel()
      })
    },
    // 关闭清除对话框
    cancel() {
      this.clearConfirm = ''
      this.clearDialog = false
    },
    // 打开导入对话框
    handleImport() {
      if (this.formdata.FFinished == 1) {
        this.$modal.msgWarning('订单已结束，不能导入人员。', '提醒')
        return
      }
      this.$refs.editImport.showDialog(this.fzId, this.formdata)
    },
    // 来检短信提醒
    handleSMS() {
      let error = ''
      this.tableList.forEach((el) => {
        if (!el.patientcode) {
          error = "<font color='red'>" + el.patientname + '</font>需要生成体检号'
        }
      })
      if (error) {
        this.$alert(error, '提示', {
          dangerouslyUseHTMLString: true,
        })
        return
      }
      this.$refs.editSMS.showDialog(this.ids, this.formdata.peisorgreservation.ddh)
    },
    // 未生成
    handleNotYet() {
      let array = []
      this.tableList.forEach((el) => {
        if (!el.patientcode) {
          array.push(el)
        }
      })
      if (!array.length) {
        this.$modal.msg('没有状态为未生成的体检者')
        return
      } else {
        this.$refs.rightTable.clearSelection() //清空表格数据
        array.forEach((el) => {
          this.$refs.rightTable.toggleRowSelection(el)
        })
      }
    },
    // 批量设置
    handleSettings() {
      if (!this.patientClass) {
        this.$alert('请选自一种会员类型', '批量设置提示')
      } else {
        if (!this.ids.length) {
          this.$alert('请至少选择一条记录', '批量设置提示')
        } else {
          let error = ''
          let data = []
          this.selectIndex.forEach((el) => {
            this.tableList.forEach((val) => {
              if (el == val.tableIndex && !val.patientcode) {
                error = (val.patientname ? val.patientname : '当前选择项') + '未生成体检号,请保存后再试'
              }
              if (el == val.tableIndex) {
                data.push(val.patientcode)
              }
            })
          })
          if (error) {
            this.$alert(error, '批量设置提示')
            return
          }
          this.loading = true
          updatehy({
            data: data.join(','),
            id: this.patientClass,
          })
            .then(() => {
              this.$modal.msgSuccess('更改成功')
              this.loading = false
              this.selectIndex.forEach((el) => {
                this.tableList.forEach((val) => {
                  if (el == val.tableIndex) {
                    val.idPatientclass = this.patientClass
                  }
                })
              })
            })
            .catch(() => {
              this.loading = false
            })
        }
      }
    },
    // 保存
    handleSave(save) {
      if (!this.tableList.length) {
        this.$alert('没有需要保存的体检者', '提示')
        return
      }
      let isModify = false
      let error = ''
      this.tableList.forEach((el, index) => {
        if (!el.patientcode || el.fRegistered != 1) {
          isModify = true
        }
        if (!error && !el.patientname) {
          error = '第' + (index + 1) + '名体检者姓名不能为空'
        }
      })
      if (!isModify) {
        this.$alert('没有需要保存的体检者', '提示')
        return
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      let first = ''
      let second = ''
      for (let i = 0; i < this.tableList.length - 1; i++) {
        if (this.tcInfo.idPatientclass3 != 1) {
          first = this.tableList[i].idcardno
            ? this.tableList[i].idcardno
            : this.tableList[i].idSex +
            '&' +
            this.tableList[i].age +
            '&' +
            (this.tableList[i].phone ? this.tableList[i].phone : null) +
            '&' +
            (this.tableList[i].orgDepart ? this.tableList[i].orgDepart : null) +
            '&' +
            (this.tableList[i].worktypeId ? this.tableList[i].worktypeId : null) +
            '&' +
            (this.tableList[i].patientname ? this.tableList[i].patientname : null)
          for (let j = i + 1; j < this.tableList.length; j++) {
            second = this.tableList[j].idcardno
              ? this.tableList[j].idcardno
              : this.tableList[j].idSex +
              '&' +
              this.tableList[j].age +
              '&' +
              (this.tableList[j].phone ? this.tableList[j].phone : null) +
              '&' +
              (this.tableList[j].orgDepart ? this.tableList[j].orgDepart : null) +
              '&' +
              (this.tableList[j].worktypeId ? this.tableList[j].worktypeId : null) +
              '&' +
              (this.tableList[j].patientname ? this.tableList[j].patientname : null)
            if (first == second) {
              this.$alert('存在信息相同的人员 ' + this.tableList[j].patientname + ' 可在分组处点击设为重复', '提示')
              return
            }
          }
        }
      }
      if (this.saveDebounce) {
        this.$moadl.msgWarning('保存中,请勿重复点击')
        return
      }
      this.saveDebounce = true
      if (this.formdata.peisorgreservation) {
        this.formdata = { ...this.formdata, ...this.formdata.peisorgreservation }
        this.formdata.peisorgreservation = undefined
      }
      this.loading = true
      let griddata = []
      let tableList = this.tableList.map((item) => item.id)
      this.tempList.forEach((el) => {
        if (!tableList.includes(el.id)) {
          el.state = 'removed'
          griddata.push(el)
        }
      })
      let temp = this.tempList.map((item) => item.id)
      this.tableList.forEach((el) => {
        if (!temp.includes(el.id)) {
          el.state = 'added'
          el.idExamsuite = el.tcid
        } else {
          el.state = 'modified'
        }
        griddata.push(el)
      })
      let hasChange = false
      for (let i = griddata.length - 1; i >= 0; i--) {
        for (let j = this.tempList.length - 1; j >= 0; j--) {
          if (
            this.tempList[j].id == griddata[i].id &&
            this.tempList[j].hospitalcode == griddata[i].hospitalcode &&
            this.tempList[j].tsLimit == griddata[i].tsLimit &&
            this.tempList[j].patientname == griddata[i].patientname &&
            this.tempList[j].idcardno == griddata[i].idcardno &&
            this.tempList[j].idSex == griddata[i].idSex &&
            this.tempList[j].idPatientclass == griddata[i].idPatientclass &&
            this.tempList[j].age == griddata[i].age &&
            this.tempList[j].phone == griddata[i].phone &&
            this.tempList[j].birthdate == griddata[i].birthdate &&
            this.tempList[j].idMarriage == griddata[i].idMarriage &&
            this.tempList[j].idNation == griddata[i].idNation &&
            this.tempList[j].cultural == griddata[i].cultural &&
            this.tempList[j].orgDepart == griddata[i].orgDepart &&
            this.tempList[j].note == griddata[i].note &&
            this.tempList[j].worktypeId == griddata[i].worktypeId &&
            this.tempList[j].trades == griddata[i].trades &&
            this.tempList[j].workno == griddata[i].workno &&
            this.tempList[j].workDate == griddata[i].workDate &&
            this.tempList[j].zgl == griddata[i].zgl &&
            this.tempList[j].harmDate == griddata[i].harmDate &&
            this.tempList[j].jhgl == griddata[i].jhgl &&
            this.tempList[j].medicaltype == griddata[i].medicaltype &&
            this.tempList[j].sabc == griddata[i].sabc
          ) {
            hasChange = true
            this.$delete(griddata, i)
            break
          }
        }
      }
      if (save) {
        if (griddata.length == 0) {
          if (hasChange) {
            this.$modal.msgSuccess('保存成功')
          } else {
            this.$alert('没有需要保存的体检者', '提示')
          }
          this.loading = false
          this.saveDebounce = false
        } else {
          saveOrUpdatePatientBc({
            formdata: this.formdata,
            griddata,
            id: this.tcInfo.id,
          })
            .then(() => {
              this.$modal.msgSuccess('保存成功')
              this.loading = false
              this.saveDebounce = false
              this.handleskipLevel()
            })
            .catch((err) => {
              console.error(err)
              this.loading = false
              this.saveDebounce = false
            })
        }
      } else {
        for (let i = griddata.length - 1; i >= 0; i--) {
          if (griddata[i].patientcode) {
            this.$delete(griddata, i)
          }
        }
        if (griddata.length == 0) {
          if (!hasChange) {
            this.$modal.msgSuccess('保存成功')
          } else {
            this.$alert('没有需要保存的体检者', '提示')
          }
          this.loading = false
          this.saveDebounce = false
        } else {
          saveOrUpdatePatient({
            formdata: this.formdata,
            griddata,
            id: this.tcInfo.id,
          })
            .then(() => {
              this.$modal.msgSuccess('预登记成功')
              this.loading = false
              this.saveDebounce = false
              this.handleskipLevel()
            })
            .catch((err) => {
              console.error(err)
              this.loading = false
              this.saveDebounce = false
            })
        }
      }
    },
    // 跨级预约
    handleskipLevel(type) {
      getToBeSubmitted({ orderId: this.formdata.ddh }).then((res) => {
        if (res.data.length) {
          this.$refs.appointmentApproval.showDialog(this.orderId, this.orderName, res.data)
        } else {
          if (type == 1) {
            this.$modal.msgWarning('暂无等待提交预约审批的体检者')
          }
          if (this.tcInfo?.id) {
            this.handlePatientData(this.tcInfo.id)
          }
        }
      })
    },
    // 刷新
    handleFreshen() {
      if (this.tcInfo.id) this.handlePatientData(this.tcInfo.id)
    },
  },
}
</script>

<style lang="scss">
.order-table-right {
  flex: auto;
  display: flex;
  flex-direction: column;

  .right-table {
    flex: auto;
    min-height: 0;
    max-height: calc(100% - 60px);

    .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
      background: transparent;
    }

    .el-input__inner {
      padding: 0 8px;
      border-width: 0 !important;
      text-align: center;
      background: transparent;

      &:focus {
        border-width: 1px !important;
        text-align: left;
      }
    }

    .el-table--mini .el-table__cell {
      padding: 0;
    }

    .checkbox1 {
      display: flex;
      flex-direction: column;
      align-items: center;

      input[type='checkbox'] {
        position: relative;
        width: 13px;
        height: 13px;
      }

      input[type='checkbox']:checked:disabled::after {
        position: absolute;
        top: 0;
        width: 13px;
        height: 13px;
        line-height: 13px;
        background-color: #fe6939;
        color: #fff;
        display: inline-block;
        visibility: visible;
        text-align: center;
        border-radius: 3px;
        content: '✓';
        font-size: 8px;
      }
    }

    select {
      width: 100%;
      height: 22px;
      text-align: center;
      background-color: transparent;
      border-color: transparent;
      cursor: pointer;

      &:focus,
      &:hover {
        border-color: rgb(118, 118, 118);
      }
    }
  }
}
</style>
