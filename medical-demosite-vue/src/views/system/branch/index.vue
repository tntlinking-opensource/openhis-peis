<!-- 分中心管理  开发人：麦沃德科技半夏 -->
<template>
  <div class="app-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" class="no-margin-bottom">
      <el-form-item label="分中心名字" prop="fzx">
        <el-input v-model="queryParams.fzx" placeholder="请输入分中心名字" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="简码" prop="jm">
        <el-input v-model="queryParams.jm" placeholder="请输入简码" clearable style="width: 230px"
          @keyup.enter.native="handleQuery" />
                  </el-form-item> -->
      <el-form-item label="输入码" prop="srm">
        <el-input v-model="queryParams.srm" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="是否默认" prop="isDefault">
        <el-select v-model="queryParams.isDefault" placeholder="请选择是否默认" clearable style="width: 230px">
          <el-option label="是" :value="1" />
          <el-option label="否" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:branch:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:branch:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleRemove" v-hasPermi="['system:branch:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-connection" size="mini" :disabled="single" @click="handleDefault" v-hasPermi="['system:branch:default']">默认</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-c-scale-to-original" size="mini" @click="handleSync" v-hasPermi="['system:branch:sync']">同步</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleDelete" v-hasPermi="['system:branch:delete']">删除体检号</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" @click="handleModifyItem" v-hasPermi="['system:branch:item:modify']">修改项目状态</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" @click="handleModifyHealth" v-hasPermi="['system:branch:health:modify']">修改健康体检状态</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" @click="handleModifyOccupation" v-hasPermi="['system:branch:occupation:modify']">修改职业体检状态</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" @click="handleModifyWork" v-hasPermi="['system:branch:workType:modify']">修改工种</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" @click="handleModifyDoctor" v-hasPermi="['system:branch:workType:modify']">修改开单医生</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-refresh" size="mini" @click="handleKingDeeupation" v-hasPermi="['system:branch:keingDee:modify']">金蝶分中心更新</el-button>
      </el-col> -->
    </el-row>
    <div class="table-box">
      <el-table ref="branchList" border v-loading="loading" :data="branchList" height="100%" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column label="分中心名字" align="center" prop="fzx" show-overflow-tooltip />
        <el-table-column label="简码" align="center" prop="jm" show-overflow-tooltip />
        <el-table-column label="输入码" align="center" prop="srm" show-overflow-tooltip />
        <el-table-column label="是否默认" align="center" prop="isDefault">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isDefault == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:branch:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.row)" v-hasPermi="['system:branch:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 添加或修改分中心维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1160px" class="sub-center" destroy-on-close append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="180px" :inline="true" hide-required-asterisk>
        <el-form-item label="分中心名字" prop="fzx">
          <el-input v-model="form.fzx" placeholder="请输入分中心名字" clearable style="width: 360px" @input="nameChange" />
        </el-form-item>
        <el-form-item label="简码" prop="jm">
          <el-input v-model="form.jm" placeholder="英文字母，长度不能超过两位" clearable style="width: 360px" maxlength="2" />
        </el-form-item>
        <el-form-item label="输入码" prop="srm">
          <el-input v-model="form.srm" placeholder="系统自动生成" readonly style="width: 360px" />
        </el-form-item>
        <el-form-item label="父级分中心" prop="pid">
          <el-select v-model="form.pid" placeholder="请选择父级分中心" clearable style="width: 360px">
            <el-option v-for="item in parentOptions" :key="item.id" :label="item.fzx" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否显示" prop="isShow">
          <el-select v-model="form.isShow" placeholder="请选择是否显示" clearable style="width: 360px">
            <el-option label="否" value="0" />
            <el-option label="是" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="机构类型" prop="branchType">
          <el-select v-model="form.branchType" placeholder="请选择机构类型" clearable style="width: 360px">
            <el-option v-for="item in typeOptions" :key="item.id" :label="item.text" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="支持在线支付" prop="fpayOnline">
          <el-select v-model="form.fpayOnline" placeholder="请选择是否支持在线支付" style="width: 360px">
            <el-option :key="1" label="是" :value="1" />
            <el-option :key="0" label="否" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入电话" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="区域信息">
          <el-select v-model="form.province" placeholder="请选择省" @change="provinceChange" style="width: 120px">
            <el-option v-for="options in provinceOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
          <el-select v-model="form.city" placeholder="请选择市" :disabled="!form.province" @change="cityChange" style="width: 120px">
            <el-option v-for="options in cityOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
          <el-select v-model="form.area" placeholder="请选择区" :disabled="!form.city" style="width: 120px">
            <el-option v-for="options in areaOptions" :key="options.zoneCode" :label="options.zoneName" :value="options.zoneCode" />
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入详细地址" clearable style="width: 360px">
            <el-button slot="append" icon="el-icon-map-location" @click="showMap"></el-button>
          </el-input>
        </el-form-item>
        <!-- <el-form-item label="预约周期（天）" prop="reservationPeriod">
          <el-input-number v-model="form.reservationPeriod" controls-position="right" :min="1" @blur="periodBlur" style="width: 360px; text-align: left" />
        </el-form-item>
        <el-form-item label="出号时间段（上午）" prop="selectTime">
          <el-time-picker is-range v-model="selectTime" value-format="HH:mm" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" placeholder="选择时间范围" style="width: 360px"> </el-time-picker>
        </el-form-item>
        <el-form-item label="出号时间段2（下午）" prop="selectTime2">
          <el-time-picker is-range v-model="selectTime2" value-format="HH:mm" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" placeholder="选择时间范围" style="width: 360px"> </el-time-picker>
        </el-form-item> -->
        <el-form-item label="是否开启预约" prop="needReservation">
          <el-select v-model="form.needReservation" placeholder="请选择是否开启预约" style="width: 360px">
            <el-option :key="1" label="启动" :value="1" />
            <el-option :key="0" label="关闭" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="机构门店ID" prop="hospitalSubId">
          <el-input v-model="form.hospitalSubId" placeholder="请输入机构门店ID" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="拼音简码" prop="pyjm">
          <el-input v-model="form.pyjm" placeholder="英文字母，长度不能超过两位" clearable style="width: 360px" maxlength="2" />
        </el-form-item>
        <el-form-item label="支持微信小程序" prop="isWechatApp">
          <el-select v-model="form.isWechatApp" placeholder="请选择是否支持微信小程序" style="width: 360px">
            <el-option :key="1" label="是" :value="1" />
            <el-option :key="0" label="否" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="微信小程序排队接口地址" prop="queueUrl">
          <el-input v-model="form.queueUrl" placeholder="请输入微信小程序排队接口地址" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="机构介绍" prop="introduce">
          <el-input v-model="form.introduce" placeholder="请输入机构介绍" type="textarea" :autosize="autosize" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="前台须知" prop="qtInfo">
          <el-input v-model="form.qtInfo" placeholder="请输入前台须知" type="textarea" :autosize="autosize" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="检前须知" prop="inspectInfo">
          <el-input v-model="form.inspectInfo" placeholder="请输入检前须知" type="textarea" :autosize="autosize" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="当前中心组织名" prop="centerorgname">
          <el-input v-model="form.centerorgname" placeholder="请输入当前中心组织名" type="textarea" :autosize="autosize" clearable style="width: 360px" />
        </el-form-item>
        <el-form-item label="缩略图" prop="upImgName" style="display: block">
          <div class="setimg">
            <image-upload id="upload-t" @submitFile="submitFileT" ref="uploadT" :uploadData="uploadDataT" :typeChoose="1" @uploadFinish="uploadFinishT" uploadWidth="100%" />
          </div>
        </el-form-item>
        <el-form-item label="图片" prop="upImg" style="display: block">
          <div class="setimg">
            <image-upload id="upload-p" @submitFile="submitFileP" ref="uploadP" :uploadData="uploadDataP" :typeChoose="1" @uploadFinish="uploadFinishP" uploadWidth="100%" />
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <!-- 百度地图位置选择 -->
      <address-select ref="addressSelect" @confirmMapAddress="confirmMapAddress" :appendToBody="true"></address-select>
    </el-dialog>
    <!-- 操作弹窗 -->
    <operate-items ref="operateItems"></operate-items>
  </div>
</template>

<script>
import addressSelect from '@/components/AddressSelect/index.vue'
import operateItems from './operate'
import pinyin from '@/utils/pinyin.js'
import { listBranch, getBranch, delBranch, addBranch, updateBranch, upgradeKingdeeOrganization, getRegionData } from '@/api/system/branch'
import { getCookie } from '@/utils/getCookie.js'

export default {
  name: 'Branch',
  dicts: ['sys_yes_no'],
  components: {
    addressSelect,
    operateItems,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 文本域自适应内容高度参数
      autosize: { minRows: 4, maxRows: 4 },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        fzx: undefined,
        jm: undefined,
        srm: undefined,
      },
      // 总条数
      total: 0,
      // 分中心维护表格数据
      branchList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {
        picture: '', //缩略图
        pics: '', //图片
      },
      selectTime: undefined,
      selectTime2: undefined,
      // 表单校验
      rules: {
        fzx: [{ required: true, message: '分中心名称不能为空', trigger: 'change' }],
        jm: [{ required: true, message: '简码不能为空', trigger: 'change' }],
        pyjm: [{ required: true, message: '拼音简码不能为空', trigger: 'change' }],
      },
      // 父级分中心
      parentOptions: [{ id: '-1', fzx: 'QDITHC健康管理中心' }],
      // 机构类型
      typeOptions: [
        { id: 1, text: '专业机构' },
        { id: 2, text: '公立三甲' },
        { id: 3, text: '公立医院' },
        { id: 4, text: '民营医院' },
        { id: 5, text: '养老中心' },
      ],
      // 省数据
      provinceOptions: [],
      // 市数据
      cityOptions: [],
      // 区数据
      areaOptions: [],
      upload: {
        title: '上传',
        openT: false,
        openP: false,
      },
      uploadDataT: {
        url: '/common/upload', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1,
        // fileType: ['xls', 'xlsx'],//文件类型
        data: {
          //上传时附带的额外参数
        },
        picture: '',
      },
      uploadDataP: {
        url: '/common/uploads', //文件上传地址
        multiple: true, //是否可以上传多个
        fileList: [],
        // fileType: ['xls', 'xlsx'],//文件类型
        data: {
          //上传时附带的额外参数
        },
        picture: '',
      },
      imgPath: getCookie('imgPath'),
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //确认缩略图
    submitFileT() {
      var msg = this.$refs.uploadT.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadT.handelUpload()
      } else {
        this.uploadFinishT(1)
      }
    },
    //接收缩略图返回地址
    uploadFinishT(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.upload.openT = false
      this.loading.close()
      this.loading = false
      this.form.picture = res.data
      return
    },
    //确认图片
    submitFileP() {
      var msg = this.$refs.uploadP.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadP.handelUpload()
      } else {
        this.uploadFinishP(1)
      }
    },
    //接收图片返回地址
    uploadFinishP(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }

      this.upload.openP = false
      this.loading.close()
      this.loading = false

      this.form.pics = res.data.urlList[0]
      return
    },
    //打开上传缩略图
    uploadThumbnail() {
      this.upload.openT = true
    },
    //打开上传图片
    uploadPicture() {
      this.upload.openP = true
    },
    /** 查询分中心维护列表 */
    getList() {
      this.loading = true
      listBranch(this.queryParams).then((response) => {
        this.branchList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    // 省改变
    provinceChange() {
      this.form.city = ''
      this.form.area = ''
      this.getRegion(2, this.form.province)
    },
    // 市改变
    cityChange() {
      this.form.area = ''
      this.getRegion(3, this.form.city)
    },
    // 获取省市区数据
    getRegion(level, zoneCode) {
      var query = {
        level,
        zoneCode,
      }
      getRegionData(query).then((res) => {
        if (level == 1) {
          this.provinceOptions = res.data
        } else if (level == 2) {
          this.cityOptions = res.data
        } else if (level == 3) {
          this.areaOptions = res.data
        }
      })
    },
    // 显示地图
    showMap() {
      this.$refs.addressSelect.show()
    },
    // 确认地图地址
    confirmMapAddress(addressInfo) {
      this.form.lng = addressInfo.lngBD
      this.form.lat = addressInfo.latBD
      this.form.lngGcj = addressInfo.lngGCJ
      this.form.latGcj = addressInfo.latGCJ
      this.form.address = addressInfo.address
    },
    // 预约周期
    periodBlur() {
      if (!this.form.reservationPeriod) {
        this.form.reservationPeriod = 7
      }
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
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 分中心名称改变
    nameChange(value) {
      this.form.srm = pinyin(value)
    },
    // 表单重置
    reset() {
      this.form = {
        fzx: undefined,
        jm: undefined,
        srm: undefined,
        pid: undefined,
        type: undefined,
        fpayOnline: 0,
        tel: undefined,
        province: undefined,
        city: undefined,
        area: undefined,
        lngBD: undefined,
        latBD: undefined,
        lngGCJ: undefined,
        latGCJ: undefined,
        address: undefined,
        reservationPeriod: 7,
        isWechatApp: 0,
        needReservation: 0,
      }
      this.selectTime = ['8:30', '10:30']
      this.selectTime2 = undefined
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.branchList.clearSelection()
      this.$refs.branchList.toggleRowSelection(row)
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.getRegion(1)
      this.title = '新增分中心'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.uploadDataP.fileList = []
      this.reset()
      const id = row.id || this.ids

      getBranch(id).then((response) => {
        this.form = response.data
        this.getRegion(1)
        if (this.form.province) {
          this.getRegion(2, this.form.province)
        }
        if (this.form.city) {
          this.getRegion(3, this.form.city)
        }
        this.selectTime = [response.data.startTime, response.data.endTime]
        this.selectTime2 = [response.data.startTime2, response.data.endTime2]
        this.open = true
        this.title = '编辑分中心'

        if (this.form.picture) {
          this.uploadDataT.picture = getCookie('imgPath') + this.form.picture
        }
        if (this.form.pics) {
          this.uploadDataP.picture = getCookie('imgPath') + this.form.pics
        }
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.selectTime) {
            this.form.startTime = this.selectTime[0]
            this.form.endTime = this.selectTime[1]
          } else {
            this.form.startTime = undefined
            this.form.endTime = undefined
          }
          if (this.selectTime2) {
            this.form.startTime2 = this.selectTime2[0]
            this.form.endTime2 = this.selectTime2[1]
          } else {
            this.form.startTime1 = undefined
            this.form.endTime1 = undefined
          }
          if (this.form.id != null) {
            updateBranch(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addBranch(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleRemove(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('您确定要删除该信息吗？')
        .then(function () {
          return delBranch(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'system/branch/export',
        {
          ...this.queryParams,
        },
        `branch_${new Date().getTime()}.xlsx`
      )
    },
    // 默认
    handleDefault() {},
    // 同步
    handleSync() {
      this.$modal
        .confirm('确定要同步吗？')
        .then(function () {
          return
        })
        .then(() => {
          this.$modal.msgSuccess('操作成功')
        })
        .catch(() => {})
    },
    // 删除体检号
    handleDelete() {
      this.$refs.operateItems.handleShow(1)
    },
    // 修改项目状态
    handleModifyItem() {
      this.$refs.operateItems.handleShow(2)
    },
    // 修改健康体检状态
    handleModifyHealth() {
      this.$refs.operateItems.handleShow(3)
    },
    // 修改职业体检状态
    handleModifyOccupation() {
      this.$refs.operateItems.handleShow(4)
    },
    // 修改体检者工种
    handleModifyWork() {
      this.$refs.operateItems.handleShow(5)
    },
    // 修改开单医生
    handleModifyDoctor() {
      this.$refs.operateItems.handleShow(6)
    },
    //金蝶分中心更新
    handleKingDeeupation() {
      upgradeKingdeeOrganization().then((res) => {
        this.$modal.msgSuccess(res.msg)
      })
    },
  },
}
</script>
<style lang="scss">
.sub-center {
  .el-input-number {
    .el-input__inner {
      text-align: left;
    }
  }

  .label-title {
    display: block;
    font-weight: 600;
    font-size: 14px;
    line-height: 20px;
    color: #333333;
    margin-bottom: 12px;
  }
}

.setimg {
  display: flex;
  flex-direction: column;
  align-items: center;

  img {
    width: 100px;
    height: 100px;
    background: rgba($color: #000000, $alpha: 0.3);
  }

  .btn {
    margin: 8px;
    width: 70px;
    height: 36px;
  }
}
</style>

<style scoped>
#upload-t /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
}

#upload-t /deep/ .el-upload-list {
  max-width: 80px;
}

#upload-p /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
}

#upload-p /deep/ .el-upload-list {
  max-width: 80px;
  display: flex;
}

#upload-p /deep/ .el-upload-list__item {
  margin: 0;
}
</style>
