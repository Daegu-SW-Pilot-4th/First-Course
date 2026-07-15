package org.example.hellobiodome03;

/**
 * 온도, 습도, 산소 농도를 Command Line Arguments로 입력받아
 * 생명나무의 건강지수(H)를 계산하는 프로그램.
 * <p>
 * 공식: H = μB × |√Humidity − Temperature| + (Oxygen / π²)
 * <p>
 * 보너스 기능: 계산 결과를 소수점 셋째 자리에서 반올림하여(둘째 자리까지) 출력한다.
 * Math 패키지 사용이 금지되어 제곱근·반올림 모두 직접 구현한다.
 */
public class HelloBiodome03
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
        return ((value >= 0) ? value : (-1 * value));
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
        if (value < 0) throw new ArithmeticException("-> 음수의 제곱근은 계산할 수 없습니다.");
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
     * √습도와 온도의 차이에 대한 절대값을 계산하여 반환한다.
     *
     * @param sqrtHumidity 습도의 제곱근 값
     * @param temperature 온도 값
     * @return |sqrtHumidity - temperature|
     */
    private static double calcAbsoluteDifference(double sqrtHumidity, double temperature)
    {
        return calcAbsolute(sqrtHumidity - temperature);
    }

    /**
     * 건강지수 H를 계산하여 반환한다.
     * 계산 과정에서 예외(예: 습도가 음수여서 calcSquareRoot()가 던지는 예외)가
     * 발생하면 안내 문구를 출력하고 프로그램을 종료한다.
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
            H = (COEFFICIENT * calcAbsoluteDifference(calcSquareRoot(humidity), temperature)) + (oxygen / (PI * PI));
        } catch (RuntimeException e) {
            System.out.println("-> 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.");
            System.exit(1);
            return 0;
        }
        return (H);
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
            return 0;
        }

        return parsed;
    }

    /**
     * 프로그램 진입점. 3개의 Command Line Arguments(온도, 습도, 산소 농도 순)를
     * 입력받아 건강지수 H를 계산해 출력한다.
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

            System.out.printf("-> 생명지수 H = %.2f\n", calcHealthScale(temperature, humidity, oxygen));
        }
        else
        {
            System.out.println("-> 입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요.");
            System.exit(1);
        }
    }
}
