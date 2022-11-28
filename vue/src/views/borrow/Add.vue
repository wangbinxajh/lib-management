<template>
  <div>

    <div style="margin-bottom: 30px">  添加借书记录 </div>
    <el-form :inline="true" :model="form"   style="width: 80%" label-width="100px" >
      <el-form-item label="图书名称" prop="bookName">
        <el-input v-model="form.bookName" disabled placeholder="请输入图书名称"></el-input>
      </el-form-item>

      <el-form-item label="图书标准码" prop="bookNo">
        <el-select v-model="form.bookNo" clearable filterable placeholder="请选择" @change="selBook">
          <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookNo"
              :value="item.bookNo">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="借出天数" prop="days">
        <el-input-number v-model="form.days" :min="1" :max="30" label="借出天数"></el-input-number>
      </el-form-item>

      <el-form-item label="会员码" prop="userNo">
        <el-select v-model="form.userNo" clearable filterable placeholder="请选择" @change="selUser">
          <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.username"
              :value="item.username">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="用户名称" prop="userName">
        <el-input v-model="form.userName"  disabled  placeholder="用户名称"></el-input>
      </el-form-item>



      <el-form-item label="用户联系方式" prop="userPhone">
        <el-input v-model="form.userPhone" disabled placeholder="用户联系方式"></el-input>
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
      form: { days:1},
      books: [],
      users: [],
    }
  },
  created() {
    request.get('/book/list').then(res=>{
      this.books = res.data
    })
    request.get('/user/list').then(res=>{
      this.users = res.data
    })
  },
  methods: {
      save(){
        request.post('/borrow/save',this.form).then(res=>{
          if (res.code == '200'){
              this.$notify.success("新增成功")
              this.form = {}
              this.load()
          }else {
               this.$notify.error(res.msg)
          }
        })
      },
    selBook(){
          const book = this.books.find(v=>v.bookNo === this.form.bookNo)
          this.form.bookName = book.name
    },
    selUser(){
          const user = this.users.find(v=>v.username === this.form.userNo)
          this.form.userName = user.name
          this.form.userPhone = user.phone
    },
    handleChange(){

    }
  }
}
</script>
