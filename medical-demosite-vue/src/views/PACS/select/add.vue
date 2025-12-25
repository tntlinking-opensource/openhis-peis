<template>
  	<div class="add-container">
      <el-dialog title="上传文件" :visible.sync="open" class="add-charge" width="26%" append-to-body
			:close-on-click-modal="false">
      <el-upload
  class="upload-demo"
  drag
  action="https://jsonplaceholder.typicode.com/posts/"
  multiple>
  <i class="el-icon-upload"></i>
  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
  <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过3MB</div>
</el-upload>
      </el-dialog>  
    </div>
</template>

<script>
export default {
    data()
    {
      return{
        open:false, 
      }
    },
    methods:{
      // 添加
		handleAdd() {
		
		
			this.open = true;
		
		},
    
    }

}
</script>

<style>

</style>