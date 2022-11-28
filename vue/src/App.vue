<template>
  <div id="app">
    <router-view/>
<!--    &lt;!&ndash; 头部区域代码布局 &ndash;&gt;-->
<!--    <div style="height: 60px;  line-height: 60px;  background-color: white; margin-bottom: 2px">-->
<!--        <img src="@/assets/book.png" alt="" style="width: 40px; position: relative; top: 10px; left: 20px">-->
<!--        <span style="margin-left: 25px;font-size: 24px">图书管理系统</span>-->
<!--    </div>-->
<!--    &lt;!&ndash;侧边栏和主体 &ndash;&gt;-->
<!--    <div style="display: flex">-->
<!--      &lt;!&ndash;侧边栏导航 &ndash;&gt;-->
<!--        <div style="width: 200px; min-height: calc(100vh - 62px); overflow: hidden; margin-right: 2px; background-color: white">-->
<!--          &lt;!&ndash;通过路由的方式访问页面 $route.path, router ,default-openeds="['/']"属性是下拉菜单打开状态可以查询element,&ndash;&gt;-->
<!--          <el-menu :default-active="$route.path" router  class="el-menu-demo">-->
<!--            <el-menu-item index="/">-->
<!--              <i class="el-icon-eleme"></i>-->
<!--              <span>首页</span>-->
<!--            </el-menu-item>-->
<!--            <el-submenu index="user">-->
<!--              <template slot="title">-->
<!--                <i class="el-icon-question"></i>-->
<!--                <span>会员管理</span>-->
<!--              </template>-->
<!--              <el-menu-item index="/addUser">会员添加</el-menu-item>-->
<!--              <el-menu-item index="/userList">会员列表</el-menu-item>-->
<!--            </el-submenu>-->
<!--            <el-submenu index="admin">-->
<!--              <template slot="title">-->
<!--                <i class="el-icon-user"></i>-->
<!--                <span>管理员管理</span>-->
<!--              </template>-->
<!--              <el-menu-item index="/addAdmin">管理员添加</el-menu-item>-->
<!--              <el-menu-item index="/adminList">管理员列表</el-menu-item>-->
<!--            </el-submenu>-->
<!--          </el-menu>-->
<!--        </div>-->

<!--      &lt;!&ndash; 主体  &ndash;&gt;-->
<!--      <div style="flex: 1; background-color: white; padding: 10px">-->
<!--        <router-view/>-->

<!--      </div>-->

<!--    </div>-->
  </div>
</template>

