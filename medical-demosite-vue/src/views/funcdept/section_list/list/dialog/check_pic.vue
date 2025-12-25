<template>
  <el-dialog title="看图" :visible.sync="open" class="check-picture-dialog" :width="picPath ? '60vw' : '970px'" append-to-body destroy-on-close>
    <div v-if="picPath" style="width: 100%">
      <img :src="picPath" alt="" style="width: 100%" />
    </div>
    <template v-else>
      <div ref="form" v-loading="loading" v-if="open" class="item-content">
        <div v-if="imgList.length" style="width: 100%">
          <div class="image-box" :style="{ '--theme': theme }">
            <div class="img" :class="checkList.indexOf(item.id) > -1 ? 'check' : ''" v-for="item in imgList" :key="item.id" @click="checkImg(item.id)" @dblclick="handleDblClick(item.id)">
              <img :src="imgPath + item.filePath" />
              <i class="el-icon-check"></i>
            </div>
          </div>
          <el-image ref="previewImg" style="display: none" :src="viewList[0]" :preview-src-list="viewList"></el-image>
        </div>
        <div v-else>
          <h3>体检号{{ queryParams.patientCode }}还没有上传过图片!</h3>
        </div>
      </div>
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
    </template>
    <div slot="footer" class="dialog-footer">
      <el-button class="section-btn-style1" @click="handelDelete()">删 除</el-button>
      <el-button class="section-btn-style2" @click="cancel()">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import Cookies from 'js-cookie'
import { getViewImg, removeImg } from '@/api/funcdept/section_list/dialog.js'
export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 上传弹窗
      open: false,
      // 搜索参数
      queryParams: {
        current: 1,
        size: 30,
        patientCode: '',
        ksID: '',
      },
      // 总数
      total: 0,
      // 图片列表
      imgList: [],
      // 已选列表
      checkList: [],
      // 预览列表
      viewList: [],
      // 预览索引
      viewIndex: -1,
      //图片地址
      imgPath: Cookies.get('imgPath'),
      // 双击计时器
      dbTimer: undefined,
      // 单张图片Id
      picId: '',
      // 单张图片路径
      picPath: '',
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  methods: {
    // 打开弹窗
    showDialog(patientCode, picPath, picId) {
      if (picPath) {
        this.picPath = picPath
        this.picId = picId
        console.log(picPath)
        this.open = true
      } else {
        this.picPath = ''
        this.queryParams.patientCode = patientCode
        this.queryParams.ksID = this.$route.meta.ksID
        this.imgList = []
        this.checkList = []
        this.viewList = []
        this.getList()
        this.open = true
      }
    },
    // 获取图片列表
    getList() {
      this.loading = true
      this.viewList = []
      this.viewIndex = -1
      getViewImg(this.queryParams).then((res) => {
        this.imgList = res.data.records
        this.imgList.forEach((el) => {
          this.viewList.push(this.imgPath + el.filePath)
        })
        this.total = res.data.total
        this.loading = false
      })
    },
    // 选择图片
    checkImg(id) {
      clearTimeout(this.dbTimer)
      this.dbTimer = setTimeout(() => {
        if (this.checkList.indexOf(id) == -1) {
          this.checkList.push(id)
          this.imgList.forEach((el, index) => {
            if (el.id == id) {
              this.viewIndex = index
            }
          })
        } else {
          this.$delete(this.checkList, this.checkList.indexOf(id))
        }
      }, 200)
    },
    // 双击直接预览
    handleDblClick(id) {
      clearTimeout(this.dbTimer)
      if (this.checkList.indexOf(id) == -1) {
        this.checkList.push(id)
      }
      this.handelPreview()
    },
    // 删除
    handelDelete() {
      console.log(this.picId)
      if (!this.picId && !this.checkList.length) {
        this.$modal.alertWarning('请选择一张或多张图片！', '提醒')
        return
      }
      this.$modal
        .confirm('确定要删除图片吗？', '提醒')
        .then(() => {
          const params = {
            ids: this.picId || this.checkList.join(','),
          }

          return removeImg(params)
        })
        .then(() => {
          this.imgList = []
          this.checkList = []
          this.viewList = []
          this.picId ? this.cancel() : this.getList()
          this.$modal.msgSuccess('删除成功！')
        })
        .catch(() => {})
    },
    // 预览
    handelPreview() {
      this.$refs.previewImg.showViewer = true
    },
    // 关闭
    cancel() {
      this.open = false
    },
  },
}
</script>

<style lang="scss">
.check-picture-dialog {
  .el-dialog {
    // height: 660px;

    .item-content {
      display: flex;
      flex-wrap: wrap;
      height: 660px;
      overflow-y: auto;
    }

    .image-box {
      box-sizing: border-box;
      display: flex;
      flex-wrap: wrap;
      width: 100%;
      margin-left: -12px;

      .img {
        width: 32%;
        height: 200px;
        margin-left: 12px;
        margin-bottom: 12px;
        position: relative;
        border: 1px solid #dcdfe6;
        border-radius: 5px;
        overflow: hidden;
        cursor: pointer;

        i {
          position: absolute;
          bottom: 0;
          right: 0;
          width: 20px;
          height: 20px;
          line-height: 20px;
          color: #fff;
          font-size: 16px;
          background: #fff;
          border: 1px solid #dcdfe6;
          text-align: center;
        }

        img {
          width: 100%;
          height: 100%;
          object-fit: contain;
        }

        &.check {
          border-color: #{'var(--theme)'};

          i {
            border-color: #{'var(--theme)'};
            background: #{'var(--theme)'};
          }
        }
      }
    }
  }
}
</style>
