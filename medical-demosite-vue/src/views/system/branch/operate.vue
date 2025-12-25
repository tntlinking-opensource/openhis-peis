<!-- 分中心管理弹窗  开发人：麦沃德科技半夏 -->
<template>
  <div class="operate-container">
    <el-dialog title="删除体检号" :visible.sync="open" class="operate-center" width="446px" v-if="popType == 1" :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-position="top" hide-required-asterisk @submit.native.prevent v-loading="loading">
        <el-form-item label="请输入完整体检号" prop="patientCode">
          <el-input ref="patientCode" v-model="form.patientCode" placeholder="请输入体检号" clearable style="width: 100%" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改项目状态" :visible.sync="open" class="operate-project" width="760px" v-else-if="popType == 2" :close-on-click-modal="false">
      <div class="flex-direction-column">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
          <el-form-item label="体检号" prop="patientCode">
            <el-input v-model="queryParams.patientCode" placeholder="请输入完整体检号" class="search-input" clearable style="width: 204px" @keyup.enter.native="handleQuery" />
            <el-button size="mini" type="primary" @click="handleQuery" style="margin-right: 22px; border-radius: 0 3px 3px 0">搜索 </el-button>
          </el-form-item>
          <el-form-item label="项目状态" prop="fExaminated">
            <el-select v-model="queryParams.fExaminated" placeholder="请选择项目状态" style="width: 260px" clearable>
              <el-option label="未检" :value="0" />
              <el-option label="已检" :value="1" />
            </el-select>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <div class="table-box">
          <el-table ref="tableList" border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange" @row-click="rowClick">
            <!-- <el-table-column type="selection" width="55" align="center" /> -->
            <el-table-column label="序列" width="55" type="index" align="center" />
            <el-table-column label="科室名称" prop="ksName" align="center" show-overflow-tooltip />
            <el-table-column label="项目名称" prop="examfeeitemName" align="center" show-overflow-tooltip />
            <el-table-column label="状态" prop="fexaminated" align="center">
              <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.fexaminated == 1">已检</el-tag>
                <el-tag type="danger" v-else>未检</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-edit" @click="handleChange(scope.row)" v-hasPermi="['system:branch:edit']">修改状态</el-button>
                <el-button size="mini" type="text" icon="el-icon-edit" @click="handleModifyDept(scope.row)" v-hasPermi="['system:branch:edit']">修改科室</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="popType == 3 ? '修改健康体检状态' : '修改职业体检状态'" :visible.sync="open" class="operate-center" width="418px" v-else-if="popType == 3 || popType == 4" :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="110px" hide-required-asterisk v-loading="loading">
        <el-form-item label="体检号" prop="patientcode">
          <el-input v-model="form.patientcode" placeholder="请输入完整体检号" clearable style="width: 260px" />
        </el-form-item>
        <el-form-item label="健康体检状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择健康体检状态" style="width: 260px">
            <el-option v-for="item in statusOptions" :key="item.id" :label="item.text" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改工种" :visible.sync="open" width="460px" v-else-if="popType == 5" :close-on-click-modal="false">
      <div class="flex-direction-column">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" label-width="80px" v-loading="workLoading">
          <el-form-item label="体检号" prop="patientCode">
            <el-input v-model="queryParams.patientCode" placeholder="请输入完整体检号" style="width: 335px" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="修改工种" prop="fExaminated">
            <search-select ref="workType" :selectData="workTypeData" selectWidth="335" @change="workTypeChange"></search-select>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改开单医生" :visible.sync="open" width="460px" v-else-if="popType == 6" :close-on-click-modal="false">
      <div class="flex-direction-column">
        <!-- 筛选 -->
        <el-form :model="queryParams" ref="queryForm" size="small" label-width="100px" v-loading="doctorLoading">
          <el-form-item label="体检号" prop="patientcode">
            <el-input v-model="queryParams.patientcode" placeholder="请输入完整体检号" style="width: 300px" clearable @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="修改开单医生" prop="userName">
            <input-select ref="doctorName" :selectData="selectData"   @change="selectChanges"></input-select>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
     <el-dialog title="修改科室" :visible.sync="open" width="460px" v-else-if="popType == 7" :close-on-click-modal="false">
      <div class="flex-direction-column">
        <!-- 筛选 -->
        <el-form :model="deptForm" ref="deptForm" size="small" label-width="80px" v-loading="deptLoading">
          <el-form-item label="体检号" prop="patientCode">
            <el-input v-model="deptForm.patientCode" disabled style="width: 335px" />
          </el-form-item>
          <el-form-item label="科室" prop="ksId">
            <search-select 
              ref="deptSelect" 
              :selectData="deptSelectData" 
              selectWidth="335" 
              @change="deptChange">
            </search-select>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDeptForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { delCode, findProjectStatus, modifyProjectStatus, modifyPeispatientStatus, modifyWorkType, updateOpenDoctor, modifyDepartment } from '@/api/system/branch.js'
export default {
  data() {
    return {
      // 弹窗宽度 
      popWidth: '',
      // 弹窗类型
      popType: '',
      // 遮罩层
      loading: false,
      // 科室修改遮罩层
      deptLoading: false,
      // 选中数组
      ids: [],
      // 查询参数
      queryParams: {
        patientCode: undefined,
        fExaminated: undefined,
      },
      // 排检表格数据
      tableList: [],
      // 体检类型列表
      statusOptions: [
        { id: '-1', text: '总检未开始' },
        { id: '0', text: '总检开始' },
        { id: '1', text: '总检完成' },
        { id: '2', text: '报告已打印' },
        { id: '3', text: '一审通过' },
        { id: '5', text: '二审通过' },
        { id: '7', text: '终审通过' },
        { id: '9', text: '报告已交接' },
        { id: '10', text: '报告已通知' },
        { id: '11', text: '报告已领取' },
      ],
      // 科室下拉数据
      deptSelectData: {
          placeholder: '请选择',
          inputTitle: '科室名称', // 搜索标题
          inputPlaceholder: '请输入科室名称', // 搜索placeholder
          key: '科室码', //第一列标题
          value: '科室名称', //第二列标题
          url: '/basconclusion/findAllDepartment', //请求连接
          bindValue: '',
          firstName: 'inputCode', //接口返回值对应第一列的参数名
          secondName: 'name', //接口返回值对应第二列的参数名
          optionFlag: 'id', // 使用id作为唯一标识
          queryData: 'deptName', // 搜索的参数名
      },
      // 科室表单
      deptForm: {
        id: undefined,
        patientCode: undefined,
        ksId: undefined
      },
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientCode: [{ required: true, message: '体检号不能为空', trigger: 'change' }],
        patientcode: [{ required: true, message: '体检号不能为空', trigger: 'change' }],
      },
      // 工种加载中
      workLoading: false,
      // 工种搜索数据
      workTypeData: {
        placeholder: '请选择',
        inputTitle: '工种名称', // 搜索标题
        inputPlaceholder: '请输入工种名称', // 搜索placeholder
        value: '工种名称',
        url: '/reception/order/getBaseWorktypeSql', //请求连接
        bindValue: '',
        secondName: 'typeName', //接口返回值对应第二列的参数名
        optionFlag: 'id',
        queryData: 'typeName',
      },
      // 工种id
      worktypeId: '',
      // 工种名称
      tradesName: '',
      // 开单医生加载中
      doctorLoading: false,
      // 开单医生搜索数据
      selectData: {
        placeholder: '请输入输入码选择',
        key: '医生id', //第一列标题
        value: '开单医生', //第二列标题
        url: '/sell/sellDate/getXsryByCode', //请求连接
        selectWidth: 300, //选择器宽度（选填，默认230）不加px,传100%则为100%
        bindValue: '', //初始值
        firstName: 'userNo', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'userName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'code', //向接口传递的参数名(不传默认为'inputCode')
      },
    }
  },
  methods: {
    // 提交科室修改表单
    submitDeptForm() {
        if (!this.deptForm.patientCode) {
          this.$alert('体检号不能为空', '提示')
          return
        }
        
        if (!this.deptForm.ksId) {
          this.$alert('请选择科室', '提示')
          return
        }
        
        this.$confirm(`是否确认将体检号${this.deptForm.patientCode}的科室进行修改?`)
          .then(() => {
            this.deptLoading = true
            const params = {
              id: this.deptForm.id,
              ksId: this.deptForm.ksId
            }
            return modifyDepartment(params)
          })
          .then(() => {
            this.deptLoading = false
            this.open = false
            this.$modal.msgSuccess('修改成功')
            this.handleQuery()
          })
          .catch((error) => {
            if (error !== 'cancel') {
              this.deptLoading = false
              console.error(error)
            }
          })
    },
    // 显示 
    handleShow(type) {
      this.popType = type
      this.reset()
      this.open = true
      if (type == 1) {
        this.$nextTick(() => {
          this.$refs.patientCode.focus()
        })
      }
    },
    // 查询列表
    getList() {
      this.loading = true
      findProjectStatus(this.queryParams)
        .then(({ data }) => {
          this.tableList = data
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 搜索
    handleQuery() {
      this.getList()
    },
    // 修改项目状态
    handleChange(param) {
      param.fexaminated = param.fexaminated == 0 ? 1 : 0
      param = [param]
      this.loading = true
      modifyProjectStatus(param)
        .then(() => {
          this.loading = false
          this.$modal.msgSuccess('状态更改成功')
          this.handleQuery()
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 处理修改科室
    handleModifyDept(row) {
        this.popType = 7;
        this.deptForm.id = row.id;
        this.deptForm.patientCode = this.queryParams.patientCode;
        this.deptForm.ksId = '';

        this.open = true; // 重新打开弹窗
        
        // 初始化科室选择器
        this.$nextTick(() => {
          if (this.$refs.deptSelect) {
            this.$refs.deptSelect.initData(null);
          }
        });
      },
      // 科室选择变化
      deptChange(value) {
        this.deptForm.ksId = value.id;
      },  
    // 获取工种
    workTypeChange(value) {
      this.worktypeId = value.id
      this.tradesName = value.typeName
    },
    // 获取开单医生  
    selectChanges(value) {
      console.log("value的值",value);
      this.queryParams.userName = value.userName
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item)
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.tableList.clearSelection()
      this.$refs.tableList.toggleRowSelection(row)
    },
    // 表单重置
    reset() {
      if (this.popType == 1) {
        this.form = {
          patientCode: undefined,
        }
        this.resetForm('form')
      } else if (this.popType == 2) {
        this.tableList = []
        this.resetForm('queryForm')
      } else if (this.popType == 3 || this.popType == 4) {
        this.form = {
          patientcode: undefined,
          status: '-1',
        }
        this.resetForm('form')
      } else if (this.popType == 5) {
        this.$nextTick(() => {
          this.$refs.workType.initData(null)
        })
        this.resetForm('queryForm')
      } else if (this.popType == 6) {
        this.$nextTick(() => {
          this.$refs.doctorName.initData(null)
        })
        this.queryParams = {
          patientcode: '',
          userName: '',
        }
      } else if (this.popType == 7) {
        this.deptForm = {
          id: undefined,
          patientCode: undefined,
          ksId: undefined
        };
        this.resetForm('deptForm');
        this.$nextTick(() => {
          if (this.$refs.deptSelect) {
            this.$refs.deptSelect.initData(null);
          }
        });
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 提交按钮
    submitForm() {
      if (this.popType == 1) {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.$modal
              .confirm(`注意：将删除体检号${this.form.patientCode}下所有数据！确定要删除吗？`)
              .then(() => {
                this.loading = true
                delCode({
                  patientCode: this.form.patientCode,
                }).then(() => {
                  this.loading = false
                  this.open = false
                  this.$modal.msgSuccess('删除成功')
                })
              })
              .catch(() => {})
          }
        })
      } else if (this.popType == 2) {
        let param = JSON.parse(JSON.stringify(this.ids))
        param.forEach((el) => {
          el.fexaminated = el.fexaminated == 0 ? 1 : 0
        })
        this.$confirm('是否更改选中的项目状态', '提示')
          .then(() => {
            this.loading = true
            modifyProjectStatus(param)
              .then(() => {
                this.loading = false
                this.$modal.msgSuccess('状态更改成功')
                this.handleQuery()
              })
              .catch((error) => {
                console.error(error)
                this.loading = false
              })
          })
          .catch(() => {})
      } else if (this.popType == 5) {
        let error = ''
        if (!this.queryParams.patientCode) {
          error = '体检号不能为空'
        } else if (!this.worktypeId) {
          error = '工种类型不能为空'
        }
        if (error) {
          this.$alert(error, '提示')
          return
        }
        this.$confirm(`是否确定将体检号${this.queryParams.patientCode} 的工种修改为${this.tradesName}`)
          .then(() => {
            let query = {
              patientcode: this.queryParams.patientCode,
              worktypeId: this.worktypeId,
              tradesName: this.tradesName,
            }
            this.workLoading = true
            modifyWorkType(query)
              .then(() => {
                this.workLoading = false
                this.open = false
                this.$modal.msgSuccess('修改成功')
              })
              .catch((error) => {
                this.workLoading = false
                console.error(error)
              })
          })
          .catch(() => {})
      } else if (this.popType == 6) {
        let error = ''
        if (!this.queryParams.patientcode) {
          error = '体检号不能为空'
        } else if (!this.queryParams.userName) {
          error = '请选择开单医生'
        }
        if (error) {
          this.$alert(error, '提示')
          return
        }
        this.$confirm(`是否确定将体检号${this.queryParams.patientcode} 的开单医生修改为${this.queryParams.userName}`)
          .then(() => {
            this.doctorLoading = true
            updateOpenDoctor(this.queryParams)
              .then(() => {
                this.doctorLoading = false
                this.open = false
                this.$modal.msgSuccess('修改成功')
              })
              .catch((error) => {
                this.doctorLoading = false
                console.error(error)
              })
          })
          .catch(() => {})
      } else {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            let diseaseHealth = this.popType == 3 ? 0 : 1
            this.loading = true
            modifyPeispatientStatus({
              diseaseHealth,
              patientcode: this.form.patientcode,
              status: this.form.status,
            }).then(() => {
              this.loading = false
              this.open = false
              this.$modal.msgSuccess('修改成功')
            })
          }
        })
      }
    },
  },
}
</script>
<style lang="scss">
.operate-container {
  .operate-project {
    .el-dialog {
      height: 100%;
      max-height: 700px;
    }

    .search-input {
      .el-input__inner {
        border-radius: 4px 0 0 4px;
        margin-right: -1px;
      }
    }
  }
}
</style>
