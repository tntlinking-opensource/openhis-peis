<template>
  	<div class="add-container">
      <el-dialog title="导出DCM" :visible.sync="open" class="add-charge" width="26%" append-to-body
			:close-on-click-modal="false">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="70px" v-show="showSearch">
        <el-form-item label="科室">
          <input-select ref="selectData" :selectData="labTypeData" @change="labTypeChange"></input-select>
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="submitForm">确 定</el-button>
				<el-button type="primary" plain @click="reset">重 置</el-button>
				<el-button @click="cancel">取 消</el-button>
			</div>
      </el-dialog>
    </div>
</template>

<script>
export default {
  data()
    {
      return{
        open:false, 
        showSearch:true,
         // 科室名称
      labTypeData: {
        placeholder: '请输入输入码选择',
        key: '科室码',
        value: '科室名称',
        url: '/basconclusion/findAllDepartment',
        bindValue: '', //初始值(必传)

      },
      queryParams:{
          depName:undefined
      },
      }
    },
    methods:{
      // 添加
		handleAdd() {
		
			this.open = true;
		
		},
    // 科室名称选择结果
    labTypeChange(value) {
      this.queryParams.depName = value.name;
      this.labTypeData.bindValue = value.name;
    },
    	// 提交按钮
		submitForm() {
			this.$refs["queryForm"].validate(valid => {
				if (valid) {
					if (this.form != null) {
						// updateItems(this.form).then(response => {
						// 	this.$modal.msgSuccess("修改成功");
							this.open = false;
						// 	this.$emit("getList")
						// });
					} else {
						// addItems(this.form).then(response => {
						// 	this.$modal.msgSuccess("添加成功");
							this.open = false;
						// 	this.$emit("getList")
						// });
					}
				}
			});
		},
    //取消按钮
    cancel()
    {
      this.open = false;
      this.reset();
    },
     // 重置数据
     reset() {
      // if (this.popData) {
      //   this.form = JSON.parse(JSON.stringify(this.popData))
      //   this.form.depName = undefined;
      //   this.$nextTick(() => {
      //    this.$refs.select1.initData(this.form.depName);
      //    this.$refs.select2.initData(this.form.depName);
      //     this.selectData1.bindValue = this.form.depName
      //   })
      //   return
      // }
      this.queryParams = {
        depName: undefined,
      }
      this.resetForm("queryForm");
      //  this.labTypeData.bindValue = undefined
       this.$nextTick(()=>{
        this.$refs.selectData.initData(this.queryParams.depName)
       })

    },
    }
}
</script>

<style>

</style>