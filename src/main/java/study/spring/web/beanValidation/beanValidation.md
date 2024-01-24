# BeanValidation
*****
### 1️⃣ BeanValidation
검증 기능을 지금처럼 매번 코드로 작성하는 것은 상당히 번거롭다. 특히 특정 필드에 대한 검증 로직은 대부분 빈 값인
지 아닌지, 특정 크기를 넘는지 아닌지와 같이 매우 일반적인 로직이다.

```java
public class Item {
private Long id;
@NotBlank
private String itemName;
@NotNull
@Range(min = 1000, max = 1000000)
private Integer price;
@NotNull
@Max(9999)
private Integer quantity;
}
```
이런 검증 로직을 모든 프로젝트에 적용할 수 있게 공통화하고, 표준화 한 것이 바로 Bean Validation 이다. Bean Validation을 잘 활용하면, 애노테이션 하나로 검증 로직을 매우 편리하게 적용할 수 있다.
*****
### 2️⃣ BeanValidation의 한계
수정시 검증 요구사항<br>
데이터를 등록할 때와 수정할 때는 요구사항이 다를 수 있다.<br>
예를 들어 회원 등록을 할 시에는 회원번호가 필요 없지만, 회원 수정시에는 회원 번호가 필요하다.<br>
동일한 모델 객체를 등록할 때와 수정할 때 각각 다르게 검증하는 방법을 알아보자.<br><br>
**방법1) BeanValidation의 groups 기능을 사용한다.<br>**
**방법2) Item을 직접 사용하지 않고, ItemSaveForm, ItemUpdateForm 같은 폼 전송을 위한 별도의 모델 객체를 만들 어서 사용한다.<br>**

### 3️⃣ Form 전송 객체 분리
실무에서는 `groups` 를 잘 사용하지 않는데, 그 이유가 다른 곳에 있다. 바로 등록시 폼에서 전달하는 데이터가 `User` 도메인 객체와 딱 맞지 않기 때문이다.<br>
소위 "Hello World" 예제에서는 폼에서 전달하는 데이터와 `User` 도메인 객체가 딱 맞는다. 하지만 실무에서는 회원 등록시회원과관련된데이터만전달받는것이아니라,약관정보도추가로받는등 `User` 과관계없는수많은부가데 이터가 넘어온다.<br>
그래서 보통 `User` 을 직접 전달받는 것이 아니라, 복잡한 폼의 데이터를 컨트롤러까지 전달할 별도의 객체를 만들어서 전달한다. 예를 들면 `UserSaveForm` 이라는 폼을 전달받는 전용 객체를 만들어서 `@ModelAttribute` 로 사용한다. 이것을 통해 컨트롤러에서 폼 데이터를 전달 받고, 이후 컨트롤러에서 필요한 데이터를 사용해서 `User` 을 생성한다.<br>

**폼 데이터 전달에 User 도메인 객체 사용**
`HTML Form -> User -> Controller -> User -> Repository`
장점: User 도메인 객체를 컨트롤러, 리포지토리 까지 직접 전달해서 중간에 User을 만드는 과정이 없어서 간단하다.
단점: 간단한 경우에만 적용할 수 있다. 수정시 검증이 중복될 수 있고, groups를 사용해야 한다.
**폼 데이터 전달을 위한 별도의 객체 사용**
`HTML Form -> UserSaveForm -> Controller -> User 생성 -> Repository`
장점: 전송하는 폼 데이터가 복잡해도 거기에 맞춘 별도의 폼 객체를 사용해서 데이터를 전달 받을 수 있다. 보통 등록과, 수정용으로 별도의 폼 객체를 만들기 때문에 검증이 중복되지 않는다.
단점: 폼 데이터를 기반으로 컨트롤러에서 User 객체를 생성하는 변환 과정이 추가된다.