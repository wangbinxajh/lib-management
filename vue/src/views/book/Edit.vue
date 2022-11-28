<template>
  <div>

    <div style="margin-bottom: 30px"> 编辑图书 </div>
    <el-form :inline="true" :model="form"   style="width: 80%" label-width="100px" >
      <el-form-item label="名称">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="编号">
        <el-input v-model="form.bookNo" placeholder="请输入编号"></el-input>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description" placeholder="请输入描述"></el-input>
      </el-form-item>
      <el-form-item label="分类">
        <el-cascader :props="{value:'name',label:'name'}" v-model="form.categories" :options="categories" @change="handleChange"></el-cascader>
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
  name: 'EditBook',
  data() {
    return {
      form: {},
      categories: []
    }
  },
  created(){
    request.get('/category/tree').then(res=>{
      this.categories = res.data
    })
      const  id = this.$route.query.id
      request.get("/book/" + id ).then(res=>{
        this.form = res.data
        if (this.form.category){
          this.form.categories = this.form.category.split('>')
          console.log(this.form.categories)
        }
      })
  },
  methods: {
    save(){
      request.put('/book/update',this.form).then(res=>{
        if (res.code == '200'){
          this.$notify.success("更新成功")
           this.$router.push("/BookList")
        }else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>
