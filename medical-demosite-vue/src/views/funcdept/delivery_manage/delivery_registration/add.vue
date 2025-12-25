<!-- 外送登记 开发人：麦沃德科技矢北/暴雨 -->
<template>
  <div class="add-container">
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :inline="true" label-width="110px" hide-required-asterisk>
        <el-form-item label="体检号" prop="patientcode">
          <el-input v-model="form.patientcode" placeholder="请输入" @keyup.enter.native="getList()" clearable style="width: 240px" />
        </el-form-item>
        <el-form-item label="姓名" prop="patientname">
          <el-input :disabled="true" v-model="form.patientname" placeholder="请输入" clearable style="width: 240px" />
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-select :disabled="true" v-model="form.xb" placeholder="" clearable style="width: 240px">
            <el-option label="男" :value="0"></el-option>
            <el-option label="女" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="承送人" prop="sendPeople">
          <input-select ref="select" :selectData="selectData" :initialValue="form.sendPeopleName" @change="labTypeChange"></input-select>
        </el-form-item>
        <el-form-item label="外送时间" prop="sendDate">
          <el-date-picker v-model="form.sendDate" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="datetime"> </el-date-picker>
        </el-form-item>
        <el-form-item label="外送备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入" type="textarea" :rows="3" clearable style="width: 600px" />
        </el-form-item>
      </el-form>
      <el-tag style="width: 100%">项目列表</el-tag>
      <el-table ref="examList" border :data="examList" stripe height="320px" @selection-change="handleSelectionChange" @row-click="rowClick">
        <el-table-column fixed type="selection" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column label="项目名称" prop="itemName" align="center" show-overflow-tooltip />
        <el-table-column label="科室名称" prop="ksName" align="center" show-overflow-tooltip />
        <el-table-column label="外送机构" prop="bz" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <input-select ref="select" :currentIndex="scope.row.index" :selectData="orgSelectData" :initialValue="scope.row.wsjg" @change="orgChange($event, scope.$index)"> </input-select>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total > 0" :total="total" :page.sync="page.current" :limit.sync="page.size" @pagination="getList" />

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="primary" plain @click="reset">重 置</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getListDataInAddUp, getListDataInAddDown, saveDataList } from '@/api/funcdept/delivery_manage/delivery_registration'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      examList: [],
      // 总条数
      total: 0,
      queryParams: {
        griddata: [],
        formdata: {},
      },
      // 表单参数
      form: {
        patientcode: undefined,
      },
      page: {
        current: 1,
        size: 10,
      },
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 科室名称
      selectData: {
        placeholder: '请输入输入码选择',
        key: '部门名称',
        value: '用户名称',
        url: 'outside/sendRegister/getLqrData',
        bindValue: '', //初始值(必传)
        selectWidth: 240,
        firstName: 'inCode',
        secondName: 'occupationSummary',
        queryData: 'srm',
      },
      orgSelectData: {
        placeholder: '',
        key: '输入码',
        value: '外送机构',
        url: '/outside/sendRegister/getWsjgData',
        bindValue: '', //初始值(必传)
        selectWidth: 150,
        firstName: 'inputCode',
        secondName: 'wsjg',
        queryData: 'srm',
      },
      // 选择的数据
      selectList: [],
    }
  },
  methods: {
    // 添加
    handleAdd() {
      this.reset()
      this.selectData.bindValue = undefined
      this.open = true
      this.title = '外送登记'
    },
    //获取数据
    getList() {
      this.loading = true
      getListDataInAddDown({ ...this.form, ...this.page }).then((response) => {
        if (response.data.records < 1) {
          this.$message({
            message: '没有该体检者的信息',
            type: 'warning',
          })
        } else {
          this.examList = response.data.records
          for (var i in this.examList) {
            this.examList[i].index = i
          }
          this.total = response.data.total
          this.loading = false
        }
      })
      getListDataInAddUp({ ...this.form, ...this.page })
        .then((response) => {
          if (!response.data) {
            return
          } else {
            if (response.data.outsideMain) {
              this.form = response.data.outsideMain
            } else {
              this.form = response.data
            }
            this.form.sendDate = this.form.sendDate ? (this.form.sendDate.length < 11 ? this.form.sendDate.slice(0, 10) + ' 08:00:00' : this.form.sendDate) : this.$getDate()
          }
          this.loading = false
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    // 表单重置
    reset() {
      this.examList = []
      this.total = 0
      this.resetForm('form')
    },
    //取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表格勾选状态
    handleSelectionChange(selection) {
      this.selectList = selection
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') 
      this.$refs.examList.clearSelection()
      this.$refs.examList.toggleRowSelection(row)
    },
    // 数据选择框内容发生改变
    orgChange(value, index) {
      this.examList[index].wsjgId = value.id
      this.examList[index].wsjg = value.wsjg
    },
    labTypeChange(value) {
      this.form.sendPeople = value.id
      this.selectData.bindValue = value.occupationSummary
    },
    // 提交按钮
    submitForm() {
      this.queryParams.formdata = JSON.parse(JSON.stringify(this.form))
      this.queryParams.griddata = JSON.parse(JSON.stringify(this.selectList))
      if (this.queryParams.griddata.length < 1) {
        this.$message({
          message: '请选择需要外送登记的项目',
          type: 'warning',
        })
      } else {
        for (var i = 0; i < this.queryParams.griddata.length; i++) {
          if (this.queryParams.griddata[i].bz == '') {
            this.$message({
              message: this.queryParams.griddata[i].bz + '外送机构不能为空',
              type: 'warning',
            })
            return
          } else if (i == this.queryParams.griddata.length - 1) {
            this.$refs['form'].validate((valid) => {
              if (valid) {
                saveDataList(this.queryParams)
                  .then((response) => {
                    this.$modal.msgSuccess('外送登记成功')
                    this.open = false
                    this.$emit('getList')
                  })
                  .catch((error) => {
                    console.error(error)
                  })
              }
            })
          }
        }
      }
    },
  },
}
</script>
