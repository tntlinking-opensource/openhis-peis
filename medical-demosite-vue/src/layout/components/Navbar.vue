<!-- 顶部第一栏 -->
<template>
  <div class="navbar">
    <div class="left-title">
      <img :src="logo" style="cursor: pointer" @click="toIndex" />
      <span>{{ backgroundConfig.title }}</span>
    </div>
    <div class="right-menu">
      <div class="system-info">
        推荐使用谷歌浏览器
        <span style="padding-left: 16px;cursor: pointer;">
          <img src="@/assets/icon/topNav/guge.png" style="width: 16px;height: 16px;" />
          <a href="https://www.google.cn/intl/zh-CN_ALL/chrome/fallback/" target="_blank">前往下载</a>
        </span>
      </div>
      <div class="system-info">
        系统维护到期时间：<span>{{ expiredTime || '2024.09.16' }}</span>
      </div>
      <div class="system-info">
        当前版本号：<span>{{ versionInfo.curVersion || '' }}</span>
      </div>
      <div class="function-btn">
        <div class="function-btn-name first-btn" @click="handleCheckUpdate">
          <img src="@/assets/icon/topNav/check_update.png" alt="" />
          检查更新
        </div>
        <div class="function-btn-name second-btn" @click="handleUpdateLog">
          <img src="@/assets/icon/topNav/update_log.png" alt="" />
          更新日志
        </div>
        <div class="function-btn-name third-btn" @click="handleInstructions">
          <img src="@/assets/icon/topNav/instructions.png" alt="" />
          操作说明 
        </div>
        <div class="function-btn-name" @click="handleShowmsg">
          <el-badge :value="msgCount > 0 ? msgCount : null" :max="99">
            <img src="@/assets/icon/topNav/msgld.png" style="width: 18px;height: 18px;" />
          </el-badge>
        </div>
      </div>
      <template v-if="device !== 'mobile'">
        <search id="header-search" class="right-menu-item" />
        <screenfull id="screenfull" class="right-menu-item hover-effect" />
      </template>
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar" />
          <span class="user-name">{{ name }}</span>
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <!-- <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span> 
          </el-dropdown-item> -->
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- 检查更新对话框  -->
    <dialog-update ref="dialogUpdate" :versionInfo="versionInfo"></dialog-update>
    <!-- 最新版本对话框 -->
    <el-dialog :visible.sync="dialogLatest" width="400px" :show-close="false" center class="check-update-dialog lastest-version">
      <div slot="title" class="check-update-title" @click="dialogLatest = false">
        <div class="lastest-version">
          <img src="@/assets/icon/topNav/tick.png" alt="" />
          当前已是最新版本
        </div>
        <div class="check-update-version" style="font-size: 14px">
          最新版本:<span>正式版 {{ versionInfo.curVersion }}</span>
        </div>
      </div>
    </el-dialog>
    <!-- 消息对话框 -->
    <el-dialog :visible.sync="dialogMsg" width="888px" title="系统通知">
        <div class="dialog-main">
          <div class="dialog-main-item" v-for="(item, index) in msgList" :key="index">
              <div class="main-item-title">
                  <div style="flex: 1;font-weight: 600;">{{ item.noticeName }}</div>
                  <div>{{ item.createdate }}</div>
              </div>
              <div style="padding-top: 16px;">{{ item.content }}</div>
          </div>
          <div style="text-align: center;padding: 64px 0;font-size: 16px;" v-if="msgList.length == 0">当前暂无通知~</div>
        </div>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" layout="prev, pager, next" @pagination="getMsg" />
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="readMsg">我知道了</el-button>
        </span>
    </el-dialog>
    <!-- 更新记录对话框 -->
    <dialog-log ref="dialogLog"></dialog-log>
    <!-- 更新记录对话框 -->
    <dialog-instructions ref="dialogInstructions"></dialog-instructions>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import logoImg from '@/assets/logo/logo2.png'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYiGit from '@/components/RuoYi/Git'
import RuoYiDoc from '@/components/RuoYi/Doc'
import dialogUpdate from './navbarDialog/dialog_update.vue'
import dialogLog from './navbarDialog/dialog_log.vue'
import dialogInstructions from './navbarDialog/dialog_instructions.vue'


import { getInfo, getBackgroundConfig } from '@/api/login'
import { notification, allRead } from '@/api/system/notice'


export default {
  props: ['expiredTime', 'versionInfo'],
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc,
    dialogUpdate,
    dialogLog,
    dialogInstructions,
  },
  data() {
    return {
      logo: logoImg,
      // 已经是最新版本对话框  
      dialogLatest: false,
      // 背景配置
      backgroundConfig: {},
      // 消息数量  
      msgCount:'',
      // 消息弹窗  
      dialogMsg:false,
      // 消息列表
      msgList:[],
      // 查询条件
      queryParams: {
        current: 1,
        size: 10
      },
      total:0
    }
  },
  computed: {
    ...mapGetters(['sidebar', 'avatar', 'name', 'device']),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val,
        })
      },
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      },
    },
  },
  mounted() {
    // 在组件挂载后调用getMsgnum方法 
    this.getMsgnum();
    this.getMsg();
    this.getBackgroundConfig()

  },
  methods: {
    // 获取背景配置
    getBackgroundConfig() {
      getBackgroundConfig().then((res) => {
        this.backgroundConfig = res.data;
      }).catch((error) => {
        console.error('获取背景配置失败:', error);
      });
    },
    // 已读消息
    readMsg() {
      this.dialogMsg = false
      allRead()
        .then((response) => {
          this.getMsgnum()
        })
        .catch((err) => {
          console.error(err)
        })
    },
    // 获取通知消息 
    getMsg() {
      notification(this.queryParams)
        .then((response) => {
          this.msgList = response.data.records
          this.total = response.data.total
        })
        .catch((err) => {
          console.error(err)
        })
    },
    toIndex() {
      this.$router.push({ path: '/index' })
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$store.dispatch('LogOut').then(() => {
            location.href = '/index'
          })
        })
        .catch(() => {})
    },
    // 打开消息弹窗
    handleShowmsg() {
      this.dialogMsg = true
    },
    // 获取消息数 
    getMsgnum(){
      getInfo().then((res) => {
        this.msgCount = res.unreadMsg
      })
    },
    // 检查更新 
    handleCheckUpdate() {
      if (this.versionInfo.lastVersion == 1) {
        this.dialogLatest = true
      } else {
        this.$refs.dialogUpdate.showDialog()
      }
    },
    // 更新日志 
    handleUpdateLog() {
      this.$refs.dialogLog.showDialog()
    },
    // 打开操作说明 
    handleInstructions() {
      this.$refs.dialogInstructions.showDialog()
    },
  },
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 30px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .left-title {
    float: left;
    height: 100%;
    line-height: 28px;
    padding-left: 24px;
    display: flex;
    align-items: center;

    img {
      width: 26px;
      height: 26px;
      margin-right: 16px;
    }

    span {
      font-size: 20px;
      color: #333333;
    }
  }
  .right-menu {
    float: right;
    height: 100%;
    // line-height: 56px;
    display: flex;
    align-items: center;

    &:focus {
      outline: none;
    }

    .system-info {
      margin: 0 8px;
      font-size: 14px;
      font-weight: 600;
      color: #333;
      span {
        color: #ff2020;
      }

      img {
        vertical-align: middle; 
      }

      a {
        vertical-align: middle; 
      }
    }

    .function-btn {
      display: flex;
      align-items: center;
      margin: 2px 0;
      .function-btn-name {
        display: flex;
        align-items: center;
        padding: 2px 8px;
        margin: 0 8px;
        font-size: 12px;
        color: #fff;
        border-radius: 4px;
        cursor: pointer;
        &.first-btn {
          background-color: #ff5a50;
        }
        &.second-btn {
          background: linear-gradient(180deg, #fe6939 0%, #ff9e68 100%) !important;
        }
        &.third-btn {
          background: linear-gradient(180deg, #1890ff 0%, #68b7ff 100%) !important;
        }
        img {
          width: 12px;
          height: 12px;
          margin-right: 2px;
        }
      }
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        // &:hover {
        //   background: rgba(0, 0, 0, .025)
        // }
      }
    }

    .avatar-container {
      margin-right: 48px;

      .avatar-wrapper {
        position: relative;
        box-sizing: border-box;
        display: flex;
        align-items: center;
        height: 40px;

        .user-avatar {
          cursor: pointer;
          width: 26px;
          height: 26px;
          border-radius: 10px;
        }

        .user-name {
          margin-left: 5px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 50%;
          transform: translateY(-50%);
          font-size: 12px;
        }
      }
    }
  } 
  .dialog-main {
    max-height: 460px;
    overflow-y: auto;

    .dialog-main-item{
      margin-bottom: 16px;
      padding: 16px;
      background: #f9fbff;

      .main-item-title{
        display: flex;
        align-items: center;
       
      }
    }
   
  }
}
</style>
<style scoped>
.check-update-dialog /deep/ .el-dialog {
  border-radius: 8px;
}
.check-update-dialog /deep/ .el-dialog__header {
  padding: 16px;
  border-radius: 8px;
  background: linear-gradient(180deg, #fe6939 0%, #ff9e68 100%);
}
.check-update-dialog /deep/ .el-dialog__body {
  padding: 16px;
}
.check-update-dialog .check-update-title {
  background-image: url('../../assets/icon/topNav/update_bg.png');
  background-size: 100% 100%;
}
.check-update-dialog .check-update-title {
  font-size: 16px;
  color: #fff;
}
.check-update-dialog .check-update-title span {
  color: #000;
}

.lastest-version /deep/ .el-dialog__body {
  display: none;
}
.lastest-version /deep/ .el-dialog__header {
  padding: 0;
}
.lastest-version .check-update-title {
  padding: 32px 0;
}
.lastest-version .lastest-version {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 8px;
  font-size: 20px;
  color: #fff;
}
.lastest-version .lastest-version img {
  width: 24px;
  height: 24px;
  margin-right: 8px;
}
</style>
