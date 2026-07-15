‼️ (문제 3) "당신의 생명지수는 얼마입니까"
---

## 📢 개요
온도, 습도, 산소 농도 값을 입력받아 생명나무의 건강지수(H)를 계산해 출력하는 프로그램이다.

## 💻 코드 평가(테스팅) 방법

---

### 📁 코드 구조 및 작업 과정 요약

#### 0. 전체 코드 구현 구조

```
HelloBiodome03.java
├── calcAbsolute(double)                              ← 절대값 계산 (static 메서드)
├── calcSquareRoot(double)                             ← 제곱근 계산, Newton-Raphson (static 메서드)
├── calcAbsoluteDifference(double, double)             ← √습도-온도 절대값 계산 (static 메서드)
├── calcHealthScale(double, double, double)            ← 건강지수 H 계산 (static 메서드)
├── parseValueInput(String)                            ← 입력 파싱 및 유효성 검사 (static 메서드)
└── main(String[] args)                                ← 진입점, 입력 수신 및 출력
```

#### 1. calcSquareRoot() — 제곱근 계산 (Newton-Raphson)

```java
private static double calcSquareRoot(double value)
{
    if (value < 0) throw new ArithmeticException("-> 음수의 제곱근은 계산할 수 없습니다.");   // ① 음수 입력 거부
    else if (value == 0) return 0;                                                        // ② 0 처리

    double epsilon = 1e-15;
    double x = value;
    while (calcAbsolute(x - value / x) > epsilon * x)                                     // ③ 뉴턴-랩슨 반복
    {
        x = 0.5 * (x + value / x);
    }
    return (x);
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 음수 검사 | 음수 입력 시 `ArithmeticException` 발생 | 예외 처리 보강 |
| ② 0 처리 | 0 입력 시 즉시 0 반환 | 경계값 처리 |
| ③ 근사 계산 | 뉴턴-랩슨 방법으로 `Math.sqrt()` 없이 제곱근 근사 | `Math` 패키지 사용 불가 제약 |

> **Math.sqrt() 대체 이유:** 제약사항에서 Java 기본 제공 Math 패키지 사용이 금지되어, `Math.sqrt()` 대신 뉴턴-랩슨(Newton-Raphson) 방법으로 제곱근을 직접 계산한다.
> **음수 예외 처리 이유:** 초기 구현은 `Math.sqrt()`의 동작(음수 입력 시 `NaN` 반환)을 그대로 벤치마킹했으나, 그 결과 습도가 음수일 때 오류 없이 `H = NaN`이 출력되는 문제가 있었다. 이를 방지하기 위해 음수 입력 시 예외를 던지도록 보강했다.

#### 2. calcAbsoluteDifference() — √습도와 온도의 절대값 차이 계산

```java
private static double calcAbsoluteDifference(double sqrtHumidity, double temperature)
{
    return calcAbsolute(sqrtHumidity - temperature);
}
```

- 요구사항의 "√습도와 온도를 입력 받아 절대값을 계산하고 결과를 반환하는 메서드"를 전용 메서드로 분리하여 구현했다.

#### 3. calcHealthScale() — 건강지수 H 계산

```java
private static double calcHealthScale(double temperature, double humidity, double oxygen)
{
    double H;
    try {
        H = (COEFFICIENT * calcAbsoluteDifference(calcSquareRoot(humidity), temperature)) + (oxygen / (PI * PI));
    } catch (RuntimeException e) {
        System.out.println("-> 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.");
        System.exit(1);
        return 0;
    }
    return (H);
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 공식 적용 | H = μB × &#124;√Humidity − Temperature&#124; + Oxygen/π² | 기능 요구사항 |
| ② 예외 처리 | 습도가 음수여서 `calcSquareRoot()`가 예외를 던지면 안내 문구 출력 후 종료 | 예외 처리 보강 |

#### 4. parseValueInput() — 입력 파싱 및 유효성 검사

```java
private static double parseValueInput(String value)
{
    if (value == null || value.isBlank()) { ... }     // ① 빈 입력 거부
    double parsed;
    try { parsed = Double.parseDouble(value); }        // ② 숫자 형식 검사
    catch (NumberFormatException e) { ... }
    return parsed;
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 빈 입력 검사 | null·빈 문자열 거부 | 유효성 검사 |
| ② 숫자 형식 검사 | `Double.parseDouble()` + `NumberFormatException` 처리 | 구현 지침 |

#### 5. main() — 입력 수신 및 출력

```java
if (args.length == 3)
{
    temperature = parseValueInput(args[0]);
    humidity = parseValueInput(args[1]);
    oxygen = parseValueInput(args[2]);
    System.out.printf("-> 생명지수 H = %.2f\n", calcHealthScale(temperature, humidity, oxygen));
}
else
{
    System.out.println("-> 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.");
    System.exit(1);
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 인자 수 검사 | `args.length == 3` 체크 | 기능 요구사항 |
| ② 파싱 | `parseValueInput()` 호출 | 구현 지침 |
| ③ 계산 및 출력 | `calcHealthScale()` 호출 후 소수점 둘째 자리까지 출력 | 보너스 과제 |

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
javac -d out src\main\java\org\example\hellobiodome03\HelloBiodome03.java

# macOS / Linux
mkdir out
javac -d out src/main/java/org/example/hellobiodome03/HelloBiodome03.java
```

컴파일 성공 시 `out/org/example/hellobiodome03/` 경로에 `.class` 파일이 생성된다.

#### 3단계 — 프로그램 실행

```bash
# Windows / macOS / Linux 공통
java -cp out org.example.hellobiodome03.HelloBiodome03 <온도> <습도> <산소농도>
```

**실행 예시**

```bash
# 기본 입력
java -cp out org.example.hellobiodome03.HelloBiodome03 25.5 65.0 21.0
# → 생명지수 H = 9.37

# 습도 0 (경계값)
java -cp out org.example.hellobiodome03.HelloBiodome03 25.5 0 21.0
# → 생명지수 H = 12.71

# 습도 음수 입력
java -cp out org.example.hellobiodome03.HelloBiodome03 25.5 -10.0 21.0
# → 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.

# 숫자가 아닌 값 입력
java -cp out org.example.hellobiodome03.HelloBiodome03 3.2 55.1 aa
# → 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.
```

## 🧑‍💻 문제 해결 과정에서 느낀 점
- (직접 작성해주세요)
