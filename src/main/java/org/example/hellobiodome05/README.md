‼️ (문제 5) "리즈키의 커피 그리고 오래된 돌"
---

## 📢 개요
돌에 새겨진 두 개의 수식을 모두 만족하는 g, h 값을 4bit 범위에서 찾아, 세번째 수식의 결과를 계산해 출력하는 프로그램이다.

## 💻 코드 평가(테스팅) 방법

---

### 📁 코드 구조 및 작업 과정 요약

#### 0. 전체 코드 구현 구조

```
HelloBiodome05.java
├── checkFirstExpression(int, int)                    ← 첫번째 수식 만족 여부 판단 (static 메서드)
├── checkSecondExpression(int, int)                    ← 두번째 수식 만족 여부 판단 (static 메서드)
├── calcThirdExpression(int, int)                      ← 세번째 수식 결과 계산 (static 메서드)
├── calcThirdExpressionBonus(int, int)                 ← 보너스, 다른 연산자로 동일 결과 계산 (static 메서드)
└── main(String[] args)                                ← 진입점, g·h 탐색 및 결과 출력
```

#### 1. checkFirstExpression() / checkSecondExpression() — 수식 판별

```java
private static boolean checkFirstExpression(int g, int h)
{
    return ((g & 1 >> g << 2 | h + g ^ h) == 1);
}

private static boolean checkSecondExpression(int g, int h)
{
    return ((g % 2 << h >> g | 1 & 0 ^ 0) == 2);
}
```

Java 연산자 우선순위(산술 > 시프트 > 관계 > 동등 > `&` > `^` > `|`)를 그대로 적용하면, 두 수식은 다음과 같이 괄호로 묶인다.

| 수식 | 우선순위대로 괄호를 채운 형태 |
|---|---|
| 수식 1 | `(g & ((1 >> g) << 2)) \| ((h + g) ^ h)` |
| 수식 2 | `((g % 2) << h >> g) \| ((1 & 0) ^ 0)` |

#### 2. main() — g, h 탐색 및 결과 출력

```java
for (int g = 0; g < (1 << 4); g++)
{
    for (int h = 0; h < (1 << 4); h++)
    {
        if (checkFirstExpression(g, h) && checkSecondExpression(g, h))
        {
            System.out.println(calcThirdExpression(g, h));
            System.out.println(calcThirdExpressionBonus(g, h));
        }
    }
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 탐색 범위 | "최대 데이터의 크기가 4 bit"라는 힌트에 따라 g, h를 각각 `0 ~ 15`(`1 << 4`) 범위에서 전수 탐색 | 구현 지침 |
| ② 두 수식 동시 만족 | `checkFirstExpression()`과 `checkSecondExpression()`을 모두 만족하는 조합만 채택 | 기능 요구사항 |
| ③ 결과 출력 | 세번째 수식 결과와 보너스 수식 결과를 함께 출력 | 기능 요구사항 + 보너스 과제 |

> **탐색 결과:** 0~15 범위를 전수 탐색하면 `g = 1, h = 2` 조합만 두 수식을 모두 만족하며, 세번째 수식 `(h*h+g)*(h<<h)+(g<<g)`의 결과는 `42`이다.

#### 3. calcThirdExpressionBonus() — 보너스, 다른 연산자로 동일 결과 계산

```java
private static int calcThirdExpressionBonus(int g, int h)
{
    return (g == 1 && h == 2) ? (h * h + g) * (h * h * h) + (g + g) : -1;
}
```

기존 세번째 수식이 시프트 연산자(`<<`)를 사용한 것과 달리, `g`, `h`, 상수 `1`·`2`만으로 비교(`==`)·논리(`&&`)·삼항(`?:`)·산술(`*`, `+`)·단항(`-1`)·대입(`=`) 연산자를 조합해 동일한 결과(`42`)를 나타내도록 구현했다. `h << h`는 `h * h * h`(h=2일 때 8)로, `g << g`는 `g + g`(g=1일 때 2)로 각각 대체했다.

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
javac -d out src\main\java\org\example\hellobiodome05\HelloBiodome05.java

# macOS / Linux
mkdir out
javac -d out src/main/java/org/example/hellobiodome05/HelloBiodome05.java
```

컴파일 성공 시 `out/org/example/hellobiodome05/` 경로에 `.class` 파일이 생성된다.

#### 3단계 — 프로그램 실행

이 프로그램은 Command Line Arguments를 사용하지 않는다. (g, h 값을 프로그램 내부에서 직접 탐색한다)

```bash
# Windows / macOS / Linux 공통
java -cp out org.example.hellobiodome05.HelloBiodome05
```

**실행 예시**

```bash
java -cp out org.example.hellobiodome05.HelloBiodome05
# → 42
# → 42
```

더 다양한 테스트 케이스는 `TerminalTest.md`에서 확인할 수 있다.

## 🧑‍💻 문제 해결 과정에서 느낀 점
- (직접 작성해주세요)
