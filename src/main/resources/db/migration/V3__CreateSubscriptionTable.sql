create sequence subscription_id_seq start 1 increment 1;
create table subscriptions(
	id int8 not null,
	owner_user_id int8,
	subscriber_user_id int8,
	primary key(id)
);

alter table subscriptions add constraint owner_user_id_fk foreign key(owner_user_id) references users;
alter table subscriptions add constraint subscriber_user_id_fk foreign key(subscriber_user_id) references users;