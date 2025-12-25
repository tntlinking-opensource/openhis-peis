package com.center.medical.enterprise.common.config;

import java.util.HashSet;

/**
 * @author Citrus
 * @date 2021/8/6 14:32
 */
public class SensitiveWordConfig {

    private static final String SENSITIVE_WORD_SEARCH_URL = "/sys/pconfig/info/SENSITIVE_WORDS";
    private static final String WEBJARS_URL = "/webjars/**";
    private static final String SWAGGER_URL = "/swagger/**";
    private static final String API_DOCS_URL = "/v2/api-docs";
    private static final String DOC_URL = "/doc.html";
    private static final String SWAGGER_UI_URL = "/swagger-ui.html";
    private static final String SWAGGER_RESOURCES_URL = "/swagger-resources/**";
    private static final String CAPTCHA_URL = "/captcha.jpg";
    private static final String SAVE_CONFIG = "/sys/pconfig/save";
    /**
     * excel
     */
    private static final String SOLD_EXCEL = "/order/order/soldExcel";
    private static final String PL_SOLD_EXCEL = "/platform/order/soldExcel";
    private static final String SCORE_SOLD_EXCEL = "/score/order/soldExcel";
    private static final String IMPORT_USER_MODEL = "/admin/user/importUserModel";
    private static final String ADMIN_FORM_EXCEL = "/admin/form/formExcel";
    private static final String PLATFORM_FORM_EXCEL = "/platform/form/formExcel";
    private static final String EXPORT_USER = "/admin/user/exportUser";
    private static final String UN_DELIVERY_ORDER_EXCEL = "/order/order/unDeliveryOrderExcel";
    private static final String DOWNLOAD_MODEL = "/prod/prod/downloadModel";
    private static final String EXPORT_PROD = "/prod/prod/exportProd";
    private static final String GET_FIN_DETAIL_FORM = "/platform/financialManagement/getFinanDetailForm";
    private static final String EXPORT_SHOP = "/platform/shopDetail/exportShop";
    private static final String EXPORT_SUPPLIER = "/supplier/supplier/exportSupplier";
    private static final String IMPORT_SUPPLIER = "/supplier/supplier/importExcel";
    private static final String MODEL_SUPPLIER = "/supplier/supplier/downLoadModel";
    private static final String MODEL_SUPPLIER_PROD = "/supplier/supplierProd/downLoadModel";
    private static final String IMPORT_SUPPLIER_PROD = "/supplier/supplierProd/importExcel";
    private static final String EXPORT_SUPPLIER_PROD = "/supplier/supplierProd/exportSupplierProd";
    private static final String EXPORT_STOCK_BILL_LOG = "/shop/stockBillLog/exportStockBillLog";
    private static final String EXPORT_STOCK_BILL_LOG_ITEM = "/shop/stockBillLogItem/downloadModel";
    private static final String IMPORT_STOCK_BILL_LOG_ITEM = "/shop/stockBillLogItem/importExcel";
    private static final String STOCK_FLOW_EXPORT_EXCEL = "/shop/stockBillLogItem/exportFlow";
    private static final String EXPORT_SKU = "/sku/exportSkuList";
    private static final String MODEL_TAKE_PROD = "/stock/takeStockProd/downLoadModel";
    private static final String IMPORT_TAKE_PROD = "/stock/takeStockProd/importExcel";
    private static final String EXPORT_TAKE_PROD = "/stock/takeStockProd/exportTakeStockProd";
    private static final String EXPORT_TAKE_STOCK = "/stock/takeStock/exportTakeStock";
    private static final String PURCHASES_ORDER_DOWNLOAD_MODEL = "/purchase/order/downloadModel";
    private static final String PURCHASES_ORDER_EXPORT_EXCEL = "/purchase/order/exportExcel";
    private static final String PURCHASES_ORDER_INBOUND_EXPORT = "/purchase/order/inbound/export";
    private static final String PURCHASES_ORDER_INBOUND_DOWNLOAD_MODEL = "/purchase/order/inbound/downloadModel";
    private static final String PURCHASES_ORDER_INBOUND_EXPORT_EXCEL = "/purchase/order/inbound/exportExcel";
    private static final String DISTRIBUTION_QR_CODE_INVITATION = "/p/distribution/qrCode/invitation";
    private static final String MINI_QR_CODE = "/qrcodeTicket/miniQrCode";
    /**
     * 权限
     */
    private static final String GET_SHOP_MENU = "/sys/shopMenu/nav";
    /**
     * jsapi签名
     */
    private static final String CREATE_JSAPI_SIGNATURE = "/p/wx/jsapi/createJsapiSignature";

    static final HashSet<String> SENSITIVE_WHITE_SET;

    static {
        SENSITIVE_WHITE_SET = new HashSet<>();
        SENSITIVE_WHITE_SET.add(SENSITIVE_WORD_SEARCH_URL);
        SENSITIVE_WHITE_SET.add(WEBJARS_URL);
        SENSITIVE_WHITE_SET.add(SWAGGER_URL);
        SENSITIVE_WHITE_SET.add(API_DOCS_URL);
        SENSITIVE_WHITE_SET.add(DOC_URL);
        SENSITIVE_WHITE_SET.add(SWAGGER_UI_URL);
        SENSITIVE_WHITE_SET.add(SWAGGER_RESOURCES_URL);
        SENSITIVE_WHITE_SET.add(CAPTCHA_URL);
        SENSITIVE_WHITE_SET.add(SAVE_CONFIG);
        SENSITIVE_WHITE_SET.add(SOLD_EXCEL);
        SENSITIVE_WHITE_SET.add(PL_SOLD_EXCEL);
        SENSITIVE_WHITE_SET.add(SCORE_SOLD_EXCEL);
        SENSITIVE_WHITE_SET.add(IMPORT_USER_MODEL);
        SENSITIVE_WHITE_SET.add(ADMIN_FORM_EXCEL);
        SENSITIVE_WHITE_SET.add(PLATFORM_FORM_EXCEL);
        SENSITIVE_WHITE_SET.add(EXPORT_USER);
        SENSITIVE_WHITE_SET.add(UN_DELIVERY_ORDER_EXCEL);
        SENSITIVE_WHITE_SET.add(DOWNLOAD_MODEL);
        SENSITIVE_WHITE_SET.add(EXPORT_PROD);
        SENSITIVE_WHITE_SET.add(GET_FIN_DETAIL_FORM);
        SENSITIVE_WHITE_SET.add(GET_SHOP_MENU);
        SENSITIVE_WHITE_SET.add(EXPORT_SHOP);
        SENSITIVE_WHITE_SET.add(IMPORT_SUPPLIER);
        SENSITIVE_WHITE_SET.add(EXPORT_SUPPLIER);
        SENSITIVE_WHITE_SET.add(MODEL_SUPPLIER);
        SENSITIVE_WHITE_SET.add(MODEL_SUPPLIER_PROD);
        SENSITIVE_WHITE_SET.add(IMPORT_SUPPLIER_PROD);
        SENSITIVE_WHITE_SET.add(EXPORT_SUPPLIER_PROD);
        SENSITIVE_WHITE_SET.add(EXPORT_STOCK_BILL_LOG);
        SENSITIVE_WHITE_SET.add(EXPORT_STOCK_BILL_LOG_ITEM);
        SENSITIVE_WHITE_SET.add(IMPORT_STOCK_BILL_LOG_ITEM);
        SENSITIVE_WHITE_SET.add(CREATE_JSAPI_SIGNATURE);
        SENSITIVE_WHITE_SET.add(STOCK_FLOW_EXPORT_EXCEL);
        SENSITIVE_WHITE_SET.add(EXPORT_SKU);
        SENSITIVE_WHITE_SET.add(MODEL_TAKE_PROD);
        SENSITIVE_WHITE_SET.add(IMPORT_TAKE_PROD);
        SENSITIVE_WHITE_SET.add(EXPORT_TAKE_PROD);
        SENSITIVE_WHITE_SET.add(EXPORT_TAKE_STOCK);
        SENSITIVE_WHITE_SET.add(PURCHASES_ORDER_DOWNLOAD_MODEL);
        SENSITIVE_WHITE_SET.add(PURCHASES_ORDER_EXPORT_EXCEL);
        SENSITIVE_WHITE_SET.add(PURCHASES_ORDER_INBOUND_EXPORT);
        SENSITIVE_WHITE_SET.add(PURCHASES_ORDER_INBOUND_DOWNLOAD_MODEL);
        SENSITIVE_WHITE_SET.add(PURCHASES_ORDER_INBOUND_EXPORT_EXCEL);
        SENSITIVE_WHITE_SET.add(DISTRIBUTION_QR_CODE_INVITATION);
        SENSITIVE_WHITE_SET.add(MINI_QR_CODE);
    }

    private SensitiveWordConfig() {

    }

    public static HashSet<String> getSensitiveWhiteSet() {
        return SENSITIVE_WHITE_SET;
    }
}
