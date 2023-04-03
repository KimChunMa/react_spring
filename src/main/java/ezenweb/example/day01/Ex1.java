    package ezenweb.example.day01;

    public class Ex1 {
        public static void main(String[] args) {
            //일반 dto
            Dto dto =new Dto(1,"qwe","qwe",100,"010-4444-4444");

            //롬북 dto (빈생성자)
            LombokDto lombokDto = new LombokDto();

            //롬북 dto (전체생성자)
            LombokDto dto2 =new LombokDto(1,"qwe","qwe",100,"010-4444-4444");


            System.out.println("get : "+ dto2.getMid());
            dto2.setMid("asd");

            //tostring
            System.out.println(dto2.toString());

            LombokDto dto3 = LombokDto.builder().mno(1).mid("qwe").mpassword("qwe")
                    .mpoint(100).phone("010-").build();

            System.out.println(dto3.toString());
        }
    }
