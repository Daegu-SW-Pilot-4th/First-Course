‼️ (문제 1) "반갑습니다, 스프링와트입니다."
---

## 📢 개요
사용자 이름을 Command Line Arguments로 입력받아 맞춤 환영 인사를 출력하는 프로그램이다.

## 💻 코드 평가(테스팅) 방법

---

### 📁 코드 구조 및 작업 과정 요약

#### 0. 전체 코드 구현 구조

```
HelloBiodome01.java
├── buildGreeting(String input)   ← 인사말 생성 로직 (static 메서드)
└── main(String[] args)           ← 진입점, 입력 수신 및 출력
```

#### 1. main() — 입력 수신 및 출력

```java
public static void main(String[] args) 
{
    if (args.length > 0) 
    {
        String name = String.join(" ", args);  // 여러 인자를 공백으로 합쳐 전체 이름 복원
        buildGreeting(name);
    }
    else 
    {
        System.out.println("입력한 이름을 확인해주세요!");
        System.exit(1);
    }
}
```

- Command Line Arguments(`args[]`)로 이름을 받는다 — Scanner/BufferedReader 미사용
- `String.join(" ", args)`으로 띄어쓰기가 포함된 이름(`Alex Lee` 등)을 하나의 문자열로 복원한다
- 변수 `name`에 입력값을 저장한 뒤 `buildGreeting()`에 전달한다

#### 2. buildGreeting() — 인사말 생성 로직

```java
private static void buildGreeting(String input) 
{
    // 1. 보안: ANSI 이스케이프 시퀀스 제거 (터미널 출력 조작 방지)
    String sanitized = input.replaceAll("\033\\[[0-9;]*[A-Za-z]", "");
    // 2. 보안: 개행·null 바이트 등 제어 문자 제거 후 앞뒤 공백 제거
    sanitized = sanitized.replaceAll("[\\p{Cntrl}]", "").trim();

    // 3. 유효성 검사: 공백만 입력된 경우 포함
    if (sanitized.isEmpty())
    {
        System.out.println("입력한 이름을 확인해주세요!");
        System.exit(1);
    }

    // 4. 보너스: 10글자 초과 시 절단 — length()/substring()/반복문 미사용, 정규식만 사용
    String name = sanitized.replaceAll("^(.{1,10}).*$", "$1");
    // 5. 보너스: 이름을 따옴표로 감싸 출력
    System.out.println("\"" + name + "\"님, 스프링와트에 오신 걸 환영합니다!");
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① ANSI 제거 | `\033[...m` 패턴 제거 | 보안 (터미널 색상 조작 방지) |
| ② 제어문자 제거 | `\n`, `\r`, `\0` 등 제거 후 trim | 보안 (로그 인젝션 방지) |
| ③ 빈 이름 처리 | isEmpty() 체크 → 안내 문구 출력 후 종료 | 기능 요구사항 |
| ④ 10글자 절단 | 정규식 `^(.{1,10}).*$` | 보너스 과제 |
| ⑤ 따옴표 출력 | `"이름"님, ...` | 보너스 과제 |

> **보너스 제약 충족 근거:** `replaceAll("^(.{1,10}).*$", "$1")`은 정규식 캡처 그룹으로 첫 10글자를 추출한다.
> `String.length()`, `String.substring()`, 반복문(`for`/`while`) 을 사용하지 않아 보너스 제약을 모두 만족한다.

---

### 🚀 GitHub Clone 후 실행 방법

#### ✨ 사전 요구 사항

| 항목 | 작업 환경 | 최소 요구 | 비고 |
|---|---|---|---|
| JDK | Amazon Corretto 17.0.19 | 11 이상 | `java -version`으로 확인 |

```bash
# Java 버전 확인
java -version
# 출력 예시: openjdk version "17.0.19" 2025-04-15 LTS (Amazon Corretto)
```

#### 1단계 — 저장소 Clone 및 이동

```bash
git clone <repository-url>
cd java_first_course
```

#### 2단계 — 출력 디렉토리 생성 및 컴파일

```bash
# Windows (PowerShell / CMD)
mkdir out
javac -d out src\main\java\org\example\hellobiodome01\HelloBiodome01.java

# macOS / Linux
mkdir out
javac -d out src/main/java/org/example/hellobiodome01/HelloBiodome01.java
```

컴파일 성공 시 `out/org/example/hellobiodome01/` 경로에 `.class` 파일이 생성된다.

#### 3단계 — 프로그램 실행

```bash
# Windows / macOS / Linux 공통
java -cp out org.example.hellobiodome01.HelloBiodome01 <이름>
```

**실행 예시**

```bash
# 단일 이름
java -cp out org.example.hellobiodome01.HelloBiodome01 홍길동
# → "홍길동"님, 스프링와트에 오신 걸 환영합니다!

# 띄어쓰기 있는 이름
java -cp out org.example.hellobiodome01.HelloBiodome01 "Alex Lee"
# → "Alex Lee"님, 스프링와트에 오신 걸 환영합니다!

# 이름 없이 실행
java -cp out org.example.hellobiodome01.HelloBiodome01
# → 입력한 이름을 확인해주세요!

# 10글자 초과 이름 (보너스)
java -cp out org.example.hellobiodome01.HelloBiodome01 "아돌프 블레인 찰스 데이비드"
# → "아돌프 블레인 찰스"님, 스프링와트에 오신 걸 환영합니다!
```

## 🧑‍💻 문제 해결 과정에서 느낀 점
1. 실제 테스터가 어떤 입력값을 입력할지 모른다. → CLAUDE CODE를 활용하여 다양한 엣지 케이스를 생성 및 코드 보완
2. Length, Substring 함수 제외, For, While 반복문 사용 금지라는 조건이 있어 어떻게 구현하면 좋을지 막막했다. → 동료가 정규 표현식을 활용하여 문제를 해결했다는 부분에 영감을 받아 코드를 수정 <br>
[Java 정규 표현식 참고 사이트](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%A0%95%EA%B7%9C%EC%8B%9DRegular-Expression-%EC%82%AC%EC%9A%A9%EB%B2%95-%EC%A0%95%EB%A6%AC)
3. 문제를 확인하면 단순히 이름을 파싱하여 환영 문구를 출력하는 것이 과제지만, 그 속에 처리해야 할 구문이 수십가지이고, 팀 내 코드 컨벤션 규칙을 지키면서 코드를 작성해야 하니 문제 해결 과정이 연속이었다.
4. '내가 악성 사용자면 이 시스템을 어떻게 악용할까?'에 대해 생각을 해보았다. CLAUDE가 짜준 내용이 반이지만, 그 CLAUDE를 어떻게 잘 활용하여 코드의 사용성, 일관성, 보안성을 잡으면 좋을지 생각해 볼 수 있는 시간이었다.
