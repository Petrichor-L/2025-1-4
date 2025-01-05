# 学生成绩管理系统
主要使用 Vue3 + SpringBoot 实现

# 如何运行：
1. 在 2025-1-4\MavenDemo\src\main\resources\application.properties文件中修改下面三行，使java程序能够连接本地数据库（记得把书名号《》也删掉）
  - spring.datasource.url=jdbc:mysql://localhost:3306/《数据库的名字》?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
  - spring.datasource.username=《数据库账号》
  - spring.datasource.password=《数据库密码》
2. 运行mysql数据库，运行仓库中的 2025-1-4\grade.sql，建表，包含测试数据，账号密码在user表中，数据库的名字为 grade
3. 运行 2025-1-4\MavenDemo\src\main\java\com\etc\boot\Application.java
4. 在 2025-1-4\VUE\ 文件夹下运行终端，依次输入以下命令
  - pnpm install
  - pnpm dev
5. 在浏览器输入 http://localhost:5173/ 即可访问


# Student Grade Management System
Implemented primarily using Vue3 + SpringBoot

# How to Run:
1. In the `2025-1-4\MavenDemo\src\main\resources\application.properties` file, modify the following three lines to enable the Java program to connect to the local database (remember to delete the book symbols 《》 as well):
   - `spring.datasource.url=jdbc:mysql://localhost:3306/《Database Name》?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false`
   - `spring.datasource.username=《Database Username》`
   - `spring.datasource.password=《Database Password》`

2. Run the MySQL database and execute the `2025-1-4\grade.sql` file from the repository to create tables, including test data. The account and password are in the `user` table, and the database name is `grade`.

3. Run `2025-1-4\MavenDemo\src\main\java\com\etc\boot\Application.java`.

4. In the `2025-1-4\VUE\` folder, open a terminal and enter the following commands in sequence:
   - `pnpm install`
   - `pnpm dev`

5. Enter `http://localhost:5173/` in the browser to access the system.
