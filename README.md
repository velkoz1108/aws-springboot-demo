### config credentials

如果没有配置credentials，则会抛出如下错误

```shell
software.amazon.awssdk.core.exception.SdkClientException: Unable to load credentials from any of the providers 
in the chain AwsCredentialsProviderChain(credentialsProviders=[SystemPropertyCredentialsProvider(), 
EnvironmentVariableCredentialsProvider(), WebIdentityTokenCredentialsProvider(), ProfileCredentialsProvider(), 
ContainerCredentialsProvider(), InstanceProfileCredentialsProvider()]) : [SystemPropertyCredentialsProvider(): 
Unable to load credentials from system settings. 
Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId).,
 EnvironmentVariableCredentialsProvider(): Unable to load credentials from system settings. 
 Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId).,
  WebIdentityTokenCredentialsProvider(): Either the environment variable AWS_WEB_IDENTITY_TOKEN_FILE 
  or the javaproperty aws.webIdentityTokenFile must be set., ProfileCredentialsProvider(): 
  Profile file contained no credentials for profile 'default': ProfileFile(profiles=[]), 
  ContainerCredentialsProvider(): Cannot fetch credentials from container - 
neither AWS_CONTAINER_CREDENTIALS_FULL_URI or AWS_CONTAINER_CREDENTIALS_RELATIVE_URI environment variables are set., 
InstanceProfileCredentialsProvider(): Unable to load credentials from service endpoint.]
```

- 第一种方式是在构建docker镜像时将credentials文件放在`~/.aws/credentials`下

```shell
ADD ./.aws/credentials /root/.aws/credentials
```

- 在运行容器时注入环境变量

```shell
$  docker run -itd --name aws-demo-v2 -e AWS_ACCESS_KEY_ID=xxx -e AWS_SECRET_ACCESS_KEY=yyyy -p 8989:8989 aws-springboot-demo:2.0
```

注意`AWS_ACCESS_KEY_ID`和`AWS_SECRET_ACCESS_KEY`需要大写，小写不行。

- 第三种是通过java options注入参数

```shell
$ docker run -itd --name aws-demo-v3 -e JAVA_OPTS="$JAVA_OPTS -Daws.accessKeyId=xxx -Daws.secretAccessKey=yyy" -p 8989:8989 aws-springboot-demo:3.0
```

注意`Dockerfile`中`$JAVA_OPTS`不能少，否则不起作用

```shell
java -jar $JAVA_OPTS /root/aws-springboot-demo.jar
```