<!-- 签单计划-申请签单计划 麦沃德科技 开发人:暴雨 -->
<template>
  <el-dialog :title="title" class="add-items" :visible.sync="open" width="1500px" style=" min-height:850px" append-to-body
    :close-on-click-modal="false" @close="cancel">
    <!--顶部Tab页-->
    <el-tabs type="border-card" style="height: 100%;">
      <!--签单内容-->
      <el-tab-pane label="签单内容">
        <el-tag style="width: 100%;margin-bottom: 10px;font-size: 15px">企业基本信息</el-tag>
        <el-form ref="form" :model="form" :inline="true" label-width="140px" hide-required-asterisk>
          <el-form-item label="订单名称" class="add-labels required" prop="ddmc">
            <el-input v-model="form.ddmc" @input="nameChange" placeholder="请输入单位名称" clearable style="width: 300px" />
          </el-form-item>
          <el-form-item label="客户单位名称" prop="khdwmcid">
            <input-select :selectData="selectData" @change="selectChange" :initialValue="form.khdwmc"></input-select>
          </el-form-item>
          <el-form-item label="输入码" prop="inputCode">
            <el-input v-model="form.inputCode" readonly placeholder="输入订单名称自动获取" clearable style="width: 300px" />
          </el-form-item>

          <el-form-item label="单位地址" prop="khdwdz">
            <div class="address-box">
              <span class="address-title">省</span>
              <el-select v-model="form.province" placeholder="请选择省" @change="ProvinceChange" style="width: 100%">
                <el-option v-for="options in ProvinceOptions" :key="options.zoneCode" :label="options.zoneName"
                  :value="options.zoneCode" />
              </el-select>
              <span class="address-title">市</span>
              <el-select v-model="form.city" placeholder="请选择市" :disabled="!form.province" @change="CityChange"
                style="width: 100%">
                <el-option v-for="options in CityOptions" :key="options.zoneCode" :label="options.zoneName"
                  :value="options.zoneCode" />
              </el-select>
              <span class="address-title">区</span>
              <el-select v-model="form.district" placeholder="请选择区" :disabled="!form.city" @change="DistrictChange"
                style="width: 100%">
                <el-option v-for="options in DistrictOptions" :key="options.zoneCode" :label="options.zoneName"
                  :value="options.zoneCode" />
              </el-select>
              <span class="address-title">街道</span>
              <el-select v-model="form.street" placeholder="请选择街道" :disabled="!form.district" @change="StreetChange"
                style="width: 100%">
                <el-option v-for="options in StreetOptions" :key="options.zoneCode" :label="options.zoneName"
                  :value="options.zoneCode" />
              </el-select>
            </div>
          </el-form-item>
          <el-form-item label="是否新单" prop="newOrder" class="add-labels required">
            <el-select v-model="form.newOrder" clearable style="width: 300px" placeholder="请选择">
              <el-option :value="1" label="是"></el-option>
              <el-option :value="0" label="否"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="年度" prop="yearFlag" class="add-labels required">
            <el-input style="width: 280px" v-model="form.yearFlag" placeholder="请输入" clearable />
          </el-form-item>
          <el-form-item  label="社会信用代码" prop="socialCreditCode" class="add-labels required">
            <el-input style="width: 300px" v-model="form.socialCreditCode" placeholder="请输入" clearable />
          </el-form-item>
          <el-form-item label="签单类型" prop="orderType" class="add-labels required">
            <input-select :selectWidth="300" :selectData="typeSelectData" @change="typeSelectChange"
              :initialValue="form.saleName"></input-select>
          </el-form-item>

          <!----------------------------------------------------签单人------------------------------------------------------------------>
          <el-tag style="width: 100%;margin-bottom: 10px;font-size: 15px">签单申请人信息</el-tag>
          <el-form-item label="所属部门" prop="deptName" class="add-labels required">
            <input-select :selectWidth="300" :selectData="depSelectData" @change="depSelectChange"
              :initialValue="form.saleName"></input-select>

          </el-form-item>
          <el-form-item label="业务员" prop="salesName">
            <input-select :selectWidth="300" :selectData="saleSelectData" @change="saleSelectChange"
              :initialValue="form.saleName"></input-select>
          </el-form-item>
          <el-form-item label="保护日期" prop="protectStartDate" class="add-labels required">
            <el-date-picker v-model="form.protectStartDate" style="width: 300px;" value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime" placeholder="请选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="保护截止日期" prop="protectEndDate" class="add-labels required">
            <el-date-picker v-model="form.protectEndDate"  style="width: 300px;" value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime" placeholder="请选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="申请人" prop="applierName">
            <el-input readonly v-model="form.applierName" placeholder="请输入申请人" clearable style="width: 300px" />
          </el-form-item>
          <el-tag style="width: 100%;margin-bottom: 10px;font-size: 15px">预计签单信息</el-tag>
          <el-form-item label="签单人数" prop="orderPeople">
            <el-input-number v-model="form.orderPeople" controls-position="right" :min="1" :max="999"
              style="width: 300px" />
          </el-form-item>
          <el-form-item label="签单金额" prop="orderMoney">
            <el-input-number v-model="form.orderMoney" controls-position="right" :min="1" :max="999"
              style="width: 300px" />
          </el-form-item>
          <el-form-item label="签单日期" prop="dealDate">
            <el-date-picker v-model="form.dealDate" style="width: 300px;" value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime" placeholder="请选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="体检结束日期" prop="examedDate">
            <el-date-picker v-model="form.examedDate" style="width: 300px;" value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime" placeholder="请选择日期"></el-date-picker>
          </el-form-item>
          <el-tag style="width: 100%;margin-bottom: 10px;font-size: 15px">签单跟踪进度</el-tag>
          <el-form-item label="当前状态" prop="status">
            <el-select v-model="form.status" style="width:300px ;" placeholder="">
              <el-option label="失效 " :value="-1"></el-option>
              <el-option label="待提交" :value="0"></el-option>
              <el-option label="撞单处理中" :value="1"></el-option>
              <el-option label="待审核" :value="2"></el-option>
              <el-option label="审核通过" :value="3"></el-option>
              <el-option label="审核驳回" :value="4"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属分中心" prop="branchId" class="add-labels required">
            <el-input-number v-model="form.branchId" controls-position="right" :min="1" :max="999" style="width: 300px" />
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <!--签单联系人-->
      <el-tab-pane label="签单联系人">
       
        <el-row :gutter="20" style="height: 100%;">
          <!--左侧表格-->
      
          <el-col :span="16" style="height: 100%;">
              <el-table :data="tableData" style="display:inline-block;flex-grow: 1;" v-loading="loading" :border="true"
              :stripe="true" height="90%" @selection-change="handleSelectionChange">
              <el-table-column type="selection" align="center" fixed="left"></el-table-column>
              <el-table-column type="index" label="序列" width="55" align="center" fixed="left"></el-table-column>
              <el-table-column prop="name" label="姓名" min-width="100px" align="center" fixed="left"></el-table-column>
              <el-table-column prop="phone" label="手机号" min-width="160px" align="center"></el-table-column>
              <el-table-column prop="telephone" label="办公电话" min-width="160px" align="center"
                show-overflow-tooltip></el-table-column>
              <el-table-column prop="duties" label="职务" min-width="160px" align="center"
                show-overflow-tooltip></el-table-column>
            </el-table>
           
          </el-col>
     
          <!--右侧表单-->
          <el-col :span="8">
            <el-tag style="width: 100%;margin-bottom: 10px;font-size: 15px">联系人信息</el-tag>
            <el-form ref="form" :model="form" :inline="true" label-width="100px" hide-required-asterisk>
              <el-form-item label="姓名" prop="linker">
                <el-input v-model="form.tjid" placeholder="请输入姓名" clearable style="width: 320px" />
              </el-form-item>
              <el-form-item label="职务" prop="post">
                <el-select v-model="form.tjid" clearable style="width: 320px" placeholder="请选择">
                  <el-option v-for="item in tjlx" :key="item.id" :value="item.id" :label="item.text"></el-option>
                </el-select>
              </el-form-item>
              <!-- <el-form-item label="重要级别" prop="tjid">
                <el-select v-model="form.tjlx" clearable style="width: 320px" placeholder="请选择">
                  <el-option v-for="item in tjlx" :key="item.id" :value="item.id" :label="item.text"></el-option>
                </el-select>
              </el-form-item> -->
              <el-form-item label="手机号码" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入手机号码" clearable style="width: 320px" />
              </el-form-item>
              <el-form-item label="电子邮件" prop="email">
                <el-input v-model="form.tjid" placeholder="请输入电子邮件" clearable style="width: 320px" />
              </el-form-item>
              <el-form-item label="个人爱好" prop="hobby">
                <el-input v-model="form.ywbz" clearable style="width: 320px" type="textarea" :rows="3" />
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" clearable style="width: 320px" type="textarea" :rows="3" />
              </el-form-item>
              <div style="margin-left: 200px">
                <el-button type="primary" @click="submitForm">新增</el-button>
                <el-button type="primary" plain @click="reset">保存</el-button>
                <el-button @click="cancel">删除</el-button>
              </div>
            </el-form>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <!--底部按钮-->
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">提交申请</el-button>
      <el-button type="primary" plain @click="reset">保存</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import pinyin from "@/utils/pinyin.js";
import { addItem, updatedData,getDetailData } from "@/api/sale/order_management/signing_plan.js"
import { getZoneData, getUnitArea, } from '@/api/customer/customer_list.js'
export default {
  data() {
    return {
      title: '',


      userType: [],
      //体检类型
      tjlx: [],
      loading: false,
      single: true,//必选且单选
      multiple: true,//必选
      total: 10,//总数
      queryParams: {
        current: 1,//初始页数
        size: 10,//页值
      },
      tableData: [

      ],
      // 客户单位名称输入码选择
      selectData: {
        placeholder: '请输入输入码选择',
        key: '输入码', //第一列标题
        value: '客户单位名称', //第二列标题
        url: '/sell/createorder/getKhdwmcData', //请求连接
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        bindValue: '',
        firstName: 'khdwsrm', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'khdwmc', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // 业务员输入码选择
      saleSelectData: {
        placeholder: '请输入输入码选择',
        value: '客户单位名称', //第二列标题
        url: '/system/user/getListByKey', //请求连接
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        bindValue: '',
        firstName: 'username', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // 部门输入码选择
      depSelectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '部门名称', //第二列标题
        url: '/system/dept/list', //请求连接
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        bindValue: '',
        firstName: 'deptId', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'deptName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      // 签单类型输入码选择
      typeSelectData: {
        placeholder: '请输入输入码选择',
        key: '输入码',
        value: '体检类别', //第二列标题
        url: 'data/businessCat/list', //请求连接
        selectWidth: 280, //选择器宽度（选填，默认230）不加px
        bindValue: '',
        firstName: 'typeId', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'typeName', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key', //向接口传递的参数名
      },
      province: '',
      city: '',
      district: '',
      street: '',
      // 单位省数据
      ProvinceOptions: [],
      CityOptions: [],
      DistrictOptions: [],
      StreetOptions: [],
      form: {
        street: '',
        ddmc: '',
        inputCode: '',
      },
      open: false,
    }
  },
  created() {
    this.getZoneData(1)
  },
  methods: {
    //查询列表
    getList() { },
    //交接
    submitForm() {
      this.form.khdwdz = this.province + this.city + this.district + this.street
      if (this.form.id != null) {
        addItem(this.form).then(() => {
          this.$modal.msgSuccess("修改成功");
          this.open = false;
        });
      } else {
        addItem(this.form).then(() => {
          this.$modal.msgSuccess("添加成功");
          this.open = false;
        });
      }
    },
    // 客户单位名称返回值
    selectChange(value) {
      this.form.khdwmcid = value.id
      this.form.khdwmc = value.khdwmc
      // this.form.ddmc = value.khdwmc + new Date().getFullYear()
      // this.form.khdwdh = value.khdh
    },
    //业务员名称返回值
    saleSelectChange(value) {
      this.saleSelectData.bindValue = value.username
      this.form.salesId = value.id
      this.form.salesName = value.username
      // this.form.ddmc = value.khdwmc + new Date().getFullYear()
      // this.form.khdwdh = value.khdh
    },
    //部门名称返回值
    depSelectChange(value) {
      
      this.depSelectData.bindValue = value.deptName
      this.form.deptId = value.deptId
     
    
      // this.form.ddmc = value.khdwmc + new Date().getFullYear()
      // this.form.khdwdh = value.khdh
    },
    //类型名称返回值
    typeSelectChange(value) {
      
      this.typeSelectData.bindValue = value.typeName
      this.form.orderTypeId = value.typeId
      this.form.orderType = value.typeName
      // this.form.ddmc = value.khdwmc + new Date().getFullYear()
      // this.form.khdwdh = value.khdh
    },
    //获取销售人员类型
    getUserType() {
      userType().then(response => {
        
        this.userType = response.data
      })
    },
    //获取地区数据
    getZoneData(level, zoneCode,) {
      const params = {
        size: 9999,
        current: 1,
        level: level,
        zoneCode: zoneCode
      }
      //leave标识层级 
      getZoneData(params).then(response => {
        
        if (level == 1) {

          this.ProvinceOptions = response.data
        }
        else if (level == 2) {
          this.CityOptions = response.data
        }
        else if (level == 3) {
          this.DistrictOptions = response.data
        } else {
          this.StreetOptions = response.data
        }
      })

    },


    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //弹窗开启
    handerWindow(id) {
      this.open = true;
      this.title = '申请签单计划';
      this.reset()
    
      if(id){  
      const id = id 
      this.title = "编辑签单计划";
      getDetailData(id).then(response => {
        
        if(response.code==200){
          this.form = response.data;  
        }
      });
      }
    },
    nameChange(value) {
      
      this.form.inputCode = pinyin(value)
    },
    // 搜索
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    //删除
    handleremove() {
      this.ids.forEach(val => {
        this.tableData.forEach((el, index) => {
          if (val == el.id) {
            this.$delete(this.tableData, index)
          }
        })
      })
    },

    // 省改变
    ProvinceChange(zoneCode) {
      //用于对客户单位地址拼接作用
      for (var i in this.ProvinceOptions) {
        if (this.ProvinceOptions[i].zoneCode == zoneCode) {
          this.province = this.ProvinceOptions[i].zoneName
        }
      }
      this.form.pcode = zoneCode

      this.city = '', this.district = '', this.street = '',
        this.form.city = undefined; this.form.district = undefined; this.form.street = undefined
      const leave = 2
      const zone = zoneCode
      this.form.province = zoneCode
      this.getZoneData(leave, zone,)
    },
    // 市改变
    CityChange(zoneCode) {
      //用于对客户单位地址拼接作用
      for (var i in this.CityOptions) {
        if (this.CityOptions[i].zoneCode == zoneCode) {
          this.city = this.CityOptions[i].zoneName
        }
      }
      this.form.ccode = zoneCode
      this.form.street = undefined; this.form.district = undefined; this.district = '', this.street = ''
      const leave = 3
      const zone = zoneCode
      this.form.city = zoneCode
      this.getZoneData(leave, zone,)
    },
    // 区改变
    DistrictChange(zoneCode) {
      //用于对客户单位地址拼接作用
      for (var i in this.DistrictOptions) {
        if (this.DistrictOptions[i].zoneCode == zoneCode) {
          this.district = this.DistrictOptions[i].zoneName
        }
      }
      this.form.dcode = zoneCode
      this.form.street = undefined, this.street = ''
      const leave = 4
      const zone = zoneCode
      this.form.district = zoneCode
      this.getZoneData(leave, zone,)
    },
    //街道改变
    StreetChange(zoneCode) {
     
      //用于对客户单位地址拼接作用
      this.form.scode = zoneCode
      for (var i in this.StreetOptions) {
        if (this.StreetOptions[i].zoneCode == zoneCode) {
          this.street = this.StreetOptions[i].zoneName
        }
      }

    },
    //重置
    reset() { },
    //取消
    cancel() {
      this.open = false;
      // this.$refs.inputSelect.initData('')
      this.reset();
    },
  }
}
</script>
<style lang="scss">
.address-box {
  width: 1210px;
  display: flex;
  justify-content: space-between;

  .address-title,
  .el-select {
    margin-right: 12px;
  }

  .el-select {
    flex: 1;

    .el-input__inner {
      border-color: #DCDFE6 !important;

      &:focus {
        border-color: #{'var(--theme)'} !important;
      }

      .add-labels {
        width: 120px;
        color: #FF2727 !important;
        background: #F6F9FF !important;
        font-size: 14px;
      }
    }
  }
}</style>
