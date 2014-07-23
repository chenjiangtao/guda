delete from jc_channel_ext where channel_id in (select channel_id from jc_channel where model_id in (select model_id  from jc_model where model_id=8));


delete from jc_model_item where model_id in (select model_id  from jc_model where model_id=8);

delete from jc_channel where model_id in (select model_id  from jc_model where model_id=8);

delete from jc_channel_model where model_id in (select model_id  from jc_model where model_id=8);

delete from jc_model where model_id=8;

