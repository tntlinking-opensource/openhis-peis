// 自助登机  开发人：麦沃德科技 矢北
import request from '@/utils/request'

//验证个人信息
 export function registerApi(data){
  return request({
    url:'/machine/registrationMachine/register',
    method: 'post',
    data:data
  })
}
// 获取套餐提交信息
 export function registerSelect(params){
   return request({
     url:'/machine/registrationMachine/registerSelect',
     method: 'get',
     params
   })
 }

 //个人信息确认
 export function registerInfo(query){
  return request({
    url:'/machine/registrationMachine/registerInfo',
    method: 'get',
    params:query
  })
}

//确认个人信息 
export function confirmRegister(data){
  return request({
    url:'/machine/registrationMachine/confirmRegister',
    method: 'post',
    data
  })
}

//获取体检项目
export function getItemData(params){
  return request({
    url:'/machine/registrationMachine/getItemData',
    method: 'get',
    params
  })
}

//分组确认项目
export function groupConfirmItems(params){
  return request({
    url:'/machine/registrationMachine/groupConfirmItems',
    method: 'get',
    params
  })
}

// 检查金额
export function checkAmount(params){
  return request({
    url:'/machine/registrationMachine/checkAmount',
    method: 'get',
    params
  })
}

// 自助登记-打印
export function printApi(data){
  return request({
    url:'/machine/registrationMachine/print',
    method: 'post',
    data
  })
}

// 自助登记-打印导引单数据
export function modelApi(data){
  return request({
    url:'/machine/registrationMachine/model',
    method: 'post',
    data
  })
}

// 查询对应的卡号及体检者信息
export function onMessage(params){
  return request({
    url:'/machine/readCard/onMessage',
    method: 'get',
    params
  })
}

// 选择体检项目
export function addItemsApi(params){
  return request({
    url:'/machine/additionalPay/items',
    method: 'get',
    params
  })
}

// 加密身份证号
export function encryptionIdCardNo(params){
  return request({
    url:'/machine/reportPrint/encryptionIdCardNo',
    method: 'get',
    params
  })
}

// 发送手机验证码
export function sendSmsCode(data){
  return request({
    url:'/machine/reportPrint/sendVerificationCode',
    method: 'post',
    data
  })
}

// 验证码登录
export function verificationCodeLogin(data){
  return request({
    url:'/machine/reportPrint/verificationCodeLogin',
    method: 'post',
    data
  })
}

// 报告打印列表
export function reportPrintList(params){
  return request({
    url:'/machine/reportPrint/reportPrintList',
    method: 'get',
    params
  })
}

// 付款
export function reportPrintPayment(data){
  return request({
    url:'/machine/reportPrint/reportPrintPayment',
    method: 'post',
    data
  })
}

// 查询通联是否支付成功
export function queryTongLian(params){
  return request({
    url:'/machine/reportPrint/queryTongLian',
    method: 'get',
    params
  })
}

// 支付后回调方法
export function callback(data){
  return request({
    url:'/machine/reportPrint/callback',
    method: 'post',
    data
  })
}



