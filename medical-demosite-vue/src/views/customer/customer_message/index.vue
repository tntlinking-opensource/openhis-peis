<!-- 客户消息 麦沃德科技 开发人: 暴雨 -->
<template>
    <div class="app-container flex-direction-column">
        <el-form size="small" :inline="true" >
            <el-form-item label="姓名" style="margin-right:16px;">
                <el-input style="width:180px;" placeholder="请输入姓名" v-model="form.khdwmc" clearable></el-input>
            </el-form-item>
            <el-form-item label="手机号" style="margin-right:16px;">
                <el-input style="width:180px;" placeholder="请输入手机号" v-model="form.khlxdh" clearable></el-input>
            </el-form-item>
            <el-form-item label="消息类型" style="margin-right:16px;">
              <el-select style="width:200px;" v-model="form.isExamed" clearable placeholder="请选择">
                <el-option :key="0" label="未来检" :value="0" />
                <el-option :key="1" label="已来检" :value="1" />
              </el-select>
            </el-form-item>
            <el-form-item label="选择日期" prop="date">
              <el-date-picker v-model="queryParams.date" style="width: 360px;" value-format="yyyy-MM-dd" type="daterange"
                              range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button size="mini" type="primary" icon="el-icon-plus" plain @click="handleWindow()"
                    v-hasPermi="['customer:inspectionTracking:handle']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button size="mini" type="success" icon="el-icon-edit-outline" plain @click="inspectedWindow()"
                    v-hasPermi="['customer:inspectionTracking:inspected']">编辑</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button size="mini" type="danger" icon="el-icon-delete" plain @click="inspectedWindow()"
                         v-hasPermi="['customer:inspectionTracking:inspected']">删除</el-button>
            </el-col>
			<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <div class="table-box">
            <el-table :data="tableData" v-loading="loading" :border="true" :stripe="true" @selection-change="handleSelectionChange"
                row-key="id" height="100%">
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                <el-table-column prop="khdwmc" label="体检号" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="khdwlxr" label="姓名" align="center"></el-table-column>
                <el-table-column prop="khlxdh" label="手机号" align="center"></el-table-column>
                <el-table-column prop="sctjksrq" label="消息类型" align="center"></el-table-column>
                <el-table-column prop="clr" label="通知时间" align="center"></el-table-column>
                <el-table-column prop="xcgtrq" label="消息内容" align="center"></el-table-column>
                <el-table-column label="通知状态" align="center" prop="clzt">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.clzt == 0" type="success">已通知</el-tag>
                        <el-tag v-else type="danger">等待通知</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center"   fixed="right" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" style="color: #ffba00" @click="handleUpdate(scope.row)"
                               v-hasPermi="['basis:chargeCategory:edit']">编辑</el-button>
                    <el-button size="mini" type="text" style="color: #ff2727" @click="handleDelete(scope.row)"
                               v-hasPermi="['basis:chargeCategory:remove']">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 分页 -->
		<pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
			@pagination="getList" />
        <handleItems ref="handleItems" @getList="getList"></handleItems>
    </div>
</template>

<script>
import handleItems from './handle.vue';
import { getList,setExamed } from '@/api/customer/inspection_tracking'
    export default{
        components:{ handleItems },
        data(){
            return{
                selection:{},
                // 遮罩层
                loading: false,
                // 选中数组
                ids: [],
                // 总条数
                total: 30,
                // 非单个禁用
                single: true,
                // 非多个禁用
                multiple: true,
                // 显示搜索条件
                showSearch: true,
                // 查询参数
                queryParams: {
                    current: 1,
                    size: 10,
                    sfxmsrm: undefined,
                    examfeeitemName: undefined,
                    tjlx: undefined,
                    xb: undefined,
                    idDepart: undefined,
                    examfeeitemCode: undefined,
                    idLabtype: undefined,
                },
                form:{
                  khdwmc:"",
                  khlxdh:"",
                  clr:"",
                  startTime:"",
                  endTime:"",
                  isExamed:"",
                },
                tableData:[]
            }
        },
      created() {
          this.getList()
      },
      methods:{
          // 查询列表
          getList() {
            this.loading = true;
            getList(this.form).then(response => {
              this.tableData = response.data.records;
              this.total = response.data.total;
              this.loading = false;
            });
          },
            // 搜索
            handleQuery() {
              this.form.current = 1;
              if (this.form.date) {
                this.form.startTime = this.form.date[0] + " 00:00:00"
                this.form.endTime = this.form.date[1] + " 23:59:59"
              } else {
                this.form.startTime = undefined
                this.form.endTime = undefined
              }
              this.getList();
            },
            //重置
            resetQuery(){

            },
            //新增
            handleWindow(){
              this.$refs.handleItems.handleWindow();
            },
            //设置已检
            inspectedWindow(){

            },
          // 多选框选中数据
          handleSelectionChange(selection) {
            this.ids = selection.map((item) => item.id);
            this.rows = selection;
            this.single = selection.length != 1;
            this.multiple = !selection.length;
          },
        }
    }
</script>
