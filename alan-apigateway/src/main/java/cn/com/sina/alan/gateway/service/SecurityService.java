package cn.com.sina.alan.gateway.service;

import cn.com.sina.alan.common.config.ApiKeyStatus;
import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.dao.KeyModelMapper;
import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.common.exception.ApiKeyException;
import cn.com.sina.alan.common.model.KeyModel;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by whf on 8/6/16.
 */
@Service
public class SecurityService {
    private static Logger log = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private KeyModelMapper keyMapper;

    /**
     * 验证签名是否正确
     * @param parmMap
     * @param pubKey
     * @return 正确返回true
     */
    @Transactional(readOnly = true)
    public boolean checkSign(Map<String, String> parmMap, String pubKey, String sign) throws AlanException {
        if (StringUtils.isEmpty(pubKey) || CollectionUtils.isEmpty(parmMap)) {
            log.warn("参数Map或pubKey不能为空");
            return false;
        }

        // 查出对应的key
        KeyModel key = keyMapper.selectByPublicKey(pubKey);
        if (null == key) {
            log.warn("公钥 {} 不存在", pubKey);
            throw new ApiKeyException("公钥" + pubKey + "不存在");
        }

        // 检查key的状态是否为启用
        if (false == ApiKeyStatus.isEnable(key.getStatus())) {
            log.warn("pub_key {} 被禁用", pubKey);
            throw new ApiKeyException("key" + pubKey + "禁用");
        }

        // 计算签名
        String correctSign = hmacSign(parmMap, key.getSecKey());
        log.debug("public key = {}, secret key = {}, 对参数 {} 的签名结果为: {}", pubKey, key.getSecKey(), parmMap, correctSign);

        boolean result = correctSign.equalsIgnoreCase(sign);
        log.debug("签名验证结果: {}, 正确的签名: {}, 当前签名: {}", result, correctSign, sign);

        return result;
    }

    /**
     * 将Map中的参数排序后组合成字符串,并使用HMAC-Sha1算法签名
     * @param parmMap
     * @param secKey
     * @return
     */
    private String hmacSign(Map<String, String> parmMap, String secKey) {
        StringBuilder sb = new StringBuilder(parmMap.size() * Const.AVERAGE_PARM_LENGTH);

        Map<String, String> sortedMap = new TreeMap<>(parmMap);
        sortedMap.entrySet().forEach( entry -> {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        } );

        String sequence = sb.toString();
        log.debug("排序后的参数串: {}", sequence);

        String sign = HmacUtils.hmacSha1Hex(secKey.getBytes(), sequence.getBytes());

        return sign;
    }
}
