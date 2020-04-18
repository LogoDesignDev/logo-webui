<template>
  <el-dialog title="编辑图库" :visible.sync="dialogFormVisible" :before-close="modalClose"
  :modal-append-to-body="false" :close-on-click-modal="false">
    <el-form :model="form">
      <el-form-item label="图库名称" :label-width="formLabelWidth">
        <el-input class="input" v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="图库权限" :label-width="formLabelWidth">
        <el-select v-model="form.region" placeholder="请选择">
          <el-option label="私有" value="shanghai"></el-option>
          <el-option label="公有" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="作者名称" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="danger" @click="modalClose">删除图库</el-button>
      <el-button @click="modalClose">取 消</el-button>
      <el-button type="primary" @click="modalClose">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'MyLogoEdit',
  props: {
    dialogFormVisible: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      form: {
        name: '你好',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '120px'
    };
  },
  methods: {
    modalClose () {
      this.$emit('dialog-cancel')
    }
  }
}
</script>

<style>
  .input {
    width: 300px;
  }
</style>
