![reversal](https://capsule-render.vercel.app/api?type=rect&text=AnnieHands!&textBg=true&color=gradient&fontAlign=30&fontSize=30&desc=손을%20잡아주세요.&descAlign=60&descAlignY=50)
## 🔍 프로젝트 설명
- 배포 링크➡️ [**https://anniehands.co.kr**](https://anniehands.co.kr)
- 테스트 계정
  - 일반 유저
  - ID: test
  - PASSWORD: 1234
- 개인 프로젝트로 Back/FrontEnd 개발을 주도하고, AWS를 활용하여 CI/CD와 DataBase 등 Infra를 구축한 프로젝트
- 반려동물 용품을 판매하는 쇼핑몰에 실종 및 입양 게시판을 추가하여, 반려동물 커뮤니티의 특성을 결합한 웹 애플리케이션
- 개발 중인 애플리케이션으로 지속적으로 업데이트 중 ⚙️
### 시스템 아키텍쳐
![시스템 아키텍쳐](https://github.com/jiho313/anniehands/assets/130119257/26d524c4-d236-4c2d-a1c9-277cfcf0ee0f)
### ERD
![anniehands shop (2)](https://github.com/jiho313/anniehands/assets/130119257/b5809883-b3ec-49ba-a1bd-2826da640a40)
## 🛠️사용기술
|영역|기술|
| --- | --- |
| 🖥 Back-End | ![Spring Boot](https://img.shields.io/badge/Spring_Boot-green?style=flat&logo=spring-boot&logoColor=white) ![Spring Security](https://img.shields.io/badge/Spring_Security-green?style=flat&logo=springsecurity&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-darkgreen?style=flat&logo=thymeleaf) ![Gradle](https://img.shields.io/badge/Gradle-blue?style=flat&logo=Gradle)|
| 🎨 Front-End | ![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white) ![JavaScript](https://img.shields.io/badge/JavaScript-yellow?style=flat&logo=javascript&logoColor=black) ![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white) |
| 🗄️ DataBase |![MySQL](https://img.shields.io/badge/MySQL-blue?style=flat&logo=mysql&logoColor=black) ![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white) ![amazonrds](https://img.shields.io/badge/RDS-527FFF?style=flat&logo=Amazonrds&logoColor=white)|
| ☁️ Infra |![amazonec2](https://img.shields.io/badge/EC2-FF9900?style=flat&logo=amazonec2&logoColor=white) ![amazons3](https://img.shields.io/badge/S3-DC382D?style=flat&logo=amazons3&logoColor=white) ![Route53](https://img.shields.io/badge/Route53-8C4FFF?style=flat&logo=amazonroute53&logoColor=white) ![ACM](https://img.shields.io/badge/ACM-569A31?style=flat&logo=amazonaws&logoColor=white) ![ELB](https://img.shields.io/badge/ELB-FF9900?style=flat&logo=amazonaws&logoColor=white) ![CodeDeploy](https://img.shields.io/badge/CodeDeploy-569A31?style=flat&logo=amazonaws&logoColor=white) |
| 🔧 Tools | ![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github) ![GitHubActions](https://img.shields.io/badge/GitHubActions-2088FF?style=flat&logo=githubactions&logoColor=white) ![IntelliJ](https://img.shields.io/badge/IntelliJ-100000?style=flat&logo=intellijidea) |

## 🌱 트러블 슈팅 및 성장내용
### 1. **GitHub Actions와 AWS를 활용한 CI/CD 도입**
<details>
<summary>클릭하고 더 자세히 보기!</summary>
	
### **[상황설명]**

1. 기존에 스프링 부트 애플리케이션인 ‘AnnieHands’는 EC2를 main brach에 변화가 있을 때마다 git clone을 하여 새로 테스트 및 빌드하여 배포하고 있었음.

### **[문제점 및 이슈]**

1. 새로운 기능을 개발하거나 코드의 변화가 있을 때마다 git clone 작업을 수동으로 진행해야 함.
2. EC2에서 빌드에 실패할 경우, 배포가 원활하게 이루어지지 않으며 이로 인해 애플리케이션이 중단되는 문제가 발생함.

### **[원인분석]**

1. CI/CD Pipeline을 구성하지 않고 수동 배포 방식으로 구현했기 때문임.

![Manual Deployment](https://github.com/jiho313/anniehands/assets/130119257/061ee078-ce9e-4c21-b421-53fbf7a3ebcd)

### **[해결 방안 및 결과]**

1. CI/CD 관련 도구인 GitHub Actions과 AWS S3, CodeDeploy를 활용하여 CI/CD Pipeline을 구축함.
    1. main branch에 Push나 PR이 발생하면 GitHub Actions를 실행됨.
    2. 정의한 workflow에 따라 자동으로 테스트 및 빌드를 수행함.
    3. 빌드된 아티팩트를 S3에 전송하고, CodeDeploy에게 배포 명령을 내림.
    4. CodeDeploy는  S3의 아티팩트를 EC2의 임시 디렉토리에 업로드 함.
    5. `appspec.yml`에 정의된 대로 임시 디렉토리의 아티팩트를 EC2의 최종위치로 복사함.
    6. `deploy.sh`에 정의된 절차에 따라 아티팩트가 업로드된 EC2 경로를 찾아 배포 수행함.
2. 이로 인해 더 이상 main branch가 업데이트될 때마다 수동으로 clone과 배포 작업을 할 필요가 없어짐.
3. 테스트 및 빌드가 정상적으로 완료됐을 때 자동으로 EC2에 배포 과정을 수행하므로 지속적 배포가 가능해졌음.
![CICD Pipeline](https://github.com/jiho313/anniehands/assets/130119257/b3473562-d6f8-45b7-83dd-0894c8e8699d)

### **[깨달은 점]**

1. **개발외 작업에 걸리는 시간을 최소화하자! CI/CD Pipeline** 도입으로 테스트와 빌드 그리고 배포 과정을 자동화함으로써, 이전의 수동 빌드 배포 방식에 들였던 시간과 노력을 줄일 수 있었다. 이는 **개발에 더 집중할 수 있는 환경을 마련**할 수 있었다.
2. **자동화된 배포로 향상된 안정성** 이렇게 자동화된 프로세스를 통해 빌드와 배포 과정에서 발생할 수 있는 인간의 실수를 줄여줄 수 있기 때문에 개발과 배포의 안정성을 높일 수 있다.
</details>

> **요약➡️ CI/CD Pipeline도입**으로 **테스트, 빌드, 배포 과정을 자동화**함으로써 개발에 더 집중할 수 있는 환경을 마련했고, 자동화를 통해 인간의 실수를 줄이고 개발 및 배포 과정의 안정성을 향상시켰다.
>
- 🔴 CI/CD 도입 전 수동 배포 과정
![Manual Deployment](https://github.com/jiho313/anniehands/assets/130119257/061ee078-ce9e-4c21-b421-53fbf7a3ebcd)
- 🔵 CI/CD 도입 후 자동화 된 빌드/배포
![CICD Pipeline](https://github.com/jiho313/anniehands/assets/130119257/0e84c13c-89e4-4591-a7cd-cd4b304b93d8)

### 2. **AOP 개념을 활용한 `@ControllerAdivce` 중앙집중식 예외 처리**
<details>
<summary>클릭하고 더 자세히 보기!</summary>
	
**(중앙처리 전) try~catch 문이 여러 컨트롤러에 흩어져 있고 에러 메시지 직접 정의**

```java
public class UserController {

    @PostMapping("/signup")
    public String signup(@Valid UserForm userForm, BindingResult errors) {
        validateUserForm(userForm, errors);
        if (errors.hasErrors()) {
            return "page/user/signup";
        }
        try {
						// 순환 참조 방지를 위해 가입 전용 userRegistrationService에서 userService를 의존하여 가입
            userRegistrationService.registerUser(userForm);
            return "redirect:/page/user/login";
        } catch (UserException e) {
            // UserException 발생 시 처리 로직
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/" + ex.getRedirectUrl(); // 예외 객체에 저장된 URL로 리다이렉트
        } catch (Exception e) {
            // 기타 예외 처리 로직
            redirectAttributes.addFlashAttribute("errorMessage", "등록 중 오류 발생");
            return "redirect:/page/user/signup";
        }
    }
}
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 아이디 혹은 이메일 중복 검사
    private void validateDuplicateUser(User user) {
        userRepository.findById(user.getId()).ifPresent(u -> {
            throw new UserException("이미 사용 중인 아이디입니다.");
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserException("이미 사용 중인 이메일입니다.");
        });
    }
}
```

**(중앙처리 후) 예외를 `GlobalExceptionHandler` 에서 한 번에 처리함**

```java
@ControllerAdvice
public class GlobalExceptionHandler{

		// 유저 관련 예외 처리 핸들러
    @ExceptionHandler(UserException.class)
    public String userExceptionHandler(UserException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/" + ex.getRedirectUrl();
    }
    // 잘못된 페이지 요청을 했을 때 예외 처리 핸들러
    @ExceptionHandler(PageException.class)
    public String pageExceptionHandler(PageException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/";
    }
}
public class UserController {

    @PostMapping("/signup")
    public String signup(@Valid UserForm userForm, BindingResult errors) {
				validateUserForm(userForm, errors);
        if (errors.hasErrors()) {
            return "page/user/signup";
        }
				// 순환 참조 방지를 위해 가입 전용 서비스 클래스에서 userService를 의존하여 가입
				userRegistrationService.registerUser(userForm);
				return "redirect:/page/user/login";
    }
}
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 저장
    public User saveUser(UserForm userForm) {
        User user = createUser(userForm);
        validateDuplicateUser(user);
        userRepository.save(user);
        return user;
    }
    // 아이디 혹은 이메일 중복 검사
    private void validateDuplicateUser(User user) {
        userRepository.findById(user.getId()).ifPresent(u -> {
            throw new UserException("이미 사용 중인 아이디입니다.");
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserException("이미 사용 중인 이메일입니다.");
        });
    }
}
```

### [상황설명]

1. 기존의 AnnieHands에서는 각 컨트롤러에서 예외를 개별적으로 처리하고 있었음.
2. 또한, 각 예외 상황에서 에러 코드와 메시지를 직접 정의하고 있었음.

### **[문제점 및 이슈]**

1. 각 컨트롤러에서 예외를 직접 처리하면서 소모되는 시간이 늘어나고, 많은 양의 예외 처리 로직이 많은 양 중복됨. 이는 유지보수의 어려움으로 이어질 것임.
2. 에러 코드와 메시지를 직접 정의하면서 발생하는 일관성 결여와 변경의 어려움, 그리고 오타와 같은 실수가 발생할 수 있음.

### **[원인분석]**

1. 중앙집중식 예외 처리를 하지 않고 각 컨트롤러마다 개별적으로 예외를 처리하고 있기 때문임.
2. 초기 구상 시 에러 코드와 메시지의 재사용성을 고려하지 않았음.

### **[해결 방안 및 결과]**

1. **`@ControllerAdvice`** 를 사용하여 중앙집중식 예외처리 방식을 도입, 모든 예외 처리 로직을 하나의 클래스로 집중시킴.
    1. 이는 예외 처리 코드의 중복을 제거하고 개발자는 기능 개발에 집중할 수 있기 때문에 생산성이 높아짐.
2. 커스텀 에러 코드와 메시지를 `**Enum`** 으로 통일하여 정의함.
    1. 이는 일관성 있는 예외 처리와 변경 및 관리가 훨씬 용이해짐.

### **[깨달은 점]**

1. **중복 최소화, 생산성 최대화** 중앙집중식 예외 처리 방식의 도입으로 각 컨트롤러에 흩어져 있던 예외 처리 로직의 중복을 제거할 수 있었고, 개발자는 기능 개발에 더 집중할 수 있게 되었다. 이는 전체적인 개발 생산성을 크게 향상시킬 수 있다.
2. **스마트한 에러 관리** `**Enum**`을 활용한 커스텀 에러 코드와 메시지 정의 방식은 에러 관리의 일관성을 보장하며, 에러 코드와 메시지의 변경 및 관리를 용이하게 만들 수 있다. 이를 통해 에러 처리에 대한 실수를 줄일 수 있고, 보다 유지 보수의 편의성을 향상 시킬 수 있다.
</details>

> **요약➡️AOP(관점 지향 프로그래밍)** 개념을 활용한 **중앙집중식 예외 처리 방식 도입**은 컨트롤러별 예외 처리 로직의 중복을 줄여 개발 생산성을 향상시켰고, **`Enum`을 사용한 에러 코드 및 메시지 관리**는 에러 처리의 일관성과 유지 보수의 편의성을 높일 수 있었다.
> 

### 3. **회원가입시 SMS 인증 번호를 세션 저장에서 Redis 도입 저장**
<details>
<summary>클릭하고 더 자세히 보기!</summary>
<aside>
	
🚨 [**해당 과정을 담은 블로그 포스팅**](https://marchcodig.tistory.com/299)

</aside>

### [상황설명]

1. 기존 웹 회원가입 시 필요한 휴대전화 SMS 인증 방식에서 발급된 인증 번호를 세션에 저장하고 있었음.
2. 인증 번호의의 유효시간은 세션의 타임아웃 기능으로 파괴할 수 있음.

### **[문제점 및 이슈]**

1. 특정 기능에 대한 개별적 타임아웃 설정이 어려움. 이는 다른 세션 기반의 비즈니스 로직이 존재할 때 해당 로직에 영향을 끼칠 수 있음.
2. 휘발성이 강한 데이터인 인증번호는 세션에 저장하기 적합하지 않다고 판단함.

### **[원인분석]**

1. 세션은 일반적으로 서버가 유지되는 지속적이고 중요한 데이터를 저장하기에 알맞음. 하지만 인증번호는 일시적으로 비지속적인 특징이 있음.

### **[해결 방안 및 결과]**

1. 인증 번호 관리를 위해 레디스를 도입.
    1. 데이터를 개별적으로 관리할 수 있음. 레디스는 각 key에 대하여 독립적으로 데이터를 관리할 수 있기 때문에 다른 데이터나 로직에 영향을 주지 않고 데이터에 대한 행동 제어가 가능함.
    2. 고속 처리에 유리함. 레디스는 key-value 방식을 사용하기 때문에 대규모 트랜잭션을 처리한다고 가정했을 때도 빠르게 작업 수행이 가능함.

### **[깨달은 점]**

1. **효율적인 데이터 관리 전략!** 세션 대신 레디스를 사용함으로써 데이터 관리의 유연성과 타임아웃 및 중복 문제를 사전에 예방할 수 있었다.
2. **현대적 기술 적용** 요구 사항과 기능의 특성을 고려하여 새로운 기술을 적용함으로써 기존의 문제를 해결하고, 보다 발전된 개발과 서비스를 제공할 수 있다.
</details>

> **요약**➡️ 세션 대신 **레디스**를 사용함으로써 데이터 관리를 더욱 **유연하게 관리**할 수 있었고, **타임아웃 및 중복 문제를 사전에 예방**하며 새로운 기술을 적극적으로 적용하여 개발과 서비스를 개선할 수 있었다.






