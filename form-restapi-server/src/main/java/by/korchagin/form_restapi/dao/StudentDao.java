package by.korchagin.form_restapi.dao;

import by.korchagin.form_restapi.dto.StudentDTO;

import java.util.UUID;

public interface StudentDao {
    UUID createStudent(StudentDTO student, UUID advisorId);
}
