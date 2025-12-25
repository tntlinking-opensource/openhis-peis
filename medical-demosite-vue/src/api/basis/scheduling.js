// 平安好医生排检  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 查询平安软件-排检列表
export function listPeissortexam(query) {
	return request({
		url: '/paPeissortexam/list',
		method: 'get',
		params: query
	})
}

// 查询平安软件-排检详细
export function getPeissortexam(id) {
	return request({
		url: '/paPeissortexam/' + id,
		method: 'get'
	})
}

// 新增平安软件-排检
export function addPeissortexam(data) {
	return request({
		url: '/paPeissortexam',
		method: 'post',
		data: data
	})
}

// 修改平安软件-排检
export function updatePeissortexam(data) {
	return request({
		url: '/paPeissortexam',
		method: 'put',
		data: data
	})
}

// 删除平安软件-排检
export function delPeissortexam(id) {
	return request({
		url: '/paPeissortexam/' + id,
		method: 'delete'
	})
}