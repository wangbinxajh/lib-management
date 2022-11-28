<template>
  <div class="home">
    <!-- 表单搜索 -->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入用户名" v-model="params.username"></el-input>
      <el-input style="width: 240px ; margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-input style="width: 240px ; margin-left: 5px" placeholder="请输入邮箱" v-model="params.email"></el-input>
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
          prop="phone"
          label="联系方式">
      </el-table-column>

      <el-table-column
          prop="email"
          label="邮箱">
      </el-table-column>
      <el-table-column label="状态">
        <template  v-slot="scope">
            <el-switch
                v-model="scope.row.status"
                @change="changeStatus(scope.row)"
                active-color="#13ce66"
                inactive-color="#ff4949">
            </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="350">
          <template v-slot="scope">
<!--            scope.row就是当前行的数据-->
            <el-button type="primary" @click="$router.push('/editAdmin?id=' + scope.row.id)">编辑</el-button>
              <el-popconfirm title="确定删除吗" @confirm="del(scope.row.id)">
                <el-button type="danger" slot="reference">删除</el-button>
              </el-popconfirm>
            <!--<el-button style="margin-left:5px" type="warning" @click="handleChangePass(scope.row)">修改密码</el-button> -->
          </template>
      </el-table-column>
    </el-table>



    <!-- 分页 -->
    <div style="margin-top: 20px">
      <el-pagination background layout="prev, pager, next" @current-change="handelCurrentChange"  :page-sizes="params.pageSize"  :current-page="params.pageNum" :total="total">
      </el-pagination>
    </div>
    <!--
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px">
        <el-form-item label="新密码">
          <el-input  v-model="form.newPass" autocomplete="off"  show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePass">确 定</el-button>
      </div>
    </el-dialog>
    -->
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue';

import request from "@/utils/request";
import Cookies from "js-cookie";


export default {
  name: 'adminList',
  data(){
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) :{},
      tableData: [ ],
      total: 0,
      form :{},
      dialogTableVisible: false,
      params:{
        pageNum: 1,
        pageSize: 10,
        username: '',
        phone: '',
        email: ''
      }

    }
  },
  created(){
    this.load()
  },
  methods: {
    changeStatus(row){
      request.put('/admin/update',row).then(res=>{
        if (res.code == '200'){
          this.$notify.success("修改成功")
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    },
    handleChangePass(row){
        this.form = JSON.parse(JSON.stringify(row))
        this.dialogFormVisible = true

    },
    savePass(){
        request.put("/admin/password" ,this.form).then(res =>{
            if (res.code == '200'){
                this.$notify.success("修改成功")
                if (this.form.id === this.admin.id){
                    Cookies.remove('admin')
                    this.$router.push('/login')
                }
            }else {
              this.$notify.error("修改失败")
            }
        })
    },
    load(){
      //   fetch('http://localhost:9090/admin/list').then(res=>res.json()).then(res=>{
      //     console.log(res)
      //     this.tableData = res
      //   })
      request.get('/admin/page', {
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
        username: '',
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
      request.delete("/admin/delete/" + id).then(res=>{
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
