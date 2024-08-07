package com.chinagoods.bigdata.functions.string;

import com.chinagoods.bigdata.functions.utils.MysqlUtil;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyl
 * date: 2024-07-29
 * time: 10:30
 * describe: 获取对应时间设备所属食堂rest_name_format
 */
@Description(name = "rest_name_format"
        , value = "_FUNC_(string,string) - Return the name of the cafeteria based on the device code and payment time"
        , extended = "Example:\n> SELECT _FUNC_(device_no,pay_at) FROM src;")
public class UDFRestNameFormat extends GenericUDF {
    private static final Logger logger = LoggerFactory.getLogger(UDFRestNameFormat.class);
    private static final String DB_URL = "jdbc:mysql://172.18.5.22:3306/source?characterEncoding=UTF-8&useSSL=false";
    private static final String DB_USER = "source";
    private static final String DB_PASSWORD = "jP8*dKw,bRjBVos=";
    /**
     * 设备对应食堂有效期 kjt_device_valid_inf
     */
    private static final String REST_QUERY_SQL = "select device_no,rest_name,valid_start_at,valid_end_at from kjt_device_valid_inf ";
    private ObjectInspectorConverters.Converter[] converters;
    private static final int ARG_COUNT = 2;
    List<List<String>> deviceList = new ArrayList<List<String>>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public UDFRestNameFormat() {
    }

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != ARG_COUNT) {
            throw new UDFArgumentException(
                    "The function rest_name_format takes exactly " + ARG_COUNT + " arguments.");
        }
        converters = new ObjectInspectorConverters.Converter[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            converters[i] = ObjectInspectorConverters.getConverter(arguments[i],
                    PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }
        queryRestDeviceList();
        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }

    @Override
    public String evaluate(DeferredObject[] arguments) throws HiveException {
        assert (arguments.length == ARG_COUNT);
        String deviceNo = converters[0].convert(arguments[0].get()).toString();
        String payAt = converters[0].convert(arguments[1].get()).toString();
        LocalDateTime paramAt = LocalDateTime.parse(payAt, formatter);
        for (List<String> subList : deviceList) {
            LocalDateTime start = LocalDateTime.parse(subList.get(2), formatter);
            LocalDateTime end = LocalDateTime.parse(subList.get(3), formatter);
            if (subList.get(0).equals(deviceNo) && paramAt.isAfter(start) && paramAt.isBefore(end)) {
                return subList.get(1);
            }
        }
        return null;
    }

    /**
     * @throws UDFArgumentException 查询mysql异常
     */
    public void queryRestDeviceList() throws UDFArgumentException {
        try {
            MysqlUtil mysqlUtil = new MysqlUtil(DB_URL, DB_USER, DB_PASSWORD);
            deviceList = mysqlUtil.getLists(REST_QUERY_SQL);
        } catch (Exception e) {
            logger.error("Failed to query the rest name. Procedure, the error details are: ", e);
            throw new UDFArgumentException(String.format("Failed to query the rest name. Procedure, the error details are: %s", e));
        }
    }

    @Override
    public String getDisplayString(String[] strings) {
        assert (strings.length == ARG_COUNT);
        return "rest_name_format(" + strings[0] + ", " + strings[1] + ")";
    }

    public static void main(String[] args) throws HiveException {
        List<String> list = new ArrayList<>();
        list.add("YPT13285");
        list.add("YPT13286");
        list.add("YPT13289");
        list.add("YPT13290");
        UDFRestNameFormat urlFormat = new UDFRestNameFormat();
        for (String dvo : list) {
            String restName;
            try {
                DeferredObject[] deferredObjects = new DeferredObject[2];
                // 设备编码、支付时间
                deferredObjects[0] = new DeferredJavaObject(dvo);
                deferredObjects[1] = new DeferredJavaObject("2024-07-16 12:34:23");
                ObjectInspector[] inspectorArr = new ObjectInspector[2];
                inspectorArr[0] = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
                inspectorArr[1] = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
                urlFormat.initialize(inspectorArr);
                restName = urlFormat.evaluate(deferredObjects);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(restName);
        }
    }
}
