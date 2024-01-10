create definer = root@localhost trigger start_before_end
    before insert
    on course
    for each row
BEGIN
    IF NEW.start_time IS NOT NULL AND NEW.end_time IS NOT NULL THEN
        IF NEW.start_time >= NEW.end_time THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Start time must be before end time';
        END IF;
    END IF;
END;

