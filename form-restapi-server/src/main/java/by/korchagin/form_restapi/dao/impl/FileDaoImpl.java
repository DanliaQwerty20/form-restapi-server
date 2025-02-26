package by.korchagin.form_restapi.dao.impl;

import by.korchagin.form_restapi.dao.FileDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class FileDaoImpl implements FileDao {

    private final JdbcTemplate jdbcTemplate;

    public FileDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveFile(UUID applicationId, String fileName, String filePath, String fileType) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("application_files")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", UUID.randomUUID());
        parameters.put("application_id", applicationId);
        parameters.put("file_name", fileName);
        parameters.put("file_path", filePath);
        parameters.put("file_type", fileType);

        jdbcInsert.execute(parameters);
    }
}