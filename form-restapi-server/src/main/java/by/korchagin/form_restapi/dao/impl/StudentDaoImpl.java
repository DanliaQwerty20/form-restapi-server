package by.korchagin.form_restapi.dao.impl;

import by.korchagin.form_restapi.dao.StudentDao;
import by.korchagin.form_restapi.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class StudentDaoImpl implements StudentDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Transactional
    public UUID createStudent(StudentDTO studentDTO, UUID advisorId) {
        UUID studentId = UUID.randomUUID();
        String sql = "INSERT INTO students (id, first_name, last_name, email, phone_number, faculty, course, education_level, education_form, research_advisor_id, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        int rowsInserted = jdbcTemplate.update(sql,
                studentId,
                studentDTO.firstName(),
                studentDTO.lastName(),
                studentDTO.email(),
                studentDTO.phoneNumber(),
                studentDTO.faculty(),
                studentDTO.course(),
                studentDTO.educationLevel(),
                studentDTO.educationForm(),
                advisorId
        );

        if (rowsInserted == 0) {
            throw new RuntimeException("Student insertion failed");
        }

        return studentId;
    }
}
