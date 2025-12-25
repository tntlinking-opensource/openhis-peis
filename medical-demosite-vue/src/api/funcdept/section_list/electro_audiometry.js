// 电测听  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 获取列队信息
export function getRankData(query) {
  return request({
    url: '/abteilung/division/getRankData',
    method: 'get',
    params: query
  })
}

// 获取电测听（听阈偏差值+备注）
export function getDeviation() {
  return request({
    url: '/total/disease/audio',
    method: 'get',
  })
}

// 电测听 上传图片-保存
export function UploadImg(data) {
  return request({
    url: '/abteilung/electroAudiometer/UploadImg',
    method: 'post',
    data
  })
}