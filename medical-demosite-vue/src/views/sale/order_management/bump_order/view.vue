<template>
  <el-dialog title="订单比对" width="auto" :visible.sync="open">
    <div class="order">
      <div class="mainList">

        <div  style="border:1px solid #D4D6D9 ;margin-top: 6px;width: 100%;">
          <div style="width:100% ;padding-left: 15px; height:48px;
             color: #FF2020;background-color:#FFF1F1 ;font-size: 16px;font-weight: 600; 
             line-height: 48px;">
            主要订单信息
          </div>
          <div style="margin:16px ;font-size: 16px;font-weight: bold;color: #333333;">客户订单信息</div>
          <el-form  size="small" inline :model="this.lookData[0]" label-width="140px">
            <el-form-item label="	订单id" prop="id">
              <el-input :readonly="read" v-model="form.id" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="	订单名称" prop="ddmc">
              <el-input :readonly="read" v-model="form.ddmc" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="	销售经理" prop="xsjl">
              <el-input :readonly="read" v-model="form.xsjl" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="	分中心" prop="fzx">
              <el-input :readonly="read" v-model="form.fzx" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <div style="margin:16px ;font-size: 16px;font-weight: bold;color: #333333;">客户单位信息</div>
            <!----------------------------------- 客户单位信息  ----------------------------------->
            <el-form-item label="客户单位团体号" prop="intId">
              <el-input :readonly="read" v-model="form.intId" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="客户单位名称" prop="khdwmc	">
              <el-input :readonly="read" v-model="form.khdwmc" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="客户单位联系人" prop="khdwlxr">
              <el-input :readonly="read" v-model="form.khdwlxr" placeholder="" clearable style="width: 260px" />
            </el-form-item>

            <el-form-item label="客户电话" prop="khdh">
              <el-input :readonly="read" v-model="form.khdh" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="	法人单位名称" prop="frdwmc">
              <el-input :readonly="read" v-model="form.frdwmc" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="企业名称" prop="licenseName">
              <el-input :readonly="read" v-model="form.licenseName" placeholder="请输入营业执照上的企业名称" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="社会信用代码" prop="socialCreditCode">
              <el-input :readonly="read" v-model="form.socialCreditCode" placeholder="" clearable style="width: 260px" />
            </el-form-item>
            <el-form-item label="	客户单位注册地址" prop="khdwzcdz">
              <el-input :readonly="read" v-model="form.khdwzcdz" placeholder="" clearable style="width: 260px" />
            </el-form-item>

            <el-form-item label="备注" prop="bz">
              <el-input :readonly="read" v-model="form.bz" resize="none" type="textarea" placeholder="" clearable
                style="width: 260px" />
            </el-form-item>
          
          </el-form>
        </div>

      </div>
      <div class="leftList" style=" margin-top: 6px; width: 75%;min-width: 400px;  border: 1px solid #D4D6D9;">
        <div   style="width:100% ;padding-left: 15px; height:48px;
               color: #0059FF;background-color:#EBF2FF ;font-size: 16px;font-weight: 600; 
             line-height: 48px;">
          撞单订单信息</div>
        <div class="orderList" style="display: flex; overflow: auto;" >
          <div v-for="item in lookData.slice(1, lookData.length)" :key="item.id" >
            <div style="border-right:1px solid #D4D6D9 ; width: 440px; ">
              <div style="margin:16px ;font-size: 16px;font-weight: bold;color: #333333;">客户订单信息</div>
              <el-form  size="small" inline :model="form" label-width="140px">
                <el-form-item label="	订单id" prop="id">
                  <el-input :readonly="read" v-model="item.id" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="	订单名称" prop="ddmc">
                  <el-input :readonly="read" v-model="item.ddmc" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="	销售经理" prop="xsjl">
                  <el-input :readonly="read" v-model="item.xsjl" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="	分中心" prop="fzx">
                  <el-input :readonly="read" v-model="item.fzx" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <div style="margin:16px ;font-size: 16px;font-weight: bold;color: #333333;">客户单位信息</div>
                <!----------------------------------- 客户单位信息  ----------------------------------->
                <el-form-item label="客户单位团体号" prop="intId">
                  <el-input :readonly="read" v-model="item.intId" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="客户单位名称" prop="khdwmc	">
                  <el-input :readonly="read" v-model="item.khdwmc" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="客户单位联系人" prop="khdwlxr">
                  <el-input :readonly="read" v-model="item.khdwlxr" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="客户电话" prop="khdh">
                  <el-input :readonly="read" v-model="item.khdh" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="	法人单位名称" prop="frdwmc">
                  <el-input :readonly="read" v-model="item.frdwmc" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="企业名称" prop="rate">
                  <el-input :readonly="read" v-model="item.rate" placeholder="请输入营业执照上的企业名称" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="社会信用代码" prop="rejectPlanId">
                  <el-input :readonly="read" v-model="item.rejectPlanId" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="	客户单位注册地址" prop="khdwzcdz">
                  <el-input :readonly="read" v-model="item.khdwzcdz" placeholder="" clearable style="width: 260px" />
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                  <el-input :readonly="read" v-model="item.remark" resize="none" type="textarea" placeholder="" clearable
                    style="width: 260px" />
                </el-form-item>

              </el-form>
            </div>



          </div>



        </div>


      </div>

    </div>

  </el-dialog>
</template>

<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 表单
      form: {},
      read:true,
      // 表单校验
      rules: {

      },
      lookData: [{}, {},{}, {},{}, {},{}, {},{}, {},]
    };
  },
  computed: { },
  watch: { },
  created() { },
  mounted() { },
  methods: {
    // 显示
    handleLook(data) {
     
      this.reset();
      this.open = true;
      this.form=data[0]
      this.lookData=data
      
    },
    // 表单重置
    reset() {
      this.form = {
        pinganId: undefined,
      }
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 提交按钮
    submitForm() {
      addItem(this.form).then(() => {
        this.$modal.msgSuccess("操作成功！");
        this.open = false;
        this.$emit("getList")
      });
    },
  },
};
</script>

<style lang="scss" scope>
.order {
  max-width: 1750px;
  width: 100%;
  display: flex;
  flex-wrap: nowrap;
  height: 100%;
  padding: 0;
  overflow: auto;

  .mainList {
    margin-right: 10px;
    width: 420px;
    height: auto;
    flex-shrink: 0,
  }

  .leftList {
    overflow: auto;

    .orderList {
      display: flex;
      width: 100%;
      height: auto;
      flex-shrink: 0,
    }
  }

}</style>