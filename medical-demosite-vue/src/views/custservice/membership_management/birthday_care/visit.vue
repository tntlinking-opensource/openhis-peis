<!-- 生日关怀 麦沃德科技 开发人:清风/暴雨 -->
<template>
    <div class="add-container">
        <el-dialog id="dialogPadding" title="信息回访" :visible.sync="open" width="418px" append-to-body :close-on-click-modal="false">
            <el-form label-width="100px" :inline="true" hide-required-asterisk @submit.native.prevent>
                <el-form-item label="短消息名称" prop="templateId">
                    <input-select ref="inputSelect" :selectData="selectData" @change="selectChange" style="width: 260px;"></input-select>
                </el-form-item>
                <el-form-item label="短消息类型">
                    <el-select style="width:260px"  v-model="form.messageType">
                        <el-option  v-for="item in options" :key="item.id"
                        :label="item.typeName" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="短消息正文">
                    <el-input type="textarea" style="width:260px" v-model="form.visitNote" :rows="3"></el-input>
                </el-form-item>
                <el-form-item label="立即发送">
                    <el-checkbox v-model="form.immediately" @change="cleartime"></el-checkbox>
                </el-form-item>
                <el-form-item label="发送时间" v-if="form.immediately==false">
                    <el-date-picker style="width:260px" v-model="form.sendTime" type="date" placeholder="选择日期"></el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="onOk">保存</el-button>
                <el-button type="primary" plain @click="reset">重置</el-button>
                <el-button @click="onCancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { getoption,saOrUp } from '@/api/custservice/membership_management/birthday_care/birthday_care'


    export default{
        data(){
            return{
                idss:undefined,
                open:false,
                form:{
                    messageName:undefined,
                    messageType:undefined,
                    visitNote:undefined,
                    immediately:true,
                    sendTime:undefined,
                    ids:undefined
                },
                options:[],
                // 输入选择
                selectData: {
                  placeholder: '请输入输入码',
                  key: '输入码',//第一列标题
                  value: '模板名称',//第二列标题
                  url: '/shortmessage/getComboData',//请求连接
                  bindValue: '', //初始值(必传)
                  firstName: 'inputcode',//接口返回值对应第一列的参数名(不传默认为'inputCode')
                  secondName: 'messageName',//接口返回值对应第二列的参数名(不传默认为'name')
                  queryData: 'key',//向接口传递的参数名(不传默认为'inputCode')
                },
            }
        },
        methods:{
          visit(id){
            this.form.ids=id
            this.open = true;
            this.getoptions()
          },
          onOk(){
            this.form.messageName=this.idss
            this.form.messageType = parseInt(this.form.messageType)
            saOrUp(this.form).then(response => {
              this.$modal.msgSuccess("保存成功");
              this.open = false;
              this.reset()
              this.$emit("getList")
            });
          },
          //重置
          reset(){
              this.form.messageName=undefined,
              this.form.messageType=undefined,
              this.form.visitNote=undefined,
              this.form.sendTime=undefined,
              this.form.immediately=true,
              this.form.ids=undefined
              this.$refs.inputSelect.initData('')
          },
          //获取选项
          getoptions(){
            getoption().then(response => {
              this.options = response.data.records;
            });
          },
          //组件查询赋值
          selectChange(value){
            this.form.messageName = value.messageName
            this.form.visitNote = value.messageText
            this.idss=value.id
          },
          //取消
          onCancel(){
            this.reset()
            this.open = false;
          },
          // 清除时间
          cleartime(){
            this.form.sendTime=undefined
          }
        }
    }
</script>

<style scoped>
    #dialogPadding /deep/ .el-dialog__body{
        padding: 0;
    }
</style>
