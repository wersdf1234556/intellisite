package org.tonzoc.model;

import java.util.Arrays;
import java.util.List;

//返回结果
public class ReturnModel {

    private Integer status;
    private String message;
    private List<Object> result;
    private Integer total_item_count;  // 结果总条数
    private String last_timestamp;  // 结束时间戳
    private Object[] data_list;

    public ReturnModel() {
    }

    public ReturnModel(Integer status, String message, List<Object> result, Integer total_item_count, String last_timestamp, Object[] data_list) {
        this.status = status;
        this.message = message;
        this.result = result;
        this.total_item_count = total_item_count;
        this.last_timestamp = last_timestamp;
        this.data_list = data_list;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public String getLast_timestamp() {
        return last_timestamp;
    }

    public void setLast_timestamp(String last_timestamp) {
        this.last_timestamp = last_timestamp;
    }

    public void setTotal_item_count(Integer total_item_count) {
        this.total_item_count = total_item_count;
    }

    public void setData_list(Object[] data_list) {
        this.data_list = data_list;
    }

    @Override
    public String toString() {
        return "ReturnModel{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", total_item_count=" + total_item_count +
                ", last_timestamp='" + last_timestamp + '\'' +
                ", data_list=" + Arrays.toString(data_list) +
                '}';
    }
}
