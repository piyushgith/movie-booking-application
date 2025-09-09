-- Insert 10 Movies
INSERT INTO MOVIE (DURATION, GENRE, LANGUAGE, MOVIE_NAME, RATING, RELEASE_DATE,MOVIE_IMAGE) VALUES
--(120, 'ACTION', 'ENGLISH', 'Deadpool & Wolverine', 8.0, '2024-07-26','https://placehold.co/150x100/702963/FFFFFF?text=Deadpool+Wolverine'),
--(150, 'THRILLER', 'HINDI', 'Andhadhun', 9.8, '2024-08-15','https://placehold.co/150x100/702963/FFFFFF?text=Andhadhun'),
--(135, 'DRAMA', 'ENGLISH', 'Alien: Romulus', 7.5, '2024-08-16','https://placehold.co/150x100/702963/FFFFFF?text=Alien+Romulus'),
--(140, 'COMEDY', 'HINDI', 'Don 2', 6.5, '2024-08-15','https://placehold.co/150x100/702963/FFFFFF?text=Don+2'),
--(130, 'THRILLER', 'ENGLISH', 'Blink Twice', 6.8, '2024-08-23','https://placehold.co/150x100/702963/FFFFFF?text=Blink+Twice'),
--(145, 'ACTION', 'HINDI', 'Yodha', 6.2, '2024-03-15','https://placehold.co/150x100/702963/FFFFFF?text=Yodha'),
--(125, 'ANIMATION', 'ENGLISH', 'Mufasa: The Lion King', 7.0, '2024-12-20','https://placehold.co/150x100/702963/FFFFFF?text=Mufasa'),
--(160, 'DRAMA', 'HINDI', 'Shershaah 2', 7.2, '2025-01-15','https://placehold.co/150x100/702963/FFFFFF?text=Shershaah+2'),
--(110, 'COMEDY', 'ENGLISH', 'Beetlejuice Beetlejuice', 7.4, '2024-09-06','https://placehold.co/150x100/702963/FFFFFF?text=Beetlejuice'),
--(155, 'DRAMA', 'ENGLISH', 'Dune: Part Three', 8.5, '2025-02-14','https://placehold.co/150x100/702963/FFFFFF?text=Dune+3'),
(148, 'ACTION', 'ENGLISH', 'Spider-Man: No Way Home', 8.4, '2021-12-17','https://placehold.co/150x100/702963/FFFFFF?text=Spider-Man'),
(181, 'SCIENCE_FICTION', 'ENGLISH', 'Avengers: Endgame', 8.4, '2019-04-26','https://placehold.co/150x100/702963/FFFFFF?text=Avengers+Endgame'),
(155, 'ACTION', 'ENGLISH', 'Top Gun: Maverick', 8.3, '2022-05-27','https://placehold.co/150x100/702963/FFFFFF?text=Top+Gun+Maverick'),
(142, 'DRAMA', 'ENGLISH', 'The Batman', 7.8, '2022-03-04','https://placehold.co/150x100/702963/FFFFFF?text=The+Batman'),
(130, 'COMEDY', 'HINDI', 'Mahavatar Narsimha', 6.0, '2025-08-25','https://placehold.co/150x100/702963/FFFFFF?text=Mahavatar+Narsimha'),
(169, 'ACTION', 'HINDI', 'RRR', 8.0, '2022-03-25','https://placehold.co/150x100/702963/FFFFFF?text=RRR'),
(175, 'DRAMA', 'HINDI', 'Dangal', 8.4, '2016-12-23','https://placehold.co/150x100/702963/FFFFFF?text=Dangal'),
(164, 'ACTION', 'TAMIL', 'Vikram', 8.4, '2022-06-03','https://placehold.co/150x100/702963/FFFFFF?text=Vikram'),
(169, 'ACTION', 'TELUGU', 'Pushpa: The Rise', 7.6, '2021-12-17','https://placehold.co/150x100/702963/FFFFFF?text=Pushpa+The+Rise'),
(148, 'THRILLER', 'ENGLISH', 'John Wick: Chapter 4', 7.7, '2023-03-24','https://placehold.co/150x100/702963/FFFFFF?text=John+Wick+4');


-- THEATERS TABLE (8 Theaters - 2 per location)
INSERT INTO theater (address, location, name) VALUES
-- BANGALORE
('MG Road, Brigade Gateway', 'BANGALORE', 'PVR Forum Mall'),
('Whitefield Main Road', 'BANGALORE', 'INOX Garuda Mall'),

-- CHENNAI
('Express Avenue Mall, Royapettah', 'CHENNAI', 'PVR Express Avenue'),
('Phoenix MarketCity, Velachery', 'CHENNAI', 'AGS Cinemas OMR'),

-- DELHI
('Select City Walk, Saket', 'DELHI', 'PVR Select City'),
('Ambience Mall, Gurgaon', 'DELHI', 'INOX Insignia'),

-- MUMBAI
('Phoenix Mills, Lower Parel', 'MUMBAI', 'PVR Phoenix'),
('Palladium Mall, High Street Phoenix', 'MUMBAI', 'INOX Palladium');

-- SHOWS TABLE (2 shows per movie per theater = 10 movies × 8 theaters × 2 shows = 160 shows)
INSERT INTO show (date, time, movie_id, theater_id) VALUES
-- Movie 1 (Spider-Man: No Way Home) in all theaters
('2024-01-15', '10:00', 1, 1), ('2024-01-15', '18:00', 1, 1),
('2024-01-15', '11:00', 1, 2), ('2024-01-15', '19:00', 1, 2),
('2024-01-15', '12:00', 1, 3), ('2024-01-15', '20:00', 1, 3),
('2024-01-15', '13:00', 1, 4), ('2024-01-15', '21:00', 1, 4),
('2024-01-15', '14:00', 1, 5), ('2024-01-15', '22:00', 1, 5),
('2024-01-15', '15:00', 1, 6), ('2024-01-15', '23:00', 1, 6),
('2024-01-15', '16:00', 1, 7), ('2024-01-15', '19:30', 1, 7),
('2024-01-15', '17:00', 1, 8), ('2024-01-15', '20:30', 1, 8),

-- Movie 2 (Avengers: Endgame) in all theaters
('2024-01-16', '10:00', 2, 1), ('2024-01-16', '18:00', 2, 1),
('2024-01-16', '11:00', 2, 2), ('2024-01-16', '19:00', 2, 2),
('2024-01-16', '12:00', 2, 3), ('2024-01-16', '20:00', 2, 3),
('2024-01-16', '13:00', 2, 4), ('2024-01-16', '21:00', 2, 4),
('2024-01-16', '14:00', 2, 5), ('2024-01-16', '22:00', 2, 5),
('2024-01-16', '15:00', 2, 6), ('2024-01-16', '23:00', 2, 6),
('2024-01-16', '16:00', 2, 7), ('2024-01-16', '19:30', 2, 7),
('2024-01-16', '17:00', 2, 8), ('2024-01-16', '20:30', 2, 8),

-- Movie 3 (Top Gun: Maverick) in all theaters
('2024-01-17', '10:00', 3, 1), ('2024-01-17', '18:00', 3, 1),
('2024-01-17', '11:00', 3, 2), ('2024-01-17', '19:00', 3, 2),
('2024-01-17', '12:00', 3, 3), ('2024-01-17', '20:00', 3, 3),
('2024-01-17', '13:00', 3, 4), ('2024-01-17', '21:00', 3, 4),
('2024-01-17', '14:00', 3, 5), ('2024-01-17', '22:00', 3, 5),
('2024-01-17', '15:00', 3, 6), ('2024-01-17', '23:00', 3, 6),
('2024-01-17', '16:00', 3, 7), ('2024-01-17', '19:30', 3, 7),
('2024-01-17', '17:00', 3, 8), ('2024-01-17', '20:30', 3, 8),

-- Movie 4 (The Batman) in all theaters
('2024-01-18', '10:00', 4, 1), ('2024-01-18', '18:00', 4, 1),
('2024-01-18', '11:00', 4, 2), ('2024-01-18', '19:00', 4, 2),
('2024-01-18', '12:00', 4, 3), ('2024-01-18', '20:00', 4, 3),
('2024-01-18', '13:00', 4, 4), ('2024-01-18', '21:00', 4, 4),
('2024-01-18', '14:00', 4, 5), ('2024-01-18', '22:00', 4, 5),
('2024-01-18', '15:00', 4, 6), ('2024-01-18', '23:00', 4, 6),
('2024-01-18', '16:00', 4, 7), ('2024-01-18', '19:30', 4, 7),
('2024-01-18', '17:00', 4, 8), ('2024-01-18', '20:30', 4, 8),

-- Movie 5 (Pathaan) in all theaters
('2024-01-19', '10:00', 5, 1), ('2024-01-19', '18:00', 5, 1),
('2024-01-19', '11:00', 5, 2), ('2024-01-19', '19:00', 5, 2),
('2024-01-19', '12:00', 5, 3), ('2024-01-19', '20:00', 5, 3),
('2024-01-19', '13:00', 5, 4), ('2024-01-19', '21:00', 5, 4),
('2024-01-19', '14:00', 5, 5), ('2024-01-19', '22:00', 5, 5),
('2024-01-19', '15:00', 5, 6), ('2024-01-19', '23:00', 5, 6),
('2024-01-19', '16:00', 5, 7), ('2024-01-19', '19:30', 5, 7),
('2024-01-19', '17:00', 5, 8), ('2024-01-19', '20:30', 5, 8),

-- Movie 6 (RRR) in all theaters
('2024-01-20', '10:00', 6, 1), ('2024-01-20', '18:00', 6, 1),
('2024-01-20', '11:00', 6, 2), ('2024-01-20', '19:00', 6, 2),
('2024-01-20', '12:00', 6, 3), ('2024-01-20', '20:00', 6, 3),
('2024-01-20', '13:00', 6, 4), ('2024-01-20', '21:00', 6, 4),
('2024-01-20', '14:00', 6, 5), ('2024-01-20', '22:00', 6, 5),
('2024-01-20', '15:00', 6, 6), ('2024-01-20', '23:00', 6, 6),
('2024-01-20', '16:00', 6, 7), ('2024-01-20', '19:30', 6, 7),
('2024-01-20', '17:00', 6, 8), ('2024-01-20', '20:30', 6, 8),

-- Movie 7 (Dangal) in all theaters
('2024-01-21', '10:00', 7, 1), ('2024-01-21', '18:00', 7, 1),
('2024-01-21', '11:00', 7, 2), ('2024-01-21', '19:00', 7, 2),
('2024-01-21', '12:00', 7, 3), ('2024-01-21', '20:00', 7, 3),
('2024-01-21', '13:00', 7, 4), ('2024-01-21', '21:00', 7, 4),
('2024-01-21', '14:00', 7, 5), ('2024-01-21', '22:00', 7, 5),
('2024-01-21', '15:00', 7, 6), ('2024-01-21', '23:00', 7, 6),
('2024-01-21', '16:00', 7, 7), ('2024-01-21', '19:30', 7, 7),
('2024-01-21', '17:00', 7, 8), ('2024-01-21', '20:30', 7, 8),

-- Movie 8 (Vikram) in all theaters
('2024-01-22', '10:00', 8, 1), ('2024-01-22', '18:00', 8, 1),
('2024-01-22', '11:00', 8, 2), ('2024-01-22', '19:00', 8, 2),
('2024-01-22', '12:00', 8, 3), ('2024-01-22', '20:00', 8, 3),
('2024-01-22', '13:00', 8, 4), ('2024-01-22', '21:00', 8, 4),
('2024-01-22', '14:00', 8, 5), ('2024-01-22', '22:00', 8, 5),
('2024-01-22', '15:00', 8, 6), ('2024-01-22', '23:00', 8, 6),
('2024-01-22', '16:00', 8, 7), ('2024-01-22', '19:30', 8, 7),
('2024-01-22', '17:00', 8, 8), ('2024-01-22', '20:30', 8, 8),

-- Movie 9 (Pushpa: The Rise) in all theaters
('2024-01-23', '10:00', 9, 1), ('2024-01-23', '18:00', 9, 1),
('2024-01-23', '11:00', 9, 2), ('2024-01-23', '19:00', 9, 2),
('2024-01-23', '12:00', 9, 3), ('2024-01-23', '20:00', 9, 3),
('2024-01-23', '13:00', 9, 4), ('2024-01-23', '21:00', 9, 4),
('2024-01-23', '14:00', 9, 5), ('2024-01-23', '22:00', 9, 5),
('2024-01-23', '15:00', 9, 6), ('2024-01-23', '23:00', 9, 6),
('2024-01-23', '16:00', 9, 7), ('2024-01-23', '19:30', 9, 7),
('2024-01-23', '17:00', 9, 8), ('2024-01-23', '20:30', 9, 8),

-- Movie 10 (John Wick: Chapter 4) in all theaters
('2024-01-24', '10:00', 10, 1), ('2024-01-24', '18:00', 10, 1),
('2024-01-24', '11:00', 10, 2), ('2024-01-24', '19:00', 10, 2),
('2024-01-24', '12:00', 10, 3), ('2024-01-24', '20:00', 10, 3),
('2024-01-24', '13:00', 10, 4), ('2024-01-24', '21:00', 10, 4),
('2024-01-24', '14:00', 10, 5), ('2024-01-24', '22:00', 10, 5),
('2024-01-24', '15:00', 10, 6), ('2024-01-24', '23:00', 10, 6),
('2024-01-24', '16:00', 10, 7), ('2024-01-24', '19:30', 10, 7),
('2024-01-24', '17:00', 10, 8), ('2024-01-24', '20:30', 10, 8);


-- More dynamic approach using number generation
INSERT INTO SHOW_SEAT (is_available, is_food_contains, price, seat_no, seat_type, theater_id, show_id)
WITH row_generator AS (
    SELECT 'A' as row_char
    UNION ALL SELECT 'B'
    UNION ALL SELECT 'C'
    UNION ALL SELECT 'D'
    UNION ALL SELECT 'E'
    UNION ALL SELECT 'F'
    UNION ALL SELECT 'G'
    UNION ALL SELECT 'H'
),
column_generator AS (
    SELECT 1 as col_num
    UNION ALL SELECT 2
    UNION ALL SELECT 3
    UNION ALL SELECT 4
    UNION ALL SELECT 5
    UNION ALL SELECT 6
    UNION ALL SELECT 7
    UNION ALL SELECT 8
    UNION ALL SELECT 9
    UNION ALL SELECT 10
),
all_seats AS (
    SELECT
        rg.row_char,
        cg.col_num,
        CONCAT(rg.row_char, cg.col_num) as full_seat_no
    FROM row_generator rg
    CROSS JOIN column_generator cg
),
seat_pricing AS (
    SELECT
        aset.row_char,
        aset.col_num,
        aset.full_seat_no,
        CASE
            WHEN aset.row_char IN ('A', 'B') THEN 'PREMIUM'
            WHEN aset.row_char IN ('C', 'D', 'E') THEN 'STANDARD'
            ELSE 'REGULAR'
        END as seat_category,
        CASE
            WHEN aset.row_char IN ('A', 'B') THEN 250
            WHEN aset.row_char IN ('C', 'D', 'E') THEN 200
            ELSE 150
        END as seat_price,
        CASE
            WHEN aset.col_num IN (3, 7) THEN TRUE
            ELSE FALSE
        END as food_available
    FROM all_seats aset
)
SELECT
    TRUE as is_available,
    sp.food_available as is_food_contains,
    sp.seat_price as price,
    sp.full_seat_no as seat_no,
    sp.seat_category as seat_type,
    s.theater_id,
    s.show_id
FROM SHOW s
CROSS JOIN seat_pricing sp
ORDER BY s.show_id, sp.row_char, sp.col_num;