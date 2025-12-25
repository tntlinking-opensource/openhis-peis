<!-- PACS登记  开发人：麦沃德科技矢北 -->
<template>
  <el-row>
    <el-col :span="10">
      <div class="app-container flex-direction-column">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <span style=" font-family: ' PingFang SC';font-style: normal;font-weight: 400;font-size: 14px;
        line-height:14px;">体检号:</span> <el-input v-model="num" size="mini" style="width:150px"
              placeholder="请输入体检号"></el-input>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd"
              v-hasPermi="['PACS:regist:read']">读取身份证</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="handleAdd"
              v-hasPermi="['PACS:regist:clear']">清空</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-refresh-right" @click="handleAdd"
              v-hasPermi="['PACS:regist:flash']">刷新</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain size="mini" icon="el-icon-circle-check" @click="handleAdd"
              v-hasPermi="['PACS:regist:booking']">完成预约</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain size="mini" icon="el-icon-circle-check" @click="handleAdd"
              v-hasPermi="['PACS:regist:register']">完成登记</el-button>
          </el-col>
        </el-row>
        <div style="border:1px solid #D4D6D9;height: 100%;">
          <h3 style="margin-left:20px;">基本信息</h3>
          <el-form v-loading="popLoading" ref="form" border :model="form" :inline="true" hide-required-asterisk>
            <el-form-item prop="testdate" label-width="90px" label="体检时间">
              <el-date-picker v-model="form.value1" type="date" style="width:240px" placeholder="选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item prop="date" label-width="90px" label="预约时间">
              <el-date-picker v-model="form.value2" type="date" style="width:240px" placeholder="选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item prop="number" label-width="90px" label="体检号">
              <el-input v-model="form.number" style="width:240px" placeholder="请输入体检号"></el-input>
            </el-form-item>
            <el-form-item prop="type" label-width="90px" label="体检类型">
              <input-select :selectData="labTypeData" style="width:240px" @change="labTypeChange"></input-select>
            </el-form-item>
            <el-form-item prop="name" label-width="90px" label="姓名">
              <el-input v-model="form.name" style="width:240px" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item prop="phy" label-width="90px" label="健康体检">
              <el-input v-model="form.phy" style="width:240px" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item prop="gender" label-width="90px" label="性别">
              <el-select v-model="form.gender" style="width:240px;" placeholder="请选择">
            <el-option :value="1" label="男"></el-option>
            <el-option :value="0" label="女"></el-option>
          </el-select>
            </el-form-item>
            <el-form-item prop="age" label-width="90px" style="text-align:center" label="年龄">
              <el-input-number v-model="form.age" style="width:240px"  :min="1" :max="100" label="请输入"></el-input-number>
            </el-form-item>
            <el-form-item prop="birth" label-width="90px" label="生日">
              <el-date-picker v-model="form.birth" type="date" style="width:240px" placeholder="选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item prop="marriage" label-width="90px" label="婚姻状况">
              <el-select v-model="form.marriage" style="width:240px;" placeholder="请选择">
            <el-option :value="1" label="未婚"></el-option>
            <el-option :value="2" label="已婚（含同居）"></el-option>
            <el-option :value="3" label="丧偶"></el-option>
            <el-option :value="4" label="离异"></el-option>
            <el-option :value="5" label="其他"></el-option>
          </el-select>
            </el-form-item>
            <el-form-item prop="phone" label-width="90px" label="手机号">
              <el-input v-model="form.phone" style="width:240px" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item prop="note" label-width="90px" label="备注">
              <el-input type="textarea" resize="none" rows="3" v-model="form.note" style="width:600px" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="黑名单" label-width="90px" prop="check">
              <el-checkbox v-model="check" label=""></el-checkbox>
            </el-form-item>
            <el-form-item v-if="check" prop="blackList" label-width="90px" label="黑名单备注">
              <el-input type="textarea" resize="none" rows="3" v-model="form.blackList" style="width:600px" placeholder="请输入"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-col>
    <el-col :span="14">
      <div class="app-container flex-direction-column">
        <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd" >添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain size="mini" icon="el-icon-delete" >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain size="mini" icon="el-icon-receiving" >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" icon="el-icon-refresh-right" >刷新</el-button>
      </el-col>
    </el-row>
    <div class="table-box">
      <el-table style="width:100%" :data="tableList"  v-loading="loading" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="depName" label="收费项目" align="center" />
        <el-table-column prop="check" label="检查部位" align="center" />
        <el-table-column prop="regist" label="登记" align="center">
          <template slot-scope="scope">
            <el-checkbox v-model="tRegist"   v-if="scope.row.regist == 1" ></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column prop="checked" label="已检" align="center" >
          <template slot-scope="scope">
            <el-checkbox v-model="fRegist"   v-if="scope.row.regist == 0" ></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column prop="department" label="科室" align="center" />
        <el-table-column prop="register" label="登记人" align="center" />
        </el-table>
        </div>
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" v-show="showSearch">
          <el-form-item label="体检号" prop="tjh">
				<el-input v-model="queryParams.tjh" placeholder="请输入体检号" clearable style="width: 140px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
      <el-form-item label="姓名" prop="name">
				<el-input v-model="queryParams.name" placeholder="请输入体检号" clearable style="width: 120px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
      <el-form-item label="电话" prop="tel">
				<el-input v-model="queryParams.tel" placeholder="请输入体检号" clearable style="width: 160px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
      <el-form-item label="身份证" prop="id">
				<el-input v-model="queryParams.id" placeholder="请输入身份证" clearable style="width: 160px;"
					@keyup.enter.native="handleQuery" />
			</el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
        </el-form>
        <div class="table-box">
          <el-table style="width:100%" :data="tableList2" v-loading="loading"  @selection-change="handleSelectionChange" >
       <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序列" width="55" type="index" align="center" />
        <el-table-column prop="depName" label="体检号" align="center" />
        <el-table-column prop="name" label="姓名" align="center" />
        <el-table-column prop="gender" label="性别" align="center" >
          <template slot-scope="scope">
						<div v-for="item in xbOptions" :key="item.id">
							<span v-if="item.id == scope.row.gender">{{ item.text }}</span>
						</div>
					</template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" align="center" />
        <el-table-column prop="tel" label="手机号" align="center" />
        <el-table-column prop="time" label="预约时间" align="center" />
        <el-table-column prop="idcard" label="身份证号" align="center" />
        </el-table>
          </div>
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
      @pagination="getList" />
        </div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  created() {
    this.getList();
  },
  data() {
    return {
      total:50,
      	// 性别
			xbOptions: [
				{ id: 0, text: "男" },
				{ id: 1, text: "女" },
				{ id: 2, text: "通用" }
			],
      showSearch:true,
      loading: true,
      queryParams:
      {
        tjh:undefined,
        name:undefined,
        tel:undefined,
        id:undefined
      },
      tableList2:[],
      tableList:[
        {
          register:"admin",
          regist:0,
        },
      ],
      fRegist:undefined,
      //检查
      check:'',
      //年龄
      age:'',
      // 科室名称
      labTypeData: {
        placeholder: '请输入输入码选择',
        key: '科室码',
        value: '科室名称',
        url: '/basconclusion/findAllDepartment',
        bindValue: '', //初始值(必传)

      },
      form: {
        value1: '',
        value2: '',
      },
      num: '',
      popLoading: false,
    }
  },
  methods: {
       // 查询列表
       getList() {
      this.loading = true;
        this.loading = false;
    },
    handleAdd() {

    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //增加行
    handleAdd()
    {
      var list = { index: this.tableList.length + 1, tjtcmc: undefined, spzt: undefined, idExamclass: undefined, tjlx: undefined, syxb: undefined, jhys: undefined, sfwc: undefined, zhjg: undefined, tcysjg: undefined, tczk: undefined, khdwmc: undefined, sfybd: undefined, sfyhtc: undefined, nlz: undefined, nld: undefined, fkfs: undefined, zytjlb: undefined, tjtcsrm: undefined, tjtcjc: undefined, forbid: undefined, }
      this.tableList.push(list)
    },
 // 科室选项
 labTypeChange(value) {
      this.queryParams.depName = value.name
      this.selectData1.bindValue = value.name;
    },
     // 多选框选中数据
     handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
      ///搜素
      handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
  }
}
</script>

<style>

</style>