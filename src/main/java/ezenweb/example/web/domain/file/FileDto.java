package ezenweb.example.web.domain.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder
public class FileDto {
    private String originalFilename; //실제 순수 파일명
    private String uuidFile; //식별된 파일명
    private String sizeKb; // 용량

}
