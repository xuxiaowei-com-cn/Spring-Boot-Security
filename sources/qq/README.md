mvn install:install-file -Dfile="D:\Sdk4J-2.0.1.jar" -DgroupId=net.gplatform -DartifactId=Sdk4J -Dversion=2.0.1 -Dpackaging=jar

<!-- 微博 组件 weibo4j -->
<Sdk4J.version>2.0.1</Sdk4J.version>

<dependency>
	<groupId>net.gplatform</groupId>
	<artifactId>Sdk4J</artifactId>
	<version>${Sdk4J.version}</version>
</dependency>