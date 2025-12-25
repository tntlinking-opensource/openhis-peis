<!-- 团检专属卡放增加页面  开发人：麦沃德科技矢北 -->
<template>
  <div class="add-container" style="width:794px">
    <el-dialog :title="title" :visible.sync="open" class="add-charge" width="794px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="110px" hide-required-asterisk>
        <el-form-item label="卡类型" label-width="80px" prop="typeId">
          <el-select v-model="form.typeId" placeholder="请选择" @change="typeChange" style="width: 260px">
            <el-option style="width: 230px" v-for="options in typeOptions" :key="options.id" :label="options.text"
              :value="options.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="卡前缀" prop="cardPrefix">
          <el-input v-model="form.firstname" :readonly='true' placeholder="输入项目名称后自动生成" clearable style="width: 260px" />
        </el-form-item>
        <el-form-item label="卡标识" label-width="80px" prop="cardLogo">
          <el-input v-model="form.cardLogo" :readonly='true' placeholder="输入项目名称后自动生成" clearable style="width: 260px" />
        </el-form-item>
        <el-form-item label="有效期" prop="validity">
          <el-date-picker v-model="form.validity" type="datetime" value-format="yyyy-MM-dd HH:MM:SS" clearable style="width: 260px" placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="金额" label-width="80px" prop="balanceLimit">
          <el-input-number v-model="form.balanceLimit" controls-position="right" clearable style="width: 260px" :min="0"
            :max="999999"></el-input-number>
        </el-form-item>
        <el-form-item label="现有最大卡号" prop="zdkh">
          <el-input v-model="form.zdkh" :readonly='true' placeholder="输入项目名称后自动生成"  style="width: 260px" />
        </el-form-item>
        <el-form-item label="起始卡号" label-width="80px" prop="qskh">
          <el-input v-model="form.qskh" readonly placeholder="请输入流水"  style="width: 260px" />
        </el-form-item>
        <el-form-item label="发卡数量" prop="sl">
          <el-input-number v-model="form.sl" controls-position="right" clearable style="width: 260px" :min="1"
            :max="500"></el-input-number>

        </el-form-item>
        <el-form-item label="领取人" label-width="80px" prop="getterId">
          <input-select :selectData="selectDataLQR" style="width: 260px;" @change="selectChangeLQR"></input-select>
        </el-form-item>
        <el-form-item label="领取时间:" prop="getTime">
          <el-date-picker v-model="form.getTime" type="datetime" value-format="yyyy-MM-dd HH:MM:SS"  clearable style="width: 260px" placeholder="选择日期">
            </el-date-picker>
        </el-form-item>
        <el-form-item label="生成密码" label-width="80px" prop="generatePassword">
          <el-checkbox v-model="form.generatePassword" style="width: 260px" border />
        </el-form-item>
        <el-form-item label-width="110px" label="订单:" prop="orderId">
          <input-select :selectData="selectDataDD" v-model="form.orderId"  style="width: 260px" @change="selectChangeDD"></input-select>
        </el-form-item>
        <el-form-item label-width="80px" label="套餐:" prop="tcId">
          <input-select :disabled="tcJY"  :selectData="selectDataTC" :queryParams="this.query" style="width: 260px"
            @change="selectChangeTC"></input-select>
        </el-form-item>
        <el-form-item label="卡说明:" label-width="80px" prop="explain">
          <el-input v-model="form.cardState" resize="none" placeholder="请输入内容" clearable style="width: 642px"
            type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="备注:" label-width="80px" prop="note">
          <el-input v-model="form.memo" resize="none" placeholder="请输入内容" clearable style="width: 642px" type="textarea"
            :rows="3" />
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
import { cardTypeData, cardChangeData,addCard } from '@/api/finance/card/experience_card_management.js'
export default {
  data() {
    return {
      tcJY:true,
      ///记录表单时间的数据
      value1: '',
      form: {
        typeId :'',
        getterId:'',
        orderId:'',
        tcId :'',
        validity :undefined,
        getTime:undefined,
        qskh:'',
      },
      query:{
        id:undefined
      },
      //领取人套餐数据
      selectDataLQR: {
        placeholder: '请输入输入码选择',
        key: '科室',//第一列标题 
        value: '领取人',//第二列标题 
        url: '/finance/sendCard/getLqrData',//请求连接
        bindValue: '',
        queryData: 'srm',
        firstName: 'inputCode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'occupationSummary',//接口返回值对应第二列的参数名(不传默认为'name')
      },
      //订单数据
      selectDataDD: {
        placeholder: '请输入输入码选择',
        value: '订单名称',
        url: '/finance/sendCard/getAllOrderData',//请求连接
        bindValue: '',
        queryData: 'key',
        firstName: 'ddmc',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'ddmc'
      },
      //套餐数据
      selectDataTC: {
        placeholder: '请输入输入码选择',
        value: '套餐名称',//第二列标题 
        url: '/finance/sendCard/getOrderMealData',//请求连接
        bindValue: '',
        queryData: 'key',
        firstName: 'tjtcmc',//接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'tjtcmc',//接口返回值对应第二列的参数名(不传默认为'name')
        queryData:'key',
      },
      title: '添加',
      open: false,
      // 表单校验
      rules: {
        typeId: [{ required: true, message: "卡类型不能为空", trigger: "change" }],
        qskh: [{ required: true, message: "起始卡号不能为空", trigger: "change" }],
        getterId: [{ required: true, message: "领取人不能为空", trigger: "change" }],
        getTime: [{ required: true, message: "领取时间不能为空", trigger: "change" }],
        validity: [{ required: true, message: "有效期不能为空", trigger: "change" }],
        orderId: [{ required: true, message: "订单不能为空", trigger: "change" }],
        tcId: [{ required: true, message: "团检套餐不能为空", trigger: "change" }],


      },
      ///不同的
      typeOptions: [

      ],
    }
  },
  methods: {

    //卡类型改变时候调用
    typeChange() {
      const query = {
        typeId: this.form.typeId
      }
      cardChangeData(query).then(response => {
        this.form.balanceLimit = response.data.balanceLimit
        this.form.cardLogo = response.data.cardLogo
        this.form.cardPrefix = response.data.cardPrefix
        this.form.cardState = response.data.cardState
        this.form.qskh = response.data.qskh
        this.form.type = response.data.type
        this.form.zdkh = response.data.zdkh
        this.form.typeId = query.typeId
        this.form.sl = 1
      })
    },
    //领取人改变方法
    selectChangeLQR(value) {
      this.form.getterId = value.id
      this.selectDataLQR.bindValue = value.occupationSummary

    },
    //订单改变方法
    selectChangeDD(value) {
    
      this.form.orderId = value.id
      this.selectDataDD.bindValue = value.ddmc
      this.tcJY=!this.form.orderId
      this.query.id=value.id

    },
    //套餐
    selectChangeTC(value) {
   
      this.form.tcId=value.id
      this.selectDataTC.bindValue = value.tjtcmc
      
   
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm() {
     
      if(this.form.sl<1){
        this.$message.warning("请至少发一张卡！！！")
      }else{
        this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            addCard(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
             	this.$emit('getList');
             });
          } else {
            addCard(this.form).then(response => {
            this.$modal.msgSuccess("添加成功");
            this.open = false;
            this.$emit('getList');
             });
          }
        }
      });
      }
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加";
      cardTypeData({ type: 0, key: 'group' }).then(response => {
       
        this.typeOptions = response.data

      })
    },
    // 表单重置
    reset() {
      this.tcJY=true
      this.selectDataTC.bindValue=undefined
     
      this.selectDataDD.bindValue=undefined
      this.selectDataLQR.bindValue=undefined
      this.resetForm("form")
     
    }
  },
}
</script>

<style></style>