-- Создание таблицы для хранения информации о файлах
CREATE TABLE application_files (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Уникальный идентификатор файла
    application_id UUID NOT NULL, -- Идентификатор заявки
    file_name VARCHAR(300) NOT NULL, -- Имя файла
    file_path VARCHAR(300) NOT NULL, -- Путь к файлу в MinIO
    file_type VARCHAR(50) NOT NULL, -- Тип файла (например, "signed" или "additional")
    uploaded_at TIMESTAMP DEFAULT NOW(), -- Дата и время загрузки
    FOREIGN KEY (application_id) REFERENCES surveys(id) ON DELETE CASCADE
);