package org.example.hellobiodome02;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 태양광, 풍력, 지열 에너지 생산량을 Command Line Arguments로 입력받아
 * 총 에너지 사용량을 계산하는 프로그램.
 * <p>
 * 보너스 기능: 각 에너지원이 전체 생산량에서 차지하는 비율(%)을 계산하여 함께 출력한다.
 */
public class HelloBiodome02
{
    /**
     * 세 에너지 생산량의 총합을 계산하여 반환한다.
     * short 값 세 개를 더하면 Java 자동 형변환으로 int로 승격된다.
     *
     * @param solarProduction 태양광 에너지 생산량
     * @param windProduction 풍력 에너지 생산량
     * @param geothermalProduction 지열 에너지 생산량
     * @return 총 에너지 생산량
     */
    private static int calcEnergyProduction(short solarProduction, short windProduction, short geothermalProduction)
    {
        return solarProduction + windProduction + geothermalProduction;
    }

    /**
     * 입력 문자열을 파싱하여 유효성 검사를 거친 에너지 생산량 값을 반환한다.
     * null·빈 문자열, 숫자가 아닌 값, 0~30,000 범위를 벗어난 값은 안내 문구를
     * 출력하고 프로그램을 종료한다.
     *
     * @param value 사용자로부터 전달받은 원시 에너지 생산량 문자열
     * @return 0~30,000 범위로 검증된 에너지 생산량
     */
    private static short parseEnergyInput(String value)
    {
        if (value == null || value.isBlank())
        {
            System.out.println("에너지 생산량 입력값을 확인해주세요!");
            System.exit(1);
        }

        int parsed;
        try
        {
            parsed = Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            System.out.println("숫자만 입력 가능합니다!");
            System.out.println("현재 입력된 값: " + value);
            System.exit(1);
            return 0;
        }

        // int 상태에서 먼저 범위 검사 후 short로 캐스팅한다.
        // 캐스팅을 먼저 하면 short 범위를 초과한 값(예: 65536 → 0)이 검사를 통과하는 오버플로우 버그가 발생한다.
        if (parsed < 0 || parsed > 30000)
        {
            System.out.println("에너지 생산량의 범위는 0 ~ 30,000 사이여야 합니다!");
            System.out.println("현재 입력된 값: " + value);
            System.exit(1);
        }
        return (short) parsed;
    }

    /**
     * 프로그램 진입점. 3개의 Command Line Arguments(태양광, 풍력, 지열 순)로
     * 에너지 생산량을 입력받아 총합과 (보너스) 비율을 출력한다.
     * 인자가 3개가 아니면 재입력 안내 문구를 출력하고 종료한다.
     *
     * @param args 사용자가 입력한 에너지 생산량 토큰 (태양광, 풍력, 지열 순)
     */
    public static void main(String[] args)
    {
        short solarProduction;
        short windProduction;
        short geothermalProduction;
        int totalEnergyProduction;
        double solarRatio;
        double windRatio;
        double geothermalRatio;

        if (args.length == 3)
        {
            solarProduction = parseEnergyInput(args[0]);
            windProduction = parseEnergyInput(args[1]);
            geothermalProduction = parseEnergyInput(args[2]);
            totalEnergyProduction = calcEnergyProduction(solarProduction, windProduction, geothermalProduction);
            // 총합이 0이면 0으로 나누어 NaN이 발생하는 것을 방지한다 (BigDecimal은 NaN을 받으면 예외를 던진다).
            if (totalEnergyProduction == 0)
            {
                solarRatio = 0;
                windRatio = 0;
                geothermalRatio = 0;
            }
            else
            {
                solarRatio = (double) solarProduction / totalEnergyProduction * 100;
                windRatio = (double) windProduction / totalEnergyProduction * 100;
                geothermalRatio = (double) geothermalProduction / totalEnergyProduction * 100;
            }

            System.out.println("-> 총 에너지 사용량은 " + totalEnergyProduction + "입니다.");
            System.out.printf("태양광 %.9f%%, 풍력 %.8f%%, 지열 %.7f%%%n",
                new BigDecimal(solarRatio).setScale(9, RoundingMode.DOWN).doubleValue(),
                new BigDecimal(windRatio).setScale(8, RoundingMode.DOWN).doubleValue(),
                new BigDecimal(geothermalRatio).setScale(7, RoundingMode.DOWN).doubleValue());
        }
        else
        {
            System.out.println("태양광, 풍력, 지열 에너지 생산량을 정확히 입력했는지 확인하세요!!");
            System.exit(1);
        }
    }
}
