package com.ruoyi.version.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.ruoyi.version.domain.VersionReleaseEntity;
import com.ruoyi.version.service.DingTalkService;
import com.ruoyi.version.service.IVersionReleaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * @author yzwang
 * @date 2026-01-09 10:00
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DingTalkServiceImpl implements DingTalkService {

    private final IVersionReleaseService versionReleaseService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean sendReleaseNotice(VersionReleaseEntity release) {
        release.setCreateBy(getUsername());
        release.setCreateTime(new Date());
        boolean save = versionReleaseService.save(release);
        if (!save) {
            log.error("保存发布预告信息失败:{}", release);
            throw new RuntimeException("保存发布预告信息失败");
        }
        Long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String secret = "SECc9ffcf99b768ca59df07f2a8d10bd47e1346330a8a0293fd77cab30c5570bc02";
        String stringToSign = timestamp + "\n" + secret;
       try {
           Mac mac = Mac.getInstance("HmacSHA256");
           mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
           byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
           String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
           System.out.println(sign);

           //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
           DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?sign="+sign+"&timestamp="+timestamp);
           OapiRobotSendRequest req = new OapiRobotSendRequest();
           OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
           markdown.setTitle("发布预告");
           markdown.setText(buildMarkdown(release));
           //定义 @ 对象
           OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
           //设置消息类型
           req.setMsgtype("markdown");
           req.setMarkdown(markdown);
           at.setIsAtAll(true);
           req.setAt(at);
           OapiRobotSendResponse rsp = client.execute(req,"83f9b8f330352c8fb118127c4fcc669ba55117dbc66e8497a1c3a412938d820a");
           System.out.println(rsp.getBody());
       }catch (Exception e){
              log.error("发送钉钉消息失败:{}", e.getMessage());
             throw new RuntimeException(e.getMessage());
       }
        return true;
    }

    private String buildMarkdown(VersionReleaseEntity release) {
        StringBuilder builder = new StringBuilder();
        builder.append("### 发布预告").append("\n\n");
        builder.append("- 发布产品：").append(formatValue(release.getReleaseProduct())).append("\n");
        builder.append("- 发布应用：").append(formatValue(release.getReleaseApplication())).append("\n");
        builder.append("- 发布内容：").append(formatValue(release.getReleaseContent())).append("\n");
        builder.append("- 影响范围：").append(formatValue(release.getImpactScopeName())).append("\n");
        builder.append("- 代码分支：").append(formatValue(release.getCodeBranch())).append("\n");
        builder.append("- 发布时间：").append(formatDate(release.getReleaseTime())).append("\n");
        builder.append("- 发布版本：").append(formatValue(release.getReleaseVersion())).append("\n");
        builder.append("- 发布服务：").append(formatValue(release.getReleaseService())).append("\n");
        builder.append("- 发布人：").append(formatValue(release.getReleaseUser())).append("\n");
        return builder.toString();
    }

    private String formatDate(Date date) {
        if (date == null) {
            return "未填写";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    private String formatValue(String value) {
        if (Objects.isNull(value) || value.trim().isEmpty()) {
            return "未填写";
        }
        return value.trim();
    }
}
