package by.korchagin.form_restapi.dao.impl;

import by.korchagin.form_restapi.dao.FileDao;
import by.korchagin.form_restapi.dto.FileData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

@Repository
public class FileDaoImpl implements FileDao {

    private final JdbcTemplate jdbcTemplate;

    public FileDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveFile(FileData fileData) {
        String sql = """
                INSERT INTO application_files (id, application_id, file_name, file_path, file_type)
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, UUID.randomUUID());
            ps.setObject(2, fileData.applicationId());
            ps.setString(3, fileData.fileName());
            ps.setString(4, fileData.filePath());
            ps.setString(5, fileData.fileType());
            return ps;
        });
    }

    @Override
    public List<FileData> getFilesByApplicationId(UUID applicationId) {
        String sql = """
                SELECT id, application_id, file_name, file_path, file_type
                FROM application_files
                WHERE application_id = ?
                """;

        return jdbcTemplate.query(sql, new Object[]{applicationId}, (rs, rowNum) -> new FileData((UUID) rs.getObject("application_id"), rs.getString("file_name"), rs.getString("file_path"), rs.getString("file_type")));
    }

    @Override
    public String getFileNameByPath(String filePath) {
        String sql = """
                SELECT file_name
                FROM application_files
                WHERE file_path = ?
                """;

        return jdbcTemplate.queryForObject(sql, new Object[]{filePath}, String.class);
    }
}