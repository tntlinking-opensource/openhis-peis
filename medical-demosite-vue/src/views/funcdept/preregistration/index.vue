<!-- 护理登记 开发人：麦沃德科技暴雨/矢北 2023-8-11-暴雨添加批量交单按钮-->
<template>
  <div class="app-container flex-direction-column">
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <!-- 筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
        <el-form-item label="体检号" prop="patientCode" style="margin-bottom: 0">
          <el-input size="mini" v-model="queryParams.patientCode" placeholder="请输入体检号" clearable style="width: 180px" @keyup.enter.native="handleQuery()"></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 0">
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-circle-check" @click="handleMyd" v-hasPermi="['funcdept:preregistration:handleMyd']">满意度录入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain size="mini" icon="el-icon-tickets" @click="handleJd" v-hasPermi="['funcdept:preregistration:handleJd']">交单</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-tickets" @click="handleJds" v-hasPermi="['funcdept:preregistration:handleJds']">批量交单</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-printer" @click="printBarcodes" v-hasPermi="['funcdept:preregistration:printBarcodes']">打印条码</el-button>
          </el-col>
        </el-form-item>
      </el-form>
    </el-row>

    <!-- 主体 -->
    <div class="table-box">
      <el-row :gutter="10" class="mb8" style="height: 100%">
        <!-- 左侧 -->
        <index-left ref="indexLeft" :patientCode="queryParams.patientCode" @changeCode="changeCode"></index-left>
        <!-- 右侧 -->
        <index-right ref="indexRight" :patientCode="queryParams.patientCode"></index-right>
      </el-row>
    </div>

    <!-- 打印对话框 -->
    <el-dialog title="打印" :visible.sync="openPrint" width="500px" destroy-on-close append-to-body style="overflow: hidden" :close-on-click-modal="false">
      <el-form :model="printParams" size="small" label-width="100px">
        <el-form-item label="模式" prop="mode">
          <el-radio-group v-model="printParams.mode" :disabled="setModel">
            <el-radio v-for="item in modelType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="个数">
          <el-radio-group v-model="printNum">
            <el-radio :label="3">3</el-radio>
            <el-radio :label="6">6</el-radio>
            <el-radio :label="9">9</el-radio>
            <el-radio :label="12">12</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-radio-group v-model="printParams.content">
            <el-radio v-for="item in radioType" :key="item.id" :label="item.id">{{ item.text }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="printConfirm">确 定</el-button>
        <el-button type="" @click="openPrint = false">取消</el-button>
      </div>
      <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0">
        <embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0" />
      </object>
    </el-dialog>
    <!-- 团检弹窗 -->
    <group ref="group"></group>
    <!-- 批量交单 -->
    <repetition ref="repetition"></repetition>
    <!-- 处理未检项目 -->
    <pop-data ref="popData" @closePopDialog="closePopDialog"></pop-data>
  </div>
</template>

<script>
import { getCookie } from '@/utils/getCookie.js' //获取cookie数据
//打印条形码接口及插件
import { printBarCode } from '@/utils/printBarCode.js'
import { getPatientData, submitForm, getPopData } from '@/api/funcdept/preregistration'
import indexLeft from './index_left.vue'
import indexRight from './index_right.vue'
import recommendLeft from './recommend/recommend_left.vue'
import group from './group.vue'
import repetition from './repetition.vue'
import popData from './pop_data.vue'

export default {
  name: 'Preregistration',
  components: {
    indexLeft,
    indexRight,
    recommendLeft,
    group,
    repetition,
    popData,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 筛选参数
      queryParams: {
        patientCode: undefined,
        autoFill: true,
      },
      // 打印弹窗
      openPrint: false,
      // 打印参数
      printParams: {
        mode: '1',
        style: '1',
        content: '0',
      },
      setModel: false,
      //打印-- 条码/导引单选项
      modelType: [
        //模式
        { id: '0', text: '设计' },
        { id: '1', text: '打印' },
      ],
      radioType: [
        //内容
        { id: '0', text: '条码' },
      ],
      // 打印个数
      printNum: 6,
    }
  },
  mounted() {
    if (this.$route.params.patientcode) {
      this.queryParams.patientCode = this.$route.params.patientcode
      this.handleQuery()
    }
  },
  methods: {
    // 输入体检号搜索
    handleQuery() {
      if (!this.queryParams.patientCode) {
        this.$message({
          message: '请输入体检号再进行交单或者查询！',
          type: 'warning',
        })
      } else {
        this.$refs.indexLeft.getListData(this.queryParams.patientCode, true)
      }
    },
    // 关闭弹窗刷新右侧表格
    closePopDialog() {
      this.$refs.indexLeft.getListData(this.queryParams.patientCode, true, 1)
    },
    //打印条码
    printBarcodes() {
      this.openPrint = true
    },
    // 确认打印
    printConfirm() {
      if (this.queryParams.patientCode) {
        //获取体检者id
        getPatientData(this.queryParams)
          .then((response) => {
            const query = {
              id: getCookie('cid'), //分中心id
              ids: [response.data.patientData.id], //体检者ids
              model: this.printParams.mode, //模板id
            }
            if (this.printParams.content == '0') {
              printBarCode(
                query,
                () => {
                  this.openPrint = false
                },
                this.printNum
              )
            } else if (this.printParams.content == '1') {
              let queryParams = {
                id: getCookie('cid'), //分中心id
                ids: this.formData.patientcode, //体检号
                model: this.printParams.mode, //模板id
                type: '0',
                dydStyle: this.printParams.style, //导引单类型 0 简单 1 复杂 2 普通
                printBar: undefined,
              }
              if (this.printParams.content == '2') {
                queryParams.printBar = JSON.stringify(query)
              }
              let routeUrl = this.$router.resolve({
                name: 'BillsModelList',
                query: queryParams,
              })
              this.openPrint = false
              window.open(routeUrl.href, '_blank')
            }
          })
      } else {
        this.$message({
          message: '请输入体检号查询后进行打印',
          type: 'warning',
        })
      }
    },
    //批量录入
    handleJds() {
      this.$refs.repetition.showDialog()
    },
    // 补全体检号改变
    changeCode(code, skipPop) {
      this.queryParams.patientCode = code
      if (skipPop === true) {
        this.$refs.indexLeft.getInspect(this.queryParams.patientCode, true)
      } else {
        getPopData({ patientCode: this.queryParams.patientCode }).then(({ data }) => {
          if (data && data.length) {
            this.$refs.popData.showDialog(data)
            return
          }
          this.$refs.indexLeft.getInspect(this.queryParams.patientCode, true)
        })
      }
    },

    //交单
    handleJd() {
      if (this.queryParams.patientCode === '' || this.queryParams.patientCode === null || this.queryParams.patientCode === undefined) {
        this.$message({
          message: '请输入体检号再进行交单或者查询！！！',
          type: 'warning',
        })
      } else {
        const patientCode = this.queryParams

        this.$modal
          .confirm('确定要交单?')
          .then(function () {
            return submitForm(patientCode)
          })
          .then(() => {
            this.$modal.msgSuccess('处理成功')
          })
      }
    },
    //满意度
    handleMyd() {
      if (this.queryParams.patientCode === '' || this.queryParams.patientCode === null || this.queryParams.patientCode === undefined) {
        this.$message({
          message: '请输入体检号再进行满意度录入！！！',
          type: 'warning',
        })
      }
    },
  },
}
</script>

<style scoped>
.mb8 {
  display: flex;
}
</style>
