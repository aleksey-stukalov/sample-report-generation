alter table REPORTGENERATION_ORDER add column DATE_ date ^
update REPORTGENERATION_ORDER set DATE_ = current_date where DATE_ is null ;
alter table REPORTGENERATION_ORDER alter column DATE_ set not null ;
