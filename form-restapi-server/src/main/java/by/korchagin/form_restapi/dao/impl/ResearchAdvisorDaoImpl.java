package by.korchagin.form_restapi.dao.impl;

import by.korchagin.form_restapi.dao.ResearchAdvisorDao;
import by.korchagin.form_restapi.dto.ResearchAdvisorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class ResearchAdvisorDaoImpl implements ResearchAdvisorDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResearchAdvisorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public UUID createResearchAdvisor(ResearchAdvisorDTO advisorDTO) {
        UUID advisorId = UUID.randomUUID();
        String sql = "INSERT INTO research_advisors (id, first_name, last_name, academic_title, department, university, email, phone_number, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        int rowsInserted = jdbcTemplate.update(sql,
                advisorId,
                advisorDTO.firstName(),
                advisorDTO.lastName(),
                advisorDTO.academicTitle(),
                advisorDTO.department(),
                advisorDTO.university(),
                advisorDTO.email(),
                advisorDTO.phoneNumber()
        );

        if (rowsInserted == 0) {
            throw new RuntimeException("Research Advisor insertion failed");
        }

        return advisorId;
    }

    @Override
    public List<ResearchAdvisorDTO> getAllResearchAdvisors() {
        String sql = "SELECT id, first_name, last_name, academic_title, department, university, email, phone_number FROM research_advisors";
        return jdbcTemplate.query(sql, new ResearchAdvisorRowMapper());
    }

    private static class ResearchAdvisorRowMapper implements RowMapper<ResearchAdvisorDTO> {
        @Override
        public ResearchAdvisorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ResearchAdvisorDTO(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("academic_title"),
                    rs.getString("department"),
                    rs.getString("university"),
                    rs.getString("email"),
                    rs.getString("phone_number")
            );
        }
    }
}
