/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: AdminCustomController
 * @packageName: com.sox.webapp.controller.admin
 * @description: A class to handle http requests and responses for admin custom service
 * @date: 2021-07-21
 */
package com.sox.webapp.controller.admin;

import com.sox.webapp.util.Constant;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCustomController {

    /**
     * update the notification message, only the user who is ROLE_GOD can execute this method
     * @param notification data from the user
     * @return redirect to a URL
     */
    @RequestMapping("/admin/editNotification")
    @Secured({"ROLE_GOD"})
    public String editNotification(@RequestParam("notification") String notification){
        Constant.notificationMessage = notification;
        return "redirect:/admin/adminCustom";
    }
}
