insert into user (id, create_date, modified_date, name, password, user_id)  values(0, current_time(), current_time(), '이승철', '1234', 'lsc109');
insert into label (id, create_date, modified_date, writer_id, title) values (0, current_time(), current_time(), 1, '전체메모');
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (1, current_time(), current_time(), 1, '메모2제목', '메모내용2', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (2, current_time(), current_time(), 1, '메모3제목', '메모내용3', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (3, current_time(), current_time(), 1, '메모4제목', '메모내용4', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (4, current_time(), current_time(), 1, '메모5제목', '메모내용5', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (5, current_time(), current_time(), 1, '메모6제목', '메모내용6', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (6, current_time(), current_time(), 1, '메모7제목', '메모내용7', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (7, current_time(), current_time(), 1, '메모8제목', '메모내용8', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (8, current_time(), current_time(), 1, '메모9제목', '메모내용9', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (9, current_time(), current_time(), 1, '메모10제목', '메모내영10', 0, 1);
insert into memo (id, create_date, modified_date, writer_id, title, content, deleted,label_id) values (10, current_time(), current_time(), 1, '메모11제목', '메모내영11', 0, 1);

