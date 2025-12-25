<!-- 外送结果上传 开发人：麦沃德科技暴雨/矢北 -->
<template>
  <div class="app-container flex-direction-column add-delivery">
    <!-- 添加或修改对话框 -->
    <!-- <el-dialog :title="title" class="add-delivery" :visible.sync="open" :close-on-press-escape="false" :close-on-click-modal="false" width="90%" append-to-body> -->
    <el-form ref="form" :model="queryParams" :inline="true" hide-required-asterisk>
      <el-form-item label="姓名" prop="patientname">
        <el-input v-model="queryParams.patientname" placeholder="请输入姓名" :readonly="onlyCheck" :clearable="!onlyCheck" style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="外送时间" v-if="!onlyCheck">
        <el-date-picker v-model="queryParams.date" style="width: 320px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" :readonly="onlyCheck" :clearable="!onlyCheck" style="width: 180px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item v-if="!onlyCheck">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <div class="delivery-top">
      <div class="delivery-top-column">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8" v-if="!onlyCheck">
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAddRow">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" :disabled="multiple" icon="el-icon-edit-outline" @click="handleDeleteRow">删除</el-button>
          </el-col>
        </el-row>
        <!--表格-->
        <div class="table-box">
          <el-table border :data="examList" ref="examList" height="100%" size="mini" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <el-table-column type="selection" align="center" />
            <el-table-column label="序列" type="index" width="65" align="center" />
            <el-table-column label="收费项目" align="center" prop="itemName" show-overflow-tooltip>
              <template slot-scope="scope">
                <input-select
                  ref="inputSelect1"
                  :selectData="selectData1"
                  :queryParams="selectParams"
                  selectSize="mini"
                  :currentIndex="scope.row.index"
                  selectWidth="100%"
                  v-model="examList.itemName"
                  :initialValue="scope.row.itemName"
                  :isTrim="true"
                  @change="DataChange1($event, scope.$index)"
                  v-if="!onlyCheck"
                ></input-select>
                <!-- <el-input v-model="" size="mini" style="width: 100%" readonly></el-input> -->
                <span v-else>{{ scope.row.itemName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="科室名称" prop="ksName" align="center" show-overflow-tooltip />
            <el-table-column label="状态" prop="status" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-tag size="mini" type="success" v-if="scope.row.status == 1">已处理</el-tag>
                <el-tag size="mini" type="danger" v-else>未处理</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="delivery-top-column">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8" v-if="!onlyCheck">
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-upload2" @click="handleUpload">上传</el-button>
          </el-col>
        </el-row>
        <div class="image-box">
          <div v-for="(src, index) in imgUrlFull" :key="index" style="position: relative">
            <el-image class="img" fit="contain" :src="src" :preview-src-list="imgUrlFull"></el-image>
            <i v-if="!onlyCheck" class="el-icon-error" style="color: red; font-size: 20px; position: absolute; top: 4px; right: 4px; cursor: pointer" @click="handleDeletePic(src)"></i>
          </div>
        </div>
      </div>
    </div>
    <div class="delivery-bottom">
      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb8" v-if="!onlyCheck">
        <el-col :span="1.5">
          <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAddDownRow" v-hasPermi="['funcdept:deliveryManage:deliveryUpload:add']">新增 </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain size="mini" :disabled="multiple2" icon="el-icon-edit-outline" @click="handleDeleteDownRow" v-hasPermi="['funcdept:deliveryManage:deliveryUpload:edit']">删除 </el-button>
        </el-col>
      </el-row>
      <!--表格-->
      <div class="table-box">
        <el-table ref="examList2" border :data="examList2" size="mini" height="100%" stripe @selection-change="handleDownSelectionChange" @row-click="rowClick1">
          <el-table-column type="selection" align="center" />
          <el-table-column label="序列" type="index" width="65" align="center" />
          <el-table-column label="收费项目" prop="idFee" align="center">
            <template slot-scope="scope">
              <input-select v-if="!onlyCheck" ref="inputSelect2" :selectData="selectData2" selectSize="mini" :queryParams="selectParams" selectWidth="100%" :currentIndex="scope.row.index" :initialValue="scope.row.idFee" @change="DataChange2($event, scope.$index)"></input-select>
              <span v-else>{{ scope.row.idFee }}</span>
            </template>
          </el-table-column>
          <el-table-column label="检查项目" prop="Check" align="center" show-overflow-tooltip />
          <el-table-column label="结果" prop="resultHand" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-input v-if="!onlyCheck" v-model="scope.row.resultHand" style="width: 100%" size="mini"></el-input>
              <span v-else>{{ scope.row.resultHand }}</span>
            </template>
          </el-table-column>
          <el-table-column label="提示" prop="ts" align="center" show-overflow-tooltip />
          <el-table-column label="参考低值" prop="ckdz" align="center" show-overflow-tooltip />
          <el-table-column label="参考高值" prop="ckgx" align="center" show-overflow-tooltip />
        </el-table>
      </div>
    </div>
    <div class="add-delivery-footer" v-if="!onlyCheck">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
      <el-button @click="cancel">关 闭</el-button>
    </div>

    <!-- ********************查询弹窗页面******************** -->
    <el-dialog title="外送登记信息" :visible.sync="diaOpen" width="80%" :close-on-click-modal="false" append-to-body>
      <el-form :model="queryParams" :inline="true" label-width="80px" hide-required-asterisk>
        <el-form-item label="体检号" prop="patientcode">
          <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item label="姓名" prop="patientname">
          <el-input v-model="queryParams.patientname" placeholder="请输入姓名" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item label="外送时间">
          <el-date-picker v-model="queryParams.date" style="width: 320px" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" @click="diaHandleQuery">搜索</el-button>
          <el-button type="primary" size="mini" @click="diaReset" plain>重置</el-button>
        </el-form-item>
      </el-form>
      <!--  表格部分 -->
      <div class="table-box" style="height: 500px">
        <el-table ref="diaTableList" border :data="diaTableList" height="90%" @selection-change="diaHandleSelectionChange" @row-click="rowClick2" @row-dblclick="rowDbclick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column prop="patientcode" label="体检号" align="center" />
          <el-table-column prop="xm" label="姓名" align="center" />
          <el-table-column prop="idsex" label="性别" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.idsex == 0">男</el-tag>
              <el-tag v-else>女</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="sendPeople" label="承送人" align="center" />
          <el-table-column prop="wsjg" label="外送机构" align="center" />
          <el-table-column prop="sendDate" label="外送时间" align="center" />
        </el-table>
        <pagination v-show="diaTotal > 0" :total="diaTotal" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="diaGetList" />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="diaSubmitForm">保存</el-button>
        <el-button @click="diaCancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 上传图片对话框 -->
    <el-dialog title="上传图片" :visible.sync="uploadOpen" width="500px" append-to-body :close-on-click-modal="false" :destroy-on-close="true">
      <el-form :inline="true" v-loading="uploadLoading" element-loading-text="上传中" label-position="top">
        <el-form-item label="选择文件" style="width: 100%">
          <image-upload ref="uploadImage" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></image-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="uploadSubmit()">上 传</el-button>
        <el-button type="primary" plain @click="uploadReset()">重 置</el-button>
        <el-button @click="uploadCancel()">取 消</el-button>
      </div>
    </el-dialog>
    <!-- </el-dialog> -->
  </div>
</template>
<script>
import { getMainListData, getPictureData, getEditData, getDepart, getItemName, saveDataList, getUploadPic, recordMatch, getPictureWsxmData } from '@/api/funcdept/delivery_manage/delivery_upload'
export default {
  name: 'Delivery_upload_edit',
  data() {
    return {
      selectData1: {
        placeholder: '',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        url: '/outside/sendGovern/getPictureWsxmData', //请求连接
        bindValue: '',
        firstName: 'inputCode',
        secondName: 'xmmc',
      },
      selectData2: {
        placeholder: '',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        url: '/outside/sendGovern/getWsxmData', //请求连接
        bindValue: '',
        firstName: 'inputCode',
        secondName: 'xmmc',
      },
      //收费项目列表列表
      itemName: [],
      diaTableList: [],
      diaOpen: false,
      // 遮罩层
      loading: true,
      selection: [],
      //中部分左半表格
      examList: [],
      examListTemp: [],
      readonly: true,
      //下部分表格
      examList2: [],
      examList2Temp: [],
      //弹窗总条数
      diaTotal: 0,
      // 总条数
      total: 0,
      // 组件参数
      selectParams: {
        patientcode: undefined,
      },
      //弹窗数据
      diaList: [],
      // 非单选禁用
      single: true,
      // 非多选禁用
      multiple: true,
      multiple2: true,
      // 表单参数
      form: {},
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      //右侧图片路径
      addImg: [],
      // 保存图片参数
      saveItemName: [],
      // 上传参数
      saveParams: {
        formData: { id: undefined },
        patientcode: undefined,
        addImg: '',
        delImg: '',
        //下半部分表格数据
        griddata: [],
        //上半部分表格
        itemGriddata: [],
      },
      //查询参数
      tableQuery: {
        patientcode: undefined,
      },
      tableQueryDown: {},
      // 科室的查询
      departQuery: {
        idChargeFee: undefined,
        patientcode: undefined,
        itemId: undefined,
      },
      //弹窗表格操作
      diaIds: [],
      //左半部分选中的行的数据
      selectLeft: [],
      imgUp: [],
      // 下部分选中行的id
      downIds: [],
      selectBelow: [],
      leftLength: undefined,
      downLength: undefined,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        patientcode: undefined,
        patientname: undefined,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
      },
      // 数据id
      outId: undefined,
      // 打开弹窗
      uploadOpen: false,
      // 上传组件参数
      uploadData: {
        url: '/outside/sendGovern/upload', //文件上传地址
        multiple: true, //是否可以上传多个
        data: {}, //上传时附带的额外参数
      },
      // 加载
      uploadLoading: false,
      //图片路径
      imgUrl: [],
      // 完整路径
      imgUrlFull: [],
      // 上传的图片列表
      addPic: [],
      // 拼接好id的
      addPic2: [],
      // 删除的图片列表
      delPic: [],
      // 图片前缀
      imgPath: this.$getCookie('imgPath'),
      // 表格索引
      tableIndex: 0,
      // 下表格索引
      tableIndex2: 0,
      // 提交防抖
      submitTimer: false,
      // 仅查看
      onlyCheck: false,
    }
  },
  created() {
    if (this.$route.query.queryParams) {
      let query = JSON.parse(this.$route.query.queryParams)
      let id = this.$route.query.id
      this.onlyCheck = this.$route.query.onlyCheck || false
      this.tableQuery = query
      this.queryParams.patientcode = query.patientcode
      this.queryParams.patientname = query.patientname
      this.selectParams.patientcode = query.patientcode
      this.departQuery.patientcode = query.patientcode
      this.saveParams.patientcode = query.patientcode
      this.outId = id
      this.getDetailsData()
    }
  },
  methods: {
    //弹窗搜索
    diaHandleQuery() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.queryParams.current = 1
      this.diaGetList()
    },
    // 搜索
    handleQuery() {
      if (this.queryParams.patientcode) {
        this.tableQuery.patientcode = this.queryParams.patientcode
        this.queryParams.patientCode = this.queryParams.patientcode
        recordMatch(this.queryParams).then(({ data }) => {
          if (data.retu == -1) {
            this.$alert('	该体检者没有外送项目，请检查输入的是否正确', '提示')
          } else if (data.retu == 0) {
            this.queryParams.current = 1
            this.diaOpen = true
            this.getList()
          } else {
            this.queryParams.patientname = data.retu[0].patientname
            this.tableQuery.patientname = data.retu[0].patientname
            this.queryParams.patientcode = data.retu[0].patientcode
            this.queryParams.patientCode = data.retu[0].patientcode
            this.tableQuery.patientcode = data.retu[0].patientcode
            this.tableQuery.patientCode = data.retu[0].patientcode
            this.outId = data.retu[0].id
            this.getDetailsData()
          }
        })
      } else {
        this.queryParams.current = 1
        this.diaOpen = true
        this.getList()
      }
    },
    // 添加
    handleAdd(query, id) {
      this.reset()
      this.open = true
      this.title = '外送结果添加'
      if (query) {
        this.title = '外送结果编辑'
        this.tableQuery = query
        this.queryParams.patientcode = query.patientcode
        this.queryParams.patientname = query.patientname
        this.selectParams.patientcode = query.patientcode
        this.departQuery.patientcode = query.patientcode
        this.saveParams.patientcode = query.patientcode
        this.outId = id
        this.getDetailsData()
      }
    },
    getDetailsData() {
      this.loading = true
      //获取左中部分表格数据
      getPictureData(this.tableQuery)
        .then((response) => {
          response.data.forEach((el) => {
            el.tableIndex = this.tableIndex++
          })
          this.examList = response.data
          this.examListTemp = JSON.parse(JSON.stringify(response.data))
          this.loading = false
          this.leftLength = this.examList.length
          this.selectParams.patientcode = this.tableQuery.patientcode
          // if (this.examList.length == 0) {
          //   getPictureWsxmData({
          //     patientcode: this.tableQuery.patientcode,
          //     size: 100,
          //   }).then(({ data }) => {
          //     if (data.records && data.records.length) {
          //       let ids = this.examList.map((item) => item.idCharge)
          //       data.records.forEach((el) => {
          //         if (!ids.includes(el.idItemName)) {
          //           this.examList.push({
          //             idCharge: el.idItemName,
          //             itemName: el.xmmc,
          //             ksName: el.ksName,
          //             state: 'added',
          //             tableIndex: this.tableIndex++,
          //           })
          //         }
          //       })
          //     }
          //   })
          // }
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
      //获取下部分表格数据
      getEditData(this.tableQuery)
        .then((response) => {
          this.examList2 = response.data
          this.examList2Temp = JSON.parse(JSON.stringify(response.data))
          this.loading = false
          this.downLength = this.examList2.length
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
      // 获取对应图片
      this.delPic = []
      this.addPic2 = []
      if (this.outId) {
        this.imgUrlFull = []
        getUploadPic({
          id: this.outId,
        })
          .then(({ data }) => {
            if (data) {
              this.imgUrl = data
              data.forEach((el) => {
                this.imgUrlFull.push(this.imgPath + el)
              })
            }
          })
          .catch((error) => {
            console.error(error)
            this.loading = false
          })
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectLeft = selection.map((item) => item)
      this.imgUp = selection.map((item) => item.itemName)
      this.single = selection.length != 1
      this.multiple = !selection.length
      this.saveItemName = selection.map((item) => item.idCharge)
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.examList.clearSelection()
      this.$refs.examList.toggleRowSelection(row)
    },
    // 多选框选中数据
    handleDownSelectionChange(selection) {
      this.downIds = selection.map((item) => item.index)
      this.selectBelow = selection.map((item) => item)
      this.single = selection.length != 1
      this.multiple2 = !selection.length
    },
    // 表格单击事件
    rowClick1(row) {
      this.$refs.examList2.clearSelection()
      this.$refs.examList2.toggleRowSelection(row)
    },
    // 搜索框内容变化
    DataChange1(value, index) {
      this.queryParams.itemName = value.xmmc
      this.departQuery.itemId = value.id
      getDepart(this.departQuery).then((response) => {
        let error = ''
        this.examList.forEach((el) => {
          if (el.idCharge == response.data.idItemName) {
            error = `收费项目${value.xmmc}已存在,请重新选择`
          }
        })
        if (error) {
          this.$alert(error)
          this.$refs.inputSelect1.initData()
          return
        }
        this.$set(this.examList[index], 'itemName', value.xmmc)
        this.$set(this.examList[index], 'ksName', response.data.ksName)
        this.$set(this.examList[index], 'idCharge', response.data.idItemName)
      })
    },
    // 搜索框内容变化
    DataChange2(value, index) {
      this.departQuery.idChargeFee = value.id
      getItemName(this.departQuery).then((response) => {
        let error = ''
        this.examList2.forEach((el) => {
          if (el.id == value.id) {
            error = `收费项目${value.xmmc}已存在,请重新选择`
          }
        })
        if (error) {
          this.$alert(error)
          this.$refs.inputSelect2.initData()
          return
        }
        this.$set(this.examList2[index], 'Check', response.data[0].bcheck)
        this.$set(this.examList2[index], 'idCheck', response.data[0].idCheck)
        this.$set(this.examList2[index], 'idFee', response.data[0].idFee)
        this.$set(this.examList2[index], 'id', response.data[0].idCharge)
        this.$set(this.examList2[index], 'ckdz', response.data[0].ckdz)
        this.$set(this.examList2[index], 'ckgx', response.data[0].ckgx)
      })
    },
    diaReset() {
      this.queryParams.date = undefined
      this.queryParams.startTime = undefined
      this.queryParams.endTime = undefined
      this.queryParams.patientcode = undefined
      this.queryParams.patientCode = undefined
      this.queryParams.patientname = undefined
      this.diaGetList()
    },

    // 表单重置
    reset() {
      this.tableQuery = {
        patientcode: undefined,
      }
      this.imgUrl = []
      this.imgUrlFull = []
      this.examList = []
      this.examList2 = []
      this.selectList = []
      this.total = 0
      this.outId = ''
      this.saveParams = {
        formData: { id: undefined },
        patientcode: undefined,
        addImg: '',
        delImg: '',
        //下半部分表格数据
        griddata: [],
        //上半部分表格
        itemGriddata: [],
      }
      this.resetForm('queryExam')
      this.queryParams = {
        patientcode: undefined,
        date: undefined,
        startTime: undefined,
        endTime: undefined,
        patientname: undefined,
        current: 1,
        size: 10,
      }
      this.resetForm('form')
    },
    diaGetList() {
      getMainListData(this.queryParams).then((response) => {
        this.diaTableList = response.data.records
        if (response.data.records[0]) {
          this.saveParams.formData.id = response.data.records[0].id
        }
        this.diaTotal = response.data.total
      })
    },
    getList() {
      if (this.queryParams.date) {
        this.queryParams.startTime = this.queryParams.date[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.date[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = undefined
        this.queryParams.endTime = undefined
      }
      this.loading = true
      getMainListData(this.queryParams).then((response) => {
        this.diaTableList = response.data.records
        if (response.data.records[0]) {
          this.saveParams.formData.id = response.data.records[0].id
        }
        this.diaTotal = response.data.total
        this.loading = false
      })
    },

    // 弹窗取消按钮
    diaCancel() {
      this.diaOpen = false
    },
    // 上传图片
    handleUpload() {
      if (this.imgUp[0]) {
        this.uploadOpen = true
      } else {
        this.$message({
          message: '请选择收费项目！',
          type: 'warning',
        })
      }
    },
    // 取消按钮
    cancel() {
      this.$tab.closePage()
    },
    diaHandleSelectionChange(selection) {
      this.diaIds = selection.map((item) => item.id)
      this.diaList = selection
      this.single = selection.length != 1
      // this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick2(row) {
      this.$refs.diaTableList.clearSelection()
      this.$refs.diaTableList.toggleRowSelection(row)
    },
    // 表格双击事件
    rowDbclick() {
      this.diaSubmitForm()
    },
    // 增加上中行
    handleAddRow() {
      let obj = {
        tableIndex: this.tableIndex++,
        itemName: '',
        ksName: '',
        state: 'added',
      }
      this.examList.push(obj)
    },
    // 删除选中
    handleDeleteRow() {
      this.selectLeft
        .map((item) => item.tableIndex)
        .forEach((el) => {
          for (let i = this.examList.length - 1; i >= 0; i--) {
            if (el == this.examList[i].tableIndex) {
              this.$delete(this.examList, i)
              break
            }
          }
        })
    },
    // 增加行
    handleAddDownRow() {
      // let index = 0
      // if (this.examList2.length > 0) {
      //   if (this.examList2[this.examList2.length - 1].index) {
      //     index = this.examList2[this.examList2.length - 1].index + 1
      //   } else {
      //     index = this.examList2.length + 1
      //   }
      // }
      let obj = {
        tableIndex2: this.tableIndex2++,
        state: 'added',
        ckgx: undefined,
        ckdz: undefined,
        ts: undefined,
        resultHand: undefined,
        Check: undefined,
        idCheck: undefined,
        idFee: undefined,
        id: undefined,
      }
      // this.saveParams.griddata.push(obj)
      this.examList2.push(obj)
    },
    // 删除下方选中
    handleDeleteDownRow() {
      this.selectBelow
        .map((item) => item.tableIndex)
        .forEach((el) => {
          for (let i = this.examList2.length - 1; i >= 0; i--) {
            if (el == this.examList2[i].tableIndex) {
              this.$delete(this.examList2, i)
              break
            }
          }
        })
    },
    // 上传文件成功
    uploadFinish(value, res) {
      if (value == 1) {
        res.data.urlList.forEach((el) => {
          this.imgUrlFull.push(this.imgPath + el)
          this.addPic.push(el)
          this.addPic2.push(el + '@chargeIds:' + this.saveItemName.join(','))
        })
        this.imgUrl = [...this.imgUrl, ...this.addPic]
        this.$modal.msgSuccess('上传成功', '提醒')
        this.uploadOpen = false
      }
      this.uploadLoading = false
    },
    // 上传重置
    uploadReset() {
      this.$refs.uploadImage.resetUpload()
    },
    // 上传提交
    uploadSubmit() {
      var msg = this.$refs.uploadImage.isUpload()
      if (msg === true) {
        this.uploadLoading = true
        this.$refs.uploadImage.handelUpload()
      } else {
        this.$modal.msgWarning(msg, '提醒')
      }
    },
    // 删除上传的图片
    handleDeletePic(src) {
      this.$confirm('是否删除当前图片?', '提示')
        .then(() => {
          this.imgUrlFull.forEach((el, index) => {
            if (el.includes(src)) {
              this.$delete(this.imgUrlFull, index)
            }
          })
          let isNew = false
          this.addPic.forEach((el, index) => {
            if (src.includes(el)) {
              this.$delete(this.addPic, index)
              this.$delete(this.addPic2, index)
              isNew = true
            }
          })
          if (!isNew) {
            this.imgUrl.forEach((el) => {
              if (src.includes(el)) {
                this.delPic.push(el)
              }
            })
          }
        })
        .catch()
    },
    // 取消
    uploadCancel() {
      this.uploadOpen = false
    },
    // 提交按钮
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.submitTimer) {
            return
          }
          this.submitTimer = true
          setTimeout(() => {
            this.submitTimer = false
          }, 1000)
          var data = ''
          data = this.addPic2.join('@@@')
          let itemGriddata = []
          let idList = this.examList.map((item) => item.id)
          this.examListTemp.forEach((el) => {
            if (idList.includes(el.id)) {
              el.state = 'modified'
            } else {
              el.state = 'removed'
            }
            itemGriddata.push(el)
          })
          this.examList.forEach((el) => {
            if (el.state == 'added') {
              itemGriddata.push(el)
            }
          })
          let griddata = []
          let idList2 = this.examList2.map((item) => item.id)
          this.examList2Temp.forEach((el) => {
            if (idList2.includes(el.id)) {
              el.state = 'modified'
            } else {
              el.state = 'removed'
            }
            griddata.push(el)
          })
          this.examList2.forEach((el) => {
            if (el.state == 'added') {
              griddata.push(el)
            }
          })
          this.saveParams.itemGriddata = itemGriddata
          this.saveParams.griddata = griddata
          this.saveParams.addImg = data
          this.saveParams.delImg = this.delPic.join(',')
          this.saveParams.patientcode = this.queryParams.patientcode
          saveDataList(this.saveParams).then((response) => {
            this.$modal.msgSuccess('提交成功')
            this.open = false
            this.handleQuery()
            this.$emit('getList')
          })
        }
      })
    },
    // 提交按钮
    diaSubmitForm() {
      if (this.diaIds.length < 1) {
        this.$message({
          message: '请至少选择一条记录',
          type: 'warning',
        })
      } else if (this.diaIds.length > 1) {
        this.$message({
          message: '只能选择一条记录添加',
        })
      } else {
        this.queryParams = this.diaList[0]
        this.queryParams.patientname = this.diaList[0].xm
        this.selectParams.patientcode = this.diaList[0].patientcode
        this.departQuery.patientcode = this.diaList[0].patientcode
        this.tableQuery.patientcode = this.diaList[0].patientcode
        this.saveParams.patientcode = this.diaList[0].patientcode
        this.saveParams.formData.id = this.diaList[0].id
        this.getDetailsData()
        this.diaOpen = false
      }
    },
  },
}
</script>
<style lang="scss">
.add-delivery {
  .delivery-top {
    display: flex;
    height: calc(50% - 35px);
    min-height: 200px;

    .delivery-top-column {
      border: 1px solid #c4c4c4;
      width: 50%;
      display: flex;
      flex-direction: column;
      padding-top: 12px;

      .el-row {
        padding: 0 12px;
      }

      .table-box {
        flex: 1;
        margin: -1px;
        overflow: hidden;
      }

      .image-box {
        display: flex;
        flex-wrap: wrap;
        padding: 0 6px;
        overflow: auto;

        .img {
          width: 100px;
          height: 100px;
          margin: 6px;
        }
      }

      &:last-child {
        margin-left: 10px;
      }
    }
  }

  .delivery-bottom {
    height: calc(50% - 35px);
    min-height: 200px;
    border: 1px solid #c4c4c4;
    margin-top: 10px;
    padding-top: 12px;
    display: flex;
    flex-direction: column;

    .el-row {
      padding: 0 12px;
    }

    .table-box {
      flex: 1;
      margin: -1px;
      overflow: hidden;
    }
  }
  .add-delivery-footer {
    margin-top: 8px;
    text-align: center;
  }
}
</style>
