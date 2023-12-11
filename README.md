# Final_Project

# 이메일 인증번호 보내기

. 참고 : 1. https://limjunho.github.io/2022/08/08/EmailAuthentication.html
. 참고 : 2. https://gwamssoju.tistory.com/108
. 참고 : 3. https://green-bin.tistory.com/83

. 참고 : 아래 오류 발생
2023-12-09 14:21:22.241 WARN 6068 --- [ restartedMain] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'mailService' defined in file [C:\Final_Project\bookmark\build\classes\java\main\site\markeep\bookmark\email\service\MailService.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.mail.javamail.JavaMailSender' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
: 해결 방법
이 오류는 MailService 클래스에서 JavaMailSender의 의존성을 주입받을 수 없다는 것을 나타냅니다. 주로 이런 오류는 JavaMailSender를 주입할 수 있는 Bean이 Spring 컨테이너에 없거나, 여러 개 존재해서 Spring이 어떤 것을 주입해야 하는지 판단하지 못할 때 발생합니다.

      @Configuration
      public class MailConfig {

         @Bean
         public JavaMailSender javaMailSender() {
            return new JavaMailSenderImpl();
         }
      }
