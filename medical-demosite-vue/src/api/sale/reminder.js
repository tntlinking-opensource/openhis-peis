// 备忘提醒  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询
export function listRemind(query) {
	return request({
		url: '/sell/remind/page',
		method: 'get',
		params: query
	})
}

// 查询详情
export function getRemind(id) {
	return request({
		url: '/sell/remind/' + id,
		method: 'get'
	})
}

// 新增
export function addRemind(data) {
	return request({
		url: '/sell/remind',
		method: 'post',
		data: data
	})
}

// 修改
export function updateRemind(data) {
	return request({
		url: '/sell/remind',
		method: 'put',
		data: data
	})
}

// 删除
export function delRemind(id) {
	return request({
		url: '/sell/remind/' + id,
		method: 'delete'
	})
}

// 结束
export function overRemind(ids) {
	return request({
		url: '/sell/remind/handle?ids=' + ids,
		method: 'PUT'
	})
}

// 获取客户单位名称
export function getKhdwmcData(query) {
	return request({
		url: '/sell/createorder/getKhdwmcData',
		method: 'get',
		params: query
	})
}