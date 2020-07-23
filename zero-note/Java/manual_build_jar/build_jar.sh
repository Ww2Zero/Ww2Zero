###
 # @Author: zero
 # @Date: 2020-06-12 13:32:25
 # @LastEditTime: 2020-07-23 18:51:41
 # @LastEditors: Please set LastEditors
 # @Description: In User Settings Edit
 # @FilePath: /run-java/build_jar.sh
### 
#使用单纯的javac 和 java 命令构建 jar

## 项目结构  
# .
# ├── classes
# │   ├── com
# │   │   └── hello.class        command 3 对应生成
# │   └── zero
# │       └── Zero.class         command 1 对应生成
# ├── lib
# │   ├── hello.jar              command 4 对应生成
# │   └── zero.jar               command 2 对应生成
# ├── resources
# │   ├── manifest-test1.text
# │   └── manifest-test2.text
# └── src
#     ├── com
#     │   └── hello.java   com.hello 调用 zero.Zero.zerozone()方法
#     └── zero
#         └── Zero.java  

# 1 编译Zero.java 并将放到目标目录classes中
javac -d classes/ src/zero/Zero.java

# 2 将classes目录下 zero下所有的.class文件 按照resources/manifest-test1.text文件中的压缩成jar 放在lib目录下
jar cfm lib/zero.jar resources/manifest-test1.text -C classes/ zero

# 3 指定加载classpath：lib/zero.jar，并编译hello.java 并将放到目标目录classes中
javac -d classes/ -cp lib/zero.jar: src/com/hello.java

# 4 将classes目录下 com下所有的.class文件 按照resources/manifest-test1.text文件中的压缩成jar 放在lib目录下
jar cfm lib/hello.jar resources/manifest-test2.text  -C classes/ com/

# 5 启动hello.jar
java -jar lib/hello.jar 
