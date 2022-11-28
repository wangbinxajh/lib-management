<template>
  <div class="home">
    <!-- 表单搜索 -->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入名称" v-model="params.name"></el-input>
      <el-input style="width: 240px ; margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search" ></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh" ></i>重置</el-button>
    </div>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column
          prop="id"
          label="编号"
          width="180">
      </el-table-column>

      <el-table-column
          prop="username"
          label="卡号">
      </el-table-column>

      <el-table-column
          prop="name"
          label="名称"
          width="180">
      </el-table-column>

      <el-table-column
          prop="age"
          label="年龄"
          width="180">
      </el-table-column>

      <el-table-column
          prop="address"
          label="地址">
      </el-table-column>

      <el-table-column
          prop="phone"
          label="联系方式">
      </el-table-column>

      <el-table-column
          prop="sex"
          label="性别">
      </el-table-column>

      <el-table-column label="操作">
          <template v-slot="scope">
<!--            scope.row就是当前行的数据-->
            <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)">编辑</el-button>
            <el-popconfirm title="确定删除吗" @confirm="del(scope.row.id)">
              <el-button type="danger" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
      </el-table-column>



    </el-table>



    <!-- 分页 -->
    <div style="margin-top: 20px">
      <el-pagination background layout="prev, pager, next" @current-change="handelCurrentChange"  :page-sizes="params.pageSize"  :current-page="params.pageNum" :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue';

import request from "@/utils/request";


export default {
  name: 'User',
  data(){
    return {
      tableData: [ ],
      total: 0,
      params:{
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''
      }

    }
  },
  created(){
    this.load()
  },
  methods: {
    load(){
      //   fetch('http://localhost:9090/user/list').then(res=>res.json()).then(res=>{
      //     console.log(res)
      //     this.tableData = res
      //   })
      request.get('/user/page', {
        params: this.params
      }).then(res =>{
        if (res.code == '200'){
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset(){
      this.params = {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''
      }
      this.load()
    },
    //翻页功能
    handelCurrentChange(pageNum){
      this.params.pageNum = pageNum
      this.load()
    },
    del(id){
      request.delete("/user/delete/" + id).then(res=>{
        if(res.code == '200'){
            this.$notify.success('删除成功')
          this.load()
        }else {
            this.$notify.error(res.msg)
        }
      })
    }


  },
  components: {
    HelloWorld
  }
}
</script>
