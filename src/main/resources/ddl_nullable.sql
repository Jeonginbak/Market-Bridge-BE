drop table if exists MarketBridge.address
(
    address_id bigint auto_increment
    primary key,

    member_id  bigint       ,
    alias      varchar(255) ,
    city       varchar(255) ,
    name       varchar(255) ,
    phone_no   varchar(255) ,
    street     varchar(255) ,
    zipcode    varchar(255) ,
    is_default bit          ,

    created_at datetime(6)  ,
    updated_at datetime(6)  ,
    deleted_at datetime(6)  null
    );

drop table if exists MarketBridge.admin
(

    admin_id   bigint auto_increment
    primary key,

    level      int          ,
    emp_no     varchar(255) ,
    name       varchar(255) ,
    password   varchar(255) ,

    created_at datetime(6)  ,
    updated_at datetime(6)  ,
    deleted_at datetime(6)  null
    );

drop table if exists MarketBridge.board
(
    board_id     bigint auto_increment
    primary key,

    content_type varchar(255) ,
    content      varchar(255) ,

    created_at   datetime(6)  ,
    updated_at   datetime(6)  ,
    deleted_at   datetime(6)  null

    );

drop table if exists MarketBridge.cancel_payment
(
    cancel_payment_id bigint auto_increment
    primary key,

    order_id          bigint       ,
    code              varchar(255) ,

    created_at        datetime(6)  ,
    updated_at        datetime(6)  ,
    deleted_at        datetime(6)  null
    );

drop table if exists MarketBridge.cart
(
    cart_id        bigint auto_increment
    primary key,

    member_id      bigint      ,
    prod_option_id bigint      ,
    is_subs        bit         ,
    quantity       int         ,

    created_at     datetime(6) ,
    updated_at     datetime(6) ,
    deleted_at     datetime(6) null


    );

drop table if exists MarketBridge.category
(
    category_id bigint auto_increment
    primary key,

    prev_id     bigint       ,
    next_id     bigint       ,
    name        varchar(255) ,

    created_at  datetime(6)  ,
    updated_at  datetime(6)  ,
    deleted_at  datetime(6)  null
    );

drop table if exists MarketBridge.coupon
(
    coupon_id     bigint auto_increment
    primary key,

    product_id    bigint       ,
    name          varchar(255) ,
    price         int          ,
    minimum_price int          ,
    count         int          ,
    start_date    datetime(6)  ,
    end_date      datetime(6)  ,

    created_at    datetime(6)  ,
    updated_at    datetime(6)  ,
    deleted_at    datetime(6)  null


    );

drop table if exists MarketBridge.coupon_budget
(
    coupon_budget_id bigint auto_increment
    primary key,

    member_id        bigint      ,
    price            int         ,

    created_at       datetime(6) ,
    updated_at       datetime(6) ,
    deleted_at       datetime(6) null
    );

drop table if exists MarketBridge.member_coupon
(
    member_coupon_id bigint auto_increment
    primary key,

    coupon_id        bigint      ,
    member_id        bigint      ,
    is_used          bit         ,
    used_date        datetime(6) ,

    created_at       datetime(6) ,
    updated_at       datetime(6) ,
    deleted_at       datetime(6) null
    );

drop table if exists MarketBridge.delivery
(
    delivery_id     bigint auto_increment
    primary key,

    address_id      bigint       ,
    order_detail_id bigint       ,
    seller_id       bigint       ,
    delivery_type   varchar(255) ,
    status_code     varchar(255) ,
    carrier         varchar(255) ,
    tracking_no     varchar(255) ,
    ship_date       datetime(6)  ,
    delivered_date  datetime(6)  ,

    created_at      datetime(6)  ,
    updated_at      datetime(6)  ,
    deleted_at      datetime(6)  null

    );

drop table if exists MarketBridge.estimated_time
(
    estimated_time_id bigint auto_increment
    primary key,

    add_day           int         ,
    hour              int         ,

    created_at        datetime(6) ,
    updated_at        datetime(6) ,
    deleted_at        datetime(6) null
    );

drop table if exists MarketBridge.help_desk
(
    help_desk_id  bigint auto_increment
    primary key,

    member_id     bigint       ,
    product_id    bigint       ,
    prod_order_id bigint       ,
    content_type  varchar(255) ,
    content       varchar(255) ,

    created_at    datetime(6)  ,
    updated_at    datetime(6)  ,
    deleted_at    datetime(6)  null
    );

drop table if exists MarketBridge.image
(
    image_id   bigint auto_increment
    primary key,

    type       varchar(255) ,
    url        varchar(255) ,

    created_at datetime(6)  ,
    updated_at datetime(6)  ,
    deleted_at datetime(6)  null
    );

drop table if exists MarketBridge.member
(

    member_id   bigint auto_increment
    primary key,

    membership  varchar(255) ,
    social_type varchar(255) ,
    email       varchar(255) ,
    phone_no    varchar(255) ,
    name        varchar(255) ,
    password    varchar(255) ,
    is_agree    bit          ,
    is_alert    bit          ,

    created_at  datetime(6)  ,
    updated_at  datetime(6)  ,
    deleted_at  datetime(6)  null
    );

drop table if exists MarketBridge.option_category
(
    option_category_id bigint auto_increment
    primary key,

    name               varchar(255) ,

    created_at         datetime(6)  ,
    updated_at         datetime(6)  ,
    deleted_at         datetime(6)  null

    );

drop table if exists MarketBridge.payment
(
    payment_id    bigint auto_increment
    primary key,

    prod_order_id bigint       ,
    pay_num       varchar(255) ,
    pg            varchar(255) ,
    receipt_id    varchar(255) ,
    pay_method    varchar(255) ,
    status_code   varchar(255) ,

    created_at    datetime(6)  ,
    updated_at    datetime(6)  ,
    deleted_at    datetime(6)  null
    );

drop table if exists MarketBridge.point
(
    point_id   bigint auto_increment
    primary key,

    member_id  bigint       ,
    in_point   int          ,
    out_point  int          ,
    balance    int          ,
    comments   varchar(255) ,

    created_at datetime(6)  ,
    updated_at datetime(6)  ,
    deleted_at datetime(6)  null

    );

drop table if exists MarketBridge.prod_option
(
    prod_option_id     bigint auto_increment
    primary key,

    option_category_id bigint       ,
    product_id         bigint       ,
    price              int          ,
    name               varchar(255) ,

    created_at         datetime(6)  ,
    updated_at         datetime(6)  ,
    deleted_at         datetime(6)  null
    );

drop table if exists MarketBridge.prod_order
(
    prod_order_id  bigint auto_increment
    primary key,

    member_id      bigint       ,
    status_code    varchar(255) ,
    total_price    bigint       ,
    point_rate     int          ,
    saved_point    int          ,
    delivered_date datetime(6)  ,

    created_at     datetime(6)  ,
    updated_at     datetime(6)  ,
    deleted_at     datetime(6)  null
    );

drop table if exists MarketBridge.prod_order_detail
(
    prod_order_detail_id bigint auto_increment
    primary key,

    product_id           bigint       ,
    prod_order_id        bigint       ,
    coupon_id            bigint       ,
    status_code          varchar(255) ,
    price                int          ,
    used_coupon          int          ,
    used_point           int          ,
    quantity             int          ,
    delivered_date       datetime(6)  ,
    reason               varchar(255) null,
    cancelled_at         datetime(6)  null,

    created_at           datetime(6)  ,
    updated_at           datetime(6)  ,
    deleted_at           datetime(6)  null


    );

drop table if exists MarketBridge.prod_tag
(
    prod_tag_id bigint auto_increment
    primary key,

    product_id  bigint      ,
    tag_id      bigint      ,

    created_at  datetime(6) ,
    updated_at  datetime(6) ,
    deleted_at  datetime(6) null

    );

drop table if exists MarketBridge.product
(
    product_id    bigint auto_increment
    primary key,

    category_id   bigint       ,
    name          varchar(255) ,
    price         int          ,
    is_own        bit          ,
    is_subs       bit          ,
    discount_rate int          ,
    thumb_img     varchar(255) ,

    created_at    datetime(6)  ,
    updated_at    datetime(6)  ,
    deleted_at    datetime(6)  null
    );

drop table if exists MarketBridge.product_image
(
    product_image_id bigint auto_increment
    primary key,

    image_id         bigint      ,
    product_id       bigint      ,

    created_at       datetime(6) ,
    updated_at       datetime(6) ,
    deleted_at       datetime(6) null
    );

drop table if exists MarketBridge.qna
(
    board_id   bigint auto_increment
    primary key,

    member_id  bigint       ,
    seller_id  bigint       ,
    board_type varchar(255) ,
    content    varchar(255) ,

    created_at datetime(6)  ,
    updated_at datetime(6)  ,
    deleted_at datetime(6)  null
    );

drop table if exists MarketBridge.review
(
    review_id  bigint auto_increment
    primary key,

    member_id  bigint       ,
    product_id bigint       ,
    content    varchar(255) ,
    rating     int          ,

    created_at datetime(6)  ,
    updated_at datetime(6)  ,
    deleted_at datetime(6)  null
    );

drop table if exists MarketBridge.review_image
(
    review_image_id bigint auto_increment
    primary key,

    image_id        bigint      ,
    review_id       bigint      ,

    created_at      datetime(6) ,
    updated_at      datetime(6) ,
    deleted_at      datetime(6) null
    );

drop table if exists MarketBridge.review_survey
(
    review_survey_id          bigint auto_increment
    primary key,

    review_id                 bigint      ,
    review_survey_category_id bigint      ,
    survey_content_id         bigint      ,

    created_at                datetime(6) ,
    updated_at                datetime(6) ,
    deleted_at                datetime(6) null
    );

drop table if exists MarketBridge.review_survey_category
(
    review_survey_category_id bigint auto_increment
    primary key,

    product_id                bigint       ,
    name                      varchar(255) ,

    created_at                datetime(6)  ,
    updated_at                datetime(6)  ,
    deleted_at                datetime(6)  null
    );

drop table if exists MarketBridge.reward
(

    reward_id  bigint auto_increment
    primary key,

    rate       int         ,

    created_at datetime(6) ,
    updated_at datetime(6) ,
    deleted_at datetime(6) null
    );

drop table if exists MarketBridge.seller
(
    seller_id  bigint auto_increment
    primary key,

    name       varchar(255) ,
    owner      varchar(255) ,
    biz_no     varchar(255) ,
    license_no varchar(255) ,
    category   varchar(255) ,
    detail     varchar(255) ,
    address    varchar(255) ,
    email      varchar(255) ,
    account_no varchar(255) ,

    created_at datetime(6)  ,
    updated_at datetime(6)  ,
    deleted_at datetime(6)  null
    );

drop table if exists MarketBridge.seller_product
(
    seller_product_id bigint auto_increment
    primary key,

    product_id        bigint      ,
    seller_id         bigint      ,

    created_at        datetime(6) ,
    updated_at        datetime(6) ,
    deleted_at        datetime(6) null

    );

drop table if exists MarketBridge.social_credential
(
    social_credential_id bigint auto_increment
    primary key,

    member_id            bigint       ,
    token_id             varchar(255) ,

    created_at           datetime(6)  ,
    updated_at           datetime(6)  ,
    deleted_at           datetime(6)  null

    );

drop table if exists MarketBridge.status_code
(
    status_code_id   bigint auto_increment
    primary key,

    code             varchar(255) ,
    name             varchar(255) ,
    status_code_type varchar(255) ,

    created_at       datetime(6)  ,
    updated_at       datetime(6)  ,
    deleted_at       datetime(6)  null
    );

drop table if exists MarketBridge.stock
(
    stock_id          bigint auto_increment
    primary key,

    product_option_id bigint      ,
    warehouse_id      bigint      ,
    quantity          int         ,

    created_at        datetime(6) ,
    updated_at        datetime(6) ,
    deleted_at        datetime(6) null

    );

drop table if exists MarketBridge.survey_content
(
    survey_content_id  bigint auto_increment
    primary key,

    survey_category_id bigint       ,
    seq_no             int          ,
    content            varchar(255) ,

    created_at         datetime(6)  ,
    updated_at         datetime(6)  ,
    deleted_at         datetime(6)  null
    );

drop table if exists MarketBridge.tag
(
    tag_id     bigint auto_increment
    primary key,

    name       varchar(255) ,

    created_at datetime(6)  ,
    updated_at datetime(6)  ,
    deleted_at datetime(6)  null
    );

drop table if exists MarketBridge.warehouse
(
    warehouse_id bigint auto_increment
    primary key,

    seller_id    bigint       ,
    alias        varchar(255) ,
    city         varchar(255) ,
    name         varchar(255) ,
    phone_no     varchar(255) ,
    street       varchar(255) ,
    zipcode      varchar(255) ,

    created_at   datetime(6)  ,
    updated_at   datetime(6)  ,
    deleted_at   datetime(6)  null
    );

