<!-- 线上审批  开发人：麦沃德科技 予安 -->
<template>
  <div class="approval-online">
    <template v-if="!isLogin">
      <div class="preview-search-report">
        <div class="search-logo">
          <img src="@/assets/logo/logo.png" />
        </div>
        <el-form class="search-box" ref="searchForm" :model="searchForm" :rules="rules" @submit.native.prevent>
          <el-form-item class="search-input" prop="username">
            <el-autocomplete style="width: 100%" v-model="searchForm.username" value-key="text" :fetch-suggestions="querySearchAsync" placeholder="请输入用户名"> </el-autocomplete>
          </el-form-item>
          <el-form-item class="search-input" prop="password">
            <el-input type="password" v-model="searchForm.password" placeholder="请输入密码" @keyup.enter.native="handelSearch"></el-input>
          </el-form-item>
          <el-form-item class="search-button">
            <el-button type="primary" @click="handleLogin">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <template v-else>
      <div class="header">
        <!-- <div class="title">审批列表</div> -->
        <el-input class="header-input" v-model="queryParams.creator" placeholder="提交人姓名" @keyup.enter.native="handleSearch"></el-input>
        <el-select style="width: 35vw" v-model="queryParams.status" placeholder="审批状态" clearable @change="handleSearch">
          <el-option :label="item.label" :value="item.value" v-for="(item, index) in approvalStatus" :key="index"></el-option>
        </el-select>
        <!-- <el-button class="logout" @click="handleSearch">搜索</el-button> -->
        <el-button class="logout" @click="handleLogout">退出登录</el-button>
      </div>

      <div class="order-list" :infinite-scroll-distance="20" v-infinite-scroll="load" style="overflow: auto">
        <!-- <li v-for="i in count" class="infinite-list-item">{{ i }}</li> -->
        <div class="order-item" v-for="(item, index) in workList" :key="index" @click="toDetails(item)">
          <div class="order-top">
            <div style="display: flex; justify-content: space-between">
              <div class="order-name">{{ item.caseName }}</div>
              <!-- <div class="order-name">体检类型</div> -->
            </div>
            <div class="combo-name"><span style="color: #333">提交人: </span>{{ item.creator }}</div>
            <div class="combo-name"><span style="color: #333">提交时间: </span>{{ item.createdate ? item.createdate.slice(0, 16) : '' }}</div>
            <div class="combo-name"><span style="color: #333">当前审批人: </span>{{ item.userName }}</div>
            <!--<div class="money">&yen;12311 变动成本率：30.01%</div> -->
          </div>
          <div class="order-bottom order-top">
            <div class="people-name"><span style="color: #333">审批类型: </span>{{ item.typeName }}</div>
            <!-- <div class="approval-state">
          </div> -->
            <el-tag type="info" v-if="item.status == -1">已失效</el-tag>
            <el-tag :type="item.status == 2 ? 'success' : item.status == 3 ? 'danger' : 'warning'" v-else>{{ ['等待开始', '进行中', '已通过', '被驳回'][item.status] }}</el-tag>
          </div>
        </div>
        <div class="approval-empty" v-if="!hasMore && workList.length">没有更多了~</div>
      </div>
    </template>
  </div>
</template>

<script>
import { getListApi, questDetails } from '@/api/approval/approval_quest.js'
import { login, getLogUsers, getInfo, getDomain } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import store from '@/store'
import Cookies from 'js-cookie'
export default {
  name: 'Approval_online',
  data() {
    return {
      isLogin: false,
      // 表单
      searchForm: {
        username: null,
        password: null,
      },
      // 校验
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
        password: [{ required: true, message: '请输入密码', trigger: 'change' }],
      },
      queryParams: {
        current: 1,
        size: 20,
        creator: '',
        status: '',
      },
      // 审批状态
      approvalStatus: [
        { value: '', label: '' },
        { value: '-1', label: '已失效' },
        { value: '0', label: '等待开始' },
        { value: '1', label: '进行中' },
        { value: '2', label: '已通过' },
        { value: '3', label: '被驳回' },
      ],
      // 审批列表
      workList: [],
      // 是否还有更多
      hasMore: false,
      timer: false,
      timer2: false,
    }
  },
  created() {
    // this.getList()
    let approvalUsername = localStorage.getItem('approvalUsername')
    let approvalPassword = localStorage.getItem('approvalPassword')
    if (getToken()) {
      this.isLogin = true
      this.getList()
      setTimeout(() => {
        this.timer2 = true
      }, 2000)
    } else if (approvalUsername && approvalPassword) {
      this.searchForm.username = approvalUsername
      this.searchForm.password = approvalPassword
      this.handleLogin()
    }
  },
  methods: {
    // 根据输入内容查询用户
    querySearchAsync(queryString = '', cb) {
      if (queryString || queryString == ' ') {
        queryString = queryString.trim()
      }
      getLogUsers({ key: queryString }).then(({ data }) => {
        cb(data)
      })
    },
    // 登录
    handleLogin() {
      this.$refs.searchForm.validate((valid) => {
        if (valid) {
          login(this.searchForm.username, this.searchForm.password).then((res) => {
            setToken(res.token)
            localStorage.setItem('approvalUsername', this.searchForm.username)
            localStorage.setItem('approvalPassword', this.searchForm.password)
            getInfo().then((res) => {
              Cookies.set('userNo', res.user.userNo, { expires: 30 })
              this.isLogin = true
              this.getList()
              setTimeout(() => {
                this.timer2 = true
              }, 2000)
            })
          })
        }
      })
    },
    // 退出登录
    handleLogout() {
      this.$confirm('是否退出当前账号?', '提示')
        .then(() => {
          store.dispatch('LogOut').then(() => {
            this.isLogin = false
          })
        })
        .catch(() => {})
    },
    // 搜索
    handleSearch() {
      this.queryParams.current = 1
      this.getList()
    },
    // 查询审批列表
    getList() {
      getListApi(this.queryParams)
        .then(({ data }) => {
          this.workList = this.queryParams.current == 1 ? data.records : [...this.workList, ...data.records]
          this.hasMore = this.queryParams.current * this.queryParams.size < data.total
          this.total = data.total
          this.timer = false
        })
        .catch((err) => {
          console.error(err)
          this.timer = false
        })
    },
    // 跳转审批详情
    toDetails(item) {
      const routeUrl = this.$router.resolve({
        name: 'ApprovalOnlineItem', //这里是跳转页面的name
        query: {
          //要传的参数
          id: item.id,
          bizId: item.bizId,
          typeFlag: item.typeFlag,
        },
      })
      window.location = routeUrl.href
    },
    // 触底加载
    load() {
      if (this.hasMore && !this.timer && this.timer2) {
        this.timer = true
        this.queryParams.current++
        this.getList()
      }
    },
  },
}
</script>
<style lang="scss">
.approval-online {
  height: 100%;
  padding: 0;

  .preview-search-report {
    padding: 0 20px;

    .search-logo {
      width: 100%;
      height: 34vh;
      min-height: 160px;
      display: flex;
      justify-content: center;
      align-items: flex-end;

      img {
        width: 140px;
        height: auto;
        margin: 0 auto;
      }
    }

    .search-box {
      margin: 12vh auto 0;
      max-width: 450px;

      .search-input {
        margin: 12px 0 0;
        background: #f6f6f6;
        border-radius: 5px;

        .el-form-item__content {
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
        }

        .el-input {
          flex: 1;

          .el-input__inner {
            background: transparent;
            color: #999;
            font-size: 14px;
            height: 40px;
            line-height: 40px;
            // border: none;
          }
        }

        .el-button {
          width: 96px;
          height: 40px;
          line-height: 40px;
          padding: 0;
          border-radius: 5px;

          span {
            font-size: 14px;
          }
        }
      }
      .search-tips {
        margin-top: 8px;
        font-size: 12px;
        color: red;
        text-align: right;
      }

      .search-button {
        margin: 24px 0 0;

        .el-button {
          width: 100%;
          height: 50px;
          line-height: 50px;
          padding: 0;
          border-radius: 5px;
        }
      }
    }
  }

  .header {
    position: fixed;
    top: 0;
    z-index: 2;
    display: flex;
    align-items: center;
    // justify-content: space-between;
    flex-wrap: wrap;
    width: 100%;
    padding: 12px 8px;
    background-color: #fff;
    .logout {
      color: #fbe7e8;
      border-color: #f2b8ba;
      background-color: #d41318;
    }
    .title {
      font-size: 18px;
      font-weight: 600;
    }
    .header-input {
      width: 35vw;
      margin-right: 8px;
    }
  }
  .order-list {
    height: 100%;
    padding: 60px 0 30px;
    background-color: #f6f7fb;
    .approval-empty {
      color: #666;
      text-align: center;
    }
    .order-item {
      margin: 6px 12px;
      border-radius: 3px;
      .order-top {
        padding: 8px;
        background-color: #fff;
        .order-name {
          font-size: 17px;
          font-weight: 600;
        }
        .combo-name {
          margin: 8px 0;
          font-size: 16px;
        }
      }
      .order-bottom {
        display: flex;
        justify-content: space-between;
        margin-top: 2px;
      }
    }
  }
}
</style>
