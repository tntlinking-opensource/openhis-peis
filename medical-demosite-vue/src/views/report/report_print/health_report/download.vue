<!-- 批量下载 开发人：麦沃德科技予安 -->
<template>
  <el-dialog title="批量下载" :visible.sync="open" width="800px" append-to-body>
    <el-form ref="queryForm" size="small" @submit.native.prevent v-loading="loading">
      <el-form-item label="已选顾客">
        <div>{{ personList.length }}名</div>
      </el-form-item>
      <el-form-item label="命名规则">
        <el-radio-group v-model="nameType">
          <el-radio label="1">体检号-姓名-登记日期</el-radio>
          <el-radio label="2">身份证号-姓名-登记日期</el-radio>
          <el-radio label="3">身份证号-姓名</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="文件名示例">
        <div v-if="nameType == 1">010230000001-张三-2024-01-01.pdf</div>
        <div v-else-if="nameType == 2">370284202401010123-张三-2024-01-01.pdf</div>
        <div v-else>370284202401010123-张三.pdf</div>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleConfirm">确 定</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { batchDownload,checkUrl } from '@/api/report/report_print/health_report'
import { handleExportZip } from '@/utils/downloadZip.js'
export default {
  props: ['fileName'],
  data() {
    return {
      // 遮罩层 
      loading: false,
      // 是否显示弹出层
      open: false,
      // 体检者列表 
      personList: [],
      // 命名规则
      nameType: '1',
      // 下载防抖
      downloadDebounce: false,
    }
  },
  methods: {
    // 打开弹窗
    handleShow(personList) {
      this.downloadDebounce = false
      this.personList = personList
      this.open = true
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 确认下载
    handleConfirm() {
      if (this.downloadDebounce) {
        return
      }
      this.downloadDebounce = true
      let array = []
      this.personList.forEach((el) => {
        array.push({
          namingMethod: this.nameType == 1 ? el.patientcode + '-' + el.patientname + '-' + el.dateregister.slice(0, 10) : this.nameType == 2 ? el.idcardno + '-' + el.patientname + '-' + el.dateregister.slice(0, 10) : el.idcardno + '-' + el.patientname,
          urlPdf: el.urlPdf,
        })
      })

      // 校验url是否有效
      checkUrl(array).then((response) => {
          console.log("校验url是否有效",response.data);

          if(response.data){
            this.$modal.loading('全力下载中，请稍后...')

            batchDownload(array)
                    .then((res) => {
                      handleExportZip(res, this.fileName != 2 ? '健康报告' : '职业报告', (res) => {
                        this.downloadDebounce = false
                        this.open = false
                        this.$modal.closeLoading()
                      }).catch((err) => {
                        this.downloadDebounce = false
                        this.$modal.closeLoading()
                        console.error(err)
                      })
                    })
                    .catch((err) => {
                      this.downloadDebounce = false
                      this.$modal.closeLoading()
                      console.error(err)
                    })
            }else{
                  this.downloadDebounce = false
                  this.$modal.closeLoading()
                  this.$modal.msgError(response.msg)
                  return
            }
      })

      

      
    },
  },
}
</script>
