<template>
  <div>

    <div style="margin-bottom: 30px">  添加图书 </div>
    <el-form :inline="true" :model="form"   style="width: 80%" label-width="100px" >
      <el-form-item label="图书名称">
        <el-input v-model="form.name" placeholder="请输入图书名称"></el-input>
      </el-form-item>
      <el-form-item label="编号">
        <el-input v-model="form.bookNo" placeholder="编号"></el-input>
      </el-form-item>

      <el-form-item label="描述">
        <el-input type="textarea" v-model="form.description" placeholder="描述"></el-input>
      </el-form-item>

      <el-form-item label="作者">
        <el-input  v-model="form.author" placeholder="作者"></el-input>
      </el-form-item>

      <el-form-item label="出版社">
        <el-input  v-model="form.publisher" placeholder="出版社"></el-input>
      </el-form-item>

      <el-form-item label="数量">
        <el-input  v-model="form.nums" placeholder="数量"></el-input>
      </el-form-item>


      <el-form-item label="分类" prop="category">
        <el-cascader :props="{value:'name',label:'name'}" v-model="form.categories" :options="categories" @change="handleChange"></el-cascader>
      </el-form-item>

      <el-form-item label="封面">
        <el-input  v-model="form.cover" placeholder="封面"></el-input>
      </el-form-item>


      <el-form-item label="出版日期" prop="publishDate">
        <el-date-picker v-model="form.publishDate"  value-format="yyyy-MM-dd"  type="date" placeholder="请选择日期">
        </el-date-picker>
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
  name: 'AddBook',
  data() {
    return {
      form: {},
      categories: []
    }
  },
  created() {
    request.get('/category/tree').then(res=>{
      this.categories = res.data
    })
  },
  methods: {
      save(){
        request.post('/book/save',this.form).then(res=>{
          if (res.code == '200'){
              this.$notify.success("新增成功")
              this.form = {}
              this.load()
          }else {
               this.$notify.error(res.msg)
          }
        })
      },
    handleChange(){

    }
  }
}
</script>
