<template>
  <el-dialog title="快捷开药" :visible.sync="open" width="1580px" append-to-body style="overflow: hidden" class="prescribe">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="80px">
      <el-form-item label="药品分类" prop="drugClass">
        <el-select v-model="queryParams.drugClass" filterable remote reserve-keyword placeholder="请输入药品分类" :remote-method="remoteMethod" :loading="medicineLoading" clearable style="width: 160px" @change="handleQuery">
          <el-option v-for="item in medicineOptions" :key="item.id" :label="item.text" :value="item.id"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="药品名称" prop="drugName">
        <el-input v-model="queryParams.drugName" placeholder="请输入药品名称" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="规格" prop="drugStandard">
        <el-input v-model="queryParams.drugStandard" placeholder="请输入规格" clearable style="width: 160px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button class="section-btn-style1" icon="el-icon-search" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="flex">
      <div class="left-table">
        <el-table border ref="leftTable" v-loading="loading" :data="leftTable" style="width: 100%" stripe height="540px" @selection-change="examChange" @row-click="rowClickLeft">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column label="药品分类" prop="drugClass" min-width="140" align="center" show-overflow-tooltip />
          <el-table-column label="药品名称" prop="drugName" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="规格" prop="drugStandard" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="单价" prop="drugPrice" min-width="140" align="center" show-overflow-tooltip />
        </el-table>
        <!-- 分页 -->
        <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" style="background: none" />
      </div>
      <div class="center-btn">
        <el-button class="section-btn-style2" @click="selectOption"><i class="el-icon-upload2 el-icon--right"></i></el-button>
        <el-button class="section-btn-style2" @click="removeOption" style="margin: 24px 0 0 !important"><i class="el-icon-upload2 el-icon--left"></i></el-button>
        <el-button class="section-btn-style2" @click="handleHistory" style="padding: 7px 0; margin: 24px 0 0 !important; width: 50px; text-align: center">历 史 </el-button>
      </div>
      <!-- 右侧表格 -->
      <div class="right-table">
        <el-table border v-loading="loading" :data="selectList" height="400px" @selection-change="selectChange" :row-style="tableRowClassName">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column label="药品" prop="drugName" align="center" min-width="160px" show-overflow-tooltip />
          <el-table-column label="开药医生" prop="username" align="center" min-width="120px" />
          <el-table-column label="数量" prop="prescribeNum" align="center" min-width="120px">
            <template slot-scope="scope">
              <span v-if="scope.row.username != username || scope.row.isFinished != 0">{{ scope.row.prescribeNum }}</span>
              <el-input-number v-else v-model="scope.row.prescribeNum" step-strictly size="small" :min="1" controls-position="right" style="width: 100%"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="开药时间" prop="prescribeTime" align="center" min-width="170px" />
          <el-table-column label="开药原因" prop="reason" align="center" min-width="160px" show-overflow-tooltip />
        </el-table>
        <div class="reason">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="80px" label-position="top">
            <el-form-item label="开药原因" prop="reason" style="margin-bottom: 8px">
              <el-input v-model="reason" placeholder="请输入开药原因" type="textarea" :rows="5" style="width: 650px" />
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSave">保 存</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { handleDrugPage, getClassCheckData, getAddedData, saveOrUpdateDrug, getLastDrugs } from '@/api/funcdept/section_list/dialog.js'
import { getCookie } from '@/utils/getCookie.js'
export default {
  data() {
    return {
      // ks
      patientcode: undefined,
      // 打开主弹窗
      open: false,
      // 药品分类加载
      medicineLoading: false,
      // 药品分类
      medicineOptions: [],
      // 表格加载中
      loading: false,
      loadingR: false,
      // 筛选参数
      queryParams: {
        current: 1,
        size: 20,
        drugClass: undefined,
        drugName: undefined,
        drugStandard: undefined,
      },
      // 检查项目表格数据
      leftTable: [],
      selectList: [],
      tempList: [],
      cidList: [],
      // 选中数组
      examItems: [],
      selectIds: [],
      // 总数
      total: 0,
      // 开药原因
      reason: undefined,
      username: undefined,
    }
  },
  created() {
    this.bus.$on('returnConclusions', (reason) => {
      this.reason = reason
    })
    this.username = decodeURIComponent(getCookie('username'))
  },
  beforeDestroy() {
    this.bus.$off('returnConclusions')
  },
  methods: {
    showDialog(patientcode) {
      this.patientcode = patientcode
      this.open = true
      this.bus.$emit('getConclusions')
      this.getList()
      this.getHistory()
    },
    // 获取药品分类
    remoteMethod() {
      this.medicineLoading = true
      getClassCheckData({
        key: this.queryParams.drugClass,
      }).then(({ data }) => {
        this.medicineLoading = false
        this.medicineOptions = data
      })
    },
    // 获取表格数据
    getList() {
      this.loading = true
      handleDrugPage(this.queryParams).then(({ data }) => {
        this.leftTable = data.records
        this.total = data.total
        this.loading = false
      })
    },
    // 获得已开药记录
    getHistory() {
      this.loadingR = true
      getAddedData({ patientcode: this.patientcode }).then(({ data }) => {
        this.selectList = data ? data : []
        this.tempList = JSON.parse(JSON.stringify(this.selectList))
        // this.selectIds = data ? data.map(item => item.drugId) : []
        this.loadingR = false
      })
    },
    // 查询
    handleQuery() {
      this.current = 1
      this.getList()
    },
    // 非当前医师开药、开药状态不为0行变色
    tableRowClassName({ row, rowIndex }) {
      if (row.username != decodeURIComponent(getCookie('username')) || row.isFinished == 1 || row.isFinished == 2 || row.isFinished == 3) {
        return {
          'background-color': '#ccc',
        }
      }
    },

    // 多选框选中数据
    examChange(selection) {
      this.examItems = selection.map((item) => item)
    },
    selectChange(selection) {
      this.selectIds = selection.map((item) => item.drugId)
    },
    // 单击某行
    rowClickLeft(row) {
      this.$refs.leftTable.clearSelection() //清空表格数据
      this.$refs.leftTable.toggleRowSelection(row, true)
    },
    // 移入选项
    selectOption() {
      for (var i in this.examItems) {
        if (this.selectIds.indexOf(this.examItems[i].drugId) == -1) {
          this.examItems[i].username = decodeURIComponent(getCookie('username'))
          this.$set(this.examItems[i], 'prescribeNum', 1)
          this.examItems[i].prescribeTime = this.$getDate()
          this.examItems[i].reason = ''
          this.examItems[i].isFinished = 0
          this.selectList.push(this.examItems[i])
          this.selectIds.push(this.examItems[i].drugId)
        } else {
          this.$alert('药品' + this.examItems[i].drugName + '已存在', '提示')
          return
        }
      }
    },
    // 移出选项
    removeOption() {
      for (let i in this.selectIds) {
        let index = this.selectList.findIndex((item) => {
          if (item.username == decodeURIComponent(getCookie('username')) && item.isFinished == 0 && this.selectIds[i] == item.drugId) {
            return true
          }
        })
        if (index == -1) {
          this.$alert('药房已操作的药品不能删除', '提示')
          return
        }
        this.$delete(this.selectList, index)
        this.$delete(this.selectIds, index)
      }
    },

    // 历史
    handleHistory() {
      this.loadingR = true
      getLastDrugs({ patientcode: this.patientcode }).then(({ data }) => {
        this.selectList = data ? [...this.selectList, ...data] : this.selectList
        this.loadingR = false
      })
    },
    // 保存
    handleSave() {
      if (this.tempList.length == 0 && this.selectList.length == 0) {
        this.$modal.msgWarning('请添加收费项目')
        return
      }
      this.$confirm('确定要保存吗？', '确定', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
      })
        .then(() => {
          let griddata = []
          let _inclouds1 = this.selectList.map((item) => item.drugId)
          this.tempList.forEach((el) => {
            if (_inclouds1.includes(el.drugId)) {
              this.selectList.forEach((val) => {
                if (el.drugId == val.drugId) {
                  el.prescribeNum = val.prescribeNum
                }
              })
              el.state = 'modified'
              griddata.push(el)
            } else {
              el.state = 'removed'
              griddata.push(el)
            }
          })
          let _inclouds2 = this.tempList.map((item) => item.drugId)
          this.selectList.forEach((val) => {
            if (val.username == decodeURIComponent(getCookie('username')) && val.isFinished == 0 && !_inclouds2.includes(val.drugId)) {
              val.state = 'added'
              val.prescribeTime = Date.now()
              griddata.push(val)
            }
          })
          griddata = Array.from(new Set(griddata))
          for (let i = griddata.length - 1; i >= 0; i--) {
            if (griddata[i].isFinished != 0) {
              this.$delete(griddata, i)
            }
          }
          saveOrUpdateDrug({
            formdata: {
              patientcode: this.patientcode,
              reason: this.reason,
            },
            griddata,
          }).then(({ data }) => {
            this.$message({
              type: 'success',
              message: '保存成功!',
            })
            this.open = false
          })
        })
        .catch(() => {})
    },
  },
}
</script>

<style lang="scss">
.prescribe {
  .flex {
    display: flex;
  }

  .left-table {
    width: calc(55% - 80px);
    min-width: 600px;
    margin: -1px;
  }

  .center-btn {
    width: 80px;
    background: #fff;
    height: auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .el-icon--left {
      transform: rotate(-90deg);
    }

    .el-icon--right {
      transform: rotate(90deg);
    }
  }

  .right-table {
    width: 45%;
    min-width: 600px;
    flex: 1;
    margin: -1px;
  }

  .reason {
    height: 175px;
    overflow-y: auto;
    margin-top: 8px;
    padding: 5px 12px 0;
    border: 1px solid #dfe6ec;
  }

  .el-textarea__inner {
    background: #f6f7fb;
  }
}
</style>
