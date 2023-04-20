<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <!--侧边栏-->
    <el-aside width="300px" style="background-color: rgb(238, 241, 246);height: 100%;">
      <Aside/>
    </el-aside>

    <el-container>
      <!--抬头-->
      <el-header style="text-align: right; font-size: 12px">
        <Header/>
      </el-header>

      <!--主体部分-->
      <el-main>
        <keep-alive>
          <router-view></router-view>
        </keep-alive>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import Aside from "@/components/Aside";
import Header from "@/components/Header";
import Home from "@/views/Home";
export default {
  components: {Home, Header, Aside},

};
</script>
