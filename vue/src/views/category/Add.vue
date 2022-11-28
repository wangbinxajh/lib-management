<template>
  <div>

    <div style="margin-bottom: 30px">  添加分类 </div>
    <el-form :inline="true" :model="form"   style="width: 80%" label-width="100px" >
      <el-form-item label="分类名称">
        <el-input v-model="form.name" placeholder="请输入分类名称"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remark" placeholder="备注"></el-input>
      </el-form-item>

        <div style="text-align: center; margin-top: 30px">
            <el-button type="primary" @click="save"> 提交</el-button>
             <el-button type="danger"> 取消</el-button>
        </div>

    </el-form>


  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue';

import request from "@/utils/request";


export default {
  name: 'AddCategory',
  data() {
    return {
      form: {}
    }
  },
  methods: {
      save(){
        request.post('/category/save',this.form).then(res=>{
          if (res.code == '200'){
              this.$notify.success("新增成功")
              this.form = {}
              this.load()
          }else {
               this.$notify.error(res.msg)
          }
        })
      }
  }
}
</script>
