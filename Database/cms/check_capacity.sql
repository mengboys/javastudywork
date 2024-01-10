create definer = root@localhost trigger check_capacity
    before insert
    on enrollment
    for each row
BEGIN
    DECLARE current_capacity INT;
    DECLARE max_capacity_course INT;

    -- 获取当前课程已选择的学生人数
    SELECT COUNT(*)
    INTO current_capacity
    FROM enrollment
    WHERE course_id = NEW.course_id;

    -- 获取课程的最大容量
    SELECT max_capacity
    INTO max_capacity_course
    FROM course
    WHERE id = NEW.course_id;

    -- 检查是否超过课程最大容量
    IF current_capacity >= max_capacity_course THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Course capacity exceeded';
    END IF;
END;

