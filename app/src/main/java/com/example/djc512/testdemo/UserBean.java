package com.example.djc512.testdemo;

import java.util.List;

/**
 * Created by DjC512 on 2017-7-16.
 */

public class UserBean {

    /**
     * code : OK
     * data : [{"cellphone":"13655263236","id":18,"name":"2222"},{"cellphone":"15085858585","id":20,"name":"看看看看看"},{"cellphone":"11111","id":22,"name":"11111"},{"cellphone":"123456","id":25,"name":"Hahaha"}]
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cellphone : 13655263236
         * id : 18
         * name : 2222
         */

        private String cellphone;
        private int id;
        private String name;

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
