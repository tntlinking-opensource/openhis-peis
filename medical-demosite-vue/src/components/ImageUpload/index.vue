<template>
  <div class="stencil-upload-images" :style="{ width: uploadWidth }">
    <!-- 图片上传 -->
    <el-upload
      v-if="typeChoose == '1'"
      ref="upload"
      class="upload-files"
      list-type="picture"
      :file-list="files"
      drag
      action=""
      :multiple="uploadData.multiple || false"
      :limit="uploadData.limit"
      :on-change="uploadChange"
      :on-exceed="uploadExceed"
      :http-request="handelUpload"
      :on-remove="uploadRemove"
      :auto-upload="false"
      :style="'width' + uploadWidth"
    >
      <el-button size="small" type="primary">点击上传</el-button>
      <div v-if="uploadData.picture" style="border-radius: 6px; padding: 10px; width: 100px; height: 92px; margin-top: 5px; border: #c0ccda 1px solid">
        <img style="max-width: 100%; height: 100%" :src="uploadData.picture" />
      </div>
    </el-upload>

    <el-upload v-else ref="upload" class="upload-files" drag action="" :multiple="uploadData.multiple || false" :limit="uploadData.limit" :on-change="uploadChange" :on-exceed="uploadExceed" :http-request="handelUpload" :on-remove="uploadRemove" :auto-upload="false" :style="'width' + uploadWidth">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>
      <div class="upload-tip" v-if="uploadData.limit">最多可上传{{ uploadData.limit }}个图片</div>
    </el-upload>
  </div>
</template>

<script>
// uploadData: {
//   url: '',//图片上传地址
//   multiple: false,//是否可以上传多个
//   limit: 1,//图片上传个数限制
//   fileType: "",//图片类型
//   data: {},//上传时附带的额外参数
//   typeChoose: 0,// 选择上传图片的样式 0-拖拽(默认) 1-按钮
//   fileList: [],// 反显图片
// }
// uploadWidth: 300px,//组件宽度
// uploadModel: '',//特殊模板

//导入
// import UploadFile from '@/components/UploadFile'
// components: {
//    UploadFile
// },

// 使用
// <upload-file :uploadData="uploadData" @uploadSuccess="uploadSuccess" :uploadWidth="300px"></upload-file >
// 上传图片成功
// uploadSuccess(val) {
//   this.参数 = val
// },
import request from '@/utils/request'
export default {
  name: 'ImageUpload',
  props: ['uploadData', 'uploadWidth', 'typeChoose'],
  data() {
    return {
      // 已选择图片
      files: this.uploadData.fileList || [],
      // 显示文字
      showText: '',
      // 上传多张使用防抖
      uploadTimer: null,
    }
  },
  methods: {
    // 图片改变
    uploadChange(file, files) {
      if (!this.checkType(file)) {
        this.$refs.upload.clearFiles()
        this.files = []
        this.showText = ''
        if (this.uploadData.fileType && this.uploadData.fileType.length) {
          return this.$message.error(`图片格式不正确, 请上传${this.uploadData.fileType.join('/')}格式图片!`)
        } else {
          return this.$message.error(`图片格式不正确, 请上传图片!`)
        }
      } else {
        if (this.uploadData.multiple) {
          this.fileNum++
          this.files.push(file)
          if (this.showText) this.showText += '、' + file.name
          else this.showText = file.name
          clearTimeout(this.uploadTimer)
          this.uploadTimer = setTimeout(() => {
            this.$emit('submitFile')
          }, 100)
        } else {
          this.files[0] = file
          this.showText = file.name
          this.$emit('submitFile')
        }
      }
    },
    // 校检图片类型
    checkType(file) {
      if (this.uploadData.fileType && this.uploadData.fileType.length) {
        let fileExtension = ''
        if (file.name.lastIndexOf('.') > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf('.') + 1)
        }
        const isTypeOk = this.uploadData.fileType.some((type) => {
          if (file.raw.type.indexOf(type) > -1) return true
          if (fileExtension && fileExtension.indexOf(type) > -1) return true
          return false
        })
        if (!isTypeOk) {
          return false
        }
      } else {
        if (file.raw.type.indexOf('image') == -1) return false
      }
      return true
    },
    // 上传请求
    handelUpload() {
      let formData = new FormData()
      if (this.uploadData.multiple) {
        this.files.map((file) => {
          formData.append('files', file.raw)
        })
      } else {
        formData.append('file', this.files[0].raw)
      }
      for (var key in this.uploadData.data) {
        formData.append(key, this.uploadData.data[key])
      }
      request({
        url: this.uploadData.url,
        method: 'post',
        data: formData,
      })
        .then((res) => {
          this.$emit('uploadFinish', 1, res)
        })
        .catch((err) => {
          this.$emit('uploadFinish', 0, err)
        })
    },
    // 图片超出
    uploadExceed(a) {
      this.$modal.msgError(`上传图片数量不能超过 ${this.uploadData.multiple ? this.uploadData.limit : 1} 个!`)
    },
    // 删除图片
    uploadRemove(file) {
      let index = this.files.findIndex((item) => {
        if (file.uid == item.uid) {
          return true
        }
      })
      this.$delete(this.files, index)
    },
    // 删除图片
    handleDelete(index) {
      this.files.splice(index, 1)
    },
    // 判断是否可以上传
    isUpload() {
      if (this.files.length == 0) {
        return '请选择要上传的图片！'
      }
      return true
    },
    // 重置
    resetUpload() {
      this.$refs.upload.clearFiles()
      this.files = []
      this.showText = ''
    },
  },
}
</script>

<style lang="scss">
.stencil-upload-images {
  .el-upload-dragger {
    border: 1px dashed #d4d6d9;
    border-radius: 8px;
    width: 100%;
    height: 100%;
    min-width: 100px !important;
    padding: 32px;
  }

  .el-upload__text {
    font-size: 14px;
    line-height: 14px;
    color: #333333;
  }

  .upload-tip {
    font-size: 12px;
    line-height: 12px;
    color: #bbbdbf;
    margin-top: 16px;
  }

  .el-icon-upload {
    margin-top: 0 !important;
  }

  .el-upload-list {
    .el-upload-list__item:first-child {
      margin-top: 0;
    }
  }
}
</style>
<style scoped></style>
