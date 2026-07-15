package org.example.hellobiodome04;

/**
 * 온도, 습도, 산소 농도를 Command Line Arguments로 입력받아
 * 생명나무의 환경 값이 안정 범위 내에 있는지 판단하는 프로그램.
 * <p>
 * 안정 범위: 온도 10°C 이상 27.5°C 미만, 습도 40% 초과 60% 미만, 산소 농도 19.5% 이상 23.5% 이하.
 * <p>
 * 보너스 기능: 세 값이 모두 안정 범위이면 건강지수 H를 계산하여 소수점 셋째 자리에서
 * 반올림하여(둘째 자리까지) 함께 출력한다. Math 패키지 사용이 금지되어 제곱근·반올림 모두 직접 구현한다.
 */
public class HelloBiodome04
{
    public static final double PI = 3.14;
    public static final double COEFFICIENT = 0.415;

    /**
     * 주어진 값의 절대값을 계산하여 반환한다.
     *
     * @param value 절대값을 구할 대상 값
     * @return value의 절대값
     */
    private static double calcAbsolute(double value)
    {
        if (value < 0) return (-1 * value);
        return (value);
    }

    /**
     * Newton-Raphson 방법으로 제곱근을 근사 계산하여 반환한다.
     * Math.sqrt() 사용이 금지되어 있어 직접 구현했다.
     *
     * @param value 제곱근을 구할 대상 값 (0 이상이어야 함)
     * @return value의 제곱근
     * @throws ArithmeticException value가 음수인 경우
     */
    private static double calcSquareRoot(double value)
    {
        if (value < 0)  throw new ArithmeticException("-> 음수의 제곱근은 계산할 수 없습니다.");
        else if (value == 0) return 0;

        double epsilon = 1e-15;
        double x = value;

        // Newton-Raphson method를 활용하여 제곱근 찾기
        while (calcAbsolute(x - value / x) > epsilon * x)
        {
            x = 0.5 * (x + value / x);
        }
        return (x);
    }

    /**
     * 건강지수 H를 계산하여 반환한다. (안정 범위 내의 값에서만 호출된다)
     *
     * @param temperature 온도
     * @param humidity 습도
     * @param oxygen 산소 농도
     * @return 계산된 건강지수 H
     */
    private static double calcHealthScale(double temperature, double humidity, double oxygen)
    {
        double H;
        try {
            H = (COEFFICIENT * calcAbsolute(calcSquareRoot(humidity) - temperature)) + (oxygen / (PI * PI));
        } catch (RuntimeException e) {
            System.out.println("-> 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.");
            System.exit(1);
            return (0);
        }
        return (H);
    }

    /**
     * 온도 값이 안정 범위(10°C 이상 27.5°C 미만)에 있는지 판단한다.
     *
     * @param temperature 온도
     * @return 안정 범위이면 true
     */
    private static boolean checkBoundaryOfTemperature(double temperature)
    {
        return (10 <= temperature && temperature < 27.5);
    }

    /**
     * 습도 값이 안정 범위(40% 초과 60% 미만)에 있는지 판단한다.
     *
     * @param humidity 습도
     * @return 안정 범위이면 true
     */
    private static boolean checkBoundaryOfHumidity(double humidity)
    {
        return (40 < humidity && humidity < 60);
    }

    /**
     * 산소 농도 값이 안정 범위(19.5% 이상 23.5% 이하)에 있는지 판단한다.
     *
     * @param oxygen 산소 농도
     * @return 안정 범위이면 true
     */
    private static boolean checkBoundaryOfOxygen(double oxygen)
    {
        return (19.5 <= oxygen && oxygen <= 23.5);
    }

    /**
     * 입력 문자열을 파싱하여 유효성 검사를 거친 double 값을 반환한다.
     * null·빈 문자열이거나 숫자가 아니면 안내 문구를 출력하고 프로그램을 종료한다.
     *
     * @param value 사용자로부터 전달받은 원시 입력 문자열
     * @return 파싱된 double 값
     */
    private static double parseValueInput(String value)
    {
        if (value == null || value.isBlank())
        {
            System.out.println("-> 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.");
            System.exit(1);
        }

        double parsed;
        try
        {
            parsed = Double.parseDouble(value);
        }
        catch (NumberFormatException e)
        {
            System.out.println("-> 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.");
            System.exit(1);
            return (0);
        }

        return (parsed);
    }

    /**
     * 프로그램 진입점. 3개의 Command Line Arguments(온도, 습도, 산소 농도 순)를
     * 입력받아 각 값이 안정 범위에 있는지 판단해 결과를 출력한다.
     * 모두 안정 범위이면 건강지수 H도 함께 출력한다.
     * 인자가 3개가 아니면 재입력 안내 문구를 출력하고 종료한다.
     *
     * @param args 사용자가 입력한 값 토큰 (온도, 습도, 산소 농도 순)
     */
    public static void main(String[] args)
    {
        double temperature;
        double humidity;
        double oxygen;

        if (args.length == 3)
        {
            temperature = parseValueInput(args[0]);
            humidity = parseValueInput(args[1]);
            oxygen = parseValueInput(args[2]);

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
        }
        else
        {
            System.out.println("-> 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.");
            System.exit(1);
        }
    }
}
