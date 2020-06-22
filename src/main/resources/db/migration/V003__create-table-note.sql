create table note(
	id bigint not null auto_increment,
    order_service_id bigint not null,
    description text not null,
    send_date datetime not null,
    
    primary key(id)
);

alter table note add constraint fk_note_order_service
foreign key(order_service_id) references order_service(id);