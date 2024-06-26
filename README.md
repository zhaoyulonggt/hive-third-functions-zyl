# hive-third-functions 

[![Author](https://img.shields.io/badge/Author-%E4%B8%AD%E9%BE%84%E7%A8%8B%E5%BA%8F%E5%91%98-blue.svg)](https://www.shanruifeng.win)
[![Build Status](https://travis-ci.org/aaronshan/hive-third-functions.svg?branch=master)](https://travis-ci.org/aaronshan/hive-third-functions)
[![Documentation Status](https://img.shields.io/badge/docs-latest-brightgreen.svg?style=flat)](https://github.com/aaronshan/hive-third-functions/tree/master/README.md)
[![Documentation Status](https://img.shields.io/badge/中文文档-最新-brightgreen.svg)](https://github.com/aaronshan/hive-third-functions/tree/master/README-zh.md)
[![Release](https://img.shields.io/github/release/aaronshan/hive-third-functions.svg)](https://github.com/aaronshan/hive-third-functions/releases)
[![Stars](https://img.shields.io/github/stars/aaronshan/hive-third-functions.svg?label=Stars&style=social)](https://github.com/aaronshan/hive-third-functions)

## Introduction

Some useful custom hive udf functions, especial array and json functions.

> Note:
> hive-third-functions support hive-0.11.0 or higher.

## Build

### 1. install dependency

Now, jdo2-api-2.3-ec.jar not available in the maven central repository, so we have to manually install it into our local maven repository.

```
wget https://repository.mapr.com/nexus/content/groups/mapr-public/releases/javax/jdo/jdo2-api/2.3-ec/jdo2-api-2.3-ec.jar -O ~/jdo2-api-2.3-ec.jar
mvn install:install-file -DgroupId=javax.jdo -DartifactId=jdo2-api -Dversion=2.3-ec -Dpackaging=jar -Dfile=~/jdo2-api-2.3-ec.jar
```

### 2. mvn package 

```
cd ${project_home}
mvn clean package -Ppro
```

If you want to skip unit tests, please run:
```
cd ${project_home}
mvn clean package -DskipTests -Ppro
```

It will generate hive-third-functions-${version}-shaded.jar in target directory.

You can also directly download file from [release page](https://github.com/aaronshan/hive-third-functions/releases).

> current latest version is `2.2.1`

## Maven

Now, I had already release `hive-third-functions` to maven repositories. To add a dependency on `hive-third-functions` using Maven, use the following:

```
<dependency>
  <groupId>com.github.aaronshan</groupId>
  <artifactId>hive-third-functions</artifactId>
  <version>2.2.1</version>
</dependency>
```

## Functions

### 1. string functions

| function| description |
|:--|:--|
|pinyin(string) -> string | convert chinese to pinyin|
|md5(string) -> string | md5 hash|
|sha256(string) -> string |sha256 hash|
|codepoint(string) -> integer | Returns the Unicode code point of the only character of string.|
|hamming_distance(string1, string2) -> bigint | Returns the Hamming distance of string1 and string2.|
|levenshtein_distance(string1, string2) -> bigint | Returns the Levenshtein edit distance of string1 and string2.|
|normalize(string, form) -> varchar | Transforms string with the specified normalization form. form must be be one of the following keywords: <span id="jump">Normalize Form Description</span> |
|strpos(string, substring) -> bigint | Returns the starting position of the first instance of substring in string. Positions start with 1. If not found, 0 is returned.|
|split_to_map(string, entryDelimiter, keyValueDelimiter) -> map&lt;varchar, varchar> | Splits string by entryDelimiter and keyValueDelimiter and returns a map. entryDelimiter splits string into key-value pairs. keyValueDelimiter splits each pair into key and value.|
|split_to_multimap(string, entryDelimiter, keyValueDelimiter) -> map(varchar, array(varchar)) | Splits string by entryDelimiter and keyValueDelimiter and returns a map containing an array of values for each unique key. entryDelimiter splits string into key-value pairs. keyValueDelimiter splits each pair into key and value. The values for each key will be in the same order as they appeared in string.|

[Normalize Form Description](#jump)

| Form	| Description |
|:--|:--|
| NFD	| Canonical Decomposition |
| NFC	| Canonical Decomposition, followed by Canonical Composition |
| NFKD	| Compatibility Decomposition |
| NFKC	| Compatibility Decomposition, followed by Canonical Composition |

### 2. array functions

| function| description |
|:--|:--|
|array_contains(array&lt;E&gt;, E) -> boolean | whether array contains value or not.|
|array_equals(array&lt;E&gt;, array&lt;E&gt;) -> boolean | whether two array equals or not.|
|array_intersect(array, array) -> array | returns the two array's intersection, without duplicates.|
|array_max(array&lt;E&gt;) -> E | returns the maximum value of input array.|
|array_min(array&lt;E&gt;) -> E | returns the minimum value of input array.|
|array_join(array, delimiter, null_replacement) -> string | concatenates the elements of the given array using the delimiter and an optional `null_replacement` to replace nulls.|
|array_distinct(array) -> array | remove duplicate values from the array.|
|array_position(array&lt;E&gt;, E) -> long | returns the position of the first occurrence of the element in array (or 0 if not found).|
|array_remove(array&lt;E&gt;, E) -> array | remove all elements that equal element from array.|
|array_reverse(array) -> array | reverse the array element.|
|array_sort(array) -> array | sorts and returns the array. The elements of array must be orderable.|
|array_concat(array, array) -> array | concatenates two arrays.|
|array_value_count(array&lt;E&gt;, E) -> long | count array's element number that element value equals given value.|
|array_slice(array, start, length) -> array | subsets array starting from index start (or starting from the end if start is negative) with a length of length.|
|array_element_at(array&lt;E&gt;, index) -> E | returns element of array at given index. If index < 0, element_at accesses elements from the last to the first.|
|array_shuffle(array) -> array | Generate a random permutation of the given array x.|
|sequence(start, end) -> array<Long> | Generate a sequence of integers from start to stop.|
|sequence(start, end, step) -> array<Long> | Generate a sequence of integers from start to stop, incrementing by step.|
|sequence(start_date_string, end_data_string, step) -> array<String> | Generate a sequence of date string from start to stop, incrementing by step.|
|array_value_count(array&lt;E&gt;, E) -> long | count array's element number that element value equals given value..|

### 3. map functions
| function| description |
|:--|:--|
|map_build(x&lt;K&gt;, y&lt;V&gt;) -> map&lt;K, V&gt;| returns a map created using the given key/value arrays.|
|map_concat(x&lt;K, V&gt;, y&lt;K, V&gt;) -> map&lt;K,V&gt; | returns the union of two maps. If a key is found in both `x` and `y`, that key’s value in the resulting map comes from `y`.|
|map_element_at(map&lt;K, V&gt;, key) -> V | returns value for given `key`, or `NULL` if the key is not contained in the map.|
|map_equals(x&lt;K, V&gt;, y&lt;K, V&gt;) -> boolean |  whether map x equals with map y or not.|

### 4. date functions

| function| description |
|:--|:--|
|day_of_week(date_string \| date) -> int | day of week,if monday,return 1, sunday return 7, error return null.|
|day_of_year(date_string \| date) -> int | day of year. The value ranges from 1 to 366.|
|zodiac_en(date_string \| date) -> string | convert date to zodiac|
|zodiac_cn(date_string \| date) -> string | convert date to zodiac chinese |
|type_of_day(date_string \| date) -> string | for chinese. 获取日期的类型(1: 法定节假日, 2: 正常周末, 3: 正常工作日 4:攒假的工作日),错误返回-1. |

### 5. json functions

| function| description |
|:--|:--|
|json_array_get(json, jsonPath) -> array(varchar) |returns the element at the specified index into the `json_array`. The index is zero-based.|
|json_array_length(json, jsonPath) -> array(varchar) |returns the array length of `json` (a string containing a JSON array).|
|json_array_extract(json, jsonPath) -> array(varchar) |extract json array by given jsonPath.|
|json_array_extract_scalar(json, jsonPath) -> array(varchar) |like `json_array_extract`, but returns the result value as a string (as opposed to being encoded as JSON).|
|json_extract(json, jsonPath) -> array(varchar) |extract json by given jsonPath.|
|json_extract_scalar(json, jsonPath) -> array(varchar) |like `json_extract`, but returns the result value as a string (as opposed to being encoded as JSON).|
|json_size(json, jsonPath) -> array(varchar) |like `json_extract`, but returns the size of the value. For objects or arrays, the size is the number of members, and the size of a scalar value is zero.|

### 6. bitwise functions

| function| description |
|:--|:--|
|bit_count(x, bits) -> bigint | count the number of bits set in `x` (treated as bits-bit signed integer) in 2’s complement representation |
|bitwise_and(x, y) -> bigint | returns the bitwise AND of `x` and `y` in 2’s complement arithmetic.|
|bitwise_not(x) -> bigint | returns the bitwise NOT of `x` in 2’s complement arithmetic. |
|bitwise_or(x, y) -> bigint | returns the bitwise OR of `x` and `y` in 2’s complement arithmetic.|
|bitwise_xor(x, y) -> bigint | returns the bitwise XOR of `x` and `y` in 2’s complement arithmetic. |

### 7. china id card functions

| function| description |
|:--|:--|
|id_card_province(string) -> string |get user's province|
|id_card_city(string) -> string |get user's city|
|id_card_area(string) -> string |get user's area|
|id_card_birthday(string) -> string |get user's birthday|
|id_card_gender(string) -> string |get user's gender|
|is_valid_id_card(string) -> boolean |determine is valid china id card No.|
|id_card_info(string) -> json |get china id card info. include province, city, area etc.|

### 8. geographic functions 

| function| description |
|:--|:--|
|wgs_distance(double lat1, double lng1, double lat2, double lng2) -> double | calculate WGS84 coordinate distance, in meters. |
|gcj_to_bd(double,double) -> json | GCJ-02(火星坐标系) convert to BD-09(百度坐标系), 谷歌、高德——>百度|
|bd_to_gcj(double,double) -> json | BD-09(百度坐标系) convert to GCJ-02(火星坐标系), 百度——>谷歌、高德|
|wgs_to_gcj(double,double) -> json | WGS84(地球坐标系) convert to GCJ02(火星坐标系)|
|gcj_to_wgs(double,double) -> json | GCJ02(火星坐标系) convert to GPS84(地球坐标系), output coordinate WGS-84 accuracy within 1 to 2 meters.|
|gcj_extract_wgs(double,double) -> json | GCJ02(火星坐标系) convert to GPS84, output coordinate WGS-84 accuracy within 0.5 meters. but compute cost more time than `gcj_to_wgs`. |

> 关于互联网地图坐标系的说明见: [当前互联网地图的坐标系现状](https://github.com/aaronshan/hive-third-functions/tree/master/README-geo.md)


### 9. url functions

| function| description |
|:--|:--|
|url_encode(value) -> string | escapes value by encoding it so that it can be safely included in URL query parameter names and values|
|url_decode(value) -> string | unescape the URL encoded value. This function is the inverse of `url_encode`. |
|standard_url_format(string,string) -> array(varchar) | The normalized url returns the standard url and the 3-level category name|

### 10. math functions

| function| description |
|:--|:--|
|infinity() -> double | Returns the constant representing positive infinity.|
|is_finite(x) -> boolean | Determine if x is finite.|
|is_infinite(x) -> boolean |Determine if x is infinite.|
|is_nan(x) -> boolean | Determine if x is not-a-number.|
|nan() -> double | Returns the constant representing not-a-number. |
|from_base(string, radix) -> bigint | Returns the value of string interpreted as a base-radix number.|
|to_base(x, radix) -> varchar | Returns the base-radix representation of x.|
|cosine_similarity(x, y) -> double | Returns the cosine similarity between the sparse vectors x and y|
|inverse_normal_cdf(mean, sd, p) -> double | Compute the inverse of the Normal cdf with given mean and standard deviation (sd) for the cumulative probability (p): P(N &lt; n). The mean must be a real value and the standard deviation must be a real and positive value. The probability p must lie on the interval (0, 1). |
|normal_cdf(mean, sd, v) -> double | Compute the Normal cdf with given mean and standard deviation (sd): P(N < v; mean, sd). The mean and value v must be real values and the standard deviation must be a real and positive value.|

### 11. regexp functions
| function| description |
|:--|:--|
|regexp_like(string, pattern) -> boolean | Evaluates the regular expression pattern and determines if it is contained within string.|
|regexp_extract_all(string, pattern) -> array(varchar) | Returns the substring(s) matched by the regular expression pattern in string. |
|regexp_extract(string, pattern) -> varchar | Returns the first substring matched by the regular expression pattern in string.|
|regexp_replace(string, pattern) -> varchar | Removes every instance of the substring matched by the regular expression pattern from string.|
|regexp_replace(string, pattern, replacement) -> varchar | Replaces every instance of the substring matched by the regular expression pattern in string with replacement. |

### 12. parse functions
|parse_user_agent(string) -> array(varchar) | Parses the user agent and returns an ArrayList<Text> containing device_family, os_family, os_minor, os_major, user_agent_minor, and user_agent_major.|

## Use

Put these statements into `${HOME}/.hiverc` or exec its on hive cli env.

```
add jar ${jar_location_dir}/hive-third-functions-${version}-shaded.jar
create temporary function array_contains as 'UDFArrayContains';
create temporary function array_equals as 'UDFArrayEquals';
create temporary function array_intersect as 'UDFArrayIntersect';
create temporary function array_max as 'UDFArrayMax';
create temporary function array_min as 'UDFArrayMin';
create temporary function array_join as 'UDFArrayJoin';
create temporary function array_distinct as 'UDFArrayDistinct';
create temporary function array_position as 'UDFArrayPosition';
create temporary function array_remove as 'UDFArrayRemove';
create temporary function array_reverse as 'UDFArrayReverse';
create temporary function array_sort as 'UDFArraySort';
create temporary function array_concat as 'UDFArrayConcat';
create temporary function array_value_count as 'UDFArrayValueCount';
create temporary function array_slice as 'UDFArraySlice';
create temporary function array_element_at as 'UDFArrayElementAt';
create temporary function array_shuffle as 'UDFArrayShuffle';
create temporary function sequence as 'UDFSequence';
create temporary function array_value_count as 'UDFArrayValueCount';
create temporary function bit_count as 'UDFBitCount';
create temporary function bitwise_and as 'UDFBitwiseAnd';
create temporary function bitwise_not as 'UDFBitwiseNot';
create temporary function bitwise_or as 'UDFBitwiseOr';
create temporary function bitwise_xor as 'UDFBitwiseXor';
create temporary function map_build as 'UDFMapBuild';
create temporary function map_concat as 'UDFMapConcat';
create temporary function map_element_at as 'UDFMapElementAt';
create temporary function map_equals as 'UDFMapEquals';
create temporary function day_of_week as 'UDFDayOfWeek';
create temporary function day_of_year as 'UDFDayOfYear';
create temporary function type_of_day as 'UDFTypeOfDay'; 
create temporary function zodiac_cn as 'UDFZodiacSignCn';
create temporary function zodiac_en as 'UDFZodiacSignEn';
create temporary function pinyin as 'UDFChineseToPinYin';
create temporary function md5 as 'UDFMd5';
create temporary function sha256 as 'UDFSha256';
create temporary function codepoint as 'UDFCodePoint';
create temporary function hamming_distance as 'UDFStringHammingDistance';
create temporary function levenshtein_distance as 'UDFStringLevenshteinDistance';
create temporary function normalize as 'UDFStringNormalize';
create temporary function strpos as 'UDFStringPosition';
create temporary function split_to_map as 'UDFStringSplitToMap';
create temporary function split_to_multimap as 'UDFStringSplitToMultimap';
create temporary function json_array_get as 'UDFJsonArrayGet';
create temporary function json_array_length as 'UDFJsonArrayLength';
create temporary function json_array_extract as 'UDFJsonArrayExtract';
create temporary function json_array_extract_scalar as 'UDFJsonArrayExtractScalar';
create temporary function json_extract as 'UDFJsonExtract';
create temporary function json_extract_scalar as 'UDFJsonExtractScalar';
create temporary function json_size as 'UDFJsonSize';
create temporary function id_card_province as 'UDFChinaIdCardProvince';
create temporary function id_card_city as 'UDFChinaIdCardCity';
create temporary function id_card_area as 'UDFChinaIdCardArea';
create temporary function id_card_birthday as 'UDFChinaIdCardBirthday';
create temporary function id_card_gender as 'UDFChinaIdCardGender';
create temporary function is_valid_id_card as 'UDFChinaIdCardValid';
create temporary function id_card_info as 'UDFChinaIdCardInfo';
create temporary function wgs_distance as 'UDFGeoWgsDistance';
create temporary function gcj_to_bd as 'UDFGeoGcjToBd';
create temporary function bd_to_gcj as 'UDFGeoBdToGcj';
create temporary function wgs_to_gcj as 'UDFGeoWgsToGcj';
create temporary function gcj_to_wgs as 'UDFGeoGcjToWgs';
create temporary function gcj_extract_wgs as 'UDFGeoGcjExtractWgs';
create temporary function url_encode as 'UDFUrlEncode';
create temporary function url_decode as 'UDFUrlDecode';
create temporary function infinity as 'UDFMathInfinity';
create temporary function is_finite as 'UDFMathIsFinite';
create temporary function is_infinite as 'UDFMathIsInfinite';
create temporary function nan as 'UDFMathNaN';
create temporary function is_nan as 'UDFMathIsNaN';
create temporary function from_base as 'UDFMathFromBase';
create temporary function to_base as 'UDFMathToBase';
create temporary function cosine_similarity as 'UDFMathCosineSimilarity';
create temporary function normal_cdf as 'UDFMathNormalCdf';
create temporary function inverse_normal_cdf as 'UDFMathInverseNormalCdf';
create temporary function regexp_extract as 'UDFRe2JRegexpExtract';
create temporary function regexp_extract_all as 'UDFRe2JRegexpExtractAll';
create temporary function regexp_like as 'UDFRe2JRegexpLike';
create temporary function regexp_replace as 'UDFRe2JRegexpReplace';
create temporary function regexp_split as 'UDFRe2JRegexpSplit';
create temporary function standard_url_format as 'UDFStandardUrlFormat';
create temporary function parse_user_agent as 'UDFParseUserAgent';


```

You can use these statements on hive cli env get detail of function.
```
hive> describe function zodiac_cn;
zodiac_cn(date) - from the input date string or separate month and day arguments, returns the sing of the Zodiac.
```

or

```
hive> describe function extended zodiac_cn;
zodiac_cn(date) - from the input date string or separate month and day arguments, returns the sing of the Zodiac.
Example:
 > select zodiac_cn(date_string) from src;
 > select zodiac_cn(month, day) from src;
```

### example
```
 select pinyin('中国') => zhongguo
 select md5('aaronshan') => 95686bc0483262afe170b550dd4544d1
 select sha256('aaronshan') => d16bb375433ad383169f911afdf45e209eabfcf047ba1faebdd8f6a0b39e0a32
```

```
select day_of_week('2016-07-12') => 2
select day_of_year('2016-01-01') => 1
select type_of_day('2016-10-01') => 1
select type_of_day('2016-07-16') => 2
select type_of_day('2016-07-15') => 3
select type_of_day('2016-09-18') => 4
select zodiac_cn('1989-01-08') => 魔羯座
select zodiac_en('1989-01-08') => Capricorn
```

```
select array_contains(array(16,12,18,9), 12) => true
select array_equals(array(16,12,18,9), array(16,12,18,9)) => true
select array_intersect(array(16,12,18,9,null), array(14,9,6,18,null)) => [null,9,18]
select array_max(array(16,13,12,13,18,16,9,18)) => 18
select array_min(array(16,12,18,9)) => 9
select array_join(array(16,12,18,9,null), '#','=') => 16#12#18#9#=
select array_distinct(array(16,13,12,13,18,16,9,18)) => [9,12,13,16,18]
select array_position(array(16,13,12,13,18,16,9,18), 13) => 2
select array_remove(array(16,13,12,13,18,16,9,18), 13) => [16,12,18,16,9,18]
select array_reverse(array(16,12,18,9)) => [9,18,12,16]
select array_sort(array(16,13,12,13,18,16,9,18)) => [9,12,13,13,16,16,18,18]
select array_concat(array(16,12,18,9,null), array(14,9,6,18,null)) => [16,12,18,9,null,14,9,6,18,null]
select array_value_count(array(16,13,12,13,18,16,9,18), 13) => 2
select array_slice(array(16,13,12,13,18,16,9,18), -2, 3) => [9,18]
select array_element_at(array(16,13,12,13,18,16,9,18), -1) => 18
select array_shuffle(array(16,12,18,9))
select sequence(1, 5) => [1, 2, 3, 4, 5]
select sequence(5, 1) => [5, 4, 3, 2, 1]
select sequence(1, 9, 4) => [1, 5, 9]
select sequence('2016-04-12 00:00:00', '2016-04-14 00:00:00', 24*3600*1000) => ['2016-04-12 00:00:00', '2016-04-13 00:00:00', '2016-04-14 00:00:00']
```

```
select map_build(array('key1','key2'), array(16,12)) => {"key1":16,"key2":12}
select map_concat(map_build(array('key1','key2'), array(16,12)), map_build(array('key1','key3'), array(17,18))) => {"key1":17,"key2":12,"key3":18}
select map_element_at(map_build(array('key1','key2'), array(16,12)), 'key1') => 16
select map_equals(map_build(array('key1','key2'), array(16,12)), map_build(array('key1','key2'), array(16,12))) => true
```

```
select id_card_info('110101198901084517') => {"valid":true,"area":"东城区","province":"北京市","gender":"男","city":"北京市"}
```

```
select json_array_get("[{\"a\":{\"b\":\"13\"}}, {\"a\":{\"b\":\"18\"}}, {\"a\":{\"b\":\"12\"}}]", 1); => {"a":{"b":"18"}}
select json_array_get('["a", "b", "c"]', 0); => a
select json_array_get('["a", "b", "c"]', 1); => b
select json_array_get('["c", "b", "a"]', -1); => a
select json_array_get('["c", "b", "a"]', -2); => b
select json_array_get('[]', 0); => null
select json_array_get('["a", "b", "c"]', 10); => null
select json_array_get('["c", "b", "a"]', -10); => null
select json_array_length("[{\"a\":{\"b\":\"13\"}}, {\"a\":{\"b\":\"18\"}}, {\"a\":{\"b\":\"12\"}}]"); => 3
select json_array_extract("[{\"a\":{\"b\":\"13\"}}, {\"a\":{\"b\":\"18\"}}, {\"a\":{\"b\":\"12\"}}]", "$.a.b"); => ["\"13\"","\"18\"","\"12\""]
select json_array_extract_scalar("[{\"a\":{\"b\":\"13\"}}, {\"a\":{\"b\":\"18\"}}, {\"a\":{\"b\":\"12\"}}]", "$.a.b") => ["13","18","12"]
select json_extract("{\"a\":{\"b\":\"12\"}}", "$.a.b"); => "12"
select json_extract_scalar("{\"a\":{\"b\":\"12\"}}", "$.a.b") => 12
select json_extract_scalar('[1, 2, 3]', '$[2]');
select json_extract_scalar(json, '$.store.book[0].author');
select json_size('{"x": {"a": 1, "b": 2}}', '$.x'); => 2
select json_size('{"x": [1, 2, 3]}', '$.x'); => 3
select json_size('{"x": {"a": 1, "b": 2}}', '$.x.a'); => 0
```

```
select gcj_to_bd(39.915, 116.404) => {"lng":116.41036949371029,"lat":39.92133699351022}
select bd_to_gcj(39.915, 116.404) => {"lng":116.39762729119315,"lat":39.90865673957631}
select wgs_to_gcj(39.915, 116.404) => {"lng":116.41024449916938,"lat":39.91640428150164}
select gcj_to_wgs(39.915, 116.404) => {"lng":116.39775550083061,"lat":39.91359571849836}
select gcj_extract_wgs(39.915, 116.404) => {"lng":116.39775549316407,"lat":39.913596801757805}
```

```
select url_encode('http://shanruifeng.cc/') => http%3A%2F%2Fshanruifeng.cc%2F
select standard_url_format('wap','https://m.chinagoods.com/en/venue?id=14&dsds=d') => ["https://m.chinagoods.com/en/venue/?id=14","营销会场","营销会场","测试-领券中心"] 
```

```
select cosine_similarity(map_build(array['a'], array[1.0]), map_build(array['a'], array[2.0])); => 1.0
```

```
select parse_user_agent('Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36') => [Mac, Mac OS X, 15, 10, 0, 69]
```
