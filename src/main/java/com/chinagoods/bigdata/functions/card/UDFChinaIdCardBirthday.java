package com.chinagoods.bigdata.functions.card;

import com.chinagoods.bigdata.functions.utils.CardUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * date: 2016-07-25
 * time: 20:14
 */
@Description(name = "id_card_birthday"
        , value = "_FUNC_(string) - get birthday by given china id card."
        , extended = "Example:\n > select _FUNC_(string) from src;")
public class UDFChinaIdCardBirthday extends UDF{
    private Text result = new Text();

    public UDFChinaIdCardBirthday() {
    }

    public Text evaluate(Text idCard) {
        if (idCard == null) {
            return null;
        }
        String birthday = CardUtils.getIdCardBirthday(idCard.toString());
        if (birthday == null) {
            return null;
        }
        result.set(birthday);
        return result;
    }
}
