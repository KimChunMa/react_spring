package ezenweb.example.day01;

public class Ex2 {
    public static void main(String[] args) {
        LombokDto dto = new LombokDto().builder()
                .mno(2).mid("qwe").mpassword("qwe").mpoint(200).phone("010-3333-3333")
                .build();
        System.out.println("dto : "+dto.toString());

        Dao dao = new Dao();

        boolean result = dao.setmember(dto);
        System.out.println(result);



    }
}
