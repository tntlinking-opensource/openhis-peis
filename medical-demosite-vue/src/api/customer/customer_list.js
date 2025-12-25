// 客户管理  开发人：麦沃德科技 予安/矢北
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 查询我的客户列表
export function listSellcustomer(query) {
  return request({
    url: '/sell/customer/page',
    method: 'get',
    params: query
  })
}
// 查询我的客户详细
export function getSellcustomer(id) {
  return request({
    url: '/sell/customer/info/' + id,
    method: 'get'
  })
}

// 新增我的客户
export function addSellcustomer(data, query) {
  return request({
    url: '/sell/customer' + (query ? ('?filePath=' + query) : ''),
    method: 'post',
    data
  })
}

// 修改我的客户
export function updateSellcustomer(data, query) {
  return request({
    url: '/sell/customer' + (query ? ('?filePath=' + query) : ''),
    method: 'put',
    data
  })
}

// 删除我的客户
export function delSellcustomer(id) {
  return request({
    url: '/sell/customer/' + id,
    method: 'delete'
  })
}

//客户释放
export function setBan(query) {
  return request({
    url: '/sell/customer/setBan',
    method: 'post',
    params: query
  })
}
//判断是否签过合同
export function isJDHT(query) {
  return request({
    url: '/sell/customer/isZskh',
    method: 'get',
    params: query
  })
}
//客户升级
export function upgrade(query) {
  return request2({
    url: '/sell/customer/upgrade',
    method: 'put',
    params: query
  })
}
//   客户转移列表获取
export function getMoveList(query) {
  return request({
    url: '/sell/customerTransfer/getXsryData',
    method: 'get',
    params: query
  })
}
//客户转移保存
export function saveTransferData(data) {
  return request({
    url: '/sell/customerTransfer/saveTransfer',
    method: 'post',
    data: data
  })
}
// 客户转移保存
export function saveTransfer() {
  return request({
    url: '/sell/customerTransfer',
    method: 'post',
    params: query
  })
}
// 客户转移更新
export function upDataTransfer() {
  return request({
    url: '/sell/customerTransfer',
    method: 'put',
    params: query
  })
}
// 客户转移删除
export function deleteTransfer(ids) {
  return request({
    url: '/sell/customerTransfer/' + ids,
    method: 'delete',
    params: query
  })
}

// 客户转移详情
export function detailTransfer(ids) {
  return request({
    url: '/sell/customerTransfer/' + ids,
    method: 'delete',
    params: query
  })
}


//生成网站账号
export function generateAccount(query) {
  return request({
    url: '/sell/customer/generateAccount',
    method: 'post',
    data: query
  })
}

//重置密码
export function resetPassword(query) {
  return request({
    url: '/sell/customer/resetPassword',
    method: 'Put',
    data: query
  })
}

//客户跟进保存
export function saveKhgt(query) {
  return request({
    url: '/sell/teamremind/saveKhgtData',
    method: 'post',
    data: query
  })
}
//重复名称检测
export function isRepeatName(Name) {
  return request({
    url: '/sell/customer/onBlur',
    method: 'get',
    params: Name
  })
}

// 获取职业病危害信息
export function getfzxList() {
  return request({
    url: '/sell/customer/getWhyslbData',
    method: 'get',
    params: {
      current: 1,
      size: 99999,
    }
  })
}
// 获取门类数据
export function getIndusData(query) {
  return request({
    url: '/sell/customer/getIndusData',
    method: 'get',
    params: query
  })
}
// 获取地区类数据
export function getZoneData(query) {
  return request({
    url: '/sell/customer/getZoneData',
    method: 'get',
    params: query
  })
}
// 获取经济类数据
export function getEconomyCode() {
  return request({
    url: '/sell/customer/getEconomyCode',
    method: 'get',

  })
}
// 获取全区域名称数据
export function getUnitArea() {
  return request({
    url: '/sell/customer/getUnitArea',
    method: 'get',
  })
}
//获取接害因素
export function getJhysList() {
  return request({
    url: '/sell/createmeal/getJhysDataByFzx',
    method: 'get'
  })
}
//获取详情
export function getDetailData(query) {
  return request({
    url: '/sell/customer/customerFollow',
    method: 'get',
    params: query
  })
}
//客户跟进保存
export function saveData(data) {
  return request({
    url: '/sell/customer/saveCustomerFollowData',
    method: 'post',
    data: data
  })
}

//金蝶客户数据更新
export function upgradeCustomer() {
  return request({
    url: '/finance/kingdee/upgradeCustomer',
    method: 'post'
  })
}

//检验匹配团体金蝶名
export function checkOrgName() {
  return request({
    url: "/finance/kingdee/checkOrgName",
    method: 'post'
  })
}

//分中心选择
export function getCenterOrgNameList() {
  return request({
    url: "/sell/customer/getCenterOrgNameList",
    method: 'get'
  })
}
