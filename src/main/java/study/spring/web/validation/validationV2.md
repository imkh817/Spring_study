# validationV2
*****
### 1️⃣ BindingResult
BindingResult는 검증오류를 보관하는 객체다. BindingResult에 검증오류를 보관하는 방법은 세 가지가 있다.

#### 1. @ModelAttribute 객체에 타입 오류등으로 바인딩이 실패하는 경우
- 사용자가 정수형 필드에 문자를 넣는 경우를 생각해보면 된다. 객체에 타입 오류 등으로 바인딩이 실패하면 스프링이 FieldError를 생성해서 BindingResult에 넣어준다. BindingResult가 있으면 쿼리 파라미터를 ModelAttribute 객체로 바인딩하는데에 실패해도 컨트롤러가 호출된다. BindingResult는 Model에 자동으로 포함되기 때문에 어떤 오류가 발생했는지 사용자에게 친절하게 알려줄 수 있다. BindingResult가 없으면 400 오류가 발생하면서 컨트롤러가 호출되지 않고 오류 페이지로 이동한다. 오류 페이지로 이동하면 사용자가 입력했던 값이 모두 날아간다. 이는 사용자 경험 측면에서 나쁘다.

#### 2. 개발자가 직접 넣어주는 경우
#### 3. validator를 사용하는 경우
*****
### 2️⃣ Field Error
- BindingResult에 보관되는 오류 객체다. FieldError 는 이름 그대로, 필드에 오류가 있는 경우 발생하는 에러다. 필드의 타입이 맞지 않을 때 스프링이 생성할 수도 있고, 개발자가 검증을 수행해서 필드에 오류가 있다면 직접 생성해서 BindingResult 의 addError() 메서드를 통해 넣을수 있다. 생성자는 2개가 있다.
```java
public FieldError(String objectName, String field, String defaultMessage);
public FieldError(String objectName, String field, @Nullable Object rejectedValue, boolean bindingFailure, @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage);
```
생성자의 매개변수 목록은 다음과 같다
 * objectName: 오류가 발생한 객체 이름
 *  field: 오류 필드
 *  rejectedValue: 사용자가 입력한 값(거절된 값)
 *  bindingFailure: 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
 *  codes: 메시지 코드
 *  arguments: 메시지에서 사용하는 인자
 *  defaultMessage: 기본 오류 메시지
*****
### 참고🤩
#### ❗️bindingResult로 넘긴 오류 이름과 필드명이 같아야 뷰 단에서 thymeleaf의 th:errors를 사용할 수 있다.
#### ❗️th:errors는 Thymeleaf에서 해당 필드에 대한 오류 메시지를 자동으로 표시하는 역할을 한다. 
#### ❗️이는 해당 필드에 오류가 있을 때에만 오류 메시지를 보여주도록 Thymeleaf가 처리하게 된다. 
#### ❗️따라서 th:text를 사용하여 직접 메시지를 지정할 필요 없이, th:errors만으로도 해당 필드의 오류 메시지를 표시할 수 있습니다.

*****
### 3️⃣ rejectValue()
그러나 FieldError 를 직접 생성하는 것은 너무 불편하다. 생성자의 매개변수가 너무 많다. BindingResult 는 자신이 검증해야할 객체 바로 다음에 오기 때문에 자신이 검증할 객체에대해 이미 알고있다. 그러므로, BindingResult는 FieldError 를 직접 생성하지 않고 필드 오류를 처리할 수 있도록 단순화 해주는 **rejectValue()** 메서드를 제공한다.

* field: 오류 필드명
* errorCode: 오류 코드 (메시지 코드가 아니다. 뒤에서 설명할 MessageResolver를 위한 오류 코드다)
* errorArgs: 오류 메시지의 {0}을 치환하기 위한 값
* defaultMessage: 오류 메시지를 찾을수 없을 때 사용하는 기본 메시지

```java
void rejectValue(@Nullable String field, String errorCode, String defaultMessage);
void rejectValue(@Nullable String field, String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage);
```