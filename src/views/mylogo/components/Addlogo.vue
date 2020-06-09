<template>
  <el-dialog title="添加" :visible.sync="AddLogoVisible" :before-close="modalClose"
  :modal-append-to-body="false" :close-on-click-modal="false">
    <div class="border">
      <el-image v-if="previewImageSrc" :src="previewImageSrc" class="preImg" :fit="fit"></el-image>
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </div>
    <el-input
      placeholder="请输入Logo名称"
      v-model="input"
      class="inputName"
      clearable>
    </el-input>
    <div slot="footer" class="dialog-footer">
      <el-button id="choosePicBtn">
        <input type="file" @change="displayImage" ref="fileInput">
        选择图片
      </el-button>
      <el-button @click="modalClose">取 消</el-button>
      <el-button type="primary" @click="uploadImage">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getToken } from 'utils/auth'
import { upload, addlogo } from 'api/mylogo'
import store from 'store'
export default {
  name: 'DetailAdd',
  props: {
    AddLogoVisible: {
      type: Boolean,
      default: false
    },
    galleryid: {
      type: Number
    }
  },
  data () {
    return {
      previewImageSrc: null,
      file: null,
      fit: 'contain',
      input: ''
    };
  },
  methods: {
    displayImage () {
      const file = this.$refs.fileInput.files[0]
      this.file = file
      const fr = new FileReader()
      fr.onload = (e) => {
        this.previewImageSrc = e.target.result
      }
      fr.readAsDataURL(file)
    },
    uploadImage () {
      const formdata = new FormData()
      formdata.append('file', this.file)
      upload(formdata).then(this.AddLogo)
    },
    AddLogo (res) {
      const data = res.data
      console.log(data.url)
      if (data.code === 200) {
        const postdata = {
          token: getToken(),
          galleryid: this.$props.galleryid,
          name: this.input,
          author: store.state.userInfo.username,
          imgUrl: 'http://47.115.52.184:8900/' + data.url
        }
        addlogo(postdata).then(this.handleAddLogoSucc)
      }
    },
    handleAddLogoSucc (res) {
      res = res.data
      const code = res.code
      this.$emit('Add-succ', code)
    },
    modalClose () {
      this.$emit('Add-cancel')
    }
  }
}
</script>

<style lang="less" scoped>
  .preImg {
    width: 178px;
    height: 178px;
    display: block;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .inputName {
    width: 500px;
    margin-top: 10px;
  }
  .border {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    position: relative;
    overflow: hidden;
    width: 178px;
    height: 178px;
    margin-bottom: 10px;
  }
  #choosePicBtn {
    height: 40px;
    width: 100px;
    padding: 0;
    float: left;
  }
  #choosePicBtn input{
    height: 40px;
    width: 100px;
    color: transparent;
    opacity: 0;
    position: absolute;
    margin-left: -22px;
    margin-top: -12px;
  }
</style>
