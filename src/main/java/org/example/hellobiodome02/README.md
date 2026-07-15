‼️ (문제 2) "태양의 따스한 햇살을 더해"
---

## 📢 개요
태양광, 풍력, 지열 에너지 생산량 3가지를 입력받아 총 에너지 사용량과 (보너스) 각 에너지원의 비율을 계산해 출력하는 프로그램이다.

## 💻 코드 평가(테스팅) 방법

---

### 📁 코드 구조 및 작업 과정 요약

#### 0. 전체 코드 구현 구조

```
HelloBiodome02.java
├── parseEnergyInput(String value)              ← 입력 파싱 및 유효성 검사 (static 메서드)
├── calcEnergyProduction(short, short, short)   ← 총 에너지 생산량 합산 (static 메서드)
└── main(String[] args)                         ← 진입점, 입력 수신 및 출력
```

#### 1. parseEnergyInput() — 입력 파싱 및 유효성 검사

```java
private static short parseEnergyInput(String value)
{
    if (value == null || value.isBlank()) { ... }    // ① 빈 입력 거부

    int parsed;
    try { parsed = Integer.parseInt(value); }        // ② 숫자 형식 검사
    catch (NumberFormatException e) { ... }

    if (parsed < 0 || parsed > 30000) { ... }        // ③ 범위 검사 (int 기준)
    return (short) parsed;                           // ④ 범위 통과 후 캐스팅
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 빈 입력 검사 | null·빈 문자열 거부 | 유효성 검사 |
| ② 숫자 형식 검사 | `Integer.parseInt()` + `NumberFormatException` 처리 | 구현 지침 |
| ③ 범위 검사 | 0 ~ 30,000 외 거부 — `int` 상태에서 검사 후 캐스팅 | 기능 요구사항 |
| ④ 형변환 | `int → short` 명시적 캐스팅 | 구현 지침 |

> **캐스팅 순서 주의:** `int`로 먼저 파싱하고 범위를 검사한 뒤 `(short)`로 캐스팅한다.
> 캐스팅을 먼저 하면 short 범위를 초과한 값(예: 65536 → 0)이 검사를 통과하는 오버플로우 버그가 발생한다.

#### 2. calcEnergyProduction() — 총 에너지 생산량 합산

```java
private static int calcEnergyProduction(short solarProduction, short windProduction, short geothermalProduction)
{
    return solarProduction + windProduction + geothermalProduction;
}
```

- `short` 세 개의 합산 시 Java 자동 형변환(numeric promotion)으로 `int`로 승격된다.
- 최대 합계는 90,000으로 `short` 범위(32,767)를 초과하므로 반환 타입을 `int`로 선언한다.

#### 3. main() — 입력 수신 및 출력

```java
if (args.length == 3)
{
    solarProduction      = parseEnergyInput(args[0]);
    windProduction       = parseEnergyInput(args[1]);
    geothermalProduction = parseEnergyInput(args[2]);
    totalEnergyProduction = calcEnergyProduction(solarProduction, windProduction, geothermalProduction);

    if (totalEnergyProduction == 0)                                    // ④ 0 나눗셈 방지
    {
        solarRatio = 0; windRatio = 0; geothermalRatio = 0;
    }
    else
    {
        solarRatio      = (double) solarProduction / totalEnergyProduction * 100;
        windRatio       = (double) windProduction / totalEnergyProduction * 100;
        geothermalRatio = (double) geothermalProduction / totalEnergyProduction * 100;
    }

    System.out.println("-> 총 에너지 사용량은 " + totalEnergyProduction + "입니다.");
    System.out.printf("태양광 %.9f%%, 풍력 %.8f%%, 지열 %.7f%%%n",
        new BigDecimal(solarRatio).setScale(9, RoundingMode.DOWN).doubleValue(),
        new BigDecimal(windRatio).setScale(8, RoundingMode.DOWN).doubleValue(),
        new BigDecimal(geothermalRatio).setScale(7, RoundingMode.DOWN).doubleValue());
}
```

| 단계 | 처리 내용 | 관련 요구사항 |
|---|---|---|
| ① 인자 수 검사 | `args.length == 3` 체크 | 기능 요구사항 |
| ② 파싱 | `parseEnergyInput()` 호출 | 구현 지침 |
| ③ 합산 | `calcEnergyProduction()` 호출 | 기능 요구사항 |
| ④ 0 나눗셈 방지 | 총합이 0이면 비율을 모두 0으로 처리 (버그 수정) | 예외 처리 보강 |
| ⑤ 비율 계산 | `short → double` 명시적 캐스팅 후 나눗셈 | 보너스 과제 |
| ⑥ 버림 출력 | `BigDecimal` + `RoundingMode.DOWN` | 보너스 과제 |

> **0 나눗셈 버그 수정 이력:** 세 값이 모두 0일 때 `0.0 / 0` 연산 결과가 `NaN`이 되어
> `new BigDecimal(NaN)`이 `NumberFormatException: Infinite or NaN`을 던지며 프로그램이
> 크래시하는 버그가 있었다. 총합이 0인 경우를 먼저 분기 처리해 비율을 0으로 고정했다.

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
javac -d out src\main\java\org\example\hellobiodome02\HelloBiodome02.java

# macOS / Linux
mkdir out
javac -d out src/main/java/org/example/hellobiodome02/HelloBiodome02.java
```

컴파일 성공 시 `out/org/example/hellobiodome02/` 경로에 `.class` 파일이 생성된다.

#### 3단계 — 프로그램 실행

```bash
# Windows / macOS / Linux 공통
java -cp out org.example.hellobiodome02.HelloBiodome02 <태양광> <풍력> <지열>
```

**실행 예시**

```bash
# 기본 입력
java -cp out org.example.hellobiodome02.HelloBiodome02 10000 20000 30000
# → 총 에너지 사용량은 60000입니다.
# → 태양광 16.666666666%, 풍력 33.33333333%, 지열 50.0000000%

# 최대값 입력
java -cp out org.example.hellobiodome02.HelloBiodome02 30000 30000 30000
# → 총 에너지 사용량은 90000입니다.
# → 태양광 33.333333333%, 풍력 33.33333333%, 지열 33.3333333%

# 모두 0 입력 (회귀 테스트 — 이전엔 NaN 크래시)
java -cp out org.example.hellobiodome02.HelloBiodome02 0 0 0
# → 총 에너지 사용량은 0입니다.
# → 태양광 0.000000000%, 풍력 0.00000000%, 지열 0.0000000%

# 인자 없이 실행
java -cp out org.example.hellobiodome02.HelloBiodome02
# → 태양광, 풍력, 지열 에너지 생산량을 정확히 입력했는지 확인하세요!!

# 숫자가 아닌 값 입력
java -cp out org.example.hellobiodome02.HelloBiodome02 abc 20000 30000
# → 숫자만 입력 가능합니다!
# → 현재 입력된 값: abc
```

## 🧑‍💻 문제 해결 과정에서 느낀 점
1. 실제 테스터가 어떤 입력값을 입력할지 모른다. → CLAUDE CODE를 활용하여 다양한 엣지 케이스를 생성하여 테스트 해보았고, 이 과정을 통해 스스로 작성한 코드의 에러율을 줄일 수 있었다.
2. 스스로 코드를 작성해보고, CLAUDE CODE를 통해 코드 검증 과정을 수행하는 것이 학습에 도움이 많이 되는 것 같았다. <br> 물론, 중간중간에 구글링과 CLAUDE CODE와의 대화를 통해 코드 개선 방향을 검색하기도 했지만, 그 속에서 내가 작성한 코드의 일관성과 보안성을 더욱 향상 시킬 수 있었다.
