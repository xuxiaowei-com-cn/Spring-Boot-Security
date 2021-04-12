~~~
mvn install:install-file -Dfile="D:\weibo4j-oauth2-2.1.1-beta3.1.2.jar" -DgroupId=com.belerweb -DartifactId=weibo4j-oauth2 -Dversion=2.1.1-beta3.1.2 -Dpackaging=jar
~~~

<!-- 微博 组件 weibo4j -->
~~~
<weibo4j-oauth2.version>2.1.1-beta3.1.2</weibo4j-oauth2.version>
~~~

~~~
<dependency>
	<groupId>com.belerweb</groupId>
	<artifactId>weibo4j-oauth2</artifactId>
	<version>${weibo4j-oauth2.version}</version>
</dependency>
~~~
