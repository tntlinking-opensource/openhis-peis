<!-- 报告搜索-报告列表 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-search-list" :style="{ width: screenWidth + 'px' }">
    <template v-if="!bindPage">
      <div style="padding: 0 16px">
        <header class="list-header" style="position: fixed; top: 0; left: 0; width: 100%; padding: 16px; background-color: #fff">
          <div class="">
            <el-input style="width: 140px" v-model="searchYear" type="number" placeholder="请输入查询年份"></el-input>
            <el-button class="addbtn" @click="getList">查询</el-button>
          </div>
          <el-button class="addbtn" @click="handleCheckCard"><i class="el-icon-setting" style="margin-right: 8px"></i>设置</el-button>
        </header>
        <div style="padding-top: 60px"></div>
        <div class="list-item" :class="{ 'fill-up': reportList.length == 1 }" v-for="(item, index) in reportList" :key="index" @click="toReport(item)">
          <div class="item-name">{{ item.name }}</div>
          <div class="item-tag">
            <div class="tag-date">{{ item.createTime }}</div>
            <div class="tag-btn" :style="{ '--theme': theme }" v-if="item.status == 1 || !item.isOld">查看报告</div>
            <div class="tag-btn" style="color: #ccc" v-else>报告生成中</div>
          </div>
        </div>
        <div class="list-empty" v-if="!reportList || reportList.length == 0">
          <img src="@/assets/images/empty.png" />
          <span>暂无相关报告</span>
        </div>
      </div>
      <!-- 温馨提示 -->
      <div class="tips">
        <p style="font-weight: 600">温馨提示：</p>
        <p>1、报告查询条件:使用当前登录手机号+绑定身份证号与登记档案信息进行验证，如有一个与体检时登记信息不符，将无法查询。</p>
        <p>2、个检5-7个工作日，团检7-14工作日。</p>
      </div>
    </template>
    <template v-else>
      <div style="padding: 0 16px">
        <header class="list-header">
          <div style="flex: 1">
            <button class="back-btn" @click="handleBack"><i class="el-icon-arrow-left"></i> 返回</button>
          </div>
          <el-button class="addbtn" @click="handleAddCard">添加证件</el-button>
          <el-button class="back-index" @click="handleLogOut">退出登录</el-button>
        </header>
        <div class="list-empty" v-if="cardList.length == 0">
          <img src="@/assets/images/empty.png" />
          <span>暂未绑定身份证</span>
        </div>
        <div class="list-item" :class="{ 'fill-up': cardList.length == 1 }" v-for="(item, index) in cardList" :key="item.id">
          <div class="item-tag" style="justify-content: space-between; margin-top: 0">
            <div class="item-name">{{ item.idcard }}</div>
            <div class="tag-btn" :style="{ '--theme': theme }" @click="unbind(index)">解除绑定</div>
          </div>
        </div>
      </div>
      <el-dialog :visible.sync="openCard" class="preview-search-list-dialog" width="95%" append-to-body :close-on-click-modal="false">
        <el-input v-model="cardNum" placeholder="请添加绑定登记时所用证件号以用于查看报告"></el-input>
        <div class="cardTips">
          <span v-if="cardTips">{{ cardTips }}</span>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleConfirm">确 定</el-button>
          <el-button @click="handleCancel">取 消</el-button>
        </div>
      </el-dialog>
    </template>
  </div>
</template>
<script>
import { getOnlineList, getOnlinelistOld, getOnlineDetailsOld, checkIdCard, idcardPage, saOrUp } from '@/api/preview/search_report.js'
export default {
  name: 'SearchList',
  data() {
    return {
      screenWidth: '',
      // 报告列表
      reportList: [],
      // 是否绑定身份证
      bindPage: false,
      // 绑定身份证列表
      cardList: [],
      // 添加身份证弹窗
      openCard: false,
      // 添加的身份证号码
      cardNum: '',
      // 身份证验证提示
      cardTips: '',
      // 筛选年份
      searchYear: undefined,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  created() {
    document.title = '体检报告查询'
    this.screenWidth = screen.width
    var body = document.body
    body.style.width = screen.width + 'px'
    // this.searchYear = this.$getDate('').slice(0, 4)
    var item = localStorage.getItem('searchReport')
    if (item && JSON.parse(item).token) {
      checkIdCard(JSON.parse(localStorage.getItem('searchReport'))).then((res) => {
        if (!res) {
          this.bindPage = true
          this.openCard = true
          // this.handleIdcardPage()
        } else {
          this.getList()
        }
      })
    } else {
      let routeUrl = this.$router.resolve({
        name: 'SearchReport',
      })
      window.open(routeUrl.href, '_self')
    }
  },
  methods: {
    // 退出登录
    handleLogOut() {
      this.$confirm('确定要退出登录吗?', '提示', {
        customClass: 'phone-message-box',
      })
        .then(() => {
          localStorage.removeItem('searchReport')
          let routeUrl = this.$router.resolve({
            name: 'SearchReport',
          })
          window.open(routeUrl.href, '_self')
        })
        .catch()
    },
    // 获取报告列表
    getList() {
      var query = JSON.parse(localStorage.getItem('searchReport'))
      query.year = this.searchYear
      this.reportList = []
      getOnlinelistOld(query).then((res) => {
        let list = []
        res.data.forEach((el) => {
          // if (el.status == 1) {
          if (el.createTime) {
            el.createTime = this.$getDate(el.createTime)
          }
          el.isOld = true
          list.push(el)
          // }
        })
        this.reportList = list.reverse()
        getOnlineList(query)
          .then((res) => {
            if (res && res.length) {
              this.reportList = [...res, ...this.reportList]
            }
            this.reportList.forEach((el) => {
              el.name = this.getReportName(el)
            })
            this.reportList = JSON.parse(JSON.stringify(this.reportList))
          })
          .catch((err) => {
            console.error(err)
            this.reportList.forEach((el) => {
              el.name = this.getReportName(el)
            })
            this.reportList = JSON.parse(JSON.stringify(this.reportList))
          })
      })
    },
    // 获取报告名称
    getReportName(item) {
      // var date = ''
      // if (item.createTime) date = item.createTime.split(' ')[0]
      var name = `${item.patientName}_${item.patientcode}`
      return name
    },
    // 跳转报告页面
    toReport(item) {
      if (item.isOld) {
        if (item.status != 1) {
          return
        }
        var query = JSON.parse(localStorage.getItem('searchReport'))
        query.id = item.id
        getOnlineDetailsOld(query).then((res) => {
          if (res.code == 200) {
            const routeUrl = this.$router.resolve({
              name: 'ShareReportView', //这里是跳转页面的name
              query: {
                //要传的参数
                pdfUrl: res.data,
              },
            })
            // window.open(routeUrl.href, '_blank')
            window.location = routeUrl.href
          }
        })
      } else {
        const routeUrl = this.$router.resolve({
          name: 'IndividualHealthReport', //这里是跳转页面的name
          query: {
            //要传的参数
            from: 1,
            id: this.encode(`${item.branchId}&${item.id}`),
          },
        })
        window.open(routeUrl.href, '_blank')
      }
    },
    // 参数加密
    encode(str) {
      return btoa(
        encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function toSolidBytes(match, p1) {
          return String.fromCharCode('0x' + p1)
        })
      )
    },
    // 获取身份证绑定列表
    handleIdcardPage() {
      idcardPage(JSON.parse(localStorage.getItem('searchReport'))).then((res) => {
        this.cardList = res.records
      })
    },
    // 查看身份证列表
    handleCheckCard() {
      this.bindPage = true
      this.handleIdcardPage()
    },
    // 添加身份证
    handleAddCard() {
      this.openCard = true
      this.cardNum = ''
      this.cardTips = ''
    },
    // 确定添加
    handleConfirm() {
      if (!this.cardNum) {
        this.cardTips = '请输入身份证'
        return
      }
      this.cardTips = ''
      this.changeIdCard()
    },
    // 解除绑定身份证
    unbind(index) {
      this.changeIdCard(1, index)
    },
    // 添加身份证号
    changeIdCard(type, index) {
      let configData = JSON.parse(localStorage.getItem('searchReport'))
      let array = this.cardList.map((item) => item.idcard)
      if (index === 0 || index) {
        this.$delete(array, index)
      }
      let query = []
      if (type != 1) {
        query = [this.cardNum]
      }
      if (array.length) {
        query = [...query, ...array]
      }
      saOrUp(configData.token, query).then((res) => {
        if (res) {
          let message = '绑定成功'
          if (type == 1) {
            message = '解除' + message
          }
          this.$message({
            showClose: true,
            message,
            type: 'success',
          })
          this.handleCheckCard()
          this.openCard = false
        }
      })
    },
    // 取消
    handleCancel() {
      this.openCard = false
    },
    // 返回报告列表
    handleBack() {
      this.bindPage = false
      this.getList()
    },
  },
}
</script>

<style lang="scss" scope>
body {
  width: 100vw;
  background: #f6f7fb;
}

.preview-search-list {
  padding-bottom: 140px;
  max-width: 100%;
  // padding-bottom: ;
  margin: 0 auto;
  // display: flex;
  // flex-wrap: wrap;

  .list-header {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-top: 20px;

    .back-btn {
      padding: 4px 8px;
      font-size: 14px;
      color: #fbe7e8;
      border-color: #f2b8ba;
      background-color: #d41318;
      border: none;
      border-radius: 5px;
    }

    .addbtn {
      color: #fbe7e8;
      border-color: #f2b8ba;
      background-color: #d41318;
    }

    .back-index {
      position: fixed;
      bottom: 40px;
      left: 50%;
      transform: translateX(-50%);
      color: #fbe7e8;
      border-color: #f2b8ba;
      background-color: #d41318;
    }
  }

  .list-item {
    background: #fff;
    padding: 12px;
    border-radius: 5px;
    cursor: pointer;
    // width: 48%;
    margin: 12px 1% 0;

    .item-name {
      color: #333;
      font-size: 16px;
      line-height: 22px;
    }

    .item-tag {
      margin-top: 8px;
      display: flex;
      align-items: center;

      .tag-date {
        color: #666;
        font-size: 14px;
        line-height: 20px;
        flex: 1;
      }

      .tag-btn {
        color: #{'var(--theme)'};
        font-size: 14px;
        font-weight: 400;
        line-height: 20px;
        margin-left: 12px;
      }
    }

    &.fill-up {
      width: 100%;
      margin: 12px 0 0;
    }
  }

  .list-empty {
    width: 100%;
    text-align: center;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 30vh;

    img {
      width: 60%;
      height: auto;
      max-width: 320px;
    }

    span {
      font-size: 18px;
      color: #999;
    }
  }
  .tips {
    position: fixed;
    bottom: 0;
    width: 100%;
    padding: 10px 16px;
    color: #000;
    // background-color: rgb(137, 217, 97);
    background-color: rgb(246, 247, 251);
    p {
      margin: 0;
    }
  }
  @media screen and (max-width: 820px) {
    .list-item {
      // width: 100%;
      margin: 12px 0 0;
    }
  }
}
.cardTips {
  height: 20px;
  font-size: 12px;
  color: red;
}
.phone-message-box {
  width: 80vw !important;
  max-width: 310px;
}
</style>
<style scoped>
.preview-search-list-dialog /deep/ .el-dialog {
  max-width: 400px !important;
}
.preview-search-list-dialog /deep/ .el-dialog .el-dialog__body {
  padding-bottom: 0;
}
</style>
