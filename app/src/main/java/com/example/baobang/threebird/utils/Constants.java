package com.example.baobang.threebird.utils;

public class Constants {
    public static int CLIENT_REQUEST_CODE = 1;
    public static int PRODUCT_REQUEST_CODE = 2;
    public static int ORDER_REQUEST_CODE = 3;
    public static String CLIENT = "CLIENT";
    public static String PRODUCT = "PRODUCT";
    public static String ORDER = "ORDER";
    public static String OPTION = "OPTION";
    public static int CAMERA_PIC_REQUEST = 1337;
    public static int SELECT_FILE = 1338;
    public static int AVATAR_WIDTH = 100;
    public static int AVATAR_HEIGHT = 100;
    public static int IMAGE_WIDTH = 100;
    public static int IMAGE_HEIGHT = 110;

    //
    public static int PADDING = 12;
    public static int MARGIN = 20;
    public static int MARGIN_SMALL = 8;
    // order status;
    public static int COMPLETED = 0;
    public static int CANCEL = 1;
    public static int DELIVERY = 2;

    // option
    public static int ADD_OPTION = 0;
    public static int EDIT_OPTION = 1;
    public static int DETAIL_OPTION = 2;
    public static int DELETE_OPTION = 3;

    public static String EMAIL_REGULAR = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static String PHONE_REGULAR = "(\\+84|0)\\d{9,10}";
    public static String NUMBER_REGUAR = "\\d{1,}";
}
