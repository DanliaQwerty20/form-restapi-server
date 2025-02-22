-- Создание таблицы студентов
-- Таблица для хранения информации о студентах
CREATE TABLE students (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Уникальный идентификатор студента
    first_name VARCHAR(100) NOT NULL, -- Имя студента
    last_name VARCHAR(100) NOT NULL, -- Фамилия студента
    email VARCHAR(150) NOT NULL, -- Электронная почта студента
    phone_number VARCHAR(150), -- Номер телефона студента
    faculty VARCHAR(150), -- Факультет студента
    course INTEGER CHECK (course > 0 AND course <= 6), -- Курс обучения (от 1 до 6)
    research_advisor_id UUID, -- Ссылка на научного руководителя
    created_at TIMESTAMP DEFAULT NOW() -- Дата и время создания записи
);

-- Создание таблицы научных руководителей
-- Таблица для хранения информации о научных руководителях
CREATE TABLE research_advisors (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Уникальный идентификатор научного руководителя
    first_name VARCHAR(300) NOT NULL, -- Имя научного руководителя
    last_name VARCHAR(300) NOT NULL, -- Фамилия научного руководителя
    academic_title VARCHAR(300), -- Академическое звание
    department VARCHAR(300), -- Кафедра
    university VARCHAR(300) NOT NULL, -- Университет
    email VARCHAR(300) NOT NULL, -- Электронная почта
    phone_number VARCHAR(300), -- Номер телефона
    created_at TIMESTAMP DEFAULT NOW() -- Дата и время создания записи
);

-- Создание таблицы заявок на участие
-- Таблица для хранения заявок студентов на конференции
CREATE TABLE surveys (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Уникальный идентификатор заявки
    student_id UUID NOT NULL REFERENCES students(id) ON DELETE CASCADE, -- Ссылка на студента
    topic_title VARCHAR(300) NOT NULL, -- Тема выступления
    topic_description TEXT, -- Описание темы выступления
    conference_date DATE, -- Дата конференции
    conference_room VARCHAR(300), -- Аудитория, где проводится конференция
    status VARCHAR(300) DEFAULT 'Черновик', -- Статус заявки
    research_advisor_feedback TEXT, -- Отзыв научного руководителя
    created_at TIMESTAMP DEFAULT NOW() -- Дата и время создания записи
);

-- Создание таблицы вопросов и ответов
-- Таблица для хранения вопросов и ответов по заявкам
CREATE TABLE questions (
    id SERIAL PRIMARY KEY, -- Уникальный идентификатор вопроса
    survey_id UUID NOT NULL REFERENCES surveys(id) ON DELETE CASCADE, -- Ссылка на заявку
    question_text TEXT NOT NULL, -- Текст вопроса
    answer TEXT -- Ответ на вопрос
);

-- Связь студентов с научными руководителями
ALTER TABLE students
ADD CONSTRAINT fk_research_advisor FOREIGN KEY (research_advisor_id) REFERENCES research_advisors(id) ON DELETE SET NULL;
