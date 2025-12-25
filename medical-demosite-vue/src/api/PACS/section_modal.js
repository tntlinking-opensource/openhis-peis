// 影像科室  开发人：麦沃德科技予安
import request from '@/utils/request'

// 获取体检者列表
export function getListData(params) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/patient/list',
    method: 'get',
    params
  })
}

// 获取体检者总计
export function getTotalData(params) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/patient/total',
    method: 'get',
    params
  })
}

// 查询体检者状态,如：体检者已总检不能修改
export function checkApi(params) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/check',
    method: 'get',
    params
  })
}

// 体检者收费项目列表查询
export function examItemApi(params) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/item/list',
    method: 'get',
    params
  })
}

// 选择收费项目，展示小结描述等信息
export function searchApi(params) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/item/search',
    method: 'get',
    params
  })
}
// 选择收费项目，获取体征词列表
export function tzcListApi(params) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/sign/list',
    method: 'get',
    params
  })
}
// 选择收费项目，获取检查项目图片
export function imgListApi(params) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/img/list',
    method: 'get',
    params
  })
}
// 单张删除图片
export function deleteImageApi(ids) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/attachment/delete/' + ids,
    method: 'DELETE',
    params: {
      ids
    }
  })
}
// 清除图片
export function clearImageApi(id) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/attachment/deleteByFeeitemId/' + id,
    method: 'DELETE',
    params: {
      id
    }
  })
}


// 审核、保存
export function saveOrUpdate(data) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/saveOrUpdate',
    method: 'post',
    data
  })
}

// 反审核
export function reverseApi(data) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/reverse',
    method: 'PUT',
    data
  })
}

// 历史列表分页查询
export function historyApi(params) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/history/list',
    method: 'get',
    params
  })
}


// 获取选择技术方法
export function techApi(params) {
  return request({
    url: '/pacs/pacsReport/tech',
    method: 'get',
    params
  })
}
// 生成影像科室报告
export function createRad(data) {
  return request({
    url: '/pacs/pacsReport/createRad',
    method: 'post',
    data
  })
}
// 预览报告内容
export function getData(params) {
  return request({
    url: '/report/reportContent/getData',
    method: 'get',
    params
  })
}

// 彩超截图上传
export function uploadScreenshot(data) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/uploadScreenshot',
    method: 'post',
    data
  })
}

// 获取当前分中心下所有彩超科室列表
export function getKsIdList(ksId) {
  return request({
    url: '/base/ksip/list/' + ksId,
    method: 'get',
    params: {
      ksId
    }
  })
}

// 根据科室配置ID彩超抓图
export function readImages(feeitemId, ksIpId) {
  return request({
    url: `/pacs/sysytem/pacsAbteilungs/readImages/${feeitemId}/${ksIpId}`,
    method: 'get',
  })
}

// 获取Dicom图片信息
export function getDicomInfo(src) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/dcmimg/info',
    method: 'get',
    params: src
  })
}


// 图片设置进报告
export function setInReport(data) {
  return request({
    url: '/pacs/sysytem/pacsAbteilungs/setInReport',
    method: 'put',
    data
  })
}