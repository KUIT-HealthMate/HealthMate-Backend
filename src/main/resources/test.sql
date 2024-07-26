insert into users(user_id, email, nickname, status, created_at)
values (1, 'test1@gmail.com', 'test1', 'GENERAL', current_timestamp);

insert into users(user_id, email, nickname, status, created_at)
values (2, 'test2@gmail.com', 'test2', 'GENERAL', current_timestamp);

insert into supplements (supplement_id, user_id, name, after_meal, selected_day, breakfast, lunch, dinner,
                         start_date, end_date, status, created_at, updated_at)
values (1, 1, 'test1', '30', '0000000', true, true, true, '2012-12-12', '2012-12-14', 'ACTIVE', current_timestamp(), current_timestamp());

insert into supplements (supplement_id, user_id, name, after_meal, selected_day, breakfast, lunch, dinner,
                         start_date, end_date, status, created_at, updated_at)
values (2, 1, 'test2', '30', '0000000', true, true, true, '2012-12-12', '2012-12-14', 'ACTIVE', current_timestamp(), current_timestamp());

insert Into supplement_checker(supplement_checker_id, supplement_id, time_slot, status, check_date)
values (1, 1, 'LUNCH', true, current_date);

insert Into supplement_checker(supplement_checker_id, supplement_id, time_slot, status, check_date)
values (2, 1, 'LUNCH', true, '2012-12-14');
