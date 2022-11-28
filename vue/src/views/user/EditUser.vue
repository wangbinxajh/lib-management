<template>
  <div>

    <div style="margin-bottom: 30px">  新增用户 </div>
    <el-form :inline="true" :model="form"   style="width: 80%" label-width="100px" >
      <el-form-item label="姓名">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
      </el-form-item>

      <el-form-item label="性别">
        <el-input v-model="form.sex" placeholder="请输入性别"></el-input>
      </el-form-item>

      <el-form-item label="联系方式">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
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
  data() {
    return {
      form: {}
    }
  },
  created(){
      const  id = this.$route.query.id
      request.get("/user/" + id ).then(res=>{
        this.form = res.data
      })
  },
  methods: {
    save(){
      request.put('/user/update',this.form).then(res=>{
        if (res.code == '200'){
          this.$notify.success("更新成功")
           this.$router.push("/userList")
        }else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>
