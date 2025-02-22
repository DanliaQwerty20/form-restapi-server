-- Добавление новых полей в таблицу students
ALTER TABLE students
ADD COLUMN education_level VARCHAR(50), -- Поле для уровня образования
ADD COLUMN education_form VARCHAR(50); -- Поле для формы обучения



