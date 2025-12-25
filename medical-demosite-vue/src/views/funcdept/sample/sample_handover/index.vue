<!-- 样本交接 麦沃德科技 开发人:清风/暴雨-->
<template>
	<div class="app-container flex-direction-column">
		<!-- 搜索栏 -->
		<el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
			<el-form-item label="交接时间">
				<el-date-picker v-model="queryParams.date" style="width: 360px;" value-format="yyyy-MM-dd" type="daterange"
					range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
				<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
			</el-form-item>
		</el-form>
		<!-- 操作按钮 -->
		<el-row :gutter="10" class="mb8">
			<el-col :span="1.5">
				<el-button type="primary" icon="el-icon-plus" v-hasPermi="['funcdept:sample:sampleHandover:handover']" plain @click="addWindow()">样本交接</el-button>
			</el-col>
		</el-row>
		<!-- 表格数据 -->
		<div class="table-box" >
			<el-table :data="tableData" :border="true" :stripe="true">
				<el-table-column type="selection" align="center"></el-table-column>
				<el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
				<el-table-column prop="patientcode" label="体检号" align="center"></el-table-column>
				<el-table-column prop="patientname" label="姓名" align="center"></el-table-column>
				<el-table-column label="性别" align="center">
					<template slot-scope="scope">
						{{ scope.row.idSex == 0 ? "男" : "女" }}
					</template>
				</el-table-column>
				<el-table-column prop="count" label="样本个数" align="center"></el-table-column>
				<el-table-column prop="deliveryName" label="交接人" align="center"></el-table-column>
				<el-table-column prop="recipient" label="承接人" align="center"></el-table-column>
				<el-table-column prop="deliveryTime" label="交接时间" align="center"></el-table-column>
			</el-table>
		</div>

		<!-- dialog新增对话框 -->
		<el-dialog :visible.sync="showDialog" title="样本交接" append-to-body width="1200px"  :close-on-click-modal="false">
			<el-form label-width="64px" :inline="true" hide-required-asterisk :model="queryParam" >
				<el-form-item label="体检号" style="margin-right:24px; padding: 0;">
					<el-input placeholder="请输入体检号回车查询" style="width:160px;" clearable v-model="queryParam.patientCode" @keyup.enter.native="handleQuerys"></el-input>
				</el-form-item>
				<el-form-item label="交接人" style="margin-right:24px; padding: 0;">
					<el-input style="width:120px;" v-model="queryParam.name" :disabled="true"></el-input>
				</el-form-item>
        <el-form-item label="承接人">
          <input-select :selectData="revPersonIdData" clearable ref="inputSelect" selectWidth="180" @change="issueIdChange" ></input-select>
        </el-form-item>
				<el-form-item label="选择日期" label-width="auto" style="margin-right:24px; padding: 0;">
					<el-date-picker v-model="queryParam.delivertyTime" style="width:160px;" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期时间"></el-date-picker>
				</el-form-item>
				<el-form-item style="margin-right:0;">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuerys">搜索</el-button>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeList">删除</el-button>
				</el-form-item>
			</el-form>
			<!-- 内表格 -->
			<el-table :data="tableList" :border="true" :stripe="true" style="height: 600px"
			:header-cell-style="{'text-align':'center'}" @selection-change="handleSelectionChange" >
				<el-table-column type="selection" align="center"></el-table-column>
				<el-table-column type="index" label="序列" width="55"  align="center"></el-table-column>
				<el-table-column prop="patientcode" label="体检号"  align="center"></el-table-column>
				<el-table-column prop="patientname" label="姓名"  align="center"></el-table-column>
				<el-table-column label="性别" align="center">
					<template slot-scope="scope">
						{{ scope.row.idSex == 0 ? "男" : "女" }}
					</template>
				</el-table-column>
				<el-table-column prop="examfeeitemName" label="项目名称" align="center" show-overflow-tooltip></el-table-column>
			</el-table>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="onOk()">保存</el-button>
				<el-button type="primary" plain @click="onReload()">重置</el-button>
				<el-button @click="onCancel">取消</el-button>
			</div>
		</el-dialog>

	</div>
</template>

<script>
  import { getListbycode, getListData, DeleteList, saveOrUpdate } from '@/api/funcdept/sample/sample_handover'
  import { getLoginName } from '@/api/report/health_handover/health_handover'
  import { unaudit } from '@/api/report/health_handover/profession_handover'


  export default{
		data(){
			return{
				total:10,//总数
				queryParams:{
          current:1,//初始页数
          size:10,//页值
          date: [],
				},
        queryParam:{
          current:1,//初始页数
          size:10,//页值
          name:undefined,
          patientCode:undefined,
          delivertyTime:undefined,
          recipient:undefined
        },
        revPersonIdData:{
          placeholder: '请输入输入码选择',
          key: '部门',//第一列标题
          value: '姓名',//第二列标题
          url: '/outside/sendRegister/getLqrData',//请求连接
          bindValue: '',
          queryData:'srm',
          firstName: 'inputCode',
          secondName: 'inCode',
        },
				tableData:[],
        tableList:[],
				showDialog:false,
			}
		},
    created() {
      this.getList(), this.getlogin()
    },
    methods:{
      //保存
      onOk(){
        this.loading = true;
        let patientCode=[], param={}

         param = {
          deliveryTime:this.queryParam.delivertyTime,
          patientCode:[],
          recipient:this.queryParam.recipient
        }

        this.tableList.forEach(el=>{
          param.patientCode.push(el.patientcode)
        })

        if(this.queryParam.delivertyTime == undefined) {
          this.$modal.msgWarning("日期不能为空!");
        }else if(this.queryParam.recipient == undefined){
          this.$modal.msgWarning("承接人不能为空!");
        }else{
          saveOrUpdate(param).then(response => {
            this.loading = false;
            this.onCancel()
            this.$modal.msgSuccess("样本交接成功!");
          });
        }

      },
      //承接人选项
      issueIdChange(value){
        this.queryParam.recipient = value.inCode
      },
      //分页查询
      getList() {
        this.loading = true;
        getListData(this.queryParams).then(response => {
          this.tableData = response.data.records;
          this.total = response.data.total;
          this.loading = false;
        });
      },
      //搜索
      handleQuery(){
        if (this.queryParams.date) {
          this.queryParams.startTime = this.queryParams.date[0]
          this.queryParams.endTime = this.queryParams.date[1]
        }else{
          this.queryParams.startTime = []
          this.queryParams.endTime = []
        }
        this.getList()
      },
      //重置
      resetQuery(){
      this.queryParams.date=''
      },
      //删除
      removeList(){
        this.ids.forEach(val => {
          this.tableList.forEach((el, index) => {
            if (val == el.id) {
              this.$delete(this.tableList,index)
            }
          })
        })
      },
      //重置
      onReload(){
        this.tableList = [],
        this.queryParam.patientCode=undefined
        this.queryParam.delivertyTime=undefined
        this.queryParam.recipient=undefined
        this.$refs.inputSelect.initData('')
        this.handleQuery();
      },
      //弹窗搜索
      handleQuerys(){
        let tjh
        this.tableList.forEach((item,index) => {
            tjh=item.patientcode
        });
        if(tjh == this.queryParam.patientCode){
          this.$alert("请勿重复交接同一样本!", "提醒", {
            confirmButtonText: "确定",
            type: "warning",
          })
        }else{
          this.loading = true;
          getListbycode(this.queryParam).then(response => {
            this.tableList.push( response.data )
            this.total = response.data.total;
            this.loading = false;
          });
        }

      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map((item) => item.id);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      //获取登录用户姓名
      getlogin(){
        this.loading = true;
        getLoginName().then(response => {
          this.queryParam.name = response.data;
          this.loading = false;
        });
      },
			addWindow(){
        this.showDialog=true;
			},
			onCancel(){
				this.showDialog=false;
        this.onReload()
			}
		}
	}
</script>
