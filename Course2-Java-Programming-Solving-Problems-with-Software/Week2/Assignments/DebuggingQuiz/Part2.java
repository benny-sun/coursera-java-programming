public class Part2
{
    public Part2()
    {
    }

    public void findAbc(String input){
        int index = input.indexOf("abc");
        while (true){
            if (index == -1 || index >= input.length() - 3){
                break;
            }
            String found = input.substring(index+1, index+4);
            System.out.println(found);
            index = input.indexOf("abc",index+3);
        }
    }

    public void test(){
        String[] cases = new String[]{
                "abcdkfjsksioehgjfhsdjfhksdfhuwabcabcajfieowj", //bcd,bca,bca
                "kdabcabcjei", //bca,bcj
                "ttabcesoeiabco", //bce,bco
                "abcbabccabcd", //bcb,bcc,bcd
                "qwertyabcuioabcp",//bcu,bcp
                "abcabcabcabca"//bca, bca, bca, bca
            };

        for (String s: cases) {
            try {
                System.out.println("string:" + s);
                findAbc(s);
                System.out.println("------");
            } catch (Exception e) {
                System.out.println("string:" + s + ", error:" + e.getMessage());
            }
        }
    }

    public void printLength(String s) {
        System.out.println(s.length());
    }
}
