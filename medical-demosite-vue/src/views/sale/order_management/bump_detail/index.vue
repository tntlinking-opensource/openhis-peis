<!-- 撞单排查详情  开发人：麦沃德科技矢北 -->
<template>
   <div class="app-container flex-direction-column general-inspection" style=" min-height: auto; padding: 0; overflow: auto">
  <el-row  :gutter="10" style="height: 100%">
    <el-col :span="15" style="height: 100%">
        <div  style="height:51%;border:1px solid #D4D6D9;padding: 12px;  overflow: auto">
          <!-- 基本信息表单 -->
          <span class="label-title">企业基本信息</span>
          <el-form ref="form" size="small" :model="basicForm" :inline="true" label-width="80px" hide-required-asterisk>
            <el-form-item label="单位名称" prop="khdwmc">
              <el-input v-model="basicForm.dwmcid" placeholder="请输入订单名称" clearable style="width:280px" />
            </el-form-item>
            <el-form-item label="签单计划" prop="qdjh">
              <el-input v-model="basicForm.qdjh" placeholder="请输入订单名称" clearable style="width: 280px" />
            </el-form-item>
            <el-form-item label="单位地址" prop="qdjh">
              <el-input v-model="basicForm.qdjh" placeholder="请输入订单名称" clearable style="width: 280px" />
            </el-form-item>
            <el-form-item label="输入码" prop="qdjh">
              <el-input v-model="basicForm.qdjh" placeholder="请输入订单名称" clearable style="width: 280px" />
            </el-form-item>
         
            <el-form-item label="所在区域" prop="szqy">
              <el-input v-model="basicForm.szqy" placeholder="请输入订单名称" clearable style="width: 280px" />
            </el-form-item>
            <el-form-item label="成立日期" prop="clrq">
              <el-date-picker style="width:280px;" v-model="basicForm.setDate" placeholder="请选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="所属部门" prop="jyfw">
              <el-input v-model="basicForm.jyfw" placeholder="请输入订单名称" clearable style="width: 280px" />
            </el-form-item>
            <el-form-item label="业务员" prop="clrq">
              <el-input v-model="basicForm.clrq" placeholder="请输入订单名称" clearable style="width: 280px" />
            </el-form-item>
            <el-form-item label="登记日期" prop="qygm">
              <el-date-picker style="width:280px;" v-model="basicForm.registerDate" placeholder="请选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="客户类型" prop="jyfw">
              <el-input v-model="basicForm.jyfw" placeholder="请输入订单名称" clearable style="width: 280px" />
            </el-form-item>
            <el-form-item label="签单数量" prop="clrq">
              <el-input v-model="basicForm.clrq" placeholder="请输入订单名称" clearable style="width: 280px" />
            </el-form-item>
            <el-form-item label="新旧分类" prop="qygm">
              <el-select v-model="basicForm.fenlei" style="width: 280px" placeholder="请选择">
                <el-option label="新" :value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <div style="height:3px;"></div>
        <div style="height:48%;border:1px solid #D4D6D9;padding: 12px;  overflow: auto">       
          <el-form ref="queryForm" :model="queryForm" :inline="true" label-width="80px" hide-required-asterisk
            v-loading="popLoading">
            <el-form-item label="体检号" prop="tjid">
              <el-input size="small" style="width:240px;" v-model="queryForm.tjid"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input size="small" style="width:140px;" v-model="queryForm.phone"></el-input>
            </el-form-item>
            <el-form-item style="padding-left: 10px;">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <div class="table-box" style="height: 70%;" >
            <el-table :data="tableList" v-loading="loading" align="center" height="100%" :stripe="true"
              @selection-change="handleSelectionChange" >
              <el-table-column type="selection" align="center" />
              <el-table-column prop="dwmc" label="单位名称" width="200" align="center" />
              <el-table-column prop="ssywy" label="所属业务员" width="100" align="center" />
              <el-table-column prop="bm" label="所属部门" width="120" align="center" />
              <el-table-column prop="qd" label="是否签单" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.xb == 0">是</span>
                  <span v-if="scope.row.xb == 1">否</span>
                </template>
              </el-table-column>
              <el-table-column prop="xm" label="姓名" width="100px" align="center" />
              <el-table-column prop="zw" label="职务" width="100px" align="center" />
              <el-table-column prop="dh" label="办公电话" width="120px" align="center" />
              <el-table-column prop="jtdh" label="家庭电话" width="120px" align="center" />
              <el-table-column prop="sj" label="手机" width="120px" align="center" />
            </el-table>  
          </div>
          <pagination v-show="total > 0" :total="total" :page.sync="queryForm.current" :limit.sync="queryForm.size" />
      </div>
     
    </el-col>
    <el-col :span="9"  style="height: 100%">
      <div style="height:51%;border:1px solid #D4D6D9;padding: 12px;  overflow: auto" >   
          <span class="label-title">联系人信息</span>
          <div class="table-box" style="height: 90%;">
            <el-table :data="familyList" v-loading="loading" align="center" :stripe="true"
              @selection-change="handleSelectionChange" height="80%" >
              <el-table-column type="selection" align="center" />
              <el-table-column prop="lxdh" label="姓名" width="100px" align="center" />
              <el-table-column prop="lxdh" label="职务" width="100px" align="center" />
              <el-table-column prop="lxdh" label="办公电话" width="120px" align="center" />
              <el-table-column prop="lxdh" label="家庭电话" width="120px" align="center" />
              <el-table-column prop="lxdh" label="手机" width="120px" align="center" />
            </el-table>
            <pagination v-show="total > 0" :total="total" :page.sync="queryForm.current" :limit.sync="queryForm.size" />
          </div>
        </div>
        <div style="height:2px;"></div>
        <div style="height:48%;border:1px solid #D4D6D9;padding:0,18px; overflow: auto">
          <span class="label-title">处理内容</span>
          <el-form ref="form" size="small" :model="submitForm" :inline="true" label-width="110px" hide-required-asterisk>
            <el-form-item label="是否撞单" label-width="110px" prop="isBump">
              <el-select style="width:150px" v-model="submitForm.isBump" placeholder="请输入">
                <el-option label="是" :value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label-width="110px" label="保护截止日期" prop="jzdate">
              <el-date-picker style="width:160px;" v-model="submitForm.jzdate" placeholder="请选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item label-width="110px" label="交由部门处理" prop="subDepart">
              <el-select style="width:150px;" v-model="submitForm.subDepart" placeholder="请输入">
                <el-option label="是" :value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="撞单原因">
              <el-input type="textarea" rows="3" resize="none" style="width: 350px ;" v-model="submitForm.reason"
                placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input type="textarea" rows="3" resize="none" style="width: 350px ;" v-model="submitForm.note"
                placeholder="请输入"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button size="mini" type="primary" @click="submitForm">保存</el-button>
            <el-button size="mini" type="primary" @click="reset" plain>重置</el-button>
            <el-button size="mini" @click="cancel">返回重填</el-button>
          </div>
        </div>
    </el-col>
  </el-row>
  </div>

</template>

<script>
export default {
  name:'detail',
  data() {
    return {
      basicForm: {},
      open: true,
      queryForm: {},
      popLoading: undefined,
      loading: false,
      tableList: [{},{},
      {},
      {},
      {},
      {},
    
      ],
      familyList:[],
      submitForm: {},
      total:5,
    }

  },
  methods: {
    //查询
    handleQuery() {

    },
    //重置
    resetQuery() {
      this.queryForm.tjid=undefined,
      this.queryForm.phone=undefined
      this.resetForm("queryForm");
    },
    handleSelectionChange(selection) {

    },
    reset()
    {
     
    },
    cancel()
    {

    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 0;
}
.dialog-footer{
  padding-bottom: 6px;
  float: right;
}
.label-title{
  display: block;
    font-weight: 600;
    font-size: 14px;
    line-height: 20px;
    color: #333333;
    margin-bottom: 12px;
}
</style>
<style>