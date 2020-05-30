<template>
  <el-dialog title="添加" :visible.sync="AddLogoVisible" :before-close="modalClose"
  :modal-append-to-body="false" :close-on-click-modal="false">
    <el-upload
      action="https://jsonplaceholder.typicode.com/posts/"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove">
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" append-to-body="true">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
    <div slot="footer" class="dialog-footer">
      <el-button @click="modalClose">取 消</el-button>
      <el-button type="primary" @click="modalClose">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'DetailAdd',
  props: {
    AddLogoVisible: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      dialogImageUrl: '',
      dialogVisible: false
    };
  },
  methods: {
    modalClose () {
      this.$emit('Add-cancel')
    },
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePictureCardPreview (file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    }
  }
}
</script>

<style lang="less" scoped>

</style>
