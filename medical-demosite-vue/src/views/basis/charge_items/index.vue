<!-- 收费项目设置  开发人：麦沃德科技半夏/清风 -->
<template>
  <div class="app-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="70px" class="no-margin-bottom">
      <el-form-item label="输入码" prop="sfxmsrm">
        <el-input v-model="queryParams.sfxmsrm" placeholder="请输入输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="项目名称" prop="examfeeitemName">
        <el-input v-model="queryParams.examfeeitemName" placeholder="请输入收费项目名称" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="体检类型" prop="tjlx">
        <el-select v-model="queryParams.tjlx" placeholder="请选择体检类型" clearable style="width: 230px" @change="handleQuery">
          <el-option v-for="options in tjlxOptions" :key="options.id" :label="options.text" :value="options.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="forMale">
        <el-select v-model="queryParams.forMale" placeholder="请选择性别" clearable style="width: 230px" @change="handleQuery">
          <el-option label="男" :value="0" />
          <el-option label="女" :value="1" />
          <el-option label="通用" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="科室名称" prop="idDepart">
        <input-select :selectData="departData" selectWidth="230" @change="departChange"></input-select>
      </el-form-item>
      <el-form-item label="接口代码" prop="examfeeitemCode">
        <el-input v-model="queryParams.examfeeitemCode" placeholder="请输入接口代码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="标本类型" prop="idLabtype">
        <input-select :selectData="labTypeData" selectWidth="230" @change="labTypeChange"></input-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['basis:chargeItems:add']">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain size="mini" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['basis:chargeItems:edit']">编辑 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['basis:chargeItems:remove']">删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-upload2" :disabled="single" @click="handleUpload" v-hasPermi="['basis:chargeItems:upload']">上传图片 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-refresh" @click="handleRefresh" v-hasPermi="['basis:chargeItems:refresh']">更新 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-help" :disabled="multiple" @click="handleSetApp(1)" v-hasPermi="['basis:chargeItems:app']">APP项目 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-circle-close" :disabled="multiple" @click="handleSetApp(0)" v-hasPermi="['basis:chargeItems:app']">取消APP项目 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-lock" :disabled="multiple" @click="handleDisable(1)" v-hasPermi="['basis:chargeItems:disable']">禁用 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-unlock" :disabled="multiple" @click="handleDisable(0)" v-hasPermi="['basis:chargeItems:disable']">反禁用 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-upload2" @click="handleExport" v-hasPermi="['search:businessType:export']">导出Excel</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-download" @click="handImport" v-hasPermi="['search:businessType:export']">导入收费项目设置</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-download" @click="handImportOld" v-hasPermi="['search:businessType:export']">导入老系统收费项目</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="table-box">
      <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" width="55" align="center" />
        <el-table-column label="序列" fixed type="index" width="60" align="center" />
        <el-table-column label="收费项目名称" fixed prop="examfeeitemName" min-width="200" align="center" show-overflow-tooltip />
        <el-table-column label="输入码" fixed prop="sfxmsrm" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="接口代码" prop="examfeeitemCode" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="导引单分组" prop="dydfz" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="体检类型" prop="tjlx" min-width="140" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ tjlx(scope.row.tjlx) }}
          </template>
        </el-table-column>
        <el-table-column label="导引单打印" prop="dyddybs" min-width="120" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.dyddybs == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="所属科室名称" prop="departName" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="餐序" prop="cx" min-width="100" align="center">
          <template slot-scope="scope">
            <div v-for="item in cxOptions" :key="item.id">
              <span v-if="item.id == scope.row.cx">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="性别" prop="xb" min-width="100" align="center">
          <template slot-scope="scope">
            <div v-for="item in xbOptions" :key="item.id">
              <span v-if="item.id == scope.row.xb">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="价格" prop="unitprice" min-width="120" align="center" />
        <el-table-column label="销售打印分类" prop="xsdyfl" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="外送机构" prop="idCooporg" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="分中心" prop="fzxIds" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="APP显示" prop="examfeeitemCodex" min-width="100" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.examfeeitemCodex == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="禁用" prop="isBan" min-width="100" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isBan == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="折扣" prop="zk" min-width="120" align="center" />
        <el-table-column label="套餐价格" prop="suiteprice" min-width="120" align="center" />
        <el-table-column label="特需价格" prop="txjg" min-width="120" align="center" />
        <el-table-column label="外宾价格" prop="wbjg" min-width="120" align="center" />
        <el-table-column label="优待价格" prop="ydjg" min-width="120" align="center" />
        <el-table-column label="内部价" prop="nbj" min-width="120" align="center" />
        <el-table-column label="耗材价格" prop="materialprice" min-width="120" align="center" />
        <el-table-column label="成本价" prop="costprice" min-width="120" align="center" />
        <el-table-column label="外部价" prop="coopprice" min-width="120" align="center" />
        <el-table-column label="样本类型" prop="yblx" min-width="140" align="center" />
        <el-table-column label="独立标示" prop="dlbs" min-width="100" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.dlbs == 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="费用类型" prop="fylx" min-width="140" align="center" show-overflow-tooltip />
        <el-table-column label="收费项目打印名称" prop="examfeeitemNameprn" min-width="160" align="center" show-overflow-tooltip />
        <el-table-column label="报告打印标示" prop="bgdybs" min-width="120" align="center" />
        <el-table-column label="打印排列序号" prop="xh" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="标示" prop="bs" min-width="120" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-for="item in bsOptions" :key="item.id">
              <span v-if="item.id == scope.row.bs">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="检查次数" prop="jccs" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="检查意义" prop="jcyy" min-width="120" align="center" show-overflow-tooltip />
        <el-table-column label="操作" fixed="right" width="200" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)" v-hasPermi="['basis:chargeItems:edit']">编辑</el-button>
            <el-button size="mini" type="text" style="color: #0059ff" @click="handleView(scope.row)" v-hasPermi="['basis:chargeItems:query']">检查项目</el-button>
            <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)" v-hasPermi="['basis:chargeItems:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    <!-- 检查项目对话框 -->
    <view-dialog ref="viewDialog"></view-dialog>
    <!-- 添加或修改对话框 -->
    <add-dialog ref="addDialog" @getList="getList"></add-dialog>
    <import-list ref="importList" @getList="getList"> </import-list>
    <!-- 上传图片对话框 -->
    <el-dialog title="上传图片" class="image-upload" :visible.sync="showUpload" :destroy-on-close="true" width="600px" append-to-body :close-on-click-modal="false">
      <el-form v-loading="uploadLoading" element-loading-text="上传中">
        <el-form-item>
          <image-upload ref="uploadImage" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></image-upload>
          <div class="image-upload-title">当前图片</div>
          <div class="image-upload-list">
            <el-image class="img" fit="contain" :src="imgUrl" :preview-src-list="[imgUrl]" v-if="imgUrl"></el-image>
            <span v-else>暂无图片</span>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload">上 传</el-button>
        <el-button type="primary" plain @click="deleteUpload" v-if="imgUrl">删除图片</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { listItems, delItems, updateJccs, setApp, setBan, getPicture, removeImg, importItems } from '@/api/basis/charge'
import addDialog from './add'
import importList from './import'
import viewDialog from './view'
import Cookies from 'js-cookie'
export default {
  name: "Charge_items",
  components: { addDialog, viewDialog, importList },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        sfxmsrm: undefined,
        examfeeitemName: undefined,
        tjlx: undefined,
        forMale: undefined,
        idDepart: undefined,
        examfeeitemCode: undefined,
        idLabtype: undefined,
      },
      // 排检表格数据
      tableList: [],
      // 科室列表
      departData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '科室', //第二列标题
        url: '/system/keshi/list', //请求连接
        bindValue: '',
        firstName: 'srm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 标本类型列表
      labTypeData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '名称', //第二列标题
        url: '/data/yblx/list', //请求连接
        bindValue: '', //初始值(必传)
        firstName: 'srm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
      },
      // 体检类型
      tjlxOptions: [
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
      // 性别
      xbOptions: [
        { id: 0, text: '男' },
        { id: 1, text: '女' },
        { id: 2, text: '通用' },
      ],
      //餐序
      cxOptions: [
        { id: '0', text: '餐前' },
        { id: '1', text: '餐中' },
        { id: '2', text: '餐后' },
        { id: '3', text: '无' },
      ],
      //标示
      bsOptions: [
        { id: '0', text: '健康标示' },
        { id: '1', text: '职业标示' },
      ],
      selection: {},
      // 显示模态框
      showUpload: false,
      // 上传加载
      uploadLoading: false,
      // 上传组件参数
      uploadData: {
        url: '/items/upload', //图片上传地址
        multiple: false, //是否可以上传多个
        limit: 1, //图片上传个数限制
        data: {}, //上传时附带的额外参数
      },
      // 当前图片
      imgUrl: '',
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true
      listItems(this.queryParams)
        .then((response) => {
          this.tableList = response.data.records
          this.total = response.data.total
          this.loading = false
        })
        .catch((err) => {
          console.error(err)
          this.loading = false
        })
    },
    // 模板类型
    tjlx(value) {
      if (value == 0) {
        return '健康体检'
      } else if (value == 1) {
        return '职业体检'
      } else if (value == 2) {
        return '综合'
      }
    },
    // 选择科室
    departChange(value) {
      this.queryParams.idDepart = value.id
      this.departData.bindValue = value.name
      this.handleQuery()
    },
    // 选择标本类型
    labTypeChange(value) {
      this.queryParams.idLabtype = value.id
      this.labTypeData.bindValue = value.name
      this.handleQuery()
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.departData.bindValue = undefined
      this.labTypeData.bindValue = undefined
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 添加
    handleAdd() {
      this.$refs.addDialog.handleAdd()
    },
    // 删除
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除该数据项？')
        .then(function () {
          return delItems(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => { })
    },
    ///导入名单打开方法
    handImport() {
      this.$refs.importList.handleShow(this.ids[0])
    },
    // 导入老系统收费项目
    handImportOld() {
      this.$prompt('请输入收费项目id', '提示')
        .then(({ value }) => {
          if (!value) {
            this.$modal.msgError('请输入收费项目id')
            return
          }
          importItems({
            ids: value.split(','),
          }).then(() => {
            this.$modal.msgSuccess('导入成功')
            this.getList()
          })
        })
        .catch(() => { })
    },
    // 编辑
    handleUpdate(row) {
      let rows = row.id ? row : this.selection
      if (rows.isPublic == 1 && fdataRole != '1') {
        this.$modal.msgWarning('该收费项目是公共数据，您没有修改公共数据的权限！')
        return
      }
      this.$refs.addDialog.handleUpdate(rows)
    },
    // 检查项目
    handleView(row) {
      this.$refs.viewDialog.handleShow(row)
    },
    // 上传图片
    handleUpload() {
      this.showUpload = true
      this.getPicture()
    },
    // 获取图片
    getPicture() {
      getPicture(this.selection.id).then((res) => {
        this.imgUrl = res.data ? Cookies.get('imgPath') + res.data : res.data
      })
    },
    // 更新
    handleRefresh() {
      this.$modal
        .confirm('确定要更新收费项目检查次数吗？', '提醒', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
        .then(() => {
          this.$modal.loading('全力执行中，请稍后...')
          updateJccs()
            .then(() => {
              this.$modal.closeLoading()
              this.getList()
              this.$modal.msgSuccess('更新成功')
            })
            .catch(() => {
              this.$modal.closeLoading()
            })
        })
        .catch(() => { })
    },
    // APP项目
    handleSetApp(state) {
      this.$modal
        .confirm(state == 1 ? '确定使所选项目在APP可见吗？' : '确定使所选项目在APP不可见吗？', '提醒', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
        .then(() => {
          var data = {
            ids: String(this.ids),
            state: state,
          }
          this.$modal.loading('全力执行中，请稍后...')
          setApp(data)
            .then(() => {
              this.$modal.closeLoading()
              this.getList()
              this.$modal.msgSuccess('操作成功')
            })
            .catch(() => {
              this.$modal.closeLoading()
            })
        })
        .catch(() => { })
    },
    // 禁用
    handleDisable(state) {
      this.$modal
        .confirm('确定禁用所选项目吗？', '提醒', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
        .then(() => {
          var data = {
            ids: String(this.ids),
            state: state,
          }
          this.$modal.loading('全力执行中，请稍后...')
          setBan(data)
            .then(() => {
              this.$modal.closeLoading()
              this.getList()
              this.$modal.msgSuccess('操作成功')
            })
            .catch(() => {
              this.$modal.closeLoading()
            })
        })
        .catch(() => { })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/items/export',
        {
          ...this.queryParams,
        },
        `收费项目列表.xlsx`
      )
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      if (selection.length == 1) {
        this.selection = selection[0]
      } else {
        this.selection = {}
      }
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 上传图片成功
    uploadFinish(value) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功！', '提醒')
        this.showUpload = false
      }
      this.uploadLoading = false
    },
    // 确认上传图片
    submitUpload() {
      var msg = this.$refs.uploadImage.isUpload()
      if (msg === true) {
        if (this.imgUrl) {
          this.$modal
            .confirm('将替换上次上传的图片，确定要上传吗？', '提醒')
            .then(() => {
              this.uploadData.data = { itemsId: this.selection.id }
              this.uploadLoading = true
              this.$refs.uploadImage.handelUpload()
            })
            .catch(() => { })
        } else {
          this.uploadData.data = { itemsId: this.selection.id }
          this.uploadLoading = true
          this.$refs.uploadImage.handelUpload()
        }
      } else {
        this.$modal.msgWarning(msg, '提醒')
      }
    },
    // 删除图片
    deleteUpload() {
      this.$modal
        .confirm('确定要删除图片吗？', '提醒')
        .then(() => {
          return removeImg({ itemsId: this.selection.id })
        })
        .then(() => {
          this.getPicture()
          this.$modal.msgSuccess('删除成功！')
        })
        .catch(() => { })
    },
    // 取消上传图片
    cancelUpload() {
      this.showUpload = false
    },
  },
}
</script>
<style lang="scss">
.image-upload {
  .image-upload-title {
    font-size: 14px;
    line-height: 20px;
    color: #000000;
    margin-top: 16px;
  }

  .image-upload-list {
    margin-top: 12px;

    .img {
      max-width: 400px;
      max-height: 360px;
      display: block;
      margin: 0 auto;
    }

    span {
      display: block;
      padding: 20px;
      text-align: center;
      font-size: 14px;
      line-height: 22px;
      color: #666;
    }
  }
}
</style>
