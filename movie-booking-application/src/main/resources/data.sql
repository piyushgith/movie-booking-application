-- Insert 10 Movies
INSERT INTO MOVIES (ID, DURATION, GENRE, LANGUAGE, MOVIE_NAME, RATING, RELEASE_DATE) VALUES
(1, 120, 'ACTION', 'ENGLISH', 'Deadpool & Wolverine', 8.0, '2024-07-26'),
(2, 150, 'DRAMA', 'HINDI', 'Narshimha', 7.8, '2024-08-15'),
(3, 135, 'DRAMA', 'ENGLISH', 'Alien: Romulus', 7.5, '2024-08-16'),
(4, 140, 'COMEDY', 'HINDI', 'Khel Khel Mein', 6.5, '2024-08-15'),
(5, 130, 'THRILLER', 'ENGLISH', 'Blink Twice', 6.8, '2024-08-23'),
(6, 145, 'ACTION', 'HINDI', 'Yodha', 6.2, '2024-03-15'),
(7, 125, 'ANIMATION', 'ENGLISH', 'Mufasa: The Lion King', 7.0, '2024-12-20'),
(8, 160, 'DRAMA', 'HINDI', 'Shershaah 2', 7.2, '2025-01-15'),
(9, 110, 'COMEDY', 'ENGLISH', 'Beetlejuice Beetlejuice', 7.4, '2024-09-06'),
(10, 155, 'DRAMA', 'ENGLISH', 'Dune: Part Three', 8.5, '2025-02-14');

-- Insert 8 Theaters (2 per location: Bangalore, Chennai, Delhi, Mumbai)
INSERT INTO THEATERS (ID, ADDRESS,LOCATION, NAME) VALUES
(1, 'MG Road', 'Bangalore', 'PVR Orion Mall'),
(2, 'Koramangala', 'Bangalore', 'INOX Forum Mall'),
(3, 'T Nagar', 'Chennai', 'Sathyam Cinemas'),
(4, 'Anna Nagar', 'Chennai', 'SPI Palazzo'),
(5, 'Connaught Place', 'Delhi', 'PVR Plaza'),
(6, 'Saket', 'Delhi', 'INOX DLF Mall'),
(7, 'Bandra', 'Mumbai', 'PVR Juhu'),
(8, 'Andheri', 'Mumbai', 'Cinepolis Andheri');

-- Insert Theater Seats (80 seats per theater: 8 rows A-H, 10 columns 1-10)
-- Using a loop or cross join to generate seat numbers
INSERT INTO THEATER_SEATS (id, seat_no, seat_type, theater_id)
SELECT
    ROW_NUMBER() OVER () AS id,
    CONCAT(row_name, col_num) AS seat_no,
    'CLASSIC' AS seat_type,
    theater_id
FROM (
    SELECT
        t.id AS theater_id,
        row_name,
        col_num
    FROM
        (SELECT id FROM THEATERS) t
        CROSS JOIN
        (SELECT 'A' AS row_name UNION SELECT 'B' UNION SELECT 'C' UNION SELECT 'D'
         UNION SELECT 'E' UNION SELECT 'F' UNION SELECT 'G' UNION SELECT 'H') rows
        CROSS JOIN
        (SELECT 1 AS col_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
         UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) cols
) seat_combinations
ORDER BY theater_id, row_name, col_num;

-- Insert Shows (2 shows per movie per theater)
-- 10 movies * 8 theaters * 2 shows = 160 shows
INSERT INTO SHOWS (show_id, date, time, movie_id, theater_id)
SELECT
    ROW_NUMBER() OVER () AS show_id,
    '2025-09-10' AS date,
    CASE
        WHEN ROW_NUMBER() OVER (PARTITION BY m.id, t.id) = 1 THEN '15:00:00'
        ELSE '18:00:00'
    END AS time,
    m.id AS movie_id,
    t.id AS theater_id
FROM MOVIES m CROSS JOIN THEATERS t
ORDER BY m.id, t.id;

-- Insert Show Seats (80 seats per show)
-- 160 shows * 80 seats = 12,800 show seats
INSERT INTO SHOW_SEATS (id, is_available, is_food_contains, price, theater_seat_id, show_id)
SELECT
    ROW_NUMBER() OVER () AS id,
    TRUE AS is_available,
    FALSE AS is_food_contains,
    200 AS price,
    ts.id AS theater_seat_id,
    s.show_id
FROM SHOWS s JOIN THEATER_SEATS ts ON s.theater_id = ts.theater_id
ORDER BY s.show_id, ts.id;



--
---- Insert 10 rows into movies
--INSERT INTO movies (duration, genre, language, movie_name, rating, release_date) VALUES
--(120, 'ACTION', 'ENGLISH', 'The Dark Knight', 8.4, '2008-07-18'),
--(150, 'DRAMA', 'HINDI', 'Dangal', 8.3, '2016-12-23'),
--(135, 'COMEDY', 'TAMIL', 'Boss Engira Baskaran', 7.2, '2010-09-10'),
--(140, 'THRILLER', 'TELUGU', 'Drishyam', 8.3, '2013-07-31'),
--(125, 'ANIMATION', 'ENGLISH', 'Toy Story', 8.3, '1995-11-22'),
--(130, 'ROMANTIC', 'KANNADA', 'Mungaru Male', 8.1, '2006-12-29'),
--(145, 'HISTORICAL', 'MARATHI', 'Harishchandrachi Factory', 8.4, '2009-01-29'),
--(110, 'SOCIAL', 'PUNJAB', 'Udta Punjab', 7.7, '2016-06-17'),
--(155, 'WAR', 'ENGLISH', 'Dunkirk', 7.8, '2017-07-21'),
--(115, 'SPORTS', 'HINDI', 'Chak De! India', 8.1, '2007-08-10');
--
---- Insert 10 rows into theaters
--INSERT INTO theaters (address, name) VALUES
--('123 MG Road, Bengaluru', 'PVR Orion'),
--('456 Anna Salai, Chennai', 'Sathyam Cinemas'),
--('789 Bandra West, Mumbai', 'INOX Nariman'),
--('101 Jubilee Hills, Hyderabad', 'Prasads Multiplex'),
--('321 Connaught Place, Delhi', 'Regal Cinema'),
--('654 Koramangala, Bengaluru', 'Cinepolis'),
--('987 Velachery, Chennai', 'Phoenix Marketcity'),
--('147 Andheri East, Mumbai', 'PVR Dynamix'),
--('258 Banjara Hills, Hyderabad', 'GVK One'),
--('369 Karol Bagh, Delhi', 'Liberty Cinema');
--
---- Insert 10 rows into theater_seats
--INSERT INTO theater_seats (seat_no, seat_type, theater_id) VALUES
--('A1', 'CLASSIC', 1),
--('A2', 'CLASSIC', 1),
--('B1', 'PREMIUM', 2),
--('B2', 'PREMIUM', 2),
--('C1', 'CLASSIC', 3),
--('C2', 'CLASSIC', 3),
--('D1', 'PREMIUM', 4),
--('D2', 'PREMIUM', 4),
--('E1', 'CLASSIC', 5),
--('E2', 'CLASSIC', 5);
--
---- Insert 10 rows into shows
--INSERT INTO shows (date, time, movie_id, theater_id) VALUES
--('2025-09-01', '10:00:00', 1, 1),
--('2025-09-01', '13:00:00', 2, 2),
--('2025-09-02', '16:00:00', 3, 3),
--('2025-09-02', '19:00:00', 4, 4),
--('2025-09-03', '11:00:00', 5, 5),
--('2025-09-03', '14:00:00', 6, 6),
--('2025-09-04', '17:00:00', 7, 7),
--('2025-09-04', '20:00:00', 8, 8),
--('2025-09-05', '12:00:00', 9, 9),
--('2025-09-05', '15:00:00', 10, 10);
--
---- Insert 10 rows into show_seats
--INSERT INTO show_seats (is_available, is_food_contains, price, seat_no, seat_type, show_show_id) VALUES
--(true, false, 200, 'A1', 'CLASSIC', 1),
--(true, true, 350, 'A2', 'PREMIUM', 1),
--(false, false, 250, 'B1', 'CLASSIC', 2),
--(true, false, 300, 'B2', 'PREMIUM', 2),
--(true, true, 220, 'C1', 'CLASSIC', 3),
--(false, false, 400, 'C2', 'PREMIUM', 3),
--(true, true, 280, 'D1', 'CLASSIC', 4),
--(true, false, 360, 'D2', 'PREMIUM', 4),
--(false, true, 210, 'E1', 'CLASSIC', 5),
--(true, false, 390, 'E2', 'PREMIUM', 5);
--
---- Insert 10 rows into users
--INSERT INTO users (address, age, email_id, gender, mobile_no, name, password, roles) VALUES
--('12 Jayanagar, Bengaluru', 25, 'rahul.sharma@gmail.com', 'MALE', '9876543210', 'Rahul Sharma', 'pass123', 'USER'),
--('34 T Nagar, Chennai', 30, 'priya.singh@gmail.com', 'FEMALE', '8765432109', 'Priya Singh', 'pass456', 'USER'),
--('56 Bandra, Mumbai', 22, 'arjun.mehta@gmail.com', 'MALE', '7654321098', 'Arjun Mehta', 'pass789', 'USER'),
--('78 Banjara Hills, Hyderabad', 28, 'anita.verma@gmail.com', 'FEMALE', '6543210987', 'Anita Verma', 'pass101', 'USER'),
--('90 Karol Bagh, Delhi', 35, 'vikas.yadav@gmail.com', 'MALE', '5432109876', 'Vikas Yadav', 'pass202', 'USER'),
--('23 Koramangala, Bengaluru', 27, 'neha.kumar@gmail.com', 'FEMALE', '4321098765', 'Neha Kumar', 'pass303', 'USER'),
--('45 Velachery, Chennai', 29, 'suresh.nair@gmail.com', 'MALE', '3210987654', 'Suresh Nair', 'pass404', 'USER'),
--('67 Andheri, Mumbai', 24, 'pooja.desai@gmail.com', 'FEMALE', '2109876543', 'Pooja Desai', 'pass505', 'USER'),
--('89 Gachibowli, Hyderabad', 31, 'ravi.patel@gmail.com', 'MALE', '1098765432', 'Ravi Patel', 'pass606', 'USER'),
--('11 Connaught Place, Delhi', 26, 'meera.joshi@gmail.com', 'FEMALE', '0987654321', 'Meera Joshi', 'pass707', 'USER');

-- Insert 10 rows into tickets
--INSERT INTO tickets (booked_at, booked_seats, total_tickets_price, show_show_id, user_id) VALUES
--('2025-08-30', 'A1', 200, 1, 1),
--('2025-08-30', 'A2', 350, 1, 2),
--('2025-08-31', 'B1', 250, 2, 3),
--('2025-08-31', 'B2', 300, 2, 4),
--('2025-09-01', 'C1', 220, 3, 5),
--('2025-09-01', 'C2', 400, 3, 6),
--('2025-09-02', 'D1', 280, 4, 7),
--('2025-09-02', 'D2', 360, 4, 8),
--('2025-09-03', 'E1', 210, 5, 9),
--('2025-09-03', 'E2', 390, 5, 10);