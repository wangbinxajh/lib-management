<template>
  <div class="home">
    <!-- 表单搜索 -->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入分类名称" v-model="params.name"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search" ></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh" ></i>重置</el-button>
    </div>
    <el-table :data="tableData" stripe style="width: 100%"  row-key="id" default-expand-all>
      <el-table-column
          prop="id"
          label="编号"
          width="180">
      </el-table-column>

      <el-table-column
          prop="name"
          label="分类名称">
      </el-table-column>
      <el-table-column
          prop="remark"
          label="备注">
      </el-table-column>
      <el-table-column
          prop="createtime"
          label="创建时间">
      </el-table-column>


      <el-table-column label="操作" width="300">
          <template v-slot="scope">
<!--            scope.row就是当前行的数据-->
            <el-button type="primary" @click="">编辑</el-button>
            <el-button type="success" v-if="!scope.row.pid" @click="handleAdd(scope.row)">添加二级分类</el-button>
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

    <el-dialog title="添加二级分类" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px">
        <el-form-item label="分类名称" prop="name">
          <el-input  v-model="form.name" ></el-input>
        </el-form-item>
        <el-form-item label="备注名称" prop="remark">
          <el-input  v-model="form.remark" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue';

import request from "@/utils/request";
import Cookies from "js-cookie";


export default {
  name: 'CategoryList',
  data(){
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) :{},
      tableData: [ ],
      total: 0,
      dialogFormVisible: false,
      form: {},
      pid: null,
      params:{
        pageNum: 1,
        pageSize: 10,
        name: '',
        remark: ''
      }

    }
  },
  created(){
    this.load()
  },
  methods: {
    load(){
      //   fetch('http://localhost:9090/admin/list').then(res=>res.json()).then(res=>{
      //     console.log(res)
      //     this.tableData = res
      //   })
      request.get('/category/page', {
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
        remark: ''
      }
      this.load()
    },
    //翻页功能
    handelCurrentChange(pageNum){
      this.params.pageNum = pageNum
      this.load()
    },
    del(id){
      request.delete("/category/delete/" + id).then(res=>{
        if(res.code == '200'){
            this.$notify.success('删除成功')
          this.load()
        }else {
            this.$notify.error(res.msg)
        }
      })
    },
    handleAdd(row){
        this.pid = row.id
        this.dialogFormVisible = true
    },
    save(){
      this.form.pid = this.pid
      request.post('/category/save',this.form).then(res=>{
        if (res.code == '200'){
          this.$notify.success("新增二级分类成功")
          this.dialogFormVisible = false
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
