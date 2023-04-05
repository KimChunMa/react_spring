package ezenweb.example.Day03;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.parser.Entity;

public interface NoteEntityRepository
        extends JpaRepository <NoteEntity ,Integer >{

    }
