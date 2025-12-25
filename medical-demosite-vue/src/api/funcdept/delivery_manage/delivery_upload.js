// 外送登记 开发人：麦沃德矢北 
import request from '@/utils/request'

export function getListData(query) {
  return request({
    url: '/outside/sendGovern/page',
    method: 'get',
    params: query
  })
}
//删除接口
export function deleteListData(ids) {
  return request({
    url: '/outside/sendGovern/' + ids,
    method: 'delete',
    params:{
      ids
    }
  })
}

// 保存
export function saveDataList(data) {
  return request({
    url: '/outside/sendGovern/saveOrUpdate',
    method: 'post',
    data: data
  })
}
//查看详情
export function getDetails(id) {
  return request({
    url: '/outside/sendGovern/' + id,
    method: 'get',
  })
}

//判断能否编辑
export function editJudgment(query) {
  return request({
    url: '/outside/sendGovern/canEdit',
    method: 'get',
    params: query
  })
}
//查看弹窗详情
export function getMainListData(query) {
  return request({
    url: '/outside/sendGovern/getMainListData',
    method: 'get',
    params: query
  })
}

// 中部左半部分表格数据
export function getPictureData(query) {
  return request({
    url: '/outside/sendGovern/getPictureData',
    method: 'get',
    params: query
  })
}
// 已保存数据表格(下半部分)
export function getEditData(query) {
  return request({
    url: '/outside/sendGovern/getEditData',
    method: 'get',
    params: query
  })
}
//上传图片
export function upLoadImg(imgFile) {
  return request({
    url: '/outside/sendGovern/upload',
    method: 'post',
    data: imgFile
  })
}
//保存接口
export function saveOrUpdate(data) {
  return request({
    url: '/outside/sendGovern/saveOrUpdate',
    method: 'post',
    data: data,
  })
}
// 输入框变化获取科室
export function getDepart(query) {
  return request({
    url: '/outside/sendGovern/getDjOutData',
    method: 'get',
    params: query
  })
}
// 输入框变化获取检测项目
export function getItemName(query) {
  return request({
    url: '/outside/sendGovern/getAllOutData',
    method: 'get',
    params: query
  })
}

// 获取上传的图片
export function getUploadPic(params) {
  return request({
    url: '/outside/sendGovern/look',
    method: 'get',
    params
  })
}

// 新增外送登记结果处理-查询
export function recordMatch(params) {
  return request({
    url: '/outside/sendGovern/recordMatch',
    method: 'get',
    params
  })
}
// 登记外送项目数据获取
export function getPictureWsxmData(params) {
  return request({
    url: '/outside/sendGovern/getPictureWsxmData',
    method: 'get',
    params
  })
}