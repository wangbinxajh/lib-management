<template>
  <div class="home">
    <!-- 表单搜索 -->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.name"></el-input>
      <el-input style="width: 240px" placeholder="请输入图书编码" v-model="params.bookNo"></el-input>
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
          label="图书名称">
      </el-table-column>
      <el-table-column
          prop="description"
          label="描述">
      </el-table-column>
      <el-table-column
          prop="author"
          label="作者">
      </el-table-column>
      <el-table-column
          prop="cover"
          label="封面">
        <template v-slot="scope">
            <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
        </template>
      </el-table-column>

      <el-table-column
          prop="bookNo"
          label="图书编号">
      </el-table-column>

      <el-table-column
          prop="nums"
          label="图书数量">
      </el-table-column>

      <el-table-column
          prop="category"
          label="图书分类">
      </el-table-column>
      <el-table-column
          prop="publishDate"
          label="出版日期">
      </el-table-column>
      <el-table-column
          prop="createtime"
          label="创建时间">
      </el-table-column>


      <el-table-column label="操作" width="300">
          <template v-slot="scope">
<!--            scope.row就是当前行的数据-->
            <el-button type="primary" @click="$router.push('/editBook?id=' + scope.row.id)">编辑</el-button>
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
import Cookies from "js-cookie";


export default {
  name: 'BookList',
  data(){
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) :{},
      tableData: [ ],
      total: 0,
      params:{
        pageNum: 1,
        pageSize: 10,
        name: '',
        bookNo: ''
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
      request.get('/book/page', {
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
        bookNo: ''
      }
      this.load()
    },
    //翻页功能
    handelCurrentChange(pageNum){
      this.params.pageNum = pageNum
      this.load()
    },
    del(id){
      request.delete("/book/delete/" + id).then(res=>{
        if(res.code == '200'){
            this.$notify.success('删除成功')
          this.load()
        }else {
            this.$notify.error(res.msg)
        }
      })
    },

  },
  components: {
    HelloWorld
  }
}
</script>
