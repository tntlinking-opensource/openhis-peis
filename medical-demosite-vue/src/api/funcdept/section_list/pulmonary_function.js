// 肺功能  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 肺功能读卡查询用户信息
export function getLungInfo(params) {
  return request({
    url: '/abteilung/divisionLung/search',
    method: 'get',
    params
  })
}

// 肺功能根据fvc及fev获取体征词
export function getLungSign(params) {
  return request({
    url: '/abteilung/divisionLung/getSign',
    method: 'get',
    params
  })
}

// 肺功能获取结伦词列表数据
export function jlcDataPF(params) {
  return request({
    url: '/abteilung/divisionLung/jlcData',
    method: 'get',
    params
  })
}

// 审核
export function saveOrUpdatePF(data) {
  return request({
    url: '/abteilung/divisionLung/saveOrUpdate',
    method: 'POST',
    data
  })
}

// 反审核
export function reversePF(params) {
  return request({
    url: '/abteilung/divisionLung/reverse',
    method: 'put',
    params
  })
}

// 读取串口数据发送给后端进行解析
export function uploadLungIm(data) {
  return request({
    url: '/abteilung/divisionLung/uploadLungIm',
    method: 'post',
    data
  })
}

// 读取串口数据发送给后端进行解析淮南
export function uploadLungImHN(data) {
  return request({
    url: '/abteilung/divisionLung/uploadHnLung',
    method: 'post',
    data
  })
}
