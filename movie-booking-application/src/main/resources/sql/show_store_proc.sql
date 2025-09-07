DELIMITER $$

CREATE PROCEDURE AddMultipleMovieShows(
    IN p_movie_id INTEGER,
    IN p_theater_id INTEGER,  -- Pass specific theater_id or -1 for all theaters
    IN p_days_to_schedule INTEGER,  -- Number of days to schedule (default 6)
    IN p_shows_per_day INTEGER  -- Number of shows per day per theater
)
BEGIN
    DECLARE v_current_date DATE;
    DECLARE v_end_date DATE;
    DECLARE v_date_counter DATE;
    DECLARE v_theater_counter INTEGER;
    DECLARE v_show_counter INTEGER;
    DECLARE v_time_slot TIME;
    DECLARE v_time_increment INTEGER;
    DECLARE v_base_hour INTEGER;
    DECLARE v_done INTEGER DEFAULT FALSE;
    DECLARE v_theater_id INTEGER;
    DECLARE v_total_shows_added INTEGER DEFAULT 0;

    -- Cursor for theaters
    DECLARE theater_cursor CURSOR FOR
        SELECT id FROM THEATER
        WHERE (p_theater_id = -1 OR id = p_theater_id);

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = TRUE;

    -- Validate inputs
    IF p_movie_id IS NULL OR p_movie_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid movie_id provided';
    END IF;

    -- Check if movie exists
    IF NOT EXISTS (SELECT 1 FROM MOVIE WHERE id = p_movie_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Movie not found';
    END IF;

    -- Set default values if needed
    IF p_days_to_schedule IS NULL OR p_days_to_schedule <= 0 THEN
        SET p_days_to_schedule = 6;
    END IF;

    IF p_shows_per_day IS NULL OR p_shows_per_day <= 0 THEN
        SET p_shows_per_day = 4;  -- Default 4 shows per day
    END IF;

    -- Initialize dates
    SET v_current_date = CURDATE();
    SET v_end_date = DATE_ADD(v_current_date, INTERVAL p_days_to_schedule DAY);

    -- Start transaction
    START TRANSACTION;

    BEGIN
        -- Loop through each date
        SET v_date_counter = v_current_date;

        WHILE v_date_counter <= v_end_date DO

            -- Open cursor for theaters
            OPEN theater_cursor;
            SET v_done = FALSE;

            theater_loop: LOOP
                FETCH theater_cursor INTO v_theater_id;

                IF v_done THEN
                    LEAVE theater_loop;
                END IF;

                -- Calculate time slots for shows
                -- Distribute shows evenly between 10:00 and 23:00 (13 hours = 780 minutes)
                SET v_time_increment = FLOOR(780 / p_shows_per_day);

                -- Add shows for this theater on this date
                SET v_show_counter = 0;

                WHILE v_show_counter < p_shows_per_day DO
                    -- Calculate show time
                    -- Start at 10:00 and add increments
                    SET v_base_hour = 10 * 60;  -- 10:00 in minutes
                    SET v_time_slot = SEC_TO_TIME((v_base_hour + (v_show_counter * v_time_increment)) * 60);

                    -- Ensure we don't exceed 23:00
                    IF v_time_slot <= '23:00:00' THEN
                        -- Check if show already exists
                        IF NOT EXISTS (
                            SELECT 1 FROM `SHOW`
                            WHERE date = v_date_counter
                            AND time = v_time_slot
                            AND movie_id = p_movie_id
                            AND theater_id = v_theater_id
                        ) THEN
                            -- Insert the show
                            INSERT INTO `SHOW` (date, time, movie_id, theater_id)
                            VALUES (v_date_counter, v_time_slot, p_movie_id, v_theater_id);

                            SET v_total_shows_added = v_total_shows_added + 1;
                        END IF;
                    END IF;

                    SET v_show_counter = v_show_counter + 1;
                END WHILE;

            END LOOP theater_loop;

            CLOSE theater_cursor;

            -- Move to next date
            SET v_date_counter = DATE_ADD(v_date_counter, INTERVAL 1 DAY);

        END WHILE;

        -- Commit transaction
        COMMIT;

        -- Return summary
        SELECT
            v_total_shows_added AS shows_added,
            p_movie_id AS movie_id,
            CASE
                WHEN p_theater_id = -1 THEN 'All Theaters'
                ELSE CONCAT('Theater ID: ', p_theater_id)
            END AS theater_scope,
            v_current_date AS start_date,
            v_end_date AS end_date,
            p_shows_per_day AS shows_per_day_per_theater;

    END;

EXCEPTION
    WHEN SQLEXCEPTION THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error occurred while adding shows';
END$$

DELIMITER ;


-- =====================================================
-- ENHANCED VERSION WITH CUSTOM TIME SLOTS
-- =====================================================

DELIMITER $$

CREATE PROCEDURE AddMultipleMovieShowsWithTimeSlots(
    IN p_movie_id INTEGER,
    IN p_theater_id INTEGER,  -- Pass specific theater_id or -1 for all theaters
    IN p_days_to_schedule INTEGER,  -- Number of days to schedule (default 6)
    IN p_time_slots VARCHAR(500)  -- Comma-separated time slots e.g., '10:00,14:00,18:00,22:00'
)
BEGIN
    DECLARE v_current_date DATE;
    DECLARE v_end_date DATE;
    DECLARE v_date_counter DATE;
    DECLARE v_done INTEGER DEFAULT FALSE;
    DECLARE v_theater_id INTEGER;
    DECLARE v_time_slot TIME;
    DECLARE v_time_string VARCHAR(50);
    DECLARE v_pos INTEGER DEFAULT 1;
    DECLARE v_total_shows_added INTEGER DEFAULT 0;
    DECLARE v_time_slots_temp VARCHAR(500);

    -- Cursor for theaters
    DECLARE theater_cursor CURSOR FOR
        SELECT id FROM THEATER
        WHERE (p_theater_id = -1 OR id = p_theater_id);

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = TRUE;

    -- Validate movie
    IF NOT EXISTS (SELECT 1 FROM MOVIE WHERE id = p_movie_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Movie not found';
    END IF;

    -- Set defaults
    IF p_days_to_schedule IS NULL OR p_days_to_schedule <= 0 THEN
        SET p_days_to_schedule = 6;
    END IF;

    IF p_time_slots IS NULL OR p_time_slots = '' THEN
        SET p_time_slots = '10:00,14:00,18:00,22:00';  -- Default time slots
    END IF;

    -- Initialize dates
    SET v_current_date = CURDATE();
    SET v_end_date = DATE_ADD(v_current_date, INTERVAL p_days_to_schedule DAY);

    -- Start transaction
    START TRANSACTION;

    -- Loop through each date
    SET v_date_counter = v_current_date;

    WHILE v_date_counter <= v_end_date DO

        -- Open cursor for theaters
        OPEN theater_cursor;
        SET v_done = FALSE;

        theater_loop: LOOP
            FETCH theater_cursor INTO v_theater_id;

            IF v_done THEN
                LEAVE theater_loop;
            END IF;

            -- Parse and process time slots
            SET v_time_slots_temp = CONCAT(p_time_slots, ',');

            WHILE LENGTH(v_time_slots_temp) > 0 DO
                SET v_pos = LOCATE(',', v_time_slots_temp);

                IF v_pos > 0 THEN
                    SET v_time_string = TRIM(SUBSTRING(v_time_slots_temp, 1, v_pos - 1));
                    SET v_time_slots_temp = SUBSTRING(v_time_slots_temp, v_pos + 1);

                    -- Convert string to time
                    SET v_time_slot = STR_TO_DATE(v_time_string, '%H:%i');

                    -- Validate time is between 10:00 and 23:00
                    IF v_time_slot >= '10:00:00' AND v_time_slot <= '23:00:00' THEN
                        -- Check if show already exists
                        IF NOT EXISTS (
                            SELECT 1 FROM `SHOW`
                            WHERE date = v_date_counter
                            AND time = v_time_slot
                            AND movie_id = p_movie_id
                            AND theater_id = v_theater_id
                        ) THEN
                            -- Insert the show
                            INSERT INTO `SHOW` (date, time, movie_id, theater_id)
                            VALUES (v_date_counter, v_time_slot, p_movie_id, v_theater_id);

                            SET v_total_shows_added = v_total_shows_added + 1;
                        END IF;
                    END IF;
                END IF;
            END WHILE;

        END LOOP theater_loop;

        CLOSE theater_cursor;

        -- Move to next date
        SET v_date_counter = DATE_ADD(v_date_counter, INTERVAL 1 DAY);

    END WHILE;

    -- Commit transaction
    COMMIT;

    -- Return summary
    SELECT
        v_total_shows_added AS shows_added,
        p_movie_id AS movie_id,
        CASE
            WHEN p_theater_id = -1 THEN 'All Theaters'
            ELSE CONCAT('Theater ID: ', p_theater_id)
        END AS theater_scope,
        v_current_date AS start_date,
        v_end_date AS end_date,
        p_time_slots AS time_slots_used;

EXCEPTION
    WHEN SQLEXCEPTION THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error occurred while adding shows';
END$$

DELIMITER ;


-- =====================================================
-- USAGE EXAMPLES
-- =====================================================

-- Example 1: Add shows for movie ID 1 in all theaters for 6 days with 4 shows per day
CALL AddMultipleMovieShows(1, -1, 6, 4);

-- Example 2: Add shows for movie ID 2 in theater ID 3 for 7 days with 3 shows per day
CALL AddMultipleMovieShows(2, 3, 7, 3);

-- Example 3: Add shows with specific time slots for movie ID 1 in all theaters
CALL AddMultipleMovieShowsWithTimeSlots(1, -1, 6, '10:00,13:30,17:00,20:30');

-- Example 4: Add shows with custom times for a specific theater
CALL AddMultipleMovieShowsWithTimeSlots(1, 5, 6, '11:00,14:00,18:00,21:00,22:30');


-- =====================================================
-- HELPER PROCEDURE TO VIEW SCHEDULED SHOWS
-- =====================================================

DELIMITER $$

CREATE PROCEDURE ViewScheduledShows(
    IN p_movie_id INTEGER,
    IN p_date DATE
)
BEGIN
    SELECT
        s.show_id,
        s.date,
        s.time,
        m.movie_name,
        t.name AS theater_name,
        t.location AS theater_location
    FROM `SHOW` s
    JOIN MOVIE m ON s.movie_id = m.id
    JOIN THEATER t ON s.theater_id = t.id
    WHERE (p_movie_id IS NULL OR s.movie_id = p_movie_id)
    AND (p_date IS NULL OR s.date = p_date)
    ORDER BY s.date, t.id, s.time;
END$$

DELIMITER ;


-- =====================================================
-- PROCEDURE TO DELETE SHOWS (for cleanup/testing)
-- =====================================================

DELIMITER $$

CREATE PROCEDURE DeleteMovieShows(
    IN p_movie_id INTEGER,
    IN p_theater_id INTEGER,  -- -1 for all theaters
    IN p_start_date DATE,
    IN p_end_date DATE
)
BEGIN
    DECLARE v_deleted_count INTEGER;

    -- Start transaction
    START TRANSACTION;

    -- Delete shows based on criteria
    DELETE FROM `SHOW`
    WHERE movie_id = p_movie_id
    AND (p_theater_id = -1 OR theater_id = p_theater_id)
    AND date BETWEEN p_start_date AND p_end_date;

    SET v_deleted_count = ROW_COUNT();

    -- Commit transaction
    COMMIT;

    -- Return result
    SELECT
        v_deleted_count AS shows_deleted,
        p_movie_id AS movie_id,
        CASE
            WHEN p_theater_id = -1 THEN 'All Theaters'
            ELSE CONCAT('Theater ID: ', p_theater_id)
        END AS theater_scope,
        p_start_date AS start_date,
        p_end_date AS end_date;
END$$

DELIMITER ;

-- Example: Delete all shows for movie 1 between specific dates
-- CALL DeleteMovieShows(1, -1, '2024-01-15', '2024-01-21');