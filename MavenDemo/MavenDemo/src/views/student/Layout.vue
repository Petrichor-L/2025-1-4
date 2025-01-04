<template>
  <div class="student-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu router>
          <el-menu-item index="/student">首页</el-menu-item>
          <el-menu-item index="/student/grades">成绩查询</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'StudentLayout'
}
</script> 