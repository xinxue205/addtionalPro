package es;

/**
 * ES常量相关类
 * Created by daiyitian
 */
public interface EsConstants {

    /**
     * ES索引
     */
    String INDEX_NAME = "s2b";

    /**
     * ES spu文档类型
     */
    String DOC_GOODS_TYPE = "es_goods";

    /**
     * ES sku文档类型
     */
    String DOC_GOODS_INFO_TYPE = "es_goods_info";

    /**
     * ES品牌分类联合文档类型
     */
    String DOC_CATE_BRAND_TYPE = "goods_cate_brand";

    /**
     * 默认，中文分词
     */
    String DEF_ANALYZER = "ik_max_word";

    /**
     * 空格为分隔符
     */
    String PINYIN_ANALYZER = "pinyin";

    /**
     * ES 优惠券文档类型
     */
    String DOC_COUPON_INFO_TYPE = "es_coupon_info";

    /**
     * ES employee文档类型
     */
    String DOC_EMPLOYEE_TYPE = "es_employee";

    /**
     * 分销员
     */
    String DISTRIBUTION_CUSTOMER = "es_distribution_customer";

    /**
     *
     */
    String STORE_EVALUATE_SUM = "es_store_evaluate_sum";

    /**
     * 拼团活动
     */
    String GROUPON_ACTIVITY = "es_groupon_activity";

    /**
     * ES 公司店铺
     */
    String DOC_STORE_INFORMATION_TYPE = "es_store_information";

    /**
     * ES 优惠券活动
     */
    String DOC_COUPON_ACTIVITY = "es_coupon_activity";

    /**
     * 商品库
     */
    String DOC_STANDARD_GOODS = "es_standard_goods";

    /**
     * 品牌
     */
    String ES_GOODS_BRAND = "es_goods_brand";

    /**
     * 敏感词库
     */
    String SENSITIVE_WORDS = "es_sensitive_words";

    /**
     * ES 结算单 合并供应商结算和商家结算
     */
    String DOC_SETTLEMENT = "es_settlement";

    /**
     * 素材
     */
    String SYSTEM_RESOURCE = "es_system_resource";

    /**
     * ES 会员详情信息
     */
    String DOC_CUSTOMER_DETAIL = "es_customer_detail";

    /**
     * ES 会员资金
     */
    String DOC_CUSTOMER_FUNDS = "es_customer_funds";

    /**
     * 敏感词
     */
    String SEARCH_ASSOCIATIONAL_WORD = "es_search_associational_word";

    /**
     * 操作日志
     */
    String SYSTEM_OPERATION_LOG = "es_system_operation_log";

    /**
     * 评价管理
     */
    String GOODS_EVALUATE = "es_goods_evaluate";


    /**
     * 订单开票
     */
    String ORDER_INVOICE = "es_order_invoice";

    String ES_CUSTOMER_INVOICE = "es_customer_invoice";

    /**
     * ES 会员积分详情信息
     */
    String DOC_CUSTOMER_POINTS_DETAIL = "es_customer_points_detail";

    /**
     * ES 积分商品文档类型
     */
    String DOC_POINTS_GOODS_INFO_TYPE = "es_points_goods_info";

    /**
     * 订单收款
     */
    String DOC_PAY_ORDER = "es_pay_order";

    /**
     * 分销记录
     */
    String DISTRIBUTION_RECORD = "es_distribution_record";

    /**
     * 邀请新人记录
     */
    String INVITE_NEW_RECORD = "es_invite_new_record";

    /**
     * 退单退款
     */
    String DOC_REFUND_ORDER = "es_refund_order";
    /**
     * Es 对账单
     */
    String DOC_RECONCILIATION = "es_reconciliation";
    /**
     * Es 会员提现
     */
    String DOC_CUSTOMER_DRAW_CASH = "es_customer_draw_cash";

    /**
     * 分销素材
     */
    String DISTRIBUTION_GOODS_MATTER = "es_distribution_goods_matter";
}
