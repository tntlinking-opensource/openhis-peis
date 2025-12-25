<!-- 单独下载 开发人：麦沃德科技予安 -->
<template>
  <el-dialog title="下载PDF" :visible.sync="open" width="800px" append-to-body>
    <el-form ref="queryForm" size="small" @submit.native.prevent v-loading="loading">
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
export default {
  props: ['fileName'],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 是否显示弹出层
      open: false,
      // 体检者列表
      tjzlist:{},
      urlPdf: '',
      // 命名规则
      nameType: '1',
      // 下载防抖
      downloadDebounce: false,
    }
  },
  methods: {
    // 打开弹窗
    handleShow(data,urlPdf) {
      this.open = true
      this.urlPdf = urlPdf
      this.tjzlist = data
      console.log("tjzlist值是",data);
      console.log("urlPdf值是",urlPdf);

      
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 确认下载
    handleConfirm() {
      const pdfUrl = this.urlPdf;
      console.log(1111111);
      console.log("pdfUrl值是",pdfUrl);
      
      fetch(pdfUrl)
        .then(response => {
          if (!response.ok) {
            throw new Error(response.msg);
          }
          return response.blob();
        })
        .then(blob => {
         console.log(22222222);

          const link = document.createElement('a');
          link.href = URL.createObjectURL(blob);
          if(this.nameType == 1){
            link.download = this.tjzlist.patientcode + '-' + this.tjzlist.patientname +  '-' +this.tjzlist.dateregister + '.pdf';
          }else if(this.nameType == 2){
            link.download = this.tjzlist.idcardno +  '-' + this.tjzlist.patientname +  '-' + this.tjzlist.dateregister +'.pdf';
          }else if(this.nameType == 3){
            link.download = this.tjzlist.idcardno +  '-' + this.tjzlist.patientname + '.pdf';
          }
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          console.log("link的值",link);
          
          URL.revokeObjectURL(link.href);
        })
        .catch(error => {
          console.error('Fetch error details:', error);
          this.$alert(error.message, '错误');
        });
    },
  },
}
</script>
