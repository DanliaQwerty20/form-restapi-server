package by.korchagin.form_restapi.dao.impl;

import by.korchagin.form_restapi.dao.SurveyDao;
import by.korchagin.form_restapi.dto.SurveyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class SurveyDaoImpl implements SurveyDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SurveyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Transactional
    public UUID createSurvey(SurveyDTO surveyDTO, UUID studentId) {
        UUID surveyId = UUID.randomUUID();
        String sql = "INSERT INTO surveys (id, student_id, topic_title, topic_description, status, created_at) " +
                "VALUES (?, ?, ?, ?, 'Черновик', NOW())";

        int rowsInserted = jdbcTemplate.update(sql,
                surveyId,
                studentId,
                surveyDTO.topicTitle(),
                surveyDTO.topicDescription()
        );

        if (rowsInserted == 0) {
            throw new RuntimeException("Survey insertion failed");
        }

        return surveyId;
    }

    public List<SurveyDTO> getAllSurveys() {
        String sql = "SELECT id, student_id, topic_title, topic_description, conference_date, status FROM surveys";
        return jdbcTemplate.query(sql, this::mapRowToSurvey);
    }

    private SurveyDTO mapRowToSurvey(ResultSet rs, int rowNum) throws SQLException {
        return new SurveyDTO(
                rs.getString("topic_title"),
                rs.getString("topic_description"),
                rs.getDate("conference_date").toLocalDate()
        );
    }
}
