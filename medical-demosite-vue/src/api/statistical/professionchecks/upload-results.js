// 云平台上传结果 开发人:麦沃德科技 予安

import request from '@/utils/request'

// 分页查询
export function getPageInfo(params) {
  return request({
    url: "/statistics/professionUploadResult/page",
    method: 'get',
    params
  })
}

// 重新上传
export function updateStatus(params) {
  return request({
    url: "/statistics/professionUploadResult/updateStatus",
    method: 'put',
    params
  })
}

// 矫正工龄
export function calculateZglAndJhgl(data) {
  return request({
    url: "/reception/order/calculateZglAndJhgl",
    method: 'post',
    data
  })
}