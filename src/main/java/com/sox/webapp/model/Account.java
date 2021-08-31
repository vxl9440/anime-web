package com.sox.webapp.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Account implements Serializable {

    @Pattern(regexp = "[a-z0-9A-Z]{6,20}", message = "账号必须为6~20个字母和数字组合")
    @NotNull(message = "用户名不能为空")
    @TableId
    private String username;

    @Pattern(regexp = "[a-z0-9A-Z!@#$%^&*()]{6,20}", message = "密码必须为6~20个字母和数字和特殊符号组合")
    @NotNull(message = "密码不能为空")
    private String password;

    @Pattern(regexp = "[a-z0-9A-Z!@#$%^&*()]{6,20}", message = "密码必须为6~20个字母和数字和特殊符号组合")
    @TableField(exist = false)
    private String oldPassword;

    private String role;
}
