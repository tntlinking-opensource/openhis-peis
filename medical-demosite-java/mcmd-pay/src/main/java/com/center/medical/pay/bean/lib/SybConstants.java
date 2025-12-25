package com.center.medical.pay.bean.lib;

/**
 * 这个文件仅用作测试报备设备使用,正式的数据从数据库里取
 */
public class SybConstants {
	//正式环境参数(东区)
//	public static final String SYB_ORGID = "";//集团/机构模式下该参数不为空，且appid与key是与次参数对应
//	public static final String SYB_CUSID = "660452080991KGT";
//	public static final String SYB_APPID = "00287295";
//	public static final String SYB_MD5_APPKEY = "AAAD52FF0327E6CDA05E34A055A8465B";
//	public static final String SYB_APIURL = "https://vsp.allinpay.com/apiweb/unitorder";//生产环境
//	public static final String SIGN_TYPE = "SM2";
//	//设备号,要用的话得重新报备
//	public static final String TERMINFO = "{\"longitude\":\"+120.18671\",\"latitude\":\"+35.960840\",\"termno\":\"49584958\",\"devicetype\":\"11\"}";
//	public static final String SYB_RSACUSPRIKEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCgySS6FjcrhIbPHbNCbJ6BafcF/y+8gPJ2qoqfa9Cjk2X/dAkp+TN/yYK5WJYpoi7FrxbGn3Md9y8dvL8ndR+MUrNYpN2b39eyitZrCJ9QYkFwRY3d847NCSWS8DhSaZVxwjjXYJU2iM90PM1JRAM/LWLbRcIrSiTGikGI48rzJhYOh0KOdTYu3EhxlgjP2e2Edvf2n/RUA+jmeCAusLQ/pMTm5XNXb/zuzNcslZqCKVI9LaROITj1dAPSuwbs1iXJnB7Jwf54aP7IbIZjswWLVZvxlj6cvLI21fS2+TmCEoZ23px7BcKR4IwZhqJkTHk787F+4yRfRxAU4D9BsLNLAgMBAAECggEANM+chprBaEPRW7ovpQO0VGGPx6J39XyNJzkA6zgogXHqsKtWTzkRF0oC7AoS2H3yyu+llXvN/euwyB2006r+v8JyMg2E1KwDVxuPLfzZBzgeslfGLz7Oir4UX10Tws33TjQzI1sP56esLEvESepS/q9Tpx4Uls91oNjMib/4a0Bz8JcHGPnJT1MKL6i2zFPemsk5vbcfuyCh06OQjascdkxpMv/5P2TS+aDgVIPmWuZFcAUZ5Mu7+iLCozbBWD2wzsc3cFWnr6sdj5PVuh3JNpHNhW33Cb2B8V0J7WPd97limympzc4NqCKIw3eKv4M6DSSJxegyoJMPrG6vl9M+sQKBgQDP/hB9INodI43bPaT7gzry1PZV4lVNt8p0/fioNZKu1a2oMNiuQw0FUl2fJP0cGuXp+8dQYCD6uee+Fx+aE/XOCu4eN6qp/aZVwHgr2uYOIjA/qIJZhmpAKkKMLZ0A3v1iI2s8rc3pvh1nEDfEkdIMD+KyROu2S31GukbwSM40TQKBgQDF5bXghPnm9o+/HFwaiQcHQpeUXih6Y/3uTbYNqZVlwD9gpg1io+fTsKdUIJFAsfD80HYTHcQa3ZZxeV9ifR/iYhVq62pQ96Wam+fTvomFBgVeSuQ5Sk9bcV2jcodmJJnDofZfHkGIsVGtutZL+444jg3q9MaXt0qUlKFlKF+x9wKBgCE8D5Zu8WtGixxFNV+bEIhMzy/aApdwjsEy89Cs/stBCYaeh4EQ5bu79YnAd1FOWv6QZMDLG3n0yrrtJvePyO8rf/EEJBkVlsaNAqa/S5Nmkx8Y5fs4JzIpjoinoTbKauKLlk8T1ptK7ZfSV5bpVg7nWunxaVivp0zt5vfuKkThAoGAd4p/BxTMcDvYMTVMXasQV85eCf2mrefg6CaBJleeK0yp/BPSWhdwXmuDaEoT8+SJEe5KpNmCMEhSLFGI0NGMhQ+CqId0PVya6H3s1/ds0rIEZCURH5pHvZEJY0Y3i/fn+kjHrkV0iMGz+SG3eUaHbppAxgD+RX8uyfnOVHDC/ZkCgYBnKLif3DnC6tBDr6ZsLEe6uE59x9JrsuIucKe1mj8ON6Qd2ttiprBFLssdi3WwHj2aIbioSzsP+4OahogsOpQB1Bani1E2+2nSMWnMcen+kiJAFMinS7tCp+NrGFupaCm1oMM+xucLJUF6ATnQve6SE7P0wOBxFLCao5Qep9HHoA==\n";
//	public static final String SYB_RSATLPUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMkkuhY3K4SGzx2zQmyegWn3Bf8vvIDydqqKn2vQo5Nl/3QJKfkzf8mCuViWKaIuxa8Wxp9zHfcvHby/J3UfjFKzWKTdm9/XsorWawifUGJBcEWN3fOOzQklkvA4UmmVccI412CVNojPdDzNSUQDPy1i20XCK0okxopBiOPK8yYWDodCjnU2LtxIcZYIz9nthHb39p/0VAPo5nggLrC0P6TE5uVzV2/87szXLJWagilSPS2kTiE49XQD0rsG7NYlyZweycH+eGj+yGyGY7MFi1Wb8ZY+nLyyNtX0tvk5ghKGdt6cewXCkeCMGYaiZEx5O/OxfuMkX0cQFOA/QbCzSwIDAQAB";
////	/**商户sm2私钥,用于向通联发起请求前进行签名**/
//	public static final String SYB_SM2PPRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQghSUW/OvckEYgRGJUBk/nEYwLzNQPWMTw6keGmG75kfmgCgYIKoEcz1UBgi2hRANCAARGY4lx16FS08EB5iQivxzVQu5HYavdlyDi5+yzUdV1BmBBUE40Blp42+xfjiIwV9/W23is5YeTlUSeEOiVyDns";
////	/**通联平台sm2公钥，用于请求返回或者通联通知的验签**/
////	/**固定这个公钥,千万别改成后台的公钥，要不然会导致签名失败**/
//	public static final String SYB_SM2TLPUBKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEBQicgWm0KAMqhO3bdqMUEDrKQvYg8cCXHhdGwq7CGE6oJDzJ1P/94HpuVdBf1KidmPxr7HOH+0DAnpeCcx9TcQ==";





//	//正式环境参数(西区)
//	public static final String SYB_ORGID = "";//集团/机构模式下该参数不为空，且appid与key是与次参数对应
//	public static final String SYB_CUSID = "660452080111ZFU";
//	public static final String SYB_APPID = "00302716";
//	public static final String SYB_MD5_APPKEY = "CCCD52FF0123E8CDA0534A066A8465B";
//	public static final String SYB_APIURL = "https://vsp.allinpay.com/apiweb/unitorder";//生产环境
//	public static final String SIGN_TYPE = "SM2";
//	//设备号,要用的话得重新报备
//	public static final String TERMINFO = "{\"longitude\":\"+120.42402\",\"latitude\":\"+35.53319\",\"termno\":\"49584969\",\"devicetype\":\"11\"}";
//	public static final String SYB_RSACUSPRIKEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDBTWlbM5RF1blBxT9oMCML+/TVP5+hBFLEwxeNoCDCvo4mYvVWhcLlYOtAAj/dD7aNdoub2rN4fXfVBalD1vpvicGcAlUreaoDzChfE9zTtbpbcvHRMftivP0GhuJKUHU5LUIk01CIE50eU6E+bRZP4uL8d8EzXbrm5Mkl1//HERLiDoAu5P/bDZMsNJ//EQPh5X+lTcc3dNLfYCqLrVVBSZT2sMvICMhnvstEzDPF6DkwAIQkME5j9j5N9A5VNyb1i/QjJHTR6Ox33LYdmFw/6dn7LR4z088R6dlSKY7rA8e1Z3YVioHq5JWBCTtiV7C2Y4/mhP/l7W1weW6UuEFfAgMBAAECggEAF7gx3+eTNQM18YH3NwfacmuEjlr5ca7P9eYB8LZ/8/Whssh81hT20Te/K5e7EhtjVAuCXmm2kRKa+ZBGxhTN8+bW/kJJBNsv3LnDZcX7Ax2EacP6iVLVR+Hsjc2lBz0ajUgQ8Dzf37rBvQltD/XJ1vFT12qzJzmZUsuAepIn3YYeZAKv4sZm7T3scrowGsrBt+jW/3IH5GE6owO3idK3CCj0zG7NOAHyfbYqbxKXPji3rQRGiNYeEuDNif8BaBomUrlChF90fFtcrsSAWM11hmr48vauznKJdbNYqupOBBK9ThjntN1jaLhmxsiHTAYYPPyuXKBFFbExNEnfd8tqOQKBgQDg7t+FF8FgUIO8dqgRBtWtndvne8dL84A9rovouwrt+8V5nx4VRVqD2n3iuzx6SK3ZwFpVQACfmbv+n/xbIoZwlVj3i6Jed5vHuwaGiBy7oqmfJvbOfDgoRGuRZ0N14cow4/tLAkaZ4Ca7t4y551eIWbbMZJ1Yq3V68hbFpATuKQKBgQDcACXdIuBmeDvNqIkRQ9SKdPVd+46JBpOUhRC5JEeQ0tdmjArM1PRcJrqIy7M66hWEpF9R8RsYOmLlwvMj6W3M+e/Ej4IaOKBVedkFTfr460p38NZAGqGXa2eHige+I6Y23yWOnukhMhP15pq1PB3t+gkxHytCzi7p7lt+mKcURwKBgBd6t9hTv3rHTMkKdca5OOrTlJXYRNh3xYYqxy33AFARPO7SHjjIMYeb8hINy2VUWtllC8jkUmxWhtowZw44T2JHJK9KYxuLTraNnkPCx4UKmLwlPBQXwWDqq3PVHKSceheaZKvSw/3h+QkVQtpQ/MEd5Ef3pLkRxMSdqqTc1yrpAoGBAIrdUQkXVZt4cw8qPXJ3ZTYMA0DSYzLcwtSxdU/XtZCVdT8WUE7gcGmmTIHCShx62yBBfKVFUfvmBrzOZBib5CHI9W4Vw2J3scSiR7kQcsUiB3xTuXj3Fl50fgHQv3hmGilyistORQseB/lkGO0iyCWJeLnX2b+uU3B3cqsjAftLAoGBAMsE0KVOopISyCmC8Z+oitvMyJAeRQJI3ajhY5ecTyWwdbHGdS+erzwtlEpgTU3yIRm++/xoyNdc3a/IdjOiN4lm/1HDjK23WD8mE88EBInnMZTHahnP2YReSCpEnw3WNhf6h9V8nauq5EHm2OWG8nJu1xvaAC4z3Rz/p1Tri9lQ\n";
//	public static final String SYB_RSATLPUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwU1pWzOURdW5QcU/aDAjC/v01T+foQRSxMMXjaAgwr6OJmL1VoXC5WDrQAI/3Q+2jXaLm9qzeH131QWpQ9b6b4nBnAJVK3mqA8woXxPc07W6W3Lx0TH7Yrz9BobiSlB1OS1CJNNQiBOdHlOhPm0WT+Li/HfBM1265uTJJdf/xxES4g6ALuT/2w2TLDSf/xED4eV/pU3HN3TS32Aqi61VQUmU9rDLyAjIZ77LRMwzxeg5MACEJDBOY/Y+TfQOVTcm9Yv0IyR00ejsd9y2HZhcP+nZ+y0eM9PPEenZUimO6wPHtWd2FYqB6uSVgQk7YlewtmOP5oT/5e1tcHlulLhBXwIDAQAB";
//	//	/**商户sm2私钥,用于向通联发起请求前进行签名**/
//	public static final String SYB_SM2PPRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgeqNRhzsh2Lv2B/5AzvolW4AGnM6zTfdxSDZAcfOqgyagCgYIKoEcz1UBgi2hRANCAASv7uhDEo6qHYEu72s/MEf0Tr3NqLahMR3NNUw1eGQ2cUtIRpXFAu+A9CJRViflrJhtG9GwCXuFM8fk+odS2uET";
//	//	/**通联平台sm2公钥，用于请求返回或者通联通知的验签**/
////	/**固定这个公钥,千万别改成后台的公钥，要不然会导致签名失败**/
//	public static final String SYB_SM2TLPUBKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEBQicgWm0KAMqhO3bdqMUEDrKQvYg8cCXHhdGwq7CGE6oJDzJ1P/94HpuVdBf1KidmPxr7HOH+0DAnpeCcx9TcQ==";

	//测试环境调试参数
//	public static final String SYB_ORGID = "";
//	public static final String SYB_CUSID = "990581007426001";
//	public static final String SYB_APPID = "00000051";
//	public static final String SYB_MD5_APPKEY = "allinpay888";
//	public static final String SYB_APIURL = "https://test.allinpaygd.com/apiweb/unitorder";//生产环境
//	public static final String SIGN_TYPE = "MD5";
	/**商户RSA私钥,用于向通联发起请求前进行签名**/
//	public static final String SYB_RSACUSPRIKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK5aIo+E1eyWwEIgMB8ZEZRAaWjSAglmfKVhzy8N1eLjAlqPjJgOCqXGEYt/r61AyIjCCJiYVDTHzcqstmbBU7HKpYjTsquCLjRWcL/fhMwMGBSg2bP5mqw5locSOz1gtRujmd3kZo9OIJuWtfG2+wgPPdKUdGZS+5K8WtWCF4z1AgMBAAECgYAPvvqvkPzb9tpqrmsCJ/qvM6kBazP9Ytjfe8ehFYQLT1qrUJsPMXdzNMHpYhD82eSyeymZFGrIcIIMq4/2lD+pYOMQTMGGjoVb2wnQhZFqPdgjXgOQ90E43X69jD3p5F8CuKVNa13I4l3iyfzlVIL780JPdJdug7yKEFdSeOQZUQJBAONlFpIqz87pbnwzfgO5kRTbbI7DcyObb8OEeCK3VlGB3r9P4NoMEDaXm+HnIdv53gnFq+xgbREWUt2nFq9dSUUCQQDESOIdSvIBc3KQTYR+cnlQTH0SOvm0Tlx4KekBCLxTFAFyBqnOBLdVyQb6Z1wxGz855AjnNbHy1rFhUYQ6hPfxAkAIRZUcnBITJMqwGe9rk0SDzbeVOebmVLEsG5WDLcgmDuNbcjxrsiSk178D6LSCnARHtrkaUCenh3hcN8fLeUlBAkABNP2G9pYEYkRbFM7yxBtw3feK7Cfq7uxspL1VD0uxKxdTLy1OIgNKmMDdO1N6zdMWtQtE+LSObLmMgqbQgU7RAkBFX5kl4+B3k+/aCYB/ndqd1nQIr4SNAtLFJDtlW2xah9W2lQL/7KQDT4o4dUMY51m7Bu61SAmKtralv7Hf25yf";
//	/**通联平台RSA公钥，用于请求返回或者通联通知的验签**/
//	public static final String SYB_RSATLPUBKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYXfu4b7xgDSmEGQpQ8Sn3RzFgl5CE4gL4TbYrND4FtCYOrvbgLijkdFgIrVVWi2hUW4K0PwBsmlYhXcbR+JSmqv9zviVXZiym0lK3glJGVCN86r9EPvNTusZZPm40TOEKMVENSYaUjCxZ7JzeZDfQ4WCeQQr2xirqn6LdJjpZ5wIDAQAB";
//
//	/**商户sm2私钥,用于向通联发起请求前进行签名**/
//	public static final String SYB_SM2PPRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgjj4Rk+b0YjwO+UwXofnHf4bK+kaaY5Btkd8nMP2VimmgCgYIKoEcz1UBgi2hRANCAAQqlALW4qGC3bP1x3wo5QsKxaCMEZJ2ODTTwOQ+d8UGU7GoK/y/WMBQWf5upMnFU06p5FxGooXYYoBtldgm03hq";
//	/**通联平台sm2公钥，用于请求返回或者通联通知的验签**/
//	public static final String SYB_SM2TLPUBKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE/BnA8BawehBtH0ksPyayo4pmzL/u1FQ2sZcqwOp6bjVqQX4tjo930QAvHZPJ2eez8sCz/RYghcqv4LvMq+kloQ==";
	
}
