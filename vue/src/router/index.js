import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import Cookies from "js-cookie";


Vue.use(VueRouter)

const routes = [

    //=-------------登录-------------
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Login.vue')
  },
//下面的404页面，在上面路由都没走到，就执行404页面
  {
    path: '/',
    name: 'Layout',
    redirect:'/home',
    component: Layout,  //下面都是子路由
    children: [
      //-----主页-------
      {
        path: 'home',
        name: 'Home',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/home/HomeView.vue')
      },

      // --- USER表 ---------
      {
        path: 'userList',
        name: 'UserList',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/user/User.vue')
      },
      {
        path: 'addUser',
        name: 'AddUser',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/user/AddUser')
      },
      {
        path: 'editUser',
        name: 'EditUser',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/user/EditUser')
      },
      // --- admin表 ---------
      {
        path: 'adminList',
        name: 'AdminList',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/admin/List.vue')
      },
      {
        path: 'addAdmin',
        name: 'AddAdmin',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/admin/Add.vue')
      },
      {
        path: 'editAdmin',
        name: 'EditAdmin',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/admin/Edit.vue')
      },
      // --- category ---------
      {
        path: 'categoryList',
        name: 'CategoryList',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/category/List.vue')
      },
      {
        path: 'addCategory',
        name: 'AddCategory',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/category/Add.vue')
      },
      {
        path: 'editCategory',
        name: 'EditCategory',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/category/Edit.vue')
      },

      // --- book ---------
      {
        path: 'bookList',
        name: 'BookList',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/book/List.vue')
      },
      {
        path: 'addBook',
        name: 'AddBook',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/book/Add.vue')
      },
      {
        path: 'editBook',
        name: 'EditBook',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/book/Edit.vue')
      },
      // --- borrow ---------
      {
        path: 'borrowList',
        name: 'BorrowList',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/borrow/List.vue')
      },
      {
        path: 'addBorrow',
        name: 'AddBorrow',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/borrow/Add.vue')
      },
      {
        path: 'editBorrow',
        name: 'EditBorrow',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/borrow/Edit.vue')
      },
      //--------------还书 -----------------
      {
        path: 'returList',
        name: 'returList',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/retur/List.vue')
      }

    ]
  },
  {
    path: "*",
    component: () => import('@/views/404')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//登录校验，路由守卫，拦截器，判断是否登录 ，前台拦截器，如果没有登录首先被前台拦截
router.beforeEach((to,from,next)=>{
  if (to.path === '/login') next()
  const  admin = Cookies.get("admin")
  if (!admin &&  to.path !=='/login') return next("/login")
  next()
})

export default router
