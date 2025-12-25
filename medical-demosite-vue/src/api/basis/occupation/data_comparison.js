// 省市级平台数据对照 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 获取数据字典分类
export function getClassApi() {
  return request({
    url: '/baseDictionary/getDataDictionary',
    method: 'get',
  })
}

// 查询省市级数据对照列表
export function getListApi(query) {
  return request({
    url: '/baseDictionary/getOurDictionary',
    method: 'get',
    params: query
  })
}

// 查询省市级数据对照详细
export function getDetailsApi(id, classCode) {
  return request({
    url: '/baseDictionary/' + id + '/' + classCode,
    method: 'get',
    params: {
      id,
      classCode
    }
  })
}

// 新增省市级数据对照
export function addApi(data) {
  return request({
    url: '/baseDictionary/setDataMatch',
    method: 'post',
    data,
  })
}

// 修改省市级数据对照
export function updateApi(data) {
  return request({
    url: '/medical/harm',
    method: 'put',
    data: data
  })
}

// 删除省市级数据对照
export function delApi(id) {
  return request({
    url: '/medical/harm/' + id,
    method: 'delete'
  })
}
