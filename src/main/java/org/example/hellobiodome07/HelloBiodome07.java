package org.example.hellobiodome07;

/**
 * DNA 염기서열을 입력받아 반복되는 뉴클레오타이드를 숫자로 압축해 출력하는 프로그램.
 */
public class HelloBiodome07
{
    /**
     * 문자가 5가지 뉴클레오타이드(C, Y, J, E, H) 또는 공백인지 확인한다.
     */
    private static boolean isInBase(char nucleotide)
    {
        return (
                nucleotide == 'C' || nucleotide == 'Y' || nucleotide == 'J' ||
                nucleotide == 'E' || nucleotide == 'H' || nucleotide == ' '
        );
    }

    /**
     * 입력 문자열을 대문자로 변환하고, 연속된 공백을 1개로 축소한다.
     */
    private static String normalize(String sourceDNA)
    {
        String  normalized = "";
        boolean prevIsSpace = false;

        for (int idx = 0; idx < sourceDNA.length(); idx++)
        {
            char ch = Character.toUpperCase(sourceDNA.charAt(idx));

            if (ch == ' ' && prevIsSpace)
                continue;
            normalized += ch;
            prevIsSpace = (ch == ' ');
        }
        return (normalized);
    }

    /**
     * 정규화된 염기서열을 반복 문자 수로 압축한 문자열을 반환한다.
     * 공백은 압축 대상에서 제외하고 그대로 유지한다.
     */
    private static String compressDNA(String sourceDNA)
    {
        String  compress = "-> ";
        int     countCurrentDNA = 0;
        char    currentDNA = '\0';
        int     idx = 0;

        for (; idx < sourceDNA.length() && isInBase(sourceDNA.charAt(idx)); idx++)
        {
            if (idx == 0)
            {
                currentDNA = sourceDNA.charAt(idx);
                countCurrentDNA++;
            }
            else if (currentDNA == sourceDNA.charAt(idx))
                countCurrentDNA++;
            else
            {
                compress += currentDNA;
                if (countCurrentDNA > 1)
                    compress += Integer.toString(countCurrentDNA);
                currentDNA = sourceDNA.charAt(idx);
                countCurrentDNA = 1;
            }
        }
        if (idx != sourceDNA.length())
        {
            System.out.println("-> 염기서열은 C, J, H, E, Y 다섯가지로만 입력됩니다. 확인하고 다시 입력해주세요");
            System.exit(1);
        }
        compress += currentDNA;
        if (countCurrentDNA > 1)
            compress += Integer.toString(countCurrentDNA);
        return (compress);
    }

    public static void main(String[] args)
    {
        String dna = normalize(String.join(" ", args));

        if (dna.isEmpty())
        {
            System.out.println("-> 염기서열이 입력되지 않았습니다. 다시 입력해주세요");
            System.exit(1);
        }
        System.out.println(compressDNA(dna));
    }
}
