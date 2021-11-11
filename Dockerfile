FROM anapsix/alpine-java
MAINTAINER twang@telenav.cn

WORKDIR /root

#ADD ./.aws/credentials /root/.aws/credentials
ADD ./target/aws-springboot-demo-*.jar /root/aws-springboot-demo.jar

EXPOSE 8989

ENTRYPOINT ["sh", "-c", "java -jar $JAVA_OPTS /root/aws-springboot-demo.jar"]