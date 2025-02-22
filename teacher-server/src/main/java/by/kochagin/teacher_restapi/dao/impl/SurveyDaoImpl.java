package by.kochagin.teacher_restapi.dao.impl;

import by.kochagin.teacher_restapi.dao.SurveyDao;
import by.kochagin.teacher_restapi.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class SurveyDaoImpl implements SurveyDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SurveyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SurveyResultDTO> getSurveysWithPagination(int offset, int pageSize) {
        String sql = """
                       SELECT
                s.id,
                s.topic_title,
                              st.first_name AS student_first_name, st.last_name AS student_last_name,
                              ra.first_name AS advisor_first_name, ra.last_name AS advisor_last_name,
                  s.conference_date,
                  s.status
                       FROM surveys s
                       JOIN students st ON s.student_id = st.id
                       JOIN research_advisors ra ON st.research_advisor_id = ra.id
                    LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new SurveyResultDTO(
                rs.getObject("id", UUID.class),
                rs.getString("topic_title"),
                rs.getString("student_first_name") + " " + rs.getString("student_last_name"),
                rs.getString("advisor_first_name") + " " + rs.getString("advisor_last_name"),
                rs.getObject("conference_date", LocalDate.class),
                rs.getString("status")

        ), pageSize, offset);
    }

    @Override
    public void updateSurvey(UUID applicationId, SurveyUpdateRequestDTO updateRequest) {
        // Обновление данных в таблице surveys
        String sqlSurvey = """
        UPDATE surveys
        SET topic_title = ?, topic_description = ?, conference_date = ?, status = ?, conference_room = ?
        WHERE id = ?
    """;
        jdbcTemplate.update(sqlSurvey,
                updateRequest.survey().topicTitle(),
                updateRequest.survey().topicDescription(),
                java.sql.Date.valueOf(updateRequest.survey().conferenceDate()),
                updateRequest.survey().status(),
                updateRequest.survey().conferenceRoom(), // Добавляем обновление кабинета
                applicationId
        );

        // Обновление данных в таблице students
        String sqlStudent = """
        UPDATE students
        SET first_name = ?, last_name = ?, email = ?, phone_number = ?, faculty = ?, course = ?, education_level = ?, education_form = ?
        WHERE id = ?
    """;
        jdbcTemplate.update(sqlStudent,
                updateRequest.student().firstName(),
                updateRequest.student().lastName(),
                updateRequest.student().email(),
                updateRequest.student().phoneNumber(),
                updateRequest.student().faculty(),
                updateRequest.student().course(),
                updateRequest.student().educationLevel(), // Уровень образования
                updateRequest.student().educationForm(), // Форма обучения
                updateRequest.student().id() // Используем корректный идентификатор студента
        );

        // Обновление данных в таблице research_advisors
        String sqlAdvisor = """
        UPDATE research_advisors
        SET first_name = ?, last_name = ?, academic_title = ?, department = ?, university = ?, email = ?, phone_number = ?
        WHERE id = ?
    """;
        jdbcTemplate.update(sqlAdvisor,
                updateRequest.researchAdvisor().firstName(),
                updateRequest.researchAdvisor().lastName(),
                updateRequest.researchAdvisor().academicTitle(),
                updateRequest.researchAdvisor().department(),
                updateRequest.researchAdvisor().university(),
                updateRequest.researchAdvisor().email(),
                updateRequest.researchAdvisor().phoneNumber(),
                updateRequest.researchAdvisor().id() // Используем корректный идентификатор научного руководителя
        );
    }

    @Override
    public void updateSurveyStatus(UUID applicationId, String status) {
        String sql = "UPDATE surveys SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, applicationId);
    }

    @Override
    public SurveyDetailDTO getApplicationById(UUID applicationId) {
        String sql = """
        SELECT
            s.id AS survey_id,
            s.topic_title,
            s.topic_description,
            s.conference_date,
            s.status,
            s.conference_room, -- Добавляем поле кабинета
            st.id AS student_id,
            st.first_name AS student_first_name,
            st.last_name AS student_last_name,
            st.email AS student_email,
            st.phone_number AS student_phone_number,
            st.education_level AS student_education_level,
            st.education_form AS student_education_form,
            st.faculty AS student_faculty,
            st.course AS student_course,
            st.research_advisor_id AS research_advisor_id,
            ra.id AS advisor_id,
            ra.first_name AS advisor_first_name,
            ra.last_name AS advisor_last_name,
            ra.academic_title AS advisor_academic_title,
            ra.department AS advisor_department,
            ra.university AS advisor_university,
            ra.email AS advisor_email,
            ra.phone_number AS advisor_phone_number
        FROM
            dip.surveys s
        JOIN
            dip.students st ON s.student_id = st.id
        JOIN
            dip.research_advisors ra ON st.research_advisor_id = ra.id
        WHERE
            s.id = ?
    """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new SurveyDetailDTO(
                UUID.fromString(rs.getString("survey_id")),
                new StudentDTO(
                        UUID.fromString(rs.getString("student_id")),
                        rs.getString("student_first_name"),
                        rs.getString("student_last_name"),
                        rs.getString("student_email"),
                        rs.getString("student_phone_number"),
                        rs.getString("student_faculty"),
                        rs.getInt("student_course"),
                        UUID.fromString(rs.getString("research_advisor_id")),
                        rs.getString("student_education_level"),
                        rs.getString("student_education_form")
                ),
                new ResearchAdvisorDTO(
                        UUID.fromString(rs.getString("advisor_id")),
                        rs.getString("advisor_first_name"),
                        rs.getString("advisor_last_name"),
                        rs.getString("advisor_academic_title"),
                        rs.getString("advisor_department"),
                        rs.getString("advisor_university"),
                        rs.getString("advisor_email"),
                        rs.getString("advisor_phone_number")
                ),
                new SurveyDTO(
                        UUID.fromString(rs.getString("survey_id")),
                        rs.getString("topic_title"),
                        rs.getString("topic_description"),
                        rs.getDate("conference_date") != null ? rs.getDate("conference_date").toLocalDate() : null,
                        rs.getString("status"),
                        rs.getString("conference_room")
                )
        ), applicationId);
    }

}