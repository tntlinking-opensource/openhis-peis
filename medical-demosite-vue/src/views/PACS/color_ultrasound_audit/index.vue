<!-- 彩超审核查看 开发人：麦沃德科技矢北 -->
<template>
  <div class="app-container flex-direction-column">

    <el-row style="height: 100%;" >
      <el-col :span="16" style="height: 100%;">
        <el-form :model="queryParams" ref="queryFrom" size="mini" :inline="true" label-width="70px" v-show="showSearch">
      <el-form-item label="输入码" prop="sfxmsrm">
				<el-input v-model="queryParams.sfxmsrm" placeholder="请输入输入码" clearable style="width: 160px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
      <el-form-item label="姓名" prop="name">
				<el-input v-model="queryParams.name" placeholder="请输入姓名" clearable style="width: 120px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
      <el-form-item prop="date" label="登记日期">
        <el-date-picker v-model="queryParams.date" style="width:254px;" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="table-box" style="height: 96%;" >
      <el-table border v-loading="loading" :data="tableList" height="90%" stripe
        @selection-change="handleSelectionChange">
        <el-table-column  type="selection" width="60" align="center" />
        <el-table-column label="序列" type="index" width="65" align="center" />
        <el-table-column  label="体检号" prop="code"  min-width="160" align="center" />
        <el-table-column  label="姓名" prop="name"  min-width="100" align="center" />
        <el-table-column  label="收费项目" prop="chargeItem"  min-width="180" align="center" />
        <el-table-column  label="检查人" prop="checker"  min-width="120" align="center" />
        <el-table-column  label="录入人" prop="people"  min-width="160" align="center" />
        <el-table-column  label="登记日期" prop="data"  min-width="160" align="center" />
        <el-table-column  label="性别" prop="gender"  min-width="100" align="center" >
          <template slot-scope="scope">
            <div v-for="item in sexOptions" :key="item.id">
              <span v-if="item.id == scope.row.idSex">{{ item.text }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column  label="年龄" prop="age"  width="140px" align="center" />
        </el-table>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
      </div>
      </el-col >
      <el-col :span="8" style="height: 100%;padding-left: 5px;">
       
          <div style="height: 50%;" >
            <span>描述</span>
          <el-input v-model="des" type="textarea" resize="none" style="background-color: #F6F7FB;"   rows="15"  placeholder="请输入描述"></el-input>
          </div>
         <div style="height: 50%;margin-top: 5px;">
          <span>小结</span>
          <el-input v-model="sum" type="textarea" resize="none" style="background-color: #F6F7FB;"   rows="15"  placeholder="请输入小结"></el-input>
         </div>  
   
      </el-col>
    </el-row>

  </div>
   
   

</template>

<script>
export default {
data(){
  return {
    //小结
    sum:undefined,
    //描述
    des:undefined,
    	// 遮罩层
			loading: true,
    // 显示搜索条件
			showSearch: true,
    ///总数
      total:50,
    // 查询参数
			queryParams: {
				current: 1,
				size: 20,
				sfxmsrm: undefined,
				name: undefined,
				tjlx: undefined,
				forMale: undefined,
				idDepart: undefined,
				examfeeitemCode: undefined,
				idLabtype: undefined,
     
			},
      // 排检表格数据
			tableList: [],
  }
},
created() {
		this.getList();
	},
methods:{
  // 搜索
  handleQuery() {
			this.queryParams.current = 1;
			this.getList();
		},
    	// 重置
		resetQuery() {
			this.resetForm("queryFrom");
			this.handleQuery();
		},
    // 多选框选中数据
		handleSelectionChange(selection) {
			this.ids = selection.map((item) => item.id);
			this.single = selection.length != 1;
			this.multiple = !selection.length;
		},
    	// 查询列表
		getList() {
			this.loading = true;
			// listPrinttype(this.queryParams).then(response => {
			// 	this.tableList = response.data.records;
			// 	this.total = response.data.total;
			
			// });
      this.loading = false;
		},
}
}
</script>

<style>

</style>