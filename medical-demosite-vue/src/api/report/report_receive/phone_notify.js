// 职业报告领取通知  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 分页查询健康一审页面数据
export function getListData(query) {
  return request({
    url: '/report/phoneInform/page',
    method: 'GET',
    params: query
  })
}

// 通知接口
export function getNotice(query) {
  return request({
    url: '/report/phoneInform/notice' ,
    method: 'PUT',
    params: query
  })
}

// 获取短信分类
export function getMessageTypeData() {
  return request({
    url: '/shortmessage/getMessageTypeData',
    method: 'GET',
  })
}

// 通过体检号查询信息
export function searchCode(patientCode) {
  return request({
    url: `/report/phoneInform/searchCode`,
    method: 'GET',
    params: {
      patientCode
    }
  })
}

// 提交短信通知
export function sendMsg(query) {
  return request({
    url: '/report/phoneInform/sendMsg',
    method: 'post',
    data: query
  })
}

// 取消发送短信通知
export function cancelSMS(patientCode) {
  return request({
    url: '/report/phoneInform/cancelSMS',
    method: 'PUT',
    params: {
      patientCode
    }
  })
}

// 查看短信数据
export function getSmsData(query) {
  return request({
    url: '/report/healthPhoneInform/getSmsData',
    method: 'GET',
    params: query
  })
}
