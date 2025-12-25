<template>
  <el-dialog title="科室加项" class="section-add-item" :visible.sync="open" width="1580px" append-to-body style="overflow: hidden" :close-on-click-modal="false" destroy-on-close @close="cancle">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" label-width="110px" class="no-margin-bottom">
      <el-form-item label="收费项目名称" prop="examfeeitemName" label-width="60">
        <el-input v-model="queryParams.examfeeitemName" placeholder="请输入收费项目名称" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="收费项目输入码" prop="sfxmsrm" label-width="60">
        <el-input v-model="queryParams.sfxmsrm" placeholder="请输入收费项目输入码" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button class="section-btn-style1" icon="el-icon-search" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="flex" id="mainTable">
      <div class="left-table">
        <el-table ref="leftTable" border v-loading="loading" :data="leftList" style="width: 100%" stripe height="50vh" @selection-change="examChange" @row-click="rowClickLeft">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序列" width="55" type="index" align="center" />
          <el-table-column label="收费项目名称" prop="sfxmmc" min-width="140" align="center" show-overflow-tooltip />
          <el-table-column label="收费项目输入码" prop="sfxmsrm" min-width="120" align="center" show-overflow-tooltip />
          <el-table-column label="性别" prop="xb" min-width="80" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ ['男', '女', '通用'][scope.row.xb] || '通用' }}
            </template>
          </el-table-column>
          <el-table-column label="检查意义" prop="jcyy" min-width="140" align="center" show-overflow-tooltip />
          <el-table-column label="价格" prop="jg" min-width="100" align="center" show-overflow-tooltip />
        </el-table>
        <!-- 分页 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getListLeft" style="background: none" />
      </div>
      <div class="center-btn">
        <el-button class="section-btn-style2" @click="selectOption"><i class="el-icon-right" style="font-size: 20px"></i></el-button>
        <el-button class="section-btn-style2" @click="removeOption" style="margin: 24px 0 0 !important"><i class="el-icon-back" style="font-size: 20px"></i></el-button>
      </div>
      <div class="right-table">
        <el-table ref="selectList" border v-loading="rightLoading" :data="selectList" style="width: 100%" height="calc(50vh + 50px)" :row-class-name="tableRowClassName" @selection-change="selectChange" @row-click="rowClickRight">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="项目名称" prop="examfeeitemName" align="center" show-overflow-tooltip width="200px" />
          <el-table-column label="价格" prop="price" align="center" show-overflow-tooltip width="80px" />
          <el-table-column label="加项医生" prop="doctorUsername" align="center" show-overflow-tooltip width="100px" />
          <el-table-column label="支付状态" prop="fFeecharged" align="center" show-overflow-tooltip width="100px">
            <template slot-scope="scope">
              <span v-if="scope.row.fFeecharged" style="color: green">已支付</span>
              <span v-else style="color: red">未支付</span>
            </template>
          </el-table-column>
          <el-table-column label="所属科室" prop="ssks" align="center" show-overflow-tooltip width="100px" />
          <el-table-column label="备注" prop="remarks" align="center" show-overflow-tooltip width="200px">
            <template slot-scope="scope">
              <span v-if="scope.row.qty">{{ scope.row.remarks }}</span>
              <el-input v-model="scope.row.remarks" size="mini" v-else></el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="handlePayAdd">支 付</el-button>
      <el-button class="section-btn-style1" @click="handleSave">保 存</el-button>
      <el-button class="section-btn-style2" @click="cancle">取 消</el-button>
    </div>
    <!-- 加项支付对话框 -->
    <add-item-pay ref="addItemPay" @getListRight="getListRight"></add-item-pay>
  </el-dialog>
</template>

<script>
import { getSfxm, getAddListData, saveOrUpdate, getTempFeeitem } from '@/api/funcdept/section_list/dialog.js'
import { getCookie } from '@/utils/getCookie.js'
import addItemPay from './add_item_pay.vue'
export default {
  components: {
    addItemPay,
  },
  data() {
    return {
      patientcode: undefined,
      // 打开主弹窗
      open: false,
      // 表格加载中
      loading: false,
      rightLoading: false,
      // 筛选参数
      queryParams: {
        current: 1,
        size: 50,
        examfeeitemName: undefined,
        sfxmsrm: undefined,
        syxb: undefined,
        tjlx: undefined,
      },

      // 检查项目表格数据
      leftList: [],
      selectList: [],
      cidList: [],
      // 选中数组
      examItems: [],
      selectIds: [],
      // 总数
      total: 0,
      // 接口中未确认的数据
      changeList: [],
    }
  },
  methods: {
    showDialog(personData) {
      this.queryParams.syxb = personData.syxb
      this.queryParams.tjlx = personData.tjlx
      this.patientcode = personData.patientcode
      this.changeList = []
      this.open = true
      this.getListLeft()
      this.getListRight()
    },
    // 获取科室加项左侧数据
    getListLeft() {
      this.loading = true
      getSfxm(this.queryParams).then(({ data }) => {
        this.leftList = data.records
        this.leftList.forEach((el) => {
          el.itemId = el.id
          el.doctorUsername = decodeURIComponent(getCookie('username'))
          el.examfeeitemName = el.sfxmmc
          el.price = el.jg
        })
        this.total = data.total
        this.loading = false
      })
    },
    // 获取科室加项右侧数据
    getListRight() {
      this.rightLoading = true
      getAddListData({ patientcode: this.patientcode }).then(({ data }) => {
        this.selectList = data
        this.cidList = data.map((item) => item.itemId)
        data.forEach((el) => {
          if (el.qty) {
          } else {
            this.changeList.push(el)
          }
        })
        this.changeList = JSON.parse(JSON.stringify(this.changeList))
        this.rightLoading = false
      })
    },
    // 带qty参数的不能被修改
    tableRowClassName({ row }) {
      if (row.qty) {
        return 'no-change'
      }
      if (row.doctorUsername) {
        return 'jxys-color'
      }
      return ''
    },
    // 查询
    handleQuery() {
      this.queryParams.current = 1
      this.getListLeft()
    },

    // 多选框选中数据
    examChange(selection) {
      this.examItems = selection.map((item) => item)
    },
    // 单击某行
    rowClickLeft(row) {
      this.$refs.leftTable.clearSelection() //清空表格数据
      this.$refs.leftTable.toggleRowSelection(row, true)
    },
    selectChange(selection) {
      this.selectIds = selection.map((item) => item.itemId)
    },
    // 单击某行
    rowClickRight(row, col) {
      if (col.type != 'selection') this.$refs.selectList.clearSelection() //清空表格数据
      this.$refs.selectList.toggleRowSelection(row, true)
    },
    // 移入选项
    selectOption() {
      for (var i in this.examItems) {
        if (this.cidList.indexOf(this.examItems[i].itemId) == -1) {
          this.selectList.unshift(this.examItems[i])
          this.selectList[0].remarks = undefined
          this.cidList.unshift(this.examItems[i].itemId)
          this.selectList = JSON.parse(JSON.stringify(this.selectList))
        } else {
          this.$alert(this.examItems[i].examfeeitemName + '项目已存在', '提示', {
            confirmButtonText: '确定',
          })
          return
        }
      }
    },
    // 移出选项
    removeOption() {
      for (let i in this.selectIds) {
        let index = this.selectList.findIndex((item) => {
          if (this.selectIds[i] == item.itemId) {
            return true
          }
        })
        if (this.selectList[index].qty) {
        } else {
          this.$delete(this.selectList, index)
          this.$delete(this.cidList, index)
        }
      }
    },
    // 保存
    handleSave() {
      let griddata = []
      let data = []
      this.selectList.forEach((el) => {
        if (el.qty) {
        } else {
          // el.id = undefined
          griddata.push(el)
        }
      })
      if (griddata.length == 0 && this.changeList.length == 0) {
        this.$modal.msgWarning('请添加收费项目')
        return
      }
      const clLoading = this.$loading({ target: '#mainTable' })
      let _inclouds1 = griddata.map((item) => item.itemId)
      this.changeList.forEach((el) => {
        if (_inclouds1.includes(el.itemId)) {
        } else {
          el.state = 'removed'
          data.push(el)
        }
      })
      let _inclouds2 = this.changeList.map((item) => item.itemId)
      griddata.forEach((val) => {
        if (!_inclouds2.includes(val.itemId)) {
          val.state = 'added'
          data.push(val)
        } else if (val.fFeecharged != 1) {
          val.state = 'modified'
          data.push(val)
        }
      })
      data = Array.from(new Set(data))
      saveOrUpdate({
        griddata: data,
        patientcode: this.patientcode,
      })
        .then(() => {
          this.$modal.msgSuccess('保存成功')
          this.changeList = []
          this.getListRight()
          this.$nextTick(() => {
            this.handlePayAdd(true)
          })
          clLoading.close()
        })
        .catch(() => {
          clLoading.close()
        })
    },
    // 支付加项
    handlePayAdd(isClose) {
      getTempFeeitem({
        patientcode: this.patientcode,
        showAll: 1,
      }).then(({ data }) => {
        if (data.list?.length) {
          this.$refs.addItemPay.showDialog(data)
        } else {
          if (isClose === true) {
            this.cancle()
          } else {
            this.$alert('当前体检者没有需要支付的加项项目', '提示')
          }
        }
      })
    },
    cancle() {
      this.selectList = []
      this.cidList = []
      this.examItems = []
      this.selectIds = []
      this.open = false
    },
  },
}
</script>

<style lang="scss">
.section-add-item {
  .flex {
    display: flex;
  }

  .left-table {
    width: calc(52% - 70px);
    min-width: 600px;
    margin: -1px;
  }

  .center-btn {
    width: 70px;
    background: #fff;
    height: auto;
    border: 1px solid #dfe6ec;
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
    width: 48%;
    min-width: 600px;
    flex: 1;
    margin: -1px;
    .el-table .no-change {
      background-color: #ddd;
    }
    .el-table .jxys-color {
      background-color: #70c245;
    }
    .el-table--medium .el-table__cell {
      padding: 5px 0;
    }
    .el-input__inner {
      padding: 0;
      border: none;
      background-color: rgba(0, 0, 0, 0);
      &:focus {
        border: 1px solid #dcdfe6;
      }
    }
  }
  .el-table .cell {
    font-size: 16px;
    color: #000;
  }
}
</style>
<style scope>
.section-add-item /deep/ .el-table__header-wrapper .el-checkbox {
  display: none;
}
.section-add-item /deep/ .el-button span {
  font-size: 16px;
}
.section-add-item /deep/ .el-input__inner,
.section-add-item /deep/ .el-textarea__inner {
  font-size: 16px;
}

.section-add-item /deep/ .el-form-item__content {
  font-size: 15px !important;
  color: #000 !important;
}
</style>
