package com.chinagoods.bigdata.functions.card;

import com.chinagoods.bigdata.functions.utils.CardUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * date: 2016-07-25
 * time: 20:11
 */
@Description(name = "id_card_city"
        , value = "_FUNC_(string) - get city by given china id card."
        , extended = "Example:\n > select _FUNC_(string) from src;")
public class UDFChinaIdCardCity extends UDF {
    private Text result = new Text();

    public UDFChinaIdCardCity() {
    }

    public Text evaluate(Text idCard) {
        if (idCard == null) {
            return null;
        }
        String city = CardUtils.getIdCardCity(idCard.toString());
        if (city == null) {
            return null;
        }
        result.set(city);
        
        return result;
    }
}
