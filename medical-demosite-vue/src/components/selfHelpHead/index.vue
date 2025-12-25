<template>
  <div class="body-header">
    <div class="process"
      style="width:1140px;height: 80%;padding: 0 6px;display: flex;align-items: center; justify-content: space-around; ">
      <div
        style="width:40px;height: 40px;border-radius: 50%;background-color:#AB0004 ;display: flex;align-items: center;justify-content: center;font-size: 24px;color: #FFFFFF;">
        1</div>
      <div
         style="width:140px;height: 0.5px;  border:1px #AB0004 solid;display: flex;  align-items: center;justify-content: center;">
      </div>
      <div
        style="width:40px;height: 40px;border-radius: 50%;background-color:#AB0004 ;display: flex;align-items: center;justify-content: center;font-size: 24px;color: #FFFFFF;">
        2</div>
        <div
        style="width:140px;height: 0.5px;border:1px #AB0004 solid;display: flex;  align-items: center;justify-content: center;">
      </div>
      <div
        style="width:40px;height: 40px;border-radius: 50%;background-color:#AB0004 ;display: flex;align-items: center;justify-content: center;font-size: 24px;color: #FFFFFF;">
        2</div>
      <div
        style="width:140px;height: 0.5px;border:1px #AB0004 solid;display: flex;align-items: center;justify-content: center;">
      </div>
      <div
        style="width:40px;height: 40px;border-radius: 50%;background-color:#AB0004 ;display: flex;align-items: center;justify-content: center;font-size: 24px;color: #FFFFFF;">
        3</div>
      <div
        style="width:140px;height: 0.5px;border:1px #E0E0E0 solid;display: flex;align-items: center;justify-content: center;">
      </div>
      <div
        style="width:40px;height: 40px;border-radius: 50%;background-color:#E0E0E0 ;display: flex;align-items: center;justify-content: center;font-size: 24px;color: #FFFFFF;">
        4</div>
      <div
        style="width:140px;height: 0.5px;border:1px #E0E0E0 solid;display: flex;align-items: center;justify-content: center;">
      </div>
      <div
        style="width:40px;height: 40px;border-radius: 50%;background-color:#E0E0E0 ;display: flex;align-items: center;justify-content: center;font-size: 24px;color: #FFFFFF;">
        5</div>


    </div>
    <div class="text" style="width:100% ;display:flex ;justify-content: space-between;">
      <div v-if="orderNum<1" style="color:#E0E0E0;font-weight: 400;font-size: 20px;">身份验证</div>
      <div v-else style="color:#AB0004;font-weight: 400;font-size: 20px;">身份验证</div>
      <div  v-if="orderNum<2" style="margin-right:0vw;color:#E0E0E0;font-weight: 400;font-size: 20px;">选择项目</div>
      <div  v-else style="margin-right:0vw;color:#AB0004;font-weight: 400;font-size: 20px;">选择项目</div>
      <div  v-if="orderNum<3" style="margin-right:2vw;color:#E0E0E0;font-weight: 400;font-size: 20px;">科室报告</div>
      <div v-else style="margin-right:2vw;color:#E0E0E0;font-weight: 400;font-size: 20px;">科室报告</div>
      <div  v-if="orderNum<4" style="color:#AB0004;color:#E0E0E0;font-weight: 400;font-size: 20px;">完成支付</div>
      <div v-else style="color:#AB0004;color:#E0E0E0;font-weight: 400;font-size: 20px;">完成支付</div>
      <div  v-if="orderNum<5" style="color:#AB0004;color:#E0E0E0;font-weight: 400;font-size: 20px;" @click="target">登记完成</div>
      <div v-else style="color:#AB0004;color:#E0E0E0;font-weight: 400;font-size: 20px;" @click="target">登记完成</div>
    </div>
  </div>
</template>

<script>
export default {
    data(){
      return{
        orderNum:0
      }
    },
    methods(){

    },
}
</script>

<style></style>