‼️ (문제 4) "모두의 생명나무를 위하여"
---

## 📢 개요
온도, 습도, 산소 농도 값을 입력받아 각 값이 안정 범위에 있는지 판단하고, 세 값이 모두 안정적이면 생명나무의 건강지수(H)까지 함께 출력하는 프로그램이다.

## 💻 코드 평가(테스팅) 방법

---

### 📁 코드 구조 및 작업 과정 요약

#### 0. 전체 코드 구현 구조

```
HelloBiodome04.java
├── calcAbsolute(double)                              ← 절대값 계산 (static 메서드)
├── calcSquareRoot(double)                             ← 제곱근 계산, Newton-Raphson (static 메서드)
├── calcHealthScale(double, double, double)            ← 건강지수 H 계산, 보너스 (static 메서드)
├── checkBoundaryOfTemperature(double)                 ← 온도 안정 범위 판단 (static 메서드)
├── checkBoundaryOfHumidity(double)                    ← 습도 안정 범위 판단 (static 메서드)
├── checkBoundaryOfOxygen(double)                      ← 산소 농도 안정 범위 판단 (static 메서드)
├── parseValueInput(String)                            ← 입력 파싱 및 유효성 검사 (static 메서드)
└── main(String[] args)                                ← 진입점, 범위 판단 및 결과 출력
```

#### 1. checkBoundaryOf*() — 각 항목의 안정 범위 판단

```java
private static boolean checkBoundaryOfTemperature(double temperature)
{
    return (10 <= temperature && temperature < 27.5);
}

private static boolean checkBoundaryOfHumidity(double humidity)
{
    return (40 < humidity && humidity < 60);
}

private static boolean checkBoundaryOfOxygen(double oxygen)
{
    return (19.5 <= oxygen && oxygen <= 23.5);
}
```

| 항목 | 안정 범위 | 경계 처리 |
|---|---|---|
| 온도 | 10°C 이상 27.5°C 미만 | 하한 포함(`<=`), 상한 제외(`<`) |
| 습도 | 40% 초과 60% 미만 | 양쪽 모두 제외(`<`, `>`) |
| 산소 농도 | 19.5% 이상 23.5% 이하 | 양쪽 모두 포함(`<=`) |

각 항목을 독립된 boolean 메서드로 분리해, 요구사항의 "조건문을 활용해 boolean 값을 반환하는 메서드" 지침을 그대로 따랐다.

#### 2. main() — 범위 판단 및 결과 출력

```java
if (checkBoundaryOfTemperature(temperature) && checkBoundaryOfHumidity(humidity) && checkBoundaryOfOxygen(oxygen))
{
    System.out.printf("-> 생명의 나무는 안정적인 상태입니다. ");
    System.out.printf("건강지수는 %.2f입니다.\n", calcHealthScale(temperature, humidity, oxygen));
}
else if (!checkBoundaryOfTemperature(temperature))
    System.out.println("-> 온도값이 정상 범위를 벗어났습니다. 확인이 필요합니다.");
else if (!checkBoundaryOfHumidity(humidity))
    System.out.println("-> 습도값이 정상 범위를 벗어났습니다. 확인이 필요합니다.");
else if (!checkBoundaryOfOxygen(oxygen))
    System.out.println("-> 산소농도값이 정상 범위를 벗어났습니다. 확인이 필요합니다.");
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 전체 안정 판단 | 세 판단 메서드를 `&&`로 결합해 모두 안정 범위인지 확인 | 기능 요구사항 |
| ② 안정 시 출력 | 안정 문구 + 보너스 건강지수 H 출력 | 보너스 과제 |
| ③ 이탈 항목 안내 | 세 판단 메서드를 각각 재호출해 어떤 항목이 벗어났는지 안내 | "정상 범위를 벗어나는 요소가 무엇인지 안내" |

> **한번의 입력에서 이탈 요소는 최대 1개라는 전제:** 요구사항에서 온도와 습도가 동시에 벗어날 수 없다고 명시하고 있어, `if-else if` 체인으로 처음 발견되는 이탈 항목 하나만 안내하도록 구현했다.

#### 3. calcHealthScale() / calcSquareRoot() — 보너스 건강지수 계산

문제3(`hellobiodome03`)에서 검증된 뉴턴-랩슨 제곱근 구현과 건강지수 계산 로직을 재사용했다. 안정 범위 판단을 통과한 경우에만 호출되므로 습도는 항상 양수이며, 계산 실패 예외 처리는 방어적으로만 남겨두었다.

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
| ② 숫자 형식 검사 | `Double.parseDouble()` + `NumberFormatException` 처리 | 문자 입력 예외 처리 |

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
javac -d out src\main\java\org\example\hellobiodome04\HelloBiodome04.java

# macOS / Linux
mkdir out
javac -d out src/main/java/org/example/hellobiodome04/HelloBiodome04.java
```

컴파일 성공 시 `out/org/example/hellobiodome04/` 경로에 `.class` 파일이 생성된다.

#### 3단계 — 프로그램 실행

```bash
# Windows / macOS / Linux 공통
java -cp out org.example.hellobiodome04.HelloBiodome04 <온도> <습도> <산소농도>
```

**실행 예시**

```bash
# 기본 안정 입력
java -cp out org.example.hellobiodome04.HelloBiodome04 25.5 55.0 21.0
# → 생명의 나무는 안정적인 상태입니다. 건강지수는 9.63입니다.

# 온도만 정상 범위 이탈
java -cp out org.example.hellobiodome04.HelloBiodome04 -27 55 20
# → 온도값이 정상 범위를 벗어났습니다. 확인이 필요합니다.

# 숫자가 아닌 값 입력
java -cp out org.example.hellobiodome04.HelloBiodome04 3.2 55.1 aa
# → 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.
```

> ⚠️ `Requirements.md`의 "결과 예시" 중 `25.5 65.0 21.0`은 문서상 안정 상태로 안내되어 있으나, 습도 안정 범위(40% 초과 60% 미만) 규정과 실제로는 모순된다(65.0은 범위 밖). 자세한 내용과 재현 방법은 `TerminalTest.md`의 TC-11을 참고한다.

더 다양한 테스트 케이스(경계값 포함)는 `TerminalTest.md`에서 확인할 수 있다.

## 🧑‍💻 문제 해결 과정에서 느낀 점
- (직접 작성해주세요)
