insert into `product_category` ( `id`, `name`) values ( '0910A44F-9C50-4083-8CD7-D48FC51FA9F9', 'Atasan');
insert into `product_category` ( `id`, `name`) values ( '0910A44F-9C50-4083-8CD7-D48FC51FA9F8', 'Bawahan');
insert into `product_category` ( `id`, `name`) values ( '0910A44F-9C50-4083-8CD7-D48FC51FA9F7', 'Sepatu');

insert into `product` ( `weight`, `id`, `create_date`, `price`, `update_by`, `create_by`, `update_date`, `description`, `category_id`, `name`) values ( '1', '0910A44F-9C50-4083-8CD7-D48FC51FA9A9', '2017-11-04 08:21:07', '98000', 'admin', 'admin', '2017-11-04 08:21:12', 'Size S :\nLingkar dada 88 cm, Lebar bahu 34 cm, Panjang lengan 29 cm, Lingkar lengan 43 cm, Panjang 59 cm, Lingkar pinggang 66 cm\nSize M :\nLingkar dada 92 cm, Lebar bahu 35 cm, Panjang lengan 30 cm, Lingkar lengan 44 cm, Panjang 61 cm, Lingkar pinggang 68 cm', '0910A44F-9C50-4083-8CD7-D48FC51FA9F7', 'Anavella Plain Peplum Blouse');

insert into `product_detail` ( `size`, `id`, `stock`, `product_id`, `color`) values ( 'XL', '0910A44F-9C50-4083-8CD7-D48FC51FA9B9', '5', '0910A44F-9C50-4083-8CD7-D48FC51FA9A9', 'Pink');
insert into `product_detail` ( `size`, `id`, `stock`, `product_id`, `color`) values ( 'M', '0910A44F-9C50-4083-8CD7-D48FC51FA9B8', '5', '0910A44F-9C50-4083-8CD7-D48FC51FA9A9', 'Kuning');
insert into `product_detail` ( `size`, `id`, `stock`, `product_id`, `color`) values ( 'M', '0910A44F-9C50-4083-8CD7-D48FC51FA9B7', '5', '0910A44F-9C50-4083-8CD7-D48FC51FA9A9', 'Merah Maroon');