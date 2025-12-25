<!-- 体检基础套餐维护  开发人：麦沃德科技暴雨/矢北 -->
<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="title" :visible.sync="open" class="add-items" width="95%" append-to-body :close-on-click-modal="false">
    <el-form :rules="rules" ref="form" :model="form" :inline="true" label-width="130px" hide-required-asterisk>
      <el-form-item label="体检套餐名称" prop="tjtcmc">
        <el-input v-model="form.tjtcmc" placeholder="请输入" clearable style="width: 200px" @input="nameChange1" />
      </el-form-item>
      <el-form-item label="体检套餐输入码" prop="tjtcsrm">
        <el-input v-model="form.tjtcsrm" placeholder="请输入" readonly clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="体检套餐简称" prop="tjtcjc">
        <el-input v-model="form.tjtcjc" placeholder="请输入" clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="分中心" prop="fzxid">
        <search-select ref="select1" :selectData="selectData1" :initialValue="initFzx" :selectWidth="200" :multiple="true" @change="change1"></search-select>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="form.sort" placeholder="请输入" clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="套餐原始价格" prop="tcysjg">
        <el-input v-model="form.tcysjg" readonly clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="套餐折扣" prop="tczk">
        <el-input-number v-model="form.tczk" @blur="handleChange" placeholder="请输入" clearable style="width: 200px" :precision="2" controls-position="right" />
      </el-form-item>
      <el-form-item label="优惠价" prop="zhjg">
        <el-input-number v-model="form.zhjg" @blur="priceChange" placeholder="请输入" clearable style="width: 200px" :precision="2" controls-position="right" />
      </el-form-item>
      <el-form-item label="适用性别" prop="syxb">
        <el-select v-model="form.syxb" placeholder="请选择" style="width: 200px">
          <el-option label="男" :value="0"></el-option>
          <el-option label="女" :value="1"></el-option>
          <el-option label="通用" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否已婚套餐" prop="sfyhtc">
        <el-select v-model="form.sfyhtc" placeholder="请选择" style="width: 200px">
          <el-option label="是" value="0"></el-option>
          <el-option label="否" value="1"></el-option>
          <el-option label="通用" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="form.tjlx" placeholder="请选择体检类型" clearable style="width: 200px">
          <el-option label="健康体检" :value="0" />
          <el-option label="职业体检" :value="1" />
          <el-option label="综合" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="接害因素" prop="jhys">
        <search-select ref="select2" :selectData="selectData2" :initialValue="form.jhysmc" @change="change2" :selectWidth="200" :selectDisabled="form.tjlx == 0"> </search-select>
      </el-form-item>
      <el-form-item label="职业体检类别" prop="zytjlb">
        <el-select v-model="form.zytjlb" placeholder="请选择体检类型" clearable style="width: 200px" :disabled="form.tjlx == 0">
          <el-option label="上岗前" :value="0" />
          <el-option label="在岗期间" :value="1" />
          <el-option label="离岗时" :value="2" />
          <el-option label="离岗后" :value="3" />
          <el-option label="应急" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="年龄自" prop="nlz">
        <el-input-number v-model="form.nlz" placeholder="请输入" controls-position="right" :min="0" :max="99" :precision="0" style="width: 200px" />
      </el-form-item>
      <el-form-item label="年龄至" prop="nld">
        <el-input-number v-model="form.nld" placeholder="请输入" controls-position="right" :min="1" :max="100" :precision="0" style="width: 200px" />
      </el-form-item>
      <el-form-item label="平安套餐ID" prop="pinganId">
        <el-input v-model="form.pinganId" placeholder="请输入" clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="小程序套餐分类" prop="appTypeId">
        <el-select v-model="form.appTypeId" placeholder="请选择" style="width: 200px"> </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="bz">
        <el-input v-model="form.bz" placeholder="请输入" clearable style="width: 880px" />
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
      <!-- 表格 -->
      <el-table ref="tableList" border :data="tableList" height="430px" style="margin: -1px" @selection-change="handleSelectionChange" @click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column type="index" label="顺序" prop="sort" min-width="120" align="center"> </el-table-column>
        <el-table-column label="收费项目名称" prop="sfxmmc" min-width="274" align="center">
          <template slot-scope="scope">
            <input-select :ref="'sfxmmc' + scope.$index" :selectData="labTypeData" :queryParams="form" :currentIndex="scope.row.index" :initialValue="scope.row.sfxmmc" @change="labTypeChange"></input-select>
          </template>
        </el-table-column>
        <el-table-column label="收费项目输入码" prop="sfxmsrm" min-width="120" align="center"> </el-table-column>
        <el-table-column label="性别" prop="xb" min-width="100" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.xb == 0" type="warning">男</el-tag>
            <el-tag v-else-if="scope.row.xb == 1" type="danger">女</el-tag>
            <el-tag v-else-if="scope.row.xb == 2" type="success">通用</el-tag>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column label="检查意义" prop="jcyy" min-width="180" align="center" show-overflow-tooltip> </el-table-column>
        <el-table-column label="价格" prop="jg" min-width="100" align="center"> </el-table-column>
        <el-table-column label="备注" prop="bz" min-width="160" align="center"> </el-table-column>
        <el-table-column label="体检类型" prop="tjlx" min-width="120" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.tjlx == 0">健康体检</span>
            <span v-else-if="scope.row.tjlx == 1">职业体检</span>
            <span v-else-if="scope.row.tjlx == 2">综合体检</span>
            <span v-else>暂无声明</span>
          </template>
        </el-table-column>
        <el-table-column label="所属科室" prop="ssks" min-width="120" align="center"> </el-table-column>
        <el-table-column label="必检" prop="sfbj" min-width="160" align="center">
          <template slot-scope="scope">
            <el-checkbox :true-label="1" :false-label="0" v-model="scope.row.sfbj"></el-checkbox>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button type="primary" plain @click="reset(1)">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getCreatecombo, getDetailItem, saveDetailData, fzxData } from '@/api/basis/healthsmall'
import pinyin from '@/utils/pinyin.js'
export default {
  components: {},
  props: [],
  data() {
    var checkAge = (rule, value, callback) => {
      if (!value && value !== 0) {
        callback(new Error('年龄不能为空'))
      } else if (this.form.nlz > this.form.nld) {
        callback(new Error('年龄区间错误,请重新输入'))
      } else {
        callback()
      }
    }
    return {
      // 搜索选择器数据
      selectData1: {
        placeholder: '请选择',
        inputTitle: '输入码', // 搜索标题
        inputPlaceholder: '请输入输入码查询', // 搜索placeholder
        key: '输入码', //第一列标题
        value: '分中心名称', //第二列标题
        url: '/sell/createorder/getBranchData', //请求连接
        firstName: 'srm', //接口返回值对应第一列的参数名
        secondName: 'fzx', //接口返回值对应第二列的参数名
        queryData: 'key',
        bindValue: [],
      },
      // 收费项目名称
      labTypeData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '收费项目名称',
        third: '所属科室',
        fourth: '价格',
        url: '/sell/createcombo/getShowData',
        bindValue: '', //初始值(必传)
        queryData: 'key',
        firstName: 'sfxmsrm',
        secondName: 'sfxmmc',
        thirdName: 'ssks',
        fourthName: 'jg',
      },
      // 搜索选择器数据
      selectData2: {
        placeholder: '请选择',
        inputTitle: '输入码', // 搜索标题
        inputPlaceholder: '请输入输入码查询', // 搜索placeholder
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        url: '/harm/getHarmData', //请求连接
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'harmName', //接口返回值对应第二列的参数名
        params: {},
      },
      //项目相关参数
      itemQuery: {
        cids: undefined,
        data: undefined,
        tjlxValue: undefined,
      },
      // 体检类型列表
      typeOptions: [
        { id: 0, text: '健康体检' },
        { id: 1, text: '职业体检' },
        { id: 2, text: '综合' },
      ],
      // 弹出层加载动画
      popLoading: false,
      // 弹出层临时数据
      popData: undefined,
      // 性别列表
      forMaleOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      //数组参数
      comboAndItemVoList: [],
      //添加参数
      addList: [],
      // 检验标示列表
      examitemOptions: [
        { id: 1, text: '<' },
        { id: 2, text: '>' },
        { id: 3, text: '<>' },
      ],
      // 检查结果类型
      valueTypeOptions: [
        { id: 1, text: '数值型' },
        { id: 2, text: '字符型' },
        { id: 3, text: '枚举型' },
      ],
      //删除用的数组中
      indexs: [],
      //分中心数据表
      fzxData: [],
      // 表格列表
      tableList: [],
      // 非多个禁用
      multiple: true,
      // 选中数组
      ids: [],
      // 表单参数
      form: {
        tjtcmc: undefined,
        tjtcsrm: undefined,
        tjtcjc: undefined,
        fzxid: [],
        fzx: undefined,
        tcysjg: undefined,
        tczk: undefined,
        zhjg: undefined,
        syxb: undefined,
        sfyhtc: undefined,
        tjlx: 0,
        jhys: undefined,
        zytjlb: undefined,
        nlz: 0,
        nld: 100,
        sort: undefined,
        pinganId: undefined,
        appTypeId: undefined,
        bz: undefined,
      },
      // 分中心
      initFzx: [],
      priceTotal: 0,
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        tjtcmc: [{ required: true, message: '体检套餐名称不能为空', trigger: 'change' }],
        tjtcsrm: [{ required: true, message: '体检套餐输入码不能为空', trigger: 'change' }],
        tjtcjc: [{ required: true, message: '体检套餐简称不能为空', trigger: 'change' }],
        fzxid: [{ required: true, message: '分中心不能为空', trigger: 'change' }],
        tcysjg: [{ required: true, message: '套餐原始价格不能为空', trigger: 'change' }],
        zhjg: [{ required: true, message: '优惠价不能为空', trigger: 'change' }],
        tjlx: [{ required: true, message: '体检类型不能为空', trigger: 'change' }],
        nlz: [
          { required: true, message: '年龄不能为空', trigger: 'change' },
          { validator: checkAge, trigger: 'blur' },
        ],
        nld: [
          { required: true, message: '年龄不能为空', trigger: 'change' },
          { validator: checkAge, trigger: 'blur' },
        ],
      },
      _index: 0,
      // 表单id
      formId: undefined,
    }
  },
  methods: {
    // 搜索选择器返回值
    change1(value) {
      this.form.fzxid = []
      this.form.fzx = []
      value.forEach((el) => {
        this.form.fzxid.push(el.branchId)
        this.form.fzx.push(el.fzx)
      })
    },
    // 搜索选择器返回值
    change2(value) {
      this.form.jhys = value.id || undefined
    },
    // 输入码选择
    divisionIdChange(value) {
      this.form.divisionId = value
    },
    // 搜索选择
    searchChange(value) {
      this.form.devicetypePositionCheckitem = value
    },
    // 添加
    handleAdd() {
      this.reset()
      this.form.fzxid = []
      this.form.fzx = []
      this.form.id = null
      fzxData().then((response) => {
        this.fzxData = response.data.records
      })
      this.title = '添加检查项目'
      this.open = true
    },
    // 编辑
    handleUpdate(row, ids) {
      fzxData().then((response) => {
        this.fzxData = response.data.records
      })
      this.open = true
      this.popData = undefined
      const id = row.id || ids
      this.formId = id
      this.title = '编辑'
      this.popLoading = true
      this.form.fzxid = []
      this.form.fzx = []
      getCreatecombo(id).then((response) => {
        this.popData = JSON.parse(JSON.stringify(response.data))
        this.popLoading = false
        this.form = response.data
        this.form.fzxid = this.form.fzxid.split(',')
        this.form.fzx = this.form.fzx.split(',')
        this.initFzx = JSON.parse(JSON.stringify(this.form.fzx))
        this.itemQuery.cids = this.form.id
        this.itemQuery.data = this.form.syxb
        this.itemQuery.tjlxValue = this.form.tjlx
      })
      const data = {
        cid: id[0],
      }
      getDetailItem(data).then((response) => {
        this.tableList = response.data
        for (var i in this.tableList) {
          this.tableList[i].index = i
        }
        // this.getPrice()
      })
    },
    // 增加行
    handleAddRow() {
      if (this.tableList.length && !this.tableList[this.tableList.length - 1].sfxmid) {
        // this.$refs[`sfxmmc${this.tableList.length - 1}`].setFocus()
        this.$refs.tableList.bodyWrapper.scrollTop = this.$refs.tableList.bodyWrapper.scrollHeight
        return
      }
      var list = {
        sfxmmc: '',
        sfxmsrm: '',
        jcyy: '',
        jg: '',
        bz: '',
        ssks: '',
        tjlx: '',
        xb: '',
      }
      list._state = 'added'
      this.tableList.push(list)
      this.addList.push(list)
      for (var i in this.tableList) {
        this.tableList[i].index = i
      }
      this.$nextTick(() => {
        this.$refs.tableList.bodyWrapper.scrollTop = this.$refs.tableList.bodyWrapper.scrollHeight
      })
    },
    // 删除选中
    handleDeleteRow() {
      this.indexs.forEach((val) => {
        this.tableList.forEach((item, i) => {
          if (item.index == val) {
            this.tableList[i]._state = 'removed'
            this.comboAndItemVoList.push(this.tableList[i])
            this.$delete(this.tableList, i)
          }
        })
      })
      for (var i in this.tableList) {
        this.tableList[i].index = i
      }
      this.getPrice()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.indexs = selection.map((item) => item.index)
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 表单重置
    reset(type) {
      this.initFzx = []
      if (this.formId && type == '1') {
        this.handleUpdate({}, this.formId)
      } else {
        this.form = {
          tjtcmc: undefined,
          tjtcsrm: undefined,
          tjtcjc: undefined,
          fzxid: [],
          fzx: undefined,
          tcysjg: undefined,
          tczk: undefined,
          zhjg: undefined,
          syxb: undefined,
          sfyhtc: undefined,
          tjlx: 0,
          jhys: undefined,
          zytjlb: undefined,
          nlz: 0,
          nld: 100,
          sort: undefined,
          pinganId: undefined,
          appTypeId: undefined,
          bz: undefined,
        }
        this.$nextTick(() => {
          if (this.$refs.select2) this.$refs.select2.initData()
        })
        this.formId = undefined
        this.tableList = []
        this.comboAndItemVoList = []
        this.popData = undefined
        this.resetForm('form')
      }
    },
    // 打印项目分类名称改变
    nameChange(value) {
      this.form.inputCode = pinyin(value)
    },
    // 打印项目分类名称改变
    nameChange1(value) {
      this.form.tjtcsrm = pinyin(value)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
      this.popData = undefined
    },
    //折扣变化的时候
    handleChange() {
      if (this.form.tcysjg && this.form.tczk) {
        this.form.zhjg = ((Number(this.form.tcysjg) * Number(this.form.tczk)) / 10).toFixed(2)
      }
    },
    //优惠价发生变化
    priceChange() {
      if (this.form.zhjg && this.form.tcysjg) {
        this.form.tczk = ((Number(this.form.zhjg) / Number(this.form.tcysjg)) * 10).toFixed(2)
      }
    },
    //获取项目总价
    getPrice() {
      let priceTotal = 0
      for (var i in this.tableList) {
        if (this.tableList[i].jg) {
          priceTotal += Number(this.tableList[i].jg)
        }
      }
      this.$set(this.form, 'tcysjg', priceTotal ? priceTotal.toFixed(2) : '')
      if (this.form.tczk) {
        this.form.zhjg = Number((priceTotal * this.form.tczk) / 10).toFixed(2)
      }
    },
    // 选择收费项目选项
    labTypeChange(value, index) {
      let sfxmid = this.tableList.map((item) => item.sfxmid)
      if (sfxmid.includes(value.id)) {
        this.$alert(value.sfxmmc + `项目已存在,请重新选择`, '提示')
        this.$nextTick(() => {
          this.$refs[`sfxmmc${this.tableList.length - 1}`].setFocus()
        })
        return
      }
      //更新项目内容
      this.changeData(value, index)
      this.getPrice()
      this.handleAddRow()
    },
    ///赋值方法
    changeData(value, index) {
      this.tableList[index].sfxmmc = value.sfxmmc
      this.tableList[index].sfxmsrm = value.sfxmsrm
      this.tableList[index].jcyy = value.jcyy
      this.tableList[index].jg = value.jg
      this.tableList[index].bz = value.bz
      this.tableList[index].ssks = value.ssks
      this.tableList[index].tjlx = value.tjlx
      // this.tableList[index].id='1654692124500955136',
      this.tableList[index].xb = value.xb
      this.tableList[index].sfxmid = value.id
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let detailData = JSON.parse(JSON.stringify(this.form))
          detailData.comboAndItemVoList = JSON.parse(JSON.stringify(this.tableList))
          for (let i = detailData.comboAndItemVoList.length - 1; i >= 0; i--) {
            if (!detailData.comboAndItemVoList[i].sfxmid) {
              this.$delete(detailData.comboAndItemVoList, i)
            }
          }
          //处理删除的数组
          for (var i in this.comboAndItemVoList) {
            detailData.comboAndItemVoList.push(this.comboAndItemVoList[i])
          }
          ///处理所有数组
          for (var i in detailData.comboAndItemVoList) {
            if (detailData.comboAndItemVoList[i]._state) {
            } else {
              detailData.comboAndItemVoList[i]._state = 'modified'
            }
          }

          if (Array.isArray(detailData.fzxid)) {
            detailData.fzxid = detailData.fzxid.join(',')
          }
          if (Array.isArray(detailData.fzx)) {
            detailData.fzx = detailData.fzx.join(',')
          }
          if (this.form.id != null) {
            saveDetailData(detailData).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.$emit('getList')
            })
          } else {
            saveDetailData(detailData).then((response) => {
              this.$modal.msgSuccess('添加成功')
              this.open = false
              this.$emit('getList')
            })
          }
        }
      })
    },
  },
}
</script>
<style lang="scss">
.add-items {
  .el-form-item {
    margin-bottom: 20px;
  }

  .add-table {
    border: 1px solid #d4d6d9;

    .table-btn {
      padding: 16px 20px;
    }

    .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell {
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
</style>
