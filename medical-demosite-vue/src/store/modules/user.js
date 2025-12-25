import { login, logout, getInfo, getDomain, getDefaultBranch } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import Cookies from "js-cookie";

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      return new Promise((resolve, reject) => {
        login(username, password, code, uuid).then(res => {
          setToken(res.token)
          commit('SET_TOKEN', res.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          if (Cookies.get('isOnline') == '1') {
            Cookies.set("cid", res.user.cid, { expires: 30 });
          } else {
            getDefaultBranch().then(res2 => {
              Cookies.set("cid", res2.data.branchId, { expires: 30 });
            })
          }
          Cookies.set("username", res.user.userName, { expires: 30 });
          Cookies.set("userNo", res.user.userNo, { expires: 30 });
          const user = res.user
          // const avatar = (user.avatar == "" || user.avatar == null) ? require("@/assets/images/default-head.png") : process.env.VUE_APP_BASE_API + user.avatar;
          const avatar = (user.avatar == "" || user.avatar == null) ? require("@/assets/images/default-head.png") : Cookies.get('imgPath') + user.avatar;
          if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', res.roles)
            commit('SET_PERMISSIONS', res.permissions)
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          commit('SET_NAME', user.userName)
          commit('SET_AVATAR', avatar)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
        // getDomain().then(res => {
        //   // console.log("process.env.VUE_APP_BASE_API",process.env.VUE_APP_BASE_API,(res.data.rsLcDomain||process.env.VUE_APP_BASE_API))
        //   Cookies.set("imgPath", res.data.isOnline == 1 ? (res.data.rsPfDomain) : (res.data.rsLcDomain || process.env.VUE_APP_BASE_API), { expires: 30 });
        //   if (process.env.VUE_APP_BASE_API == 'http://XXX.XXX.XXX:8080') {
        //     Cookies.set("imgPath", 'http://XXX.XXX.XXX:8080')
        //   }
        //   Cookies.set("isOnline", res.data.isOnline, { expires: 30 })
        // }).catch(error => { })
      })
    },

    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
