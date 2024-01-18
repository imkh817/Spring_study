# validationV2

### 1️⃣ BindingResult
BindingResult는 검증오류를 보관하는 객체다. BindingResult에 검증오류를 보관하는 방법은 세 가지가 있다.

#### 1. @ModelAttribute 객체에 타입 오류등으로 바인딩이 실패하는 경우
- 사용자가 정수형 필드에 문자를 넣는 경우를 생각해보면 된다. 객체에 타입 오류 등으로 바인딩이 실패하면 스프링이 FieldError를 생성해서 BindingResult에 넣어준다. BindingResult가 있으면 쿼리 파라미터를 ModelAttribute 객체로 바인딩하는데에 실패해도 컨트롤러가 호출된다. BindingResult는 Model에 자동으로 포함되기 때문에 어떤 오류가 발생했는지 사용자에게 친절하게 알려줄 수 있다. BindingResult가 없으면 400 오류가 발생하면서 컨트롤러가 호출되지 않고 오류 페이지로 이동한다. 오류 페이지로 이동하면 사용자가 입력했던 값이 모두 날아간다. 이는 사용자 경험 측면에서 나쁘다.

#### 2. 개발자가 직접 넣어주는 경우
#### 3. validator를 사용하는 경우

## 2️⃣ Field Error
- BindingResult에 보관되는 오류 객체다. FieldError 는 이름 그대로, 필드에 오류가 있는 경우 발생하는 에러다. 필드의 타입이 맞지 않을 때 스프링이 생성할 수도 있고, 개발자가 검증을 수행해서 필드에 오류가 있다면 직접 생성해서 BindingResult 의 addError() 메서드를 통해 넣을수 있다. 생성자는 2개가 있다.

1. public FieldError(String objectName, String field, String defaultMessage);