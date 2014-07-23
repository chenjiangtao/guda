alter table m_agent add index agent_ip (ip);

alter table m_sys add index sys_ip_k (ip,k,gmt_created);
alter table m_sys add index sys_gmt_created (gmt_created);
create clustered  index sys_created  on sys_gmt_created (gmt_created);

alter table m_warn add index warn_ip_k (ip,k);